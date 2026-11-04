package sfiomn.legendarysurvivaloverhaul.common.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.integration.eclipticseasons.EclipticSeasonsUtil;
import sfiomn.legendarysurvivaloverhaul.common.integration.sereneseasons.SereneSeasonsUtil;


public class SeasonalCalendarItem extends Item
{
    public SeasonalCalendarItem(Item.Properties properties)
    {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand)
    {
        if (level.isClientSide())
        {
            if (LegendarySurvivalOverhaul.sereneSeasonsLoaded)
                player.sendOverlayMessage(SereneSeasonsUtil.seasonTooltip(player.blockPosition(), player.level()));
            else if (LegendarySurvivalOverhaul.eclipticSeasonsLoaded)
                player.sendOverlayMessage(EclipticSeasonsUtil.seasonTooltip(player.level()));
            else
                player.sendOverlayMessage(Component.translatable("message.legendarysurvivaloverhaul.no_season_loaded"));
        }
        return super.use(level, player, hand);
    }
}
