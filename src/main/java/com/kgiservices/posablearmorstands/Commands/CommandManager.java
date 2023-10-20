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
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(PosableArmorStands plugin) {
        this.plugin = plugin;
        subCommands.add(new ShowNameCommand());
        subCommands.add(new ShowArmsCommand());
        subCommands.add(new CreateCommand());
        subCommands.add(new DestroyCommand());
        subCommands.add(new SelectCommand());
        subCommands.add(new UnSelectCommand());
        subCommands.add(new VisibleCommand());
        subCommands.add(new ShowBaseCommand());
        subCommands.add(new GravityCommand());
        subCommands.add(new LeftArmCommand());
        subCommands.add(new RightArmCommand());
        subCommands.add(new LeftLegCommand());
        subCommands.add(new RightLegCommand());
        subCommands.add(new TorsoCommand());
        subCommands.add(new MoveCommand());
        subCommands.add(new SummonCommand());
        subCommands.add(new HeadCommand());
        subCommands.add(new BodyCommand());
        subCommands.add(new SmallCommand());
        subCommands.add(new CopyCommand());
        subCommands.add(new PasteCommand());
        subCommands.add(new HelpCommand());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                for (SubCommand sub : subCommands) {
                    if (sub.subCommandName().commandText.equalsIgnoreCase(args[0])) {
                        if (sub.hasPermission(player)) {
                            if (sub.isValid(player, args)) {
                                sub.Execute(player, args);
                            }
                        } else {
                            ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.No_Permission_For_Command, sub.subCommandName());
                        }
                    }
                }
            } else {
                //TODO: Display Help page to player
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
                for (SubCommand sub : subCommands) {
                    if (sub.subCommandName().commandText.equalsIgnoreCase(args[0])) {
                        if (sub.hasPermission(player)) {
                            return sub.getParameterOneList(player);
                        }
                    }
                }
            }
        }
        return null;
    }
}
