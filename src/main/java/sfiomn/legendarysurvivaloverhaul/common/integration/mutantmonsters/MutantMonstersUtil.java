package sfiomn.legendarysurvivaloverhaul.common.integration.mutantmonsters;

import fuzs.mutantmonsters.common.world.item.ArmorBlockItem;
import net.minecraft.world.item.Item;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

public class MutantMonstersUtil
{
    public static boolean isMutantMonstersArmor(Item item)
    {
        return LegendarySurvivalOverhaul.mutantMonstersLoaded && item instanceof ArmorBlockItem;
    }
}