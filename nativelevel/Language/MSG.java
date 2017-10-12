package nativelevel.Language;

import org.bukkit.ChatColor;

/**
 *
 * @author Nosliw
 */
public enum MSG {

    // GENERIC
    UNHEARD("* Your words fade into the wind *", "* ninguem perto para te ouvir *"),
    POINTS_REMAINING("You have [1] points to distribute, enter " + ChatColor.YELLOW + "/kom stats" + ChatColor.GREEN + "!", "Voce tem [1] pontos para distribuir , digite " + ChatColor.YELLOW + "/kom stats" + ChatColor.GREEN + " !"),
    CRAFT_FAIL("You failed to craft the item !", "Voce falhou ao criar o item !"),
    CRAFT_SUCCESS("You crafted the item !", "Voce criou o item !"),
    NOT_SAFEZONE("You cannot do that in a Safe Zone", "Voce nao pode fazer isto em uma Cidade"),
    CANT_DO_IT_HERE("You cannot do that here", "Voce nao pode fazer isto aqui !"),
    ITEM_SAFE_HERE("Your items will be safe here. :)", "Seus items estarao seguros aqui. Heh..."),
    ZONE_LEVEL("This zone is level [1].", "Zona nivel [1]"),
    WAIT("You must wait to do that", "Voce precisa esperar para fazer isto"),
    COLOR("Color","Cor"),
    TYPE("Type","Tipo"),
    
    // CLASS NAMES
    ALCHEMIST("Alchemist", "Alquimista"),
    BLACKSMITH("Blacksmith", "Ferreiro"),
    THIEF("Thief", "Ladino"),
    FARMER("Farmer", "Fazendeiro"),
    LUMBERJACK("Lumberjack", "Lenhador"),
    WIZARD("Wizard","Mago"),
    ENGINEER("Engineer", "Engenheiro"),
    PALADIN("Paladin", "Paladino"),
    MINER("Miner", "Minerador"),

    // ALCHEMIST.java
    ALCH_ONLY_ALCH("Only Alchemists know how to do this !", "Apenas alquimistas sabem fazer isto !"),
    ALCH_MOVE_REAGENT("You may only move the reagent after you moved the potion !", "Voce so pode remover o reagente depois de tirar a pocao !"),
    ALCH_POT_EXPLODE("You accidentaly exploded the potion !", "Voce acidentalmente explodiu a pocao !"),
    
    // BLACKSMITH.java
    BS_ANVIL_USE("You used the anvil to increase your crafting chance!", "Voce usou a bigorna para aumentar suas chances de craft !"),
    BS_SAVE_RESOURCES("You saved some resources as your a good blacksmith !", "Voce economizou alguns recursos por ser um bom ferreiro !"),
    BS_FAIL_BUT_SAVE("You failed to craft the item but managed to save some resources !","Voce falhou ao forjar o item mas salvou alguns recursos !"),
    BS_CRUSH_BLOW("You gave a crushing blow !","Voce deu um golpe esmagador !"),
    BS_SMELT_INGOT("You smelted [1] ingots !","Voce forjou [1] barras !"),
    BS_SMELT_FAIL("You failed smelting the ingots !","Voce falhou ao forjar as barras"),
    
    // ENGINEER.java
    ENG_ONLY_AT_MOSSY("You may only place redstone on mossy cobblestone !", "Voce apenas pode colocar redstone em pedras de musgo !"),
    ENG_ONLY_ENG("Only engineers know how to handle this", "Apenas engenheiros sabem usar isto"),
    ENG_FAIL_PLACE("You failed installing the equipent breaking it", "Voce falhou ao colocar o equipamento quebrando ele"),
    ENG_NEED_IRON_INGOT("You need [1] iron bars to place the piston !", "Voce precisa de [1] barras de ferro para prender o pistao !"),
    ENG_FAIL_BREAK("You failed, breaking the equipment", "Voce falhou quebrando o equipamento"),
    ENG_EXPLODE_EQUIP("You acidentally exploded the equipment", "Voce acidentalmente explodiu o equipamento"),
    
    // FARMER.java
    FARM_FAIL_TAME("You failed to tame the animal !", "Voce falhou em domesticar o animal !"),
    FARM_NO_PIG_MOUNT("You dont know how to mount pigs", "Voce nao sabe montar em porcos"),
    FARM_ANIMAL_OWNED("This animal already has a owner", "Este animal ja tem um dono"),
    FARM_SLIMEBALL_POISONED("You have been poisoned by a poisonous slimeball","Voce foi envenenado por uma slimeball envenenada"),
    FARM_EXEPT_LEATHER("You made an exceptional leather armor !", "Voce fez uma armadura de couro excepcional !"),
    FARM_SHOP_IN_HOUSE("You can only place a vendor at your shop","Voce so pode criar um lojista na sua casa"),
    FARM_FISH_PULL("You pulled the fishin hook", "Voce puxou a linha da vara"),
    FARM_FISH_THROW("You throw the fishing hook", "Voce arremeca o gancho da vara de pescar"),
    FARM_ITEM_IS_NEW("This item is just like new !","Este item esta como novo !"),
    FARM_FIX_LEATHER("You fixed the leather armor !", "Voce arrumou a armadura !"),
    FARM_NEED_LEATHER("You need leather to fix the armor", "Voce precisa de couro para arrumar a armadura !"),
    FARM_PLANT_FAIL("You missplaced the seeds failing to plant","Voce colocou as sementes de forma errada e falhou ao plantar"),
    FARM_HARVEST_FAIL("You failed to harvest the plant correctly", "Voce falhou ao colher a planta corretamente"),
    FARM_HIT_WITH_SISSORS("Hit the animal with a sissors to get extra resources","Bata no animal com uma tesoura para obter recursos extras"),
    FARM_ANIMAL_EXTRA_RESOURCES("You got extra resources from the animal", "Voce obteve recursos extras do animal"),
    FARM_FOUND_WOLF("You found an wolf egg !","Voce encontrou um ovo de lobo !"),
    FARM_TAME_FIRST("You must tame the animal first", "Voce precisa domesticar o animal primeiro"),
    FARM_NEED_SADLE("The horse must have a saddle", "Coloque uma cela no cavalo primeiro "),
    FARM_NEED_LEASH("The horse must have a leash", "Coloque uma corda no animal primeiro ! "),
    FARM_FAIL_EGGPLACE("You failed to put the animal inside the egg","Voce falhou ao colocar o animal no ovo"),
    FARM_EGGPLACED("You placed the animal inside an egg","Voce colocou o animal em um ovo"),
    FARM_HORSE_RACE("Race","Raça"),
    
    // Lumberjack.java
    HOLD_AXE("You hold the axe strongly and place it on your side upwards","Voce segurou firmemente o machado e o colocou de lado"),
    MISS_AXE("The axe has came down, you missed the timing", "O machado ja se abaixou, voce passou do ponto"),
    READY_AXE("You start to swing the axe with strenght, the SkullSplitter is ready !","Voce coloca força no machado e joga seu corpo de lado ! A machada epica esta pronta !"),
    RISE_AXE("You hold up your axe preparing for a SkullSplitter", "Voce levanta o machado e se prepara para a machadada epica"),
    
    // Entities
    FATHER_SCAMMER("Father Scammer", "Padre Baloeiro"),
    
    // Items
    SCROLL_RHODES("Teleport to Rhodes", "Pergaminho para Rhodes"),
    
    // Death
    DEATH_LOSE("You died and lost [1] levels.", "Você morreu e perdeu [1] niveis."),
    DEATH_REMAINING("You have [1] soul points remaining.", "Ficando com [1]"),
    DEATH_INFO_1("You do not lose any levels when you die, you instead lose a soul point.", "Voce nao perdeu nenhum level ao morrer, mas perdeu 1 ponto de alma !"),
    DEATH_INFO_2("You regenerate 1 soul point for every hour of online time!", "Recupere pontos de alma a cada 1 hora estando online !"),
    DEATH_INFO_3("You have ran out of soul points, and will now lose levels on death!", "Você ficou sem pontos de alma, você não vai mais manter o seu nível de"),
    
    // Technical
    INFO_CLEANUP("Cleaning up dropped items in [1] seconds!", "Irei fazer uma faxina nos items em [1] segundos !"),
    INFO_CLEANUP_NOW("Cleaning up dropped items now!", "Derrotei entidades malignas !"),
    
    LANGUAGE("Your currently language is: " + ChatColor.YELLOW + "[1]", "O seu idioma atual é: " + ChatColor.YELLOW + "[1]"),
    LANGUAGE_CHANGE("Type " + ChatColor.YELLOW + "/kom language" + ChatColor.LIGHT_PURPLE + " to change it!", "Type " + ChatColor.YELLOW + "/kom linguagem" + ChatColor.LIGHT_PURPLE + " para mudá-lo!"),
    WELCOME("Attention: " + ChatColor.GREEN + "New System Attributes!", ChatColor.GREEN + "Novo sistema de atributos !"),
    SERVER_MAINTAINANCE("Server Maintainance!", "Servidor em manutenção !"),
    REQUIRE_TUTORIAL("You can only leave here when you complete the tutorial!", "Você só pode sair daqui quando terminar o tutorial !"),
    //
    TELEPORT_WOLVES("You also teleported [1] wolves with you.", "Voce levou [1] lobos com voce !"),
    TELEPORT_AWAY_END("You cannot enter The End, and were teleported away!", "Voce nao consegue entrar no vazio final e foi teleportado !"),
    TELEPORT_AWAY_ADVENTURE("You left the game in the middle of an adventure, and were teleported away!", "Voce havia saído no meio de um local de aventura e foi teleportado !"),
    TELEPORT_TUTORIAL("You have been teleported to the tutorial!", "Voce foi teleportado para o tutorial !"),
    TELEPORT_OLD("This teleporter is old and falling appart..", "Este teleporter...está...velho...");
    //
    
    private final String EN, PT;

    private MSG(String EN, String PT) {
        this.EN = EN;
        this.PT = PT;
    }
    
    public String get(LNG language) {
        return language == LNG.EN ? EN : PT;
    }
}
