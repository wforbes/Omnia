# Omnia
Retro 2D Role-Playing Video Game that blends the Fantasy, Sci-Fi, and Steam-Punk genres. 

Targeting the PC, Mac, Linux, and Android platforms. Written using Java and JavaFX.

(Readme last updated: 1/5/2020)

![Screenshots of Omnia version 0.0.1](https://wforbes.net/wp-content/uploads/2020/12/screens_v0-0-1-1024x762.png)

## Project
Omnia is a 2D 'Retro' Role-Playing Game written in Java. I'm working on it so that I can practice programming in Java, 
experiment with ideas, and have fun. My girlfriend is an author and creative writing teacher so I'll also be collaborating 
with her for writing the game script and storyline.

This is truly a passion project. Instead of using a sophisticated game engine like Unreal, Unity, Godot, or GameMaker 
I thought I would get the most out of developing the engine myself by reading/watching game tutorials/devlogs, getting 
inspiration from game libraries, and inventing the systems myself. 

### Goals
My core goals are focused on:
  * Developing a homegrown game engine that's enjoyable to code in and expandable to the limits of imagination.
  * Building an enjoyable experience for the player that uses multiple 2D engine styles, rewarding quest experiences, 
  immersive/dynamic NPC interactions, and fun online interactions with other players. 
  * Designing a game world that's visually and interactively interesting, weird, exciting, and enjoyable to play within.
  * Writing a game story that feels like you're going on an exciting journey that will not only save the world but the 
  entire space-time continuum of your own universe and all other parallel universes.

## Story

Some of the first concepts of the world can be found here: [wforbes.net/omnia-story-notes](https://wforbes.net/omnia-story-notes)

---
## Starter Todo List (Dec 2020)
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
* [ ] **Implement JavaFX**(3/4): Add a JavaFX implementation of the core Game running classes to enable UI development and 
more precise (float, double) figures for game logic.
    * [X] ~~**Game Loop with AnimationTimer**: Test and implement the JavaFX.AnimationTimer for use with the game loop.~~
    * [X] ~~**JavaFX graphics rendering**: Test and implement JavaFX graphics classes (GraphicsContext, Image, PixelReader, 
    PixelWriter, etc.) to recreate, combine and improve on the graphics algorithms for Top-Down and Platformer namespaces~~
    * [X] ~~**Implement game logic**: Enable all existing game logic; updating levels and entities, taking key input and
    all other existing features.~~
    * [ ] **Implement new UI for menus**: Use JavaFX features to build a simple UI for the MainMenu, PauseMenu, and 
    DeathMenu.
---
## Game Development Todo Lists (2021-2022)
* [ ] **Top-Down GameState Tasks**:
    * [ ] **Improve collision logic (wall sliding)**: Right now, when you collide with a solid tile or entity your 
    movement stops. That's ok if you're moving perpendicular to the surface, but not if you're moving diagonally against
    it. Logic should be added that checks for this diagonal movement and only restricts the perpendicular vector. The
    goal is to allow the player to slide against the colliding tile/entity in the parallel direction.
    * [ ] **Improve tile graphics**: Right now, there are only basic low resolution tiles for grass and water. These 
    tiles, and perhaps the tile system itself, needs to be improved to allow for more detailed and varied ground tiles.
    This should include a few different grass tiles, dirt tiles, grass/dirt border tiles, grass/water border tiles,   
    * [ ] **Add level structures**: Right now, there are only blank solid tiles. Tiles that show graphics for buildings,
    cliffs, caves, and other solid topography need to be added.
        * [ ] **Add platformer entrance doors**: On level structures like caves or ruins, add doors that when the player 
        collides with them the game state changes to Platformer and opens the appropriate Platformer level.
    * [ ] **Implement Chat/Dialog UI Window**: Add a text box UI component that accepts typed text input from the user 
    and displays the result in the text box after typing Enter or clicking a Send button. The chat/dialog should be 
    semi-transparent so as to not block view of the level.
        * [ ] **Experiment with Game Commands**: Experiment with some basic commands which will return some game 
        information or modify the game state. These commands should be prefaced with a identifying character like '/' 
        or ';', and accept parameters. Example: typing "/getLocation" should display the player's current X/Y coordinates. 
        "/getTime real" should display the current real-life time, while "/getTime game" should display the current game world 
        time. "/setTime game (day) (hour) (minute)" should adjust the time of day to the specified day/hour/minute, if 
        hour or minute aren't provided then the beginning of the day or hour should be used. Brainstorm more useful ideas.
        * [ ] **Experiment with Chat Commands**: Experiment with different chat commands like "/say (message)" or 
        "/shout (message)" that show up in different colors in the Chat window. "/tell (EntityName) (message)" should 
        display purple text in the window and allow for direct conversation with the Entity specified in the first 
        argument. 
    * [ ] **Experiment with chat bubbles**: Test ideas on adding a chat bubble above the player when they "/say" something
    * [ ] **Add non-combat NPC entities**: Add a couple different non-combat entities that allow for some conversation 
    and other interaction. Ideas: Quest giver, Quest participant, Merchant, Friend, Stranger, etc.
        * [ ] **NPC time of day location change**: Depending on the game time, static NPCs should appear in a different 
        static location. If they are pacing/wandering they should move to a different location and pace/wander there.
        * [ ] **Implement NPC chat conversation**: When you approach an NPC and hail them, they should respond with text 
        in the chat window. This chat should include trigger phrases that, when the user repeats back to them, continues
        the conversation. Inspiration: Everquest quest dialogs.
            * [ ] **NPC behavior change with chat**: Test trigger phrases changing the NPCs behavior.
                * [ ] **Phases cause NPC movement changes**: Trigger phrases should be able to cause NPCs movement to 
                change (stop wandering, wander in a different pattern, follow the Player, )
                * [ ] **Finishing conversations open new ones**: Finishing a conversation should cause them to start a 
                different conversation the next time they are hailed.
                * [ ] **NPC conversations change with game time**: Depending on the game time, the NPC should react to 
                chat conversation differently.
        * [ ] **Implement NPC party system**: (TODO)
        * [ ] **Add Currency system**: Add a simple inventory window that displays the amount of money the player has.
        * [ ] **Implement Quest System**: Implement a system that allows the user to get a quest from an NPC, track the 
        quest in a UI window, fulfill the quest's requirements (test something simple like going to a specified location
        and talking to a different NPC), and then returning to the quest giver with the quest completed to receive a 
        reward.
        * [ ] **Quest Reward: Currency**: When the user finishes a quest, reward them with some money and add it to 
        their inventory.
        * [ ] **Implement Merchant System**: Add a system that allows the user to buy items from and sell items to 
        Merchant NPCs by deducting or adding currency in exchange for items in the player's inventory.
    * [ ] **Implement Player inventory UI**: (todo)
    * [ ] **Add health/damage system**: (todo)
    * [ ] **Implement Player Vitals Status UI**: (todo)
    * [ ] **Implement basic combat**: (todo)
    * [ ] **Add top-down entrance/exit doors**: On level structures like houses, stores, buildings, and castles; add 
    outer doors that when the player collides with them the top-down level changes to the appropriate level for that 
    structure and the player is spawned at their inner enterance/exit door.
        * [ ] **Create house levels**: Create a few small levels that correspond to houses and buildings.
        * [ ] **Create castle levels**: Create a few larger levels that correspond to castles and contain multiple 
        doors to move from room levels in the castle.
        * [ ] **Create NPCs for houses and castles**: Create some friendly NPCs to interact with in each of the house, 
        store, building, and castle levels.
* [ ] **Platformer GameState Tasks**: (todo)
    * [ ] **Add friendly NPC entities**: (todo)
    * [ ] **Implement Dialog UI**: (todo)
    * [ ] **Add player/entity collision**: (todo)
    * [ ] **Add health/damage system**: (todo)
    * [ ] **Implement Player Vitals Status UI**: (todo)
* [ ] **UI/Game-Wide Tasks**: (todo)
    * [ ] **Implement Player Leveling/Experience System**: (todo)
    * [ ] **Implement Player Statistics System**: (todo)
    * [ ] **Implement Skills System**: (todo)
    * [ ] **Implement Spells System**: (todo)
    * [ ] **Implement Class System**: (todo)
---
## Experimental / Research

### Other Engines
* [ ] **Turn-Based Game State**: Experiment with a game state that provides turn-based combat, like old Final Fantasy 
and Pokemon games.

### Networking
* [ ] **Client-to-Client Networking**: Add the networking classes from vanZeben's tutorials and successfully network two
game clients on two different PCs over LAN and WAN. Experiment with different configurations and implementations.
* [ ] **Client-to-Server Networking**: Set up a simple Node.js web server on the Google Cloud and experiment with running 
sockets between game clients and the server, updating server logic and coordinating online play. 
* [ ] **In App Web Browser**: Explore the web browser capability provided by JavaFX.

### Game Ports and Libraries
* [ ] **Research Android Port**: Research and experiment with porting the game to Android
* [ ] **Research browser port or JavaScript rewrite**: Research and experiment with porting/rewriting the game to JavaScript for the browser
* [ ] **Research Java game libraries**: Research and experiment with game libraries like [FXGL](http://almasb.github.io/FXGL/), [LWJGL](https://lwjgl.org), 
and others.
---
## Documentation
* [ ] **Begin a Game Design Document**
    * [ ] Brainstorm on the following topics:
        * [ ] **Define Prototype Complete**: What should the user be able to do on the game for the 'prototype' status
        to be complete and 'alpha' status to begin.
        * [ ] **Define Full Scope**: Outline the full scope of the project to be ready for production (3 year timeframe)
        * [ ] **Mechanics**: Start a list of how the mechanics of the prototype game will work and their purpose.
        * [ ] **Storyline**: Define the characters and setting, the journey they go on, the problems they need to solve
        * [ ] **Education**: Pull together some basic things that the game should teach in it's educational content

---
---

**Project History:** 
Back in 2013 I completed two Java game development tutorials from YouTube which have served as the backbone of getting 
the game started. One was a Top-Down game engine by [vanZeben](https://github.com/vanZeben) that mimicks the style of 
the old gameboy pokemon games and other old RPGs like Final Fantasy. The other was a Platformer game engine by 
[foreignguymike](https://github.com/foreignguymike). I planned to combine these engines and begin to build my own game 
out of them while I finished my degree. Shortly after starting that endeavor I was lucky enough to be hired at my first 
tech job working on a huge C# based web app, so my focus shifted to that style of programming. It's been 7 years now and 
I've learned a lot about software development after working for 3 other companies. Now that I'm in a position where I 
work from home and I'm enrolled at [WGU](https://wgu.edu) to finish my bachelor's degree; I'm dusting off this old 
project and starting to work on it again with the hopes of being able to use it as my Senior Capstone project for 
school.

---


## Omnia Software License
I'm not a license expert so please DM me with corrections. I think it's fair that if you're going to use this code and 
work on this game on your own, especially with plans to turn it into something of your own (which you're free to do) 
then I should get some credit for my work. That's the idea behind using the BSD 4-Clause license for this work. I expect
the type of license used on this project will change as the project continues and may at some point become proprietary, 
but this won't happen soon. If there's anything I can do to help your work, please DM me. Thanks!
#### BSD 4-Clause
Copyright (c) 2020 William Forbes c/o Submodern Studios, LLC. All rights reserved.

This software was developed by William Forbes at Submodern Studios, LLC.

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
