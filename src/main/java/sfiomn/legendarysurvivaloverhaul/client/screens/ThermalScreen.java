package sfiomn.legendarysurvivaloverhaul.client.screens;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.block.ThermalTypeEnum;
import sfiomn.legendarysurvivaloverhaul.common.containers.AbstractThermalContainer;
import sfiomn.legendarysurvivaloverhaul.util.RenderUtil;

public class ThermalScreen extends AbstractContainerScreen<AbstractThermalContainer>
{
    public static final Identifier HEATER_SCREEN = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "textures/gui/heater_screen.png");
    public static final Identifier COOLER_SCREEN = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "textures/gui/cooler_screen.png");
    public static final int FUEL_LEVEL_WIDTH = 29;
    public static final int FUEL_LEVEL_HEIGHT = 27;

    public ThermalScreen(AbstractThermalContainer screenContainer, Inventory playerInventory, Component titleIn)
    {
        super(screenContainer, playerInventory, titleIn);
    }

    @Override
    protected void init()
    {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor gui, int mouseX, int mouseY, float partialTicks)
    {
        super.extractBackground(gui, mouseX, mouseY, partialTicks);

        if (minecraft == null)
        {
            return;
        }

        Identifier texture;
        if (menu.getThermalType() == ThermalTypeEnum.HEATING)
        {
            texture = HEATER_SCREEN;
        } else
        {
            texture = COOLER_SCREEN;
        }

        RenderUtil.blit(gui, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Fuel level
        int fuelTimeHeight = (int) (menu.getFuelTimeScale() * FUEL_LEVEL_HEIGHT);
        RenderUtil.blit(gui, texture, this.leftPos + 73, this.topPos + 35 + (FUEL_LEVEL_HEIGHT - fuelTimeHeight), 176, 24 + (FUEL_LEVEL_HEIGHT - fuelTimeHeight), FUEL_LEVEL_WIDTH, fuelTimeHeight);

        // Powered status
        if (menu.isPowered())
            RenderUtil.blit(gui, texture, this.leftPos + 137, this.topPos + 37, 176, 0, 12, 24);
    }
}
