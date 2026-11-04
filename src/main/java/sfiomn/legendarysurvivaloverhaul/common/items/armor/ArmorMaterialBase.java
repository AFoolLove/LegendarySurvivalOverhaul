package sfiomn.legendarysurvivaloverhaul.common.items.armor;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.neoforged.bus.api.IEventBus;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

import java.util.Map;

/**
 * Armor materials are no longer a registry in 26.3: {@link ArmorMaterial} is a plain record
 * referenced directly by the item's {@code EQUIPPABLE}/{@code ATTRIBUTE_MODIFIERS} components
 * through {@code Item.Properties#humanoidArmor(ArmorMaterial, ArmorType)}.
 * <p>
 * The equipment textures are resolved from the data-driven equipment asset
 * ({@code assets/<namespace>/equipment/<name>.json}) referenced by {@link ArmorMaterial#assetId()}.
 */
public class ArmorMaterialBase
{
    private static final int DURABILITY_MULTIPLIER = 6;

    private static ResourceKey<EquipmentAsset> equipmentAsset(String name)
    {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, name));
    }

    private static Map<ArmorType, Integer> defense(int boots, int leggings, int chestplate, int helmet)
    {
        return Map.of(
                ArmorType.BOOTS, boots,
                ArmorType.LEGGINGS, leggings,
                ArmorType.CHESTPLATE, chestplate,
                ArmorType.HELMET, helmet,
                ArmorType.BODY, 0 // non-player
        );
    }

    // Snow armor material (defense: 1/2/1/1, ench: 17, leather equip sound, repair: wool, toughness 0, kb-res 0)
    public static final ArmorMaterial SNOW = new ArmorMaterial(
            DURABILITY_MULTIPLIER,
            defense(1, 2, 1, 1),
            17,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            ItemTags.WOOL,
            equipmentAsset("snow")
    );

    // Desert armor material (same defense, ench: 19, leather equip sound, repair: leather)
    public static final ArmorMaterial DESERT = new ArmorMaterial(
            DURABILITY_MULTIPLIER,
            defense(1, 2, 1, 1),
            19,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.0F,
            ItemTags.REPAIRS_LEATHER_ARMOR,
            equipmentAsset("desert")
    );

    /**
     * Kept as a no-op so that the (out-of-scope) main mod class keeps compiling: armor materials
     * are no longer a deferred registry in 26.3 and need no registration.
     * TODO(lead): drop the call in LegendarySurvivalOverhaul once the main class is being edited.
     */
    public static void register(IEventBus eventBus)
    {
    }
}
