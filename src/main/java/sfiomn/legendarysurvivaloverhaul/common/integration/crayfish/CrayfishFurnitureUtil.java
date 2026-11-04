package sfiomn.legendarysurvivaloverhaul.common.integration.crayfish;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Vanilla-only stub of the MrCrayfish's Furniture integration for the 26.3 port.
 */
public class CrayfishFurnitureUtil
{
    public static InteractionResult tryFillCanteenFromSinkOrBasin(Level level, BlockPos pos, Player player, ItemStack canteen)
    {
        return InteractionResult.PASS;
    }

    public static InteractionResult tryEmptyCanteenIntoSinkOrBasin(Level level, BlockPos pos, Player player, ItemStack canteen)
    {
        return InteractionResult.PASS;
    }

    public static boolean isSinkOrBasin(BlockState blockState)
    {
        return false;
    }
}
