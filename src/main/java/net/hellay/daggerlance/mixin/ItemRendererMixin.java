package net.hellay.daggerlance.mixin;

import net.hellay.daggerlance.DaggerLanceClient;
import net.hellay.daggerlance.innit.ModItems;
import net.hellay.daggerlance.item.DaggerLanceItem;
import net.hellay.daggerlance.util.SkinUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow public abstract ItemModels getModels();

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel renderHandheldModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
            for(Pair<Item, Pair<ModelIdentifier, ModelIdentifier>> pair : DaggerLanceClient.SKIN_MODELS){
                Item item = pair.getLeft();
                ModelIdentifier handheld = pair.getRight().getRight();
                ModelIdentifier inventory = pair.getRight().getLeft();
                if (stack.isOf(item) && Objects.equals(SkinUtil.getSkin(stack), inventory.toString().substring(0, inventory.toString().length() - 10))) {
                    if (!(renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND)) {
                        return this.getModels().getModelManager().getModel(handheld);
                    }
                    return this.getModels().getModelManager().getModel(inventory);
                }
            }
    return value;
    }
}
