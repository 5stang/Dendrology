package com.scottkillen.mod.dendrology.world.gen.feature.acemus;

import com.scottkillen.mod.dendrology.world.gen.feature.vanilla.AbstractLargeVanillaTree;

import static com.scottkillen.mod.dendrology.content.OverworldSpecies.ACEMUS;

public class LargeAcemusTree extends AbstractLargeVanillaTree
{
    @Override
    protected int getUnmaskedLogMeta() { return ACEMUS.getLogMeta(); }
}
