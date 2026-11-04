package sfiomn.legendarysurvivaloverhaul.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;


@EventBusSubscriber(modid = LegendarySurvivalOverhaul.MOD_ID, value = Dist.CLIENT)
public class KeyMappingRegistry
{
    // Translation key resolved by KeyMapping.Category: "key.category.legendarysurvivaloverhaul.main"
    private static final KeyMapping.Category CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(LegendarySurvivalOverhaul.MOD_ID, "main"));

    public static KeyMapping showAddedDesc;
    public static KeyMapping showBodyHealth;

    @SubscribeEvent
    public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event)
    {
        event.registerCategory(CATEGORY);
        showAddedDesc = create("added_desc", InputConstants.KEY_LSHIFT, KeyConflictContext.GUI);
        event.register(showAddedDesc);
        showBodyHealth = create("body_health", InputConstants.KEY_H, KeyConflictContext.UNIVERSAL);
        event.register(showBodyHealth);
    }

    private static KeyMapping create(String name, int key, KeyConflictContext context)
    {
        return new KeyMapping("key." + LegendarySurvivalOverhaul.MOD_ID + "." + name, context, InputConstants.Type.KEYBOARD, key, CATEGORY);
    }
}
