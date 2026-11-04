package sfiomn.legendarysurvivaloverhaul.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import javax.annotation.Nullable;

//  Deeply inspired by the tall AloeVera from MinecraftAbnormals
//  https://github.com/team-abnormals/atmospheric/blob/1.16.x/src/main/java/com/minecraftabnormals/atmospheric/client/particle/AloeBlossomParticle.java
//
//  26.3: TextureSheetParticle was replaced by SingleQuadParticle (particle render types are now
//  data-driven "layers" instead of ParticleRenderType constants). ParticleProvider#createParticle
//  additionally receives the RandomSource used for the particle.
public class FernBlossomParticle extends SingleQuadParticle
{
    protected final SpriteSet animatedSprite;
    private float angle;

    protected FernBlossomParticle(SpriteSet animatedSprite, ClientLevel level, double x, double y, double z, double xd, double yd, double zd)
    {
        super(level, x, y, z, xd, yd, zd, animatedSprite.first());

        this.setSpriteFromAge(animatedSprite);
        this.animatedSprite = animatedSprite;

        //PI * 2 = 6.28318530718
        this.angle = this.random.nextFloat() * (6.28318530718f);

        //  Duration of particle in ticks
        this.lifetime = this.random.nextInt(75) + 75;

        //  Scaling
        this.quadSize *= (float) (2.0F + this.random.nextFloat() * 0.5);

        //  Motion of particles
        this.xd = xd;
        this.yd = yd + (this.random.nextDouble() * 0.05D);
        this.zd = zd;

        //  Color
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.age % 5 == 0)
        {
            // PI * 2 = 6.28318530718
            this.angle = (float) (Math.random() * 6.28318530718);
        }
        this.xd += Math.cos(this.angle) * 0.0005;
        this.zd += Math.sin(this.angle) * 0.0005;
        this.setSpriteFromAge(this.animatedSprite);
        fadeOut();
    }

    private void fadeOut()
    {
        if (((float) age / lifetime) < 0.5f)
        {
            this.alpha = 1;
        } else
        {
            this.alpha = 1 - (((float) age / lifetime - 0.5f) * 2.0f);
        }
    }

    @Override
    protected int getLightCoords(float partialTick)
    {
        float f = this.lifetime / (((this.age + (this.lifetime * 0.5F)) + partialTick));
        f = Mth.clamp(f, 0F, 0.5F);
        int i = super.getLightCoords(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f * 15f * 16f);
        if (j > 240)
        {
            j = 240;
        }
        return j | k << 16;
    }

    @Override
    protected SingleQuadParticle.Layer getLayer()
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
            return new FernBlossomParticle(this.animatedSprite, level, x, y, z, xd, yd, zd);
        }
    }
}
