package sfiomn.legendarysurvivaloverhaul.common.integration.eclipticseasons;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

/**
 * Vanilla-only stub of the Ecliptic Seasons integration for the 26.3 port.
 */
public class EclipticSeasonsUtil
{
    public static double averageSeasonTemperature;

    public static void initAverageTemperatures()
    {
    }

    public static boolean hasDimensionSeason(Level level)
    {
        return false;
    }

    public static long getSunRiseTime(Level level)
    {
        return 6000L;
    }

    public static int getDayDuration(Level level)
    {
        return 12000;
    }

    public static int getDaysInSolarTerm(Level level)
    {
        return 0;
    }

    public static Biome.Precipitation getPrecipitation(Level level, BlockPos pos)
    {
        return Biome.Precipitation.NONE;
    }

    public static double getDayInSeasonCycle(Level level)
    {
        return 0.0d;
    }

    public static Component seasonTooltip(Level level)
    {
        return Component.empty();
    }
}
