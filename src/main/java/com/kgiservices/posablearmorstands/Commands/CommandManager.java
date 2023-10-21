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

import com.kgiservices.posablearmorstands.Commands.SubCommands.*;
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import com.kgiservices.posablearmorstands.PosableArmorStands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {
    private final PosableArmorStands plugin;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(PosableArmorStands plugin) {
        this.plugin = plugin;

        subCommands.add(new BodyCommand(plugin, this));
        subCommands.add(new CopyCommand(plugin, this));
        subCommands.add(new CreateCommand(plugin, this));
        subCommands.add(new DestroyCommand(plugin, this));
        subCommands.add(new GravityCommand(plugin, this));
        subCommands.add(new HeadCommand(plugin, this));
        subCommands.add(new HelpCommand(plugin, this));
        subCommands.add(new LeftArmCommand(plugin, this));
        subCommands.add(new LeftLegCommand(plugin, this));
        subCommands.add(new MoveCommand(plugin, this));
        subCommands.add(new PasteCommand(plugin, this));
        subCommands.add(new ReLoadCommand(plugin, this));
        subCommands.add(new RightArmCommand(plugin, this));
        subCommands.add(new RightLegCommand(plugin, this));
        subCommands.add(new SelectCommand(plugin, this));
        subCommands.add(new SetNameCommand(plugin, this));
        subCommands.add(new ShowArmsCommand(plugin, this));
        subCommands.add(new ShowBaseCommand(plugin, this));
        subCommands.add(new ShowNameCommand(plugin, this));
        subCommands.add(new SmallCommand(plugin, this));
        subCommands.add(new SummonCommand(plugin, this));
        subCommands.add(new TorsoCommand(plugin, this));
        subCommands.add(new UnSelectCommand(plugin, this));
        subCommands.add(new VisibleCommand(plugin, this));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                for (SubCommand subCommand : subCommands) {
                    if (subCommand.subCommandName().commandText.equalsIgnoreCase(args[0])) {
                        if (subCommand.hasPermission(player)) {
                            if (subCommand.isValid(player, args)) {
                                subCommand.Execute(player, args);
                            }
                        } else {
                            ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.No_Permission_For_Command, subCommand.subCommandName());
                        }
                    }
                }
            } else {
                sendPluginHelp(player);
            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commandList = new ArrayList<>();
        if (sender instanceof Player player) {
            if (args.length == 1) {
                for (SubCommand sub : subCommands) {
                    if (sub.hasPermission(player)) {
                        commandList.add(sub.subCommandName().commandText);
                    }
                }
                return commandList;
            } else if (args.length == 2) {
                for (SubCommand subCommand : subCommands) {
                    if (subCommand.subCommandName().commandText.equalsIgnoreCase(args[0])) {
                        if (subCommand.hasPermission(player)) {
                            return subCommand.getParameterOneList(player);
                        }
                    }
                }
            }
        }
        return null;
    }

    public void sendCommandHelp(Player player, String commandName) {
        for (SubCommand subCommand : subCommands) {
            if (subCommand.subCommandName().commandText.equalsIgnoreCase(commandName)) {
                if (subCommand.hasPermission(player)) {
                    subCommand.SendCommandHelp(player);
                }
            }
        }
    }

    public void sendPluginHelp(Player player) {
        for (SubCommand subCommand : subCommands) {
            if (subCommand.hasPermission(player)) {
                subCommand.SendCommandHelp(player);
            }
        }
    }

}
