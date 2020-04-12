package com.alexincube.allyouneed.blocks.redstone_clock;


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

import javax.annotation.Nonnull;
import java.util.Objects;


public class redstoneclockcontainer extends Container {

    public final redstoneclocktile tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    private final IIntArray redstoneData;

    public redstoneclockcontainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new IntArray(3));
    }

    public redstoneclockcontainer(int windowId, PlayerInventory playerInventory, redstoneclocktile tileEntity, IIntArray iIntArray) {
        super(ModContainerTypes.REDSTONE_CLOCK_CONTAINER.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
        this.redstoneData = iIntArray;

        this.trackIntArray(iIntArray);
    }

    private static redstoneclocktile getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
        Objects.requireNonNull(data, "data cannot be null!");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof redstoneclocktile)
            return (redstoneclocktile) tileAtPos;
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(@Nonnull final PlayerEntity player) {
        return isWithinUsableDistance(canInteractWithCallable, player, ModBlocks.redstone_clock.get());
    }

    @OnlyIn(Dist.CLIENT)
    public int getRedstoneControl() {
        return this.redstoneData.get(0);
    }
    @OnlyIn(Dist.CLIENT)
    public int getRedstoneTotalTime() { return this.redstoneData.get(1); }
    @OnlyIn(Dist.CLIENT)
    public int getRedstoneCurrentTime() { return this.redstoneData.get(2); }

    public void setRedstoneControl(int value) {
        this.redstoneData.set(0,value);
    }
    public void setRedstoneTotalTime(int value) {
        this.redstoneData.set(1,value);
    }
    public void setRedstoneCurrentTime(int value) {
        this.redstoneData.set(2,value);
    }
}
