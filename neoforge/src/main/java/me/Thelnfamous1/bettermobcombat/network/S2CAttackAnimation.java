package me.Thelnfamous1.bettermobcombat.network;

import me.Thelnfamous1.bettermobcombat.Constants;
import net.bettercombat.logic.AnimatedHand;
import net.bettercombat.network.Packets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record S2CAttackAnimation(int mobId, AnimatedHand animatedHand, String animationName, float length, float upswing)
        implements CustomPacketPayload {

    public static final Type<S2CAttackAnimation> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "attack_animation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CAttackAnimation> STREAM_CODEC =
            StreamCodec.of(S2CAttackAnimation::encode, S2CAttackAnimation::decode);

    private static void encode(RegistryFriendlyByteBuf buf, S2CAttackAnimation packet) {
        buf.writeInt(packet.mobId);
        buf.writeInt(packet.animatedHand.ordinal());
        buf.writeUtf(packet.animationName);
        buf.writeFloat(packet.length);
        buf.writeFloat(packet.upswing);
    }

    private static S2CAttackAnimation decode(RegistryFriendlyByteBuf buf) {
        int mobId = buf.readInt();
        AnimatedHand animatedHand = AnimatedHand.values()[buf.readInt()];
        String animationName = buf.readUtf();
        float length = buf.readFloat();
        float upswing = buf.readFloat();
        return new S2CAttackAnimation(mobId, animatedHand, animationName, length, upswing);
    }

    /** Creates a stop-animation packet. */
    public static S2CAttackAnimation stop(int mobId, int downWind) {
        return new S2CAttackAnimation(mobId, AnimatedHand.MAIN_HAND, Packets.AttackAnimation.StopSymbol, (float) downWind, 0.0F);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
