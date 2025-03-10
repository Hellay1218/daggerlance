package net.hellay.daggerlance.mixin;

import net.hellay.daggerlance.innit.ModItems;
import net.hellay.daggerlance.item.DaggerLanceItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntity {

    protected WitherSkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow protected abstract void initEquipment(Random random, LocalDifficulty localDifficulty);

    @Inject(
            method = {"initEquipment"},
            at = {@At("TAIL")}
    )
    protected void equipDaggerLanceOnWitherSkeleton(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        if ((double)random.nextFloat() > 0.9) {
            //int i = random.nextInt(16);
//            if (i < 10) {
//            }
            ItemStack stack = new ItemStack(ModItems.DAGGERLANCE);
            stack.getOrCreateNbt().putString("skin" , DaggerLanceItem.Skin.values()[random.nextInt(DaggerLanceItem.Skin.values().length)].getName());
            this.equipStack(EquipmentSlot.MAINHAND, stack);
            this.setEquipmentDropChance(EquipmentSlot.MAINHAND , 0);
            //this.updateDropChances(EquipmentSlot.MAINHAND);
        }

    }

}
