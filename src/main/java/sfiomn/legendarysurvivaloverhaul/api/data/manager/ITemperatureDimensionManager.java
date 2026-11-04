package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureDimension;

public interface ITemperatureDimensionManager
{
    JsonTemperatureDimension get(Identifier dimensionRegistryName);
}
