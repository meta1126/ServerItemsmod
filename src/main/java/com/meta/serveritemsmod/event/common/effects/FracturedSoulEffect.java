package com.meta.serveritemsmod.event.common.effects;

import com.meta.serveritemsmod.items.ModItems;
import com.meta.serveritemsmod.items.SoulBallItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public class FracturedSoulEffect extends MobEffect {
    public FracturedSoulEffect() {
        super(MobEffectCategory.HARMFUL, 0x800080); // 紫色
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 効果の適用（必要に応じて）
    }

    public boolean isRemovable(){
        return false;
    }

    public boolean canBeRemovedBy(LivingEntity entity, ItemStack item) {
        return item.getItem() instanceof SoulBallItem;
    }

    public static void updateEffect(LivingEntity entity, int newDuration, int newAmplifier) {
        MobEffectInstance existingEffect = entity.getEffect(ModEffects.FRACTURED_SOUL.get());
        if (existingEffect != null) {
            // 既存のエフェクトを新しい持続時間と強度で更新
            entity.addEffect(new MobEffectInstance(ModEffects.FRACTURED_SOUL.get(),
                    Math.max(existingEffect.getDuration(), newDuration),
                    Math.max(existingEffect.getAmplifier(), newAmplifier),
                    existingEffect.isAmbient(),
                    existingEffect.isVisible()));
        } else {
            // 新しいエフェクトを追加
            entity.addEffect(new MobEffectInstance(ModEffects.FRACTURED_SOUL.get(),
                    newDuration, newAmplifier));
        }
    }
}

