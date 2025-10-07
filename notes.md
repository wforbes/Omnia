# World

- gameState (OverworldState)
- currentArea (Area)
- previousAreas (ArrayList<Area>)
- dialogController (DialogController)
- player (Player)

## Area

- world (src/world/World)
- tileMap (/tile/TileMap)
- entityController (EntityController)
- areaObjectController (AreaObjectController)
- effectController (EffectController)



# Tile 

## (abstract)
- tiles (Tile[256])
- VOID (final Tile)
- GRASS (Tile final) .. along with GRASS2, GRASS3, GRASS4
- id (byte)
- spritePos (Point2D)
- type (int)
- mapColor (int)
- NORMAL (int final)
- BLOCKED (int final)
- WATER (int final)
- LAVA (int final)

## BasicTile
## BasicSolidTile


---
## Area ideas

- Different Area Types 
- Area specific tilesheet with a set of pixels
  - Each pixel has a specific color value
  - Each specific color value matches a tile on the tilesheet


---
StructureArea



---
## Entities

### Mob
- name (String)
- spriteSheet (Image)
- sprites (ArrayList<Image[]>)
- width (int)
- height (int)
- x (double)
- y (double)
- baseY (double)
- xmap (double)
- ymap (double)
- combatSpeed (double)
- runSpeedMod (double)
- isMoving (boolean)
- isRunning (boolean
- numSteps (int)
- facingDir (int)
- numFrames (int[])
- combatNumFrames (int[])
- FACING_N (int) static final
- FACING_S (int) static final
- FACING_W (int) static final
- FACING_E (int) static final
- FACING_NW (int) static final
- FACING_NE (int) static final
- FACING_SW (int) static final
- FACING_SE (int) static final
- movementController (/movement/MovementController)
- entityEffectController (/effect/EntityEffectController)
- isPlayer (boolean)
- collisionRadius (double)
- isColliding
- collidingAreaObject (AreaObject)
- nameColor (Color)
- nameFlashColor (Color)
- nameText (Text)
- nameFont (Font)
- nameColorTimeline (Timeline)
- gameState (OverworldState)
- collision_baseX (double)
- collision_baseY (double)
- pathfindController (/pathfind/PathfindController)
- combatSprites (ArrayList<Image[]>)
- collision_baseCircle (Circle)
- collision_baseCenterPnt (Point2D)
- statChanges (ArrayList<StatChange>) - /combat/stat/StatController
- corpseSprite (Image)
- mobCorpse (MobCorpse)
- collidingEntity (Entity))
- meleeReach (int)
- dead (boolean)
- mobType (String)

Notes:
    - Turn 'collidingAreaObject' into an array (to help handle interacting with multiple area objects nearby?)
    - Remove 'collidingEntity' - might be unused

#### Types:

##### Player

Desc: player character

##### NPC

Desc: non-combat non-player characters

##### Enemy

Desc: combat focused non-player characters

##### Spirit

Desc: alternate version of NPC and Enemy mobs

----

