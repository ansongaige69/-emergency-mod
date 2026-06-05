package com.emergency.emergencymod.block;

import com.emergency.emergencymod.blockentity.EmergencyButtonBlockEntity;
import com.emergency.emergencymod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class EmergencyButtonBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 3, 13);

    public EmergencyButtonBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                  Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        boolean nowPowered = !state.getValue(POWERED);
        level.setBlock(pos, state.setValue(POWERED, nowPowered), 3);

        // Notify block entity to start/stop alarm
        if (level.getBlockEntity(pos) instanceof EmergencyButtonBlockEntity be) {
            be.setActive(nowPowered, level, pos);
        }

        // Update all emergency lights in 32-block radius
        updateNearbyLights(level, pos, nowPowered);

        player.sendSystemMessage(Component.literal(nowPowered
            ? "§c§l⚠ EMERGENCY ACTIVATED ⚠"
            : "§a§l✓ Emergency Deactivated"));

        return InteractionResult.CONSUME;
    }

    private void updateNearbyLights(Level level, BlockPos buttonPos, boolean active) {
        int range = 32;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos checkPos = buttonPos.offset(x, y, z);
                    BlockState bs = level.getBlockState(checkPos);
                    if (bs.getBlock() instanceof EmergencyLightBlock) {
                        level.setBlock(checkPos, bs.setValue(EmergencyLightBlock.LIT, active), 3);
                        if (!active) {
                            // Force light update when turning off
                            level.setBlock(checkPos, bs.setValue(EmergencyLightBlock.LIT, false), 3);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EmergencyButtonBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                    BlockEntityType<T> type) {
        return level.isClientSide ? null :
            createTickerHelper(type, ModBlockEntities.EMERGENCY_BUTTON_BE.get(),
                EmergencyButtonBlockEntity::serverTick);
    }
}
