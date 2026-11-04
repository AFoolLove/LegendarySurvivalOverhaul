package sfiomn.legendarysurvivaloverhaul.registry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.block.ThermalTypeEnum;
import sfiomn.legendarysurvivaloverhaul.common.blocks.CoolerBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.HeaterBaseBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.HeaterTopBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.IceFernBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.IceFernGoldBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.SewingTableBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.SunFernBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.SunFernGoldBlock;
import sfiomn.legendarysurvivaloverhaul.common.blocks.WaterPlantBlock;

public class BlockRegistry
{
    // 26.3: BlockBehaviour.Properties must already carry its id when the Block is constructed, otherwise
    // effectiveDrops()/descriptionId NPE ("Block id not set"). The specialized DeferredRegister.Blocks
    // helpers call setId(...) for us; block items are registered in ItemRegistry (registerSimpleBlockItem).
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(LegendarySurvivalOverhaul.MOD_ID);

    public static final DeferredBlock<HeaterBaseBlock> HEATER =
            BLOCKS.registerBlock("heater", p -> new HeaterBaseBlock(ThermalTypeEnum.HEATING, p), HeaterBaseBlock::getProperties);

    public static final DeferredBlock<HeaterTopBlock> HEATER_TOP =
            BLOCKS.registerBlock("heater_top", HeaterTopBlock::new, HeaterTopBlock::getProperties);

    public static final DeferredBlock<CoolerBlock> COOLER =
            BLOCKS.registerBlock("cooler", p -> new CoolerBlock(ThermalTypeEnum.COOLING, p), CoolerBlock::getProperties);

    public static final DeferredBlock<SewingTableBlock> SEWING_TABLE =
            BLOCKS.registerBlock("sewing_table", SewingTableBlock::new, SewingTableBlock::getProperties);

    public static final DeferredBlock<SunFernBlock> SUN_FERN_CROP =
            BLOCKS.registerBlock("sun_fern_crop", SunFernBlock::new, SunFernBlock::getProperties);

    public static final DeferredBlock<SunFernGoldBlock> SUN_FERN_GOLD =
            BLOCKS.registerBlock("sun_fern_gold", SunFernGoldBlock::new, SunFernGoldBlock::getProperties);

    public static final DeferredBlock<IceFernBlock> ICE_FERN_CROP =
            BLOCKS.registerBlock("ice_fern_crop", IceFernBlock::new, IceFernBlock::getProperties);

    public static final DeferredBlock<IceFernGoldBlock> ICE_FERN_GOLD =
            BLOCKS.registerBlock("ice_fern_gold", IceFernGoldBlock::new, IceFernGoldBlock::getProperties);

    public static final DeferredBlock<WaterPlantBlock> WATER_PLANT_CROP =
            BLOCKS.registerBlock("water_plant_crop", WaterPlantBlock::new, WaterPlantBlock::getProperties);

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
