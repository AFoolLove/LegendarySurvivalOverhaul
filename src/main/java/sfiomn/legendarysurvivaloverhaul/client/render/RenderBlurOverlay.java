package sfiomn.legendarysurvivaloverhaul.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.attachments.thirst.ThirstAttachment;
import sfiomn.legendarysurvivaloverhaul.util.AttachmentUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Thirst low-hydration screen blur.
 *
 * 26.3 replaced the old client-side PostChain API with data-driven post effects:
 * we ship three blur variants (assets/legendarysurvivaloverhaul/post_effect/thirst_blur_*.json,
 * based on vanilla's box blur) and swap them into the local player's active post effect list
 * depending on the current blur intensity. The list is reconciled every frame so server-sent
 * post effect changes (e.g. blindness) are preserved.
 */
public class RenderBlurOverlay
{
    private static final float DEFAULT_SHADER_INTENSITY = 0;
    private static final float MAX_SHADER_INTENSITY = 4;
    private static final float SHADER_INTENSITY_STEP = 0.05f;
    private static final int HYDRATION_LEVEL_MIN_EFFECT = 6;
    private static final int HYDRATION_LEVEL_MAX_EFFECT = 2;

    private static final Identifier BLUR_SOFT =
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "thirst_blur_1");
    private static final Identifier BLUR_MEDIUM =
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "thirst_blur_2");
    private static final Identifier BLUR_STRONG =
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "thirst_blur_3");
    private static final List<Identifier> BLUR_EFFECTS = List.of(BLUR_SOFT, BLUR_MEDIUM, BLUR_STRONG);

    private static float shaderIntensity = 0;
    private static int updateTimer = 0;
    private static boolean hasShownBlurWarning = false;

    public static void render(Player player)
    {
        boolean visible = shaderIntensity > 0
                && !player.isSpectator()
                && !player.isCreative()
                && !(Minecraft.getInstance().gui.screen() instanceof DeathScreen);

        Identifier desired = !visible ? null : desiredBlurEffect();
        reconcilePostEffect(player, desired);
    }

    private static Identifier desiredBlurEffect()
    {
        if (shaderIntensity <= 0.5f)
            return null;
        if (shaderIntensity <= 1.5f)
            return BLUR_SOFT;
        if (shaderIntensity <= 2.8f)
            return BLUR_MEDIUM;
        return BLUR_STRONG;
    }

    private static void reconcilePostEffect(Player player, @Nullable Identifier desired)
    {
        if (!(player instanceof LocalPlayer localPlayer))
            return;

        List<Identifier> active = localPlayer.getActivePostEffects();
        boolean hasOurs = active.stream().anyMatch(BLUR_EFFECTS::contains);

        if (desired == null && !hasOurs)
            return;

        List<Identifier> updated = new ArrayList<>(active);
        updated.removeAll(BLUR_EFFECTS);
        if (desired != null)
            updated.add(desired);

        if (!updated.equals(active))
        {
            LegendarySurvivalOverhaul.LOGGER.debug("Thirst blur post effects: {}", updated);
            localPlayer.setActivePostEffects(updated);
        }
    }

    public static void updateBlurIntensity(@Nullable Player player)
    {
        float targetShaderIntensity = DEFAULT_SHADER_INTENSITY;
        if (player != null && player.isAlive() && !player.isCreative() && !player.isSpectator())
        {
            ThirstAttachment thirstCap = AttachmentUtil.getThirstAttachment(player);
            // hydration is 0 - 20
            int hydration = thirstCap.getHydrationLevel();

            if (hydration <= HYDRATION_LEVEL_MIN_EFFECT)
            {
                targetShaderIntensity = (1 - ((float) (hydration - HYDRATION_LEVEL_MAX_EFFECT) / (float) (HYDRATION_LEVEL_MIN_EFFECT - HYDRATION_LEVEL_MAX_EFFECT))) * MAX_SHADER_INTENSITY;

                // Show first-time blur warning
                if (!hasShownBlurWarning && targetShaderIntensity > 0)
                {
                    player.sendOverlayMessage(Component.translatable("message.legendarysurvivaloverhaul.thirst.blur_warning"));
                    hasShownBlurWarning = true;
                }
            }

            if (updateTimer++ % 2 == 0)
            {
                if (targetShaderIntensity > shaderIntensity)
                {
                    shaderIntensity = Math.min(shaderIntensity + SHADER_INTENSITY_STEP, targetShaderIntensity);
                } else if (targetShaderIntensity < shaderIntensity)
                {
                    shaderIntensity = Math.max(shaderIntensity - SHADER_INTENSITY_STEP, targetShaderIntensity);
                }
            }
        } else
        {
            shaderIntensity = 0;
        }
    }
}