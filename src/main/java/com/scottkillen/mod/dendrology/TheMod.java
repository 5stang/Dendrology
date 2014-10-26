package com.scottkillen.mod.dendrology;

import com.scottkillen.mod.dendrology.block.ModBlocks;
import com.scottkillen.mod.dendrology.config.ConfigHandler;
import com.scottkillen.mod.dendrology.item.ModItems;
import com.scottkillen.mod.dendrology.world.gen.CedrumTreeGenerator;
import com.scottkillen.mod.dendrology.world.gen.LataTreeGenerator;
import com.scottkillen.mod.dendrology.world.gen.CherryGenerator;
import com.scottkillen.mod.dendrology.world.gen.CypressGenerator;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

@Mod(modid = TheMod.MOD_ID, name = TheMod.MOD_NAME, version = TheMod.MOD_VERSION, useMetadata = true, guiFactory = TheMod.MOD_GUI_FACTORY)
public class TheMod
{
    public static final String MOD_ID = "dendrology";
    public static final String MOD_NAME = "Dendrology";
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ':';
    @SuppressWarnings("AnonymousInnerClass")
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(Blocks.log);
        }
    };
    @SuppressWarnings("WeakerAccess")
    static final String MOD_VERSION = "@MOD_VERSION@";
    @SuppressWarnings("WeakerAccess")
    static final String MOD_GUI_FACTORY = "com.scottkillen.mod.dendrology.config.client.ModGuiFactory";

    @SuppressWarnings("MethodMayBeStatic")
    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        ModItems.init();
        ModBlocks.init();
    }

    @SuppressWarnings({ "UnusedParameters", "MethodMayBeStatic" })
    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent unused)
    {
        FMLCommonHandler.instance().bus().register(ConfigHandler.INSTANCE);

        // Recipes.init();
        LataTreeGenerator.init();
        CedrumTreeGenerator.init();
        CherryGenerator.init();
        CypressGenerator.init();
    }

    @SuppressWarnings("UnusedParameters")
    @Mod.EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event)
    {
        // TODO: Handle interaction with other mods, complete your setup based on this.
    }
}
