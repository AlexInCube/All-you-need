package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.items.portable_nether_portal;
import com.alexincube.allyouneed.items.super_tool;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, allyouneed.MODID);

    // This is a very simple Item. It has no special properties except for being on our creative tab.
    public static final RegistryObject<Item> SUPER_TOOL = ITEMS.register("super_tool", () -> new super_tool());
    public static final RegistryObject<Item> PORTABLE_NETHER_PORTAL = ITEMS.register("portable_nether_portal", () -> new portable_nether_portal());


}
