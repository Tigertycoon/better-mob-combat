package portfolio;

import me.Thelnfamous1.bettermobcombat.api.MobAttackStrength;
import me.Thelnfamous1.bettermobcombat.api.MobAttackWindup;
import net.bettercombat.api.EntityPlayer_BetterCombat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.network.RegistryFriendlyByteBuf;
import io.netty.buffer.Unpooled;
import me.Thelnfamous1.bettermobcombat.network.S2CComboCountSync;
import me.Thelnfamous1.bettermobcombat.network.S2CAttackAnimation;
import net.bettercombat.logic.AnimatedHand;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EphemeralTestServerProvider.class)
class CombatPortTest {
    @Test void combatMixinsAreAppliedToServerMobs(MinecraftServer server) {
        assertTrue(MobAttackStrength.class.isAssignableFrom(Mob.class));
        assertTrue(MobAttackWindup.class.isAssignableFrom(Mob.class));
        assertTrue(EntityPlayer_BetterCombat.class.isAssignableFrom(Mob.class));
    }

    @Test void guardGoalCompatibilityLoads() throws ClassNotFoundException {
        assertNotNull(Class.forName("tallestegg.guardvillagers.common.entities.Guard$GuardMeleeGoal"));
    }

    @Test void comboPayloadPreservesResetSentinel(MinecraftServer server) {
        var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), server.registryAccess());
        try {
            var expected = new S2CComboCountSync(42, -1);
            S2CComboCountSync.STREAM_CODEC.encode(buffer, expected);
            assertEquals(expected, S2CComboCountSync.STREAM_CODEC.decode(buffer));
            assertEquals(0, buffer.readableBytes());
        } finally { buffer.release(); }
    }

    @Test void animationPayloadPreservesTimingAndStopSignal(MinecraftServer server) {
        for (var expected : new S2CAttackAnimation[]{
                new S2CAttackAnimation(17, AnimatedHand.OFF_HAND, "bettercombat:one_handed_slash", 12.5F, 0.35F),
                S2CAttackAnimation.stop(17, 4)}) {
            var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), server.registryAccess());
            try {
                S2CAttackAnimation.STREAM_CODEC.encode(buffer, expected);
                assertEquals(expected, S2CAttackAnimation.STREAM_CODEC.decode(buffer));
                assertEquals(0, buffer.readableBytes());
            } finally { buffer.release(); }
        }
    }
}
