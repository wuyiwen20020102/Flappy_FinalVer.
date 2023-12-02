
I WANNA BE FLAPPY - Android Application
========================================

Overview
--------
I WANNA BE FLAPPY is an interactive mobile game developed for Android devices. The game incorporates innovative voice control mechanics alongside traditional touch controls, providing a unique and engaging user experience.

Features
--------
1. Dual Control Mechanisms: Players can choose between voice control or touch control modes to navigate the bird in the game.
2. Dynamic Obstacles: The game features moving barriers that require skillful navigation.
3. Character Customization: Players can choose between different bird characters for a personalized gaming experience.
4. Score Tracking: The game keeps track of the player's score and displays the highest score achieved.

Installation and Running
------------------------
To install and run I WANNA BE FLAPPY:
1. Download the APK file from the provided link. (https://github.com/wuyiwen20020102/Flappy_FinalVer./blob/d778571b1274bc8fe22afc1ec5963aa68e328940/app/release/I_WANNA_BE_FLAPPY.apk)
2. Install the APK on your Android device (ensure that 'Install from Unknown Sources' is enabled in your device settings).
3. Open the app and select your preferred control mode to start playing.

Components
----------
1. `AudioRecorder`: Manages audio recording and noise level measurement for voice control.
2. `Bird`: Represents the player's character in the game, handles movement and state changes.
3. `Barrier`: Represents obstacles in the game, handles barrier movement and collision detection.
4. `GameOver`: Activity that displays the game over screen and score details.
5. `GameProperty`: Contains game constants like character images and game state identifiers.
6. `GameView_Touch`: SurfaceView for touch control mode, handles game rendering and touch interactions.
7. `GameView_Voice`: SurfaceView for voice control mode, similar to `GameView_Touch` but with voice control integration.
8. `InstructionMenu`, `PlayMenu`, `StartingMenu`: Activities for game menus and instructions.

Playing the Game
----------------
- Touch Control Mode: Tap on the screen to make the bird fly upwards. Release to let it descend.
- Voice Control Mode: Make a loud noise (like clapping or speaking) to make the bird ascend. Silence will let it descend.

Permissions
-----------
- RECORD_AUDIO: Required for the voice control functionality in the game.

Support
-------
For support or further queries, please contact us at eseoh@bu.edu.

Enjoy the Game!
