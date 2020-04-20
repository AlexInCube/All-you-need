package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.block_placer.blockplacercontainer;
import com.alexincube.allyouneed.blocks.block_breaker.blockbreakercontainer;
import com.alexincube.allyouneed.blocks.crafting_station.craftingstationcontainer;
import com.alexincube.allyouneed.blocks.redstone_clock.redstoneclockcontainer;
import com.alexincube.allyouneed.blocks.trash_can.trashcancontainer;
import com.alexincube.allyouneed.blocks.wood_crate.woodcratecontainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, allyouneed.MODID);

    public static final RegistryObject<ContainerType<woodcratecontainer>> WOOD_CRATE_CONTAINER = CONTAINER_TYPES.register("wood_crate", () -> IForgeContainerType.create(woodcratecontainer::new));
    public static final RegistryObject<ContainerType<blockbreakercontainer>> BLOCK_BREAKER_CONTAINER = CONTAINER_TYPES.register("block_breaker", () -> IForgeContainerType.create(blockbreakercontainer::new));
    public static final RegistryObject<ContainerType<trashcancontainer>> TRASH_CAN_CONTAINER = CONTAINER_TYPES.register("trash_can", () -> IForgeContainerType.create(trashcancontainer::new));
    public static final RegistryObject<ContainerType<redstoneclockcontainer>> REDSTONE_CLOCK_CONTAINER = CONTAINER_TYPES.register("redstone_clock", () -> IForgeContainerType.create(redstoneclockcontainer::new));
    public static final RegistryObject<ContainerType<blockplacercontainer>> BLOCK_PLACER_CONTAINER = CONTAINER_TYPES.register("block_placer", () -> IForgeContainerType.create(blockplacercontainer::new));
    public static final RegistryObject<ContainerType<craftingstationcontainer>> CRAFTING_STATION_CONTAINER = CONTAINER_TYPES.register("crafting_station", () -> IForgeContainerType.create(craftingstationcontainer::new));
}
