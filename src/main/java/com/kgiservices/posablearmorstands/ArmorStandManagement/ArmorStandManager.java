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

package com.kgiservices.posablearmorstands.ArmorStandManagement;

import com.kgiservices.posablearmorstands.Enums.Commands;
import com.kgiservices.posablearmorstands.Enums.ConfigurationLookup;
import com.kgiservices.posablearmorstands.Configurations.ConfigurationManager;
import com.kgiservices.posablearmorstands.Enums.LanguageLookup;
import com.kgiservices.posablearmorstands.Models.SelectedArmorStand;
import com.kgiservices.posablearmorstands.PosableArmorStands;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class ArmorStandManager {
    private final PosableArmorStands plugin;
    private static ArmorStandManager instance;
    private final Hashtable<UUID, SelectedArmorStand> selectedArmorStands = new Hashtable<>();
    private final Hashtable<UUID, SelectedArmorStand> copiedArmorStands = new Hashtable<>();

    public ArmorStandManager(PosableArmorStands plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public static ArmorStandManager getInstance() {
        return instance;
    }

    private Location calculateCreateLocation(Location location) {
        double elevation = (double) ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Create_Default_Elevation);
        double distance = (double) ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Create_Default_Distance);
        int newYaw = 0;
        int yaw = (int) location.getYaw();
        if (yaw > 135 || yaw < -135) { //Facing north
            //newYaw = 0;
            location.setZ(location.getZ() - distance);
        } else if (yaw < 45 && yaw >= 0 || yaw > -45 && yaw <= 0) { //facing south
            newYaw = 180;
            location.setZ(location.getZ() + distance);
        } else if (yaw > -135 && yaw < -45) { //facing east
            newYaw = 90;
            location.setX(location.getX() + distance);
        } else if (yaw > 45 && yaw < 135) { //facing west
            newYaw = -90;
            location.setX(location.getX() - distance);
        }
        location.setY(location.getY() + elevation);
        location.setYaw(newYaw);
        return location;
    }

    public void reloadConfiguration() {
        plugin.reloadConfiguration();

    }
    public ArmorStand createArmorStand(Player player) {
        Location location = calculateCreateLocation(player.getLocation());
        return (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
    }


    private ArmorStand getTargetedArmorStand(Player player) {
        ArmorStand armorStand = null;
        Location playerEyeLocation = player.getEyeLocation();
        Vector playerLookingDirection = player.getEyeLocation().getDirection().normalize();
        int distanceToLook = (int) ConfigurationManager.getInstance().getConfigurationValue(ConfigurationLookup.Select_Distance);
        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(playerEyeLocation, playerLookingDirection, distanceToLook, (entity) -> entity instanceof ArmorStand);
        if (rayTraceResult != null) {
            armorStand = (ArmorStand) rayTraceResult.getHitEntity();
        }
        return armorStand;
    }

    public void selectArmorStand(Player player) {
        ArmorStand armorStand = getTargetedArmorStand(player);
        if (armorStand != null) {
            selectArmorStand(player, armorStand);
        } else {
            ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Not_Looking_At_Armor_Stand);
        }
    }

    public void selectArmorStand(Player player, ArmorStand armorStand) {
        if (isArmorStandSelected(player)) {
            unSelectArmorStand(player);
        }
        armorStand.setGlowing(true);

        selectedArmorStands.put(player.getUniqueId(), new SelectedArmorStand(armorStand));
    }

    public void destroySelectArmorStand(Player player) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().remove();
        selectedArmorStands.remove(player.getUniqueId());
    }

    public void setArmorStandVisibility(Player player, boolean visible) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().setVisible(visible);
    }

    public void setArmorStandSetName(Player player, String armorstandName) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().setCustomName(ChatColor.translateAlternateColorCodes('&', armorstandName));
    }


    public void setArmsVisibility(Player player, boolean visible) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().setArms(visible);
    }

    public void setNameVisibility(Player player, boolean visible) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().setCustomNameVisible(visible);
    }

    public void setAffectedByGravity(Player player, boolean visible) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().setGravity(visible);
    }

    public void setSmall(Player player, boolean small) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().setSmall(small);
    }

    public void setBasePlateVisibility(Player player, boolean visible) {
        selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand().setBasePlate(visible);
    }

    public void unSelectArmorStand(Player player) {
        ArmorStand armorStand = selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand();
        armorStand.setGlowing(false);
        selectedArmorStands.remove(player.getUniqueId());
    }

    public void unSelectAllArmorStands() {
        for (SelectedArmorStand selectedArmorStand : selectedArmorStands.values()) {
            ArmorStand armorStand = selectedArmorStand.getSelectedArmorStand();
            armorStand.setGlowing(false);
        }
        selectedArmorStands.clear();
    }

    public void unCopyArmorStand(Player player) {
        //ArmorStand armorStand = copiedArmorStands.get(player.getUniqueId()).getSelectedArmorStand();
        copiedArmorStands.remove(player.getUniqueId());
    }


    public void selectMotionPart(Player player, Commands bodyPart, double degrees) {
        SelectedArmorStand selectedArmorStand = selectedArmorStands.get(player.getUniqueId());
        //ArmorStand armorStand = selectedArmorStand.getSelectedArmorStand();
        if (degrees == -1) {
            selectedArmorStand.clearSelectedBodyPart();
        } else {
            ItemStack itemStackMinusX = player.getInventory().getItem(0);
            ItemStack itemStackPlusX = player.getInventory().getItem(1);
            ItemStack itemStackMinusY = player.getInventory().getItem(2);
            ItemStack itemStackPlusY = player.getInventory().getItem(3);
            ItemStack itemStackMinusZ = player.getInventory().getItem(4);
            ItemStack itemStackPlusZ = player.getInventory().getItem(5);
            if (itemStackMinusX == null || itemStackPlusX == null || itemStackMinusY == null || itemStackPlusY == null || itemStackMinusZ == null || itemStackPlusZ == null) {
                ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Items_Slot6);
                return;
            }

            Material materialMinusX = itemStackMinusX.getType();
            Material materialPlusX = itemStackPlusX.getType();
            Material materialMinusY = itemStackMinusY.getType();
            Material materialPlusY = itemStackPlusY.getType();
            Material materialMinusZ = itemStackMinusZ.getType();
            Material materialPlusZ = itemStackPlusZ.getType();

            Set<Material> materials = new HashSet<>();
            materials.add(materialMinusX);
            materials.add(materialPlusX);
            materials.add(materialMinusY);
            materials.add(materialPlusY);
            materials.add(materialMinusZ);
            materials.add(materialPlusZ);

            if (materials.size() < 6) {
                ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Items_Slot6);
                return;
            }
            selectedArmorStands.get(player.getUniqueId()).setSelectedBodyPart(itemStackMinusX.getType(), itemStackPlusX.getType(), itemStackMinusY.getType(), itemStackPlusY.getType(), itemStackMinusZ.getType(), itemStackPlusZ.getType(), bodyPart, degrees);

        }
    }

    public void selectRotationPart(Player player, Commands bodyPart, double degrees) {
        SelectedArmorStand selectedArmorStand = selectedArmorStands.get(player.getUniqueId());
        //ArmorStand armorStand = selectedArmorStand.getSelectedArmorStand();
        if (degrees == -1) {
            selectedArmorStand.clearSelectedBodyPart();
        } else {
            ItemStack itemStackMinusX = player.getInventory().getItem(0);
            ItemStack itemStackPlusX = player.getInventory().getItem(1);

            if (itemStackMinusX == null || itemStackPlusX == null) {
                ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Items_Slot2);
                return;
            }

            Material materialMinusX = itemStackMinusX.getType();
            Material materialPlusX = itemStackPlusX.getType();

            Set<Material> materials = new HashSet<>();
            materials.add(materialMinusX);
            materials.add(materialPlusX);

            if (materials.size() < 2) {
                ConfigurationManager.getInstance().sendPlayerMessage(player, LanguageLookup.Invalid_Items_Slot6);
                return;
            }
            selectedArmorStands.get(player.getUniqueId()).setSelectedBodyPart(itemStackMinusX.getType(), itemStackPlusX.getType(), null, null, null, null, bodyPart, degrees);

        }
    }

    public void moveHandler(Player player, Material material) {
        SelectedArmorStand selectedArmorStand = selectedArmorStands.get(player.getUniqueId());
        ArmorStand armorStand = selectedArmorStand.getSelectedArmorStand();
        Commands bodyPart = selectedArmorStand.getSelectedBodyPart();
        double degrees = selectedArmorStand.getDegrees();

        if (selectedArmorStand.getSelectedBodyPart().equals(Commands.Move)) {
            Location location = armorStand.getLocation();

            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();

            if (material.equals(selectedArmorStand.getPlayerToolPlusX())) {
                x += degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolPlusY())) {
                y += degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolPlusZ())) {
                z += degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolMinusX())) {
                x -= degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolMinusY())) {
                y -= degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolMinusZ())) {
                z -= degrees;
            }

            location.setX(x);
            location.setY(y);
            location.setZ(z);
            armorStand.teleport(location);

        } else if (bodyPart.equals(Commands.Body)) {
            Location location = armorStand.getLocation();
            float yaw = location.getYaw();
            yaw += 180;

            if (material.equals(selectedArmorStand.getPlayerToolPlusX())) {
                if (yaw + degrees >= 360) {
                    yaw -= 360;
                }
                yaw += (float)degrees;

            } else if (material.equals(selectedArmorStand.getPlayerToolMinusX())) {
                if (yaw - degrees <= 0) {
                    yaw -= 360;
                }
                yaw -= (float)degrees;
            }

            yaw -= 180;
            location.setYaw(yaw);
            armorStand.teleport(location);

        } else {
            EulerAngle pose = new EulerAngle(0,0,0);
            if (bodyPart.equals(Commands.LeftArm)) {
                pose = armorStand.getLeftArmPose();
            } else if (bodyPart.equals(Commands.RightArm)) {
                pose = armorStand.getRightArmPose();
            } else if (bodyPart.equals(Commands.LeftLeg)) {
                pose = armorStand.getLeftLegPose();
            } else if (bodyPart.equals(Commands.RightLeg)) {
                pose = armorStand.getRightLegPose();
            } else if (bodyPart.equals(Commands.Torso)) {
                pose = armorStand.getBodyPose();
            } else if (bodyPart.equals(Commands.Head)) {
                pose = armorStand.getHeadPose();
            }

            double x = Math.toDegrees(pose.getX());
            double y = Math.toDegrees(pose.getY());
            double z = Math.toDegrees(pose.getZ());

            if (material.equals(selectedArmorStand.getPlayerToolPlusX())) {
                if (x + degrees >= 360) {
                    x -= 360;
                }
                x += degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolPlusY())) {
                if (y + degrees >= 360) {
                    y -= 360;
                }
                y += degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolPlusZ())) {
                if (z + degrees >= 360) {
                    z -= 360;
                }
                z += degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolMinusX())) {
                if (x - degrees <= 0) {
                    x -= 360;
                }
                x -= degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolMinusY())) {
                if (y - degrees <= 0) {
                    y += 360;
                }
                y -= degrees;
            } else if (material.equals(selectedArmorStand.getPlayerToolMinusZ())) {
                if (z - degrees <= 360) {
                    z += 360;
                }
                z -= degrees;
            }

            x = Math.toRadians(x);
            y = Math.toRadians(y);
            z = Math.toRadians(z);

            if (bodyPart.equals(Commands.LeftArm)) {
                armorStand.setLeftArmPose(new EulerAngle(x, y, z));
            } else if (bodyPart.equals(Commands.RightArm)) {
                armorStand.setRightArmPose(new EulerAngle(x, y, z));
            } else if (bodyPart.equals(Commands.LeftLeg)) {
                armorStand.setLeftLegPose(new EulerAngle(x, y, z));
            } else if (bodyPart.equals(Commands.RightLeg)) {
                armorStand.setRightLegPose(new EulerAngle(x, y, z));
            } else if (bodyPart.equals(Commands.Torso)) {
                armorStand.setBodyPose(new EulerAngle(x, y, z));
            } else if (bodyPart.equals(Commands.Head)) {
                armorStand.setHeadPose(new EulerAngle(x, y, z));
            }
        }

    }


    public ArmorStand returnArmorStand(Player player) {
        SelectedArmorStand selectedArmorStand = selectedArmorStands.get(player.getUniqueId());
        ArmorStand armorStand = selectedArmorStand.getSelectedArmorStand();
        Location location = calculateCreateLocation(player.getLocation());
        armorStand.teleport(location);
        return armorStand;
    }

    public void copyArmorStand(Player player) {
        if (isArmorStandCopied(player)) {
            unCopyArmorStand(player);
        }
        copiedArmorStands.put(player.getUniqueId(), new SelectedArmorStand(selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand()));
    }

    public void pasteArmorStand(Player player) {
        ArmorStand armorStandSelected = selectedArmorStands.get(player.getUniqueId()).getSelectedArmorStand();
        ArmorStand armorStandCopied = copiedArmorStands.get(player.getUniqueId()).getSelectedArmorStand();
        armorStandSelected.setLeftArmPose(armorStandCopied.getLeftArmPose());
        armorStandSelected.setRightArmPose(armorStandCopied.getRightArmPose());
        armorStandSelected.setLeftLegPose(armorStandCopied.getLeftLegPose());
        armorStandSelected.setRightLegPose(armorStandCopied.getRightLegPose());
        armorStandSelected.setBodyPose(armorStandCopied.getBodyPose());
        armorStandSelected.setHeadPose(armorStandCopied.getHeadPose());
        armorStandSelected.setArms(armorStandCopied.hasArms());
        armorStandSelected.setBasePlate(armorStandCopied.hasBasePlate());
        armorStandSelected.setGravity(armorStandCopied.hasArms());
        armorStandSelected.setSmall(armorStandCopied.isSmall());
        armorStandSelected.getLocation().setYaw(armorStandCopied.getLocation().getYaw());

    }

    public boolean isArmorStandSelected(Player player) {
        return selectedArmorStands.containsKey(player.getUniqueId());
    }

    public boolean isBodyPartSelected(Player player) {
        return selectedArmorStands.get(player.getUniqueId()).isBodyPartSelected();
    }

    public boolean isArmorStandCopied(Player player) {
        return copiedArmorStands.containsKey(player.getUniqueId());
    }

}
