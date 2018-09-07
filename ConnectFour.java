import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Class to play a Game of Connect 4.
 * Computer plays black, human plays white.
 */
public class ConnectFour {

    /** Number of columns on the board. */
    public static final int COLUMNS = 7;

    /** Number of rows on the board. */
    public static final int ROWS = 6;

    /** Color for computer player's pieces */
    public static final Color COMPUTER = Color.BLUE;

    /** Color for human player's pieces */
    public static final Color HUMAN = Color.RED;

    /** Color for blank spaces. */
    public static final Color NONE = Color.WHITE;


    /**
     * Drops a piece of given Color in column.  Modifies the board
     * array. Assumes the move is legal.
     *
     * @param board The game board.
     * @param color Color of piece to drop.
     * @param column Column in which to drop the piece.
     */
    public static void drop(Color[][] board, Color color, int column) {

        for(int r = 0; r < ROWS; r++) {
            if(board[r][column] == NONE) {
                board[r][column] = color;
                break;
            }
        }
    }


    /**
     * Checks if the board is full.
     * @param board The game board.
     * @return True if board is full, false if not.
     */
    public static boolean full(Color[][] board) {
        // we only need to check top row for available spots
        for(int c = 0; c < COLUMNS; c++) {
            if(board[ROWS-1][c] == NONE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if dropping a piece into the given column would be a
     * legal move.
     *
     * @param board The game board.
     * @param column The column to check.
     * @return true if column is neither off the edge of the board nor full.
     */
    public static boolean legal(java.awt.Color[][] board, int column) {
        // all we need to check if there is at least one available spot
        // at the top of a column.
        return column >= 0 && column < COLUMNS && board[ROWS-1][column] == NONE;
    }

    /**
     * Given the color of a player, return the color for the opponent.
     * Returns human player color when given computer player color.
     * Returns computer player color when given human player color.
     * @param color Player color.
     * @return Opponent color.
     */
    public static Color opposite(java.awt.Color color) {
        return color == COMPUTER ? HUMAN : COMPUTER;
    }

    /**
     * purely for debugging. Prints full board.
     * @param board
     */
    public static void printBoard(Color[][] board){
        char space = ' ';
        for(int r = ROWS-1; r >= 0; r--){
            for(int c = 0; c < COLUMNS; c++) {
                space = '_';
                if(board[r][c] == COMPUTER) {
                    space = 'C';
                } else if(board[r][c] == HUMAN) {
                    space = 'H';
                }
                System.out.print(" " + space);
            }
            System.out.println();
        }

    }

    /**
     * Check for a win starting at a given location and heading in a
     * given direction.
     *
     * Returns the Color of the player with four in a row starting at
     * row r, column c and advancing rowOffset, colOffset each step.
     * Returns NONE if no player has four in a row here, or if there
     * aren't four spaces starting here.
     *
     * For example, if rowOffset is 1 and colOffset is 0, we would
     * advance the row index by 1 each step, checking for a vertical
     * win. Similarly, if rowOffset is 0 and colOffset is 1, we would
     * check for a horizontal win.
     * @param board The game board.
     * @param r Row index of where win check starts
     * @param c Column index of where win check starts
     * @param rowOffset How much to move row index at each step
     * @param colOffset How much to move column index at each step
     * @return Color of winner from given location in given direction
     *         or NONE if no winner there.
     */
    public static Color winAt(Color[][] board, int r, int c,
                              int rowOffset, int colOffset) {


        Color possible = board[r][c];
        int row, col, multiplier = 1;
        if(possible == NONE){
            return NONE;
        }
        do {
            if(multiplier == 4){
                return possible;
            }
            row = rowOffset * multiplier + r;
            col = colOffset * multiplier + c;
            multiplier++;
        } while(row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && board[row][col] == possible);
        return NONE;
    }



    /**
     * Checks entire board for a win (4 in a row).
     *
     * @param board The game board.
     * @return color (HUMAN or COMPUTER) of the winner, or NONE if no
     * winner yet.
     */
    public static Color winner(Color[][] board) {
        Color color;
        //use winAt to help
        for (int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLUMNS; col++){
                // check all 8 directions
                for(int r_offset = -1; r_offset <= 1; r_offset++){
                    for(int c_offset = -1; c_offset <= 1; c_offset++){
                        // skip no offset: 0 0
                        if(!(r_offset == 0 && c_offset == 0)){
                            color = winAt(board, row, col, r_offset, c_offset);
                            if(color != NONE){
                                return color;
                            }
                        }
                    }
                }
            }
        }
        return NONE;
    }


    /**
     * Returns computer player's best move.
     * @param board The game board.
     * @param maxDepth Maximum search depth.
     * @return Column index for computer player's best move.
     */
    public static int bestMoveForComputer(Color[][] board, int maxDepth) {
        // Hint: this will be similar to max
        int bestResult = -2, bestCol = 0;
        for (int c = 0; c < COLUMNS; c++) {
            if (legal(board, c)) {
                drop(board, COMPUTER, c);
                int result = min(board, maxDepth, 0);
                undo(board, c);
                if (result >= bestResult) {
                    bestResult = result;
                    bestCol = c;
                }
            }
        }
        return bestCol;
    }

    /**
     * Returns the value of board with computer to move:
     *     1 if computer can force a win,
     *     -1 if computer cannot avoid a loss,
     *     0 otherwise.
     *
     * @param board The game board.
     * @param maxDepth Maximum search depth.
     * @param depth Current search depth.
     */
    public static int max(Color[][] board, int maxDepth, int depth) {
        // Hint: this will be similar to min
        Color winner = winner(board);
        if (winner == HUMAN) {
            return -1;
        } else if (winner == COMPUTER) {
            return 1;
        } else if (full(board) || (depth == maxDepth)) {
            return 0;
        } else {
            int bestResult = -2;

            for (int c = 0; c < COLUMNS; c++) {
                if (legal(board, c)) {
                    // This column is a legal move. We'll drop a piece
                    // there so we can see how good it is.
                    drop(board, COMPUTER, c);
                    // Call min to see what the value would be for the
                    // computer's best play. The min method will end
                    // up calling max in a similar fashion in order to
                    // figure out the best result for the human's
                    // turn, assuming the computer will play perfectly in
                    // response.
                    int result = min(board, maxDepth, depth + 1);

                    undo(board, c);

                    if (result >= bestResult) {
                        bestResult = result;
                    }
                }
            }
            return bestResult;

        }
    }


    /**
     * Returns the value of board with human to move: 
     *    1 if human cannot avoid a loss,
     *    -1 if human can force a win,
     *     0 otherwise.
     *
     * @param board The game board.
     * @param maxDepth Maximum search depth.
     * @param depth Current search depth.
     */
    public static int min(Color[][] board, int maxDepth, int depth) {

        // The comments in this method are rather verbose to help you
        // understand what is going on. I don't expect you to be so
        // wordy in your own code.

        // First, see if anyone is winning already
        Color winner = winner(board);
        if (winner == COMPUTER) {
            // computer is winning, so human is stuck
            return 1;
        } else if (winner == HUMAN) {
            // human already won, no chance for computer
            return -1;
        } else if (full(board) || (depth == maxDepth)) {
            // We either have a tie (full board) or we've searched as
            // far as we can go. Either way, call it a draw.
            return 0;
        } else {
            // At this point, we know there isn't a winner already and
            // that there must be at least one column still available
            // for play. We'll search all possible moves for the human
            // player and decide which one gives the lowest (best for
            // human) score, assuming that the computer would play
            // perfectly.

            // Start off with a value for best result that is larger
            // than any possible result.
            int bestResult = 2;

            // Loop over all columns to test them in turn.
            for (int c = 0; c < COLUMNS; c++) {
                if (legal(board, c)) {
                    // This column is a legal move. We'll drop a piece
                    // there so we can see how good it is.
                    drop(board, HUMAN, c);
                    // Call max to see what the value would be for the
                    // computer's best play. The max method will end
                    // up calling min in a similar fashion in order to
                    // figure out the best result for the computer's
                    // turn, assuming the human will play perfectly in
                    // response.
                    int result = max(board, maxDepth, depth + 1);
                    // Now that we have the result, undo the drop so
                    // the board will be like it was before.
                    undo(board, c);

                    if (result <= bestResult) {
                        // We've found a new best score. Remember it.
                        bestResult = result;
                    }
                }
            }
            return bestResult;
        }
    }


    /**
     * Removes the top piece from column. Modifies board. Assumes
     * column is not empty.
     * @param board The game board.
     * @param column Column with piece to remove.
     */
    public static void undo(java.awt.Color[][] board, int column) {
        // We'll start at the top and loop down the column until we
        // find a row with a piece in it. 
        int row = ROWS - 1;
        while(board[row][column] == NONE && row > 0) {
            row--;
        }

        // Set the top row that had a piece to empty again.
        board[row][column] = NONE;
    }

    /** Creates board array and starts game GUI. */
    public static void main(String[] args) {
        // create array for game board
        Color[][] board = new Color[ROWS][COLUMNS];
        // fill board with empty spaces
        for(int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = NONE;
            }
        }

        // show the GUI and start the game
        ConnectFourGUI.showGUI(board, HUMAN, 6);
    }


}
class ConnectFourGUI {

    public static final int ROWS = ConnectFour.ROWS;
    public static final int COLUMNS = ConnectFour.COLUMNS;

    public static final Color COMPUTER = ConnectFour.COMPUTER;
    public static final Color HUMAN = ConnectFour.HUMAN;
    public static final Color NONE = ConnectFour.NONE;
    public static final Color BOARD_COLOR = Color.YELLOW;

    /**
     * Construct and display GUI for a Connect Four game.
     * @param board The game board
     * @param firstPlayer The color of player who will start.
     * @param depth How far ahead will computer player look?
     */
    public static void showGUI(final Color[][] board,
                               final Color firstPlayer,
                               final int depth) {
        // For thread safety, invoke GUI code on event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConnectFourGUI gui =
                        new ConnectFourGUI(board, firstPlayer, depth);
                gui.startGame();
            }
        });
    }

    /**
     * Class used to draw the game board.
     */
    @SuppressWarnings({ "serial" })
	private class BoardPanel extends JPanel {

        public BoardPanel() {
            setBackground(BOARD_COLOR);
        }

        public int getRowHeight() {
            return getHeight() / ConnectFour.ROWS;
        }

        public int getColumnWidth() {
            return getWidth() / ConnectFour.COLUMNS;
        }

        @Override
        public void paintComponent( Graphics g ) {
            super.paintComponent(g);

            int rowHeight = getRowHeight();
            int colWidth = getColumnWidth();
            int rowOffset = rowHeight / 8;
            int colOffset = colWidth / 8;

            for(int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    // Flipping columns vertically so row 0 will be at bottom.
                    g.setColor(board[ROWS-row-1][col]);
                    g.fillOval( col * colWidth + colOffset,
                            row * rowHeight + rowOffset,
                            colWidth - 2*colOffset,
                            rowHeight - 2*rowOffset);
                }
            }
        }
    }

    /** Array for the game board. */
    private Color[][] board;
    /** Which player's turn is it? */
    private Color currentPlayer;
    /** How many moves ahead will the computer player search? */
    private int depth;

    // GUI components
    private final JFrame boardFrame;
    private final BoardPanel boardPanel;
    private final JLabel statusLabel;

    /**
     * Handles a mouse click on the board.
     * @param x X position of the click
     * @param y Y position of the click
     */
    private void doMouseClick(int x, int y) {
        int column = x / boardPanel.getColumnWidth();

        if( currentPlayer == HUMAN ) {
            if( ConnectFour.legal(board, column) ) {
                dropPiece(column);
            } else {
                System.out.println("Human attempted illegal move at column " + column);
            }
        } else {
            System.out.println("Ignoring click on computer's turn");
        }

    }

    /**
     * Drop a piece for the current player in the given column and
     * update for next player's turn.
     * @param column Column where piece should be dropped.
     */
    private void dropPiece(int column) {
    	ConnectFour.drop(board, currentPlayer,  column);
        currentPlayer = ConnectFour.opposite(currentPlayer);
        boardFrame.repaint();

        checkForWin();
    }

    /**
     * Have the computer make a move.
     */
    private void computerTurn() {
        if(currentPlayer == COMPUTER) {
            // Computer player may take a while so use worker thread
            // to think in the background instead of causing the GUI
            // to lock up.
            SwingWorker<Integer, ?> worker = new SwingWorker<Integer, Object>() {
                @Override
                public Integer doInBackground() {
                    // Make a copy of board for computer to play with
                    // so we won't refresh the GUI with the computer's
                    // thoughts.
                    Color[][] boardCopy = new Color[ROWS][COLUMNS];
                    for(int i = 0; i < board.length; i++) {
                        boardCopy[i] = java.util.Arrays.copyOf(board[i], board[i].length);
                    }
                    return ConnectFour.bestMoveForComputer(boardCopy, depth);
                }

                @Override
                protected void done() {
                    try {
                        int column = get();
                        if( ConnectFour.legal(board, column) ) {
                            dropPiece(column);
                        } else {
                            // Shouldn't happen if ConnectFour methods
                            // are valid, but complain just in case.
                            System.out.println("Computer attempted illegal move at column " + column);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            worker.execute();
        } else {
            System.out.println("Ignoring attempted computer play on human's turn.");
        }
    }

    /**
     * See if anyone has won and announce it if they have.
     */
    private void checkForWin() {
        statusLabel.setText("Checking for win...");

        // Checking for win may take some time, so use background
        // thread to keep GUI from locking up.
        SwingWorker<Color, ?> worker = new SwingWorker<Color, Object>() {
            @Override
            public Color doInBackground() {
                return ConnectFour.winner(board);
            }

            @Override
            protected void done() {
                Color winner = null;
                try {
                    winner = get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                boolean gameOver = true;
                if( winner == HUMAN ) {
                    statusLabel.setText("Game over: Human Wins!");
                    JOptionPane.showMessageDialog ( null, "Human Wins!", "Game Over",
                            JOptionPane.INFORMATION_MESSAGE );
                } else if( winner == COMPUTER ) {
                    statusLabel.setText("Game over: Computer Wins!");
                    JOptionPane.showMessageDialog ( null, "Computer Wins!", "Game Over",
                            JOptionPane.INFORMATION_MESSAGE );
                } else if (ConnectFour.full(board)) {
                    statusLabel.setText("Game over: Draw");
                    JOptionPane.showMessageDialog ( null, "Draw Game!", "Game Over",
                            JOptionPane.INFORMATION_MESSAGE );
                } else {
                    gameOver = false;
                    updateStatusLabel();
                }

                if(gameOver) System.exit(0);
                else if(currentPlayer == COMPUTER) computerTurn();
            }
        };
        worker.execute();
    }

    /** Update label under board to say whose turn it is. */
    private void updateStatusLabel() {
        if(currentPlayer == HUMAN) {
            statusLabel.setText("Human player's turn");
        } else if(currentPlayer == COMPUTER) {
            statusLabel.setText("Computer player's turn");
        } else {
            // shouldn't happen, but just in case...
            statusLabel.setText("UNKNOWN STATUS");
        }
    }

    /**
     * Constructor for the GUI.
     */
    private ConnectFourGUI(Color[][] board, Color player, int depth) {
        this.board = board;
        this.currentPlayer = player;
        this.depth = depth;
        boardFrame = new JFrame();
        boardFrame.setTitle("Connect Four");

        boardPanel = new BoardPanel();
        boardPanel.setPreferredSize( new Dimension(700, 600) );
        boardPanel.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                int x = e.getX();
                int y = e.getY();
                doMouseClick(x, y);
            }
        });

        JPanel statusPanel = new JPanel();
        statusLabel = new JLabel();
        statusPanel.add(statusLabel);
        updateStatusLabel();

        boardFrame.add(boardPanel, BorderLayout.CENTER);
        boardFrame.add(statusPanel, BorderLayout.PAGE_END);

        boardFrame.pack();
        boardFrame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        boardFrame.setLocationRelativeTo ( null );
        //boardFrame.setResizable ( false );
        boardFrame.setVisible ( true );
    }

    /** Kick off the connect 4 game */
    private void startGame() {
        // Not likely that we were handed a non-empty board, but check anyway
        checkForWin();
    }

}
