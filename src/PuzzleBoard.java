import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class PuzzleBoard {
    private final int[][] board = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}};
    // Piece 0 represents the space (empty) tile.
    private int spacePosX = 3;
    private int spacePosY = 3;

    /**
     * When printing the object, simply print the board array.
     * @return The tiles in the board padded with 3 spaces and organised into rows and columns.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int[] row : board) {
            for (int pos : row) {
                string.append(String.format("%1$" + 3 + "s", pos));
            }
            string.append("\n");
        }
        return string.toString();
    }


    /**
     * This simply returns the tile in the specific position supplied.
     * @param x X-coord of the tile
     * @param y Y-coord of the tile
     * @return The tile number
     */
    public String getTile(int x, int y) {
        return Integer.toString(board[y][x]);
    }


    /**
     * This use a helper method to handle generating random numbers from 0 to the max parameter.
     *
     * @param max The max value of the number to generate
     * @return The random number
     */
    private int randomRange(int max) {
        return (int) ((Math.random()) * max);
    }


    /**
     * This shuffles the board.
     *
     * @param moves How many moves to shuffle the board.
     * @throws Exception When the tile to be moved is not a valid tile
     */
    public void shuffleBoard(int moves) throws Exception {
        for (int i = 0; i < moves; i++) {
            ArrayList<Character> possibleMoves = possibleMoves();
            Character move = possibleMoves.get(randomRange(possibleMoves.size()));
            switch (move) {
                case 'r' -> movePiece(spacePosX + 1, spacePosY);
                case 'l' -> movePiece(spacePosX - 1, spacePosY);
                case 'd' -> movePiece(spacePosX, spacePosY + 1);
                case 'u' -> movePiece(spacePosX, spacePosY - 1);
            }
        }
    }

    /**
     * This returns all the possible moves relative to the space tile.
     * @return List of possible moves
     */
    private ArrayList<Character> possibleMoves() {
        ArrayList<Character> possibleMoves = new ArrayList<>(Arrays.asList('u', 'd', 'l', 'r'));
        if (spacePosX == 3) {possibleMoves.remove((Character) 'r');}
        if (spacePosX == 0) {possibleMoves.remove((Character) 'l');}
        if (spacePosY == 3) {possibleMoves.remove((Character) 'd');}
        if (spacePosY == 0) {possibleMoves.remove((Character) 'u');}
        return possibleMoves;
    }

    /**
     * This will handle moves where multiple tiles are moved at once - a line of tiles over by one piece.
     *
     * @param posX X-coord of the tile
     * @param posY Y-coord of the tile
     * @throws Exception When the tile position is not valid
     */
    private void longSlide(int posX, int posY) throws Exception {
        if (spacePosX - posX == 0) { // If line of tiles is in the column direction
            if (spacePosY - posY > 0) { // to the left side
                for (int i = 0; i < spacePosY - posY; i++) {
                    movePiece(spacePosX, spacePosY - 1);
                }
            } else { // to the right
                for (int i = 0; i < posY - spacePosY; i++) {
                    movePiece(spacePosX, spacePosY + 1);
                }
            }
        } else { // If line of tiles is in the row direction
            if (spacePosX - posX > 0) { //above
                for (int i = 0; i < spacePosX - posX; i++) {
                    movePiece(spacePosX - 1, spacePosY);
                }
            } else { // below
                for (int i = 0; i < posX - spacePosX; i++) {
                    movePiece(spacePosX + 1, spacePosY);
                }
            }
        }
        movePiece(posX, posY);
    }


    /**
     * This is the method called to move a piece.
     * Contains the code required to move a piece by one space. If trying to move a line of pieces, it will call
     * another method to handle.
     * @param posX X-coord of the tile
     * @param posY Y-coord of the tile
     * @return // If (a) piece(s) has been moved.
     * @throws Exception If the position supplied is invalid
     */
    public boolean movePiece(int posX, int posY) throws Exception {
        if (posX > 3 || posY > 3) {
            throw new Exception("Position Invalid");
        }
        int relPosX = abs(posX-spacePosX);
        int relPosY = abs(posY-spacePosY);

        if ((relPosX + relPosY) == 1) {
            board[spacePosY][spacePosX] = board[posY][posX];
            board[posY][posX] = 0;
            spacePosX = posX;
            spacePosY = posY;
            return true;
        } else if ((relPosX == 0 || relPosY == 0) && (relPosX + relPosY != 0)) {
            longSlide(posX, posY);
            return true;
        }
        else {
            return false;
        }
    }
}
