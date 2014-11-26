package com.scottkillen.mod.dendrology.content.loader;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.scottkillen.mod.dendrology.block.ModLeavesBlock;
import com.scottkillen.mod.dendrology.block.ModLogBlock;
import com.scottkillen.mod.dendrology.block.ModPlanksBlock;
import com.scottkillen.mod.dendrology.block.ModSaplingBlock;
import com.scottkillen.mod.dendrology.block.ModStairsBlock;
import com.scottkillen.mod.dendrology.block.ModWoodSlabBlock;
import com.scottkillen.mod.dendrology.content.ISpecies;
import net.minecraft.block.Block;
import java.util.List;

public class SpeciesLoader
{
    private final ImmutableList<ISpecies> species;

    private List<ModLogBlock> logBlocks;
    private List<ModLeavesBlock> leavesBlocks;
    private List<ModPlanksBlock> planksBlocks;
    private List<ModSaplingBlock> saplingBlocks;
    private List<ModWoodSlabBlock> singleSlabBlocks;
    private List<ModWoodSlabBlock> doubleSlabBlocks;
    private List<ModStairsBlock> stairsBlocks;

    public SpeciesLoader(List<? extends ISpecies> species)
    {
        this.species = ImmutableList.copyOf(species);
        logBlocks = Lists.newArrayListWithCapacity(species.size() / ModLogBlock.CAPACITY + 1);
        leavesBlocks = Lists.newArrayListWithCapacity(species.size() / ModLeavesBlock.CAPACITY + 1);
        planksBlocks = Lists.newArrayListWithCapacity(species.size() / ModPlanksBlock.CAPACITY + 1);
        saplingBlocks = Lists.newArrayListWithCapacity(species.size() / ModSaplingBlock.CAPACITY + 1);
        singleSlabBlocks = Lists.newArrayListWithCapacity(species.size() / ModWoodSlabBlock.CAPACITY + 1);
        doubleSlabBlocks = Lists.newArrayListWithCapacity(species.size() / ModWoodSlabBlock.CAPACITY + 1);
        stairsBlocks = Lists.newArrayListWithCapacity(species.size());
    }

    public void load()
    {
        loadLogBlocks();
        logBlocks = ImmutableList.copyOf(logBlocks);

        loadLeavesBlocks();
        leavesBlocks = ImmutableList.copyOf(leavesBlocks);

        loadPlanksBlocks();
        planksBlocks = ImmutableList.copyOf(planksBlocks);

        loadSaplingBlocks();
        saplingBlocks = ImmutableList.copyOf(saplingBlocks);

        loadSlabBlocks();
        singleSlabBlocks = ImmutableList.copyOf(singleSlabBlocks);
        doubleSlabBlocks = ImmutableList.copyOf(doubleSlabBlocks);

        loadStairsBlocks();
        stairsBlocks = ImmutableList.copyOf(stairsBlocks);
    }

    private void loadLeavesBlocks()
    {
        final List<ISpecies> trees = Lists.newArrayListWithCapacity(ModLeavesBlock.CAPACITY);
        for (final ISpecies aSpecies : species)
        {
            aSpecies.setLeavesMeta(trees.size());

            trees.add(aSpecies);
            if (trees.size() == ModLeavesBlock.CAPACITY)
            {
                createLeavesBlock(trees);

                trees.clear();
            }
        }
        if (!trees.isEmpty())
        {
            createLeavesBlock(trees);
        }
    }

    private void loadLogBlocks()
    {
        final List<ISpecies> pendingUpdates = Lists.newArrayListWithCapacity(ModLogBlock.CAPACITY);
        for (final ISpecies aSpecies : species)
        {
            aSpecies.setLogMeta(pendingUpdates.size());

            pendingUpdates.add(aSpecies);
            if (pendingUpdates.size() == ModLogBlock.CAPACITY)
            {
                createLogBlock(pendingUpdates);

                pendingUpdates.clear();
            }
        }
        if (!pendingUpdates.isEmpty())
        {
            createLogBlock(pendingUpdates);
        }
    }

    private void loadPlanksBlocks()
    {
        final List<ISpecies> pendingUpdates = Lists.newArrayListWithCapacity(ModPlanksBlock.CAPACITY);
        for (final ISpecies aSpecies : species)
        {
            aSpecies.setPlanksMeta(pendingUpdates.size());

            pendingUpdates.add(aSpecies);
            if (pendingUpdates.size() == ModPlanksBlock.CAPACITY)
            {
                createPlanksBlock(pendingUpdates);

                pendingUpdates.clear();
            }
        }
        if (!pendingUpdates.isEmpty())
        {
            createPlanksBlock(pendingUpdates);
        }
    }

    private void loadSaplingBlocks()
    {
        final List<String> names = Lists.newArrayList();
        final List<ISpecies> trees = Lists.newArrayList();
        for (final ISpecies aSpecies : species)
        {
            aSpecies.setSaplingMeta(names.size());

            names.add(aSpecies.getName());
            trees.add(aSpecies);
            if (names.size() == ModSaplingBlock.CAPACITY)
            {
                createSaplingBlock(names, trees);

                names.clear();
                trees.clear();
            }
        }
        if (!names.isEmpty())
        {
            createSaplingBlock(names, trees);
        }
    }

    private void loadSlabBlocks()
    {
        final List<String> names = Lists.newArrayList();
        final List<ISpecies> trees = Lists.newArrayList();
        for (final ISpecies aSpecies : species)
        {
            aSpecies.setSlabMeta(names.size());

            names.add(aSpecies.toString());
            trees.add(aSpecies);
            if (names.size() == ModWoodSlabBlock.CAPACITY)
            {
                createSlabBlocks(names, trees);

                names.clear();
                trees.clear();
            }
        }
        if (!names.isEmpty())
        {
            createSlabBlocks(names, trees);
        }
    }

    private void loadStairsBlocks()
    {
        for (final ISpecies aSpecies : species)
        {
            //noinspection ObjectAllocationInLoop
            final ModStairsBlock block = new ModStairsBlock(aSpecies.getPlanksBlock(), aSpecies.getPlanksMeta());
            block.setBlockName(String.format("stairs.%s", aSpecies.getName()));
            stairsBlocks.add(block);
            aSpecies.setStairsBlock(block);
        }
    }

    private void createLeavesBlock(List<ISpecies> pendingUpdates)
    {
        final ModLeavesBlock block = new ModLeavesBlock(pendingUpdates);
        leavesBlocks.add(block);

        for (final ISpecies update : pendingUpdates)
            update.setLeavesBlock(block);
    }

    private void createLogBlock(List<ISpecies> pendingUpdates)
    {
        final ModLogBlock block = new ModLogBlock(pendingUpdates);
        logBlocks.add(block);

        for (final ISpecies update : pendingUpdates)
            update.setLogBlock(block);
    }

    private void createPlanksBlock(List<ISpecies> pendingUpdates)
    {
        final ModPlanksBlock block = new ModPlanksBlock(pendingUpdates);
        planksBlocks.add(block);

        for (final ISpecies update : pendingUpdates)
            update.setPlanksBlock(block);
    }

    private void createSaplingBlock(List<String> names, List<ISpecies> pendingUpdates)
    {
        final ModSaplingBlock block = new ModSaplingBlock(names, pendingUpdates);
        saplingBlocks.add(block);

        for (final ISpecies update : pendingUpdates)
            update.setSaplingBlock(block);
    }

    private void createSlabBlocks(List<String> names, List<ISpecies> pendingUpdates)
    {
        final ModWoodSlabBlock singleSlabBlock = new ModWoodSlabBlock(false, names, pendingUpdates);
        final ModWoodSlabBlock doubleSlabBlock = new ModWoodSlabBlock(true, names, pendingUpdates);

        singleSlabBlocks.add(singleSlabBlock);
        doubleSlabBlocks.add(doubleSlabBlock);

        for (final ISpecies update : pendingUpdates)
        {
            update.setSlabBlock(singleSlabBlock, false);
            update.setSlabBlock(doubleSlabBlock, true);
        }
    }

    public ImmutableList<ModLogBlock> getLogBlocks()
    {
        return ImmutableList.copyOf(logBlocks);
    }

    public ImmutableList<ModLeavesBlock> getLeavesBlocks()
    {
        return ImmutableList.copyOf(leavesBlocks);
    }

    public ImmutableList<ModPlanksBlock> getPlanksBlocks()
    {
        return ImmutableList.copyOf(planksBlocks);
    }

    public ImmutableList<ModSaplingBlock> getSaplingBlocks()
    {
        return ImmutableList.copyOf(saplingBlocks);
    }

    public ImmutableList<ModWoodSlabBlock> getSingleSlabBlocks()
    {
        return ImmutableList.copyOf(singleSlabBlocks);
    }

    public ImmutableList<ModWoodSlabBlock> getDoubleSlabBlocks()
    {
        return ImmutableList.copyOf(doubleSlabBlocks);
    }

    public ImmutableList<ModStairsBlock> getStairsBlocks()
    {
        return ImmutableList.copyOf(stairsBlocks);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean isSingleSlabBlock(Block block)
    {
        return singleSlabBlocks.contains(block);
    }

    public ImmutableList<ISpecies> getSpecies()
    {
        return ImmutableList.copyOf(species);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("species", species).add("logBlocks", logBlocks)
                .add("leavesBlocks", leavesBlocks).add("planksBlocks", planksBlocks).add("saplingBlocks", saplingBlocks)
                .add("singleSlabBlocks", singleSlabBlocks).add("doubleSlabBlocks", doubleSlabBlocks)
                .add("stairsBlocks", stairsBlocks).toString();
    }
}
