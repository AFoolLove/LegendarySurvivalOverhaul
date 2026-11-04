package sfiomn.legendarysurvivaloverhaul.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.enchantments.ModEnchantments;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends EnchantmentTagsProvider
{
    public ModEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, LegendarySurvivalOverhaul.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .addOptional(ModEnchantments.REFRESHING)
                .addOptional(ModEnchantments.PURITY)
                .addOptional(ModEnchantments.RESERVOIR);
        tag(EnchantmentTags.NON_TREASURE)
                .addOptional(ModEnchantments.REFRESHING)
                .addOptional(ModEnchantments.PURITY)
                .addOptional(ModEnchantments.RESERVOIR);
        tag(EnchantmentTags.TRADEABLE)
                .addOptional(ModEnchantments.REFRESHING)
                .addOptional(ModEnchantments.PURITY)
                .addOptional(ModEnchantments.RESERVOIR);
    }
}