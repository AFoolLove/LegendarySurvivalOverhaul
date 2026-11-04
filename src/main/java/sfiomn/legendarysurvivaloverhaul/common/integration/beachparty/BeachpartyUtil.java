package sfiomn.legendarysurvivaloverhaul.common.integration.beachparty;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Vanilla-only stub of the Beachparty integration for the 26.3 port.
 */
public class BeachpartyUtil
{
    public static boolean isUnderParasol(Level level, Player player, BlockPos pos)
    {
        return false;
    }

    public static boolean canProvideShade(Identifier itemRegistryName)
    {
        return false;
    }
}
