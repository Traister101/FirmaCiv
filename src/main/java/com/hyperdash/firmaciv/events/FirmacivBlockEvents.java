package com.hyperdash.firmaciv.events;

import com.hyperdash.firmaciv.FirmaCiv;
import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.custom.CanoeComponentBlock;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hyperdash.firmaciv.block.custom.CanoeComponentBlock.AXIS;
import static com.hyperdash.firmaciv.block.custom.CanoeComponentBlock.FACING;

@Mod.EventBusSubscriber(modid = FirmaCiv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FirmacivBlockEvents {

    private FirmacivBlockEvents() {}

    @SubscribeEvent
    public static void registerToolModificationEvents(BlockEvent.BlockToolModificationEvent event){
        if(event.getToolAction() == ToolActions.AXE_STRIP) {
            if(event.getState().is(BlockTags.LOGS)){
                if(event.getState().getValue(BlockStateProperties.AXIS).isHorizontal()){

                    Block thisBlock = event.getState().getBlock();
                    BlockPos thisBlockPos = event.getPos();
                    LevelAccessor world = event.getWorld();
                    Direction.Axis rotatedirs = event.getState().getValue(BlockStateProperties.AXIS);

                    if (CanoeComponentBlock.isValidCanoeShape(world, thisBlock, thisBlockPos, rotatedirs)) {
                        world.playSound(event.getPlayer(), thisBlockPos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

                        world.destroyBlock(thisBlockPos, false);

                        Block newblock = FirmacivBlocks.CANOE_COMPONENT_BLOCK.get();
                        newblock.defaultBlockState().setValue(AXIS, Direction.Axis.Z);

                        if(rotatedirs == Direction.Axis.X){
                            world.setBlock(thisBlockPos, newblock.defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(FACING, Direction.WEST), 2);
                        } else {
                            world.setBlock(thisBlockPos, newblock.defaultBlockState().setValue(AXIS, Direction.Axis.Z), 2);
                        }

                        CanoeComponentBlock.spawnCanoeWithAxe(event.getPlayer().getLevel(), thisBlockPos);


                    }

                }

            }

            //event.getState().getBlock().
        }


    }

}