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

package com.kgiservices.posablearmorstands.Models;


import com.kgiservices.posablearmorstands.Enums.Commands;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;

public class SelectedArmorStand {
    public SelectedArmorStand(ArmorStand selectedArmorStand) {
        this.selectedArmorStand = selectedArmorStand;
    }

    public ArmorStand getSelectedArmorStand() {
        return selectedArmorStand;
    }



    public Commands getSelectedBodyPart() {
        return selectedBodyPart;
    }

    public Double getDegrees() {
        return degrees;
    }

    final ArmorStand selectedArmorStand;
    Material playerToolMinusX;
    Material playerToolPlusX;
    Material playerToolMinusY;
    Material playerToolPlusY;
    Material playerToolMinusZ;
    Material playerToolPlusZ;
    Commands selectedBodyPart;
    Double degrees;

    public Material getPlayerToolMinusX() {
        return playerToolMinusX;
    }

    public Material getPlayerToolPlusX() {
        return playerToolPlusX;
    }

    public Material getPlayerToolMinusY() {
        return playerToolMinusY;
    }

    public Material getPlayerToolPlusY() {
        return playerToolPlusY;
    }

    public Material getPlayerToolMinusZ() {
        return playerToolMinusZ;
    }

    public Material getPlayerToolPlusZ() {
        return playerToolPlusZ;
    }



    public void setSelectedBodyPart(Material playerToolMinusX, Material playerToolPlusX, Material playerToolMinusY, Material playerToolPlusY, Material playerToolMinusZ, Material playerToolPlusZ, Commands selectedBodyPart, Double degrees) {
        this.playerToolMinusX = playerToolMinusX;
        this.playerToolPlusX = playerToolPlusX;
        this.playerToolMinusY = playerToolMinusY;
        this.playerToolPlusY = playerToolPlusY;
        this.playerToolMinusZ = playerToolMinusZ;
        this.playerToolPlusZ = playerToolPlusZ;

        this.selectedBodyPart = selectedBodyPart;
        this.degrees = degrees;
    }

    public void clearSelectedBodyPart()
    {
        this.playerToolMinusX = null;
        this.playerToolPlusX = null;
        this.playerToolMinusY = null;
        this.playerToolPlusY = null;
        this.playerToolMinusZ = null;
        this.playerToolPlusZ = null;
        this.selectedBodyPart = null;
        this.degrees = -1d;
    }

    public boolean isBodyPartSelected(){ return selectedBodyPart != null; }
}
