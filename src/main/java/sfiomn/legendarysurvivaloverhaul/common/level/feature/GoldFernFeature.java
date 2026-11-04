package sfiomn.legendarysurvivaloverhaul.common.level.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import sfiomn.legendarysurvivaloverhaul.config.Config;
import sfiomn.legendarysurvivaloverhaul.registry.BlockRegistry;

/**
 * 26.3: {@link Feature} is no longer generic and configured features are plain feature instances
 * holding their own configuration; the old {@code FeaturePlaceContext} / configuration records are gone.
 */
public record GoldFernFeature(Holder<BlockStateProvider> toPlace) implements Feature
{
    public static final MapCodec<GoldFernFeature> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(BlockStateProvider.CODEC.fieldOf("to_place").forGetter(GoldFernFeature::toPlace))
                    .apply(i, GoldFernFeature::new));

    public GoldFernFeature(BlockStateProvider toPlace)
    {
        this(Holder.direct(toPlace));
    }

    @Override
    public MapCodec<GoldFernFeature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin)
    {
        BlockState state = this.toPlace.value().getOptionalState(level, random, origin);
        if (state == null)
            return false;

        if (random.nextFloat() < Config.Baked.goldFernWorldgenChance)
        {
            if (state.is(BlockRegistry.ICE_FERN_CROP.get()))
            {
                state = BlockRegistry.ICE_FERN_GOLD.get().defaultBlockState();
            } else if (state.is(BlockRegistry.SUN_FERN_CROP.get()))
            {
                state = BlockRegistry.SUN_FERN_GOLD.get().defaultBlockState();
            }
        }

        if (!state.canSurvive(level, origin))
        {
            return false;
        }

        level.setBlock(origin, state, 2);
        return true;
    }
}
