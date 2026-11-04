package sfiomn.legendarysurvivaloverhaul.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.tags.ModItemTags;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider
{
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, LegendarySurvivalOverhaul.MOD_ID);
    }

    private static ResourceKey<Item> key(Item item)
    {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(ModItemTags.CANTEEN_ENCHANTABLE)
                .add(key(ItemRegistry.CANTEEN.get()), key(ItemRegistry.LARGE_CANTEEN.get()));
    }
}