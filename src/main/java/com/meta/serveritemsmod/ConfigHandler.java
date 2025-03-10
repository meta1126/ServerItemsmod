package com.meta.serveritemsmod;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    public static final ForgeConfigSpec.IntValue ZOMBIFICATION_THRESHOLD;
    public static final ForgeConfigSpec.IntValue SKELETONIZATION_TIME;
    public static final ForgeConfigSpec.IntValue DECOMPOSITION_TIME;

    public static final ForgeConfigSpec CONFIG_SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        ZOMBIFICATION_THRESHOLD = builder
                .comment("ゾンビ化するまでの攻撃回数")
                .defineInRange("ZombificationThreshold", 5, 1, 50);

        SKELETONIZATION_TIME = builder
                .comment("スケルトン化するまでの時間（秒）")
                .defineInRange("SkeletonizationTime", 300, 10, 3600);

        DECOMPOSITION_TIME = builder
                .comment("完全消滅するまでの時間（秒）")
                .defineInRange("DecompositionTime", 600, 10, 7200);

        CONFIG_SPEC = builder.build();
    }
}
