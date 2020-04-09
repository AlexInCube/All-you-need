package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.angelblock;
import com.alexincube.allyouneed.blocks.blockbreaker.blockbreaker;
import com.alexincube.allyouneed.blocks.trashcan.trashcan;
import com.alexincube.allyouneed.blocks.woodcrate.woodcrate;
import net.minecraft.block.Block;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, allyouneed.MODID);

    public static final RegistryObject<Block> wood_crate = BLOCKS.register("wood_crate", woodcrate::new);
    public static final RegistryObject<Block> angel_block = BLOCKS.register("angel_block", angelblock::new);
    public static final RegistryObject<Block> block_breaker = BLOCKS.register("block_breaker", blockbreaker::new);
    public static final RegistryObject<Block> trash_can = BLOCKS.register("trash_can", trashcan::new);


}
