package sfiomn.legendarysurvivaloverhaul.client.shaders;

import net.minecraft.resources.Identifier;

/**
 * Screen "focus"/blur effect driver.
 *
 * <p><b>26.3 status: temporarily disabled (no-op).</b> The legacy
 * {@code GameRenderer#loadEffect/currentEffect/shutdownEffect} post-chain API (and {@code PostChain}
 * uniforms) no longer exists. 26.3 post effects are fully data driven: a
 * {@code assets/&lt;namespace&gt;/post_effect/&lt;name&gt;.json} resource is loaded by the
 * {@code ShaderManager} and requested through {@code LocalPlayer#setActivePostEffects(List<Identifier>)}
 * (or the {@code /posteffect} command).</p>
 *
 * <p>To re-enable the low-hydration blur the port needs a post-effect JSON + pipeline (see the WS-C
 * report sent to the Lead) and a call to {@code LocalPlayer#setActivePostEffects} from
 * {@link sfiomn.legendarysurvivaloverhaul.client.render.RenderBlurOverlay}. Until that resource exists
 * this class intentionally keeps the public API but does nothing, so the game never crashes on a
 * missing shader.</p>
 */
public class FocusShader {
    public static final Identifier BLOBS2_SHADER = Identifier.withDefaultNamespace("shaders/post/blobs2.json");
    public static final Identifier BLUR_SHADER = Identifier.withDefaultNamespace("shaders/post/blur.json");

    public FocusShader() {}

    public void render(float intensity) {
        // No-op: 26.3 has no client-side "current effect" post chain API (see class javadoc).
    }

    public void stopRender() {
        // No-op: see class javadoc.
    }

    public void updateIntensity(float intensity) {
        // No-op: see class javadoc.
    }
}
