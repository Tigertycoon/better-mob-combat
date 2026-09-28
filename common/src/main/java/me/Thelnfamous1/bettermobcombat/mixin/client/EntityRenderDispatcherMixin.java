package me.Thelnfamous1.bettermobcombat.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.Thelnfamous1.bettermobcombat.logic.MobAttackHelper;
import me.Thelnfamous1.bettermobcombat.logic.MobTargetFinder;
import net.bettercombat.api.AttackHand;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.client.BetterCombatClientMod;
import net.bettercombat.client.collision.OrientedBoundingBox;
import net.bettercombat.client.collision.TargetFinder;
import net.bettercombat.logic.PlayerAttackProperties;
import net.bettercombat.mixin.client.MinecraftClientAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin({EntityRenderDispatcher.class})
public class EntityRenderDispatcherMixin {
    public EntityRenderDispatcherMixin() {
    }

    @Inject(
        method = {"render"},
        at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;F)V", shift = At.Shift.AFTER)}
    )
    public <E extends Entity> void renderColliderDebug(E entity, double $$1, double $$2, double $$3, float $$4, float $$5, PoseStack matrices, MultiBufferSource bufferSource, int $$8, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        if (((MinecraftClientAccessor)client).getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            if (entity instanceof Mob mob) {
                if (BetterCombatClientMod.config.isDebugOBBEnabled) {
                    if (mob.getMainHandItem() != null) {
                        PlayerAttackProperties extendedMob = (PlayerAttackProperties)mob;
                        int comboCount = extendedMob.getComboCount();
                        AttackHand hand = MobAttackHelper.getCurrentAttack(mob, comboCount);
                        if (hand != null) {
                            WeaponAttributes attributes = hand.attributes();
                            if (attributes != null) {
                                TargetFinder.TargetResult target = MobTargetFinder.findAttackTargetResult(mob, null, hand.attack(), attributes.attackRange());
                                boolean collides = target.entities.size() > 0;
                                Vec3 cameraOffset = mob.position().reverse();
                                OrientedBoundingBox obb = target.obb.copy().offset(cameraOffset).updateVertex();
                                List<OrientedBoundingBox> collidingObbs = target.entities.stream().map((e) -> (new OrientedBoundingBox(e.getBoundingBox())).offset(cameraOffset).scale(0.95).updateVertex()).collect(Collectors.toList());
                                this.bettermobcombat$drawOutline(matrices, obb, collidingObbs, collides);
                            }
                        }
                    }
                }
            }
        }
    }

    // TODO: MC 1.21.x completely rewrote the immediate-mode rendering API (Tesselator/BufferBuilder removed;
    //       VertexConsumer.vertex(Matrix4f,x,y,z) signature changed). The OBB debug visualisation below is
    //       stubbed out for Phase 1. Re-enable when porting the rendering code to the new VertexBuffer/MeshData API.

    @Unique
    private void bettermobcombat$drawOutline(PoseStack matrixStack, OrientedBoundingBox obb, List<OrientedBoundingBox> otherObbs, boolean collides) {
        // Stubbed — see TODO above.
    }

    @Unique
    private void bettermobcombat$outlineOBB(PoseStack matrixStack, OrientedBoundingBox box, VertexConsumer buffer, float red1, float green1, float blue1, float red2, float green2, float blue2, float alpha) {
        // Stubbed — see TODO above.
    }

    @Unique
    private void bettermobcombat$look(PoseStack matrixStack, OrientedBoundingBox box, VertexConsumer buffer, float alpha) {
        // Stubbed — see TODO above.
    }
}