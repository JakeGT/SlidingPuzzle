import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class BoardGUI {
    private final JFrame frame = new JFrame("Board");
    private final PuzzleBoard board;
    private final ArrayList<ArrayList<JButton>> jButtons = new ArrayList<>();
    BoardGUI(PuzzleBoard board) {
        this.board = board;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(4, 4);
        frame.setLayout(grid);
        frame.setSize(600,600);
        createTiles();
        frame.setVisible(true);
    }

    private void createTiles() {
        for (int y = 0; y<4; y++) {
            jButtons.add(new ArrayList<>());
            for (int x = 0; x < 4; x++) {
                JButton button = new JButton();
                int finalX = x;
                int finalY = y;
                button.addActionListener(e -> movePiece(finalX, finalY));
                String tile = board.getTile(x, y);
                if (!Objects.equals(tile, "0")) {
                    button.setText(board.getTile(x, y));
                }
                button.setFont(new Font("Arial", Font.PLAIN, 40));
                jButtons.get(y).add(button);
                frame.getContentPane().add(button);
            }
        }
    }

    private void movePiece(int x, int y) {
        int[] spacePos = board.getPos();
        try {
            if (board.movePiece(x, y)) {
                jButtons.get(spacePos[1]).get(spacePos[0]).setText(board.getTile(spacePos[0], spacePos[1]));
                jButtons.get(y).get(x).setText("");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
