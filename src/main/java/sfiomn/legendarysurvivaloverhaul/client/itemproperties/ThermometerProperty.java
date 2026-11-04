package sfiomn.legendarysurvivaloverhaul.client.itemproperties;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;
import sfiomn.legendarysurvivaloverhaul.util.WorldUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 26.3 item-model property: thermometer fill level.
 *
 * <p>Important: item model properties are evaluated on the client while the stack is being rendered,
 * including the stack that currently sits on the container cursor. Writing a data component into the
 * rendered stack from here makes the client-side stack differ from the server one and can leave a
 * ghost ("fake") item on the cursor after a slot interaction. The temperature is therefore only
 * computed and cached here - the stack itself is never mutated.</p>
 */
public class ThermometerProperty implements RangeSelectItemModelProperty
{
    public static final MapCodec<ThermometerProperty> MAP_CODEC = MapCodec.unit(new ThermometerProperty());

    /** Last computed temperature per item type (thermometer variants share the same gauge). */
    private static final Map<Item, CachedTemperature> LAST_TEMPERATURE = new ConcurrentHashMap<>();

    private record CachedTemperature(float temperature, long tick) {}

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner owner, int seed)
    {
        Level level = clientLevel != null ? clientLevel : (owner != null ? owner.level() : null);
        Item item = itemStack.getItem();

        if (level == null)
        {
            return temperatureRatio(LAST_TEMPERATURE.containsKey(item)
                    ? LAST_TEMPERATURE.get(item).temperature() : fallbackTemperature());
        }

        long gameTime = level.getGameTime();
        CachedTemperature cached = LAST_TEMPERATURE.get(item);

        // Position is only available while the item is rendered in the world / on an entity.
        if (owner != null)
        {
            if (cached == null || gameTime - cached.tick() > 10)
            {
                float temperature = WorldUtil.calculateClientWorldPositionTemperature(level, owner.position());
                LAST_TEMPERATURE.put(item, new CachedTemperature(temperature, gameTime));
                cached = LAST_TEMPERATURE.get(item);
            }
            return temperatureRatio(cached.temperature());
        }

        return temperatureRatio(cached != null ? cached.temperature() : fallbackTemperature());
    }

    private static float fallbackTemperature()
    {
        return TemperatureEnum.NORMAL.getValue();
    }

    private static float temperatureRatio(float temperature)
    {
        return Mth.positiveModulo(TemperatureUtil.clampTemperature((int) temperature) / TemperatureEnum.getMax(), 1.0333333f);
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type()
    {
        return MAP_CODEC;
    }
}