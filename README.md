# SnakeAndLadders

This is implementation of classic Snakes and Ladders game with multi-player support alongwith simulations. 
Different kind of metrics such as number of wins, min/avg/max no of dice rolls for win, min/avg/max distance travelled via snakes & ladders, etc.

## Rules:
 - The game starts at position 0 which is outside the board and it finishes when a player reaches the last square that is 100.
 - The player rolls a single 6 sided dice (die) numbered from 1 to 6 to move their token by the number of squares indicated by the die rolled.
 - If, on completion of a roll, a player's token lands on the lower-numbered end of a "ladder", the player moves the token up to the ladder's higher-numbered square.
 - If the player lands on the higher-numbered square of a "snake" (or chute), the token must be moved down to the snake's lower-numbered square
 - If a 6 is rolled by the player, the player gets an additional roll after the token is moved. This additional roll is considered as a part of the same turn of the current player (1 turn can have multiple rolls if 6 is rolled); otherwise, play passes to the next player in turn.
 - The player who is first to bring their token to the last square of the board is the winner.
 - The player must roll the exact number to reach the final square. For example, if a player
needs a 3 to reach square 100 and rolls a 5, the player stays at the current location.
   - If the player rolls 6 when they are stuck, they get additional rolls, but the token does not move ahead. For example, 3 is needed to win and the player rolls a 6. Then they get the additional roll. If they roll 6 again, they get another roll, and so
on.

## Quick steps to run:
 - Checkout the code.
 - Do `mvn clean install` on root directory.
 - Once build is success, run the `Application.java` file directly from your IDE.
 - Or otherwise, you can run jar via command line using:
   - `java -jar target/SnakeAndLadders-1.0-SNAPSHOT.jar`


Happy Coding!
