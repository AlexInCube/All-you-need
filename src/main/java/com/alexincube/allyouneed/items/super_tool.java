package com.alexincube.allyouneed.items;

import com.alexincube.allyouneed.setup.ModItemGroups;
import net.minecraft.item.Item;

public class super_tool extends Item {
    public super_tool(){
        super(new Item.Properties()
                .maxStackSize(1)
                .group(ModItemGroups.MOD_ITEM_GROUP));
    }
}
