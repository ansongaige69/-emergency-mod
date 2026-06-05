package com.emergency.emergencymod.init;

import com.emergency.emergencymod.EmergencyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EmergencyMod.MOD_ID);

    public static final RegistryObject<SoundEvent> EMERGENCY_ALARM =
        SOUNDS.register("emergency_alarm", () ->
            SoundEvent.createVariableRangeEvent(new ResourceLocation(EmergencyMod.MOD_ID, "emergency_alarm")));
}
