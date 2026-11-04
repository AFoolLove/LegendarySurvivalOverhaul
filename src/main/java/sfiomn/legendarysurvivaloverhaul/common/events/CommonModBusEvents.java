package sfiomn.legendarysurvivaloverhaul.common.events;


import net.minecraft.world.entity.EntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.registry.AttributeRegistry;

@EventBusSubscriber(modid = LegendarySurvivalOverhaul.MOD_ID)
public class CommonModBusEvents
{

    @SubscribeEvent
    public static void onEntityAttributesChange(EntityAttributeModificationEvent event)
    {
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.HEATING_TEMPERATURE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.HEATING_TEMPERATURE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.COOLING_TEMPERATURE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.COOLING_TEMPERATURE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.HEAT_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.HEAT_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.COLD_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.COLD_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.THERMAL_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.THERMAL_RESISTANCE.getDelegate()
            );
        }

        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.BODY_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.BODY_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.HEAD_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.HEAD_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.CHEST_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.CHEST_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.RIGHT_ARM_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.RIGHT_ARM_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.LEFT_ARM_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.LEFT_ARM_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.LEGS_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.LEGS_RESISTANCE.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.FEET_RESISTANCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.FEET_RESISTANCE.getDelegate()
            );
        }

        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.BROKEN_HEART.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.BROKEN_HEART.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.PERMANENT_HEART.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.PERMANENT_HEART.getDelegate()
            );
        }
        if (!event.has(EntityTypes.PLAYER, AttributeRegistry.BROKEN_HEART_RESILIENCE.getDelegate()))
        {
            event.add(EntityTypes.PLAYER,
                    AttributeRegistry.BROKEN_HEART_RESILIENCE.getDelegate()
            );
        }
    }
}
