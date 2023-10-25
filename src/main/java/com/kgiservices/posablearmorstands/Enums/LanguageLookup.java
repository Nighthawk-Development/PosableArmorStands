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

public enum LanguageLookup {
    // Commands
    Commands_Body_Description("commands.body.description"),
    Commands_Body_Usage("commands.body.usage"),
    Commands_Copy_Description("commands.copy.description"),
    Commands_Copy_Usage("commands.copy.usage"),
    Commands_Create_Description("commands.create.description"),
    Commands_Create_Usage("commands.create.usage"),
    Commands_Destroy_Description("commands.destroy.description"),
    Commands_Destroy_Usage("commands.destroy.usage"),
    Commands_Gravity_Description("commands.gravity.description"),
    Commands_Gravity_Usage("commands.gravity.usage"),
    Commands_Head_Description("commands.head.description"),
    Commands_Head_Usage("commands.head.usage"),
    Commands_Help_Description("commands.help.description"),
    Commands_Help_Usage("commands.help.usage"),
    Commands_LeftArm_Description("commands.leftarm.description"),
    Commands_LeftArm_Usage("commands.leftarm.usage"),
    Commands_LeftLeg_Description("commands.leftleg.description"),
    Commands_LeftLeg_Usage("commands.leftleg.usage"),
    Commands_Move_Description("commands.move.description"),
    Commands_Move_Usage("commands.move.usage"),
    Commands_Paste_Description("commands.paste.description"),
    Commands_Paste_Usage("commands.paste.usage"),
    Commands_ReLoad_Description("commands.reload.description"),
    Commands_ReLoad_Usage("commands.reload.usage"),
    Commands_RightArm_Description("commands.rightarm.description"),
    Commands_RightArm_Usage("commands.rightarm.usage"),
    Commands_RightLeg_Description("commands.rightleg.description"),
    Commands_RightLeg_Usage("commands.rightleg.usage"),
    Commands_Select_Description("commands.select.description"),
    Commands_Select_Usage("commands.select.usage"),
    Commands_SetName_Description("commands.setname.description"),
    Commands_SetName_Usage("commands.setname.usage"),
    Commands_ShowArms_Description("commands.showarms.description"),
    Commands_ShowArms_Usage("commands.showarms.usage"),
    Commands_ShowBase_Description("commands.showbase.description"),
    Commands_ShowBase_Usage("commands.showbase.usage"),
    Commands_ShowName_Description("commands.showname.description"),
    Commands_ShowName_Usage("commands.showname.usage"),
    Commands_Small_Description("commands.small.description"),
    Commands_Small_Usage("commands.small.usage"),
    Commands_Summon_Description("commands.summon.description"),
    Commands_Summon_Usage("commands.summon.usage"),
    Commands_Torso_Description("commands.torso.description"),
    Commands_Torso_Usage("commands.torso.usage"),
    Commands_UnSelect_Description("commands.unselect.description"),
    Commands_UnSelect_Usage("commands.unselect.usage"),
    Commands_Visible_Description("commands.visible.description"),
    Commands_Visible_Usage("commands.visible.usage"),

    //Errors
    Armor_Stand_Not_Selected("errors.armor-stand-not-selected"),
    Not_Looking_At_Armor_Stand("errors.not-looking-at-armor-stand"),
    Invalid_Parameter_Count_One("errors.invalid-parameter-count-one"),
    Invalid_Parameter_Count_Zero("errors.invalid-parameter-count-zero"),
    Invalid_Angle_Parameter("errors.invalid-angle-parameter"),
    Invalid_Boolean_Parameter("errors.invalid-boolean-parameter"),
    No_Permission_For_Command("errors.no-permission-for-command"),
    Invalid_Items_Slot6("errors.invalid-items-slots6"),
    Invalid_Items_Slot2("errors.invalid-items-slots2"),
    Invalid_Distance_Parameter("errors.invalid-distance-parameter"),
    Invalid_Name_Parameter("errors.invalid-name-parameter"),
    Illegal_Name_Parameter("errors.illegal-name-parameter");

    public final String path;

    LanguageLookup(String path) {
        this.path = path;
    }
}
