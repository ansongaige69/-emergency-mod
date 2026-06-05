package com.emergency.emergencymod.init;

import com.emergency.emergencymod.EmergencyMod;
import com.emergency.emergencymod.block.EmergencyButtonBlock;
import com.emergency.emergencymod.block.EmergencyLightBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, EmergencyMod.MOD_ID);

    public static final RegistryObject<Block> EMERGENCY_BUTTON = BLOCKS.register("emergency_button",
        () -> new EmergencyButtonBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.0f, 6.0f)
            .sound(SoundType.METAL)
            .noOcclusion()));

    public static final RegistryObject<Block> EMERGENCY_LIGHT = BLOCKS.register("emergency_light",
        () -> new EmergencyLightBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(2.0f, 4.0f)
            .sound(SoundType.METAL)
            .lightLevel(state -> state.getValue(EmergencyLightBlock.LIT) ? 15 : 0)
            .noOcclusion()));
}
