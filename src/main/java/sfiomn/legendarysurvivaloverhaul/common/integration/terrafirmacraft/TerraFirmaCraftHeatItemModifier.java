package sfiomn.legendarysurvivaloverhaul.common.integration.terrafirmacraft;

import net.minecraft.world.item.ItemStack;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureResistance;
import sfiomn.legendarysurvivaloverhaul.api.temperature.AttributeModifierBase;

/**
 * Vanilla-only stub of the TerraFirmaCraft item-heat modifier for the 26.3 port.
 */
public class TerraFirmaCraftHeatItemModifier extends AttributeModifierBase
{
    @Override
    public JsonTemperatureResistance getItemAttributes(ItemStack stack)
    {
        return new JsonTemperatureResistance();
    }
}
