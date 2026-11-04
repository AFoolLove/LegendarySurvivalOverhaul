package sfiomn.legendarysurvivaloverhaul.data.providers;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 26.3 client model data generator (blockstates + block/item models).
 *
 * <p>NeoForge 26.3 replaced the old {@code BlockStateProvider}/{@code ItemModelProvider} and the
 * model system moved to the new item-definition pipeline ({@code ModelProvider}). The project keeps
 * the migrated {@code items/*.json} definitions in {@code src/main/resources} (they are version
 * specific hand-migrated data) and regenerates the model/blockstate JSON from code here, which keeps
 * the same output layout the 1.21.1 generators produced.</p>
 */
public class ModModelProvider implements DataProvider
{
    private static final String MOD_ID = LegendarySurvivalOverhaul.MOD_ID;

    private static final List<String> JUICES = List.of(
            "apple_juice", "beetroot_juice", "cactus_juice", "carrot_juice",
            "chorus_fruit_juice", "glistering_melon_juice", "glow_berries_juice",
            "golden_apple_juice", "golden_carrot_juice", "melon_juice",
            "pumpkin_juice", "sweet_berries_juice");

    private static final List<String> HEALING_ITEMS = List.of(
            "bandage", "healing_herbs", "medkit", "morphine", "plaster", "tonic");

    private static final List<String> SIMPLE_ITEMS = List.of(
            "cold_resistance_ring", "cold_string", "first_aid_supplies", "heart_container",
            "heart_fragment", "heat_resistance_ring", "ice_fern_gold_leaf", "ice_fern_leaf",
            "ice_fern_seeds", "nether_chalice", "purified_water_bottle", "shield_container",
            "sponge", "sun_fern_gold_leaf", "sun_fern_leaf", "sun_fern_seeds",
            "thermal_resistance_ring", "warm_string", "water_plant_bag", "water_plant_seeds",
            "water_purifier");

    private static final List<String> CANTEENS = List.of(
            "canteen", "canteen_purified", "large_canteen", "large_canteen_purified");

    private static final List<String> COATS = List.of(
            "cooling_coat_1", "cooling_coat_2", "cooling_coat_3",
            "heating_coat_1", "heating_coat_2", "heating_coat_3",
            "thermal_coat_1", "thermal_coat_2", "thermal_coat_3");

    private static final List<String> ARMOR = List.of(
            "desert_boots", "desert_chestplate", "desert_helmet", "desert_leggings",
            "snow_boots", "snow_chestplate", "snow_helmet", "snow_leggings");

    private static final List<String> BLOCK_PARENT_ITEMS = List.of(
            "cooler", "heater", "ice_fern_gold", "sewing_table", "sun_fern_gold");

    private final PackOutput output;

    public ModModelProvider(PackOutput output)
    {
        this.output = output;
    }

    @Override
    public String getName()
    {
        return "Legendary Survival Overhaul client models";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        List<CompletableFuture<?>> writes = new ArrayList<>();
        PackOutput.PathProvider itemModels = this.output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
        PackOutput.PathProvider blockModels = this.output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/block");
        PackOutput.PathProvider blockStates = this.output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");

        // flat item models
        for (String juice : JUICES)
        {
            flatItem(writes, cache, itemModels, juice, "item/juice/" + juice);
        }
        for (String healing : HEALING_ITEMS)
        {
            flatItem(writes, cache, itemModels, healing, "item/healing/" + healing);
        }
        for (String simple : SIMPLE_ITEMS)
        {
            flatItem(writes, cache, itemModels, simple, "item/" + simple);
        }
        for (String canteen : CANTEENS)
        {
            flatItem(writes, cache, itemModels, canteen, "item/" + canteen);
        }
        for (String coat : COATS)
        {
            flatItem(writes, cache, itemModels, coat, "item/coat/" + coat);
        }
        for (String armor : ARMOR)
        {
            flatItem(writes, cache, itemModels, armor, "item/armor/" + armor);
        }

        // thermometer variants
        for (int i = 0; i < 30; i++)
        {
            String name = String.format("thermometer_%02d", i);
            flatItem(writes, cache, itemModels, "thermometer/" + name, "item/thermometer/" + name);
        }

        // seasonal calendar variants (serene seasons 1-3, ecliptic seasons 1-6, null)
        for (String season : List.of("spring", "summer", "autumn", "winter", "dry", "wet"))
        {
            for (int level = 1; level <= 3; level++)
            {
                String name = season + level;
                flatItem(writes, cache, itemModels,
                        "seasonal_calendar/serene_seasons/seasonal_calendar_" + name,
                        "item/seasonal_calendar/serene_seasons/" + name);
            }
        }
        for (String season : List.of("autumn", "spring", "summer", "winter"))
        {
            for (int level = 1; level <= 6; level++)
            {
                String name = season + level;
                flatItem(writes, cache, itemModels,
                        "seasonal_calendar/ecliptic_seasons/seasonal_calendar_" + name,
                        "item/seasonal_calendar/ecliptic_seasons/" + name);
            }
        }
        flatItem(writes, cache, itemModels, "seasonal_calendar/seasonal_calendar_null",
                "item/seasonal_calendar/calendar_null");

        // block item models
        for (String item : BLOCK_PARENT_ITEMS)
        {
            String parent = switch (item)
            {
                case "cooler" -> "cooler_off";
                case "heater" -> "heater_off_full_block";
                default -> item;
            };
            JsonObject model = new JsonObject();
            model.addProperty("parent", MOD_ID + ":block/" + parent);
            writes.add(DataProvider.saveStable(cache, model, itemModels.json(id(item))));
        }

        // crop models
        for (String crop : List.of("ice_fern", "sun_fern"))
        {
            for (int age = 0; age <= 2; age++)
            {
                String path = crop + "_" + age;
                JsonObject model = new JsonObject();
                model.addProperty("parent", "minecraft:block/crop");
                model.addProperty("render_type", "minecraft:cutout");
                JsonObject textures = new JsonObject();
                textures.addProperty("crop", MOD_ID + ":block/" + path);
                textures.addProperty("particle", MOD_ID + ":block/" + path);
                model.add("textures", textures);
                writes.add(DataProvider.saveStable(cache, model, blockModels.json(id(path))));
            }
        }

        // water plant cross models
        for (String part : List.of("bottom", "top"))
        {
            for (int age = 0; age <= 7; age++)
            {
                String path = "water_plant_" + part + "_" + age;
                JsonObject model = new JsonObject();
                model.addProperty("parent", "minecraft:block/cross");
                model.addProperty("render_type", "minecraft:cutout");
                JsonObject textures = new JsonObject();
                textures.addProperty("cross", MOD_ID + ":block/" + path);
                textures.addProperty("particle", MOD_ID + ":block/" + path);
                model.add("textures", textures);
                writes.add(DataProvider.saveStable(cache, model, blockModels.json(id(path))));
            }
        }

        // blockstates
        blockState(writes, cache, blockStates, "cooler", "cooler_off", "cooler_on", true);
        blockState(writes, cache, blockStates, "heater", "heater_base_off", "heater_base_on", true);
        facingBlockState(writes, cache, blockStates, "heater_top", "heater_top");
        facingBlockState(writes, cache, blockStates, "sewing_table", "sewing_table");

        for (String crop : List.of("ice_fern", "sun_fern"))
        {
            JsonObject variants = new JsonObject();
            for (int age = 0; age <= 2; age++)
            {
                variants.add("age=" + age, modelRef(MOD_ID + ":block/" + crop + "_" + age));
            }
            variants.add("age=3", modelRef(MOD_ID + ":block/" + crop + "_mature"));
            JsonObject json = new JsonObject();
            json.add("variants", variants);
            writes.add(DataProvider.saveStable(cache, json, blockStates.json(id(crop + "_crop"))));
        }

        for (String gold : List.of("ice_fern_gold", "sun_fern_gold"))
        {
            JsonObject variants = new JsonObject();
            variants.add("", modelRef(MOD_ID + ":block/" + gold));
            JsonObject json = new JsonObject();
            json.add("variants", variants);
            writes.add(DataProvider.saveStable(cache, json, blockStates.json(id(gold))));
        }

        JsonObject waterPlantVariants = new JsonObject();
        for (int age = 0; age <= 7; age++)
        {
            waterPlantVariants.add("age=" + age + ",half=lower",
                    modelRef(MOD_ID + ":block/water_plant_bottom_" + age));
            waterPlantVariants.add("age=" + age + ",half=upper",
                    modelRef(MOD_ID + ":block/water_plant_top_" + age));
        }
        JsonObject waterPlant = new JsonObject();
        waterPlant.add("variants", waterPlantVariants);
        writes.add(DataProvider.saveStable(cache, waterPlant, blockStates.json(id("water_plant_crop"))));

        return CompletableFuture.allOf(writes.toArray(CompletableFuture[]::new));
    }

    private static Identifier id(String path)
    {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    private static JsonObject modelRef(String model)
    {
        JsonObject ref = new JsonObject();
        ref.addProperty("model", model);
        return ref;
    }

    private static void flatItem(List<CompletableFuture<?>> writes, CachedOutput cache,
                                 PackOutput.PathProvider provider, String modelPath, String texturePath)
    {
        JsonObject model = new JsonObject();
        model.addProperty("parent", "minecraft:item/generated");
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":" + texturePath);
        model.add("textures", textures);
        writes.add(DataProvider.saveStable(cache, model, provider.json(id(modelPath))));
    }


    private static final int[] FACING_ROTATIONS = {270, 0, 90, 180}; // east, south, west, north

    private static void blockState(List<CompletableFuture<?>> writes, CachedOutput cache,
                                   PackOutput.PathProvider provider, String block,
                                   String offModel, String onModel, boolean lit)
    {
        JsonObject variants = new JsonObject();
        for (int i = 0; i < 4; i++)
        {
            String facing = switch (i)
            {
                case 0 -> "east";
                case 1 -> "south";
                case 2 -> "west";
                default -> "north";
            };
            JsonObject off = modelRef(MOD_ID + ":block/" + offModel);
            if (FACING_ROTATIONS[i] != 0)
            {
                off.addProperty("y", FACING_ROTATIONS[i]);
            }
            variants.add("facing=" + facing + ",lit=false", off);
            JsonObject on = modelRef(MOD_ID + ":block/" + onModel);
            if (FACING_ROTATIONS[i] != 0)
            {
                on.addProperty("y", FACING_ROTATIONS[i]);
            }
            variants.add("facing=" + facing + ",lit=true", on);
        }
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        writes.add(DataProvider.saveStable(cache, json, provider.json(id(block))));
    }

    private static void facingBlockState(List<CompletableFuture<?>> writes, CachedOutput cache,
                                         PackOutput.PathProvider provider, String block, String model)
    {
        JsonObject variants = new JsonObject();
        for (int i = 0; i < 4; i++)
        {
            String facing = switch (i)
            {
                case 0 -> "east";
                case 1 -> "south";
                case 2 -> "west";
                default -> "north";
            };
            JsonObject ref = modelRef(MOD_ID + ":block/" + model);
            if (FACING_ROTATIONS[i] != 0)
            {
                ref.addProperty("y", FACING_ROTATIONS[i]);
            }
            variants.add("facing=" + facing, ref);
        }
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        writes.add(DataProvider.saveStable(cache, json, provider.json(id(block))));
    }
}