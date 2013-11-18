/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.arcanerealm.holidays.listeners;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jeppe
 */
public class SnowballListener implements Listener{
    private final String[] snowballLore = new String[]{
        ChatColor.GREEN+ChatColor.ITALIC.toString()+"Unlimited snowball throws",
        "",
        ChatColor.GREEN+ChatColor.ITALIC.toString()+"This snowball could be obtained by",
        ChatColor.GREEN+ChatColor.ITALIC.toString()+"players during the christmas event"
    };
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR ){
            Player player = event.getPlayer();
            ItemStack snowball = player.getItemInHand();
            if(snowball.getType() == Material.SNOW_BALL){
                if(player.getGameMode() != GameMode.CREATIVE){
                    ItemMeta snowballMeta = snowball.getItemMeta();
                    if(snowballMeta != null){
                        if(snowballMeta.hasDisplayName()){
                            if(snowballMeta.getDisplayName().endsWith(ChatColor.DARK_PURPLE+"Magic Snowball")){
                                if(snowballMeta.hasEnchant(Enchantment.ARROW_INFINITE)){
                                    if(snowballMeta.getEnchantLevel(Enchantment.ARROW_INFINITE) == 10){
                                        List<String> lore = snowballMeta.getLore();
                                        if(snowballMeta.getLore().size() == snowballLore.length){
                                            boolean launch = true;
                                            for(int i = 0; i < snowballLore.length; i++){
                                                if(!lore.get(i).endsWith(snowballLore[i])){
                                                    launch = false;
                                                    break;
                                                }
                                            }
                                            if(launch){
                                                event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
