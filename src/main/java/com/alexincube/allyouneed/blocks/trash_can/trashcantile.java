package com.alexincube.allyouneed.blocks.trash_can;

import com.alexincube.allyouneed.setup.ModBlocks;
import com.alexincube.allyouneed.setup.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class trashcantile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {


    @Nullable
    @OnlyIn(Dist.CLIENT)
    public trashcantile(final TileEntityType<?> type){
        super(type);
    }

    public trashcantile() {
        this(ModTileEntityTypes.trash_can_tile.get());
    }

    public final ItemStackHandler inventory = new ItemStackHandler(1) {


        @Override
        protected void onContentsChanged(final int slot) {
            super.onContentsChanged(slot);
            // Mark the tile entity as having changed whenever its inventory changes.
            // "markDirty" tells vanilla that the chunk containing the tile entity has
            // changed and means the game will save the chunk to disk later.
            trashcantile.this.markDirty();
        }
    };

    // Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);

    @Override
    public void tick(){
        if (!this.world.isRemote) {
            ItemStack itemStack = inventory.getStackInSlot(0);
            if (itemStack != ItemStack.EMPTY){
                inventory.setStackInSlot(0,ItemStack.EMPTY);
            }
        }
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return inventoryCapabilityExternal.cast();
        return super.getCapability(cap, side);
    }

    @Nonnull
    public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
        return new trashcancontainer(windowId, inventory, this);
    }

    @Nonnull

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(ModBlocks.trash_can.get().getTranslationKey());
    }
}
