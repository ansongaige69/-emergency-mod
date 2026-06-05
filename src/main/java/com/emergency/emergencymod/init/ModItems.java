package com.emergency.emergencymod.init;

import com.emergency.emergencymod.EmergencyMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, EmergencyMod.MOD_ID);

    public static final RegistryObject<Item> EMERGENCY_BUTTON_ITEM = ITEMS.register("emergency_button",
        () -> new BlockItem(ModBlocks.EMERGENCY_BUTTON.get(), new Item.Properties()));

    public static final RegistryObject<Item> EMERGENCY_LIGHT_ITEM = ITEMS.register("emergency_light",
        () -> new BlockItem(ModBlocks.EMERGENCY_LIGHT.get(), new Item.Properties()));
}
