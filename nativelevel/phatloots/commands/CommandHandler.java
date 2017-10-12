package nativelevel.phatloots.commands;

import nativelevel.phatloots.PhatLoots;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandHandler implements CommandExecutor {
    private static enum ParameterType {
        STRING, INT, DOUBLE, BOOLEAN, MATERIAL, PLAYER, OFFLINEPLAYER,
        WORLD, PHATLOOT;

        public static ParameterType getType(Class parameter) {
            try {
                return ParameterType.valueOf(parameter.getSimpleName().toUpperCase());
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private static final Comparator<Method> METHOD_COMPARATOR = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return Double.compare(((Method) o1).getAnnotation(CodCommand.class).weight(),
                                  ((Method) o2).getAnnotation(CodCommand.class).weight());
        }
    };

    private static final Comparator<CodCommand> CODCOMMAND_COMPARATOR = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return Double.compare(((CodCommand) o1).weight(), ((CodCommand) o2).weight());
        }
    };

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CodCommand {
        String command();
        String subcommand() default "";
        double weight() default 0;
        String[] aliases() default {};
        String[] usage() default {};
        String permission() default "";
        int minArgs() default 0;
        int maxArgs() default -1;
    }

    private final boolean groupedCommands;
    private final String parentCommand;
    private final TreeSet<CodCommand> metas = new TreeSet<>(CODCOMMAND_COMPARATOR);
    private final HashMap<CodCommand, TreeSet<Method>> methods = new HashMap<>();
    private final Properties aliases = new Properties();
    private final JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        groupedCommands = false;
        parentCommand = null;
    }

    public CommandHandler(JavaPlugin plugin, String commandGroup) {
        this.plugin = plugin;
        groupedCommands = true;
        PluginCommand command = plugin.getCommand(commandGroup);
        if (command == null) {
            plugin.getLogger().warning("CodCommand " + commandGroup + " was not found in plugin.yml");
            parentCommand = null;
        } else {
            command.setExecutor(this);
            parentCommand = command.getName();
        }
    }

    /**
     * Registers all command methods from the given class
     *
     * @param commandClass The given Class
     */
    public void registerCommands(Class<?> commandClass) {
        //Find each CodCommand in the class
        for (Method method : commandClass.getDeclaredMethods()) {
            if (method.getAnnotation(CodCommand.class) == null) {
                //Not a CodCommand
                continue;
            }

            //Verify the command returns a boolean
            if (method.getReturnType() == Boolean.class) {
                plugin.getLogger().warning("CodCommand " + method.getName() + " does not return a boolean");
                continue;
            }

            CodCommand annotation = method.getAnnotation(CodCommand.class);

            if (!groupedCommands) {
                PluginCommand command = plugin.getCommand(annotation.command());
                if (command == null) {
                    plugin.getLogger().warning("CodCommand " + method.getName() + " was not found in plugin.yml");
                    continue;
                }
                command.setExecutor(this);
                command.setAliases(Arrays.asList(annotation.aliases()));
            } else {
                for (String alias : annotation.aliases()) {
                    aliases.setProperty(alias, annotation.command());
                }
            }

            CodCommand meta = findMeta(annotation);
            if (meta == null || CODCOMMAND_COMPARATOR.compare(annotation, meta) < 0) {
                //This new (or first) meta has highest priority and should thus be the main one
                TreeSet treeSet;
                if (meta == null) {
                    treeSet = new TreeSet<>(METHOD_COMPARATOR);
                } else {
                    metas.remove(meta);
                    treeSet = methods.remove(meta);
                }
                meta = annotation;
                metas.add(meta);
                methods.put(meta, treeSet);
            }
            methods.get(meta).add(method);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!groupedCommands) {
            handleCommand(sender, findMeta(command.getName(), ""), args);
            return true;
        }
        //Command is part of a group of commands

        if (args.length == 0) {
            //No subcommand was added
            CodCommand meta = findMeta("&none", null);
            if (meta != null) {
                handleCommand(sender, meta, new String[0]);
            } else {
                displayHelpPage(sender);
            }
            return true;
        }

        String subcommand = aliases.containsKey(args[0])
                            ? aliases.getProperty(args[0])
                            : args[0];
        String arg1 = args.length > 1 ? args[1] : null;
        CodCommand meta = findMeta(subcommand, arg1);
        if (meta != null) {
            int index = meta.command().equals("&variable") ? 0 : 1;
            if (!meta.subcommand().isEmpty()) {
                index++;
            }
            handleCommand(sender, meta, Arrays.copyOfRange(args, index, args.length));
        } else if (subcommand.equals("help")) { //Default 'help' subcommand
            subcommand = arg1 != null && aliases.containsKey(arg1)
                         ? aliases.getProperty(arg1)
                         : arg1;
            switch (args.length) {
            case 2:
                meta = findMeta(subcommand, null);
                break;
            case 3:
                meta = findMeta(subcommand, args[2]);
                break;
            default:
                break;
            }
            if (meta != null) {
                displayUsage(sender, meta);
            } else {
                displayHelpPage(sender);
            }
        } else { //Invalid command
            sender.sendMessage("§6" + subcommand + "§4 is not a valid command");
            displayHelpPage(sender);
        }
        return true;
    }

    /**
     * Discovers the correct method to invoke for the given command
     *
     * @param sender The CommandSender who is executing the command
     * @param command The command which was sent
     * @param args The arguments which were sent with the command
     */
    private void handleCommand(CommandSender sender, CodCommand meta, String[] args) {
        try {
            //Check for permissions
            if (!meta.permission().isEmpty() && !sender.hasPermission(meta.permission())) {
                sender.sendMessage("§4You don't have permission to do that");
                return;
            }

            //Iterate through each method of the command to find one which has matching parameters
            for (Method method : methods.get(meta)) {
                Class[] requestedParameters = method.getParameterTypes();
                Object[] parameters = new Object[requestedParameters.length];
                int argumentCount = args.length;

                //Check the type of command sender
                if (requestedParameters[0] == Player.class && !(sender instanceof Player)) {
                    //Invalid CommandSender type
                    continue;
                }
                parameters[0] = sender;

                //Check if there is a variable parameter (String[])
                if (requestedParameters[requestedParameters.length - 1] == String[].class) {
                    argumentCount = requestedParameters.length - 2;
                    int variableParameterCount = args.length - argumentCount;

                    if (variableParameterCount < meta.minArgs()) {
                        //Too few commands
                        continue;
                    }

                    if (meta.maxArgs() != -1 && variableParameterCount > meta.maxArgs()) {
                        //Too many commands
                        continue;
                    }

                    String[] variableParameters = variableParameterCount == 0 ? new String[0] : Arrays.copyOfRange(args, argumentCount, args.length);
                    parameters[parameters.length - 1] = variableParameters;
                } else if (requestedParameters.length - 1 != argumentCount) {
                    //Invalid amount of commands
                    continue;
                }

                //Loop through each argument to see if they match the parameters
                boolean match = true;
                for (int i = 0; i < argumentCount; i++) {
                    Object o = validate(args[i], requestedParameters[i + 1]);
                    if (o == null) {
                        //Invalid parameter type
                        match = false;
                        break;
                    } else {
                        //Place the returned object in the array of parameters
                        parameters[i + 1] = o;
                    }
                }

                if (match) {
                    //The parameters fit, call the method
                    if (!(Boolean) method.invoke(method.getDeclaringClass().newInstance(), parameters)) {
                        //The method wishes the usage of the command to be displayed
                        displayUsage(sender, meta);
                    }
                    return;
                }
            }

            displayUsage(sender, meta);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            sender.sendMessage("§4An error occured while trying to perform this command. Please notify a server administator.");
            plugin.getLogger().log(Level.SEVERE, "Error occured when executing command " + getCommand(meta), ex);
        }
    }

    /**
     * Verifies that the argument is of the given class
     *
     * @param argument The argument that was given
     * @param parameter The Class that the argument should be
     * @return true if the argument correctly represents the given Class
     */
    private Object validate(String argument, Class parameter) {
        try {
            ParameterType type = ParameterType.getType(parameter);

            switch (type) {
            case STRING:
                return argument;
            case INT:
                return Integer.parseInt(argument);
            case DOUBLE:
                return Double.parseDouble(argument);
            case BOOLEAN:
                switch (argument) {
                case "true":
                case "on":
                case "yes":
                    return true;
                case "false":
                case "off":
                case "no":
                    return false;
                default:
                    return null;
                }
            case MATERIAL:
                return argument.matches("[0-9]+")
                       ? Material.getMaterial(Integer.parseInt(argument))
                       : Material.matchMaterial(argument);
            case PLAYER:
                return Bukkit.getPlayer(argument);
            case OFFLINEPLAYER:
                return Bukkit.getOfflinePlayer(argument);
            case WORLD:
                return Bukkit.getWorld(argument);
            case PHATLOOT:
                return PhatLoots.getPhatLoot(argument);
            default:
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Returns the meta of the given command
     *
     * @param command The command to retrieve the meta for
     * @param subcommand The subcommand if any
     * @return The CodCommand or null if none was found
     */
    private CodCommand findMeta(String command, String subcommand) {
        for (CodCommand meta : metas) {
            //Check if the commands match
            if (meta.command().equals(command)
                    || (meta.command().equals("&variable")
                        && !command.equals("&none")
                        && !command.equals("help"))) {
                //Check if the subcommands match
                if (meta.subcommand().isEmpty() || meta.subcommand().equals(subcommand)) {
                    return meta;
                }
            }
        }
        return null;
    }

    /**
     * Returns the meta of the given command
     *
     * @param command The command to retrieve the meta for
     * @param subcommand The subcommand if any
     * @return The CodCommand or null if none was found
     */
    private CodCommand findMeta(CodCommand annotation) {
        for (CodCommand meta : metas) {
            if (meta.command().equals(annotation.command())
                    && meta.subcommand().equals(annotation.subcommand())) {
                return meta;
            }
        }
        return null;
    }

    /**
     * Displays the help page for grouped commands
     *
     * @param sender The sender to display the help page to
     */
    private void displayHelpPage(CommandSender sender) {
        sender.sendMessage("§1Sub commands of §6/" + parentCommand + "§1:");
        sender.sendMessage("§2/" + parentCommand + " help §f<§6command§f> =§b Display the usage of a sub command");
        for (CodCommand meta : metas) {
            displayOneLiner(sender, meta);
        }
    }

    /**
     * Displays a one line usage of the given command
     *
     * @param sender The sender to display the command usage to
     * @param command The given CodCommand
     */
    private void displayOneLiner(CommandSender sender, CodCommand meta) {
        String cmd = getCommand(meta);
        if (meta.usage().length == 1) {
            sender.sendMessage(meta.usage()[0].replace("<command>", cmd));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("§2");
            sb.append(getCommand(meta));
            sb.append(" §f=§b '/");
            sb.append(parentCommand);
            sb.append(" help ");
            sb.append(meta.command());
            if (!meta.subcommand().isEmpty()) {
                sb.append(' ');
                sb.append(meta.subcommand());
            }
            sb.append("' for full usage.");
            sender.sendMessage(sb.toString());
        }
    }

    /**
     * Displays the usage of the given command
     *
     * @param sender The sender to display the command usage to
     * @param command The given CodCommand
     */
    private void displayUsage(CommandSender sender, CodCommand meta) {
        String cmd = getCommand(meta);
        for (String string : meta.usage()) {
            sender.sendMessage(string.replace("<command>", cmd));
        }
    }

    /**
     * Returns the correctly formatted command
     *
     * @param command The requested CodCommand
     * @return The command including '/' and any parent command
     */
    private String getCommand(CodCommand meta) {
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        if (groupedCommands) {
            sb.append(parentCommand);
        }
        if (!meta.command().startsWith("&")) {
            sb.append(' ');
            sb.append(meta.command());
        }
        if (!meta.subcommand().isEmpty()) {
            sb.append(' ');
            sb.append(meta.subcommand());
        }
        return sb.toString();
    }
}
