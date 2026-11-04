package sfiomn.legendarysurvivaloverhaul.client.itemproperties;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.api.thirst.HydrationEnum;
import sfiomn.legendarysurvivaloverhaul.api.thirst.ThirstUtil;

/**
 * 26.3 item-model property: canteen thirst/hydration tier.
 *
 * <p>Registered through {@code RegisterRangeSelectItemModelPropertyEvent} as
 * {@code legendarysurvivaloverhaul:thirstenum} and referenced from an item model with
 * {@code "type": "minecraft:range_dispatch"}.</p>
 */
public class CanteenProperty implements RangeSelectItemModelProperty
{
    public static final MapCodec<CanteenProperty> MAP_CODEC = MapCodec.unit(new CanteenProperty());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner owner, int seed)
    {
        HydrationEnum hydrationEnum = ThirstUtil.getHydrationEnumTag(itemStack);

        if (hydrationEnum != null)
        {
            return hydrationEnum.ordinal() / 10.0f;
        }

        return 0.0f;
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type()
    {
        return MAP_CODEC;
    }
}
