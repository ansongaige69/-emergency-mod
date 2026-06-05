package com.emergency.emergencymod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

public class EmergencyLightBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public EmergencyLightBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            // Spawn red dust particles for dramatic effect
            if (random.nextFloat() < 0.3f) {
                double px = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.8;
                double py = pos.getY() + 1.0;
                double pz = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.8;
                level.addParticle(ParticleTypes.FLAME,
                    px, py, pz,
                    0, 0.02, 0);
            }
        }
    }
}
