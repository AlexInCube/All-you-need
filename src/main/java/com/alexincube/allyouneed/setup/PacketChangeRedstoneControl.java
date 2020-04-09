package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.blocks.blockbreaker.blockbreakercontainer;
import com.alexincube.allyouneed.blocks.blockbreaker.blockbreakertile;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeRedstoneControl {
    private final int windowId;
    private final int rc;

    public PacketChangeRedstoneControl(PacketBuffer buf){
        windowId = buf.readInt();
        rc = buf.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(windowId) ;
        buf.writeInt(rc);
    }

    public PacketChangeRedstoneControl(int windowId, int rc){
        this.windowId = windowId;
        this.rc = rc;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Container playercont=ctx.get().getSender().openContainer;
            if (windowId == playercont.windowId){
                ((blockbreakercontainer)playercont).setRedstoneControl(rc);
            }

            });
        ctx.get().setPacketHandled(true);
    }
}
