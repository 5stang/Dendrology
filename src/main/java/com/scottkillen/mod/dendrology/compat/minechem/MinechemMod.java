package com.scottkillen.mod.dendrology.compat.minechem;

import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.kore.compat.Integrator;
import com.scottkillen.mod.dendrology.content.OverworldTreeSpecies;
import com.scottkillen.mod.kore.common.util.log.Logger;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.Optional.Method;
import minechem.api.RecipeAPI;
import net.minecraft.item.ItemStack;

public final class MinechemMod extends Integrator
{
    private static final String MOD_ID = "minechem";
    private static final String MOD_NAME = "Minechem";

    private static final Logger logger = Logger.forMod(TheMod.MOD_ID);

    @Method(modid = MOD_ID)
    private static void addDecomposerRecipes()
    {
        logger.info("Adding Minechem decomposer recipes.");
        for (final OverworldTreeSpecies tree : OverworldTreeSpecies.values())
        {
            //noinspection ObjectAllocationInLoop
            final ItemStack planks = new ItemStack(tree.getPlanksBlock(), 1, tree.getPlanksMeta());
            if (RecipeAPI.addDecompositionRecipe(planks, "2 cellulose"))
                logger.info("Added Minechem decomposer recipe for %s planks.", tree.getName());

            //noinspection ObjectAllocationInLoop
            final ItemStack slab = new ItemStack(tree.getSingleSlabBlock(), 1, tree.getSlabMeta());
            if (RecipeAPI.addDecompositionRecipe(slab, "2 cellulose"))
                logger.info("Added Minechem decomposer recipe for %s slab.", tree.getName());

            //noinspection ObjectAllocationInLoop
            final ItemStack sapling = new ItemStack(tree.getSaplingBlock(), 1, tree.getSaplingMeta());
            if (RecipeAPI.addDecompositionRecipe(sapling, "cellulose"))
                logger.info("Added Minechem decomposer recipe for %s sapling.", tree.getName());

            //noinspection ObjectAllocationInLoop
            final ItemStack log = new ItemStack(tree.getLogBlock(), 1, tree.getLogMeta());
            if (RecipeAPI.addDecompositionRecipe(log, "8 cellulose"))
                logger.info("Added Minechem decomposer recipe for %s wood.", tree.getName());

            //noinspection ObjectAllocationInLoop
            final ItemStack leaves = new ItemStack(tree.getLeavesBlock(), 1, tree.getLeavesMeta());
            if (RecipeAPI.addDecompositionRecipe(leaves, "4 cellulose"))
                logger.info("Added Minechem decomposer recipe for %s wood.", tree.getName());
        }
    }

    @Override
    public void doIntegration(ModState modState)
    {
        if (Loader.isModLoaded(MOD_ID) && modState == ModState.POSTINITIALIZED) addDecomposerRecipes();
    }

    @Override
    protected String modID()
    {
        return MOD_ID;
    }

    @Override
    protected String modName()
    {
        return MOD_NAME;
    }
}
