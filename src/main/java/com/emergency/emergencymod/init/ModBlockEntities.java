package com.emergency.emergencymod.init;

import com.emergency.emergencymod.EmergencyMod;
import com.emergency.emergencymod.blockentity.EmergencyButtonBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EmergencyMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<EmergencyButtonBlockEntity>> EMERGENCY_BUTTON_BE =
        BLOCK_ENTITIES.register("emergency_button", () ->
            BlockEntityType.Builder.of(EmergencyButtonBlockEntity::new,
                ModBlocks.EMERGENCY_BUTTON.get()).build(null));
}
