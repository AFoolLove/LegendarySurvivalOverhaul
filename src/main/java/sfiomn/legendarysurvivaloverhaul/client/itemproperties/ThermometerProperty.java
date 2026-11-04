package sfiomn.legendarysurvivaloverhaul.client.itemproperties;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;
import sfiomn.legendarysurvivaloverhaul.common.attachments.temperature.TemperatureItemAttachment;
import sfiomn.legendarysurvivaloverhaul.util.AttachmentUtil;

/**
 * 26.3 item-model property: thermometer fill level.
 *
 * <p>{@code ItemProperties.register} / {@code ClampedItemPropertyFunction} are gone. Properties are now
 * JSON driven: this class is registered through {@code RegisterRangeSelectItemModelPropertyEvent} and
 * referenced from an item model with {@code "type": "minecraft:range_dispatch", "property":
 * "legendarysurvivaloverhaul:temperature"}.</p>
 */
public class ThermometerProperty implements RangeSelectItemModelProperty
{
    public static final MapCodec<ThermometerProperty> MAP_CODEC = MapCodec.unit(new ThermometerProperty());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner owner, int seed)
    {
        Level level = clientLevel;
        LivingEntity holder = owner != null ? owner.asLivingEntity() : null;

        if (level == null && owner != null)
        {
            level = owner.level();
        }

        if (level == null)
        {
            return 0.5f;
        } else
        {
            try
            {
                TemperatureItemAttachment tempItemCap = AttachmentUtil.getTempItemAttachment(itemStack);
                if (holder != null && tempItemCap.shouldUpdate(level.getGameTime()))
                {
                    tempItemCap.updateWorldTemperature(level, holder, level.getGameTime());
                }
                return Mth.positiveModulo(TemperatureUtil.clampTemperature((int) tempItemCap.getWorldTemperatureLevel()) / TemperatureEnum.getMax(), 1.0333333f);
            } catch (NullPointerException e)
            {
                return 0.5f;
            }
        }
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type()
    {
        return MAP_CODEC;
    }
}
