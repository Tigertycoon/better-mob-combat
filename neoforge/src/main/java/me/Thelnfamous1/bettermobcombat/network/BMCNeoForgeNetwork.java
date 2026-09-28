package me.Thelnfamous1.bettermobcombat.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class BMCNeoForgeNetwork {

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                S2CAttackAnimation.TYPE,
                S2CAttackAnimation.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        BMCClientNetworkHandler.handleAttackAnimation(
                                payload.mobId(), payload.animationName(), payload.length(),
                                payload.animatedHand(), payload.upswing()))
        );

        registrar.playToClient(
                S2CAttackSound.TYPE,
                S2CAttackSound.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        BMCClientNetworkHandler.handlePlaySound(
                                payload.mobId(), payload.x(), payload.y(), payload.z(),
                                payload.soundId(), payload.volume(), payload.pitch(), payload.seed()))
        );

        registrar.playToClient(
                S2CComboCountSync.TYPE,
                S2CComboCountSync.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        BMCClientNetworkHandler.handleComboSync(payload.mobId(), payload.comboCount()))
        );

        registrar.playToClient(
                S2CConfigSync.TYPE,
                S2CConfigSync.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        BMCClientNetworkHandler.handleConfigSync(payload.json()))
        );
    }
}
