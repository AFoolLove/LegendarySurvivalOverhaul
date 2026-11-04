package sfiomn.legendarysurvivaloverhaul.common.items.heal;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarysurvivaloverhaul.config.Config;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static sfiomn.legendarysurvivaloverhaul.registry.MobEffectRegistry.PAINKILLER;
import static sfiomn.legendarysurvivaloverhaul.registry.MobEffectRegistry.PAINKILLER_ADDICTION;

public class MorphineItem extends Item
{

    public MorphineItem(Item.Properties p_i48487_1_)
    {
        super(p_i48487_1_);
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack)
    {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, LivingEntity entity)
    {
        return Config.Baked.morphineUseTime;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if (player.hasEffect(PAINKILLER_ADDICTION))
        {
            player.sendOverlayMessage(Component.translatable("message.legendarysurvivaloverhaul.morphine_use_under_painkiller_addiction"));
            return InteractionResult.FAIL;
        }

        player.startUsingItem(hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity)
    {
        if (level.isClientSide() || !(entity instanceof Player player))
            return stack;

        player.addEffect(new MobEffectInstance(PAINKILLER, Config.Baked.morphinePainkillerTickDuration, 0, false, false, true));
        player.addEffect(new MobEffectInstance(PAINKILLER_ADDICTION, Config.Baked.painkillerAddictionDuration, 0, false, false, true));

        if (!player.isCreative())
            stack.shrink(1);

        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Item.TooltipContext level, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag isAdvanced)
    {
        if (Config.Baked.localizedBodyDamageEnabled)
            tooltip.accept(Component.translatable("tooltip.legendarysurvivaloverhaul.body_heal_item.morphine"));
        super.appendHoverText(stack, level, display, tooltip, isAdvanced);
    }
}
