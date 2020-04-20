package com.alexincube.allyouneed.joke;

import com.alexincube.allyouneed.allyouneed;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.util.ResourceLocation;

public class DanyaRenderer extends MobRenderer<DanyaEntity, ZombieModel<DanyaEntity>> {

    private static final ResourceLocation DANYA_TEXTURE = new ResourceLocation(allyouneed.MODID, "textures/entity/danya.png");

    public DanyaRenderer(final EntityRendererManager manager) {
        super(manager, new ZombieModel<>(2,false), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(final DanyaEntity entity) {
        return DANYA_TEXTURE;
    }

}
