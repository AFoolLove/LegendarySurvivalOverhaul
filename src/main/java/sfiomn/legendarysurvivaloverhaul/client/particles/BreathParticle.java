package sfiomn.legendarysurvivaloverhaul.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

//  26.3: TextureSheetParticle was replaced by SingleQuadParticle, ParticleRenderType constants were
//  replaced by SingleQuadParticle.Layer values and ParticleProvider#createParticle now receives the
//  RandomSource used to create the particle.
public class BreathParticle extends SingleQuadParticle
{

    protected final SpriteSet animatedSprite;

    public BreathParticle(SpriteSet animatedSprite, ClientLevel level, double x, double y, double z, double xd, double yd, double zd)
    {
        super(level, x, y, z, xd, yd, zd, animatedSprite.first());
        this.xd *= 0.1f;
        this.yd *= 0.1f;
        this.zd *= 0.1f;

        this.setSpriteFromAge(animatedSprite);
        this.animatedSprite = animatedSprite;

        this.lifetime = (int) (10.0D / (random.nextDouble() + 0.3D));

        this.alpha = 0.25f;
        this.friction = 0.8F;
        this.hasPhysics = false;
    }

    @Override
    public void tick()
    {
        super.tick();
        this.setSpriteFromAge(this.animatedSprite);
    }

    @Override
    protected @NotNull SingleQuadParticle.Layer getLayer()
    {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>
    {

        private final SpriteSet animatedSprite;

        public Factory(SpriteSet animatedSprite)
        {
            this.animatedSprite = animatedSprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd, RandomSource random)
        {
            return new BreathParticle(this.animatedSprite, level, x, y, z, xd, yd, zd);
        }
    }
}
