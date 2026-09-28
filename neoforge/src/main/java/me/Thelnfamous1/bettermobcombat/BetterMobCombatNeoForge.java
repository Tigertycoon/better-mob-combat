package me.Thelnfamous1.bettermobcombat;

import me.Thelnfamous1.bettermobcombat.network.BMCNeoForgeNetwork;
import me.Thelnfamous1.bettermobcombat.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(Constants.MOD_ID)
public class BetterMobCombatNeoForge {

    public BetterMobCombatNeoForge(IEventBus modEventBus) {
        BetterMobCombat.init();
        if (FMLEnvironment.dist.isClient()) {
            BetterMobCombatClient.init();
        }
        // Register network packets via mod event bus
        modEventBus.addListener(BMCNeoForgeNetwork::register);
        // Sync server config when a player logs in
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) ->
                Services.PLATFORM.syncServerConfig((ServerPlayer) event.getEntity()));
    }
}
