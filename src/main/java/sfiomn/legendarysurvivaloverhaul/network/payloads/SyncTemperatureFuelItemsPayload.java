package sfiomn.legendarysurvivaloverhaul.network.payloads;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureFuelItem;
import sfiomn.legendarysurvivaloverhaul.common.listeners.TemperatureFuelItemListener;

import java.util.HashMap;
import java.util.Map;

public record SyncTemperatureFuelItemsPayload(
        Map<Identifier, JsonTemperatureFuelItem> temperatureFuelItems
) implements CustomPacketPayload
{

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "sync_temperature_fuel_items");

    public static final Type<SyncTemperatureFuelItemsPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTemperatureFuelItemsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.map(
                            HashMap::new,
                            Identifier.STREAM_CODEC,
                            ByteBufCodecs.fromCodecTrusted(JsonTemperatureFuelItem.CODEC)
                    ),
                    SyncTemperatureFuelItemsPayload::temperatureFuelItems,
                    SyncTemperatureFuelItemsPayload::new
            );

    public static void handle(SyncTemperatureFuelItemsPayload pkt, IPayloadContext ctx)
    {
        if (ctx.flow() != PacketFlow.CLIENTBOUND) return;
        ctx.enqueueWork(() -> TemperatureFuelItemListener.acceptServerTemperatureFuelItems(pkt.temperatureFuelItems()));
    }

    public static void sendToServer(Map<Identifier, JsonTemperatureFuelItem> data)
    {
        PacketDistributor.sendToServer(new SyncTemperatureFuelItemsPayload(data));
    }

    public static void sendToPlayer(net.minecraft.server.level.ServerPlayer player, Map<Identifier, JsonTemperatureFuelItem> data)
    {
        PacketDistributor.sendToPlayer(player, new SyncTemperatureFuelItemsPayload(data));
    }

    public static void sendToAll(Map<Identifier, JsonTemperatureFuelItem> data)
    {
        PacketDistributor.sendToAllPlayers(new SyncTemperatureFuelItemsPayload(data));
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
