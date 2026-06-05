package com.emergency.emergencymod;

import com.emergency.emergencymod.init.ModBlocks;
import com.emergency.emergencymod.init.ModBlockEntities;
import com.emergency.emergencymod.init.ModSounds;
import com.emergency.emergencymod.init.ModItems;
import com.emergency.emergencymod.init.ModCreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EmergencyMod.MOD_ID)
public class EmergencyMod {
    public static final String MOD_ID = "emergencymod";
    public static final Logger LOGGER = LogManager.getLogger();

    public EmergencyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModSounds.SOUNDS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
