# Omnia
Retro 2D Role-Playing Video Game targeted for the PC, Mac, Linux, and Android. 
Written using Java and JavaFX.

(Readme last updated: 12/17/23)

![Screenshots of Omnia version 0.0.1](https://raw.githubusercontent.com/wforbes/Omnia/master/press/screens_v0-0-1.png)

## Project
Omnia is a 2D 'Retro' Role-Playing Game with multiple game perspectives (Top-Down, Platformer, Turn-Based, Puzzle, etc),
 written in Java. I'm working on it for the final 'capstone' project in my Computer Science degree - but also so that I can practice programming in Java, experiment with ideas, and just have fun. 

Instead of using a sophisticated game engine like Unreal, Unity, Godot, or GameMaker 
I thought I would get the most out of developing the engine myself by reading/watching game tutorials/devlogs, getting 
inspiration from game libraries, and inventing systems on my own. (Every naive young programmer's last words.)

---
# To-do lists
Keeping track of my work and plans here will inspire me to keep going and stay accountable to consistently working on 
the project.
I'd like to keep my head down for all of 2021 and just code/draw as much as possible, then re-evaluate the project
when it's time to write my final capstone papers for school. Talk to you next year.
 
### Starter Todo List (Nov/Dec 2020) - Omnia v0.0.1
Resurrect and combine two old game engines to run together. Add in the use of JavaFX to improve graphics rendering and 
ease the implementation of GUI features.
* [X] ~~Resurrect [my old game engine](https://www.youtube.com/watch?v=DIMeRYfil7c&list=PLRjjchumlJl2LIs1esk_C9RPvlEf2GOLb) I worked on in 2013 based loosely on two very old tutorials:~~
    * [X] ~~**Top-Down Tutorial**: Find my code based on [Java 2D Game Development](https://www.youtube.com/watch?v=VE7ezYCTPe4&list=PL8CAB66181A502179) by vanZeben~~
    * [X] ~~**Platformer Tutorial**: Find my code based on [Java 2D Game Programming Platformer Tutorial](https://www.youtube.com/watch?v=9dzhgsVaiSo&list=PL-2t7SM0vDfcIedoMIghzzgQqZq45jYGv) by ForeignGuyMike~~
* [X] ~~**Get the Top-Down engine running**: Ensure it works as expected and experiment with some ideas to add to it.~~ 
    * [X] ~~**Top-Down: JFrame & Game Loop**: Get the Game class with the game loop running and updating tps/fps to the 
    window.~~
    * [X] ~~**Top-Down: Screen & SpriteSheet**: Add Screen class and SpriteSheet class, successfully compile while accessing
     the sheet image.~~
    * [X] ~~**Top-Down: Level & Tile**: Add InputHandler class, Level class, and Tile classes. Render a small level on the
     Screen.~~
    * [X] ~~**Top-Down: Player & Movement**: Add Entity, Player, Mob, Enemy classes. Successfully move the Player around a
     large level.~~
    * [X] ~~**Top-Down: Environment Collision**: Add collision logic between the player and solid or water tiles~~
    * [X] ~~**Top-Down: Mob to Mob Collision**: Add collision logic between mobs so they can't pass through each other.~~
* [X] ~~**Get the Platformer engine running**: Ensure it works as expected and experiment with some ideas to add to it.~~
    * [X] ~~**Platformer: GamePanel & Game Loop**: Get the GamePanel running the Game Loop and updating/rendering.~~ 
    * [X] ~~**Platformer: Main Menu**: Get the MenuState running the main menu when the game first launches.~~
    * [X] ~~**Platformer: Level 1**: Get the Level1State running with the level's TileMap, player/enemy Entity, and Hud.~~
* [X] ~~**Playable Prototype**: Combine the two engine's code. Use the GameStates for Menu, Top-Down and Platformer.~~
    * [X] ~~**Top-Down: Pause Menu**: Add a pause menu that allows the player to go back to the Main Menu or resume gameplay.~~
    * [X] ~~**Platformer: Death Menu**: Add a menu that appears upon player death that allows them to return to the Main 
    Menu or try the level again.~~
* [ ] ~~**Implement JavaFX**: Add a JavaFX implementation of the core Game running classes to enable UI development and 
more precise (float, double) figures for game logic.~~
    * [X] ~~**Game Loop with AnimationTimer**: Test and implement the JavaFX.AnimationTimer for use with the game loop.~~
    * [X] ~~**JavaFX graphics rendering**: Test and implement JavaFX graphics classes (GraphicsContext, Image, PixelReader, 
    PixelWriter, etc.) to recreate, combine and improve on the graphics algorithms for Top-Down and Platformer namespaces~~
    * [X] ~~**Implement game logic**: Enable all existing game logic; updating levels and entities, taking key input and
    all other existing features.~~
    * [X] ~~**Implement new UI for menus**: Use JavaFX features to build a simple UI for the MainMenu, PauseMenu, and 
    DeathMenu.~~
---
### Overworld (Jan 2021) - Omnia v0.0.2
#### Rewrite the Top-Down gamestate 
[Github project board](https://github.com/wforbes/Omnia/projects/5)

It's clear that the Top-Down gamestate is very limited in it's 8-bit graphics capability. This month I'm focused on rewriting
 the Top-Down gamestate to be able to handle more rich graphics like the Platformer gamestate, retaining the same Top-Down
 mechanics and utilizing the newly added JavaFX backbone to the game.
* [X] ~~**Copy Existing Code** - Copy in the base code from the Top-Down and the Platformer, using the most desirable pieces
 of either and structuring it in an organized way.~~ 
* [X] ~~**Add Player and Level** - Implement tilemap and player with movement functionality~~
* [X] ~~**Add image-based tilemap system** - Replace the .map file tilemap system of the platformer with the image-based 
tilemap system of the Top-Down engine~~ 
* [X] ~~**Add NPC logic and render NPC** - Add the Mob and NPC classes and get them rendering on the screen~~
* [X] ~~**Add GUI logic with chat window** - Add the GUI logic from Top-Down and add it's chat window. Add a developer 
    window that shows useful information when coding and add a UI display window that toggles the other two windows open 
    or closed.~~ 
* [X] ~~**Add in mob collision** - Add collision logic from Top-Down and improve it to use circular collision boundries. 
    Simplify the collision detection algorithm. Add rendering of collision boundaries and a toggle button in the dev 
    window to show/hide the boundaries.~~
* [X] ~~**Implement NPC movement** - Add in the automated NPC movement from Top-Down.~~
* [X] ~~**Style the GUI with CSS** - Add some styles to the GUI to get away from the default JavaFX look~~
* [X] ~~**Add GUI resizing** - Add the ability to resize GUI windows.~~
* [X] ~~**Render mob names** - Render each Mob's name above their head with JavaFX Text objects~~
* [X] ~~**NPC dialog and movement changes** - Add NPC dialog from Top-Down state, and stop NPC movement when they respond 
to dialog.~~
* [X] ~~**Add NPC collision reaction** - When an NPC collides with something while pacing, have them react to it. Have them 
    change direction and walk their path in the other direction. Have them stop and wait for their path to be clear and 
    continue in the same direction when it is. Have them simply stop walking indefinitely.~~
* [X] ~~**Improve chat window features** - Add a key/focus flow to quickly press Enter, start typing a chat message, send 
the message and keep playing quickly.~~
* [X] ~~**Implement Player running** - Pressing the SHIFT key while moving increases movement and animation speed.~~
* [X] ~~**Add Pause feature and menu** - Add the pause menu from the Top-Down engine.~~

Well that was fun... but I have to focus on my full time job and finishing my college degree. Expecting to come back to 
this project in a couple years to keep working on it.

---
### Return to 'Overworld' (2023)
#### Continuing to mess around with this project when I need to relax/procrastinate
 (procrastination example: making this to-do list)

- **Miscellaneous**
    * [X] ~~**Add Clickable Chat/Dialog Links** - Add links in the quest dialog that gets printed in the chat window to more 
    easily respond to quest text.~~
    * [X] ~~**Refine mob collision boundaries** - Improve the mob's collision boundaries.~~
    * [ ] **Compensate for diagonal speed increase** - add a speed modifier for diagonal movement to normalize it
    with cardinal movement  
    * [ ] **Create GUI window hierarchy** - The most recently interacted with or opened GUI windows should appear on top of
        others to emulate windows in an operating system.
      * [ ] **Path finding**
        * [X] ~~**Click to move** - Try implementing a click to move system with simple path finding~~
        * [X] ~~**NPC following** - Use path finding logic to implement NPCs being able to follow Player and other NPCs~~
        * [ ] **Advanced Pathing** - Implement an A* based path finding algorithm that can avoid collision
        * [ ] **Scripted NPC movement** - Use path finding logic to script NPC movement
        * [ ] **Scripted Player movement** - Use path finding logic to script Player movement
        

- **Environment Structures/Objects**
  * [ ] **Add collidable environment structures** - Add a layer of objects that render over the ground tiles which mobs 
      collide with.
    * [ ] **Add static environment objects** - Add decorative collidable env objects.
    * [X] ~~**Add interactable environment objects** - Add collidable environment objects that player can interacted with.~~
      * [ ] **Openable Containers**: Add some chests/furniture that can be 'opened' and fed into Inventory System and Quest System.
        * [ ] **Key Locked Containers**: Add a locked attribute to containers, restricting them from being opened without
        the player having the right 'key' item
        * [ ] **Puzzle Locked Containers**: Add a puzzle system that is presented to the player when attempting to open
        this type of container. Puzzles might be combination, pattern, lock-picking,  
      * [ ] **Gathering Nodes**: Add nodes that can be interacted with and despawn/respawn.
        * [ ] **Harvesting**
          * [X] ~~**Initial**: Add some plants that can be 'harvested' and get basic skill timers going.~~
          * [X] ~~**Basic Gather Timers**: Get the basic skill timers to keep for harvesting~~
          * [X] ~~**GUI: Skill Timer**: Show a little pop-up progress indicator while gathering.~~
          * [X] ~~**Persist node gather progress**: Save the players harvesting progress between cancels/interruptions.~~
          * [X] ~~**Add Gather looting**: (F/S 'Simple Inventory') Node loot tables, transfer of loot from node to player inventory.~~
          * [X] ~~**Loot Table** - Add a feature that entities/objects can use to define their loot possibilities.~~
          * [X] ~~**Persist Node loot** - persist the node's gathered items state between multiple harvests.~~
          * [X] ~~**'Despawn' node after gather completes**: Display some kind of empty graphic in its place, stop interactivity.~~
          * [X] ~~**'Respawn' node**: Add respawn timer for each node, after which the node respawns to be gatherable again.~~
          * [X] ~~**Randomize node locations**: Randomize locations nodes may be able to spawn, avoiding overlap/collision~~
          * [ ] **Bound node spawn locations**: Add ranges that certain nodes may spawn
        * [ ] **Mining**: Add some rocks/minerals that can be 'mined'.
        * [ ] **Logging**: Add some trees that can be 'chopped'.
      * [ ] **Gathering Skill**: Add an initial skill system that restricts the ability to gather nodes before learning the skill.
      * [ ] **Gathering Tools**: Add tool items that are required to perform a gathering skill.
        * [ ] **Improved Tools**: Using a tool with a higher gather skill statistic improves gather speed and loot yield.
    * [ ] **Add enterable buildings** - Add building structures that can be entered when player steps into it's 'door'

- **Inventory**
  * [ ] **Simple inventory feature** - Add very basic simple inventory system to get it started
    * [X] ~~**GUI: Basic 'Bag'** - Add window with item slots to represent the characters held items~~
    * [ ] **GUI: Item Window** - Add window that displays item details which opens from item slot icon
    * [X] ~~**Item list** - Add a short list of items, figure out the best way to persist it.~~
    * [ ] **Persist Held Items** - Save the player's items between game closing/opening.
    * [ ] **Consider a save feature** - do we need that yet?...

- **Enemy Basics**
    * [X] ~~**Initial**: Add a prototype enemy to serve for building other systems around.~~
    * [X] ~~**Aggro/Dumb Pursuit**: Add simple system for an enemy to aggro the player and pursue/follow it.~~

- **Melee Combat**
    * [X] ~~**Combat Animation**: Add a simple system for handling the visual animation of attacking~~
    * [X] ~~**Damage and Health**: Add a simple system for handling giving/taking damage from Player to an Enemy~~
    * [X] ~~**Mob Healthbar Window**: Add GUI that displays a mob's health level above their sprite~~
    * [X] ~~**Enemy Attacks**: Add the ability for the Enemy to attack the Player and deal damage to it~~
    * [X] ~~**Enemy Death**: Add sprites/animation and logic for the Enemy to die when it runs out of HP.~~
    * [X] ~~**Render Combat Damage Numbers**~~
    * [ ] **Simple Player Death**: Add simple system to handle Player death and respawn
    * [ ] **Enemy Loot**: Add ability for Player to loot a dead enemy
    * [ ] **Enemy Corpse Despawn**
    * [ ] **Enemy Respawn**
    * [ ] **Multiple Enemies**
    * [ ] **Player Corpse Runs**
    * [ ] **Target Window**: Add GUI that displays the target's health level
    * [ ] **Player Window**: Add GUI that displays the Player's health level

- **Projectiles** 
  * [X] **Initial**: Add a basic projectile that shoots out of the player in their facing direction
  * [ ] **Hitting Mobs/Objects**: Projectiles affect Mobs and Objects that collide with them - doing damage, nudging it,
    or just annoying them... depending on what is projected and what is hit.
    * [ ] **Shooting/Throwing**: Add weapon based projectiles that interact with player inventory,
    requiring a range weapon, expending ammo, etc.

- **Spell system** : , basic logic to cast and land, and start figuring it out
  * [ ] **Spell Book GUI**: Add some basic GUI to scribe/memorize spells to/from
  * [ ] **Spell Cast GUI**: Add some basic GUI to cast spells from
  * [ ] **Particles / Effects**
    - Particle Sprites
    - Rendered
    - Glitch/Distortions
  * [ ] **Dimensional Shifting**
    - Explore the idea of casting a spell that "shifts" the player into another dimension, causing the World and its entities/objects to look different and other previously unseen/uncollidable entities/object to appear 

- **Settings**
  * [ ] **Simple settings window** - Add a simple GUI settings window with the ability to change Player keybinds
    * [ ] **Persist Settings** - Save the player's settings between game closing/opening.

- **Water**
  * [ ] **Implement water tiles** - Add animated water tiles from the Top-Down engine. When a mob enters these tiles they 
      should appear to be submerged.
    * [ ] **Slow swimming speed** - While a mob is moving through water, their movement speed should be reduced.
    * [ ] **Fishing** - Add basic fishing gathering skill that requires player to be near water, triggers a timer, and
    randomly transfers fishing loot into the Players inventory.

---
## Bugs
- Due to x/y coords being fudged around for radial collision when working on areaobjects, the NPC attention turn during quest conversation is off in the X+ and Y+ directions

---
## Concepts, plans, ideas ...
### Engine Improvements and Homework
#### Resizing the game & starting real documentation
* [ ] **Scale and Resize the game**
    * [ ] **Enable Full Screen Mode**: The game application should run in Full Screen Mode and play well in all common 16:9 
    resolutions (1920x1080, 1600x900, 1366x768, 1280x720, 960x540, 854x480, 640x360) - optionally/experimentally on 4k (3840x2160)
    * [ ] **Enable Windowed Mode**: The game application runs in Windowed Mode and plays well in various resizes of the 
    window, down to some minimum size (like 640 wide)
    * [ ] **Ensure everything works**: Test all existing features at all available resolutions/scales and make sure 
    everything works as intended. Fix anything that's amiss. 
* [ ] **Begin the Design Document**
    * [ ] **Research 'Design Document' formats**: Find the best way to structure and best content to include in the
    design document. Explore large picture concepts and define should be accomplished with the game.
    * [ ] **Define a GUI Design**: Explore ideal GUI looks and functionality, realize what's actually possible with fx css
    * [ ] **Define a Art Design**: Explore details from favorite games to draw inspiration. 
* [ ] **Main Menu improvements**
    * [ ] **Redesign visuals**: Redesign to reflect what was discovered in the design document
    * [ ] **Add Info/About page**
* [ ] **Create ALOT of pixel art**: Draw and create and then draw some more.
* [ ] **Backlog**: Experiment with anything from the backlog that seems interesting.

---
### Inventory System
* [ ] **Inventory Window**: The user can open a GUI Inventory Window which display slots which may be occupied with items,
  represented by icon sprites
* [ ] **Moving items**: The user can pick up and move items with the cursor by clicking them
    * [ ] **Slot Item Pickup**: Left-clicking an item in an inventory slot removes the item from the slot and places it on the
      cursor - carried on the lower right corner of the cursor
    * [ ] **Slot Item Drop**: Left-clicking an empty inventory slot when there's an item on the cursor places it in the slot,
      removing it from the cursor
    * [ ] **Item Slot/Cursor Swap**: Left-clicking an empty inventory slot when there is another item on the cursor swaps the
      item in the slot with the item on the cursor
* [ ] **Deleting items**: The user can click a 'Destroy' button in the Inventory Window which will delete the item carried on
  the cursor
* [ ] **Stacking items**: Some items can be stacked with items of the same type
    * [ ] **Stack count**: Stacking items are displayed with the stack quantity number shown over the lower right corner of the
      item sprite
    * [ ] **Stack pickup**: Picking up a stacked item with more than one in the stack opens a quantity selection window to choose
      how much of the stack to pick up, clicking OK places the chosen quantity on the cursor
    * [ ] **Stack fill**: Placing a stacked item in a slot with the same item type fills the stack in the slot with as much as it can
    * [ ] **Stack drop**: Placing a stacked item in an empty slot moves the whole stack into that empty slot
* [ ] **Item Inspection**: The user can see details about items like their description, effects, and stats by opening the Item Inspection Window
* [ ] **Item Activation**: Some items can be right-clicked to activate them
    * [ ] **Usable Items**: When activated, a usable item triggers an effect specific to the item
    * [ ] **Consumable Items**: (food, drink, magic item) When activated, a consumable item triggers an effect and it's 'use count'
      is decremented, once the 'use count' reaches zero, it's removed/destroyed
    * [ ] **Readable Items**: (books, scrolls, notes, documents) When activated, a readable item opens a Reading Window that displays
      the text or image content of that item
    * [ ] **Container Items**: (bags, backpacks, satchels) When activated, a container item opens a small inventory window with that
      container's available slots. Containers essentially expand the player's inventory to hold more items. Items can be
      added/removed from these slots the same way the inventory window's slots do. Closing this window and reopening it persists
      these items in the correct slots.
* [ ] **Equipable Items**
    * [ ] **Equipable Item Slots**:Equipable items can be placed into normal inventory slots but can only placed into their designated
    * [ ] **Slot Types**:
        - Worn/Clothing/Armor: left earring, neck, head, face, right earring, back, shoulder, right wrist, hand, right ring,
          feet, legs, left right, waist, left wrist, arms, chest
        - Held/Weapon/Shield: Right hand, Left hand, range, ammo
* [ ] **Item Weights**: Items each have a 'weight' attribute which, when they occupy the player's inventory, are added to a total weight
  being held by the player that displays in the Inventory Window
* [ ] **Ground Items**: Items can be dropped on and picked up from the ground
    * [ ] **Dropping Items**: Left-clicking on the map with an item on the cursor will place an item icon on the ground near the player
    * [ ] **Picking Up Items**: Left-clicking on an item icon on the ground places it on the cursor and opens the inventory window
        * [ ] **Optional**: Left-clicking on an item icon on the ground will automatically place it in the first available inventory slot


* [ ] **Currency**: Because this is Capitalism, baby!


* [ ] **Vendor System**: Add a system that allows the user to buy items from and sell items to
  Vendor NPCs by deducting or adding currency in exchange for items in the player's inventory.

---
## Combat Systems

* [ ] **Combat System**:
    * [ ] **Enemy Mob**: Add Enemy Mob that's collidable with attacks
        * [ ] **Attack button**: When the "A" button is pressed, an attack action occurs.
        * [ ] **Emit Attack**: When an attack action occurs, a 170 degree triangle region is 'swept' for a collision check
        * [ ] **Receive Attack**: If an enemy's hitbox has collided with the attack, register that the attack happened.
        * [ ] **Notify Attack Landing**: When the attack happens, display a message in the chat window to notify that
          the attack landed
        * [ ] **Aggro Range**: Add enemy aggro range, when the player enters it - the enemy is flagged as 'attacking'
        * [ ] **Enemy Attack**: Add enemy melee attack, when the player is in melee range the enemy should swing at them using
          the same mechanics as player attacks
        * [ ] **Enemy Combat Movement**: Enable enemy movement to pursue the player directly.
        * [ ] **Enemy Combat AI**: Give the enemy AI techniques to fight with.
        * [ ] **Damage numbers**: Render damage numbers on the canvas near the associated Mob.


* [ ] **Player 'Action' Movement**:
    * [ ] **Strafing**: move sideways while continuing to face a forward direction
    * [ ] **Jumping**: scale a jumpable structure
    * [ ] **Falling**: jump/fall down ledges
    * [ ] **Dodging**: jump, dive and roll to avoid combat elements (melee, projectile, spells)
    * [ ] **Dashing**: move rapidly forward for a moment to close distance quickly


* [ ] **Skills System**: trade skills, gathering skills, combat skills, spell skills, etc. - leveling them up increases
  the reward/outcome/result of the activity


* [ ] **Class System**: (limits your skills, spells, armor, etc. to a chosen class)


* [ ] **NPC party system**: (mercenaries and stuff)


* [ ] **Health/damage system**:


* [ ] **Player Vitals Status UI**:


* [ ] **Player Leveling/Experience System**:


* [ ] **Player Statistics System**:


---
### Quest System
* [ ] **Start a quest through dialog**: The player can gain a quest through dialog with an NPC that is assigned and 
persisted in memory until completed.
    * [ ] **Quest is added to Quest GUI**: Quest assignment adds the quest to the active quests in the Quest Journal
* [ ] **Quest Journal Window**: The Quest Journal window displays all the player's active quests and details
    * [ ] **Quest List**: The active quests are displayed in a list on the window
    * [ ] **Quest Details**: Clicking a quest list item displays details about that quest in the Quest Details pane
* [ ] **Quest Objectives**: Quests are made up of multiple objectives that, when each are completed, the user is 
notified and the Journal is updated
    * [ ] **Notification window**: A small GUI window opens and closes automatically to inform the user of the 
    completion of an objective
    * [ ] **Location Objectives**: When the player enters a specified map area, the objective is completed
    * [ ] **NPC Interaction Objective**: Talking or interacting with a specified NPC completes the objective
    * [ ] **Delivery Objectives**: Giving a specified item to the right NPC will complete the objective
    * [ ] **Loot Objective**: Gaining an specified item completes the objective (*Inventory system)
    * [ ] **Combat Objective**: Fighting or killing a specified mob(s) will complete the objective (*Combat system)
* [ ] **Finishing a Quest**: Completing all objectives of a Quest will grant the player with a reward
    * [ ] **Quest Completion Window**: Quest Completion Window displays final dialog/info and rewards from the quest
    * [ ] **Currency Reward**: Being rewarded with currency adds the amount to user's inventory and notifies user 
    (*Inventory system)
    * [ ] **Item Reward**: Being rewarded with an item adds the item to user's inventory and notifies user (*Inventory system)
* [ ] **Optional Considerations**
    * [ ] **Click interaction**: Clicking on NPC opens quest interaction window for starting quests and finishing quests
    * [ ] **Available Quest Indicator**: NPC has something to notify you they have an available quest, Exclamation 
    mark/Question mark above head

---
### And like... everything else...

* [ ] **Entrance/Exit Doors**:

* [ ] **Create first interior area**:

* [ ] **Game Command System**:

* [ ] **Experiment with chat bubbles**: Test ideas on adding a chat bubble above the player when they "/say" something

* [ ] **Finishing conversations open new ones**: Finishing a conversation should cause them to start a 
        different conversation the next time they are spoken to.
  * [ ] **NPC favor system**: Talking to NPCs increase their favor toward you, slightly modifying overall faction for
  whatever group the NPC is a part of.
  * [ ] **NPC faction system**: Completing certain quests, killing certain NPCs/enemies, and doing other actions can
  increase your faction level toward a group of NPCs. Higher/Lower faction alters the way group members react to the player.


* [ ] **Mini-Map**: The nearest section of the Area is displayed in a miniature format with the 
        player's location and other noteworthy locations indicated with icons


* [ ] **World Map**:
    - The player can open a large GUI window that displays a map of the Area, zoom out to access 
        the map of the world, or zoom in from world or move over from surrounding Areas.
    - Landmarks and other important locations are indicated on the Area maps, major landmarks 
        are indicated on the world map


* [ ] **Game Time**
    - Day/Night cycle
    * [ ] **NPC time of day location change**: Depending on the game time, static NPCs should appear in a different 
            static location. If they are pacing/wandering they should move to a different location and pace/wander there.
    * [ ] **NPC conversations change with game time**: Depending on the game time, the NPC should react to 
    chat conversation differently.


* [ ] **Worn Item visuals**


* [ ] **Held Items/Weapon visuals**


* [ ] **Time Speed Changes**: Slow down or speed up immediate time spans... reverse time like Jonathan Blow? be a Time
Wizard n get all freaky....


* [ ] **Dynamic Structures**
    - Movable Structures: Player can push or pull structures to a different place on the tilemap
        - Push boxes and stones like in Zelda
    - Destructable Structures: Player can act on a structure to alter it's appearance or remove it
        - Melee hits can break a fence or weaker object
        - Projectile spells
        - Holding a Mining Pick can break a Mineral vein 
    - Interactive Structures: Pressing an interact button changes their state
    - Opening Gates: interacting with a switch or button opens
    - Placable Structures: Player can build/place tear-down/pickup a structure on the tilemap


* [ ] **Vehicles**: ? I have no idea how this would work

#### Platformer
- Review systems and features from Overworld to add to Platformer
- Redesign and resize tile sets
- Implement image based tilemap system
- Enterance/Exit doors to move from Overworld to Platformer and back
- Items
    - Collectable items
        - Coins
        - Item sets (like yoshi coins)
    - Worn Item visuals
        - Held Items/Weapon visuals
- Mechanics
    - Player/Mob collision
        - Damage from collision on player
        - Damage on Mob from head collision
        - Damage on Mob from attack collision
        - Damage on Mob from projectile collision
    - Running/Sprinting: Fine tune player movement
    - Dash: Move foward quickly over a short distance leaving a blur behind you
        - On the ground
        - While jumping
    - Double Jump
    - Wall Jumping
    - Wall Climbing
    - Improved Gliding
    - Flying
    - Diagonal/Vertical Directed Projectiles
    - Phasing avoids attacks
    - Blocking reduces damage from attacks
    - Time Speed Changes - Slow down or speed up immediate time 
    - Time Reversal
    - Structures
        - Structures that damage and shoot projectiles
        - Structures that help and heal the player
        - Structures that can be climbed on
    - Swimming
        - Water pools hamper player movement
    - Water level
- GUI / Indicators
    - Damage is indicated by floating numbers	
- Environmental Hazards
#### Game Modes
- Developer Mode
    - Move the current game state engine options into a developer menu that offer playing with expanded features that 
    aid in development
- Sandbox Mode
    - Move the current game state engine options into a Sandbox menu
    - Modify the game states to play starting from a list of options/settings to enter the sandbox with
    - Game engines run from Sandbox menu in the format that they exist with now, as an open world that is played non-linearly
- Story Mode
    - Create a new save file
    - Playing a save file runs the game on a scripted story mode which progresses through phases linearly
    - Saving the game persists all the progress in the story progress and game data
#### Turn-Based Game State
- Experiment with a game state that provides turn-based combat, like original Final Fantasy games and Pokemon games.
#### Puzzle Game State
- Create a game state that can support multiple types of puzzle games similar to Dr.Mario, Yoshi, Yoshi's Cookie, 
Bust-a-Move, Tetris, Bomberman, Minesweeper, etc.
- Invent a new Puzzle style game that is inspired by the previously mentioned games.
#### Aerial Flying Game State
- Create a fast-paced side-scrolling game state that allows the player to maneuver a flying sprite and engage in dog-fights


---
## Experimental / Research

### Networking
* [ ] **Client-to-Client Networking**: Add the networking classes from vanZeben's tutorials and successfully network two
game clients on two different PCs over LAN and WAN. Experiment with different configurations and implementations.
* [ ] **Client-to-Server Networking**: Set up a simple Node.js web server on the Google Cloud and experiment with running 
sockets between game clients and the server, updating server logic and coordinating online play. 
* [ ] **Server API**: Create an API that interfaces with another of my projects [Lvlz.app](https://levelz.app) and 
explore ways that real-life accomplishments recorded in Lvlz.app can be applied to game rewards in Omnia.
* [ ] **In App Web Browser**: Explore the web browser capability provided by JavaFX.

### Game Ports and Libraries
* [ ] **Research Android Port**: Research and experiment with porting the game to Android
* [ ] **Research browser port or JavaScript rewrite**: Research and experiment with porting/rewriting the game to JavaScript for the browser
* [ ] **Research Java game libraries**: Research and experiment with game libraries like [FXGL](http://almasb.github.io/FXGL/), [LWJGL](https://lwjgl.org),
and others.
* [ ] **Build C++ Version**: Start recreating basic game features in C++ and compare the process with working in Java
---

**Project History:** 
Back in 2013 I completed two Java game development tutorials from YouTube which have served as the backbone of getting 
the game started. One was a Top-Down game engine by [vanZeben](https://github.com/vanZeben) that mimicks the style of 
the old gameboy pokemon games and other old RPGs like Final Fantasy. The other was a Platformer game engine by 
[foreignguymike](https://github.com/foreignguymike). I planned to combine these engines and begin to build my own game 
out of them while I finished my degree. Shortly after starting that endeavor I was lucky enough to be hired at my first 
tech job working on a huge C# based web app, so my focus shifted to that style of programming. It's been 7 years now and 
I've learned a lot about software development after working for 3 other companies. Now that I'm in a position where I 
work from home and I've re-enrolled in school at [WGU](https://wgu.edu) to finish my bachelor's degree; I'm dusting off 
this old project and starting to work on it again with the hopes of being able to use it as my Senior Capstone project for 
school. I'd like to put my head down for all of 2021 and just code/draw as much as possible, then re-evaluate the project
when it's time to write my final capstone papers for school.

---


## Omnia Software License
I'm not a license expert so please DM me with corrections. I think it's fair that if you're going to use this code and 
work on this game on your own, especially with plans to turn it into something of your own (which you're free to do) 
then I should get some credit for my work. That's the idea behind using the BSD 4-Clause license for this work. I expect
the type of license used on this project will change as the project continues and may at some point become proprietary, 
but this won't happen soon. If there's anything I can do to help your work, please DM me. Thanks!
#### BSD 4-Clause
Copyright (c) 2020 William Forbes c/o Submodern Studios, LLC. All rights reserved.

This software was developed with technology made by William Forbes at Submodern Studios, LLC.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in
    the documentation and/or other materials provided with the
    distribution.

    3. All advertising materials mentioning features or use of this
    software must display the following acknowledgement: This product
    includes software developed by William Forbes, Submodern Studios 
    and its contributors.

    4. Neither the name of the University nor the names of its
    contributors may be used to endorse or promote products derived
    from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS''
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS
BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
