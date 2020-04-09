package com.alexincube.allyouneed.items;

import com.alexincube.allyouneed.setup.ModBlocks;
import com.alexincube.allyouneed.setup.ModItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class angelblockplacer extends Item {
    public angelblockplacer(){
        super(new Properties()
                .maxStackSize(64).group(ModItemGroups.MOD_ITEM_GROUP));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        BlockPos blockPos = playerIn.getPosition().down();
        if (worldIn.isAirBlock(blockPos)) {
            worldIn.setBlockState(blockPos, ModBlocks.angel_block.get().getDefaultState());
            itemstack.shrink(1);
        }
        return ActionResult.resultPass(itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("description.allyouneed.angel_block_placer"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
