package com.scottkillen.mod.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import java.util.Random;

@SuppressWarnings({
        "AssignmentToMethodParameter",
        "OverlyComplexBooleanExpression",
        "OverlyComplexMethod",
        "OverlyLongMethod",
        "UnsecureRandomNumberGeneration"
})
public class LataTree extends AbstractTree
{
    private int logDirection = 0;

    public LataTree(boolean isFromSapling)
    {
        super(isFromSapling);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(15) + 6;

        if (isPoorGrowthConditions(world, x, y, z, height, ModBlocks.sapling0)) return false;

        final Block block = world.getBlock(x, y - 1, z);
        block.onPlantGrow(world, x, y - 1, z, x, y, z);

        for (int level = 0; level <= height; level++)
        {
            if (level == height) leafGen(world, x, y + level, z);
            else placeLog(world, x, y + level, z);

            if (level > 3 && level < height)
            {
                final int branchRarity = height / level + 1;

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, -1, 0);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 1, 0);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 0, -1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 0, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, -1, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, -1, -1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 1, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, x, y, z, height, level, 1, -1);
            }
        }

        return true;
    }

    private void branch(World world, Random rand, int x, int y, int z, int treeHeight, int branchLevel, int dX, int dZ)
    {
        final int length = treeHeight - branchLevel;
        y += branchLevel;

        for (int i = 0; i <= length; i++)
        {
            if (dX == -1 && rand.nextInt(3) > 0)
            {
                x--;
                logDirection = 4;

                if (dZ == 0 && rand.nextInt(4) == 0) z = z + rand.nextInt(3) - 1;
            }

            if (dX == 1 && rand.nextInt(3) > 0)
            {
                x++;
                logDirection = 4;

                if (dZ == 0 && rand.nextInt(4) == 0) z = z + rand.nextInt(3) - 1;
            }

            if (dZ == -1 && rand.nextInt(3) > 0)
            {
                z--;
                logDirection = 8;

                if (dX == 0 && rand.nextInt(4) == 0) x = x + rand.nextInt(3) - 1;
            }

            if (dZ == 1 && rand.nextInt(3) > 0)
            {
                z++;
                logDirection = 8;

                if (dX == 0 && rand.nextInt(4) == 0) x = x + rand.nextInt(3) - 1;
            }

            placeLog(world, x, y, z);
            logDirection = 0;

            if (rand.nextInt(3) == 0)
            {
                leafGen(world, x, y, z);
            }

            if (rand.nextInt(3) > 0)
            {
                y++;
            }

            if (i == length)
            {
                placeLog(world, x, y, z);
                leafGen(world, x, y, z);
            }
        }
    }

    @SuppressWarnings({ "MethodWithMoreThanThreeNegations", "MethodWithMultipleLoops" })
    private void leafGen(World world, int x, int y, int z)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2))
                    placeLeaves(world, x + dX, y, z + dZ);

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                {
                    placeLeaves(world, x + dX, y + 1, z + dZ);
                    placeLeaves(world, x + dX, y - 1, z + dZ);
                }

                if (Math.abs(dX) + Math.abs(dZ) < 2)
                {
                    placeLeaves(world, x + dX, y + 2, z + dZ);
                    placeLeaves(world, x + dX, y - 2, z + dZ);
                }
            }
    }

    @Override
    protected Block getLeavesBlock() {return ModBlocks.leaves0;}

    @Override
    protected int getLeavesMetadata() {return 0;}

    @Override
    protected Block getLogBlock() {return ModBlocks.logs0;}

    @Override
    protected int getLogMetadata() {return logDirection;}

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("logDirection", logDirection).toString();
    }
}
