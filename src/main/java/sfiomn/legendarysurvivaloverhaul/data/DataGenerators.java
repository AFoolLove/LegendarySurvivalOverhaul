package sfiomn.legendarysurvivaloverhaul.data;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.ModDamageTypes;
import sfiomn.legendarysurvivaloverhaul.common.enchantments.ModEnchantments;
import sfiomn.legendarysurvivaloverhaul.common.level.gen.ModBiomeModifiers;
import sfiomn.legendarysurvivaloverhaul.common.level.gen.ModConfiguredFeatures;
import sfiomn.legendarysurvivaloverhaul.common.level.gen.ModPlacedFeatures;
import sfiomn.legendarysurvivaloverhaul.data.providers.LegendarySurvivalAdvancements;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import sfiomn.legendarysurvivaloverhaul.data.loot.*;
import sfiomn.legendarysurvivaloverhaul.data.providers.*;

/**
 * 26.3 datagen entry point.
 *
 * <ul>
 *   <li>{@link GatherDataRegistryEntriesEvent} (mod bus) collects datapack registry bootstraps:
 *       worldgen features, placed features, biome modifiers, damage types, enchantments,
 *       advancements, loot tables and recipes.</li>
 *   <li>{@link GatherDataEvent.Server} adds the regular data providers (tags, custom JSON data,
 *       global loot modifiers).</li>
 * </ul>
 */
@EventBusSubscriber(modid = LegendarySurvivalOverhaul.MOD_ID)
public final class DataGenerators
{
    private DataGenerators()
    {
    }

    @SubscribeEvent
    public static void gatherDataRegistries(GatherDataRegistryEntriesEvent event)
    {
        event.add(Registries.FEATURE, ModConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
                .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap)
                .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
                .advancement(LegendarySurvivalAdvancements::new)
                .recipe((recipes, advancements) -> new ModRecipeProvider(recipes, advancements))
                .lootTable(
                        new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(ModChestLootTables::new, LootContextParamSets.CHEST),
                        new LootTableProvider.SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY),
                        new LootTableProvider.SubProviderEntry(ModFishingLootTables::new, LootContextParamSets.FISHING),
                        new LootTableProvider.SubProviderEntry(ModBarteringLootTables::new, LootContextParamSets.PIGLIN_BARTER));
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event)
    {
        // blockstates + block/item models (26.3 model system: written as resources, item
        // definitions live in src/main/resources/assets/.../items)
        event.createProvider(ModModelProvider::new);
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gatherServerData(GatherDataEvent.Server event)
    {
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
        event.createProvider(ModGlobalLootModifierProvider::new);
        event.createProvider(ModBodyDamageProvider::new);
        event.createProvider(MinecraftBodyDamageProvider::new);
    }
}