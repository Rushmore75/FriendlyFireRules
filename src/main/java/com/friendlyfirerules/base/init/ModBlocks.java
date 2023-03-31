package com.friendlyfirerules.base.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

import com.friendlyfirerules.base.MonumentBlock;

public class ModBlocks {

    // When a new instance of BlockBase is created it automatically adds
    // the instance to these two lists.
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block MONUMENT = new MonumentBlock("monument", MaterialTransparent.CACTUS);

}