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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
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
            assert skin != null;
            if (skin.tooltipName != null) {
                tooltip.add(Text.literal(Text.translatable(skin.tooltipName).getString()).setStyle(Style.EMPTY.withColor(skin.color)));
            }
            if (skin.lore != null) {
                if(Screen.hasShiftDown()){
                    MutableText translatable = Text.translatable(skin.lore);
                    String[] var8 = translatable.getString().split("\n");

                    for (String line : var8) {
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

        Skin(int color , @Nullable String tooltipName, @Nullable String lore) {
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

            for (Skin skin : var1) {
                if (skin.getName().equalsIgnoreCase(name)) {
                    return skin;
                }
            }

            return null;
        }

    }

}
