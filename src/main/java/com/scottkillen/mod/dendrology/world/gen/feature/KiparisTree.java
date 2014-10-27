package com.scottkillen.mod.dendrology.world.gen.feature;

import com.scottkillen.mod.dendrology.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import java.util.Random;

@SuppressWarnings({ "OverlyComplexBooleanExpression", "MethodWithMoreThanThreeNegations", "MethodWithMultipleLoops" })
public class KiparisTree extends AbstractTree
{
    public KiparisTree(boolean isFromSapling)
    {
        super(isFromSapling);
    }

    private static boolean inRangeInclusive(int value, int min, int max)
    {
        return min <= value && value <= max;
    }

    @Override
    protected boolean canBeReplacedByLog(World world, int x, int y, int z)
    {
        return super.canBeReplacedByLog(world, x, y, z) || world.getBlock(x, y, z).getMaterial().equals(Material.water);
    }

    @Override
    protected Block getLeavesBlock() {return ModBlocks.leaves0;}

    @Override
    protected int getLeavesMetadata() {return 2;}

    @Override
    protected Block getLogBlock() {return ModBlocks.logs0;}

    @Override
    protected int getLogMetadata() {return 3;}

    @Override
    protected boolean isReplaceable(World world, int x, int y, int z)
    {
        return super.isReplaceable(world, x, y, z) || world.getBlock(x, y, z).getMaterial().equals(Material.water);
    }

    @SuppressWarnings("OverlyComplexMethod")
    @Override
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        final int size = 1 + (random.nextInt(7) < 2 ? 1 : 0) + (random.nextInt(7) < 2 ? 1 : 0) + (random.nextInt(2) == 0 ? 1 : 0);
        final int height = 4 * size + 1;

        if (isPoorGrowthConditions(world, i, j, k, height, ModBlocks.sapling0)) return false;

        final Block block = world.getBlock(i, j - 1, k);
        block.onPlantGrow(world, i, j - 1, k, i, j, k);

        for (int dY = 0; dY <= height; dY++)
        {
            if (dY != height) placeLog(world, i, j + dY, k);

            if (dY >= 1)
            {
                switch (size)
                {
                    case 1:
                        genSmallLeaves(world, i, j + dY, k);
                        break;
                    case 2:
                        genMediumLeaves(world, i, j, k, dY);
                        break;
                    case 3:
                        genLargeLeaves(world, i, j, k, dY);
                        break;
                    default:
                        genExtraLargeLeaves(world, i, j, k, dY);
                        break;
                }
            }

            if (dY == height) placeLeaves(world, i, j + dY + 1, k);
            if (dY == height && (size == 4 || size == 3)) placeLeaves(world, i, j + dY + 2, k);
        }
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void genExtraLargeLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, x + dX, y + dY, z + dZ);

                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY <= 14 && dY >= 2)
                    placeLeaves(world, x + dX, y + dY, z + dZ);

                if (Math.abs(dX) <= 2 && Math.abs(dZ) <= 2 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2) && dY == 12 || dY == 11 || dY == 3)
                    placeLeaves(world, x + dX, y + dY, z + dZ);

                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && dY <= 10 && dY >= 4)
                    placeLeaves(world, x + dX, y + dY, z + dZ);
            }
    }

    private void genLargeLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, x + dX, y + dY, z + dZ);

                if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) && (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 10 && dY >= 2)
                    placeLeaves(world, x + dX, y + dY, z + dZ);
            }
    }

    private void genMediumLeaves(World world, int x, int y, int z, int dY)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                {
                    placeLeaves(world, x + dX, y + dY, z + dZ);
                }
                if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY == 7)
                {
                    placeLeaves(world, x + dX, y + 7, z + dZ);
                }
                if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) && (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 6 && dY >= 2)
                {
                    placeLeaves(world, x + dX, y + dY, z + dZ);
                }
            }
    }

    @SuppressWarnings("MethodWithMultipleLoops")
    private void genSmallLeaves(World world, int x, int y, int z)
    {
        for (int dX = -1; dX <= 1; dX++)
            for (int dZ = -1; dZ <= 1; dZ++)
                if (Math.abs(dX) != 1 || Math.abs(dZ) != 1) placeLeaves(world, x + dX, y, z + dZ);
    }
}
