package sfiomn.legendarysurvivaloverhaul.data.providers;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.loot_modifiers.AdditionalLootTable;
import sfiomn.legendarysurvivaloverhaul.data.loot.ModBarteringLootTables;
import sfiomn.legendarysurvivaloverhaul.data.loot.ModChestLootTables;
import sfiomn.legendarysurvivaloverhaul.data.loot.ModEntityLootTables;
import sfiomn.legendarysurvivaloverhaul.data.loot.ModFishingLootTables;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider
{
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, LegendarySurvivalOverhaul.MOD_ID);
    }

    private static AdditionalLootTable inject(Identifier target)
    {
        LootItemCondition condition = LootTableIdCondition.builder(target).build();
        Identifier injectTable = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID,
                "inject/" + target.getPath());
        return new AdditionalLootTable(Optional.of(Holder.direct(condition)),
                IGlobalLootModifier.DEFAULT_PRIORITY, injectTable, false);
    }

    @Override
    protected void start()
    {
        for (ResourceKey<LootTable> lootTableKey : ModChestLootTables.chestInjectedLootTables.keySet())
        {
            this.add(lootTableKey.identifier().getPath(), inject(lootTableKey.identifier()));
        }
        for (Identifier lootTable : ModEntityLootTables.entityInjectedLootTables)
        {
            this.add(lootTable.getPath(), inject(lootTable));
        }
        for (Identifier lootTable : ModFishingLootTables.fishingInjectedLootTables)
        {
            this.add(lootTable.getPath(), inject(lootTable));
        }
        for (Identifier lootTable : ModBarteringLootTables.barteringInjectedLootTables)
        {
            this.add(lootTable.getPath(), inject(lootTable));
        }
    }
}