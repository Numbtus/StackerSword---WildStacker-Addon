package com.numbtus.wildsword;

import com.numbtus.wildsword.commands.GiveCommands;
import com.numbtus.wildsword.events.onHitMobs;
import com.numbtus.wildsword.manager.ItemManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class WildSword extends JavaPlugin {

    private static WildSword instance;
    FileConfiguration config;
    File cfile;
    private File langFile = null;
    private FileConfiguration lang = null;
    @Override
    public void onEnable() {
    instance = this;
    config = getConfig();
        saveDefaultConfig();
        createLang();
        cfile = new File(getDataFolder(), "config.yml");
        getServer().getPluginManager().registerEvents(new onHitMobs(this), this);
        getCommand("stackersword").setExecutor(new GiveCommands(this));

        System.out.println("[StackerSword] Plugin started !");
        int configVersion = config.getInt("config-version");
        if (configVersion != 1) {
            System.out.println("------------------------------");
            System.out.println("| WARNING - WARNING - WARING |");
            System.out.println("| Bad Version of the config  |");
            System.out.println("| WARNING - WARNING - WARING |");
            System.out.println("------------------------------");
        }
        ItemManager.init();

    }
    public FileConfiguration getLang() {
        return this.lang;
    }

    private void createLang() {
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            saveResource("lang.yml", false);
        }

        lang = new YamlConfiguration();
        try {
            lang.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public void reloadLang() throws UnsupportedEncodingException {
        if (langFile == null) {
            langFile = new File(getDataFolder(), "lang.yml");
        }
        lang = YamlConfiguration.loadConfiguration(langFile);

        // Look for defaults in the jar
        Reader defConfigStream = new InputStreamReader(this.getResource("lang.yml"), "UTF8");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            lang.setDefaults(defConfig);
        }
    }

    @Override
    public void onDisable() {

        System.out.println("[StackerSword] Plugin unloaded !");
    }


    public static WildSword getInstance() {
        return instance;
    }



}
