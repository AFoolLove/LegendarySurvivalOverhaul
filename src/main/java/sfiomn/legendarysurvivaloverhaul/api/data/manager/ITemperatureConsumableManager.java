package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureConsumable;

import java.util.List;

public interface ITemperatureConsumableManager
{
    List<JsonTemperatureConsumable> get(Identifier consumableRegistryName);
}
