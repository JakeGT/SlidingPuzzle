import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class PuzzleBoard {
    private final int[][] board = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}};
    private int spacePosX = 3;
    private int spacePosY = 3;

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

    public String getTile(int x, int y) {
        return Integer.toString(board[y][x]);
    }

    private int randomRange(int max) {
        return (int) ((Math.random()) * max);
    }

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

    private ArrayList<Character> possibleMoves() {
        ArrayList<Character> possibleMoves = new ArrayList<>(Arrays.asList('u', 'd', 'l', 'r'));
        if (spacePosX == 3) {possibleMoves.remove((Character) 'r');}
        if (spacePosX == 0) {possibleMoves.remove((Character) 'l');}
        if (spacePosY == 3) {possibleMoves.remove((Character) 'd');}
        if (spacePosY == 0) {possibleMoves.remove((Character) 'u');}
        return possibleMoves;
    }

    private void longSlide(int posX, int posY) throws Exception {
        if (spacePosX - posX == 0) {
            if (spacePosY - posY > 0) { // to the left side
                for (int i = 0; i < spacePosY - posY; i++) {
                    movePiece(spacePosX, spacePosY - 1);
                }
            } else {
                for (int i = 0; i < posY - spacePosY; i++) {
                    movePiece(spacePosX, spacePosY + 1);
                }
            }
        } else {
            if (spacePosX - posX > 0) { //above
                for (int i = 0; i < spacePosX - posX; i++) {
                    movePiece(spacePosX - 1, spacePosY);
                }
            } else {
                for (int i = 0; i < posX - spacePosX; i++) {
                    movePiece(spacePosX + 1, spacePosY);
                }
            }
        }
        movePiece(posX, posY);
    }

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
