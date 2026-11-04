package sfiomn.legendarysurvivaloverhaul.common.integration.curios;

import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;
import java.util.Optional;

/**
 * Curios 17 (NeoForge 26.3) integration helpers.
 */
public class CuriosUtil
{
    public static boolean isThermometerEquipped = false;

    public static boolean isCurioItemEquipped(Player player, Item item)
    {
        if (LegendarySurvivalOverhaul.curiosLoaded)
        {
            Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
            return curiosInventory.map(handler -> handler.isEquipped(item)).orElse(false);
        } else
        {
            return player.getItemInHand(InteractionHand.MAIN_HAND).is(item)
                    || player.getItemInHand(InteractionHand.OFF_HAND).is(item);
        }
    }

    public static boolean isCuriosItem(ItemStack stack)
    {
        if (!LegendarySurvivalOverhaul.curiosLoaded || stack.isEmpty())
            return false;
        // Curios 17: the curio capability is attached to items registered for a curio slot.
        return CuriosApi.getCurio(stack).isPresent();
    }

    /**
     * Equips a curio from a right-click, without a hard dependency on Curios at class-load time.
     * Tries the first empty valid slot, then swaps with the first occupied valid slot.
     */
    public static boolean equipCurio(Player player, ItemStack stack, InteractionHand hand)
    {
        if (!LegendarySurvivalOverhaul.curiosLoaded || stack.isEmpty())
            return false;

        Optional<ICuriosItemHandler> curiosInventoryOpt = CuriosApi.getCuriosInventory(player);
        if (curiosInventoryOpt.isEmpty())
            return false;

        ICuriosItemHandler curiosInventory = curiosInventoryOpt.get();
        Map<String, ICurioStacksHandler> curios = curiosInventory.getCurios();

        IDynamicStackHandler swapHandler = null;
        SlotContext swapContext = null;

        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet())
        {
            String id = entry.getKey();
            ICurioStacksHandler handler = entry.getValue();
            IDynamicStackHandler slots = handler.getStacks();
            NonNullList<Boolean> renderStates = handler.getRenders();

            for (int ix = 0; ix < slots.getSlots(); ix++)
            {
                SlotContext slotContext = new SlotContext(
                        id, player, ix, false,
                        renderStates.size() > ix && renderStates.get(ix)
                );

                if (!slots.isItemValid(ix, stack))
                    continue;

                ItemStack present = slots.getStackInSlot(ix);

                // Empty slot -> equip immediately.
                if (present.isEmpty())
                {
                    slots.setStackInSlot(ix, stack.copy());
                    if (!player.isCreative())
                        stack.shrink(stack.getCount());
                    return true;
                }

                // Otherwise remember the first valid slot we can swap with.
                if (swapHandler == null)
                {
                    swapHandler = slots;
                    swapContext = slotContext;
                }
            }
        }

        // No empty slot: swap with the first acceptable occupied slot.
        if (swapHandler != null)
        {
            int index = swapContext.index();
            ItemStack present = swapHandler.getStackInSlot(index);

            swapHandler.setStackInSlot(index, stack.copy());
            if (!player.isCreative())
                player.setItemInHand(hand, present.copy());
            return true;
        }

        return false;
    }
}