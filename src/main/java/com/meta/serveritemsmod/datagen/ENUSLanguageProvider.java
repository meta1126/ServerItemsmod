package com.meta.serveritemsmod.datagen;

import com.meta.serveritemsmod.ServerItemsMod;
import com.meta.serveritemsmod.event.common.effects.ModEffects;
import com.meta.serveritemsmod.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class ENUSLanguageProvider extends LanguageProvider {
    public ENUSLanguageProvider(PackOutput output) {
        super(output, ServerItemsMod.MODID, Locale.US.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.NOMALGATETICKET, "Nomal GateTicket");
        addItem(ModItems.NOMALHELPTICKET, "Nomal HelpTicket");
        addItem(ModItems.RAERGATETICKET ,"Raer GateTicket");
        addItem(ModItems.SUPERHELPTICKET ,"Super HelpTicket");
        addItem(ModItems.SUPERRAERGATETICKET ,"Super Rear GateTicket");
        addItem(ModItems.ULTRARAERGATETICKET ,"Ultra Rear GateTicket");
        addItem(ModItems.SOULBALL,"Soul Ball");
        addEffect(ModEffects.FRACTURED_SOUL, "Fractured Soul");


    }
}
