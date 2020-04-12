package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.block_breaker.PacketChangeRedstoneBlockBreaker;
import com.alexincube.allyouneed.blocks.redstone_clock.PacketChangeRedstoneClock;
import com.alexincube.allyouneed.blocks.redstone_clock.PacketChangeRedstoneTime;
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
                PacketChangeRedstoneBlockBreaker.class,
                PacketChangeRedstoneBlockBreaker::toBytes,
                PacketChangeRedstoneBlockBreaker::new,
                PacketChangeRedstoneBlockBreaker::handle);

        INSTANCE.registerMessage(nextID(),
                PacketChangeRedstoneTime.class,
                PacketChangeRedstoneTime::toBytes,
                PacketChangeRedstoneTime::new,
                PacketChangeRedstoneTime::handle);

        INSTANCE.registerMessage(nextID(),
                PacketChangeRedstoneClock.class,
                PacketChangeRedstoneClock::toBytes,
                PacketChangeRedstoneClock::new,
                PacketChangeRedstoneClock::handle);
    }
}
