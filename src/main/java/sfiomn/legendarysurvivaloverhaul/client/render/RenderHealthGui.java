package sfiomn.legendarysurvivaloverhaul.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.entity.player.Player;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.bodydamage.IBodyDamageAttachment;
import sfiomn.legendarysurvivaloverhaul.api.health.HealthUtil;
import sfiomn.legendarysurvivaloverhaul.common.attachments.health.HealthAttachment;
import sfiomn.legendarysurvivaloverhaul.common.integration.overflowingbars.OverflowingBarsUtil;
import sfiomn.legendarysurvivaloverhaul.config.Config;
import sfiomn.legendarysurvivaloverhaul.util.AttachmentUtil;

import java.util.Random;

public class RenderHealthGui
{
    public static final Identifier ICONS = Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "textures/gui/overlay.png");
    private static final Random rand = new Random();
    private static final int HEART_TEXTURE_WIDTH = 9;
    // Since 1.20.6+ vanilla GUI icons.png was split into individual sprites under hud/heart/*.
    // We now use the sprite-based rendering API via GuiGraphics.blitSprite.
    private static final int HEART_TEXTURE_HEIGHT = 9;
    private static HealthAttachment HEALTH_CAP = null;

    public static void render(Gui gui, GuiGraphicsExtractor guiGraphics, float partialTicks, int width, int height)
    {
        if (Config.Baked.healthOverhaulEnabled && !Minecraft.getInstance().gui.hud.isHidden())
        {
            Player player = Minecraft.getInstance().player;
            if (player != null && !player.isCreative() && !player.isSpectator())
            {
                rand.setSeed(player.tickCount * 445L);

                Profiler.get().push("health_gui");
                int leftHeight = gui.hud.leftHeight;
                int used = drawHealthBar(guiGraphics, player, width, height, leftHeight);
                if (used > 0) gui.hud.leftHeight += used;
                Profiler.get().pop();
            }
        }
    }

    /**
     * Draws the health overlay and returns how many pixels of left HUD height were consumed.
     */
    public static int drawHealthBar(GuiGraphicsExtractor gui, Player player, int width, int height, int leftHeight)
    {
        if (HEALTH_CAP == null || player.tickCount % 20 == 0)
            HEALTH_CAP = AttachmentUtil.getHealthAttachment(player);

        int brokenHearts = HealthUtil.getEffectiveBrokenHearts(player);
        float shieldHealth = HEALTH_CAP.getShieldHealth();
        
        // Check if we should blink the health bar
        IBodyDamageAttachment bodyDamageCap = null;
        if (Config.Baked.localizedBodyDamageEnabled)
        {
            bodyDamageCap = AttachmentUtil.getBodyDamageAttachment(player);
        }

        if (brokenHearts + shieldHealth == 0)
            return 0;

        int left = width / 2 - 91;           // vanilla health bar X
        int top = height - leftHeight;       // stack-aware baseline like thirst does on the right

        int playerHearts = 0;
        int totalHearts = Mth.ceil(shieldHealth / 2.0F) + brokenHearts;

        boolean appendedRow = false;
        if (Config.Baked.appendBrokenShieldHeartsToHealthBar)
        {
            playerHearts = Mth.ceil(player.getMaxHealth() / 2.0f);

            if (OverflowingBarsUtil.isHealthBarOverflowing())
                playerHearts = Math.min(10, playerHearts);

            playerHearts = playerHearts % 10;

            if (playerHearts > 0)
            {
                totalHearts += playerHearts;
                top += 10;          // matches old Forge behavior
                appendedRow = true; // we "borrowed" 10px before adding rows
            }
        }

        int healthRows = Mth.ceil(totalHearts / 10.0F);

        // Render
        int healthBlinkTimer = bodyDamageCap != null ? bodyDamageCap.getHealthBlinkTimer() : 0;
        renderHearts(gui, left, top, 10, playerHearts, brokenHearts, Mth.ceil(player.getHealth()), shieldHealth, healthBlinkTimer);

        // Report how much vertical space we actually used on the left HUD stack.
        // Matches old Forge logic: start -10 when we appended the extra player row, then add rows * 10.
        int used = healthRows * 10 - (appendedRow ? 10 : 0);
        return Math.max(used, 0);
    }

    public static void renderHearts(GuiGraphicsExtractor gui, int left, int top, int rowHeight, int playerHearts, int brokenHearts, int health, float shieldHealth, int healthBlinkTimer)
    {
        int shieldHearts = Mth.ceil((double) shieldHealth / 2.0);
        
        // Calculate blink effect (oscillate between 9 and 45 for y texture offset)
        int blinkOffset = 0;
        if (healthBlinkTimer > 0)
        {
            blinkOffset = (healthBlinkTimer % 10 < 5) ? 45 : 9;
        }

        for (int i1 = playerHearts + shieldHearts + brokenHearts - 1; i1 >= playerHearts; --i1)
        {
            int j1 = i1 / 10;
            int k1 = i1 % 10;
            int x = left + k1 * 8;
            int y = top - j1 * rowHeight;
            if (health + shieldHealth <= 4)
            {
                y += rand.nextInt(2);
            }

            boolean flag = i1 >= brokenHearts + playerHearts;
            if (flag)
            {
                renderHeart(gui, HeartType.CONTAINER, x, y, blinkOffset, false);
                renderHeart(gui, HeartType.SHIELD, x, y, blinkOffset, shieldHealth < shieldHearts * 2 && i1 == shieldHearts - 1);
            } else
            {
                renderHeart(gui, HeartType.BROKEN, x, y, blinkOffset, false);
            }
        }
    }

    public static void renderHeart(GuiGraphicsExtractor gui, HeartType heartType, int x, int y, int yTexture, boolean halfIcon)
    {
        // Use sprite-based blit for new heart sprites, but keep BROKEN from our mod texture sheet
        if (heartType.isTextureBased())
        {
            gui.blit(RenderPipelines.GUI_TEXTURED, heartType.location, x, y, (float) heartType.getX(halfIcon), (float) yTexture,
                    HEART_TEXTURE_WIDTH, HEART_TEXTURE_HEIGHT, HEART_TEXTURE_WIDTH, HEART_TEXTURE_HEIGHT, 256, 256);
        } else
        {
            gui.blitSprite(RenderPipelines.GUI_TEXTURED, heartType.getSprite(halfIcon), x, y, HEART_TEXTURE_WIDTH, HEART_TEXTURE_HEIGHT);
        }
    }

    public enum HeartType
    {
        // Vanilla heart sprites (1.20.6+): hud/heart/container, hud/heart/full, hud/heart/half
        CONTAINER(Identifier.withDefaultNamespace("hud/heart/container")),
        SHIELD(Identifier.withDefaultNamespace("hud/heart/absorbing_full"), Identifier.withDefaultNamespace("hud/heart/absorbing_half")),
        // Keep BROKEN from our mod texture atlas (overlay.png)
        BROKEN(ICONS, 0);

        // Sprite-based
        private final Identifier fullSprite;
        private final Identifier halfSprite; // may be null when not applicable
        // Texture-based (for BROKEN)
        private final Identifier location;
        private final int index;

        HeartType(Identifier singleSprite)
        {
            this.fullSprite = singleSprite;
            this.halfSprite = null;
            this.location = null;
            this.index = 0;
        }

        HeartType(Identifier fullSprite, Identifier halfSprite)
        {
            this.fullSprite = fullSprite;
            this.halfSprite = halfSprite;
            this.location = null;
            this.index = 0;
        }

        HeartType(Identifier location, int index)
        {
            this.location = location;
            this.index = index;
            this.fullSprite = null;
            this.halfSprite = null;
        }

        public boolean isTextureBased()
        {
            return this == BROKEN;
        }

        public Identifier getSprite(boolean halfIcon)
        {
            if (this == SHIELD && halfIcon && this.halfSprite != null)
            {
                return this.halfSprite;
            }
            return this.fullSprite;
        }

        public int getX(boolean halfIcon)
        {
            // Maintain original UV logic for the custom broken heart in overlay.png
            // This used to offset into the old icons grid; overlay aligns to that layout.
            return (16 + this.index) * HEART_TEXTURE_WIDTH;
        }
    }
}
