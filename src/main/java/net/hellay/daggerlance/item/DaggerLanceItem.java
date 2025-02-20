package net.hellay.daggerlance.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.hellay.daggerlance.util.SkinUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static net.hellay.daggerlance.DaggerLance.MOD_ID;

public class DaggerLanceItem extends SwordItem {
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("7ce0388d-edbe-4059-9cfd-a6ac374eb725");
    protected static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("d3848306-a3c0-4762-a3ae-71730be458f3");
    private final float attackDamage;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    public DaggerLanceItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float reach, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.attackDamage = (float)attackDamage + toolMaterial.getAttackDamage();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", (double)reach, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Weapon modifier", (double)reach, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

//    @Override
//    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
//        if (shouldDealAdditionalDamage((PlayerEntity)attacker)) {
//            target.damage(target.getRecentDamageSource() , getBonusAttackDamage(target , this.attackDamage , target.getRecentDamageSource()));
//            float f = attacker.getYaw();
//            float g = attacker.getPitch();
//            float h = -MathHelper.sin(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
//            float l = MathHelper.cos(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
//            float m = MathHelper.sqrt(h * h + l * l);
//            float n = getBonusAttackDamage(target , this.attackDamage , target.getRecentDamageSource());
//            h *= n / m;
//            l *= n / m;
//            target.addVelocity((double) h, (double) target.getVelocity().y, (double) l);
//            ServerWorld serverWorld = (ServerWorld)attacker.getWorld();
//            //attacker.setVelocity(attacker.getVelocity().withAxis(Direction.Axis.Y, 0.009999999776482582));
//            ServerPlayerEntity serverPlayerEntity;
//            if (attacker instanceof ServerPlayerEntity) {
//                serverPlayerEntity = (ServerPlayerEntity)attacker;
//                serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
//            }
//
//            if (target.isOnGround()) {
//                if (attacker instanceof ServerPlayerEntity) {
//                    serverPlayerEntity = (ServerPlayerEntity) attacker;
//                }
//            }
//        }
//
//        return true;
//    }

//    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
//        Entity attacker = damageSource.getSource();
//        if (attacker instanceof LivingEntity livingEntity) {
//            if (!shouldDealAdditionalDamage(((PlayerEntity) livingEntity))) {
//                return 0.0F;
//            } else {
//                float f = 3.0F;
//                float g = 8.0F;
//                float h = (float)(livingEntity.getVelocity().x + livingEntity.getVelocity().z)/2.0F;
//                float i;
//                if (h <= 3.0F) {
//                    i = 4.0F * h;
//                } else if (h <= 8.0F) {
//                    i = 12.0F + 2.0F * (h - 3.0F);
//                } else {
//                    i = 22.0F + h - 8.0F;
//                }
//
//                World world = livingEntity.getWorld();
//                System.out.println(i);
//                if (world instanceof ServerWorld) {
//                    ServerWorld serverWorld = (ServerWorld)world;
//                    return i;
//                } else {
//                    return i;
//                }
//            }
//        } else {
//            return 0.0F;
//        }
//    }

    //    @Override
//    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
//        if (user instanceof PlayerEntity playerEntity) {
//            float f = playerEntity.getYaw();
//            float g = playerEntity.getPitch();
//            float h = -MathHelper.sin(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
//            float l = MathHelper.cos(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
//            float m = MathHelper.sqrt(h * h + l * l);
//            float n = 3.0F;
//            h *= n / m;
//            l *= n / m;
//            playerEntity.addVelocity((double) h, (double) playerEntity.getVelocity().y, (double) l);
//            playerEntity.useRiptide(20);
//        }
//        super.onStoppedUsing(stack, world, user, remainingUseTicks);
//    }
//
//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        ItemStack itemStack = user.getStackInHand(hand);
//        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
//            return TypedActionResult.fail(itemStack);
//        } else {
//            user.setCurrentHand(hand);
//            return TypedActionResult.consume(itemStack);
//        }
//    }

//    public static boolean shouldDealAdditionalDamage(PlayerEntity attacker){
//        System.out.println((MathHelper.abs((float) attacker.getVelocity().x)  + (MathHelper.abs((float) attacker.getVelocity().z)) > 0.5F));
//        return (float)(attacker.getVelocity().x + attacker.getVelocity().z)/2 > 1.0F && !attacker.isFallFlying();
//    }

//    private static void knockbackNearbyEntities(World world, Entity attacker, Entity attacked) {
//        world.syncWorldEvent(2013, attacked.getSteppingPos(), 750);
//        world.getEntitiesByClass(LivingEntity.class, attacked.getBoundingBox().expand(3.5), getKnockbackPredicate(attacker, attacked)).forEach((entity) -> {
//            Vec3d vec3d = entity.getPos().subtract(attacked.getPos());
//            double d = getKnockback(attacker, entity, vec3d);
//            Vec3d vec3d2 = vec3d.normalize().multiply(d);
//            if (d > 0.0) {
//                entity.addVelocity(vec3d2.x, 0.699999988079071, vec3d2.z);
//                if (entity instanceof ServerPlayerEntity) {
//                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
//                    serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
//                }
//            }
//
//        });
//    }
//
//    private static double getKnockback(Entity attacker, LivingEntity attacked, Vec3d distance) {
//        return (3.5 - distance.length()) * 0.699999988079071 * (double)(attacker.fallDistance > 5.0F ? 2 : 1) * (1.0 - attacked.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
//    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState state =  context.getWorld().getBlockState(context.getBlockPos());

        if (state.isOf(Blocks.ANVIL) || state.isOf(Blocks.CHIPPED_ANVIL) || state.isOf(Blocks.DAMAGED_ANVIL) || state.isOf(Blocks.SMITHING_TABLE)){
            if(!context.getWorld().isClient) {
                SkinUtil.setSkin(context.getStack(), Objects.requireNonNull(DaggerLanceItem.Skin.getNext(Objects.requireNonNull(DaggerLanceItem.Skin.fromString(SkinUtil.getSkin(context.getStack()))))).getName());
                context.getPlayer().playSound(SoundEvents.BLOCK_SMITHING_TABLE_USE, SoundCategory.PLAYERS, 0.5f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Skin.fromString(SkinUtil.getSkin(stack)) != null){
            Skin skin = Skin.fromString(SkinUtil.getSkin(stack));
            if (skin.tooltipName != null) {
                tooltip.add(Text.literal(Text.translatable(skin.tooltipName).getString()).setStyle(Style.EMPTY.withColor(skin.color)));
            }
            if (skin.lore != null) {
                if(Screen.hasShiftDown()){
                    MutableText translatable = Text.translatable(skin.lore);
                    String[] var8 = translatable.getString().split("\n");
                    int var9 = var8.length;

                    for(int var10 = 0; var10 < var9; ++var10) {
                        String line = var8[var10];
                        tooltip.add(Text.literal(line).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
                    }
                }else {
                    tooltip.add(Text.literal(Text.translatable("tooltip.daggerlance.hidden").getString()).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
                }

            }
            super.appendTooltip(stack, world, tooltip, context);
        }
    }
    public enum Skin {
        DEFAULT(-1 ,null, null),
        GOLD(15517825 , "tooltip.daggerlance.gold", null),
        VANA(10510945 , "tooltip.daggerlance.vana" , "tooltip.daggerlance.vana_lore"),
        EMPATHY(16134261 , "tooltip.daggerlance.empathy" , null),
        MOON(9672889 , "tooltip.daggerlance.moon" , null),
        JADE(1731154 , "tooltip.daggerlance.jade" , null);

        public final int color;
        public final @Nullable String tooltipName;
        public final @Nullable String lore;

        private Skin(int color , String tooltipName, String lore) {
            this.color = color;
            this.lore = lore;
            this.tooltipName = tooltipName;
        }

        public String getName() {
            if (this.name().toLowerCase(Locale.ROOT).equals("default")){
                return MOD_ID + ":" + "daggerlance";
            }
            return MOD_ID + ":" + this.name().toLowerCase(Locale.ROOT) + "_daggerlance";
        }

        public static @Nullable Skin getNext(Skin skin) {
            Skin[] values = values();
            return values[(skin.ordinal() + 1) % values.length];
        }

        public static @Nullable Skin fromString(String name) {
            Skin[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Skin skin = var1[var3];
                if (skin.getName().equalsIgnoreCase(name)) {
                    return skin;
                }
            }

            return null;
        }

    }

}
