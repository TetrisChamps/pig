import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {

    public static void main(String[] args) {
        new Pig().program();
    }

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();

    void program() {
        //test();                 // <-------------- Uncomment to run tests!

        final int winPts = 20;    // Points to win
        Player[] players;         // The players (array of Player objects)
        Player actual;            // Actual player for round (must use)
        boolean aborted = false;  // Game aborted by player?
        int playerIndex = 0;

        // Hard coded 2 players, replace *last* of all with ...
        //players = new Player[]{new Player("Olle"), new Player("Fia")};
        players = getPlayers();  // ... this (method to read in all players)

        actual = players[playerIndex];

        welcomeMsg(winPts);


        // TODO Game logic, using small step, functional decomposition
        while (!aborted) {
            /* ----- Input ----- */
            String choice;
            while (true) {
                choice = getPlayerChoice(actual).toLowerCase();
                if (choice.equals("r") || choice.equals("n") || choice.equals("q")) {
                    break;
                }
                out.println("Bad input, please choose between (r)oll, (n)ext, or (q)uit!");
            }

            /* ----- Logic ----- */
            if (choice.equals("r")) {
                int rollValue = rand.nextInt(6) + 1;
                actual.roundPts += rollValue;
                if (actual.roundPts + actual.totalPts >= winPts) {
                    statusMsg(players);
                    break;
                }
                roundMsg(rollValue, actual);
                if (rollValue == 1) {
                    playerIndex = getNextPlayerIndex(playerIndex, players.length);
                    actual.roundPts = 0;
                }
            } else if (choice.equals("n")) {
                playerIndex = getNextPlayerIndex(playerIndex, players.length);
                actual.totalPts += actual.roundPts;
                actual.roundPts = 0;
            } else if (choice.equals("q")) {
                aborted = true;
            }
            actual = players[playerIndex];
            statusMsg(players);
        }

        gameOverMsg(actual, aborted);
    }

    // ---- Game logic methods --------------
    int getNextPlayerIndex(int playerIndex, int length) {
        return ++playerIndex % length;
    }

    // ---- IO methods ------------------

    void welcomeMsg(int winPoints) {
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }

    void statusMsg(Player[] players) {
        out.print("Points: ");
        for (int i = 0; i < players.length; i++) {
            out.println(players[i].name + " = " + players[i].totalPts + " ");
        }
        out.println();
    }

    void roundMsg(int result, Player actual) {
        if (result > 1) {
            out.println("Got " + result + " running total are " + actual.roundPts);
        } else {
            out.println("Got 1 lost it all!");
        }
    }

    void gameOverMsg(Player player, boolean aborted) {
        if (aborted) {
            out.println("Aborted");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts + player.roundPts) + " points");
        }
    }

    String getPlayerChoice(Player player) {
        out.print("Player is " + player.name + ": ");
        return sc.nextLine();
    }

    Player[] getPlayers() {
        // TODO
        out.print("Enter number of players: ");
        int numberOfPlayers = 0;
        try {
            numberOfPlayers = sc.nextInt();
        }
        catch (Exception e) {
            out.println("Number of players must be 1 or greater...");
            exit(-1);
        }
        sc.nextLine();
        Player players[] = new Player[numberOfPlayers];
        for (int i = 0; i < players.length; i++) {
            out.print("Enter player " + (i + 1) + " name: ");
            String name = sc.nextLine();
            players[i] = new Player(name);
        }

        return players;
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0

        Player(String name) {
            this.name = name;
        }
    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data, an array of Players 
        Player[] players = {new Player("a"), new Player("b"), new Player("c")};

        out.append('p');
        out.print("");

        exit(0);   // End program
    }
}



