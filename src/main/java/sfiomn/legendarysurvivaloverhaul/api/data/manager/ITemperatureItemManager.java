package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureResistance;

public interface ITemperatureItemManager
{
    JsonTemperatureResistance get(Identifier itemRegistryName);
}
