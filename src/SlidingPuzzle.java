public class SlidingPuzzle {
    public static void main(String[] args) {
        PuzzleBoard board = new PuzzleBoard();
        try {
            board.shuffleBoard(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new BoardGUI(board);
    }
}