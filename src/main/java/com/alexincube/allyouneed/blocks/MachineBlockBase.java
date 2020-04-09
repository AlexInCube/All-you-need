package com.alexincube.allyouneed.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public abstract class MachineBlockBase extends DirectionalBlock {
    public static final BooleanProperty REDSTONE_SIGNAL = BooleanProperty.create("redstone_signal");

    public MachineBlockBase(){
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

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(REDSTONE_SIGNAL) && !worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, state.cycle(REDSTONE_SIGNAL), 2);
        }
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
}
