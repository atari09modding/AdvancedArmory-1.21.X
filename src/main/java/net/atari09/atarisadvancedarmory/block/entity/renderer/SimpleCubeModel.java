package net.atari09.atarisadvancedarmory.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atari09.atarisadvancedarmory.AtarisAdvancedArmory;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class SimpleCubeModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(AtarisAdvancedArmory.res("archerstable_liquid"), "main");

    private final ModelPart cube;

    public SimpleCubeModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.cube = root.getChild("liquid");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("liquid", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.5F, -5.5F, -2.5F, 5.0F, 4.0F, 5.0F),
                PartPose.ZERO);
        return LayerDefinition.create(mesh, 16, 16);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer,
                               int packedLight, int packedOverlay, int color) {
        cube.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}