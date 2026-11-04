package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureFuelItem;

public interface ITemperatureFuelItemManager
{
    JsonTemperatureFuelItem get(Identifier itemRegistryName);
}
