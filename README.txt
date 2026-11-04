Legendary Survival Overhaul - NeoForge 26.3 migration
=====================================================

Original mod (author): Sfiomn / Legendary Workshop
  https://github.com/Alex-Hashtag/LegendarySurvivalOverhaul
This repository (26.3 port by AFoolLove):
  https://github.com/AFoolLove/LegendarySurvivalOverhaul
Bug reports / issues:
  https://github.com/AFoolLove/LegendarySurvivalOverhaul/issues

Migration status (MC 26.3 / NeoForge 26.3.0.100 / Java 25):
  - code migration complete: builds, starts and plays
  - restored third-party compatibility:
      JEI, Curios, Serene Seasons, Ecliptic Seasons,
      Mutant Monsters, Overflowing Bars
  - not ported yet (no 26.3 release of those mods):
      Vampirism, Artifacts, Refurbished Furniture, Beachparty, Meadow,
      TerraFirmaCraft, Supplementaries, Meds and Herbs, Origins,
      Weather2, Survive

Known limitation (26.3):
  - the `hide coordinates / targeted info from the debug screen` option
    (hideInfoFromDebug) is disabled: NeoForge removed
    CustomizeGuiOverlayEvent.DebugText and the debug screen is data driven now,
    so the config option currently has no effect.

License: MIT License (see LICENSE.txt)
Note:   optional compat mod jars used for local development live in libs/ (not committed).

