package src;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Chamtty
 */
public class SnakeGameFrame extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SnakeGameFrame frame = new SnakeGameFrame();
        frame.setVisible(true);
        frame.pack();
    }
    
    public SnakeGameFrame(){
        super("SnakeGame");
        this.setSize(new Dimension(300, 500));
        this.setPreferredSize(new Dimension(300, 500));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(new JPanelMain(this));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}
