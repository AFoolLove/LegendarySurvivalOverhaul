package sfiomn.legendarysurvivaloverhaul.common.integration.overflowingbars;

import fuzs.overflowingbars.common.OverflowingBars;
import fuzs.overflowingbars.common.config.ClientConfig;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

public class OverflowingBarsUtil
{
    public static boolean isHealthBarOverflowing()
    {
        return LegendarySurvivalOverhaul.overflowingbarsLoaded
                && OverflowingBars.CONFIG.get(ClientConfig.class).health.allowHealthLayers;
    }
}