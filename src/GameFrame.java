// import javax.swing.*;
import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame{
    public GameFrame(){

        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        //this.setSize(600,600);
        this.add(new GamePanel(), BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
