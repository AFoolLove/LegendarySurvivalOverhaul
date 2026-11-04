package sfiomn.legendarysurvivaloverhaul.common.integration.artifacts;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

/**
 * Vanilla-only stub of the Artifacts integration for the 26.3 port.
 */
public class ArtifactsUtil
{
    public static boolean isHoldingUmbrella(Player player)
    {
        return false;
    }

    public static boolean canProvideShade(Identifier itemRegistryName)
    {
        return false;
    }
}
