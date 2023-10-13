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

package com.kgiservices.posablearmorstands.Commands;

import com.kgiservices.posablearmorstands.ArmorStandManagement.ArmorStandManager;
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Enums.Commands;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    public abstract Commands subCommandName();

    public abstract int numberOfParameters();

    public abstract boolean isValid(Player player, String args[]);

    public abstract boolean hasPermission(Player player);

    public abstract String description(Player player);

    public abstract String usage(Player player);

    public abstract List<String> getParameterOneList (Player player);

    public abstract void Execute(Player player, String args[]);

    public boolean isValidAngle(Player player, String[] args) {
        if (!ArmorStandManager.getInstance().isArmorStandSelected(player)) {
            ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Armor_Stand_Not_Selected);
        } else if (args.length == numberOfParameters()) {
            try {
                double degrees = Double.parseDouble(args[1]);
                if (degrees >= 1d && degrees <= 360d || degrees == -1d) {
                    return true;
                } else {
                    ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Angle_Parameter, this.subCommandName());
                    ConfigurationManager.getInstance().sendPlayerMessage(player, this.usage(player));
                }
            } catch (NumberFormatException e) {
                ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Angle_Parameter, this.subCommandName());
                ConfigurationManager.getInstance().sendPlayerMessage(player, this.usage(player));
            }
        }
        return false;
    }

    public boolean isValidBoolean(Player player, String[] args) {
        if (!ArmorStandManager.getInstance().isArmorStandSelected(player)) {
            ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Armor_Stand_Not_Selected);
        } else if (args.length == numberOfParameters()) {
            if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
                return true;
            } else {
                ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Boolean_Parameter, this.subCommandName());
                ConfigurationManager.getInstance().sendPlayerMessage(player, this.usage(player));
            }
        } else {
            ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Parameter_Count_One, this.subCommandName());
            ConfigurationManager.getInstance().sendPlayerMessage(player, this.usage(player));
        }
        return false;
    }
}