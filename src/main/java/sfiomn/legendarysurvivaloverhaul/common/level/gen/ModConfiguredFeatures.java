package sfiomn.legendarysurvivaloverhaul.common.level.gen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.blocks.IceFernBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.SunFernBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.WaterPlantBlock;
import sfiomn.legendarysurvivaloverhaul.common.level.feature.DoubleBlockFeature;
import sfiomn.legendarysurvivaloverhaul.common.level.feature.GoldFernFeature;
import sfiomn.legendarysurvivaloverhaul.registry.BlockRegistry;

/**
 * 26.3: configured features are gone; a "configured feature" is simply a {@link Feature} instance
 * registered in {@link Registries#FEATURE} that carries its own configuration.
 */
public class ModConfiguredFeatures
{

    public static final ResourceKey<Feature> ICE_FERN_CONFIG_KEY = registerKey("ice_fern_config_feature");
    public static final ResourceKey<Feature> SUN_FERN_CONFIG_KEY = registerKey("sun_fern_config_feature");
    public static final ResourceKey<Feature> WATER_PLANT_CONFIG_KEY = registerKey("water_plant_config_feature");

    public static ResourceKey<Feature> registerKey(String name)
    {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, name));
    }

    public static void registerSimpleRandomPatchConfig(BootstrapContext<Feature> context, ResourceKey<Feature> resourceKey, BlockState blockState)
    {
        context.register(resourceKey, new GoldFernFeature(BlockStateProvider.of(blockState)));
    }

    public static void registerDoubleRandomPatchConfig(BootstrapContext<Feature> context, ResourceKey<Feature> resourceKey, BlockState blockState)
    {
        context.register(resourceKey, new DoubleBlockFeature(BlockStateProvider.of(blockState)));
    }

    public static void bootstrap(BootstrapContext<Feature> context)
    {
        registerSimpleRandomPatchConfig(context, ICE_FERN_CONFIG_KEY, BlockRegistry.ICE_FERN_CROP.get().defaultBlockState().setValue(IceFernBlock.AGE, IceFernBlock.MAX_AGE));
        registerSimpleRandomPatchConfig(context, SUN_FERN_CONFIG_KEY, BlockRegistry.SUN_FERN_CROP.get().defaultBlockState().setValue(SunFernBlock.AGE, SunFernBlock.MAX_AGE));
        registerDoubleRandomPatchConfig(context, WATER_PLANT_CONFIG_KEY, BlockRegistry.WATER_PLANT_CROP.get().defaultBlockState().setValue(WaterPlantBlock.AGE, WaterPlantBlock.MAX_AGE));
    }
}
