package com.alexincube.allyouneed.blocks.redstone_clock;

import com.alexincube.allyouneed.setup.ModBlocks;
import com.alexincube.allyouneed.setup.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
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
import static com.alexincube.allyouneed.blocks.redstone_clock.redstoneclock.POWERED;


public class redstoneclocktile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {


    @Nullable
    @OnlyIn(Dist.CLIENT)
    public redstoneclocktile(final TileEntityType<?> type){
        super(type);
    }

    public redstoneclocktile() {
        this(ModTileEntityTypes.redstone_clock_tile.get());
    }

    private int redstoneTotalTime=100;
    private int redstoneCurrentTime=100;

    protected final IIntArray redstoneData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 1:
                    return redstoneclocktile.this.redstoneTotalTime;
                case 2:
                    return redstoneclocktile.this.redstoneCurrentTime;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 1:
                    redstoneclocktile.this.redstoneTotalTime = value;
                    break;
                case 2:
                    redstoneclocktile.this.redstoneCurrentTime = value;
            }

        }

        public int size() {
            return 3;
        }
    };



    @Override
    public void tick(){
        if (!this.world.isRemote) {
                redstoneCurrentTime -= 1;
                if (redstoneCurrentTime <= 0) {
                    redstoneCurrentTime = redstoneTotalTime;
                    world.setBlockState(pos, ModBlocks.redstone_clock.get().getDefaultState().with(POWERED, true));
                }else{
                    world.setBlockState(pos, ModBlocks.redstone_clock.get().getDefaultState().with(POWERED, false));
                }
                this.markDirty();
            }
        }

    @Override
    public void read(CompoundNBT tag) {
        redstoneTotalTime = tag.getInt("redstonetotaltime");
        redstoneCurrentTime = tag.getInt("redstonecurrenttime");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("redstonetotaltime", redstoneTotalTime);
        tag.putInt("redstonecurrenttime", redstoneCurrentTime);
        return super.write(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }


    @Nonnull
    public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
        return new redstoneclockcontainer(windowId, inventory, this,redstoneData);
    }

    @Nonnull

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(ModBlocks.redstone_clock.get().getTranslationKey());
    }
}
