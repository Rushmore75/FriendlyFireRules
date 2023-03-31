package com.friendlyfirerules.base.init;

import com.friendlyfirerules.base.Base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTab extends CreativeTabs {

    public ModTab(String name) {
        super(Base.MOD_ID + "." + name);
    }

    /**
     * adding an Icon to the new Creative Tab
     */
    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.ACACIA_BOAT);
    }
}
