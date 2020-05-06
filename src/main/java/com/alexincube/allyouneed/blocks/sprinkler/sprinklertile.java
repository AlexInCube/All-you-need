package com.alexincube.allyouneed.blocks.sprinkler;

import com.alexincube.allyouneed.setup.ModBlocks;
import com.alexincube.allyouneed.setup.ModTileEntityTypes;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static com.alexincube.allyouneed.blocks.sprinkler.sprinklerblock.REDSTONE_SIGNAL;
import static net.minecraft.block.FarmlandBlock.MOISTURE;
import static net.minecraftforge.common.util.BlockSnapshot.readFromNBT;


public class sprinklertile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private int radius=11;
    private int coordcenter = (int) Math.floor(radius/2);
    private static final String ANGLE_TAG = "angle";
    public Container cont;

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public sprinklertile(final TileEntityType<?> type){
        super(type);
    }

    public sprinklertile() {
        this(ModTileEntityTypes.sprinkler_tile.get());
    }

    public final ItemStackHandler inventory = new ItemStackHandler(10) {


        @Override
        protected void onContentsChanged(final int slot) {
            super.onContentsChanged(slot);
            // Mark the tile entity as having changed whenever its inventory changes.
            // "markDirty" tells vanilla that the chunk containing the tile entity has
            // changed and means the game will save the chunk to disk later.
            sprinklertile.this.markDirty();
        }
    };

    // Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
    private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    private int redstoneControl=1;
    private int angle=0;


    protected final IIntArray sprinklerdata = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return redstoneControl;
                case 1:
                    return angle;
                case 2:
                    return radius;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    redstoneControl = value;
                    break;
                case 1:
                    angle = value;
                    break;
                case 2:
                    radius = value;
                    break;
            }

        }

        public int size() {
            return 3;
        }
    };


    @Override
    public void tick() {
        if (!this.world.isRemote) {
            boolean redstonesignal = world.getBlockState(pos).get(REDSTONE_SIGNAL);
            if ((redstonesignal & redstoneControl == 1) || redstoneControl == 0) {
                updateAnimation();
                    for(int i=0;i<radius;i++) {
                        for(int j=0;j<radius;j++) {
                            BlockPos pos1 = pos.add(-coordcenter+i,-1,-coordcenter+j);
                            if (world.getBlockState(pos1).getBlock()==Blocks.FARMLAND){
                                world.setBlockState(pos1, Blocks.FARMLAND.getDefaultState().with(MOISTURE,7));
                            }
                            BlockPos cropPos = pos1.add(0,1,0);
                            BlockState cropsState = world.getBlockState(cropPos);
                            Block cropsBlock = cropsState.getBlock();
                            if (cropsBlock instanceof IGrowable) {
                                IGrowable igrowable = (IGrowable)cropsBlock;
                                if (((CropsBlock)cropsBlock).isMaxAge(cropsState)==false){
                                    if (igrowable.canGrow(world, cropPos, cropsState, world.isRemote)) {
                                        if (igrowable.canUseBonemeal(world, world.rand, cropPos, cropsState)) {
                                            igrowable.grow((ServerWorld) world, world.rand, cropPos, cropsState);
                                        }
                                    }
                                }
                            }
                        }
                    }
                world.notifyBlockUpdate(pos, this.getBlockState(),this.getBlockState(), 2);
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
        return super.write(tag);
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
        return this.cont = new sprinklercontainer(windowId, inventory, this,this.sprinklerdata);
    }

    @Nonnull
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(ModBlocks.sprinkler.get().getTranslationKey());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        // This, combined with isGlobalRenderer in the TileEntityRenderer makes it so that the
        // render does not disappear if the player can't see the block
        // This is useful for rendering larger models or dynamically sized models
        return INFINITE_EXTENT_AABB;
    }

    public int getAngle(){
        return this.angle;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt(ANGLE_TAG,this.angle);
        return new SUpdateTileEntityPacket(this.pos,0,tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        BlockState state = this.world.getBlockState(this.pos);
        this.angle = pkt.getNbtCompound().getInt(ANGLE_TAG);
        this.world.notifyBlockUpdate(this.pos, state, state, 3);
    }

    public void updateAnimation() {
            this.angle += 2;
            if (this.angle > 358) {
                this.angle = 0;
            }
        spawnWaterParticles(this.world, pos, 0, this.angle);
    }

    public void spawnWaterParticles(IWorld worldIn, BlockPos posIn, int data, int angle) {
        Random random = new Random();
        if (data == 0) {
            data = 15;
        }

        BlockState blockstate = worldIn.getBlockState(posIn);
        if (!blockstate.isAir(worldIn, posIn)) {
            for(int j = 0; j<4;j++) {
                for (int i = 0; i < this.radius; ++i) {
                    double partangle = (angle+(90*j)*0.10);
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;
                    worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, posIn.getX()+partangle-coordcenter,posIn.getY(),posIn.getZ()+partangle-coordcenter,d0,d1,d2);
                }
            }
        }
    }

}
