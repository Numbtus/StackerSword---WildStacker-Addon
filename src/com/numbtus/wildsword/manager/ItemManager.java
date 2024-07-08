package com.numbtus.wildsword.manager;

import com.numbtus.wildsword.WildSword;
import com.numbtus.wildsword.divers.Op;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemManager {

    private WildSword index;
    public ItemManager(WildSword main) {
        this.index = main;
    }

    public static ItemStack legendarySword;

    public static void init() {

        createLegendarySword();
    }


    public static void createLegendarySword() {
        boolean setUnbreakable = WildSword.getInstance().getConfig().getBoolean("setUnbreakable");
        boolean hideEnchants = WildSword.getInstance().getConfig().getBoolean("hideEnchants");
        int unbLevel = WildSword.getInstance().getConfig().getInt("unbreakingLevel");
        int lootLevel = WildSword.getInstance().getConfig().getInt("lootingLevel");
        int smiteLevel = WildSword.getInstance().getConfig().getInt("smiteLevel");
        List<String> GetConfigList = WildSword.getInstance().getConfig().getStringList("swordLore");
        String SwordName = ChatColor.translateAlternateColorCodes('&',WildSword.getInstance().getConfig().getString("swordName"));




        List<String> newList = new ArrayList<String>();
        newList.addAll(GetConfigList);
        newList.replaceAll(new Op());


        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);

        if (!setUnbreakable) {
            sword.addUnsafeEnchantment(Enchantment.DURABILITY, unbLevel);
        }
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, smiteLevel);
        sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, lootLevel);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(SwordName);

        swordMeta.spigot().setUnbreakable(setUnbreakable);
        swordMeta.setLore(newList);
        if(hideEnchants){
            swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        sword.setItemMeta(swordMeta);

        legendarySword = sword;
    }
}
