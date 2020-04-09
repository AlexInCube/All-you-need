package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID(){return ID++;}

    public static void registerMessages(){
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(allyouneed.MODID,"allyouneed"),()->"1.0",s -> true, s -> true);

        INSTANCE.registerMessage(nextID(),
                PacketChangeRedstoneControl.class,
                PacketChangeRedstoneControl::toBytes,
                PacketChangeRedstoneControl::new,
                PacketChangeRedstoneControl::handle);
    }
}