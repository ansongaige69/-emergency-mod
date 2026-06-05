package com.emergency.emergencymod.init;

import com.emergency.emergencymod.EmergencyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EmergencyMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EMERGENCY_TAB = CREATIVE_TABS.register("emergency_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.emergencymod.emergency_tab"))
            .icon(() -> new ItemStack(ModItems.EMERGENCY_BUTTON_ITEM.get()))
            .displayItems((params, output) -> {
                output.accept(ModItems.EMERGENCY_BUTTON_ITEM.get());
                output.accept(ModItems.EMERGENCY_LIGHT_ITEM.get());
            })
            .build());
}
