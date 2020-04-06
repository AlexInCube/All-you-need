package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.blocks.blockbreaker.blockbreakertile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeRedstoneControl {

    private final BlockPos blockpos;
    private final int rc;

    public PacketChangeRedstoneControl(PacketBuffer buf){
        blockpos = buf.readBlockPos();
        rc = buf.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeBlockPos(blockpos);
        buf.writeInt(rc);
    }

    public PacketChangeRedstoneControl(BlockPos blockpos, int rc){
        this.blockpos = blockpos;
        this.rc = rc;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            TileEntity te = ctx.get().getSender().getServerWorld().getTileEntity(blockpos).getTileEntity();
            ((blockbreakertile) te).set(0,rc);
            //System.out.println(((blockbreakertile) te).get(0));
            });
        ctx.get().setPacketHandled(true);
    }
}
