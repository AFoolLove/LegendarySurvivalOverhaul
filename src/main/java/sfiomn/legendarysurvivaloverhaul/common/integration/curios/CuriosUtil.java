package sfiomn.legendarysurvivaloverhaul.common.integration.curios;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Vanilla-only stub of the Curios integration for the 26.3 port.
 * Third-party mod compatibility is not part of this build; see src/integration-reference/java.
 */
public class CuriosUtil
{
    public static boolean isThermometerEquipped = false;

    public static boolean isCurioItemEquipped(Player player, Item item)
    {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(item)
                || player.getItemInHand(InteractionHand.OFF_HAND).is(item);
    }

    public static boolean isCuriosItem(ItemStack stack)
    {
        return false;
    }

    public static boolean equipCurio(Player player, ItemStack stack, InteractionHand hand)
    {
        return false;
    }
}
