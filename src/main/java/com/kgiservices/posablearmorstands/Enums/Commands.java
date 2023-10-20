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

package com.kgiservices.posablearmorstands.Enums;

public enum Commands {
    Body("body"),
    Copy("copy"),
    Create("create"),
    Destroy("destroy"),
    Gravity("gravity"),
    Head("head"),
    Help("help"),
    LeftArm("leftarm"),
    LeftLeg("leftleg"),
    Move("move"),
    Paste("paste"),
    ReLoad("reload"),
    RightArm("rightarm"),
    RightLeg("rightleg"),
    Select("select"),
    SetName("setname"),
    ShowArms("showarms"),
    ShowBase("showbase"),
    ShowName("showname"),
    Small("small"),
    Summon("summon"),
    Torso("torso"),
    UnSelect("unselect"),
    Visible("visible");



    public final String commandText;

    Commands(String commandText) {
        this.commandText = commandText;
    }

}
