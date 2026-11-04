package sfiomn.legendarysurvivaloverhaul.common.items.heal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import sfiomn.legendarysurvivaloverhaul.config.Config;

public class HealingHerbsItem extends BodyHealingItem
{
    public HealingHerbsItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack)
    {
        return ItemUseAnimation.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return Config.Baked.healingHerbsUseTime;
    }
}
