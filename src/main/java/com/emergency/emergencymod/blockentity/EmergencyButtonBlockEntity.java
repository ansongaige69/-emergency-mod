package com.emergency.emergencymod.blockentity;

import com.emergency.emergencymod.block.EmergencyLightBlock;
import com.emergency.emergencymod.init.ModBlockEntities;
import com.emergency.emergencymod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class EmergencyButtonBlockEntity extends BlockEntity {
    private boolean active = false;
    private int soundTick = 0;
    private int strobeTick = 0;
    private boolean strobeState = false;

    // Sound repeats every N ticks (alarm sound is ~2s = 40 ticks)
    private static final int SOUND_INTERVAL = 38;
    // Strobe toggles every 10 ticks (0.5s on, 0.5s off)
    private static final int STROBE_INTERVAL = 10;

    public EmergencyButtonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EMERGENCY_BUTTON_BE.get(), pos, state);
    }

    public void setActive(boolean active, Level level, BlockPos pos) {
        this.active = active;
        this.soundTick = 0;
        this.strobeTick = 0;
        this.strobeState = active;
        setChanged();

        if (active && level instanceof ServerLevel serverLevel) {
            // Play alarm immediately on activation
            serverLevel.playSound(null, pos,
                ModSounds.EMERGENCY_ALARM.get(),
                SoundSource.BLOCKS,
                4.0f, 1.0f);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                   EmergencyButtonBlockEntity be) {
        if (!be.active) return;

        // --- Sound looping ---
        be.soundTick++;
        if (be.soundTick >= SOUND_INTERVAL) {
            be.soundTick = 0;
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.playSound(null, pos,
                    ModSounds.EMERGENCY_ALARM.get(),
                    SoundSource.BLOCKS,
                    4.0f, 1.0f);
            }
        }

        // --- Strobe effect on nearby Emergency Lights ---
        be.strobeTick++;
        if (be.strobeTick >= STROBE_INTERVAL) {
            be.strobeTick = 0;
            be.strobeState = !be.strobeState;
            updateNearbyLightsStrobe(level, pos, be.strobeState);
        }
    }

    private static void updateNearbyLightsStrobe(Level level, BlockPos buttonPos, boolean lit) {
        int range = 32;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos checkPos = buttonPos.offset(x, y, z);
                    BlockState bs = level.getBlockState(checkPos);
                    if (bs.getBlock() instanceof EmergencyLightBlock) {
                        level.setBlock(checkPos, bs.setValue(EmergencyLightBlock.LIT, lit), 2);
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        active = tag.getBoolean("active");
        soundTick = tag.getInt("soundTick");
        strobeTick = tag.getInt("strobeTick");
        strobeState = tag.getBoolean("strobeState");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("active", active);
        tag.putInt("soundTick", soundTick);
        tag.putInt("strobeTick", strobeTick);
        tag.putBoolean("strobeState", strobeState);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
