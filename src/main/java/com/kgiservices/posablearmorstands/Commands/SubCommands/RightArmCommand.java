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
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Enums.Commands;
import com.kgiservices.posablearmorstands.Enums.ConfigurationLookup;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import org.bukkit.entity.Player;

import java.util.List;

public class RightArmCommand extends SubCommand {
    @Override
    public Commands subCommandName() {
        return Commands.Right_Arm;
    }

    @Override
    public int numberOfParameters() {
        return 2;
    }

    @Override
    public boolean isValid(Player player, String[] args) {
        return isValidAngle(player, args);
    }

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission("posablearmorstands.pose");
    }

    @Override
    public String description(Player player) {
        return ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_RightArm_Description);
    }

    @Override
    public String usage(Player player) {
        return ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_RightArm_Usage);
    }

    @Override
    public List<String> getParameterOneList(Player player) {
        return (List<String>)ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Parameter_Two_Degree_List);
    }

    @Override
    public void Execute(Player player, String[] args) {
        ArmorStandManager.getInstance().selectMotionPart(player, Commands.Right_Arm, Double.parseDouble(args[1]));
    }
}
