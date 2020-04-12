package com.alexincube.allyouneed.blocks.block_placer;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.packets.PacketChangeRedstoneControl;
import com.alexincube.allyouneed.setup.Networking;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


public class blockplacergui extends ContainerScreen<blockplacercontainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(allyouneed.MODID, "textures/gui/container/block_breaker_gui.png");

    public blockplacergui(final blockplacercontainer container, final PlayerInventory inventory, final ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int startX = this.guiLeft;
        int startY = this.guiTop;

        // Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
        //Some blit param namings
        //blit(int x, int y, int textureX, int textureY, int width, int height);
        //blit(int x, int y, TextureAtlasSprite icon, int width, int height);
        //blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
        //blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);
        //blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
        //innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);

        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String s = this.title.getFormattedText();
        int textWidth = font.getStringWidth(s);
        int centerX = (this.xSize / 2) - (textWidth / 2);

        this.font.drawString(s, centerX, 6.0F, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 93), 0x404040);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        if (this.container.getRedstoneControl() == 1) {
            this.blit(133, 35, 176, 0, 18, 18);//x,y,texturex,texturey,width,height
        }else{
            this.blit(133, 35, 194, 0, 18, 18);//x,y,texturex,texturey,width,height
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseX >= this.guiLeft+133 && mouseY >= this.guiTop+35 && mouseX < this.guiLeft+152 && mouseY < this.guiTop+51) {
            Networking.INSTANCE.sendToServer(new PacketChangeRedstoneControl(this.container.windowId));
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
