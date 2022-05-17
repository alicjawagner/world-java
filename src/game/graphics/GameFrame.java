package game.graphics;
import game.world.World;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame(){

        //this.add(new GamePanel());
        this.add(new World());
        this.setTitle("The world");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}