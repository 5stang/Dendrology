package com.scottkillen.mod.kore.tree.block;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.TheMod;
import com.scottkillen.mod.dendrology.block.ModBlocks;
import com.scottkillen.mod.kore.tree.DescribesSlabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.*;

public class ModWoodSlabBlock extends BlockSlab
{
    public static final int CAPACITY = 8;
    private static final int METADATA_MASK = CAPACITY - 1;
    private final ImmutableList<DescribesSlabs> descriptors;

    public ModWoodSlabBlock(boolean isDouble, List<? extends DescribesSlabs> descriptors)
    {
        super(isDouble, Material.wood);

        checkArgument(!descriptors.isEmpty());
        checkArgument(descriptors.size() <= CAPACITY);
        this.descriptors = ImmutableList.copyOf(descriptors);

        setCreativeTab(TheMod.CREATIVE_TAB);
        setBlockName("slab");
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypeWood);
    }

    private static int mask(int metadata) {return metadata & METADATA_MASK;}

    private static boolean isSingleSlab(Item item)
    {
        return ModBlocks.isSingleSlabBlock(Block.getBlockFromItem(item));
    }

    @SuppressWarnings("WeakerAccess")
    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata)
    {
        final DescribesSlabs descriptor = descriptors.get(mask(metadata));
        return descriptor.getPlanksBlock().getIcon(side, mask(metadata));
    }

    @Override
    public Item getItemDropped(int metadata, Random unused, int unused2)
    {
        final DescribesSlabs descriptor = descriptors.get(mask(metadata));
        return Item.getItemFromBlock(descriptor.getSingleSlabBlock());
    }

    @Override
    protected ItemStack createStackedBlock(int metadata)
    {
        final DescribesSlabs descriptor = descriptors.get(mask(metadata));
        return new ItemStack(Item.getItemFromBlock(descriptor.getSingleSlabBlock()), 2, descriptor.getSlabMeta());
    }

    @Override
    public String getUnlocalizedName()
    {
        //noinspection StringConcatenationMissingWhitespace
        return "tile." + TheMod.RESOURCE_PREFIX + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs unused, List subblocks)
    {
        if (isSingleSlab(item))
        {
            for (int i = 0; i < descriptors.size(); ++i)
            {
                //noinspection ObjectAllocationInLoop
                subblocks.add(new ItemStack(item, 1, i));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister unused) {}

    @Override
    public String func_150002_b(int metadata)
    {
        int metadata1 = metadata;
        if (metadata1 < 0 || metadata1 >= descriptors.size())
        {
            metadata1 = 0;
        }

        return getUnlocalizedName() + '.' + descriptors.get(metadata1).getName();
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("descriptors", descriptors).toString();
    }
}
