package sfiomn.legendarysurvivaloverhaul.common.attachments.health;

import com.mojang.serialization.MapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import sfiomn.legendarysurvivaloverhaul.api.health.IHealthAttachment;
import sfiomn.legendarysurvivaloverhaul.config.Config;

public class HealthAttachment implements IHealthAttachment, ValueIOSerializable
{
    private float additionalHealth;
    private float shieldHealth;

    // Unsaved Data
    private float oldAdditionalHealth;
    private float oldShieldHealth;
    private int packetTimer;

    public HealthAttachment()
    {
        this.init();
    }

    public void init()
    {
        additionalHealth = 0;
        shieldHealth = 0;

        oldAdditionalHealth = 0;
        oldShieldHealth = 0;
        packetTimer = 0;
    }

    @Override
    public void addAdditionalHealth(float healthValue)
    {
        this.setAdditionalHealth(this.getAdditionalHealth() + healthValue);
    }

    @Override
    public void addShieldHealth(float shieldValue)
    {
        this.setShieldHealth(this.getShieldHealth() + shieldValue);
    }

    @Override
    public float getAdditionalHealth()
    {
        return additionalHealth;
    }

    @Override
    public void setAdditionalHealth(float newHealthValue)
    {
        this.additionalHealth = Math.min(newHealthValue, (float) Config.Baked.maxAdditionalHealth);
    }

    @Override
    public float getShieldHealth()
    {
        return shieldHealth;
    }

    @Override
    public void setShieldHealth(float newHealthValue)
    {
        this.shieldHealth = Mth.clamp(newHealthValue, 0.0f, (float) Config.Baked.maxShieldHealth);
    }

    @Override
    public boolean isDirty()
    {
        return additionalHealth != oldAdditionalHealth ||
                shieldHealth != oldShieldHealth;
    }

    @Override
    public void setClean()
    {
        oldAdditionalHealth = additionalHealth;
        oldShieldHealth = shieldHealth;
    }

    @Override
    public int getPacketTimer()
    {
        return packetTimer;
    }

    public CompoundTag writeNBT()
    {
        CompoundTag compound = new CompoundTag();

        compound.putFloat("additionalHealth", getAdditionalHealth());
        compound.putFloat("shieldHealth", getShieldHealth());

        return compound;
    }

    public void readNBT(CompoundTag compound)
    {
        this.init();

        if (compound.contains("additionalHealth"))
            this.setAdditionalHealth(compound.getFloatOr("additionalHealth", 0.0f));
        if (compound.contains("shieldHealth"))
            this.setShieldHealth(compound.getFloatOr("shieldHealth", 0.0f));
    }

    @Override
    public void serialize(ValueOutput output)
    {
        output.store(writeNBT());
    }

    @Override
    public void deserialize(ValueInput input)
    {
        input.read(MapCodec.assumeMapUnsafe(CompoundTag.CODEC)).ifPresent(this::readNBT);
    }
}
