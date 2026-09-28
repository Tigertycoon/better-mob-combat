# Better Mob Combat — NeoForge 1.21.1 port

[![Build and test](https://github.com/Tigertycoon/better-mob-combat/actions/workflows/build.yml/badge.svg)](https://github.com/Tigertycoon/better-mob-combat/actions/workflows/build.yml)

An unofficial NeoForge port of [Thelnfamous1/Better-Mob-Combat](https://github.com/Thelnfamous1/Better-Mob-Combat), the Better Combat add-on for humanoid mobs. Original history and authorship are retained.

## Porting work

- NeoForge entry point, service implementation and four clientbound payload codecs.
- Migration from legacy item attribute access to Minecraft 1.21.1 data components.
- Updated mob attack logic, weapon selection and Guard Villagers integration.
- Explicit mixin registration, separation of client animation code from dedicated-server mixins, and repair of removed attack-range/weapon method targets.
- One supported, reproducible NeoForge build with fixed dependency versions.

## Build and test

Java **21**, Minecraft **1.21.1**, NeoForge **21.1.236**.

```sh
./gradlew build --no-daemon
```

Use `gradlew.bat` on Windows. The mod and sources JARs are in `build/libs/`. Development runs are available with `runClient` and `runServer`; runtime dependencies are resolved automatically.

Pinned dependency profile: Better Combat **2.3.1**, Player Animator **2.0.0**, Cloth Config **15.0.140**, Guard Villagers **2.4.5** (optional for end users, included in development checks).

## Validation and limits

Four regression tests initialize the real NeoForge mod environment and check server-side combat mixin application, Guard goal class loading, combo/reset payload round-tripping, and attack/stop-animation packet timing. The tests do not establish visual animation quality, combat balance, multiplayer acceptance or compatibility with every third-party mob.

Only **NeoForge 1.21.1** is supported by this build. Historical Fabric/Forge sources remain for provenance and are excluded from the active build. Recruits integration has not been ported.

## Origin

Ported from upstream commit `d89f45baf4aef6b25b1fcfba89b33ae4b27ddef6`. Original mod: Thelnfamous1, with upstream credits to Daedelus, KosmX, Gecko and LukeGrahamLandry. Port maintenance: Niklas / Tigertycoon. [CC0-1.0](LICENSE).

See [porting notes](docs/porting.md). Please report issues specific to this port here, with the exact dependency versions.
