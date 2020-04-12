package com.alexincube.allyouneed.blocks.redstone_clock;

import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeRedstoneTime {
    private final int windowId;
    private int rc;

    public PacketChangeRedstoneTime(PacketBuffer buf){
        windowId = buf.readInt();
        rc = buf.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(windowId) ;
        buf.writeInt(rc);
    }

    public PacketChangeRedstoneTime(int windowId, int rc){
        this.windowId = windowId;
        this.rc = rc;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Container playercont=ctx.get().getSender().openContainer;
            if (windowId == playercont.windowId){
                if (rc<10){rc=10;}
                ((redstoneclockcontainer)playercont).setRedstoneTotalTime(rc);
            }

            });
        ctx.get().setPacketHandled(true);
    }
}
