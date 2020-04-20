package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.block_placer.blockplacer;
import com.alexincube.allyouneed.blocks.angelblock;
import com.alexincube.allyouneed.blocks.block_breaker.blockbreaker;
import com.alexincube.allyouneed.blocks.crafting_station.craftingstation;
import com.alexincube.allyouneed.blocks.redstone_clock.redstoneclock;
import com.alexincube.allyouneed.blocks.trash_can.trashcan;
import com.alexincube.allyouneed.blocks.wood_crate.woodcrate;
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
    public static final RegistryObject<Block> redstone_clock = BLOCKS.register("redstone_clock", redstoneclock::new);
    public static final RegistryObject<Block> block_placer = BLOCKS.register("block_placer", blockplacer::new);
    public static final RegistryObject<Block> crafting_station = BLOCKS.register("crafting_station", craftingstation::new);
}
