package me.Thelnfamous1.bettermobcombat.mixin;

import me.Thelnfamous1.bettermobcombat.Constants;
import net.minecraft.resources.ResourceLocation;

/**
 * In BC 1.20.1, this was a @Mixin accessor into ServerNetwork to obtain UUID-based modifier IDs.
 * In BC 1.21.1, those UUID fields were removed entirely from ServerNetwork.
 * BMC now defines its own ResourceLocation-based modifier IDs here as plain constants.
 * This class is intentionally NOT a Mixin — it must also be removed from bettermobcombat.mixins.json.
 */
public final class ServerNetworkAccessor {

    private ServerNetworkAccessor() {}

    public static ResourceLocation bettermobcombat$getCOMBO_DAMAGE_MODIFIER_ID() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "combo_damage_multiplier");
    }

    public static ResourceLocation bettermobcombat$getDUAL_WIELDING_MODIFIER_ID() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dual_wielding_damage_multiplier");
    }

    public static ResourceLocation bettermobcombat$getSWEEPING_MODIFIER_ID() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweeping_damage_modifier");
    }
}
