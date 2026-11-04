package sfiomn.legendarysurvivaloverhaul.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

public class SewingRecipe implements Recipe<RecipeInput>
{
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStackTemplate result;

    public SewingRecipe(Ingredient base, Ingredient addition, ItemStackTemplate result)
    {
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public Ingredient getBase()
    {
        return base;
    }

    public Ingredient getAddition()
    {
        return addition;
    }

    @Override
    public boolean matches(@NotNull RecipeInput input, Level level)
    {
        if (level.isClientSide()) return false;
        return base.test(input.getItem(0))
                && addition.test(input.getItem(1));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput input)
    {
        ItemStack itemstack = this.result.create();
        ItemStack base = input.getItem(0);
        CustomData custom = base.get(DataComponents.CUSTOM_DATA);
        if (custom != null)
        {
            itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(custom.copyTag()));
        }
        return itemstack;
    }

    @Override
    public @NotNull RecipeSerializer<? extends SewingRecipe> getSerializer()
    {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<? extends SewingRecipe> getType()
    {
        return Type.INSTANCE;
    }

    @Override
    public @NotNull PlacementInfo placementInfo()
    {
        return PlacementInfo.create(java.util.List.of(this.base, this.addition));
    }

    @Override
    public boolean showNotification()
    {
        return true;
    }

    @Override
    public String group()
    {
        return "";
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static final MapCodec<SewingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("base").forGetter(SewingRecipe::getBase),
            Ingredient.CODEC.fieldOf("addition").forGetter(SewingRecipe::getAddition),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result)
    ).apply(instance, SewingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SewingRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.base,
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.addition,
            ItemStackTemplate.STREAM_CODEC, r -> r.result,
            SewingRecipe::new
    );

    public static final RecipeSerializer<SewingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public static class Type implements RecipeType<SewingRecipe>
    {
        public static final Type INSTANCE = new Type();
        public static final Identifier ID =
                Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "sewing");
    }
}
