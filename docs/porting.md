# Porting notes

The local working copy already contained the NeoForge module, networking migration and most 1.21.1 API adaptations. Publication preparation consolidated the active build and replaced moving/non-resolvable dependency coordinates with fixed artifacts.

The first compiler pass succeeded, but runtime initialization exposed missing mixin declarations. Enabling those declarations revealed three additional problems: client-only animation registry access on a server, a `getMainHandItem` injection where Minecraft now calls `getWeaponItem`, and an obsolete `getAttackReachSqr` injection. The range customization now uses the existing `Mob.isWithinMeleeAttackRange` hook.

The regression suite guards these startup and payload boundaries. It uses NeoForge's ephemeral test server for registry initialization, not a rendered client or a simulated multiplayer combat session. Publication cleanup and checks were prepared with assistance from Codex.
