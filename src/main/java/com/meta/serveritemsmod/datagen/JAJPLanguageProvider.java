package com.meta.serveritemsmod.datagen;

import com.meta.serveritemsmod.ServerItemsMod;
import com.meta.serveritemsmod.event.common.effects.ModEffects;
import com.meta.serveritemsmod.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

public class JAJPLanguageProvider extends LanguageProvider {
    public JAJPLanguageProvider(PackOutput output) {
        super(output, ServerItemsMod.MODID, Locale.JAPAN.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.NOMALGATETICKET, "ノーマルゲートチケット");
        addItem(ModItems.NOMALHELPTICKET, "ノーマルヘルプチケット");
        addItem(ModItems.RAERGATETICKET ,"レアゲートチケット");
        addItem(ModItems.SUPERHELPTICKET ,"スーパヘルプチケット");
        addItem(ModItems.SUPERRAERGATETICKET ,"スーパレアゲートチケット");
        addItem(ModItems.ULTRARAERGATETICKET ,"ウルトラレアゲートチケット");
        addItem(ModItems.SOULBALL,"ソウルボール");
        addEffect(ModEffects.FRACTURED_SOUL, "ひび割れた魂");

    }
}
