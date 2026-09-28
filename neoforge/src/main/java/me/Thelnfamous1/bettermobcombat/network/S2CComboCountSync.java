package me.Thelnfamous1.bettermobcombat.network;

import me.Thelnfamous1.bettermobcombat.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record S2CComboCountSync(int mobId, int comboCount)
        implements CustomPacketPayload {

    public static final Type<S2CComboCountSync> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "combo_count_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CComboCountSync> STREAM_CODEC =
            StreamCodec.of(S2CComboCountSync::encode, S2CComboCountSync::decode);

    private static void encode(RegistryFriendlyByteBuf buf, S2CComboCountSync packet) {
        buf.writeInt(packet.mobId);
        buf.writeInt(packet.comboCount);
    }

    private static S2CComboCountSync decode(RegistryFriendlyByteBuf buf) {
        int mobId = buf.readInt();
        int comboCount = buf.readInt();
        return new S2CComboCountSync(mobId, comboCount);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
