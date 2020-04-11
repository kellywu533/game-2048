Game 2048
=========
Introduction:
-------------

This is the Java implementation of the popular game 2048.
It features game sound effects when running on Mac OS. The game tiles are buffered to make the tile movement smooth.
It also overrides the repaint method to reduce the flicker.

Reuqirements:
-------------
* Java - 1.8 and up
* Maven - 3.6.3 and up

Build and Run:
--------------
```shell
mvn install

java -jar target/game-2048-*-jar-with-dependencies.jar
```

Sounds:
-------
For Windows or Linux users, modify effect sound files defined in the the SoundPlayer.Type enum.

Screenshots:
------------
<img src="./docs/images/game-1.png" width="300" alt="Game Screen Shot" title="Game Screen Shot">

<img src="./docs/images/2048-3.gif" width="300" alt="Game Recording" title="Game Play">

<img src="./docs/images/2048-4-game-over.gif" width="300" alt="Game Recording" title="Game Over">
