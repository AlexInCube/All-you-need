package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.blockbreaker.blockbreakercontainer;
import com.alexincube.allyouneed.blocks.trashcan.trashcancontainer;
import com.alexincube.allyouneed.blocks.woodcrate.woodcratecontainer;
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


}
