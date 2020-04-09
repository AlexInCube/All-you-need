package com.alexincube.allyouneed.items;

import com.alexincube.allyouneed.setup.ModItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class portable_nether_portal extends Item{
    public portable_nether_portal(){
        super(new Properties()
                .maxStackSize(1)
                .group(ModItemGroups.MOD_ITEM_GROUP));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);//trigger item use

        //new SPlaySoundPacket(new ResourceLocation("block.portal.trigger"), SoundCategory.BLOCKS,playerIn.getPositionVec(),1,1);
        if (worldIn.isRemote()) {
            playerIn.playSound(SoundEvents.BLOCK_PORTAL_TRIGGER, 0.5F, 1);
            playerIn.sendMessage(new StringTextComponent("Starting moving to another dimension"));

        }

        return ActionResult.resultPass(itemstack);
    }
    @Override
    public int getUseDuration(ItemStack stack) {
        return 80;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote) {
            if (entityLiving.world.dimension.isNether()) {
                //playerIn.setPortal(playerIn.getPosition());
                entityLiving.changeDimension(DimensionType.OVERWORLD);
            }else{
                //playerIn.setPortal(playerIn.getPosition());
                entityLiving.changeDimension(DimensionType.THE_NETHER);
            }
        }
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        //new SStopSoundPacket(new ResourceLocation("block.portal.ambient"),SoundCategory.BLOCKS);
    }



    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }


}
