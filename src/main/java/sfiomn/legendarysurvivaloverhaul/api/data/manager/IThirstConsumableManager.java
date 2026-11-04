package sfiomn.legendarysurvivaloverhaul.api.data.manager;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonThirstConsumable;

import java.util.List;

public interface IThirstConsumableManager
{
    List<JsonThirstConsumable> get(Identifier itemRegistryName);

    JsonThirstConsumable get(ItemStack itemStack);
}
