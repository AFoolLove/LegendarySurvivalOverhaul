package sfiomn.legendarysurvivaloverhaul.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.item.CoatEnum;
import sfiomn.legendarysurvivaloverhaul.common.items.*;
import sfiomn.legendarysurvivaloverhaul.common.items.drink.*;
import sfiomn.legendarysurvivaloverhaul.common.items.heal.*;

import java.util.function.Consumer;

import static sfiomn.legendarysurvivaloverhaul.common.items.armor.ArmorMaterialBase.DESERT;
import static sfiomn.legendarysurvivaloverhaul.common.items.armor.ArmorMaterialBase.SNOW;


public class ItemRegistry
{
    // 26.3: Item.Properties must already carry its id when the Item is constructed (the constructor registers the
    // data component initializer for properties.itemIdOrThrow()), otherwise "Item id not set" NPE at startup.
    // The specialized DeferredRegister.Items helpers call setId(...) automatically.
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LegendarySurvivalOverhaul.MOD_ID);

    public static final DeferredItem<Item> THERMOMETER = ITEMS.registerItem("thermometer", ThermometerItem::new, p -> p.stacksTo(1));
    public static final DeferredItem<Item> SEASONAL_CALENDAR = ITEMS.registerItem("seasonal_calendar", SeasonalCalendarItem::new);

    public static final DeferredItem<Item> NETHER_CHALICE = ITEMS.registerItem("nether_chalice", NetherChaliceItem::new, p -> p.stacksTo(1).rarity(Rarity.EPIC));
    public static final DeferredItem<Item> SPONGE = ITEMS.registerItem("sponge", properties -> new WearableCurioItem(properties)
    {

        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipComponents, TooltipFlag isAdvanced)
        {
            super.appendHoverText(stack, context, display, tooltipComponents, isAdvanced);

            tooltipComponents.accept(Component.translatable("tooltip." + LegendarySurvivalOverhaul.MOD_ID + ".sponge.description"));
        }
    }, p -> p.stacksTo(1).rarity(Rarity.RARE));
    public static final DeferredItem<Item> HEAT_RESISTANCE_RING = ITEMS.registerItem("heat_resistance_ring", WearableCurioItem::new, p -> p.stacksTo(1).rarity(Rarity.RARE));
    public static final DeferredItem<Item> COLD_RESISTANCE_RING = ITEMS.registerItem("cold_resistance_ring", WearableCurioItem::new, p -> p.stacksTo(1).rarity(Rarity.RARE));
    public static final DeferredItem<Item> THERMAL_RESISTANCE_RING = ITEMS.registerItem("thermal_resistance_ring", WearableCurioItem::new, p -> p.stacksTo(1).rarity(Rarity.EPIC));
    public static final DeferredItem<Item> FIRST_AID_SUPPLIES = ITEMS.registerItem("first_aid_supplies", properties -> new WearableCurioItem(properties)
    {

        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipComponents, TooltipFlag isAdvanced)
        {
            super.appendHoverText(stack, context, display, tooltipComponents, isAdvanced);

            tooltipComponents.accept(Component.translatable("tooltip." + LegendarySurvivalOverhaul.MOD_ID + ".first_aid_supplies.description"));
        }
    }, p -> p.stacksTo(1).rarity(Rarity.RARE));

    public static final DeferredItem<Item> SNOW_HELMET = ITEMS.registerItem("snow_helmet", Item::new, p -> p.humanoidArmor(SNOW, ArmorType.HELMET));
    public static final DeferredItem<Item> SNOW_CHEST = ITEMS.registerItem("snow_chestplate", Item::new, p -> p.humanoidArmor(SNOW, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> SNOW_LEGGINGS = ITEMS.registerItem("snow_leggings", Item::new, p -> p.humanoidArmor(SNOW, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> SNOW_BOOTS = ITEMS.registerItem("snow_boots", properties -> new Item(properties)
    {
        @Override
        public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
        {
            return true;
        }
    }, p -> p.humanoidArmor(SNOW, ArmorType.BOOTS));

    public static final DeferredItem<Item> DESERT_HELMET = ITEMS.registerItem("desert_helmet", Item::new, p -> p.humanoidArmor(DESERT, ArmorType.HELMET));
    public static final DeferredItem<Item> DESERT_CHEST = ITEMS.registerItem("desert_chestplate", Item::new, p -> p.humanoidArmor(DESERT, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> DESERT_LEGGINGS = ITEMS.registerItem("desert_leggings", Item::new, p -> p.humanoidArmor(DESERT, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> DESERT_BOOTS = ITEMS.registerItem("desert_boots", Item::new, p -> p.humanoidArmor(DESERT, ArmorType.BOOTS));

    public static final DeferredItem<Item> COOLING_COAT_1 = ITEMS.registerItem("cooling_coat_1", p -> new CoatItem(CoatEnum.COOLING_1, p), props -> props.rarity(Rarity.COMMON));
    public static final DeferredItem<Item> COOLING_COAT_2 = ITEMS.registerItem("cooling_coat_2", p -> new CoatItem(CoatEnum.COOLING_2, p), props -> props.rarity(Rarity.RARE));
    public static final DeferredItem<Item> COOLING_COAT_3 = ITEMS.registerItem("cooling_coat_3", p -> new CoatItem(CoatEnum.COOLING_3, p), props -> props.rarity(Rarity.EPIC));
    public static final DeferredItem<Item> HEATING_COAT_1 = ITEMS.registerItem("heating_coat_1", p -> new CoatItem(CoatEnum.HEATING_1, p), props -> props.rarity(Rarity.COMMON));
    public static final DeferredItem<Item> HEATING_COAT_2 = ITEMS.registerItem("heating_coat_2", p -> new CoatItem(CoatEnum.HEATING_2, p), props -> props.rarity(Rarity.RARE));
    public static final DeferredItem<Item> HEATING_COAT_3 = ITEMS.registerItem("heating_coat_3", p -> new CoatItem(CoatEnum.HEATING_3, p), props -> props.rarity(Rarity.EPIC));
    public static final DeferredItem<Item> THERMAL_COAT_1 = ITEMS.registerItem("thermal_coat_1", p -> new CoatItem(CoatEnum.THERMAL_1, p), props -> props.rarity(Rarity.COMMON));
    public static final DeferredItem<Item> THERMAL_COAT_2 = ITEMS.registerItem("thermal_coat_2", p -> new CoatItem(CoatEnum.THERMAL_2, p), props -> props.rarity(Rarity.RARE));
    public static final DeferredItem<Item> THERMAL_COAT_3 = ITEMS.registerItem("thermal_coat_3", p -> new CoatItem(CoatEnum.THERMAL_3, p), props -> props.rarity(Rarity.EPIC));

    public static final DeferredItem<Item> COLD_STRING = ITEMS.registerSimpleItem("cold_string", p -> p.rarity(Rarity.COMMON));
    public static final DeferredItem<Item> WARM_STRING = ITEMS.registerSimpleItem("warm_string", p -> p.rarity(Rarity.COMMON));

    public static final DeferredItem<BlockItem> SUN_FERN_SEEDS = ITEMS.registerItem("sun_fern_seeds", p -> new BlockItem(BlockRegistry.SUN_FERN_CROP.get(), p));
    public static final DeferredItem<Item> SUN_FERN = ITEMS.registerSimpleItem("sun_fern_leaf");
    public static final DeferredItem<Item> SUN_FERN_GOLD = ITEMS.registerSimpleItem("sun_fern_gold_leaf");
    public static final DeferredItem<BlockItem> ICE_FERN_SEEDS = ITEMS.registerItem("ice_fern_seeds", p -> new BlockItem(BlockRegistry.ICE_FERN_CROP.get(), p));
    public static final DeferredItem<Item> ICE_FERN = ITEMS.registerSimpleItem("ice_fern_leaf");
    public static final DeferredItem<Item> ICE_FERN_GOLD = ITEMS.registerSimpleItem("ice_fern_gold_leaf");

    // Thirst
    public static final DeferredItem<Item> WATER_PURIFIER = ITEMS.registerItem("water_purifier", properties -> new WearableCurioItem(properties)
    {

        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipComponents, TooltipFlag isAdvanced)
        {
            super.appendHoverText(stack, context, display, tooltipComponents, isAdvanced);

            tooltipComponents.accept(Component.translatable("tooltip." + LegendarySurvivalOverhaul.MOD_ID + ".water_purifier.description"));
        }
    }, p -> p.stacksTo(1).rarity(Rarity.RARE));

    public static final DeferredItem<Item> CANTEEN = ITEMS.registerItem("canteen", CanteenItem::new, p -> p.rarity(Rarity.COMMON).enchantable(10));
    public static final DeferredItem<Item> LARGE_CANTEEN = ITEMS.registerItem("large_canteen", LargeCanteenItem::new, p -> p.rarity(Rarity.UNCOMMON).enchantable(10));

    public static final DeferredItem<Item> APPLE_JUICE = ITEMS.registerItem("apple_juice", JuiceItem::new);
    public static final DeferredItem<Item> BEETROOT_JUICE = ITEMS.registerItem("beetroot_juice", JuiceItem::new);
    public static final DeferredItem<Item> CACTUS_JUICE = ITEMS.registerItem("cactus_juice", JuiceItem::new);
    public static final DeferredItem<Item> CARROT_JUICE = ITEMS.registerItem("carrot_juice", JuiceItem::new);
    public static final DeferredItem<Item> CHORUS_FRUIT_JUICE = ITEMS.registerItem("chorus_fruit_juice", JuiceItem::new);
    public static final DeferredItem<Item> GOLDEN_APPLE_JUICE = ITEMS.registerItem("golden_apple_juice", JuiceItem::new, p -> p.rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> GOLDEN_CARROT_JUICE = ITEMS.registerItem("golden_carrot_juice", JuiceItem::new, p -> p.rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> GLISTERING_MELON_JUICE = ITEMS.registerItem("glistering_melon_juice", JuiceItem::new, p -> p.rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> MELON_JUICE = ITEMS.registerItem("melon_juice", JuiceItem::new);
    public static final DeferredItem<Item> PUMPKIN_JUICE = ITEMS.registerItem("pumpkin_juice", JuiceItem::new);
    public static final DeferredItem<Item> SWEET_BERRIES_JUICE = ITEMS.registerItem("sweet_berries_juice", JuiceItem::new);
    public static final DeferredItem<Item> GLOW_BERRIES_JUICE = ITEMS.registerItem("glow_berries_juice", JuiceItem::new);
    public static final DeferredItem<Item> PURIFIED_WATER_BOTTLE = ITEMS.registerItem("purified_water_bottle", PurifiedWaterBottleItem::new);
    public static final DeferredItem<Item> WATER_PLANT_BAG = ITEMS.registerItem("water_plant_bag", DrinkItem::new);
    public static final DeferredItem<BlockItem> WATER_PLANT_SEEDS = ITEMS.registerItem("water_plant_seeds", p -> new BlockItem(BlockRegistry.WATER_PLANT_CROP.get(), p));

    // Health Overhaul
    public static final DeferredItem<Item> HEART_CONTAINER = ITEMS.registerItem("heart_container", HeartContainerItem::new, p -> p.rarity(Rarity.EPIC));
    public static final DeferredItem<Item> HEART_FRAGMENT = ITEMS.registerSimpleItem("heart_fragment", p -> p.rarity(Rarity.RARE));
    public static final DeferredItem<Item> SHIELD_CONTAINER = ITEMS.registerItem("shield_container", ShieldContainerItem::new, p -> p.rarity(Rarity.EPIC));

    // Body Healing
    public static final DeferredItem<Item> HEALING_HERBS = ITEMS.registerItem("healing_herbs", HealingHerbsItem::new);
    public static final DeferredItem<Item> PLASTER = ITEMS.registerItem("plaster", PlasterItem::new);
    public static final DeferredItem<Item> BANDAGE = ITEMS.registerItem("bandage", BandageItem::new);
    public static final DeferredItem<Item> TONIC = ITEMS.registerItem("tonic", TonicItem::new, p -> p.rarity(Rarity.RARE));
    public static final DeferredItem<Item> MEDKIT = ITEMS.registerItem("medkit", Medkit::new, p -> p.rarity(Rarity.EPIC));
    public static final DeferredItem<Item> MORPHINE = ITEMS.registerItem("morphine", MorphineItem::new);

    // Block items (26.3: block description prefix, matching the "block.<mod>.<id>" lang keys)
    public static final DeferredItem<BlockItem> HEATER = ITEMS.registerSimpleBlockItem(BlockRegistry.HEATER);
    public static final DeferredItem<BlockItem> COOLER = ITEMS.registerSimpleBlockItem(BlockRegistry.COOLER);
    public static final DeferredItem<BlockItem> SEWING_TABLE = ITEMS.registerSimpleBlockItem(BlockRegistry.SEWING_TABLE);
    public static final DeferredItem<BlockItem> SUN_FERN_GOLD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(BlockRegistry.SUN_FERN_GOLD);
    public static final DeferredItem<BlockItem> ICE_FERN_GOLD_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(BlockRegistry.ICE_FERN_GOLD);

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
