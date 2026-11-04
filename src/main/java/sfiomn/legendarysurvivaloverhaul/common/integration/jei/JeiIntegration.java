package sfiomn.legendarysurvivaloverhaul.common.integration.jei;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;
import sfiomn.legendarysurvivaloverhaul.client.screens.SewingTableScreen;
import sfiomn.legendarysurvivaloverhaul.common.items.CoatItem;
import sfiomn.legendarysurvivaloverhaul.common.recipe.SewingRecipe;
import sfiomn.legendarysurvivaloverhaul.config.Config;
import sfiomn.legendarysurvivaloverhaul.registry.BlockRegistry;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sfiomn.legendarysurvivaloverhaul.util.internal.ThirstUtilInternal.HYDRATION_ENUM_TAG;

@JeiPlugin
public class JeiIntegration implements IModPlugin
{
    public static final Identifier PLUGIN_UID = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "jei_plugin");
    private static final String SEWING_RECIPE_TYPE_ID = LegendarySurvivalOverhaul.MOD_ID + ":sewing";

    @Override
    public Identifier getPluginUid()
    {
        return PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        if (Config.Baked.temperatureEnabled)
        {
            registration.addRecipeCategories(new SewingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        }
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        if (Config.Baked.thirstEnabled)
        {
            registration.registerSubtypeInterpreter(ItemRegistry.CANTEEN.get(), CanteenSubtypeInterpreter.INSTANCE);
            registration.registerSubtypeInterpreter(ItemRegistry.LARGE_CANTEEN.get(), CanteenSubtypeInterpreter.INSTANCE);
        }
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration)
    {
        if (!Config.Baked.temperatureEnabled)
            return;

        List<SewingRecipe> recipes = new ArrayList<>(sewingCoatRecipes());
        recipes.addAll(resourceSewingRecipes());
        registration.addRecipes(SewingRecipeCategory.SEWING_RECIPE_TYPE, recipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        if (Config.Baked.temperatureEnabled)
        {
            registration.addRecipeClickArea(SewingTableScreen.class, 40, 37, 20, 20, SewingRecipeCategory.SEWING_RECIPE_TYPE);
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        if (Config.Baked.temperatureEnabled)
        {
            registration.addCraftingStation(SewingRecipeCategory.SEWING_RECIPE_TYPE, BlockRegistry.SEWING_TABLE.get());
        }
    }

    /**
     * Synthetic recipes: any armor piece can be combined with any coat item to get the coated armor.
     * (The 26.3 client no longer receives the full recipe list from the server, so datapack recipes
     * are reconstructed separately below.)
     */
    private List<SewingRecipe> sewingCoatRecipes()
    {
        List<SewingRecipe> sewingRecipes = new ArrayList<>();

        for (Item itemArmor : BuiltInRegistries.ITEM)
        {
            if (!isArmor(itemArmor))
                continue;

            for (DeferredHolder<Item, ? extends Item> modItem : ItemRegistry.ITEMS.getEntries())
            {
                if (modItem.get() instanceof CoatItem itemCoat)
                {
                    ItemStack result = new ItemStack(itemArmor);
                    TemperatureUtil.setArmorCoatTag(result, itemCoat.coat.id());
                    sewingRecipes.add(new SewingRecipe(
                            Ingredient.of(itemArmor),
                            Ingredient.of(itemCoat),
                            ItemStackTemplate.fromNonEmptyStack(result)
                    ));
                }
            }
        }

        return sewingRecipes;
    }

    /**
     * 26.3 does not sync the full recipe registry to the client anymore, so the datapack sewing
     * recipes are read straight from the mod's own resource pack JSONs.
     */
    private List<SewingRecipe> resourceSewingRecipes()
    {
        List<SewingRecipe> recipes = new ArrayList<>();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager == null)
            return recipes;

        Map<Identifier, Resource> found = resourceManager.listResources(
                "recipe", id -> id.getNamespace().equals(LegendarySurvivalOverhaul.MOD_ID));

        for (Resource resource : found.values())
        {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8)))
            {
                JsonElement json = JsonParser.parseReader(reader);
                if (!json.isJsonObject())
                    continue;
                JsonObject object = json.getAsJsonObject();
                JsonElement type = object.get("type");
                if (type == null || !SEWING_RECIPE_TYPE_ID.equals(type.getAsString()))
                    continue;

                SewingRecipe.MAP_CODEC.codec().parse(JsonOps.INSTANCE, object)
                        .result()
                        .ifPresent(recipes::add);
            }
            catch (Exception e)
            {
                LegendarySurvivalOverhaul.LOGGER.warn("Failed to read sewing recipe from {}", resource, e);
            }
        }

        return recipes;
    }

    private static boolean isArmor(Item item)
    {
        ItemStack prototype = new ItemStack(item);
        if (!prototype.has(DataComponents.EQUIPPABLE))
            return false;
        Equippable equippable = prototype.get(DataComponents.EQUIPPABLE);
        if (equippable == null)
            return false;
        EquipmentSlot slot = equippable.slot();
        return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST
                || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
    }

    public static class CanteenSubtypeInterpreter implements ISubtypeInterpreter<ItemStack>
    {
        public static final CanteenSubtypeInterpreter INSTANCE = new CanteenSubtypeInterpreter();

        private CanteenSubtypeInterpreter()
        {
        }

        @Override
        public Object getSubtypeData(ItemStack itemStack, UidContext context)
        {
            CustomData custom = itemStack.get(DataComponents.CUSTOM_DATA);
            if (custom == null)
                return "";
            var tag = custom.copyTag();
            if (!tag.contains(HYDRATION_ENUM_TAG))
                return "";
            return tag.getStringOr(HYDRATION_ENUM_TAG, "");
        }
    }
}