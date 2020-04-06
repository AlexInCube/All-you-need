package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.blockbreaker.blockbreakertile;
import com.alexincube.allyouneed.blocks.woodcrate.woodcratetile;
import com.alexincube.allyouneed.setup.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, allyouneed.MODID);

    // We don't have a datafixer for our TileEntities, so we pass null into build.
    public static final RegistryObject<TileEntityType<woodcratetile>> wood_crate_tile = TILE_ENTITY_TYPES.register("wood_crate_tile", () ->
            TileEntityType.Builder.create(woodcratetile::new, ModBlocks.wood_crate.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<blockbreakertile>> block_breaker_tile = TILE_ENTITY_TYPES.register("block_breaker_tile", () ->
            TileEntityType.Builder.create(blockbreakertile::new, ModBlocks.block_breaker.get()).build(null)
    );

}
