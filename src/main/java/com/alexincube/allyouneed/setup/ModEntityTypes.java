package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.joke.DanyaEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, allyouneed.MODID);

    public static final String DANYA_NAME = "danya";
    public static final RegistryObject<EntityType<DanyaEntity>> DANYA = ENTITY_TYPES.register(DANYA_NAME, () ->
            EntityType.Builder.<DanyaEntity>create(DanyaEntity::new, EntityClassification.CREATURE)
                    .size(EntityType.PIG.getWidth(), EntityType.PIG.getHeight())
                    .build(new ResourceLocation(allyouneed.MODID, DANYA_NAME).toString())
    );

}
