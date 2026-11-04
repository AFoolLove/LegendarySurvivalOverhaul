package sfiomn.legendarysurvivaloverhaul.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Base class for effects that should not be curable by milk.
 *
 * NOTE(26.3 migration): NeoForge removed the EffectCure hook (fillEffectCures) in 26.3,
 * so incurability is no longer expressible at the MobEffect level. The class is kept as a
 * marker/base for the effect hierarchy; behavior is identical to a plain MobEffect for now.
 */
public class IncurableMobEffect extends MobEffect
{
    protected IncurableMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }
}