package com.scottkillen.mod.dendrology.content.overworld;


import com.scottkillen.mod.dendrology.world.AcemusColorizer;
import com.scottkillen.mod.dendrology.world.CerasuColorizer;
import com.scottkillen.mod.dendrology.world.KulistColorizer;
import com.scottkillen.mod.dendrology.world.gen.feature.AbstractTree;
import com.scottkillen.mod.dendrology.world.gen.feature.AcemusTree;
import com.scottkillen.mod.dendrology.world.gen.feature.CedrumTree;
import com.scottkillen.mod.dendrology.world.gen.feature.CerasuTree;
import com.scottkillen.mod.dendrology.world.gen.feature.DelnasTree;
import com.scottkillen.mod.dendrology.world.gen.feature.EwcalyTree;
import com.scottkillen.mod.dendrology.world.gen.feature.HekurTree;
import com.scottkillen.mod.dendrology.world.gen.feature.KiparisTree;
import com.scottkillen.mod.dendrology.world.gen.feature.KulistTree;
import com.scottkillen.mod.dendrology.world.gen.feature.LataTree;
import com.scottkillen.mod.dendrology.world.gen.feature.NucisTree;
import com.scottkillen.mod.dendrology.world.gen.feature.PorfforTree;
import com.scottkillen.mod.dendrology.world.gen.feature.SalyxTree;
import com.scottkillen.mod.dendrology.world.gen.feature.TuopaTree;
import com.scottkillen.mod.dendrology.content.ProvidesPotionEffect;
import com.scottkillen.mod.kore.tree.DefinesLeaves;
import com.scottkillen.mod.kore.tree.DefinesLog;
import com.scottkillen.mod.kore.tree.DefinesSapling;
import com.scottkillen.mod.kore.tree.DefinesSlab;
import com.scottkillen.mod.kore.tree.DefinesStairs;
import com.scottkillen.mod.kore.tree.DefinesTree;
import com.scottkillen.mod.kore.tree.DefinesWood;
import com.scottkillen.mod.kore.tree.block.LeavesBlock;
import com.scottkillen.mod.kore.tree.block.LogBlock;
import com.scottkillen.mod.kore.tree.block.SaplingBlock;
import com.scottkillen.mod.kore.tree.block.SlabBlock;
import com.scottkillen.mod.kore.tree.block.StairsBlock;
import com.scottkillen.mod.kore.tree.block.WoodBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.gen.feature.WorldGenerator;

import static com.google.common.base.Preconditions.*;
import static com.scottkillen.mod.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.ACEMUS_COLOR;
import static com.scottkillen.mod.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.BASIC_COLOR;
import static com.scottkillen.mod.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.CERASU_COLOR;
import static com.scottkillen.mod.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.KULIST_COLOR;
import static com.scottkillen.mod.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.NO_COLOR;

@SuppressWarnings({ "NonSerializableFieldInSerializableClass", "ClassHasNoToStringMethod" })
public enum OverworldTreeSpecies
        implements DefinesLeaves, DefinesLog, DefinesSapling, DefinesSlab, DefinesStairs, DefinesTree, DefinesWood,
        ProvidesPotionEffect
{
    // REORDERING WILL CAUSE DAMAGE TO SAVES
    ACEMUS(ACEMUS_COLOR, new AcemusTree()),
    CEDRUM(NO_COLOR, new CedrumTree()),
    CERASU(CERASU_COLOR, new CerasuTree()),
    DELNAS(NO_COLOR, new DelnasTree()),
    EWCALY(NO_COLOR, new EwcalyTree(), PotionHelper.sugarEffect),
    HEKUR(BASIC_COLOR, new HekurTree()),
    KIPARIS(NO_COLOR, new KiparisTree(), PotionHelper.spiderEyeEffect),
    KULIST(KULIST_COLOR, new KulistTree()),
    LATA(BASIC_COLOR, new LataTree()),
    NUCIS(BASIC_COLOR, new NucisTree()),
    PORFFOR(NO_COLOR, new PorfforTree()),
    SALYX(NO_COLOR, new SalyxTree()),
    TUOPA(BASIC_COLOR, new TuopaTree());

    private final AbstractTree treeGen;
    private final Colorizer colorizer;
    private final String potionEffect;

    private int leavesMeta;
    private int logMeta;
    private int planksMeta;
    private int saplingMeta;
    private int slabMetadata;

    private SlabBlock doubleSlabBlock = null;
    private LeavesBlock leavesBlock = null;
    private LogBlock logBlock = null;
    private WoodBlock woodBlock = null;
    private SaplingBlock saplingBlock = null;
    private SlabBlock singleSlabBlock = null;
    private StairsBlock stairsBlock = null;

    static
    {
        for (final OverworldTreeSpecies tree : OverworldTreeSpecies.values())
            tree.treeGen.setTree(tree);
    }

    OverworldTreeSpecies(Colorizer colorizer, AbstractTree treeGen, String potionEffect)
    {
        this.colorizer = colorizer;
        this.treeGen = treeGen;
        this.potionEffect = potionEffect;
    }

    OverworldTreeSpecies(Colorizer colorizer, AbstractTree treeGen)
    {
        this(colorizer, treeGen, null);
    }

    @Override
    public String potionEffect() { return potionEffect; }

    @Override
    @SideOnly(Side.CLIENT)
    public int getLeavesInventoryColor()
    {
        switch (colorizer)
        {
            case NO_COLOR:
                return 0xffffff;
            case ACEMUS_COLOR:
                return AcemusColorizer.getInventoryColor();
            case CERASU_COLOR:
                return CerasuColorizer.getInventoryColor();
            case KULIST_COLOR:
                return KulistColorizer.getInventoryColor();
            default:
                return Blocks.leaves.getRenderColor(0);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getLeavesColor(IBlockAccess blockAccess, int x, int y, int z)
    {
        switch (colorizer)
        {
            case NO_COLOR:
                return 0xffffff;
            case ACEMUS_COLOR:
                return AcemusColorizer.getColor(x, z);
            case CERASU_COLOR:
                return CerasuColorizer.getColor(x, y, z);
            case KULIST_COLOR:
                return KulistColorizer.getColor(x, y, z);
            default:
                return Blocks.leaves.colorMultiplier(blockAccess, x, y, z);
        }
    }

    @Override
    public void assignLeavesBlock(LeavesBlock leavesBlock)
    {
        checkState(this.leavesBlock == null);
        this.leavesBlock = leavesBlock;
    }

    @Override
    public void assignLeavesSubBlockIndex(int leavesMeta) { this.leavesMeta = leavesMeta; }

    @Override
    public LeavesBlock leavesBlock()
    {
        checkState(leavesBlock != null);
        return leavesBlock;
    }

    @Override
    public int leavesSubBlockIndex() { return leavesMeta; }

    @Override
    public DefinesSapling saplingDefinition() { return this; }

    @Override
    public String speciesName() { return name().toLowerCase(); }

    @Override
    public void assignLogBlock(LogBlock logBlock)
    {
        checkState(this.logBlock == null);
        this.logBlock = logBlock;
    }

    @Override
    public void assignLogSubBlockIndex(int logMeta) { this.logMeta = logMeta; }

    @Override
    public LogBlock logBlock()
    {
        checkState(logBlock != null);
        return logBlock;
    }

    @Override
    public int logSubBlockIndex() { return logMeta; }

    @Override
    public WoodBlock woodBlock()
    {
        checkState(woodBlock != null);
        return woodBlock;
    }

    @Override
    public int woodSubBlockIndex() { return planksMeta; }

    @Override
    public void assignWoodBlock(WoodBlock woodBlock)
    {
        checkState(this.woodBlock == null);
        this.woodBlock = woodBlock;
    }

    @Override
    public void assignWoodSubBlockIndex(int planksMeta) { this.planksMeta = planksMeta; }

    @Override
    public void assignStairsBlock(StairsBlock stairsBlock)
    {
        checkState(this.stairsBlock == null);
        this.stairsBlock = stairsBlock;
    }

    @Override
    public StairsBlock stairsBlock()
    {
        checkState(stairsBlock != null);
        return stairsBlock;
    }

    @Override
    public Block stairsModelBlock() { return woodBlock(); }

    @Override
    public int stairsModelSubBlockIndex() { return woodSubBlockIndex(); }

    @Override
    public String stairsName() { return speciesName(); }

    @Override
    public void assignSaplingBlock(SaplingBlock saplingBlock)
    {
        checkState(this.saplingBlock == null);
        this.saplingBlock = saplingBlock;
    }

    @Override
    public void assignSaplingSubBlockIndex(int saplingMeta) { this.saplingMeta = saplingMeta; }

    @Override
    public SaplingBlock saplingBlock()
    {
        checkState(saplingBlock != null);
        return saplingBlock;
    }

    @Override
    public int saplingSubBlockIndex() { return saplingMeta; }

    @Override
    public WorldGenerator treeGenerator() { return treeGen; }

    @Override
    public void assignDoubleSlabBlock(SlabBlock block)
    {
        checkState(doubleSlabBlock == null);
        doubleSlabBlock = block;
    }

    @Override
    public void assignSingleSlabBlock(SlabBlock block)
    {
        checkState(singleSlabBlock == null);
        singleSlabBlock = block;
    }

    @Override
    public void assignSlabSubBlockIndex(int slabMetadata) { this.slabMetadata = slabMetadata; }

    @Override
    public SlabBlock doubleSlabBlock()
    {
        checkState(doubleSlabBlock != null);
        return doubleSlabBlock;
    }

    @Override
    public SlabBlock singleSlabBlock()
    {
        checkState(singleSlabBlock != null);
        return singleSlabBlock;
    }

    @Override
    public int slabSubBlockIndex() { return slabMetadata; }

    @Override
    public Block slabModelBlock() { return woodBlock(); }

    @Override
    public int slabModelSubBlockIndex() { return woodSubBlockIndex(); }

    @Override
    public String slabName() { return speciesName(); }

    public enum Colorizer
    {
        ACEMUS_COLOR,
        BASIC_COLOR,
        CERASU_COLOR,
        KULIST_COLOR,
        NO_COLOR
    }
}
