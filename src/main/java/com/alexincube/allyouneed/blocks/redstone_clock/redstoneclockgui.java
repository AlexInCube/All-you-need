package com.alexincube.allyouneed.blocks.redstone_clock;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.packets.PacketChangeRedstoneTime;
import com.alexincube.allyouneed.setup.Networking;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class redstoneclockgui extends ContainerScreen<redstoneclockcontainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(allyouneed.MODID, "textures/gui/container/redstone_clock_gui.png");
    private Button applytime;
    private TextFieldWidget ticks;

    public redstoneclockgui(final redstoneclockcontainer container, final PlayerInventory inventory, final ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.xSize = 175;
        this.ySize = 86;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttons.clear();
        this.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        this.addButton(new Button( this.guiLeft+70, this.guiTop+50, 50, 20, I18n.format("button.allyouneed.apply"), (button) -> {
            boolean noletter=true;
            for(int i=0;i<this.ticks.getText().length();i++){
                if (!Character.isDigit(this.ticks.getText().charAt(i))){
                    noletter=false;
                }
            }
            if (noletter==true) {
                Networking.INSTANCE.sendToServer(new PacketChangeRedstoneTime(this.container.windowId, Integer.valueOf(this.ticks.getText())));
            }
        }));
        this.ticks = new TextFieldWidget(this.font,  this.guiLeft+70, this.guiTop+20, 50, 20, I18n.format("textfield.allyouneed.ticks"));
        this.ticks.setMaxStringLength(5);
        this.children.add(this.ticks);
    }


    @Override
    public void tick() {
        this.ticks.tick();
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
        // Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
        // The parameters are (x, y, u, v, width, height)
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        ticks.render(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // Copied from AbstractFurnaceScreen#drawGuiContainerForegroundLayer
        String s = this.title.getFormattedText();
        this.font.drawString(s, 8, 6.0F, 0x404040);
        this.font.drawString(this.container.getRedstoneCurrentTime()+"/"+this.container.getRedstoneTotalTime(), 8.0F, (float) (this.ySize - 50), 0x404040);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
    }
}
