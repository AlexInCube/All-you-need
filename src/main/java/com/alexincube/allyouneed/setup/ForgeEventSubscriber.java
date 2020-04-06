package com.alexincube.allyouneed.setup;



import com.alexincube.allyouneed.allyouneed;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

/**
 * Subscribe to events from the FORGE EventBus that should be handled on both PHYSICAL sides in this class
 *
 * @author Cadiboo
 */
@Mod.EventBusSubscriber(modid = allyouneed.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {
    /*
    @SubscribeEvent
    public static void checkPortalInMainHand(final LivingEvent.LivingUpdateEvent event)
    {
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            ItemStack heldItem = player.getHeldItem(Hand.MAIN_HAND);

            ItemStack portal = new ItemStack(ModItems.PORTABLE_NETHER_PORTAL.get());
            if (heldItem.getItem() == portal.getItem()){
                if (player.ticksExisted % 65 == 0) {
                    player.playSound(SoundEvents.BLOCK_PORTAL_AMBIENT, 1, 1);
                }
            }


        }
    }
    */


    @SubscribeEvent
    public static void onServerStarted(final FMLServerStartedEvent event)
    {
        System.out.println("Server Stared");
    }
}