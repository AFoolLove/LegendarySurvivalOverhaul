package sfiomn.legendarysurvivaloverhaul.common.integration.curios;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import sfiomn.legendarysurvivaloverhaul.util.AttributeBuilder;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

public class CurioAttributeBuilder extends AttributeBuilder
{
    public CurioAttributeBuilder(Holder<Attribute> attribute, Identifier descriptionId)
    {
        super(attribute, descriptionId);
    }

    public void addModifier(CurioAttributeModifierEvent event, Identifier id, double value)
    {
        // Curios 17: modifiers are added with a slot type predicate; ANY means "while equipped".
        event.addModifier(this.attribute, new AttributeModifier(id, value, AttributeModifier.Operation.ADD_VALUE));
    }
}