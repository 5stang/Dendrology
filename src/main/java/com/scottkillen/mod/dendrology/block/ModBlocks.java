package com.scottkillen.mod.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.config.Settings;
import com.scottkillen.mod.dendrology.content.OverworldTreeSpecies;
import com.scottkillen.mod.kore.tree.DefinesTree;
import com.scottkillen.mod.kore.tree.block.ModLeavesBlock;
import com.scottkillen.mod.kore.tree.block.ModLogBlock;
import com.scottkillen.mod.kore.tree.block.ModPlanksBlock;
import com.scottkillen.mod.kore.tree.block.ModSaplingBlock;
import com.scottkillen.mod.kore.tree.block.ModStairsBlock;
import com.scottkillen.mod.kore.tree.block.ModWoodSlabBlock;
import com.scottkillen.mod.kore.tree.item.LeavesItem;
import com.scottkillen.mod.kore.tree.item.LogItem;
import com.scottkillen.mod.kore.tree.item.PlanksItem;
import com.scottkillen.mod.kore.tree.item.SaplingItem;
import com.scottkillen.mod.kore.tree.item.SlabItem;
import com.scottkillen.mod.kore.tree.loader.TreeSpeciesLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import java.util.Arrays;

import static net.minecraftforge.common.ChestGenHooks.BONUS_CHEST;
import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;
import static net.minecraftforge.common.ChestGenHooks.MINESHAFT_CORRIDOR;
import static net.minecraftforge.common.ChestGenHooks.PYRAMID_DESERT_CHEST;
import static net.minecraftforge.common.ChestGenHooks.PYRAMID_JUNGLE_CHEST;
import static net.minecraftforge.common.ChestGenHooks.PYRAMID_JUNGLE_DISPENSER;
import static net.minecraftforge.common.ChestGenHooks.STRONGHOLD_CORRIDOR;
import static net.minecraftforge.common.ChestGenHooks.STRONGHOLD_CROSSING;
import static net.minecraftforge.common.ChestGenHooks.STRONGHOLD_LIBRARY;
import static net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH;

@SuppressWarnings({ "UtilityClass", "WeakerAccess" })
public final class ModBlocks
{
    private static final int DEFAULT_LEAVES_FIRE_ENCOURAGEMENT = 30;
    private static final int DEFAULT_LOG_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_PLANKS_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_STAIRS_FIRE_ENCOURAGEMENT = DEFAULT_PLANKS_FIRE_ENCOURAGEMENT;
    private static final int DEFAULT_LEAVES_FLAMMABILITY = 60;
    private static final int DEFAULT_LOG_FLAMMABILITY = 5;
    private static final int DEFAULT_PLANKS_FLAMMABILITY = 20;
    private static final int DEFAULT_STAIRS_FLAMMABILITY = DEFAULT_PLANKS_FLAMMABILITY;

    @SuppressWarnings("StaticNonFinalField")
    private static TreeSpeciesLoader overworldContent = null;

    private ModBlocks()
    {
        throw new AssertionError();
    }

    public static void preInit()
    {
        overworldContent = new TreeSpeciesLoader(Arrays.asList(OverworldTreeSpecies.values()), TheMod.INSTANCE);
        overworldContent.load();
        initLogBlocks();
        initLeavesBlock();
        initSaplingBlocks();
        initPlanksBlocks();
        initStairsBlocks();
        initSingleSlabBlocks();
        initDoubleSlabBlocks();
    }

    private static void initStairsBlocks()
    {
        final ImmutableList<ModStairsBlock> stairsBlocks = overworldContent.getStairsBlocks();

        int stairsCount = 0;
        for (final ModStairsBlock stairs : stairsBlocks)
        {
            registerStairsBlock(stairs, String.format("stairs%d", stairsCount));
            stairsCount++;
        }
    }

    private static void initPlanksBlocks()
    {
        final ImmutableList<ModPlanksBlock> planksBlocks = overworldContent.getPlanksBlocks();

        int planksCount = 0;
        for (final ModPlanksBlock wood : planksBlocks)
        {
            registerPlanksBlock(wood, String.format("wood%d", planksCount), wood.getSubBlockNames());
            planksCount++;
        }
    }

    private static void initSaplingBlocks()
    {
        final ImmutableList<ModSaplingBlock> saplingBlocks = overworldContent.getSaplingBlocks();

        int saplingCount = 0;
        for (final ModSaplingBlock sapling : saplingBlocks)
        {
            registerSaplingBlock(sapling, String.format("sapling%d", saplingCount), sapling.getSubBlockNames());
            saplingCount++;

            addSaplingToChest(sapling, VILLAGE_BLACKSMITH, Settings.getBlacksmithRarity());
            addSaplingToChest(sapling, BONUS_CHEST, Settings.getBonusChestRarity());
            addSaplingToChest(sapling, PYRAMID_DESERT_CHEST, Settings.getDesertTempleRarity());
            addSaplingToChest(sapling, DUNGEON_CHEST, Settings.getDungeonRarity());
            addSaplingToChest(sapling, PYRAMID_JUNGLE_CHEST, Settings.getJungleTempleRarity());
            addSaplingToChest(sapling, PYRAMID_JUNGLE_DISPENSER, Settings.getJungleTempleDispenserRarity());
            addSaplingToChest(sapling, MINESHAFT_CORRIDOR, Settings.getMineshaftCorridorRarity());
            addSaplingToChest(sapling, STRONGHOLD_CORRIDOR, Settings.getStrongholdCorridorRarity());
            addSaplingToChest(sapling, STRONGHOLD_CROSSING, Settings.getStrongholdCrossingRarity());
            addSaplingToChest(sapling, STRONGHOLD_LIBRARY, Settings.getStrongholdLibraryRarity());
        }
    }

    private static void addSaplingToChest(ModSaplingBlock sapling, String chestType, int rarity)
    {
        if (rarity > 0) ChestGenHooks.getInfo(chestType)
                .addItem(new WeightedRandomChestContent(new ItemStack(sapling), 1, 2, rarity));
    }

    private static void initSingleSlabBlocks()
    {
        int slabCount = 0;
        final ImmutableList<ModWoodSlabBlock> singleSlabBlocks = overworldContent.getSingleSlabBlocks();
        final ImmutableList<ModWoodSlabBlock> doubleSlabBlocks = overworldContent.getDoubleSlabBlocks();
        for (final ModWoodSlabBlock slab : singleSlabBlocks)
        {
            registerSlabBlock(slab, String.format("sslab%d", slabCount), slab, doubleSlabBlocks.get(slabCount), false);
            slabCount++;
        }
    }

    private static void initDoubleSlabBlocks()
    {
        int slabCount = 0;
        final ImmutableList<ModWoodSlabBlock> doubleSlabBlocks = overworldContent.getDoubleSlabBlocks();
        final ImmutableList<ModWoodSlabBlock> singleSlabBlocks = overworldContent.getSingleSlabBlocks();
        for (final ModWoodSlabBlock slab : doubleSlabBlocks)
        {
            registerSlabBlock(slab, String.format("dslab%d", slabCount), singleSlabBlocks.get(slabCount), slab, true);
            slabCount++;
        }
    }

    private static void initLeavesBlock()
    {
        final ImmutableList<ModLeavesBlock> leavesBlocks = overworldContent.getLeavesBlocks();

        int leavesCount = 0;
        for (final Block block : leavesBlocks)
        {
            registerLeavesBlock(block, String.format("leaves%d", leavesCount));
            leavesCount++;
        }
    }

    private static void initLogBlocks()
    {
        final ImmutableList<ModLogBlock> logBlocks = overworldContent.getLogBlocks();

        int logCount = 0;
        for (final ModLogBlock block : logBlocks)
        {
            registerLogBlock(block, String.format("logs%d", logCount), block.getSubBlockNames());
            logCount++;
        }
    }

    private static void registerLeavesBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, LeavesItem.class, name);
        Blocks.fire.setFireInfo(block, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
    }

    private static void registerLogBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        GameRegistry.registerBlock(block, LogItem.class, name, block,
                subblockNames.toArray(new String[subblockNames.size()]));
        Blocks.fire.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
    }

    private static void registerSaplingBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        GameRegistry.registerBlock(block, SaplingItem.class, name, block,
                subblockNames.toArray(new String[subblockNames.size()]));
    }

    private static void registerSlabBlock(Block block, String name, ModWoodSlabBlock singleSlab,
                                          ModWoodSlabBlock doubleSlab, boolean isDouble)
    {
        GameRegistry.registerBlock(block, SlabItem.class, name, singleSlab, doubleSlab, isDouble);
    }

    private static void registerPlanksBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        GameRegistry.registerBlock(block, PlanksItem.class, name, block,
                subblockNames.toArray(new String[subblockNames.size()]));
        Blocks.fire.setFireInfo(block, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
    }

    private static void registerStairsBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
        Blocks.fire.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    public static Iterable<? extends Block> getLeavesBlocks()
    {
        return overworldContent.getLeavesBlocks();
    }

    public static Iterable<? extends Block> getLogBlocks()
    {
        return overworldContent.getLogBlocks();
    }

    public static Iterable<? extends Block> getPlanksBlocks()
    {
        return overworldContent.getPlanksBlocks();
    }

    public static Iterable<? extends Block> getSaplingBlocks()
    {
        return overworldContent.getSaplingBlocks();
    }

    public static Iterable<? extends Block> getSingleSlabBlocks()
    {
        return overworldContent.getSingleSlabBlocks();
    }

    public static Iterable<? extends Block> getStairsBlocks()
    {
        return overworldContent.getStairsBlocks();
    }

    public static Iterable<? extends DefinesTree> getContent()
    {
        return overworldContent.getSpecies();
    }
}
