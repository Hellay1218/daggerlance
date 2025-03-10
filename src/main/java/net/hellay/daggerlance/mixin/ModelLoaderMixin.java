package net.hellay.daggerlance.mixin;

import net.hellay.daggerlance.DaggerLanceClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelid);

    @Inject(method = "<init>" , at = @At(value = "INVOKE", target = "Ljava/util/Collection;forEach(Ljava/util/function/Consumer;)V" , shift= At.Shift.BEFORE))
    public void daggerlance$addHandheldModel(BlockColors blockColors , Profiler profiler , Map<Identifier , JsonUnbakedModel> jsonUnbakedModels , Map<Identifier , List<ModelLoader.SourceTrackedData>> blockStates , CallbackInfo ci){
        for(Pair<Item, Pair<ModelIdentifier, ModelIdentifier>> pair : DaggerLanceClient.SKIN_MODELS){
            this.addModel(pair.getRight().getRight());
            this.addModel(pair.getRight().getLeft());
        }

    }
}
