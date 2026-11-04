package sfiomn.legendarysurvivaloverhaul.data;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.PackGenerator;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.data.providers.*;

import java.util.Set;

/**
 * 26.3 datagen entry point.
 *
 * <p>{@code GatherDataEvent} is now split into {@link GatherDataEvent.Client} and
 * {@link GatherDataEvent.Server}; the {@code ExistingFileHelper} is gone and datapack
 * registries are generated through {@link PackGenerator#createWorldRegistryObjects} /
 * {@link PackGenerator#createReloadableRegistryObjects}.</p>
 */
@EventBusSubscriber(modid = LegendarySurvivalOverhaul.MOD_ID)
public final class DataGenerators
{
    private DataGenerators()
    {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gatherServerData(GatherDataEvent.Server event)
    {
        PackGenerator packGenerator = event.getDefaultPackGenerator();

        // Datapack registries: world layer (features/placed features/biome modifiers) and
        // reloadable layer (damage types/enchantments).
        packGenerator.createWorldRegistryObjects(ModDatapackBuiltinEntriesProvider.WORLD_REGISTRIES,
                Set.of(LegendarySurvivalOverhaul.MOD_ID));
        packGenerator.createReloadableRegistryObjects(ModDatapackBuiltinEntriesProvider.RELOADABLE_REGISTRIES,
                Set.of(LegendarySurvivalOverhaul.MOD_ID));

        // Tags
        event.createBlockAndItemTags(
                (output, lookupProvider) -> new ModBlockTagProvider(output, lookupProvider),
                (output, lookupProvider, blockTags) -> new ModItemTagProvider(output, lookupProvider));
        event.createProvider(ModEntityTypesTagProvider::new);
        event.createProvider(ModDamageTypeTagsProvider::new);
        event.createProvider(ModEnchantmentTagProvider::new);

        // Custom JSON data (temperature / thirst / body damage)
        event.createProvider(ModTemperatureProvider::new);
        event.createProvider(MinecraftTemperatureProvider::new);
        event.createProvider(ModThirstProvider::new);
        event.createProvider(MinecraftThirstProvider::new);
        event.createProvider(ModBodyDamageProvider::new);
        event.createProvider(MinecraftBodyDamageProvider::new);
    }
}