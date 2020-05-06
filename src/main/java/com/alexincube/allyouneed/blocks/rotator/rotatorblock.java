package com.alexincube.allyouneed.blocks.rotator;

import com.alexincube.allyouneed.blocks.MachineBlockBase;
import com.alexincube.allyouneed.blocks.block_breaker.blockbreakertile;
import com.alexincube.allyouneed.setup.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class rotatorblock extends HorizontalFaceBlock {
    public static final BooleanProperty REDSTONE_SIGNAL = BooleanProperty.create("redstone_signal");
    protected static final VoxelShape LEVER_NORTH_AABB = Block.makeCuboidShape(5.0D, 4.0D, 10.0D, 11.0D, 12.0D, 16.0D);
    protected static final VoxelShape LEVER_SOUTH_AABB = Block.makeCuboidShape(5.0D, 4.0D, 0.0D, 11.0D, 12.0D, 6.0D);
    protected static final VoxelShape LEVER_WEST_AABB = Block.makeCuboidShape(10.0D, 4.0D, 5.0D, 16.0D, 12.0D, 11.0D);
    protected static final VoxelShape LEVER_EAST_AABB = Block.makeCuboidShape(0.0D, 4.0D, 5.0D, 6.0D, 12.0D, 11.0D);
    protected static final VoxelShape FLOOR_Z_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 4.0D, 11.0D, 6.0D, 12.0D);
    protected static final VoxelShape FLOOR_X_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 5.0D, 12.0D, 6.0D, 11.0D);
    protected static final VoxelShape CEILING_Z_SHAPE = Block.makeCuboidShape(5.0D, 10.0D, 4.0D, 11.0D, 16.0D, 12.0D);
    protected static final VoxelShape CEILING_X_SHAPE = Block.makeCuboidShape(4.0D, 10.0D, 5.0D, 12.0D, 16.0D, 11.0D);

    public rotatorblock() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(3.5f)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE)
                .notSolid()
        );
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(REDSTONE_SIGNAL, Boolean.valueOf(false)).with(FACE, AttachFace.WALL));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch((AttachFace)state.get(FACE)) {
            case FLOOR:
                switch(state.get(HORIZONTAL_FACING).getAxis()) {
                    case X:
                        return FLOOR_X_SHAPE;
                    case Z:
                    default:
                        return FLOOR_Z_SHAPE;
                }
            case WALL:
                switch((Direction)state.get(HORIZONTAL_FACING)) {
                    case EAST:
                        return LEVER_EAST_AABB;
                    case WEST:
                        return LEVER_WEST_AABB;
                    case SOUTH:
                        return LEVER_SOUTH_AABB;
                    case NORTH:
                    default:
                        return LEVER_NORTH_AABB;
                }
            case CEILING:
            default:
                switch(state.get(HORIZONTAL_FACING).getAxis()) {
                    case X:
                        return CEILING_X_SHAPE;
                    case Z:
                    default:
                        return CEILING_Z_SHAPE;
                }
        }
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACE, HORIZONTAL_FACING, REDSTONE_SIGNAL);
    }
}