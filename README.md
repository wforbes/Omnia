# Omnia

This is an RPG made so that I can practice Java, experiment with ideas, and have fun.

(Last updated: 11/1/2019)
___
---
### Todo List
* [x] Resurrect [an old game engine](https://www.youtube.com/watch?v=DIMeRYfil7c&list=PLRjjchumlJl2LIs1esk_C9RPvlEf2GOLb) I worked on in 2013 based loosely on two very old tutorials:
    * [x] **Top-Down Tutorial**: Find my code based on [Java 2D Game Development](https://www.youtube.com/watch?v=VE7ezYCTPe4&list=PL8CAB66181A502179) by vanZeben
    * [x] **Platformer Tutorial**: Find my code based on [Java 2D Game Programming Platformer Tutorial](https://www.youtube.com/watch?v=9dzhgsVaiSo&list=PL-2t7SM0vDfcIedoMIghzzgQqZq45jYGv) by ForeignGuyMike
    * [ ] **Playable Prototype**: Combine the two engine's code and improve switching between the two at runtime
        * [ ] **Top-Down Code**: Copy over useful code from vanZeben's tutorial, study it and optimize/make improvements
            * [X] **Game Loop & JFrame**: Get the Game class with the game loop running and updating tps/fps.
            * [X] **Screen & SpriteSheet**: Add Screen class and SpriteSheet class, successfully compile while accessing the sheet image.
            * [X] **Level & Tile**: Add InputHandler class, Level class, and Tile classes. Render a small level on the Screen.
            * [ ] **Player & Movement**: Add Entity, Player, Mob, Monster classes. Successfully move the Player around a large level.
            * [ ] **Networking**: Add the networking classes and successfully network two game clients on two different PCs.
            * [ ] **Optimize**: Scour over the code endlessly and experiment with adjustments in it's methods and structure.
                * [ ] Would it be better to use JavaFX?
                * [ ] Are the methods rendering pixels and other foundations the best way to do that? 
        * [ ] Copy over useful code from ForeignGuyMikes's tutorial, study it and optimize/make improvements
            * [ ] (To-do list for Platformer engine coming soon...)
        * [ ] Develop an optimized game loop that both the engines can use successfully and handles switching between them seamlessly
        * [ ] Develop a menu system that will save a simple game state in both top-down and platformer
        * [ ] Successfully network two clients both running the game with both engines on two different PCs
    * [ ] Research and experiment with porting the game to Android
    * [ ] Research and experiment with game engines like [LWJGL](https://lwjgl.org)

* [ ] **Begin a Game Design Document**
    * [ ] Brainstorm on the following topics:
        * [ ] **Define Prototype Complete**: What should the user be able to do on the game for the 'prototype' status
        to be complete and 'alpha' status to begin.
        * [ ] **Define Full Scope**: Outline the full scope of the project to be ready for production (3 year timeframe)
        * [ ] **Mechanics**: Start a list of how the mechanics of the prototype game will work and their purpose.
        * [ ] **Storyline**: Define the characters and setting, the journey they go on, the problems they need to solve
        * [ ] **Education**: Pull together some basic things that the game should teach in it's educational content

(more information to come soon...)
<br>
<br>
<br>

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

All advertising materials mentioning features or use of this software
must display the following acknowledgement: This product includes
software developed by William Alexander Forbes, Submodern Studios.

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
