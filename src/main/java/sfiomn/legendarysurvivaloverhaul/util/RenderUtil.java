package sfiomn.legendarysurvivaloverhaul.util;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

/**
 * Rendering helpers for the 26.3 {@code GuiGraphicsExtractor} GUI render-state system.
 *
 * <p>In 26.3 the old {@code GuiGraphics#blit(ResourceLocation, x, y, u, v, w, h)} overloads no longer
 * exist. Every blit now requires an explicit {@code RenderPipeline} (and, when a tint is needed, an ARGB
 * colour). The helpers below restore the old "texture is 256x256" convenience behaviour on top of the
 * new API so the HUD code stays readable.</p>
 */
public final class RenderUtil
{
    /** Default texture size assumed by the old implicit 7-argument {@code blit}. */
    private static final int DEFAULT_TEXTURE_SIZE = 256;

    private RenderUtil()
    {
    }

    /**
     * Opaque blit with the legacy 256x256 texture-size assumption.
     */
    public static void blit(GuiGraphicsExtractor gui, Identifier texture, int x, int y, int u, int v, int width, int height)
    {
        blit(gui, texture, x, y, u, v, width, height, DEFAULT_TEXTURE_SIZE, DEFAULT_TEXTURE_SIZE);
    }

    /**
     * Blit with an explicit texture atlas size.
     */
    public static void blit(GuiGraphicsExtractor gui, Identifier texture, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight)
    {
        gui.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, (float) u, (float) v, width, height, width, height, textureWidth, textureHeight);
    }

    /**
     * Alpha-tinted blit (replaces the old {@code RenderSystem.setShaderColor(r, g, b, alpha) + blit} pattern).
     */
    public static void blitAlpha(GuiGraphicsExtractor gui, Identifier texture, int x, int y, int u, int v, int width, int height, float alpha)
    {
        blitAlpha(gui, texture, x, y, u, v, width, height, DEFAULT_TEXTURE_SIZE, DEFAULT_TEXTURE_SIZE, alpha);
    }

    /**
     * Alpha-tinted blit with an explicit texture atlas size.
     */
    public static void blitAlpha(GuiGraphicsExtractor gui, Identifier texture, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, float alpha)
    {
        gui.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, (float) u, (float) v, width, height, width, height, textureWidth, textureHeight, ARGB.white(alpha));
    }

    /**
     * Draws a full-screen alpha-blended texture overlay (used by the frostbite / heat-stroke overlays).
     */
    public static void renderTextureOverlay(GuiGraphicsExtractor gui, Identifier texture, int screenWidth, int screenHeight, float alpha)
    {
        if (texture == null || alpha <= 0.0f)
        {
            return;
        }

        gui.blit(RenderPipelines.GUI_TEXTURED, texture, 0, 0, 0.0f, 0.0f, screenWidth, screenHeight,
                screenWidth, screenHeight, screenWidth, screenHeight, ARGB.white(alpha));
    }
}
