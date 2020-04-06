package com.alexincube.allyouneed.blocks.blockbreaker;

import com.alexincube.allyouneed.setup.ModTileEntityTypes;
import com.alexincube.allyouneed.setup.Networking;
import com.alexincube.allyouneed.setup.PacketChangeRedstoneControl;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class blockbreaker extends DirectionalBlock {

    public static final BooleanProperty REDSTONE_SIGNAL = BooleanProperty.create("redstone_signal");
    
    public blockbreaker(){
        super(Properties.create(Material.IRON)
                        .hardnessAndResistance(2.0f)
                        .harvestLevel(2)
                        .harvestTool(ToolType.PICKAXE)
                        .sound(SoundType.METAL)
        );
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(REDSTONE_SIGNAL, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(REDSTONE_SIGNAL);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite().getOpposite()).with(REDSTONE_SIGNAL, Boolean.valueOf(context.getWorld().isBlockPowered(context.getPos())));
    }

    public boolean hasTileEntity(final BlockState state) {
        return true;
    }


    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return ModTileEntityTypes.block_breaker_tile.get().create();
    }

    public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof blockbreakertile) {
                final ItemStackHandler inventory = ((blockbreakertile) tileEntity).inventory;
                for (int slot = 0; slot < inventory.getSlots(); ++slot)
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(slot));
            }
        }
        super.onReplaced(oldState, worldIn, pos, newState, isMoving);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            boolean flag = state.get(REDSTONE_SIGNAL);
            if (flag != worldIn.isBlockPowered(pos)) {
                if (flag) {
                    worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
                } else {
                    worldIn.setBlockState(pos, state.cycle(REDSTONE_SIGNAL), 2);
                }
            }

        }
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(REDSTONE_SIGNAL) && !worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, state.cycle(REDSTONE_SIGNAL), 2);
        }
    }

    public ActionResultType onBlockActivated(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            final TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof blockbreakertile)
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
        }
        return ActionResultType.SUCCESS;
    }

}
