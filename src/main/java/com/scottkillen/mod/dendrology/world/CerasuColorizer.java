package com.scottkillen.mod.dendrology.world;

import com.scottkillen.mod.dendrology.TheMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import java.io.IOException;

@SideOnly(Side.CLIENT)
public enum CerasuColorizer implements IResourceManagerReloadListener
{
    INSTANCE;
    @SuppressWarnings("StaticNonFinalField")
    private static int[] buffer = new int[256*256];
    private static final ResourceLocation texture = new ResourceLocation(TheMod.MOD_ID, "textures/colormap/cerasu.png");

    public static int getCerasuInventoryColor()
    {
        return buffer[0x80 << 8 | 0x80];
    }

    public static int getCerasuColor(int x, int y, int z)
    {
        final int i = x + y & 0xff;
        final int j = z + y & 0xff;
        return buffer[i << 8 | j];
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        try {
            //noinspection AssignmentToStaticFieldFromInstanceMethod
            buffer = TextureUtil.readImageData(resourceManager, texture);
        } catch (final IOException ignored) { }
    }
}
