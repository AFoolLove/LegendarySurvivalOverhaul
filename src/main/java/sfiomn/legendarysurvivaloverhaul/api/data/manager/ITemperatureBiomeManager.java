package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureBiomeOverride;

public interface ITemperatureBiomeManager
{
    JsonTemperatureBiomeOverride get(Identifier biomeRegistryName);
}
