package sfiomn.legendarysurvivaloverhaul.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.network.payloads.*;

@EventBusSubscriber(modid = LegendarySurvivalOverhaul.MOD_ID)
public class NetworkHandler
{

    public NetworkHandler()
    {
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event)
    {
        PayloadRegistrar reg = event.registrar(LegendarySurvivalOverhaul.MOD_ID).versioned("1");

        reg.playBidirectional(UpdateTemperaturesPayload.TYPE, UpdateTemperaturesPayload.STREAM_CODEC,
                UpdateTemperaturesPayload::handle, UpdateTemperaturesPayload::handle);

        reg.playBidirectional(UpdateWetnessPayload.TYPE, UpdateWetnessPayload.STREAM_CODEC,
                UpdateWetnessPayload::handle, UpdateWetnessPayload::handle);

        reg.playBidirectional(UpdateThirstPayload.TYPE, UpdateThirstPayload.STREAM_CODEC,
                UpdateThirstPayload::handle, UpdateThirstPayload::handle);

        reg.playBidirectional(UpdateHeartsPayload.TYPE, UpdateHeartsPayload.STREAM_CODEC,
                UpdateHeartsPayload::handle, UpdateHeartsPayload::handle);

        reg.playBidirectional(UpdateBodyDamagePayload.TYPE, UpdateBodyDamagePayload.STREAM_CODEC,
                UpdateBodyDamagePayload::handle, UpdateBodyDamagePayload::handle);

        reg.playBidirectional(DrinkBlockFluidMessage.TYPE, DrinkBlockFluidMessage.STREAM_CODEC,
                DrinkBlockFluidMessage::handle, DrinkBlockFluidMessage::handle);

        reg.playBidirectional(BodyPartHealingTimeMessage.TYPE, BodyPartHealingTimeMessage.STREAM_CODEC,
                BodyPartHealingTimeMessage::handle, BodyPartHealingTimeMessage::handle);

        reg.playBidirectional(SyncTemperatureConsumablesPayload.TYPE, SyncTemperatureConsumablesPayload.STREAM_CODEC,
                SyncTemperatureConsumablesPayload::handle, SyncTemperatureConsumablesPayload::handle);
        reg.playBidirectional(SyncTemperatureConsumableBlocksPayload.TYPE, SyncTemperatureConsumableBlocksPayload.STREAM_CODEC,
                SyncTemperatureConsumableBlocksPayload::handle, SyncTemperatureConsumableBlocksPayload::handle);
        reg.playBidirectional(SyncTemperatureBlocksPayload.TYPE, SyncTemperatureBlocksPayload.STREAM_CODEC,
                SyncTemperatureBlocksPayload::handle, SyncTemperatureBlocksPayload::handle);
        reg.playBidirectional(SyncTemperatureItemsPayload.TYPE, SyncTemperatureItemsPayload.STREAM_CODEC,
                SyncTemperatureItemsPayload::handle, SyncTemperatureItemsPayload::handle);
        reg.playBidirectional(SyncTemperatureBiomesPayload.TYPE, SyncTemperatureBiomesPayload.STREAM_CODEC,
                SyncTemperatureBiomesPayload::handle, SyncTemperatureBiomesPayload::handle);
        reg.playBidirectional(SyncTemperatureFuelItemsPayload.TYPE, SyncTemperatureFuelItemsPayload.STREAM_CODEC,
                SyncTemperatureFuelItemsPayload::handle, SyncTemperatureFuelItemsPayload::handle);
        reg.playBidirectional(SyncTemperatureMountsPayload.TYPE, SyncTemperatureMountsPayload.STREAM_CODEC,
                SyncTemperatureMountsPayload::handle, SyncTemperatureMountsPayload::handle);
        reg.playBidirectional(SyncTemperatureDimensionsPayload.TYPE, SyncTemperatureDimensionsPayload.STREAM_CODEC,
                SyncTemperatureDimensionsPayload::handle, SyncTemperatureDimensionsPayload::handle);
        reg.playBidirectional(SyncTemperatureOriginsPayload.TYPE, SyncTemperatureOriginsPayload.STREAM_CODEC,
                SyncTemperatureOriginsPayload::handle, SyncTemperatureOriginsPayload::handle);

        reg.playBidirectional(SyncThirstBlocksPayload.TYPE, SyncThirstBlocksPayload.STREAM_CODEC,
                SyncThirstBlocksPayload::handle, SyncThirstBlocksPayload::handle);
        reg.playBidirectional(SyncThirstConsumablesPaload.TYPE, SyncThirstConsumablesPaload.STREAM_CODEC,
                SyncThirstConsumablesPaload::handle, SyncThirstConsumablesPaload::handle);

        reg.playBidirectional(SyncBodyDamageHealingConsumablesPayload.TYPE, SyncBodyDamageHealingConsumablesPayload.STREAM_CODEC,
                SyncBodyDamageHealingConsumablesPayload::handle, SyncBodyDamageHealingConsumablesPayload::handle);
        reg.playBidirectional(SyncBodyPartsDamageSourcesPayload.TYPE, SyncBodyPartsDamageSourcesPayload.STREAM_CODEC,
                SyncBodyPartsDamageSourcesPayload::handle, SyncBodyPartsDamageSourcesPayload::handle);
        reg.playBidirectional(SyncBodyPartResistanceItemsPayload.TYPE, SyncBodyPartResistanceItemsPayload.STREAM_CODEC,
                SyncBodyPartResistanceItemsPayload::handle, SyncBodyPartResistanceItemsPayload::handle);
    }
}
