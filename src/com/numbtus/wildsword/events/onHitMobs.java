package com.numbtus.wildsword.events;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedEntity;
import com.numbtus.wildsword.WildSword;
import com.numbtus.wildsword.manager.ItemManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class onHitMobs implements Listener {
    private WildSword index;

    public onHitMobs(WildSword main) {
        this.index = main;
    }


    @EventHandler
    public void onHitMobs(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Entity dead = event.getEntity();
            if (player.getItemInHand() != null) {
                if (player.getItemInHand().getItemMeta().equals(ItemManager.legendarySword.getItemMeta())) {
                    if (!(event.getEntity() instanceof Player)) {


                        int stackAmount = WildStackerAPI.getEntityAmount((LivingEntity) dead);
                        int mobKilledPerHit = WildSword.getInstance().getConfig().getInt("mobKillPerHit");
                        if (stackAmount > mobKilledPerHit) {
                            int killedAmount = mobKilledPerHit - 1;
                            WildStackerAPI.getStackedEntity((LivingEntity) dead).setStackAmount(stackAmount - killedAmount, true);
                            StackedEntity stackE = WildStackerAPI.getStackedEntity((LivingEntity) dead);
                            //drop correctly xp
                            int mobXp = WildStackerAPI.getLootTable((LivingEntity) dead).getExp(stackE, 9);
                            player.giveExp(mobXp);

                            // drop correctfly mob drops
                            for (ItemStack drops : WildStackerAPI.getLootTable((LivingEntity) dead).getDrops(stackE, player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS), 9)) {
                                dead.getLocation().getWorld().dropItem(dead.getLocation(), drops);
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

}
