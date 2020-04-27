package com.alexincube.allyouneed.blocks.sprinkler;

import com.alexincube.allyouneed.setup.ModBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.client.model.data.EmptyModelData;

public class sprinklertilerenderer extends TileEntityRenderer<sprinklertile> {
    int angle = 0;
    int angle2 = angle;
    public sprinklertilerenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(sprinklertile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        boolean work = false;
        if (tileEntityIn.hasWorld()) {
            BlockState blockstate = tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos());
            if (blockstate.getBlock() instanceof sprinklerblock) {
                work = blockstate.get(sprinklerblock.WORK);
            }
        }
        matrixStackIn.push();
        matrixStackIn.translate(0, 0.62, 0);//matrix up
        //move matrix to center and rotate it
        matrixStackIn.translate(0.5,0,0.5);
        angle = (int) (tileEntityIn.getAngle());//((sprinklercontainer)tileEntityIn.cont).getAngle();

        if (work==true) {
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(angle+partialTicks*2));
            angle2 = (int) (angle+partialTicks*2);
        }else{
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(angle2));
        }
        matrixStackIn.translate(-0.5,0,-0.5);

        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        BlockState state = ModBlocks.sprinkler_top.get().getDefaultState();
        blockRenderer.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);

        matrixStackIn.pop();
    }

}
