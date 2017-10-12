/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.lojaagricola;

import java.util.ArrayList;
import java.util.List;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Jobs;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author vntgasl
 *
 */
public class LojaAgricola implements Listener {

    int vendeu = 0;
    int maxVendeu = 1000; // qto precisa vender pra mudar

    public static ItemStack getPack(Material m) {
        if (m == Material.INK_SACK) {
            return new ItemStack(Material.INK_SACK, 20, (byte) 3);

        } else {
            return new ItemStack(m, 20);
        }

    }

    /////////// CONFIG /////////////
    public static final double multiplicadorEmAlta = 2.5;
    public static final double multiplicadorEmBaixa = 0.5;

    public void onEnable() {

        vendaveis.add(new Vendavel(Material.WHEAT, 3, "Trigo"));
        vendaveis.add(new Vendavel(Material.POTATO_ITEM, 2, "Batata"));
        vendaveis.add(new Vendavel(Material.CARROT_ITEM, 3, "Cenoura"));
        //vendaveis.add(new Vendavel(Material.APPLE, 1, "Maça"));
        vendaveis.add(new Vendavel(Material.INK_SACK, 3, "Cacau"));
        vendaveis.add(new Vendavel(Material.COOKED_FISH, 15, "Peixe Frito"));
        vendaveis.add(new Vendavel(Material.COOKED_CHICKEN, 25, "Galinha Frita"));
        vendaveis.add(new Vendavel(Material.COOKED_BEEF, 25, "Bife Frito"));
        vendaveis.add(new Vendavel(Material.PUMPKIN, 3, "Abobora"));
        vendaveis.add(new Vendavel(Material.MELON, 3, "Melao"));
        vendaveis.add(new Vendavel(Material.SUGAR, 3, "Açucar"));
        vendaveis.add(new Vendavel(Material.CACTUS, 1, "Cactus"));
        vendaveis.add(new Vendavel(Material.RED_MUSHROOM, 3, "Cogumelo Vermelho"));
        vendaveis.add(new Vendavel(Material.BROWN_MUSHROOM, 3, "Cogumelo Marrom"));
        vendaveis.add(new Vendavel(Material.NETHER_STALK, 2, "Fungo do Nether"));
        // Runnable timerMudaPrecos = new Runnable() {
        //     public void run() {
        //         LojaAgricola.alteraMercado();
        //     }
        // };
        //  int tempo = 20 * 60 * 30; // cada meia hora
        //int tempo = 20 * 30; // pra testar
        //  Bukkit.getScheduler().scheduleAsyncRepeatingTask(KnightsOfMania._instance, timerMudaPrecos, tempo, tempo);
        // vai começar com nada em alta, e 6 coisas em baixa
        alteraMercado();
        alteraMercado();

    }

    /////////// FAVOR IMPLEMENTAR ISSO //////////////
    public static void daDinheiro(Player p, int qto) {
        double dinheiro = Double.parseDouble(qto + "");
        //MoneyManager.AddMoney(p.getUniqueId(), dinheiro);
        ClanLand.econ.depositPlayer(p.getName(), qto);
    }

    public static List<Vendavel> vendaveis = new ArrayList<Vendavel>();

    private static Material emAlta = null;
    private static List<Material> emBaixa = new ArrayList<Material>();

    public static boolean ciclo = false;

    public static void alteraMercado() {
        // algo vai em alta
        if (ciclo) {
            List<Vendavel> lista = new ArrayList<Vendavel>(vendaveis);

            Vendavel t = lista.get(Jobs.rnd.nextInt(lista.size()));
            while (t.m == emAlta) {
                t = lista.get(Jobs.rnd.nextInt(lista.size()));
            }
            emAlta = t.m;
            for (Player p : Bukkit.getOnlinePlayers()) {
                //if(Jobs.getJobLevel("Fazendeiro", p)==1) {
                p.sendMessage(ChatColor.GREEN + "[MercadoAgricola] " + ChatColor.YELLOW + "O preco de " + t.nome + " subiu e esta em alta!");
                //}
            }

            if (emBaixa.contains(emAlta)) {
                emBaixa.remove(emAlta);
            }
        } else {

            String caiu = "";
            List<Vendavel> lista = new ArrayList<Vendavel>(vendaveis);

            String subiu = "";
            // removendo os items que ja estao em baixa
            // eles irao subir
            List<Vendavel> remover = new ArrayList<Vendavel>();
            for (Vendavel v : lista) {
                if (emBaixa.contains(v.m)) {
                    remover.add(v);
                    subiu += v.nome + " ";
                }
            }
            lista.removeAll(remover);

            // 6 items em baixa
            // nenhum q ja estava em baixa
            for (int x = 0; x < 7; x++) {
                Vendavel sort = lista.get(Jobs.rnd.nextInt(lista.size()));
                Material sortiado = sort.m;
                if (x >= lista.size() - 1) {
                    break;
                }
                if (emBaixa.contains(sortiado)) {
                    continue;
                }
                lista.remove(x);
                if (emAlta == sortiado) {
                    continue;
                } else {
                    emBaixa.add(sortiado);
                    caiu += sort.nome + " ";

                }
            }
        }
        ciclo = !ciclo;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory() != null) {
                if (p.getOpenInventory().getTitle().equalsIgnoreCase("Loja Agricola")) {
                    p.closeInventory();
                    abreMenu(p);
                }
            }
        }
    }

    public static int getPreco(Vendavel v) {
        int preco = ConfigLoja.getPreco(v.getNomeTecnico());
        return preco;
        /*
        if (emAlta == v.m) {
            return ConfigLoja.getQtdAlta(v.getNomeTecnico());//(int) (preco * LojaAgricola.multiplicadorEmAlta);
        } else if (emBaixa.contains(v.m)) {
            int p = ConfigLoja.qtdQtdBaixa(v.getNomeTecnico());//(int) (preco * LojaAgricola.multiplicadorEmBaixa);
            if (p <= 0) {
                p = 1;
            }
            return p;
        } else {
            return (int) preco;
        }
        */
    }

    ///////// CHAMAR ISSO DO /LOJA //////////
    public static void abreMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 18, "Loja Agricola");
        List<ItemStack> emalta = new ArrayList();
        List<ItemStack> normal = new ArrayList();
        List<ItemStack> baixa = new ArrayList();

        for (Vendavel v : vendaveis) {
            ItemStack ss = LojaAgricola.getPack(v.m);
            ItemMeta meta = ss.getItemMeta();
            int qtd = ConfigLoja.getQtdNormal(v.getNomeTecnico());
            if (emAlta == v.m) {
                qtd = ConfigLoja.getQtdAlta(v.getNomeTecnico());
            } else if (emBaixa.contains(v.m)) {
                qtd = ConfigLoja.qtdQtdBaixa(v.getNomeTecnico());
            }
            List<String> lore = new ArrayList<String>();   
            meta.setDisplayName("§e§l" + v.nome);
            if (emAlta == v.m) {
                lore.add("§7Vender §6"+qtd+" §7por " + ChatColor.YELLOW +"§l"+ getPreco(v) + " §7§lEsmeraldas");
                lore.add("§6Produto em §nALTA§6 $$$");
            } else if (emBaixa.contains(v.m)) {
                lore.add("§7Vender §c"+qtd+" §7por " + ChatColor.YELLOW +"§l"+ getPreco(v) + " §7§lEsmeraldas");
                lore.add("§cProduto em §nBAIXA§c $");
            } else {
                lore.add("§7Vender §9"+qtd+" §7por " + ChatColor.YELLOW +"§l"+ getPreco(v) + " §7§lEsmeraldas");
                lore.add("§9Produto §nSEM§9 modificação $$");  
            }
            meta.setLore(lore);
            ss.setItemMeta(meta);
            if (emAlta == v.m) {

                emalta.add(ss);
            } else if (emBaixa.contains(v.m)) {
                baixa.add(ss);
            } else {
                normal.add(ss);
            }
        }
        for (ItemStack is : emalta) {

            inv.addItem(is);
        }

        for (ItemStack is : normal) {

            inv.addItem(is);
        }

        for (ItemStack is : baixa) {

            inv.addItem(is);
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void click(InventoryClickEvent ev) {

        if (ev.getInventory().getName().equalsIgnoreCase("Loja Agricola") || ev.getInventory().getTitle().equalsIgnoreCase("Loja Agricola")) {
            if (!(ev.getWhoClicked() instanceof Player)) {
                return;
            }
            Player p = (Player) ev.getWhoClicked();
            ev.setCancelled(true);
            ItemStack clicado = ev.getCurrentItem();
            if (clicado != null) {
                Material m = clicado.getType();
                
                for (Vendavel v : vendaveis) {
                    if (v.m == m) {
                        
                        int qtd = ConfigLoja.getQtdNormal(v.getNomeTecnico());
                        if (emAlta == v.m) {
                            qtd = ConfigLoja.getQtdAlta(v.getNomeTecnico());
                        } else if (emBaixa.contains(v.m)) {
                            qtd = ConfigLoja.qtdQtdBaixa(v.getNomeTecnico());
                        }
                        
                        ItemStack ss = new ItemStack(m, qtd);
                        if (v.m == Material.INK_SACK) {
                            ss = new ItemStack(Material.INK_SACK, qtd, (byte) 3);
                        }
                        int preco = getPreco(v);

                        if (!KoM.inventoryContains(ev.getWhoClicked().getInventory(), ss)) {
                            p.sendMessage(ChatColor.RED + "Voce nao tem um "+qtd+" deste item para vender !");
                            return;
                        } else {
                            KoM.removeInventoryItems(p.getInventory(), ss.getType(), qtd);
                            //p.getInventory().remove(ss);
                        }
                        p.sendMessage(ChatColor.GREEN + "Voce vendeu "+qtd+" " + v.nome + " por " + preco + " moedas!");
                        vendeu += preco;

                        if (vendeu >= maxVendeu) {
                            vendeu = 0;
                            LojaAgricola.alteraMercado();
                            LojaAgricola.alteraMercado();
                        } else {
                            double pct = (vendeu * 100) / maxVendeu;
                            p.sendMessage(ChatColor.GREEN + "Estoque da Loja: " + ChatColor.GOLD + pct + "%" + ChatColor.GREEN + " Cheio");
                        }
                        daDinheiro(p, preco);
                    }
                }
            }
        }
    }
}
