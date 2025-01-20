import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int boardwidth = 600;
            int boardheight = boardwidth;
 
            JFrame frame = new JFrame("Snake");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            snakegame snakegamee = new snakegame(boardwidth, boardheight);
            frame.add(snakegamee);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            snakegamee.repaint();
            snakegamee.requestFocus();
        });
    }
}
