package com.alexincube.allyouneed.blocks.blockbreaker;

import com.alexincube.allyouneed.setup.ModBlocks;
import com.alexincube.allyouneed.setup.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.alexincube.allyouneed.blocks.blockbreaker.blockbreaker.REDSTONE_SIGNAL;
import static net.minecraft.state.properties.BlockStateProperties.FACING;


public class blockbreakertile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {


    @Nullable
    @OnlyIn(Dist.CLIENT)
    public blockbreakertile(final TileEntityType<?> type){
        super(type);
    }

    public blockbreakertile() {
        this(ModTileEntityTypes.block_breaker_tile.get());
    }

    public final ItemStackHandler inventory = new ItemStackHandler(11) {


        @Override
        protected void onContentsChanged(final int slot) {
            super.onContentsChanged(slot);
            // Mark the tile entity as having changed whenever its inventory changes.
            // "markDirty" tells vanilla that the chunk containing the tile entity has
            // changed and means the game will save the chunk to disk later.
            blockbreakertile.this.markDirty();
        }
    };

    // Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private int redstoneControl;

    public int get(int index) {
        switch(index) {
            case 0: return blockbreakertile.this.redstoneControl;
            default: return 0;
        }
    }

    public void set(int index, int value) {
        switch(index) {
            case 0: this.markDirty(); blockbreakertile.this.redstoneControl = value;
        }
    }



    @Override
    public void tick() {
        if (!this.world.isRemote) {
            boolean redstonesignal = world.getBlockState(pos).get(REDSTONE_SIGNAL);
            if (redstonesignal && redstoneControl == 1 | redstoneControl == 0)  {
                ItemStack itemstack = this.inventory.getStackInSlot(0);//Get ITEMSTACK from slot
                Direction direction = world.getBlockState(pos).get(FACING);
                BlockPos blockp = pos.offset(direction, 1);//Get XYZ block which need to break
                Block block = world.getBlockState(blockp).getBlock();//Get block which need to break

                ItemStack itemstack2 = new ItemStack(Item.getItemFromBlock(block));//Get ITEMSTACK from BLOCK which we break
                if (world.isAirBlock(blockp) == false && itemstack.getCount() <= 63) {//If block is not air and item stack dont equal 64
                    if (itemstack.isEmpty()) {//If slot don't have any item, then set itemstack from block
                        this.inventory.setStackInSlot(0, itemstack2.copy());//Set itemstack
                    } else if (itemstack.getItem() == itemstack2.getItem()) {//If slot have any item and block which we break equivalent block which contained in slot, then increase itemstack
                        itemstack.setCount(itemstack.getCount() + 1);
                    }
                    world.destroyBlock(blockp, false);//just break the block
                }
            }
            this.markDirty();
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        inventoryCapabilityExternal.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        redstoneControl = tag.getInt("redstonecontrol");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        inventoryCapabilityExternal.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });
        tag.putInt("redstonecontrol", redstoneControl);
        System.out.println(tag.getInt("redstonecontrol"));
        return super.write(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
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
        return new blockbreakercontainer(windowId, inventory, this);
    }

    @Nonnull

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(ModBlocks.block_breaker.get().getTranslationKey());
    }
}
