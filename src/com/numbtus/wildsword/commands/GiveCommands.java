package com.numbtus.wildsword.commands;

import com.numbtus.wildsword.WildSword;
import com.numbtus.wildsword.divers.Op;
import com.numbtus.wildsword.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GiveCommands implements CommandExecutor {
    private WildSword index;

    public GiveCommands(WildSword main) {
        this.index = main;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {



        String prefix = ChatColor.translateAlternateColorCodes('&',index.getConfig().getString("prefix"));

        String helpMessage1 = ChatColor.translateAlternateColorCodes('&', "&8&l---------------------------------------------");
        String helpMessage2 = ChatColor.translateAlternateColorCodes('&', "&6&l/stackersword &e&lgive &a&lplayer &7- &lGive sword to player");
        String helpMessage3 = ChatColor.translateAlternateColorCodes('&', "&6&l/stackersword &e&lreload &7- &lReload the config.yml and lang.yml files");
        String helpMessage4 = ChatColor.translateAlternateColorCodes('&', "&6&l/stackersword &e&lhelp &7- &lView this message");




            if (args.length >= 1) {

                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(helpMessage1);
                    sender.sendMessage(helpMessage2);
                    sender.sendMessage(helpMessage3);
                    sender.sendMessage(helpMessage4);
                    sender.sendMessage(helpMessage1);

                } else if (args[0].equalsIgnoreCase("give")) {
                    if (!sender.hasPermission("stackersword.give")){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("noPermission")));
                    }else {
                        if (args.length >= 2) {
                            Player TargetPlayer = Bukkit.getPlayerExact(args[1]);
                            if (TargetPlayer == null) {

                                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', index.getLang().getString("invalidPlayer")));

                            } else {
                                TargetPlayer.getInventory().addItem(ItemManager.legendarySword);
                                String message = prefix + ChatColor.translateAlternateColorCodes('&', index.getLang().getString("successfullyGiveMessage"));
                                String newMessage = message.replace("%playerSend%", TargetPlayer.getDisplayName());
                                sender.sendMessage(newMessage);
                                TargetPlayer.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', index.getLang().getString("receiveSwordMessage")));
                            }

                        } else {
                            sender.sendMessage(helpMessage1);
                            sender.sendMessage(helpMessage2);
                            sender.sendMessage(helpMessage3);
                            sender.sendMessage(helpMessage4);
                            sender.sendMessage(helpMessage1);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (!sender.hasPermission("stackersword.reload")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("noPermission")));
                    }else {
                    index.reloadConfig();
                    index.saveDefaultConfig();
                    try {
                        index.reloadLang();
                    } catch (UnsupportedEncodingException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("langFileError")));
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', index.getLang().getString("successfullyReload")));
                }
                } else {
                    sender.sendMessage(helpMessage1);
                    sender.sendMessage(helpMessage2);
                    sender.sendMessage(helpMessage3);
                    sender.sendMessage(helpMessage4);
                    sender.sendMessage(helpMessage1);
                }
            }else {
                sender.sendMessage(helpMessage1);
                sender.sendMessage(helpMessage2);
                sender.sendMessage(helpMessage3);
                sender.sendMessage(helpMessage4);
                sender.sendMessage(helpMessage1);
            }





        return false;
        }



}
