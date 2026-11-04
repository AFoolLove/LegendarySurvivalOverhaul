package sfiomn.legendarysurvivaloverhaul.data.providers;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.advancements.triggers.RecipeCraftedTrigger;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.registry.BlockRegistry;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class LegendarySurvivalAdvancements extends AdvancementSubProvider
{
    public static final String SEW_A_COAT_ADVANCEMENT =
            LegendarySurvivalOverhaul.MOD_ID + ":main/sew_a_coat";

    public LegendarySurvivalAdvancements(BootstrapContext<Advancement> output)
    {
        super(output);
    }

    private static ItemStackTemplate icon(Item item)
    {
        return new ItemStackTemplate(item);
    }

    @Override
    public void generate()
    {
        HolderGetter<Item> items = this.output.lookup(Registries.ITEM);
        HolderGetter<Recipe<?>> recipes = this.output.lookup(Registries.RECIPE);

        AdvancementHolder root = Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        icon(ItemRegistry.THERMOMETER.get()),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".description"),
                        Optional.of(new ClientAsset.ResourceTexture(Identifier.fromNamespaceAndPath("minecraft", "block/ice"))),
                        AdvancementType.TASK, false, false, false))
                .addCriterion("main", PlayerTrigger.TriggerInstance.tick())
                .save(this.output, LegendarySurvivalOverhaul.MOD_ID + ":main/root");

        AdvancementHolder getAThermometer = Advancement.Builder.advancement()
                .display(icon(ItemRegistry.THERMOMETER.get()),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".get_a_thermometer"),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".get_a_thermometer.description"),
                        AdvancementType.TASK, true, true, false)
                .parent(root)
                .addCriterion("inventory_changed",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemPredicate.Builder.item().of(items, ItemRegistry.THERMOMETER.get())))
                .save(this.output, LegendarySurvivalOverhaul.MOD_ID + ":main/get_a_thermometer");

        AdvancementHolder getACalendar = Advancement.Builder.advancement()
                .display(icon(ItemRegistry.SEASONAL_CALENDAR.get()),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".get_a_calendar"),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".get_a_calendar.description"),
                        AdvancementType.TASK, true, true, false)
                .parent(root)
                .addCriterion("inventory_changed",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemPredicate.Builder.item().of(items, ItemRegistry.SEASONAL_CALENDAR.get())))
                .save(this.output, LegendarySurvivalOverhaul.MOD_ID + ":main/get_a_calendar");

        AdvancementHolder getASewingTable = Advancement.Builder.advancement()
                .display(icon(BlockRegistry.SEWING_TABLE.get().asItem()),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".get_a_sewing_table"),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".get_a_sewing_table.description"),
                        AdvancementType.TASK, true, true, false)
                .parent(root)
                .addCriterion("inventory_changed",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                ItemPredicate.Builder.item().of(items, BlockRegistry.SEWING_TABLE.get())))
                .save(this.output, LegendarySurvivalOverhaul.MOD_ID + ":main/get_a_sewing_table");

        // A coat is crafted through one of the 9 shaped coat recipes; the recipe_crafted trigger
        // follows the 1.21.1 data fix that lists the real recipe ids.
        List<ResourceKey<Recipe<?>>> coatRecipes = new ArrayList<>();
        for (String coat : List.of("cooling", "heating", "thermal"))
        {
            for (int level = 1; level <= 3; level++)
            {
                coatRecipes.add(ResourceKey.create(Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, coat + "_coat_" + level)));
            }
        }
        HolderSet<Recipe<?>> recipeSet = HolderSet.direct(coatRecipes.stream()
                .map(recipes::getOrThrow)
                .toList());

        Advancement.Builder.advancement()
                .display(icon(ItemRegistry.COOLING_COAT_1.get()),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".sew_a_coat"),
                        Component.translatable("advancement." + LegendarySurvivalOverhaul.MOD_ID + ".sew_a_coat.description"),
                        AdvancementType.TASK, true, true, false)
                .parent(getASewingTable)
                .addCriterion("recipe_crafted",
                        RecipeCraftedTrigger.TriggerInstance.craftedItem(recipeSet))
                .save(this.output, SEW_A_COAT_ADVANCEMENT);
    }
}