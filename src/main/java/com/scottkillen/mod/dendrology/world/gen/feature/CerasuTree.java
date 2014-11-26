package com.scottkillen.mod.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import com.scottkillen.mod.dendrology.content.ISpecies;
import com.scottkillen.mod.dendrology.world.gen.feature.cerasu.LargeCerasuTree;
import com.scottkillen.mod.dendrology.world.gen.feature.vanilla.VanillaTree;
import net.minecraft.world.World;
import java.util.Random;

public class CerasuTree extends AbstractTree
{
    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("treeGen", treeGen).add("largeTreeGen", largeTreeGen).toString();
    }

    private final AbstractTree treeGen;
    private final AbstractTree largeTreeGen;

    public CerasuTree()
    {
        treeGen = new VanillaTree();
        largeTreeGen = new LargeCerasuTree();
    }

    @Override
    public void setTree(ISpecies tree)
    {
        treeGen.setTree(tree);
        largeTreeGen.setTree(tree);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        if (rand.nextInt(10) < 9)
        {
            return treeGen.generate(world, rand, x, y, z);
        }

        return largeTreeGen.generate(world, rand, x, y, z);
    }
}
