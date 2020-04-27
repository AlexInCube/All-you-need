package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.block_placer.blockplacertile;
import com.alexincube.allyouneed.blocks.block_breaker.blockbreakertile;
import com.alexincube.allyouneed.blocks.crafting_station.craftingstationtile;
import com.alexincube.allyouneed.blocks.redstone_clock.redstoneclocktile;
import com.alexincube.allyouneed.blocks.sprinkler.sprinklertile;
import com.alexincube.allyouneed.blocks.trash_can.trashcantile;
import com.alexincube.allyouneed.blocks.wood_crate.woodcratetile;
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
    public static final RegistryObject<TileEntityType<trashcantile>> trash_can_tile = TILE_ENTITY_TYPES.register("trash_can_tile", () ->
            TileEntityType.Builder.create(trashcantile::new, ModBlocks.trash_can.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<redstoneclocktile>> redstone_clock_tile = TILE_ENTITY_TYPES.register("redstone_clock_tile", () ->
            TileEntityType.Builder.create(redstoneclocktile::new, ModBlocks.redstone_clock.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<blockplacertile>> block_placer_tile = TILE_ENTITY_TYPES.register("block_placer_tile", () ->
            TileEntityType.Builder.create(blockplacertile::new, ModBlocks.block_placer.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<craftingstationtile>> crafting_station_tile = TILE_ENTITY_TYPES.register("crafting_station_tile", () ->
            TileEntityType.Builder.create(craftingstationtile::new, ModBlocks.crafting_station.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<sprinklertile>> sprinkler_tile = TILE_ENTITY_TYPES.register("sprinkler_tile", () ->
            TileEntityType.Builder.create(sprinklertile::new, ModBlocks.sprinkler.get()).build(null)
    );
}
