package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public final class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, allyouneed.MODID);


    //public static final RegistryObject<SoundEvent> portal_idle = createSoundEvent("block.portal.idle");
    //public static final RegistryObject<SoundEvent> portal_enter = createSoundEvent("block.portal.enter");

    private static RegistryObject<SoundEvent> createSoundEvent(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(allyouneed.MODID, name)));
    }




}
