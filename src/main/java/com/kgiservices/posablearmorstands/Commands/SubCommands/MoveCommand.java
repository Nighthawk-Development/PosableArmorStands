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

package com.kgiservices.posablearmorstands.Commands.SubCommands;

import com.kgiservices.posablearmorstands.ArmorStandManagement.ArmorStandManager;
import com.kgiservices.posablearmorstands.Commands.SubCommand;
import com.kgiservices.posablearmorstands.Enums.Commands;
import com.kgiservices.posablearmorstands.Enums.ConfigurationLookup;
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand extends SubCommand {
    @Override
    public Commands subCommandName() {
        return Commands.Move;
    }

    @Override
    public int numberOfParameters() {
        return 2;
    }

    @Override
    public boolean isValid(Player player, String[] args) {
        if (!ArmorStandManager.getInstance().isArmorStandSelected(player)) {
            ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Armor_Stand_Not_Selected);
        } else if (args.length == numberOfParameters()) {
            try {
                double distance = Double.parseDouble(args[1]);
                if (distance <= (double)ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Move_Limit) &&  distance > 0) {
                    return true;
                } else {
                    ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Distance_Parameter, this.subCommandName().commandText, ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Move_Limit));
                    ConfigurationManager.getInstance().sendPlayerMessage(player, this.usage(player));
                }
            } catch (NumberFormatException e) {
                ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Distance_Parameter, this.subCommandName().commandText, ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Move_Limit));
                ConfigurationManager.getInstance().sendPlayerMessage(player, this.usage(player));
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission("posablearmorstands.move");
    }

    @Override
    public String description(Player player) {
        return ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_Move_Description);
    }

    @Override
    public String usage(Player player) {
        return ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_Move_Usage, ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Move_Limit));
    }

    @Override
    public List<String> getParameterOneList(Player player) {
        List<String> optionList = new ArrayList<>();
        String parameter = ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Parameter_Two_Move_List, ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Move_Limit));
        optionList.add(parameter);
        return optionList;
    }

    @Override
    public void Execute(Player player, String[] args) {
        ArmorStandManager.getInstance().selectMotionPart(player, Commands.Move, Double.parseDouble(args[1]));
    }
}
