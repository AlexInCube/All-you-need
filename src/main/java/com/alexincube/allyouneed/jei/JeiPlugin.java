package com.alexincube.allyouneed.jei;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.crafting_station.craftingstationgui;
import com.alexincube.allyouneed.setup.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin, IGuiContainerHandler<craftingstationgui> {
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new CraftingStationTransferInfo());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.crafting_station.get()), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(allyouneed.MODID, allyouneed.MODID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(craftingstationgui.class,this);
    }

    @Nonnull
    @Override
    public List<Rectangle2d> getGuiExtraAreas(craftingstationgui containerScreen) {
        List<Rectangle2d> areas = new ArrayList<>();
        return areas;
    }
}
