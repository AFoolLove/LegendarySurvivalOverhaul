package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureBlock;

import java.util.List;

public interface ITemperatureBlockManager
{
    List<JsonTemperatureBlock> get(Identifier blockRegistryName);
}
