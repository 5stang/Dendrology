package com.scottkillen.mod.kore.tree.block;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.scottkillen.mod.kore.common.OrganizesResources;
import com.scottkillen.mod.kore.tree.DescribesLeaves;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.*;

public class ModLeavesBlock extends BlockLeaves
{
    public static final int CAPACITY = 4;
    private static final int METADATA_MASK = CAPACITY - 1;
    private final ImmutableList<DescribesLeaves> descriptors;
    private final String resourcePrefix;

    public ModLeavesBlock(List<? extends DescribesLeaves> descriptors, OrganizesResources resourceOrganizer)
    {
        checkArgument(!descriptors.isEmpty());
        checkArgument(descriptors.size() <= CAPACITY);
        this.descriptors = ImmutableList.copyOf(descriptors);

        setCreativeTab(resourceOrganizer.getCreativeTab());
        setBlockName("leaves");

        resourcePrefix = resourceOrganizer.getResourcePrefix();
    }

    private static int mask(int metadata) {return metadata & METADATA_MASK;}

    @SuppressWarnings("WeakerAccess")
    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @SideOnly(Side.CLIENT)
    private static boolean isFancyGraphics() {return Minecraft.getMinecraft().gameSettings.fancyGraphics;}

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(int metadata) { return descriptors.get(mask(metadata)).getLeavesInventoryColor(); }

    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
    {
        final int metadata = mask(blockAccess.getBlockMetadata(x, y, z));
        return descriptors.get(metadata).getLeavesColor(blockAccess, x, y, z);
    }

    @Override
    public Item getItemDropped(int metadata, Random unused, int unused2)
    {
        return Item.getItemFromBlock(descriptors.get(mask(metadata)).getSaplingBlock());
    }

    @Override
    public int damageDropped(int metadata) { return descriptors.get(mask(metadata)).getSaplingMeta(); }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isOpaqueCube() { return !isFancyGraphics(); }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int unused, int metadata) { return field_150129_M[isFancyGraphics() ? 0 : 1][mask(metadata)]; }

    @Override
    public String[] func_150125_e()
    {
        final List<String> names = Lists.newArrayList();
        for (final DescribesLeaves descriptor : descriptors)
            names.add(descriptor.getName());
        return names.toArray(new String[names.size()]);
    }

    @Override
    public String getUnlocalizedName()
    {
        //noinspection StringConcatenationMissingWhitespace
        return "tile." + resourcePrefix + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) & 3;

    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs unused, List subBlocks)
    {
        for (int i = 0; i < descriptors.size(); i++)
            //noinspection ObjectAllocationInLoop
            subBlocks.add(new ItemStack(item, 1, i));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        field_150129_M[0] = new IIcon[descriptors.size()];
        field_150129_M[1] = new IIcon[descriptors.size()];

        for (int i = 0; i < descriptors.size(); i++)
        {
            final DescribesLeaves descriptor = descriptors.get(i);
            final String name = descriptor.getName();
            //noinspection StringConcatenationMissingWhitespace
            final String iconName = resourcePrefix + "leaves_" + name.replace('.', '_');
            field_150129_M[0][i] = iconRegister.registerIcon(iconName);
            field_150129_M[1][i] = iconRegister.registerIcon(iconName + "_opaque");
        }
    }

    @SuppressWarnings("OverlyComplexBooleanExpression")
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        final Block block = blockAccess.getBlock(x, y, z);
        return !(!isFancyGraphics() && block.equals(this)) &&
                (side == 0 && minY > 0.0D || side == 1 && maxY < 1.0D || side == 2 && minZ > 0.0D ||
                        side == 3 && maxZ < 1.0D || side == 4 && minX > 0.0D || side == 5 && maxX < 1.0D ||
                        !blockAccess.getBlock(x, y, z).isOpaqueCube());
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("descriptors", descriptors).add("resourcePrefix", resourcePrefix)
                .toString();
    }
}
