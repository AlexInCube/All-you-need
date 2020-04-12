package com.alexincube.allyouneed.blocks.redstone_clock;

import com.alexincube.allyouneed.blocks.block_breaker.blockbreakercontainer;
import com.alexincube.allyouneed.blocks.redstone_clock.redstoneclockcontainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeRedstoneClock {
    private final int windowId;
    private final int rc;

    public PacketChangeRedstoneClock(PacketBuffer buf){
        windowId = buf.readInt();
        rc = buf.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(windowId) ;
        buf.writeInt(rc);
    }

    public PacketChangeRedstoneClock(int windowId, int rc){
        this.windowId = windowId;
        this.rc = rc;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Container playercont=ctx.get().getSender().openContainer;
            if (windowId == playercont.windowId){
                ((redstoneclockcontainer)playercont).setRedstoneControl(rc);
            }

            });
        ctx.get().setPacketHandled(true);
    }
}
