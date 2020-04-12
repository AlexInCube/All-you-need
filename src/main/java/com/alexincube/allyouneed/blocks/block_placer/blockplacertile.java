package com.alexincube.allyouneed.blocks.block_placer;

import com.alexincube.allyouneed.setup.ModBlocks;
import com.alexincube.allyouneed.setup.ModTileEntityTypes;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.IIntArray;
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


import static com.alexincube.allyouneed.blocks.MachineBlockBase.REDSTONE_SIGNAL;
import static net.minecraft.block.Block.getBlockFromItem;
import static net.minecraft.state.properties.BlockStateProperties.FACING;


public class blockplacertile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {


    @Nullable
    @OnlyIn(Dist.CLIENT)
    public blockplacertile(final TileEntityType<?> type){
        super(type);
    }

    public blockplacertile() {
        this(ModTileEntityTypes.block_placer_tile.get());
    }

    public final ItemStackHandler inventory = new ItemStackHandler(11) {


        @Override
        protected void onContentsChanged(final int slot) {
            super.onContentsChanged(slot);
            // Mark the tile entity as having changed whenever its inventory changes.
            // "markDirty" tells vanilla that the chunk containing the tile entity has
            // changed and means the game will save the chunk to disk later.
            blockplacertile.this.markDirty();
        }
    };

    // Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private int redstoneControl;

    protected final IIntArray furnaceData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return redstoneControl;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    redstoneControl = value;
                    break;

            }

        }

        public int size() {
            return 1;
        }
    };


    @Override
    public void tick() {
        if (!this.world.isRemote) {
            boolean redstonesignal = world.getBlockState(pos).get(REDSTONE_SIGNAL);
            if ((redstonesignal & redstoneControl == 1) || redstoneControl == 0) {
                Direction direction = world.getBlockState(pos).get(FACING);
                BlockPos blockp = pos.offset(direction, 1);//Get XYZ block which need to break
                Block block = world.getBlockState(blockp).getBlock();//Get block which need to break
                if (world.isAirBlock(blockp) == true) {
                    for (int i = 0; i < 9; i++) {
                        ItemStack itemstack = this.inventory.getStackInSlot(i);
                        if (itemstack!=ItemStack.EMPTY){
                            BlockState block1 = getBlockFromItem(itemstack.getItem()).getDefaultState();
                            if (block1.getBlock()!= Blocks.AIR) {
                                world.setBlockState(blockp, block1);
                                itemstack.shrink(1);
                                break;
                            }
                        }
                    }
                }
                this.markDirty();
            }
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
        return new blockplacercontainer(windowId, inventory, this,furnaceData);
    }

    @Nonnull

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(ModBlocks.block_placer.get().getTranslationKey());
    }

}
