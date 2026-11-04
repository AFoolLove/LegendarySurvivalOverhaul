package sfiomn.legendarysurvivaloverhaul.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfiomn.legendarysurvivaloverhaul.common.integration.curios.CuriosUtil;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class WearableCurioItem extends Item implements ICurioItem
{

    public WearableCurioItem(Properties p_41383_)
    {
        super(p_41383_);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand)
    {

        ItemStack itemstack = player.getItemInHand(hand);

        boolean isWorn = CuriosUtil.equipCurio(player, itemstack, hand);
        if (isWorn)
            level.playSound(null, player.blockPosition(), SoundEvents.ARMOR_EQUIP_GENERIC.value(), player.getSoundSource(), 1.0f, 1.0f);

        return isWorn ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }
}
