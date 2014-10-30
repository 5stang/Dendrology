package com.scottkillen.mod.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.world.gen.feature.hekur.LargeHekurTree;
import com.scottkillen.mod.dendrology.world.gen.feature.hekur.NormalHekurTree;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import java.util.Random;

public class HekurTree extends WorldGenAbstractTree
{
    private final WorldGenAbstractTree treeGen;
    private final WorldGenAbstractTree largeTreeGen;

    public HekurTree()
    {
        super(true);
        treeGen = new NormalHekurTree();
        largeTreeGen = new LargeHekurTree();
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        while (world.getBlock(x, y - 1, z).getMaterial().equals(Material.water))
            //noinspection AssignmentToMethodParameter
            y--;

        if (rand.nextInt(10) < 9) return treeGen.generate(world, rand, x, y, z);

        return largeTreeGen.generate(world, rand, x, y, z);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("treeGen", treeGen).add("largeTreeGen", largeTreeGen).toString();
    }
}
