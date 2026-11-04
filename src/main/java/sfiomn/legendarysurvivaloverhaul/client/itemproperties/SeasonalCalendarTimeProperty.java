package sfiomn.legendarysurvivaloverhaul.client.itemproperties;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.integration.eclipticseasons.EclipticSeasonsUtil;
import sfiomn.legendarysurvivaloverhaul.common.integration.sereneseasons.SereneSeasonsUtil;

/**
 * 26.3 item-model property: position inside the current season cycle for the seasonal calendar.
 *
 * <p>Registered through {@code RegisterRangeSelectItemModelPropertyEvent} as
 * {@code legendarysurvivaloverhaul:time}.</p>
 */
@OnlyIn(Dist.CLIENT)
public class SeasonalCalendarTimeProperty implements RangeSelectItemModelProperty
{
    public static final MapCodec<SeasonalCalendarTimeProperty> MAP_CODEC = MapCodec.unit(new SeasonalCalendarTimeProperty());

    float olderD0 = 0;

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
            return 0;
        } else
        {
            try
            {
                double d0 = 0;

                if (LegendarySurvivalOverhaul.sereneSeasonsLoaded)
                {
                    d0 = SereneSeasonsUtil.getTimeInSeasonCycle(level);
                } else if (LegendarySurvivalOverhaul.eclipticSeasonsLoaded)
                    d0 = EclipticSeasonsUtil.getDayInSeasonCycle(level);

                return Mth.positiveModulo((float) d0, 1.0F);
            } catch (NullPointerException e)
            {
                return 0;
            }
        }
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type()
    {
        return MAP_CODEC;
    }
}
