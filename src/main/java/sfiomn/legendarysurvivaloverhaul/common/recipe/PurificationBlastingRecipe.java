package sfiomn.legendarysurvivaloverhaul.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarysurvivaloverhaul.api.thirst.HydrationEnum;
import sfiomn.legendarysurvivaloverhaul.api.thirst.ThirstUtil;
import sfiomn.legendarysurvivaloverhaul.common.items.drink.CanteenItem;

public class PurificationBlastingRecipe extends AbstractCookingRecipe
{
    private static final int DEFAULT_COOKING_TIME = 100;

    public static final MapCodec<PurificationBlastingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                    AbstractCookingRecipe.CookingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result()),
                    Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(AbstractCookingRecipe::experience),
                    Codec.INT.optionalFieldOf("cookingtime", DEFAULT_COOKING_TIME).forGetter(AbstractCookingRecipe::cookingTime)
            ).apply(i, PurificationBlastingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PurificationBlastingRecipe> STREAM_CODEC =
            AbstractCookingRecipe.cookingStreamCodec(PurificationBlastingRecipe::new);
    public static final RecipeSerializer<PurificationBlastingRecipe> SERIALIZER =
            new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public PurificationBlastingRecipe(Recipe.CommonInfo commonInfo, AbstractCookingRecipe.CookingBookInfo bookInfo, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime)
    {
        super(commonInfo, bookInfo, ingredient, result, experience, cookingTime);
    }

    @Override
    protected Item furnaceIcon()
    {
        return Items.BLAST_FURNACE;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level)
    {
        ItemStack inputStack = input.item();
        // Check if item type matches (ignoring NBT/enchantments) and has water
        return inputStack.getItem() instanceof CanteenItem && ThirstUtil.getCapacityTag(inputStack) > 0;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input)
    {
        ItemStack inputStack = input.item();
        int hydrationCapacity = ThirstUtil.getCapacityTag(inputStack);

        // Create result with the SAME item type as input (preserves large vs regular canteen)
        ItemStack result = new ItemStack(inputStack.getItem());

        // Copy enchantments from input to result using DataComponents
        ItemEnchantments enchantments = inputStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        if (!enchantments.isEmpty()) {
            result.set(DataComponents.ENCHANTMENTS, enchantments);
        }

        // Set the purified hydration values
        ThirstUtil.setHydrationEnumTag(result, HydrationEnum.PURIFIED);
        ThirstUtil.setCapacityTag(result, hydrationCapacity);

        return result;
    }

    @Override
    public @NotNull RecipeSerializer<? extends AbstractCookingRecipe> getSerializer()
    {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<? extends AbstractCookingRecipe> getType()
    {
        return RecipeType.BLASTING;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory()
    {
        return switch (this.category()) {
            case BLOCKS -> RecipeBookCategories.BLAST_FURNACE_BLOCKS;
            case FOOD, MISC -> RecipeBookCategories.BLAST_FURNACE_MISC;
        };
    }
}
