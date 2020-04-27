package com.alexincube.allyouneed.guithings;


import com.alexincube.allyouneed.packets.IRedstoneControlChange;
import com.alexincube.allyouneed.packets.PacketChangeRedstoneControl;
import com.alexincube.allyouneed.setup.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


public class GuiButtonRedstoneControl extends Button {

    private Container playercontainer;

    public GuiButtonRedstoneControl(int widthIn, int heightIn, Container container, IPressable onPress) {
        super(widthIn, heightIn, 20,20, "", onPress);
        playercontainer = container;
    }

    @Override
    public void onPress() {
        super.onPress();
        Networking.INSTANCE.sendToServer(new PacketChangeRedstoneControl(this.playercontainer.windowId));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX,mouseY,partialTicks);
        if (((IRedstoneControlChange)playercontainer).getRedstoneControl()==0) {
            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(new ItemStack(Items.GUNPOWDER),this.x+2,this.y+2);
        }else{
            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(new ItemStack(Items.REDSTONE),this.x+2,this.y+2);
        }
    }
}
