package com.alexincube.allyouneed.blocks.crafting_station;

import com.alexincube.allyouneed.allyouneed;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class craftingstationgui extends ContainerScreen<craftingstationcontainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(allyouneed.MODID, "textures/gui/container/crafting_station_gui.png");

    public craftingstationgui(final craftingstationcontainer container, final PlayerInventory inventory, final ITextComponent title) {
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
        // The parameters are (x, y, u, v, width, height)

        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 48.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 118.0F, (float) (this.ySize - 93), 0x404040);
    }
}
