package sfiomn.legendarysurvivaloverhaul.client.screens;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import com.mojang.blaze3d.Blaze3D;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.Component;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

public class WarningDataPackScreen extends ConfirmScreen
{

    public WarningDataPackScreen()
    {
        super(getButtonEffect(),
                Component.translatable("screen.legendarysurvivaloverhaul.datapack_transfer.title"),
                Component.translatable("screen.legendarysurvivaloverhaul.datapack_transfer.text"),
                Component.translatable("screen.legendarysurvivaloverhaul.datapack_transfer.open_folder"),
                Component.translatable("menu.returnToGame"));
    }

    private static BooleanConsumer getButtonEffect()
    {
        return (confirmButton) -> {
            if (confirmButton)
            {
                Blaze3D.openPath(LegendarySurvivalOverhaul.modConfigPath);
            } else
            {
                Minecraft.getInstance().gui.setScreen(null);
            }
        };
    }
}
