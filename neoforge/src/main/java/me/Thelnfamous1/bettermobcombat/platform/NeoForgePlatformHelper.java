package me.Thelnfamous1.bettermobcombat.platform;

import me.Thelnfamous1.bettermobcombat.BetterMobCombat;
import me.Thelnfamous1.bettermobcombat.network.*;
import me.Thelnfamous1.bettermobcombat.platform.services.IPlatformHelper;
import net.bettercombat.logic.AnimatedHand;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isCastingSpell(LivingEntity mob) {
        return false;
    }

    @Override
    public void playMobAttackAnimation(LivingEntity mob, AnimatedHand animatedHand, String animationName, float length, float upswing) {
        PacketDistributor.sendToPlayersTrackingEntity(mob,
                new S2CAttackAnimation(mob.getId(), animatedHand, animationName, length, upswing));
    }

    @Override
    public void stopMobAttackAnimation(LivingEntity mob, int downWind) {
        PacketDistributor.sendToPlayersTrackingEntity(mob,
                S2CAttackAnimation.stop(mob.getId(), downWind));
    }

    @Override
    public void syncServerConfig(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player,
                new S2CConfigSync(BetterMobCombat.getServerConfigSerialized()));
    }

    @Override
    public void playMobAttackSound(ServerLevel world, int mobId, double x, double y, double z,
                                   String soundId, float volume, float pitch, long seed,
                                   float distance, ResourceKey<Level> dimension) {
        // Send to all players in the dimension within range
        S2CAttackSound packet = new S2CAttackSound(mobId, x, y, z, soundId, volume, pitch, seed);
        world.players().forEach(player -> {
            if (player.distanceToSqr(x, y, z) <= (double) distance * distance) {
                PacketDistributor.sendToPlayer(player, packet);
            }
        });
    }

    @Override
    public void syncMobComboCount(LivingEntity mob, int comboCount) {
        PacketDistributor.sendToPlayersTrackingEntity(mob,
                new S2CComboCountSync(mob.getId(), comboCount));
    }

    @Override
    public void syncServerConfig() {
        var server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            PacketDistributor.sendToAllPlayers( // NeoForge 1.21.1: no MinecraftServer param
                            new S2CConfigSync(BetterMobCombat.getServerConfigSerialized()));
        }
    }
}
