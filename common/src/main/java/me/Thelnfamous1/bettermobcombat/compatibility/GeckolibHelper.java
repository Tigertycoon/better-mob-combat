package me.Thelnfamous1.bettermobcombat.compatibility;

import net.minecraft.world.entity.Entity;

public class GeckolibHelper {

    // Resolved at runtime so common module compiles without GeckoLib on the classpath.
    // GeckoLib 4.x (1.21+) moved the interface to software.bernie.geckolib.animatable;
    // GeckoLib 3.x used software.bernie.geckolib.core.animatable.
    private static final Class<?> GEO_ANIMATABLE_CLASS;
    static {
        Class<?> clz = null;
        try {
            // GeckoLib 4.x path (NeoForge 1.21+)
            clz = Class.forName("software.bernie.geckolib.animatable.GeoAnimatable");
        } catch (ClassNotFoundException ignored) {
            try {
                // GeckoLib 3.x fallback path
                clz = Class.forName("software.bernie.geckolib.core.animatable.GeoAnimatable");
            } catch (ClassNotFoundException ignored2) {
                // GeckoLib not loaded — isGeoAnimatable will always return false
            }
        }
        GEO_ANIMATABLE_CLASS = clz;
    }

    public static boolean isGeoAnimatable(Entity entity) {
        return GEO_ANIMATABLE_CLASS != null && GEO_ANIMATABLE_CLASS.isInstance(entity);
    }
}
