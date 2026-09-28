package me.Thelnfamous1.bettermobcombat.network;

import me.Thelnfamous1.bettermobcombat.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record S2CAttackSound(int mobId, double x, double y, double z, String soundId, float volume, float pitch, long seed)
        implements CustomPacketPayload {

    public static final Type<S2CAttackSound> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "attack_sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CAttackSound> STREAM_CODEC =
            StreamCodec.of(S2CAttackSound::encode, S2CAttackSound::decode);

    private static void encode(RegistryFriendlyByteBuf buf, S2CAttackSound packet) {
        buf.writeInt(packet.mobId);
        buf.writeDouble(packet.x);
        buf.writeDouble(packet.y);
        buf.writeDouble(packet.z);
        buf.writeUtf(packet.soundId);
        buf.writeFloat(packet.volume);
        buf.writeFloat(packet.pitch);
        buf.writeLong(packet.seed);
    }

    private static S2CAttackSound decode(RegistryFriendlyByteBuf buf) {
        int mobId = buf.readInt();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        String soundId = buf.readUtf();
        float volume = buf.readFloat();
        float pitch = buf.readFloat();
        long seed = buf.readLong();
        return new S2CAttackSound(mobId, x, y, z, soundId, volume, pitch, seed);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
