package com.numbtus.wildsword.divers;

import org.bukkit.ChatColor;

import java.util.function.UnaryOperator;

public class Op implements UnaryOperator<String> {

    public String apply(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);


    }

}
