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

import com.kgiservices.posablearmorstands.Commands.SubCommand;
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Enums.Commands;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends SubCommand {
    @Override
    public Commands subCommandName() {
        return Commands.Help;
    }

    @Override
    public int numberOfParameters() {
        return 1;
    }

    @Override
    public boolean isValid(Player player, String[] args) {
        if (args.length >= numberOfParameters()) {

            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission("posablearmorstands.use");
    }

    @Override
    public String description(Player player) {
        return ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_Help_Description);
    }

    @Override
    public String usage(Player player) {
        return ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.Commands_Help_Usage);
    }

    @Override
    public List<String> getParameterOneList(Player player) {
        List<String> commandList = new ArrayList<>();
        for (Commands command : Commands.values()) {
            commandList.add(command.commandText);
        }
        return commandList;
    }

    @Override
    public void Execute(Player player, String[] args) {
        String keyFix = "";
        if (args.length == 1) {
            for (Commands command : Commands.values()) {
                keyFix = getKeyFix(command.toString());
                sendHelp(player,
                        MessageFormat.format("Commands_{0}_Usage", keyFix),
                        MessageFormat.format("Commands_{0}_Description", keyFix));
            }
        } else if (args.length == 2) {
            keyFix = getKeyFix(args[1]);
            sendHelp(player,
                    MessageFormat.format("Commands_{0}_Usage", keyFix),
                    MessageFormat.format("Commands_{0}_Description", keyFix));
        }
    }

    private void sendHelp(Player player, String usageKey, String descriptionKey) {
        String usage = ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.valueOf(usageKey));
        String description = " &b> " + ConfigurationManager.getInstance().getLanguageValue(player, LanguageLookup.valueOf(descriptionKey));
        ConfigurationManager.getInstance().sendPlayerMessage(player, usage);
        ConfigurationManager.getInstance().sendPlayerMessage(player, description);
    }

    private String getKeyFix(String key) {
        if (key.equalsIgnoreCase("create")) key = "Create";
        else if (key.equalsIgnoreCase("destroy")) key = "Destroy";
        else if (key.equalsIgnoreCase("select")) key = "Select";
        else if (key.equalsIgnoreCase("unselect")) key = "UnSelect";
        else if (key.equalsIgnoreCase("visible")) key = "Visible";
        else if (key.equalsIgnoreCase("showbase")) key = "ShowBase";
        else if (key.equalsIgnoreCase("gravity")) key = "Gravity";
        else if (key.equalsIgnoreCase("showarms")) key = "ShowArms";
        else if (key.equalsIgnoreCase("torso")) key = "Torso";
        else if (key.equalsIgnoreCase("leftarm")) key = "LeftArm";
        else if (key.equalsIgnoreCase("rightarm")) key = "RightArm";
        else if (key.equalsIgnoreCase("leftleg")) key = "LeftLeg";
        else if (key.equalsIgnoreCase("rightleg")) key = "RightLeg";
        else if (key.equalsIgnoreCase("move")) key = "Move";
        else if (key.equalsIgnoreCase("showname")) key = "ShowName";
        else if (key.equalsIgnoreCase("small")) key = "Small";
        else if (key.equalsIgnoreCase("summon")) key = "Summon";
        else if (key.equalsIgnoreCase("copy")) key = "Copy";
        else if (key.equalsIgnoreCase("paste")) key = "Paste";
        else if (key.equalsIgnoreCase("body")) key = "Body";
        else if (key.equalsIgnoreCase("head")) key = "Head";
        else if (key.equalsIgnoreCase("setname")) key = "SetName";
        else if (key.equalsIgnoreCase("reload")) key = "ReLoad";
        else key = "Help";
        return key;
    }
}
