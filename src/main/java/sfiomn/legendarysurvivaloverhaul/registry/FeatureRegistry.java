package sfiomn.legendarysurvivaloverhaul.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.level.feature.DoubleBlockFeature;
import sfiomn.legendarysurvivaloverhaul.common.level.feature.GoldFernFeature;


public class FeatureRegistry
{
    // 26.3: Registries.FEATURE_TYPE holds the MapCodec of each custom Feature implementation.
    // Feature instances themselves are registered as worldgen features (see ModConfiguredFeatures).
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURE_TYPES =
            DeferredRegister.create(Registries.FEATURE_TYPE, LegendarySurvivalOverhaul.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<DoubleBlockFeature>> DOUBLE_BLOCK =
            FEATURE_TYPES.register("double_block", () -> DoubleBlockFeature.CODEC);
    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<GoldFernFeature>> GOLD_FERN =
            FEATURE_TYPES.register("gold_fern", () -> GoldFernFeature.CODEC);

    public static void register(IEventBus eventBus)
    {
        FEATURE_TYPES.register(eventBus);
    }
}
