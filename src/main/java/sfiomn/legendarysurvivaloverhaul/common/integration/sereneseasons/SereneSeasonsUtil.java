package sfiomn.legendarysurvivaloverhaul.common.integration.sereneseasons;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Vanilla-only stub of the Serene Seasons integration for the 26.3 port.
 */
public class SereneSeasonsUtil
{
    public static double averageSeasonTemperature;
    public static double averageTropicalSeasonTemperature;

    public enum SeasonType
    {
        NORMAL_SEASON,
        TROPICAL_SEASON,
        NO_SEASON
    }

    public static void initAverageTemperatures()
    {
    }

    public static Component seasonTooltip(BlockPos blockPos, Level level)
    {
        return Component.empty();
    }

    public static SeasonType getSeasonType(Holder<Biome> biome)
    {
        return SeasonType.NORMAL_SEASON;
    }

    public static boolean plantCanGrow(Level level, BlockPos pos, BlockState plant)
    {
        return true;
    }

    public static double getTimeInSeasonCycle(Level level)
    {
        return 0.0d;
    }

    public static boolean hasSeasons(Level level)
    {
        return false;
    }

    public static float getSeasonModifier(double previousSeasonModifier, double currentSeasonModifier, double nextSeasonModifier,
                                          int time, int subSeasonDuration)
    {
        return 0.0f;
    }
}
