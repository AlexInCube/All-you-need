package com.alexincube.allyouneed.setup;

import com.alexincube.allyouneed.allyouneed;
import com.alexincube.allyouneed.blocks.block_breaker.blockbreakergui;
import com.alexincube.allyouneed.blocks.redstone_clock.redstoneclockgui;
import com.alexincube.allyouneed.blocks.trash_can.trashcangui;
import com.alexincube.allyouneed.blocks.wood_crate.woodcrategui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = allyouneed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber {

    private static final Logger LOGGER = LogManager.getLogger(allyouneed.MODID + " Client Mod Event Subscriber");

    /**
     * We need to register our renderers on the client because rendering code does not exist on the server
     * and trying to use it on a dedicated server will crash the game.
     * <p>
     * This method will be called by Forge when it is time for the mod to do its client-side setup
     * This method will always be called after the Registry events.
     * This means that all Blocks, Items, TileEntityTypes, etc. will all have been registered already
     */
    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

        // Register ContainerType Screens
        // ScreenManager.registerFactory is not safe to call during parallel mod loading so we queue it to run later
        DeferredWorkQueue.runLater(() -> {
            ScreenManager.registerFactory(ModContainerTypes.WOOD_CRATE_CONTAINER.get(), woodcrategui::new);
            ScreenManager.registerFactory(ModContainerTypes.BLOCK_BREAKER_CONTAINER.get(), blockbreakergui::new);
            ScreenManager.registerFactory(ModContainerTypes.TRASH_CAN_CONTAINER.get(), trashcangui::new);
            ScreenManager.registerFactory(ModContainerTypes.REDSTONE_CLOCK_CONTAINER.get(), redstoneclockgui::new);
            LOGGER.debug("Registered ContainerType Screens");
        });

    }

}
