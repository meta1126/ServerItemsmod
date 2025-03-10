package com.meta.serveritemsmod.items;

import com.meta.serveritemsmod.event.common.effects.ModEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulBallItem extends Item {
    public SoulBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            removeFracturedSoulEffect(player);
            itemstack.shrink(1); // アイテムを1つ消費
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void removeFracturedSoulEffect(Player player) {
        player.removeEffect(ModEffects.FRACTURED_SOUL.get());
    }
}

