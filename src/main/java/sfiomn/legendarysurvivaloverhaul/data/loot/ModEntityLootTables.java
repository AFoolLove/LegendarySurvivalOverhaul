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

public class ModEntityLootTables implements LootTableSubProvider
{

    public static List<Identifier> entityInjectedLootTables = List.of(
            Identifier.parse("entities/drowned")
    );

    private final LootTableSubProvider.Context context;

    public ModEntityLootTables(LootTableSubProvider.Context context)
    {
        this.context = context;
    }

    @Override
    public void run()
    {

        for (Identifier lootTable : entityInjectedLootTables)
        {
            context.accept(
                    ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "inject/" + lootTable.getPath())),
                    LootTable.lootTable().withPool(
                            LootPool.lootPool()
                                    .add(LootItem.lootTableItem(ItemRegistry.WATER_PURIFIER.get()).setWeight(1))
                                    .add(EmptyLootItem.emptyItem().setWeight(100))
                    ));
        }
    }
}
