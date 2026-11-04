package sfiomn.legendarysurvivaloverhaul.data.loot;

import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;
import sfiomn.legendarysurvivaloverhaul.common.blocks.IceFernBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.SunFernBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.WaterPlantBlock;
import sfiomn.legendarysurvivaloverhaul.registry.BlockRegistry;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider
{
    public ModBlockLootTables(LootTableSubProvider.Context output)
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
    }

    private static Holder<ContextIntProvider> between(int min, int max)
    {
        return Holder.direct(new UniformGenerator(ContextIntProviders.exactly(min), ContextIntProviders.exactly(max)));
    }

    private LootItemCondition.Builder ageIs(Block block, int age)
    {
        return MatchBlock.blockMatches(this.blocks, block,
                StatePropertiesPredicate.Builder.properties().hasProperty(WaterPlantBlock.AGE, age));
    }

    private LootItemCondition.Builder waterPlantAge(int age)
    {
        return MatchBlock.blockMatches(this.blocks, BlockRegistry.WATER_PLANT_CROP.get(),
                StatePropertiesPredicate.Builder.properties().hasProperty(WaterPlantBlock.AGE, age));
    }

    @Override
    protected void generate()
    {
        this.dropSelf(BlockRegistry.HEATER.get());
        this.dropOther(BlockRegistry.HEATER_TOP.get(), BlockRegistry.HEATER.get());
        this.dropSelf(BlockRegistry.COOLER.get());
        this.dropSelf(BlockRegistry.SEWING_TABLE.get());

        LootItemCondition.Builder maxAge = waterPlantAge(WaterPlantBlock.MAX_AGE);
        LootItemCondition.Builder below1 = waterPlantAge(WaterPlantBlock.MAX_AGE - 1);
        LootItemCondition.Builder below2 = waterPlantAge(WaterPlantBlock.MAX_AGE - 2);

        this.add(BlockRegistry.WATER_PLANT_CROP.get(),
                createCropDrops(
                        BlockRegistry.WATER_PLANT_CROP.get(),
                        ItemRegistry.WATER_PLANT_SEEDS.get(),
                        ItemRegistry.WATER_PLANT_SEEDS.get(),
                        maxAge
                )
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.WATER_PLANT_BAG.get()))
                                .apply(SetItemCountFunction.setCount(between(1, 2)))
                                .when(below2))
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.WATER_PLANT_BAG.get()))
                                .apply(SetItemCountFunction.setCount(between(1, 3)))
                                .when(below1))
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.WATER_PLANT_BAG.get()))
                                .apply(SetItemCountFunction.setCount(between(2, 4)))
                                .when(maxAge))
        );

        LootItemCondition.Builder iceFernMaxAge = MatchBlock.blockMatches(this.blocks, BlockRegistry.ICE_FERN_CROP.get(),
                StatePropertiesPredicate.Builder.properties().hasProperty(IceFernBlock.AGE, IceFernBlock.MAX_AGE));

        this.add(BlockRegistry.ICE_FERN_CROP.get(),
                this.applyExplosionDecay(BlockRegistry.ICE_FERN_CROP.get(),
                        LootTable.lootTable()
                                .withPool(LootPool.lootPool()
                                        .add(LootItem.lootTableItem(ItemRegistry.ICE_FERN.get()))
                                        .when(iceFernMaxAge))
                                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.ICE_FERN_SEEDS.get())))));

        this.add(BlockRegistry.ICE_FERN_GOLD.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.ICE_FERN_GOLD.get())))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.ICE_FERN_SEEDS.get()))));

        LootItemCondition.Builder sunFernMaxAge = MatchBlock.blockMatches(this.blocks, BlockRegistry.SUN_FERN_CROP.get(),
                StatePropertiesPredicate.Builder.properties().hasProperty(SunFernBlock.AGE, SunFernBlock.MAX_AGE));

        this.add(BlockRegistry.SUN_FERN_CROP.get(),
                this.applyExplosionDecay(BlockRegistry.SUN_FERN_CROP.get(),
                        LootTable.lootTable()
                                .withPool(LootPool.lootPool()
                                        .add(LootItem.lootTableItem(ItemRegistry.SUN_FERN.get()))
                                        .when(sunFernMaxAge))
                                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.SUN_FERN_SEEDS.get())))));

        this.add(BlockRegistry.SUN_FERN_GOLD.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.SUN_FERN_GOLD.get())))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.SUN_FERN_SEEDS.get()))));
    }
}