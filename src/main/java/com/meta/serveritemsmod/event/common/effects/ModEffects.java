package com.meta.serveritemsmod.event.common.effects;

import com.meta.serveritemsmod.ServerItemsMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ServerItemsMod.MODID);

    public static final RegistryObject<MobEffect> FRACTURED_SOUL = MOB_EFFECTS.register("fractured_soul",
            () -> new FracturedSoulEffect());
}
