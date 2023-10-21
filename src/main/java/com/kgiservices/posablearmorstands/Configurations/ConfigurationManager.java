/*
 * PosableArmorStands
 * Copyright (C) 2023 Karl Grear
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kgiservices.posablearmorstands.Configurations;

import com.kgiservices.posablearmorstands.Enums.ConfigurationLookup;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import com.kgiservices.posablearmorstands.PosableArmorStands;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ConfigurationManager {
    private final PosableArmorStands  plugin;
    private final Hashtable<String, YamlConfiguration> languageConfigurations = new Hashtable<>();
    private static ConfigurationManager instance;

    public ConfigurationManager(PosableArmorStands plugin) {
        this.plugin = plugin;
        instance = this;
        loadPluginConfiguration();
        loadLanguageConfiguration();
    }

    private void loadPluginConfiguration() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }


    private void loadLanguageConfiguration() {
        languageConfigurations.clear();
        List<String> languages = new ArrayList<>() {
            {
                add("en_us");
            }
        };
        for (String language : languages) {
            File languageConfigurationFile = new File(plugin.getDataFolder(), language + "-language.yml");
            if (!languageConfigurationFile.exists()) {
                plugin.saveResource( language + "-language.yml", false);
            }
            languageConfigurationFile = new File(plugin.getDataFolder(), language + "-language.yml");
            YamlConfiguration languageConfiguration = YamlConfiguration.loadConfiguration(languageConfigurationFile);
            InputStream defaultConfigurationStream = plugin.getResource(language + "language.yml");
            if (defaultConfigurationStream != null) {
                languageConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigurationStream, StandardCharsets.UTF_8)));
            }
            languageConfigurations.put(language, languageConfiguration);
        }
    }

    public Object getConfigurationValue(ConfigurationLookup configurationLookup) {
        return plugin.getConfig().get(configurationLookup.path);
    }

    public String getConfigurationValue(ConfigurationLookup configurationLookup, Object... args) {
        return MessageFormat.format((String)plugin.getConfig().get(configurationLookup.path), args);
    }

    public String getLanguageValue (Player player, LanguageLookup languageLookup) {
        return getLanguageValue(player, languageLookup, new Object[]{});
    }

    public String getLanguageValue (Player player, LanguageLookup languageLookup, Object... args) {
        if (languageConfigurations.containsKey(player.getLocale())) {
            return languageConfigurations.get(player.getLocale().toLowerCase()).getString(languageLookup.path);
        } else {
            return languageConfigurations.get("en_us").getString(languageLookup.path);
        }
    }

    public void sendPlayerMessage(Player player, LanguageLookup languageLookup) {
       sendPlayerMessage(player, languageLookup, new Object[]{});
    }
    public void sendPlayerMessage(Player player, String message) {
        sendPlayerMessage(player, message, new Object[]{});
    }

    public void sendPlayerMessage(Player player, LanguageLookup languageLookup, Object... args) {
        String message = getLanguageValue(player, languageLookup);
        sendPlayerMessage(player, message, args);
    }

    public void sendPlayerMessage(Player player, String message, Object... args) {
        player.sendMessage(MessageFormat.format(ChatColor.translateAlternateColorCodes('&', message), args));
    }


    public static ConfigurationManager getInstance() {
        return instance;
    }


}
