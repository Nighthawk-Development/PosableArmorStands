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
    Left_arm("leftarm"),
    Left_Leg("leftleg"),
    Move("move"),
    Paste("paste"),
    Right_Arm("rightarm"),
    Right_Leg("rightleg"),
    Select("select"),
    Show_Arms("showarms"),
    Show_Base("showbase"),
    Show_Name("showname"),
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
