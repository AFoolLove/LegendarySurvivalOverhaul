package sfiomn.legendarysurvivaloverhaul.common.integration.curios;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureResistance;
import sfiomn.legendarysurvivaloverhaul.api.data.manager.TemperatureDataManager;
import sfiomn.legendarysurvivaloverhaul.registry.AttributeRegistry;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

/**
 * Adds the temperature-related attribute modifiers of equipped curios (rings, necklaces, ...).
 * Curios 17 dropped the per-slot context from {@link CurioAttributeModifierEvent}, so the
 * modifiers are registered for any slot ("ANY"); the item can only be equipped in its valid
 * slots anyway (see the {@code curios:tags/item/*} tags).
 */
public class CuriosModifier
{
    public static final CurioAttributeBuilder HEATING_TEMPERATURE = new CurioAttributeBuilder(
            AttributeRegistry.HEATING_TEMPERATURE.getDelegate(),
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "heating_temperature"));
    public static final CurioAttributeBuilder COOLING_TEMPERATURE = new CurioAttributeBuilder(
            AttributeRegistry.COOLING_TEMPERATURE.getDelegate(),
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "cooling_temperature"));
    public static final CurioAttributeBuilder HEAT_RESISTANCE = new CurioAttributeBuilder(
            AttributeRegistry.HEAT_RESISTANCE.getDelegate(),
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "heat_resistance"));
    public static final CurioAttributeBuilder COLD_RESISTANCE = new CurioAttributeBuilder(
            AttributeRegistry.COLD_RESISTANCE.getDelegate(),
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "cold_resistance"));
    public static final CurioAttributeBuilder THERMAL_RESISTANCE = new CurioAttributeBuilder(
            AttributeRegistry.THERMAL_RESISTANCE.getDelegate(),
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "thermal_resistance"));

    public CuriosModifier()
    {
    }

    public static void addAttribute(CurioAttributeModifierEvent event)
    {
        if (!LegendarySurvivalOverhaul.curiosLoaded)
            return;

        Identifier itemRegistryName = BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem());
        if (itemRegistryName == null)
            return;

        JsonTemperatureResistance tempConfig = TemperatureDataManager.getItem(itemRegistryName);
        if (tempConfig == null)
            return;

        Identifier base = itemRegistryName;
        HEATING_TEMPERATURE.addModifier(event, Identifier.fromNamespaceAndPath(base.getNamespace(), base.getPath() + "_heating_temperature"), Math.max(tempConfig.temperature, 0));
        COOLING_TEMPERATURE.addModifier(event, Identifier.fromNamespaceAndPath(base.getNamespace(), base.getPath() + "_cooling_temperature"), Math.min(tempConfig.temperature, 0));
        HEAT_RESISTANCE.addModifier(event, Identifier.fromNamespaceAndPath(base.getNamespace(), base.getPath() + "_heat_resistance"), tempConfig.heatResistance);
        COLD_RESISTANCE.addModifier(event, Identifier.fromNamespaceAndPath(base.getNamespace(), base.getPath() + "_cold_resistance"), tempConfig.coldResistance);
        THERMAL_RESISTANCE.addModifier(event, Identifier.fromNamespaceAndPath(base.getNamespace(), base.getPath() + "_thermal_resistance"), tempConfig.thermalResistance);
    }
}