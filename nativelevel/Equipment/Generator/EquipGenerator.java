package nativelevel.Equipment.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipMeta;
import nativelevel.Equipment.EquipmentEvents;
import nativelevel.Equipment.ItemAttributes;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.Equipment.WeaponType.MatArma;
import nativelevel.Equipment.WeaponType.MatArmadura;
import nativelevel.Equipment.WeaponType.TipoArma;
import nativelevel.Equipment.WeaponType.TipoArmadura;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.LangMinecraft;
import nativelevel.gemas.Raridade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Ziden
 *
 */
public class EquipGenerator {

    public static ItemStack gera(int nivel) {
        if (nivel < 1) {
            nivel = 1;
        }
        Raridade rar = Raridade.Comum;
        int sorte = Jobs.rnd.nextInt(nivel * 2);
        if (sorte >= 199 && Jobs.rnd.nextBoolean()) {
            rar = Raridade.Lendario;
        } else if (sorte > 190) {
            rar = Raridade.Epico;
        } else if (sorte > 125) {
            rar = Raridade.Raro;
        } else if (sorte > 20) {
            rar = Raridade.Incomum;
        }
        return gera(rar, nivel);
    }

    public static ItemStack gera(Raridade r, int nivel) {

        int desorte = 50 - (nivel);
        int sorte = Jobs.rnd.nextInt(100 - desorte);

        if (r == Raridade.Comum) {
            sorte -= 30;
        } else if (r == Raridade.Incomum) {
            sorte -= 15;
        } else if (r == Raridade.Epico || r == Raridade.Lendario) {
            if (sorte < 35) {
                sorte = 35;
            }
            if (r == Raridade.Lendario) {
                sorte += 20;
            }
        }
        if (sorte < 0) {
            sorte = 0;
        }

        boolean ehArma = Jobs.rnd.nextBoolean();
        ItemStack item = null;
        if (ehArma) {
            if (Jobs.rnd.nextInt(15) == 1) {
                item = new ItemStack(Material.BOW, 1);
            } else if(Jobs.rnd.nextInt(15) == 1) {
                return geraOffHand(r, nivel);
            } else {
                MatArma material;
                if (sorte >= 145) {
                    material = MatArma.DIAMOND;
                } else if (sorte >= 90) {
                    material = MatArma.IRON;
                } else if (sorte >= 35) {
                    material = MatArma.GOLD;
                } else {
                    material = MatArma.STONE;
                }
                TipoArma tipo = TipoArma.values()[Jobs.rnd.nextInt(TipoArma.values().length)];
                Material arma = Material.valueOf(material.name() + "_" + tipo.name());
                item = new ItemStack(arma, 1);
            }
        } else {
            MatArmadura material;
            if (sorte >= 145) {
                material = MatArmadura.DIAMOND;
            } else if (sorte >= 90) {
                material = MatArmadura.IRON;
            } else if (sorte >= 35) {
                material = MatArmadura.GOLD;
            } else if (sorte > 15) {
                material = MatArmadura.CHAINMAIL;
            } else {
                material = MatArmadura.LEATHER;
            }
            TipoArmadura tipo = TipoArmadura.values()[Jobs.rnd.nextInt(TipoArmadura.values().length)];
            Material arma = Material.valueOf(material.name() + "_" + tipo.name());
            item = new ItemStack(arma, 1);
        }
        item = WeaponDamage.checkForMods(item);
        gera(item, r);
        return item;
    }

    public static ItemStack geraOffHand(Raridade rar, int nivel) {
        Material gerado = Material.SHIELD;
        int rnd = 3;

        if (Jobs.rnd.nextInt(3 + (-(nivel / 10) + 10)) == 1) {
            gerado = EquipmentEvents.offHands.get(Jobs.rnd.nextInt(EquipmentEvents.offHands.size()));
        }
        ItemStack ss = new ItemStack(gerado);
        ss = WeaponDamage.checkForMods(ss);
        gera(ss, rar);
        int bonus = nivel / 15;
        if (rar == Raridade.Incomum || rar == Raridade.Comum) {
            bonus /= 2.5;
        } else if (rar == Raridade.Raro) {
            bonus /= 2;
        } else if (rar == Raridade.Lendario) {
            bonus *= 1.5d;
        }
        if (ss.getType() == Material.SHIELD) {
            ItemAttributes.addAttribute(ss, Atributo.Armadura, bonus);
        } else if (ss.getType() == Material.STICK) {
            ItemAttributes.addAttribute(ss, Atributo.Regen_Mana, bonus * 4);
        } else if (ss.getType() == Material.WOOD_SWORD) {
            ItemAttributes.addAttribute(ss, Atributo.Dano_Fisico, bonus);
        }
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = new ArrayList<String>(meta.getLore());
        lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Mão Esquerda");
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }

    public static void gera(ItemStack ss, Raridade r) {

        int props = 1;
        double power = 8;
        if (r == Raridade.Incomum) {
            power = 20;
            props = 2;
        } else if (r == Raridade.Raro) {
            props = 3;
            power = 40;
        } else if (r == Raridade.Epico) {
            props = 4;
            power = 50;
        } else if (r == Raridade.Lendario) {
            props = 5;
            power = 70;
        }

        KoM.debug("Gerando item");

        String nomeItem = LangMinecraft.get().get(ss).split(" ")[0];

        if (ss.getType() == Material.STICK) {
            nomeItem = "Varinha";
        } else if (ss.getType() == Material.WOOD_SWORD) {
            nomeItem = "Adaga";
        } else if (ss.getType() == Material.WOOD_AXE) {
            nomeItem = "Machadinha";
        }

        String ultimaLetra = nomeItem.substring(nomeItem.length() - 1, nomeItem.length());

        boolean masculino = false;

        if (ultimaLetra.equalsIgnoreCase("o") || ultimaLetra.equalsIgnoreCase("r") || ultimaLetra.equalsIgnoreCase("l")) {
            masculino = true;
        }

        for (int x = 0; x < props; x++) {

            Atributo sorteado = sorteia(ss);

            double max = sorteado.max;
            double min = sorteado.min;

            double pct = power / 100d; // LOL // 20/100 = 0.2

            KoM.debug("Adicionando " + sorteado.name() + " power " + power + " max " + max + " min " + min + " pct " + pct);

            double diferenca = max - min; // 20 - 1 = 19

            double ptos = (diferenca * pct) + min; // 19 * 0.2 = 3.8

            double variacao = Jobs.rnd.nextInt((int) Math.ceil(ptos / 3d)) - (int) Math.ceil(ptos / 3d) / 2d;
            double qtd = ptos + variacao;
            if (qtd <= 0) {
                qtd = 0;
            }
            KoM.debug("Adicionando " + qtd + " " + sorteado.name());
            ItemAttributes.addAttribute(ss, sorteado, (int) qtd);

        }
        EquipMeta meta = ItemAttributes.getAttributes(ss);
        Atributo[] doisMaiores = doisMaiores(ss);

        if (doisMaiores[0] != null) {

            
            double max = doisMaiores[0].max;

            double valor = meta.getAttribute(doisMaiores[0]);
            
            double pct = (100 * valor) / max;
            
            KoM.debug("PCT1 = "+pct);
            
            String prefixo = Nomes.getSufixo(doisMaiores[0], pct);

            nomeItem = nomeItem + " " + prefixo;
        }
        if (doisMaiores[1] != null) {

            double max = doisMaiores[1].max;

            double valor = meta.getAttribute(doisMaiores[1]);
            
            double pct = (100 * valor) / max;

            KoM.debug("PCT2 = "+pct);
            
            String sufixo = Nomes.getPrefixo(doisMaiores[1], pct);
            if (!masculino) {
                sufixo = Nomes.f(sufixo);
            }
            nomeItem = sufixo + " " + nomeItem;
        }

        nomeItem = r.getIcone() + ChatColor.WHITE + " " + nomeItem;
        ItemMeta itemMeta = ss.getItemMeta();
        itemMeta.setDisplayName(nomeItem);
        ss.setItemMeta(itemMeta);
    }

    public static Atributo[] doisMaiores(ItemStack ss) {
        EquipMeta equip = ItemAttributes.getAttributes(ss);
        Set<Atributo> attrs = equip.getAtributos();
        Atributo[] resp = new Atributo[2];
        if (attrs.size() == 0) {
            return resp;
        }
        Atributo maior = null;
        double valorMaior = -1;
        for (Atributo a : attrs) {
            double valor = equip.getAttribute(a);
            if (valor > valorMaior) {
                valorMaior = valor;
                maior = a;
            }
        }
        resp[0] = maior;
        maior = null;
        valorMaior = 0;
        if (attrs.size() > 1) {
            for (Atributo a : attrs) {
                double valor = equip.getAttribute(a);
                if (valor > valorMaior && resp[0] != a) {
                    valorMaior = valor;
                    maior = a;
                }
            }
        }
        resp[1] = maior;
        return resp;

    }

    public static void main(String[] args) {

        String s = "Arco";
        String ultimaLetra = s.substring(s.length() - 1, s.length());
        boolean masculino = false;

        System.out.println(ultimaLetra);
        if (ultimaLetra.equalsIgnoreCase("o") || ultimaLetra.equalsIgnoreCase("r")) {
            masculino = true;
        }
        System.out.println(masculino);
    }

    public static int getValor(Atributo a, int power) {
        if (a.pct) {
            return power;
        } else {
            return (int) Math.ceil((double) power / 5);
        }
    }

    public static Atributo sorteia(ItemStack ss) {

        List<Atributo> sorteaveis = new ArrayList<Atributo>();

        for (Atributo a : Atributo.values()) {

            // só arco e armaduras tem dano a distancia
            if (a == Atributo.Dano_Distancia) {
                if (ss.getType() == Material.BOW || EquipmentEvents.isArmor(ss)) {
                    sorteaveis.add(a);
                }
                // só armadura tem vida
            } else if (a == Atributo.Vida) {
                if (EquipmentEvents.isArmor(ss) || ss.getType() == Material.SHIELD) {
                    sorteaveis.add(a);
                }
                // só couro e armas tem chance esquiva
            } else if (a == Atributo.Mana || a == Atributo.Magia) {
                if (EquipmentEvents.isArmor(ss) && (ss.getType().name().contains("GOLD") || ss.getType().name().contains("LEATHER")) || ss.getType() == Material.STICK) {
                    sorteaveis.add(a);
                }
            } else if (a == Atributo.Stamina || a == Atributo.Regen_Stamina) {
                if (EquipmentEvents.isArmor(ss) && (ss.getType().name().contains("CHAIN") || ss.getType().name().contains("IRON")) || ss.getType() == Material.STICK) {
                    sorteaveis.add(a);
                }
            } else if (a == Atributo.Armadura) {
                if (EquipmentEvents.isArmor(ss) || ss.getType() == Material.SHIELD) {
                    sorteaveis.add(a);
                }
                // só couro e armas tem chance esquiva
            } else if (a == Atributo.Chance_Esquiva) {
                if (EquipmentEvents.isWeapon(ss) || ss.getType().name().contains("LEATHER") || ss.getType().name().contains("CHAIN") || ss.getType() == Material.SHIELD) {
                    sorteaveis.add(a);
                }
                // espadas nao tem critico
            } else if (a == Atributo.Chance_Critico || a == Atributo.Dano_Critico) {
                if (!ss.getType().name().contains("SWORD") && ss.getType() != Material.SHIELD) {
                    sorteaveis.add(a);
                }
                // pás não tem bonus de dano
            } else if (a == Atributo.Dano_Fisico) {
                if (!ss.getType().name().contains("SPADE") && ss.getType() != Material.SHIELD) {
                    sorteaveis.add(a);
                }
            } else if (a == Atributo.Regen_Mana) {
                if (ss.getType().name().contains("LEATHER") || ss.getType() == Material.STICK) {
                    sorteaveis.add(a);
                }
            } else {
                sorteaveis.add(a);
            }
        }

        Atributo sorteado = sorteaveis.get(Jobs.rnd.nextInt(sorteaveis.size()));
        return sorteado;
    }

}
