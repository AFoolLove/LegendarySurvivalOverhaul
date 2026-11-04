package sfiomn.legendarysurvivaloverhaul.common.integration.eclipticseasons;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sfiomn.legendarysurvivaloverhaul.api.temperature.ModifierBase;

/**
 * Vanilla-only stub of the Ecliptic Seasons temperature modifier for the 26.3 port.
 */
public class EclipticSeasonsModifier extends ModifierBase
{
    @Override
    public float getWorldInfluence(Player player, Level level, BlockPos pos)
    {
        return 0.0f;
    }
}
