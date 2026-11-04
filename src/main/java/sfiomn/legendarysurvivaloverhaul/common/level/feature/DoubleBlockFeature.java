package sfiomn.legendarysurvivaloverhaul.common.level.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * 26.3: {@link Feature} is no longer generic and configured features are plain feature instances
 * holding their own configuration; the old {@code FeaturePlaceContext} / configuration records are gone.
 */
public record DoubleBlockFeature(Holder<BlockStateProvider> toPlace) implements Feature
{
    public static final MapCodec<DoubleBlockFeature> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(BlockStateProvider.CODEC.fieldOf("to_place").forGetter(DoubleBlockFeature::toPlace))
                    .apply(i, DoubleBlockFeature::new));

    public DoubleBlockFeature(BlockStateProvider toPlace)
    {
        this(Holder.direct(toPlace));
    }

    @Override
    public MapCodec<DoubleBlockFeature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin)
    {
        BlockState state = this.toPlace.value().getOptionalState(level, random, origin);
        if (state == null)
            return false;

        if (state.canSurvive(level, origin))
        {
            if (!level.isEmptyBlock(origin.above()))
            {
                return false;
            }

            DoublePlantBlock.placeAt(level, state, origin, 2);

            return true;
        } else
        {
            return false;
        }
    }
}
