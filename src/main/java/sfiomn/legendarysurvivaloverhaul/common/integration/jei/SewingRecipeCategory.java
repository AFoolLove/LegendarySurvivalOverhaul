package sfiomn.legendarysurvivaloverhaul.common.integration.jei;

import com.mojang.serialization.Codec;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.recipe.SewingRecipe;
import sfiomn.legendarysurvivaloverhaul.registry.BlockRegistry;

public class SewingRecipeCategory implements IRecipeCategory<SewingRecipe>
{
    public static final Identifier UID = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "sewing");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "textures/gui/sewing_table_screen.png");

    public static final IRecipeType<SewingRecipe> SEWING_RECIPE_TYPE = IRecipeType.create(UID, SewingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SewingRecipeCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(TEXTURE, 10, 30, 156, 33);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.SEWING_TABLE.get()));
    }

    @Override
    public IRecipeType<SewingRecipe> getRecipeType()
    {
        return SEWING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + LegendarySurvivalOverhaul.MOD_ID + ".sewing_table");
    }

    @Override
    public int getWidth()
    {
        return 156;
    }

    @Override
    public int getHeight()
    {
        return 33;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SewingRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 9).addIngredients(recipe.getBase());
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 9).addIngredients(recipe.getAddition());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 9).addItemStack(recipe.getResultTemplate().create());
    }

    @Override
    public void draw(SewingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY)
    {
        this.background.draw(guiGraphics);
    }

    @Override
    public Codec<SewingRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager)
    {
        return SewingRecipe.MAP_CODEC.codec();
    }
}