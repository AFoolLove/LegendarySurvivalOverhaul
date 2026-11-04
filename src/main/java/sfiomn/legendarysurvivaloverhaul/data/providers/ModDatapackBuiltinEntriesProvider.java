package sfiomn.legendarysurvivaloverhaul.data.providers;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import sfiomn.legendarysurvivaloverhaul.api.ModDamageTypes;
import sfiomn.legendarysurvivaloverhaul.common.enchantments.ModEnchantments;
import sfiomn.legendarysurvivaloverhaul.common.level.gen.ModBiomeModifiers;
import sfiomn.legendarysurvivaloverhaul.common.level.gen.ModConfiguredFeatures;
import sfiomn.legendarysurvivaloverhaul.common.level.gen.ModPlacedFeatures;

/**
 * 26.3 splits datapack registries into the world layer (features, placed features, biome modifiers)
 * and the reloadable layer (damage types, enchantments); the providers are created by
 * {@code PackGenerator#createWorldRegistryObjects / createReloadableRegistryObjects}.
 */
public final class ModDatapackBuiltinEntriesProvider
{
    public static final RegistrySetBuilder WORLD_REGISTRIES = new RegistrySetBuilder()
            .add(Registries.FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public static final RegistrySetBuilder RELOADABLE_REGISTRIES = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);

    private ModDatapackBuiltinEntriesProvider()
    {
    }
}