package sfiomn.legendarysurvivaloverhaul.data.recipes;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import sfiomn.legendarysurvivaloverhaul.common.recipe.SewingRecipe;

/**
 * 26.3 port of the custom sewing recipe builder.
 */
public class SewingRecipeBuilder
{
    private final RecipeCategory category;
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemLike result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();

    public SewingRecipeBuilder(RecipeCategory category, Ingredient base, Ingredient addition, ItemLike result)
    {
        this.category = category;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public static SewingRecipeBuilder sewingRecipe(Ingredient base, Ingredient addition, ItemLike result,
                                                   RecipeCategory category)
    {
        return new SewingRecipeBuilder(category, base, addition, result);
    }

    public SewingRecipeBuilder unlockedBy(String name, Criterion<?> criterion)
    {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public void save(RecipeOutput output, String id)
    {
        this.save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(id)));
    }

    public void save(RecipeOutput output, Identifier id)
    {
        this.save(output, ResourceKey.create(Registries.RECIPE, id));
    }

    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id)
    {
        SewingRecipe recipe = new SewingRecipe(this.base, this.addition,
                new ItemStackTemplate(this.result.asItem()));
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}