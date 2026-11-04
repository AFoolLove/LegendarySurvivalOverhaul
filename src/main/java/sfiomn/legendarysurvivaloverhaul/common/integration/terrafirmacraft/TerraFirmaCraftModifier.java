package sfiomn.legendarysurvivaloverhaul.common.integration.terrafirmacraft;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sfiomn.legendarysurvivaloverhaul.api.temperature.ModifierBase;

/**
 * Vanilla-only stub of the TerraFirmaCraft temperature modifier for the 26.3 port.
 */
public class TerraFirmaCraftModifier extends ModifierBase
{
    @Override
    public float getWorldInfluence(Player player, Level level, BlockPos pos)
    {
        return 0.0f;
    }
}
