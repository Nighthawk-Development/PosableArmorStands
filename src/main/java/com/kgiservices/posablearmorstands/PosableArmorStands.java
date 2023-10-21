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

package com.kgiservices.posablearmorstands;

import com.kgiservices.posablearmorstands.ArmorStandManagement.ArmorStandManager;
import com.kgiservices.posablearmorstands.Commands.CommandManager;
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Listeners.EntityListener;
import com.kgiservices.posablearmorstands.Listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class PosableArmorStands extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("PosableArmorStands").setExecutor(new CommandManager(this));
        //getCommand("PosableArmorStands").setTabCompleter(new PosableArmorStandTabComplete(this));
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
        new ArmorStandManager(this);
        reloadConfiguration();
        this.getLogger().log(Level.INFO, this.getDescription().getVersion() + " finished loading.");
    }

    @Override
    public void onDisable() {
        ArmorStandManager.getInstance().unSelectAllArmorStands();
    }
    public void reloadConfiguration() {
        new ConfigurationManager(this);
    }
}
