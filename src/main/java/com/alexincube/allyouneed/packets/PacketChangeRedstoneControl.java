package com.alexincube.allyouneed.packets;

import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeRedstoneControl {
    private final int windowId;

    public PacketChangeRedstoneControl(PacketBuffer buf){
        windowId = buf.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(windowId);
    }

    public PacketChangeRedstoneControl(int windowId){
        this.windowId = windowId;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Container playercont=ctx.get().getSender().openContainer;
            if (windowId == playercont.windowId){
                ((IRedstoneControlChange)playercont).redstonecontrolchange();
            }

            });
        ctx.get().setPacketHandled(true);
    }
}
