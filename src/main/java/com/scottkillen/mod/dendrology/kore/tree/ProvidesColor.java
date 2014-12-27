package com.scottkillen.mod.dendrology.kore.tree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.IBlockAccess;

public interface ProvidesColor
{
    @SideOnly(Side.CLIENT)
    int getLeavesInventoryColor();

    @SideOnly(Side.CLIENT)
    int getLeavesColor(IBlockAccess blockAccess, int x, int y, int z);
}
