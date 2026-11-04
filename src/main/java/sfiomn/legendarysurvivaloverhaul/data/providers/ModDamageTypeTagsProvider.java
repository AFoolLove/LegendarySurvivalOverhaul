package sfiomn.legendarysurvivaloverhaul.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider
{
    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, LegendarySurvivalOverhaul.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.tag(DamageTypeTags.BYPASSES_ARMOR)
                .addOptional(ModDamageTypes.HYPOTHERMIA)
                .addOptional(ModDamageTypes.HYPERTHERMIA)
                .addOptional(ModDamageTypes.DEHYDRATION);
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                .addOptional(ModDamageTypes.HYPOTHERMIA)
                .addOptional(ModDamageTypes.HYPERTHERMIA)
                .addOptional(ModDamageTypes.DEHYDRATION);
        this.tag(DamageTypeTags.BYPASSES_RESISTANCE)
                .addOptional(ModDamageTypes.HYPOTHERMIA)
                .addOptional(ModDamageTypes.HYPERTHERMIA)
                .addOptional(ModDamageTypes.DEHYDRATION);
    }
}