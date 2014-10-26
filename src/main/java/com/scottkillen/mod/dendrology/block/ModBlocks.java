package com.scottkillen.mod.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.item.LeavesItem;
import com.scottkillen.mod.dendrology.item.LogItem;
import com.scottkillen.mod.dendrology.item.SaplingItem;
import com.scottkillen.mod.dendrology.world.gen.feature.LataTree;
import com.scottkillen.mod.dendrology.world.gen.feature.Cedar;
import com.scottkillen.mod.dendrology.world.gen.feature.Cherry;
import com.scottkillen.mod.dendrology.world.gen.feature.Cypress;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenerator;

import static com.scottkillen.mod.dendrology.block.ModLeavesBlock.Colorizer.*;

@SuppressWarnings({ "StaticNonFinalField", "UtilityClass", "PublicField" })
public final class ModBlocks
{
    private static final int DEFAULT_LEAVES_FIRE_ENCOURAGEMENT = 30;
    private static final int DEFAULT_LOG_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_LEAVES_FLAMMABILITY = 60;
    private static final int DEFAULT_LOG_FLAMMABILITY = 5;

    private static final ImmutableList<String> LOG0_NAMES = ImmutableList.of("lata", "cedar", "cherry", "cypress");
    private static final ImmutableList<String> LOG1_NAMES = ImmutableList.of("eucalyptus", "ginkgo", "ironwood", "maple");
    private static final ImmutableList<String> LOG2_NAMES = ImmutableList.of("palm", "poplar", "walnut", "willow");
    private static final ImmutableList<String> LEAVES0_NAMES = ImmutableList.of("lata", "cedar", "cypress", "eucalyptus");
    private static final ImmutableList<String> LEAVES1_NAMES = ImmutableList.of("ironwood", "ginkgo", "palm", "poplar");
    private static final ImmutableList<String> LEAVES2_NAMES = ImmutableList.of("cherry.pink", "cherry.white", "maple.red", "maple.yellow");
    private static final ImmutableList<String> LEAVES3_NAMES = ImmutableList.of("ginkgo.yellow", "walnut", "willow");
    private static final ImmutableList<String> SAPLING0_NAMES = ImmutableList.of("lata", "cedar", "cherry", "cypress", "eucalyptus", "ginkgo", "ironwood", "maple");
    private static final ImmutableList<String> SAPLING1_NAMES = ImmutableList.of("palm", "poplar", "walnut", "willow");
    private static final ImmutableList<? extends WorldGenerator> SAPLING0_GENS = ImmutableList.of(new LataTree(true), new Cedar(true), new Cherry(true), new Cypress(true), new WorldGenTaiga1(), new WorldGenTaiga1(), new WorldGenTaiga1(), new WorldGenTaiga1());
    private static final ImmutableList<? extends WorldGenerator> SAPLING1_GENS = ImmutableList.of(new WorldGenTaiga1(), new WorldGenTaiga1(), new WorldGenTaiga1(), new WorldGenTaiga1());
    private static final ImmutableList<ModLeavesBlock.Colorizer> LEAVES0_COLORS = ImmutableList.of(BASIC, PINE, PINE, BIRCH);
    private static final ImmutableList<ModLeavesBlock.Colorizer> LEAVES1_COLORS = ImmutableList.of(BASIC, BASIC, PINE, BASIC);
    private static final ImmutableList<ModLeavesBlock.Colorizer> LEAVES2_COLORS = ImmutableList.of(NONE, NONE, NONE, NONE);
    private static final ImmutableList<ModLeavesBlock.Colorizer> LEAVES3_COLORS = ImmutableList.of(NONE, BASIC, BIRCH);

    public static Block logs0 = new ModLogBlock(LOG0_NAMES);
    public static Block logs1 = new ModLogBlock(LOG1_NAMES);
    public static Block logs2 = new ModLogBlock(LOG2_NAMES);
    public static ModLeavesBlock leaves0 = new ModLeavesBlock(LEAVES0_NAMES, LEAVES0_COLORS);
    public static ModLeavesBlock leaves1 = new ModLeavesBlock(LEAVES1_NAMES, LEAVES1_COLORS);
    public static ModLeavesBlock leaves2 = new ModLeavesBlock(LEAVES2_NAMES, LEAVES2_COLORS);
    public static ModLeavesBlock leaves3 = new ModLeavesBlock(LEAVES3_NAMES, LEAVES3_COLORS);
    public static BlockSapling sapling0 = new ModSaplingBlock(SAPLING0_NAMES, SAPLING0_GENS);
    public static BlockSapling sapling1 = new ModSaplingBlock(SAPLING1_NAMES, SAPLING1_GENS);

    private ModBlocks()
    {
        throw new AssertionError();
    }

    public static void init()
    {
        registerLogBlock(logs0, "logs0", LOG0_NAMES);
        registerLogBlock(logs1, "logs1", LOG1_NAMES);
        registerLogBlock(logs2, "logs2", LOG2_NAMES);
        registerLeavesBlock(leaves0, "leaves0");
        registerLeavesBlock(leaves1, "leaves1");
        registerLeavesBlock(leaves2, "leaves2");
        registerLeavesBlock(leaves3, "leaves3");
        registerSaplingBlock(sapling0, "sapling0", SAPLING0_NAMES);
        registerSaplingBlock(sapling1, "sapling1", SAPLING1_NAMES);

        registerSaplings();
    }

    private static void registerSaplings()
    {   // lata
        ModLeavesBlock.addSapling(leaves0, 0, sapling0, 0);

        // cedar
        ModLeavesBlock.addSapling(leaves0, 1, sapling0, 1);

        // cypress
        ModLeavesBlock.addSapling(leaves0, 2, sapling0, 3);

        // eucalyptus
        ModLeavesBlock.addSapling(leaves0, 3, sapling0, 4);

        // ironwood
        ModLeavesBlock.addSapling(leaves1, 0, sapling0, 6);

        // ginkgo
        ModLeavesBlock.addSapling(leaves1, 1, sapling0, 5);
        ModLeavesBlock.addSapling(leaves3, 0, sapling0, 5);

        // palm
        ModLeavesBlock.addSapling(leaves1, 2, sapling1, 0);

        // poplar
        ModLeavesBlock.addSapling(leaves1, 3, sapling1, 1);

        // cherry
        ModLeavesBlock.addSapling(leaves2, 0, sapling0, 2);
        ModLeavesBlock.addSapling(leaves2, 1, sapling0, 2);

        // maple
        ModLeavesBlock.addSapling(leaves2, 2, sapling0, 7);
        ModLeavesBlock.addSapling(leaves2, 3, sapling0, 7);

        // walnut
        ModLeavesBlock.addSapling(leaves3, 1, sapling1, 2);

        // willow
        ModLeavesBlock.addSapling(leaves3, 2, sapling1, 3);
    }

    private static void registerLeavesBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, LeavesItem.class, name);
        Blocks.fire.setFireInfo(block, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
    }

    private static void registerLogBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        GameRegistry.registerBlock(block, LogItem.class, name, block, subblockNames.toArray(new String[0]));
        Blocks.fire.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
    }

    private static void registerSaplingBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        GameRegistry.registerBlock(block, SaplingItem.class, name, block, subblockNames.toArray(new String[0]));
    }
}
