package sfiomn.legendarysurvivaloverhaul.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.client.itemproperties.CanteenProperty;
import sfiomn.legendarysurvivaloverhaul.client.itemproperties.SeasonalCalendarSeasonTypeProperty;
import sfiomn.legendarysurvivaloverhaul.client.itemproperties.SeasonalCalendarTimeProperty;
import sfiomn.legendarysurvivaloverhaul.client.itemproperties.ThermometerProperty;
import sfiomn.legendarysurvivaloverhaul.client.particles.BreathParticle;
import sfiomn.legendarysurvivaloverhaul.client.particles.FernBlossomParticle;
import sfiomn.legendarysurvivaloverhaul.client.render.*;
import sfiomn.legendarysurvivaloverhaul.client.tooltips.HydrationClientTooltipComponent;
import sfiomn.legendarysurvivaloverhaul.client.tooltips.HydrationTooltipComponent;
import sfiomn.legendarysurvivaloverhaul.registry.ParticleTypeRegistry;

@EventBusSubscriber(modid = LegendarySurvivalOverhaul.MOD_ID, value = Dist.CLIENT)
public class ClientModBusEvents
{

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event)
    {
        // 26.3: LayeredDraw.Layer was replaced by the NeoForge GuiLayer interface
        // (render(GuiGraphicsExtractor, DeltaTracker)).
        GuiLayer healthLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderHealthGui.render(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        GuiLayer coldHungerLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderTemperatureGui.renderColdHunger(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        GuiLayer thirstLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderThirstGui.render(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        GuiLayer temperatureLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderTemperatureGui.renderTemperature(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        GuiLayer wetnessLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderWetnessGui.render(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        GuiLayer bodyDamageLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderBodyDamageGui.render(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        GuiLayer temperatureOverlayLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderTemperatureOverlay.render(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        GuiLayer itemFrameTooltipLayer = (guiGraphics, delta) -> {
            Gui gui = Minecraft.getInstance().gui;
            int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            RenderTooltipFrame.render(gui, guiGraphics, delta.getGameTimeDeltaPartialTick(false), w, h);
        };

        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "health_overhaul"), healthLayer);
        event.registerBelow(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "cold_hunger"), coldHungerLayer);
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "thirst"), thirstLayer);
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "temperature"), temperatureLayer);
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "wetness"), wetnessLayer);
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "body_damage"), bodyDamageLayer);
        // Anchor overlays above commonly available layers to ensure rendering order
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "temperature_overlay"), temperatureOverlayLayer);
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "item_frame_tooltip"), itemFrameTooltipLayer);
    }

    /**
     * 26.3 item-model properties are registered as codecs and referenced from item model JSON.
     * The item models must use {@code "type": "minecraft:range_dispatch"} with the ids registered below
     * (see docs/PORTING_NOTES.md / the WS-C report for the JSON structure).
     */
    @SubscribeEvent
    public static void registerItemModelProperties(RegisterRangeSelectItemModelPropertyEvent event)
    {
        event.register(Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "temperature"), ThermometerProperty.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "thirstenum"), CanteenProperty.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "time"), SeasonalCalendarTimeProperty.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "seasontype"), SeasonalCalendarSeasonTypeProperty.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onTooltipRegistration(RegisterClientTooltipComponentFactoriesEvent event)
    {
        event.register(HydrationTooltipComponent.class, component -> new HydrationClientTooltipComponent(component.hydration(), component.saturation()));
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event)
    {
        event.registerSpriteSet(ParticleTypeRegistry.SUN_FERN_BLOSSOM.get(), FernBlossomParticle.Factory::new);
        event.registerSpriteSet(ParticleTypeRegistry.ICE_FERN_BLOSSOM.get(), FernBlossomParticle.Factory::new);
        event.registerSpriteSet(ParticleTypeRegistry.COLD_BREATH.get(), BreathParticle.Factory::new);
    }
}
