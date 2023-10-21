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
import com.kgiservices.posablearmorstands.Commands.CommandManager;
import com.kgiservices.posablearmorstands.Commands.SubCommand;
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Enums.Commands;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import com.kgiservices.posablearmorstands.PosableArmorStands;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DestroyCommand extends SubCommand {
    public DestroyCommand(PosableArmorStands plugin, CommandManager commandManager) {
        super(plugin, commandManager);
    }

    @Override
    public Commands subCommandName() {
        return Commands.Destroy;
    }

    @Override
    public int numberOfParameters() {
        return 1;
    }

    @Override
    public boolean isValid(Player player, String[] args) {
        return isValidSelectedZeroParameters(player, args);
    }

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission("posablearmorstands.create");
    }

    @Override
    public String description(Player player) {
        return  ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_Destroy_Description);
    }

    @Override
    public String usage(Player player) {
        return ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_Destroy_Usage);
    }

    @Override
    public List<String> getParameterOneList(Player player) {
        return new ArrayList<>();
    }

    @Override
    public void SendCommandHelp(Player player) {
        sendHelp(player, this.usage(player), this.description(player));
    }
    @Override
    public void Execute(Player player, String[] args) {
        ArmorStandManager.getInstance().destroySelectArmorStand(player);
    }
}
