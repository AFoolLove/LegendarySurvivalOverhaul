package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonHealingConsumable;

public interface IHealingConsumableManager
{
    JsonHealingConsumable get(Identifier itemRegistryName);
}
