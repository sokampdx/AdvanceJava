import java.util.Random;

public class MyAgent extends Agent
{
    Random r;

    /**
     * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     */
    public MyAgent(Connect4Game game, boolean iAmRed)
    {
        super(game, iAmRed);
        r = new Random();
    }

    /**
     * The move method is run every time it is this agent's turn in the game. You may assume that
     * when move() is called, the game has at least one open slot for a token, and the game has not
     * already been won.
     * 
     * By the end of the move method, the agent should have placed one token into the game at some
     * point.
     * 
     * After the move() method is called, the game engine will check to make sure the move was
     * valid. A move might be invalid if:
     * - No token was place into the game.
     * - More than one token was placed into the game.
     * - A previous token was removed from the game.
     * - The color of a previous token was changed.
     * - There are empty spaces below where the token was placed.
     * 
     * If an invalid move is made, the game engine will announce it and the game will be ended.
     * 
     */
    public void move()
    {
        int move = iCanWin();

        if (move == -1) {
            move = theyCanWin();
            if (move == -1) {
                move = bestMove();
            }
        }

        moveOnColumn(move);
    }



    private int bestMove() {
        return randomMove();
    }

    /**
     * Drops a token into a particular column so that it will fall to the bottom of the column.
     * If the column is already full, nothing will change.
     * 
     * @param columnNumber The column into which to drop the token.
     */
    public void moveOnColumn(int columnNumber)
    {
        int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));   // Find the top empty slot in the column
                                                                                                  // If the column is full, lowestEmptySlot will be -1
        if (lowestEmptySlotIndex > -1)  // if the column is not full
        {
            Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);  // get the slot in this column at this index
            if (iAmRed) // If the current agent is the Red player...
            {
                lowestEmptySlot.addRed(); // Place a red token into the empty slot
            }
            else // If the current agent is the Yellow player (not the Red player)...
            {
                lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
            }
        }
    }

    /**
     * Returns the index of the top empty slot in a particular column.
     *
     * @param column The column to check.
     * @return the index of the top empty slot in a particular column; -1 if the column is already full.
     */
    public int getLowestEmptyIndex(Connect4Column column) {
        int lowestEmptySlot = -1;
        for  (int i = 0; i < column.getRowCount(); i++)
        {
            if (!column.getSlot(i).getIsFilled())
            {
                lowestEmptySlot = i;
            }
        }
        return lowestEmptySlot;
    }

    /**
     * Returns an array of Lowest Empty Slot Index for each Column.
     *
     * @return an array of Lowest Empty Slot Index for each Column.
     */
    private int [] getAllLowestEmptyIndex() {
        int numCol = myGame.getColumnCount();
        int [] lowestIndexOfCol = new int[numCol];

        for (int i = 0; i < numCol; ++i) {
            lowestIndexOfCol[i] = getLowestEmptyIndex(myGame.getColumn(i));
        }
        return lowestIndexOfCol;
    }

    /**
     * Returns a random valid move. If your agent doesn't know what to do, making a random move
     * can allow the game to go on anyway.
     * 
     * @return a random valid move.
     */
    public int randomMove()
    {
        int i = r.nextInt(myGame.getColumnCount());
        while (getLowestEmptyIndex(myGame.getColumn(i)) == -1)
        {
            i = r.nextInt(myGame.getColumnCount());
        }
        return i;
    }

    /**
     * Returns the column that would allow the agent to win.
     * 
     * You might want your agent to check to see if it has a winning move available to it so that
     * it can go ahead and make that move. Implement this method to return what column would
     * allow the agent to win.
     *
     * @return the column that would allow the agent to win. -1 if no winning column.
     */
    public int iCanWin()
    {
        Connect4Game currentGame = new Connect4Game(myGame);
        return getWinnerResult(currentGame, iAmRed);
    }

    /**
     * Returns set the correct Color in the testSlot and return the char associate with the color.
     * 'R' for red and 'Y' for yellow.
     *
     * @param currentGame is the Game that is being test for the next Winner
     * @param testRed is the color being tested in the currentGame
     * @return the column that would create a winner for the next move. -1 if no winning column.
     */
    private int getWinnerResult(Connect4Game currentGame, boolean testRed) {
        int [] lowestIndexOfCol = getAllLowestEmptyIndex();

        int numCol = myGame.getColumnCount();
        boolean isFound = false;
        int result = -1;
        char winner;
        int i = 0;

        while (i < numCol && !isFound) {
            Connect4Game testGame = new Connect4Game(currentGame);
            int lowestIndex = lowestIndexOfCol[i];
            if (lowestIndex > -1) {
                Connect4Slot testSlot = testGame.getColumn(i).getSlot(lowestIndexOfCol[i]);

                if (testRed) {
                    testSlot.addRed();
                    winner = 'R';
                } else {
                    testSlot.addYellow();
                    winner = 'Y';
                }
                isFound = (testGame.gameWon() == winner);
            }
            ++i;
        }

        if (isFound)
            result = i - 1;

        return result;
    }

    /**
     * Returns the column that would allow the opponent to win.
     * 
     * You might want your agent to check to see if the opponent would have any winning moves
     * available so your agent can block them. Implement this method to return what column should
     * be blocked to prevent the opponent from winning.
     *
     * @return the column that would allow the opponent to win. -1 if no winning column.
     */
    public int theyCanWin()
    {
        Connect4Game currentGame = new Connect4Game(myGame);
        return getWinnerResult(currentGame, !iAmRed);
    }

    /**
     * Returns the name of this agent.
     *
     * @return the agent's name
     */
    public String getName()
    {
        return "My Agent";
    }
}
