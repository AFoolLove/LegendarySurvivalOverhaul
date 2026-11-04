package sfiomn.legendarysurvivaloverhaul.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypesTagProvider extends EntityTypeTagsProvider
{
    public ModEntityTypesTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, LegendarySurvivalOverhaul.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
    }
}