package com.alexincube.allyouneed.blocks;

import com.alexincube.allyouneed.setup.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class angelblock extends Block {

    public angelblock() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(0f)
                .harvestLevel(0)
                .sound(SoundType.STONE)
        );
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        Item item = ModItems.ANGEL_BLOCK_PLACER.get();
        player.inventory.addItemStackToInventory(new ItemStack(item));
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
            entityIn.onLivingFall(fallDistance, 0.0F);
    }
}
