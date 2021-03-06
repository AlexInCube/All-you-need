package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.items.angelblockplacer;
import com.alexincube.allyouneed.items.portable_nether_portal;
import com.alexincube.allyouneed.items.super_tool;
import com.alexincube.allyouneed.items.ModdedSpawnEggItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, allyouneed.MODID);

    // This is a very simple Item. It has no special properties except for being on our creative tab.
    public static final RegistryObject<Item> SUPER_TOOL = ITEMS.register("super_tool", () -> new super_tool(ItemTier.DIAMOND,5,-2,new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
    public static final RegistryObject<Item> PORTABLE_NETHER_PORTAL = ITEMS.register("portable_nether_portal", () -> new portable_nether_portal());
    public static final RegistryObject<Item> ANGEL_BLOCK_PLACER = ITEMS.register("angel_block_placer", () -> new angelblockplacer());

    public static final RegistryObject<ModdedSpawnEggItem> DANYA_SPAWN_EGG = ITEMS.register("danya_spawn_egg", () -> new ModdedSpawnEggItem(ModEntityTypes.DANYA, 0xF0A5A2, 0xA9672B, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

}
