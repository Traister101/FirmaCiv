package com.hyperdash.firmaciv.entity.FirmacivEntityRenderer;

import com.hyperdash.firmaciv.entity.custom.CompartmentEntity.AbstractCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.CompartmentEntity.ChestCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.CompartmentEntity.EmptyCompartmentEntity;
import com.hyperdash.firmaciv.entity.custom.CompartmentEntity.WorkbenchCompartmentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CompartmentRenderer extends EntityRenderer<AbstractCompartmentEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public CompartmentRenderer(EntityRendererProvider.Context pContext/*, ModelLayerLocation pLayer*/) {
        super(pContext);
        this.shadowRadius = 0.0F;
        this.blockRenderer = pContext.getBlockRenderDispatcher();
    }

    public void render(AbstractCompartmentEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

        if(!(pEntity instanceof EmptyCompartmentEntity)){

            BlockState blockstate = Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.NORTH);
            if(pEntity instanceof ChestCompartmentEntity){
                blockstate = Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.NORTH);
            }
            if(pEntity instanceof WorkbenchCompartmentEntity){
                blockstate = Blocks.CRAFTING_TABLE.defaultBlockState();
            }

            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180F - pEntityYaw));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180F));
            pPoseStack.scale(0.6F, 0.6F, 0.6F);
            pPoseStack.translate(-0.5F, 00F, -0.5F);

            this.renderCompartmentContents(pEntity, pPartialTicks, blockstate, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }

    }

    @Override
    public ResourceLocation getTextureLocation(AbstractCompartmentEntity pEntity) {
        return null;
    }

    protected void renderCompartmentContents(AbstractCompartmentEntity pEntity, float pPartialTicks, BlockState pState, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.blockRenderer.renderSingleBlock(pState, pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY);
    }
}