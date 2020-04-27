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

public class sprinklertop extends Block {

    public sprinklertop() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(0f)
                .harvestLevel(0)
                .sound(SoundType.STONE)
        );
    }
}
