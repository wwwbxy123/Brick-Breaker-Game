package BrickBreaker;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay();
        


        obj.setBounds(10,10,700,580);
        obj.setTitle("Brick Breaker Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //obj.add(b);
        obj.add(gamePlay);

        obj.setVisible(true);

    }
}
