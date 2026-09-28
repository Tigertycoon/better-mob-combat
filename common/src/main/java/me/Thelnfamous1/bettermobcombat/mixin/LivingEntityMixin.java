package me.Thelnfamous1.bettermobcombat.mixin;

import me.Thelnfamous1.bettermobcombat.api.MobAttackStrength;
import me.Thelnfamous1.bettermobcombat.logic.MobAttackHelper;
import net.bettercombat.logic.PlayerAttackProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    // MC 1.21.1: getAttributeValue now takes Holder<Attribute>; require = 0 for graceful compat with older signatures
    @Inject(
            method = {"getAttributeValue(Lnet/minecraft/core/Holder;)D"},
            at = {@At("HEAD")},
            cancellable = true,
            require = 0
    )
    public void pre_getAttributeValue(Holder<Attribute> attribute, CallbackInfoReturnable<Double> cir) {
        if ((Object)this instanceof Mob mob) {
            int comboCount = ((PlayerAttackProperties)mob).getComboCount();
            if (!mob.level().isClientSide && comboCount > 0 && MobAttackHelper.shouldAttackWithOffHand(mob, comboCount)) {
                MobAttackHelper.offhandAttributes(mob, () -> {
                    double value = mob.getAttributes().getValue(attribute);
                    cir.setReturnValue(value);
                });
                cir.cancel();
            }
        }
    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;)V", at = @At("TAIL"))
    private void post_swing(InteractionHand $$0, CallbackInfo ci){
        if(this instanceof MobAttackStrength mob){
            mob.bettercombat$resetAttackStrengthTicker();
        }
    }
}
