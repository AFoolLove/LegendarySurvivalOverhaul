package sfiomn.legendarysurvivaloverhaul.data.recipes;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.common.recipe.PurificationBlastingRecipe;
import sfiomn.legendarysurvivaloverhaul.common.recipe.PurificationSmeltingRecipe;
import sfiomn.legendarysurvivaloverhaul.registry.RecipeRegistry;

/**
 * 26.3 port of the custom purification cooking recipe builder.
 */
public class PurificationRecipeBuilder
{
    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;
    private final Kind kind;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    @Nullable
    private String group;

    private PurificationRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, ItemLike result,
                                      Ingredient ingredient, float experience, int cookingTime,
                                      RecipeSerializer<? extends AbstractCookingRecipe> serializer, Kind kind)
    {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.serializer = serializer;
        this.kind = kind;
    }

    public static PurificationRecipeBuilder blasting(Ingredient ingredient, RecipeCategory category, ItemLike result,
                                                     float experience, int cookingTime)
    {
        return new PurificationRecipeBuilder(category, determineBlastingRecipeCategory(result), result, ingredient,
                experience, cookingTime, RecipeRegistry.PURIFICATION_BLASTING_SERIALIZER.get(), Kind.BLASTING);
    }

    public static PurificationRecipeBuilder smelting(Ingredient ingredient, RecipeCategory category, ItemLike result,
                                                     float experience, int cookingTime)
    {
        return new PurificationRecipeBuilder(category, determineSmeltingRecipeCategory(result), result, ingredient,
                experience, cookingTime, RecipeRegistry.PURIFICATION_SMELTING_SERIALIZER.get(), Kind.SMELTING);
    }

    private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike result)
    {
        // Item components are not bound yet during datapack registry bootstrap, so keep it simple:
        // all purification results (canteens / water bottle) are misc recipes.
        return CookingBookCategory.MISC;
    }

    private static CookingBookCategory determineBlastingRecipeCategory(ItemLike result)
    {
        return CookingBookCategory.MISC;
    }

    public PurificationRecipeBuilder unlockedBy(String name, Criterion<?> criterion)
    {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    public PurificationRecipeBuilder group(@Nullable String group)
    {
        this.group = group;
        return this;
    }

    public Item getResult()
    {
        return this.result;
    }

    public void save(RecipeOutput output, String id)
    {
        this.save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(id)));
    }

    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id)
    {
        ItemStackTemplate resultTemplate = new ItemStackTemplate(this.result);
        Recipe.CommonInfo commonInfo = new Recipe.CommonInfo(true);
        AbstractCookingRecipe.CookingBookInfo bookInfo =
                new AbstractCookingRecipe.CookingBookInfo(this.bookCategory, this.group == null ? "" : this.group);

        AbstractCookingRecipe recipe = this.kind == Kind.SMELTING
                ? new PurificationSmeltingRecipe(commonInfo, bookInfo, this.ingredient, resultTemplate,
                        this.experience, this.cookingTime)
                : new PurificationBlastingRecipe(commonInfo, bookInfo, this.ingredient, resultTemplate,
                        this.experience, this.cookingTime);

        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }

    private enum Kind
    {
        SMELTING,
        BLASTING
    }
}