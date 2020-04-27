package com.alexincube.allyouneed.blocks.sprinkler;

import com.alexincube.allyouneed.packets.IRedstoneControlChange;
import com.alexincube.allyouneed.setup.ModBlocks;
import com.alexincube.allyouneed.setup.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;


public class sprinklercontainer extends Container implements IRedstoneControlChange {

    public final sprinklertile tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    private final IIntArray sprinklerdata;

    public sprinklercontainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new IntArray(3));
    }

    public sprinklercontainer(int windowId, PlayerInventory playerInventory, sprinklertile tileEntity, IIntArray iIntArray) {
        super(ModContainerTypes.SPRINKLER_CONTAINER.get(), windowId);
        this.tileEntity = tileEntity;
        this.sprinklerdata = iIntArray;
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos());

        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 84;
        final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18

        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 3; ++j)
            {
                this.addSlot(new SlotItemHandler(tileEntity.inventory, j + i*3, 62 + j*18, 18 + i*18));
            }
        }


        // Player Top Inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
            }
        }

        final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
        // Player Hotbar slots
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }

        this.trackIntArray(iIntArray);
    }


    private static sprinklertile getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
        Objects.requireNonNull(data, "data cannot be null!");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof sprinklertile)
            return (sprinklertile) tileAtPos;
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(final @NotNull PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @Override
    public boolean canInteractWith(@Nonnull final PlayerEntity player) {
        return isWithinUsableDistance(canInteractWithCallable, player, ModBlocks.sprinkler.get());
    }

    @OnlyIn(Dist.CLIENT)
    public int getRedstoneControl() {
        return this.sprinklerdata.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getAngle(){
        return this.sprinklerdata.get(1);
    }

    @Override
    public void redstonecontrolchange() {
        if (this.sprinklerdata.get(0)==0){
            this.sprinklerdata.set(0,1);
        }else{
            this.sprinklerdata.set(0,0);
        }
    }
}
