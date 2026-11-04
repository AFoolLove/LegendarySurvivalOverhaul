package sfiomn.legendarysurvivaloverhaul.client.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.containers.SewingTableContainer;
import sfiomn.legendarysurvivaloverhaul.util.RenderUtil;

@OnlyIn(Dist.CLIENT)
public class SewingTableScreen extends AbstractContainerScreen<SewingTableContainer>
{
    public static final Identifier SEWING_TABLE_SCREEN = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "textures/gui/sewing_table_screen.png");
    Rect2i craftDisabledArea;

    public SewingTableScreen(SewingTableContainer screenContainer, Inventory playerInventory, Component titleIn)
    {
        super(screenContainer, playerInventory, titleIn);
    }

    @Override
    protected void init()
    {
        super.init();
        craftDisabledArea = new Rect2i(this.leftPos + 93, this.topPos + 41, 13, 13);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor gui, int mouseX, int mouseY, float partialTicks)
    {
        super.extractBackground(gui, mouseX, mouseY, partialTicks);

        if (minecraft == null)
        {
            return;
        }

        RenderUtil.blit(gui, SEWING_TABLE_SCREEN, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (canCraft())
        {
            RenderUtil.blit(gui, SEWING_TABLE_SCREEN, this.leftPos + 93, this.topPos + 41, 176, 0, 13, 13);
        }
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor gui, int mouseX, int mouseY)
    {
        super.extractTooltip(gui, mouseX, mouseY);

        if (Minecraft.getInstance().player == null) return;
        if (Minecraft.getInstance().gui.screen() == null) return;

        if (craftDisabledArea.contains(mouseX, mouseY))
        {
            if (canCraft())
            {
                Component tooltipText = Component.translatable("tooltip." + LegendarySurvivalOverhaul.MOD_ID + ".sewing_table.disabled");
                gui.setTooltipForNextFrame(Minecraft.getInstance().font, tooltipText, mouseX, mouseY);
            }
        }
    }

    /**
     * 26.3 removed {@code ArmorItem}; the "armor" input is now identified by the data-driven
     * {@code DataComponents.EQUIPPABLE} component.
     */
    private boolean canCraft()
    {
        return this.menu.getSlot(0).getItem().has(DataComponents.EQUIPPABLE)
                && this.menu.getSlot(1).getItem().getItem() instanceof Item
                && !this.menu.getSlot(2).hasItem();
    }
}
