package sfiomn.legendarysurvivaloverhaul.common.level.gen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

import java.util.List;

public class ModPlacedFeatures
{
    public static final ResourceKey<PlacedFeature> ICE_FERN_PLACED_KEY = registerKey("ice_fern_placed");
    public static final ResourceKey<PlacedFeature> SUN_FERN_PLACED_KEY = registerKey("sun_fern_placed");
    public static final ResourceKey<PlacedFeature> WATER_PLANT_PLACED_KEY = registerKey("water_plant_placed");

    public static ResourceKey<PlacedFeature> registerKey(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, name));
    }

    /**
     * 26.3 moved the old {@code RandomPatchConfiguration} (count / xz-spread / y-spread) into placement
     * modifiers, so the patch behaviour is reproduced here with count + triangle offset.
     */
    public static List<PlacementModifier> worldSurfaceWithChance(int chanceOnceEvery, int patchCount, int xzSpread, int ySpread)
    {
        return List.of(
                RarityFilter.onAverageOnceEvery(chanceOnceEvery),
                CountPlacement.of(patchCount),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                OffsetPlacement.ofTriangle(xzSpread, ySpread),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable()),
                BiomeFilter.biome());
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context)
    {
        HolderGetter<Feature> features = context.lookup(Registries.FEATURE);

        PlacementUtils.register(context,
                ICE_FERN_PLACED_KEY,
                features.getOrThrow(ModConfiguredFeatures.ICE_FERN_CONFIG_KEY),
                worldSurfaceWithChance(3, 50, 7, 3));

        PlacementUtils.register(context,
                SUN_FERN_PLACED_KEY,
                features.getOrThrow(ModConfiguredFeatures.SUN_FERN_CONFIG_KEY),
                worldSurfaceWithChance(3, 50, 7, 3));

        PlacementUtils.register(context,
                WATER_PLANT_PLACED_KEY,
                features.getOrThrow(ModConfiguredFeatures.WATER_PLANT_CONFIG_KEY),
                worldSurfaceWithChance(5, 64, 6, 3));
    }

}
