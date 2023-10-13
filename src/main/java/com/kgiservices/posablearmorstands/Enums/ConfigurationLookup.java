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

public enum ConfigurationLookup {
    Create_Default_Elevation("create.default-elevation"),
    Create_Default_Distance("create.default-distance"),
    Move_Limit("move.distance-limit"),
    Select_Distance("select.distance"),
    Parameter_One_List ("parameter-one-list"),
    Parameter_Two_TF_List ("parameter-two-tf-list"),
    Parameter_Two_Degree_List ("parameter-two-degree-list"),
    Parameter_Two_Move_List ("parameter-two-move-list");
    public final String path;

    ConfigurationLookup(String path) {
        this.path = path;
    }
}
