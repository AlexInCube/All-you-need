package com.alexincube.allyouneed;

import com.alexincube.allyouneed.packets.IRedstoneControlChange;
import com.alexincube.allyouneed.packets.PacketChangeRedstoneControl;
import com.alexincube.allyouneed.setup.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class GuiButtonRedstoneControl extends Button {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(allyouneed.MODID, "textures/gui/container/buttons.png");
    public int redstonemode;

    public GuiButtonRedstoneControl(int widthIn, int heightIn,IPressable onPress) {
        super(widthIn, heightIn, 20,20, "", onPress);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX,mouseY,partialTicks);
        Minecraft.getInstance().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        if (redstonemode == 1) {
            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(new ItemStack(Items.GUNPOWDER),this.x+2,this.y+2);
        }else{
            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(new ItemStack(Items.REDSTONE),this.x+2,this.y+2);
        }
    }
}
