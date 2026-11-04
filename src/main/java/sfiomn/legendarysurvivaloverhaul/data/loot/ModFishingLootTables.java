package sfiomn.legendarysurvivaloverhaul.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;

import java.util.Arrays;
import java.util.List;

public class ModFishingLootTables implements LootTableSubProvider
{

    public static List<Identifier> fishingInjectedLootTables = List.of(
            Identifier.parse("gameplay/fishing/treasure")
    );

    private final LootTableSubProvider.Context context;

    public ModFishingLootTables(LootTableSubProvider.Context context)
    {
        this.context = context;
    }

    @Override
    public void run()
    {

        for (Identifier lootTable : fishingInjectedLootTables)
        {
            context.accept(
                    ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "inject/" + lootTable.getPath())),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool()
                                    .add(LootItem.lootTableItem(ItemRegistry.SPONGE.get()).setWeight(20))
                                    .add(EmptyLootItem.emptyItem().setWeight(80))
                    ));
        }
    }
}
