package me.Thelnfamous1.bettermobcombat.mixin.compat.guardvillagers;

import me.Thelnfamous1.bettermobcombat.mixin.MeleeAttackGoalMixin;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tallestegg.guardvillagers.common.entities.Guard;

/**
 * Mixin into Guard.GuardMeleeGoal for NeoForge 1.21.1.
 * Package changed from tallestegg.guardvillagers.entities to
 * tallestegg.guardvillagers.common.entities.
 * checkAndPerformAttack signature changed from (LivingEntity, double) to (LivingEntity) in MC 1.21.1.
 */
@Pseudo
@Mixin(value = Guard.GuardMeleeGoal.class, remap = false)
public abstract class GuardMeleeGoalMixin extends MeleeAttackGoalMixin {

    @Shadow @Final public Guard guard;

    @Override
    protected void bettermobcombat$postBetterCombatAttack() {
        // Guard-specific: clear shield usage after attacking
        this.guard.stopUsingItem();
        if (this.guard.shieldCoolDown == 0) {
            this.guard.shieldCoolDown = 8;
        }
    }

    // Backup inject — almost always pre-empted by the WrapWithCondition in MeleeAttackGoalMixin.tick()
    // remap = false because GuardMeleeGoal is a modded class; no Mojang remapping table entry exists.
    // MC 1.21.1: checkAndPerformAttack(LivingEntity) — no 'double' parameter
    @Inject(
            method = "checkAndPerformAttack",
            remap = false,
            at = @At("HEAD"),
            cancellable = true
    )
    private void pre_checkAndPerformAttack(LivingEntity target, CallbackInfo ci) {
        if (this.bettermobcombat$useBetterCombatAttackCheck(target)) {
            ci.cancel();
        }
    }
}
