package me.Thelnfamous1.bettermobcombat.network;

import me.Thelnfamous1.bettermobcombat.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record S2CConfigSync(String json)
        implements CustomPacketPayload {

    public static final Type<S2CConfigSync> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "config_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CConfigSync> STREAM_CODEC =
            StreamCodec.of(S2CConfigSync::encode, S2CConfigSync::decode);

    private static void encode(RegistryFriendlyByteBuf buf, S2CConfigSync packet) {
        buf.writeUtf(packet.json);
    }

    private static S2CConfigSync decode(RegistryFriendlyByteBuf buf) {
        String json = buf.readUtf();
        return new S2CConfigSync(json);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
