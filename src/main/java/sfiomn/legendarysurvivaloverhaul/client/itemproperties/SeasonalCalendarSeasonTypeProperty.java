package sfiomn.legendarysurvivaloverhaul.client.itemproperties;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.integration.sereneseasons.SereneSeasonsUtil;

/**
 * 26.3 item-model property: season type for the seasonal calendar.
 *
 * <p>Registered through {@code RegisterRangeSelectItemModelPropertyEvent} as
 * {@code legendarysurvivaloverhaul:seasontype}.</p>
 *
 * <p>The vanilla-only {@link SereneSeasonsUtil.SeasonType} stub no longer carries the {@code propertyValue}
 * field that the 1.21.1 integration enum had, so the original 0.0 / 0.1 / 0.9 thresholds are mapped here
 * (same numeric values as upstream: NORMAL_SEASON=0.0, TROPICAL_SEASON=0.1, NO_SEASON=0.9).</p>
 */
public class SeasonalCalendarSeasonTypeProperty implements RangeSelectItemModelProperty
{
    public static final MapCodec<SeasonalCalendarSeasonTypeProperty> MAP_CODEC = MapCodec.unit(new SeasonalCalendarSeasonTypeProperty());

    private static final float NORMAL_SEASON_VALUE = 0.0f;
    private static final float TROPICAL_SEASON_VALUE = 0.1f;
    private static final float NO_SEASON_VALUE = 0.9f;

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable ItemOwner owner, int seed)
    {
        Level level = clientLevel;
        LivingEntity holder = owner != null ? owner.asLivingEntity() : null;

        if (level == null && owner != null)
        {
            level = owner.level();
        }

        if (level == null || owner == null)
        {
            return NO_SEASON_VALUE;
        } else
        {
            try
            {
                float d0 = 0;

                if (LegendarySurvivalOverhaul.sereneSeasonsLoaded)
                {
                    if (!SereneSeasonsUtil.hasSeasons(level))
                        return NO_SEASON_VALUE;

                    BlockPos pos = holder != null ? holder.blockPosition() : BlockPos.containing(owner.position());
                    SereneSeasonsUtil.SeasonType seasonType = SereneSeasonsUtil.getSeasonType(level.getBiome(pos));
                    d0 = switch (seasonType)
                    {
                        case NORMAL_SEASON -> NORMAL_SEASON_VALUE;
                        case TROPICAL_SEASON -> TROPICAL_SEASON_VALUE;
                        case NO_SEASON -> NO_SEASON_VALUE;
                    };

                } else if (LegendarySurvivalOverhaul.eclipticSeasonsLoaded)
                {
                    d0 = 0.3f;
                }

                return d0;
            } catch (NullPointerException e)
            {
                return NO_SEASON_VALUE;
            }
        }
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type()
    {
        return MAP_CODEC;
    }
}
