package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonBodyPartResistance;

public interface IBodyResistanceItemManager
{
    JsonBodyPartResistance get(Identifier itemRegistryName);
}
