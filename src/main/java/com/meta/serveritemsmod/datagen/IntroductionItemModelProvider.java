package com.meta.serveritemsmod.datagen;

import com.meta.serveritemsmod.ServerItemsMod;
import com.meta.serveritemsmod.event.common.effects.ModEffects;
import com.meta.serveritemsmod.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class IntroductionItemModelProvider extends ItemModelProvider {
    public IntroductionItemModelProvider(PackOutput output,  ExistingFileHelper existingFileHelper) {
        super(output, ServerItemsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.NOMALHELPTICKET.get());
        basicItem(ModItems.SUPERHELPTICKET.get());
        basicItem(ModItems.NOMALGATETICKET.get());
        basicItem(ModItems.RAERGATETICKET.get());
        basicItem(ModItems.SUPERRAERGATETICKET.get());
        basicItem(ModItems.ULTRARAERGATETICKET.get());
        basicItem(ModItems.SOULBALL.get());

        withExistingParent(ForgeRegistries.MOB_EFFECTS.getKey(ModEffects.FRACTURED_SOUL.get()).getPath(),
                mcLoc("item/generated"))
                .texture("layer0", modLoc("mob_effect/fractured_soul"));
    }
}
