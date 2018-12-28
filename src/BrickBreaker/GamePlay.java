package BrickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playX = 310;

    /** ball */
    private int ballPoxX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private MapGenerator Map;

   /** constructor */
    public GamePlay(){
        Map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        // draw map
        Map.draw((Graphics2D)g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playX, 550,100,8);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballPoxX, ballPosY,20,20);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){
            if(new Rectangle(ballPoxX,ballPosY,20,20).intersects(new Rectangle(playX,550,100,8))){
                ballDirY = -1 * ballDirX;
            }

            A: for(int i = 0; i < Map.map.length; i++){
                for(int j = 0; i < Map.map[0].length; j++){
                    if(Map.map[i][j] > 0){
                        int brickX = j * Map.brickWidth + 80;
                        int brickY = i * Map.brickHeight + 50;
                        int brickWidth = Map.brickWidth;
                        int brickheight = Map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickheight);
                        Rectangle ballRect = new Rectangle(ballPoxX,ballPosY,20,20);
                        Rectangle brickRec = rect;

                        if(ballRect.intersects(brickRec)){
                            Map.setBrickValue(0,i,j);
                            totalBricks--;
                            score = score + 5;

                            if(ballPoxX + 19 <= brickRec.x || ballPoxX + 1 >= ballRect.x + brickRec.width){
                                ballDirX = -1 * ballDirX;
                            }
                            else{
                                ballDirY = -1 * ballDirY;
                            }

                            break A;
                        }
                    }
                }
            }

            ballPoxX = ballPoxX + ballDirX;
            ballPosY = ballPosY + ballDirY;

            // boundary
            if(ballPoxX < 0){
                ballDirX = -1 * ballDirX;
            }

            if(ballPosY < 0){
                ballDirY = -1 * ballDirY;
            }

            if(ballPoxX > 670) {
                ballDirX = -1 * ballDirX;
            }

        }
        // redraw slider and ball after keyboard click
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playX >= 600){
                playX = 600;
            }
            else{
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playX < 10){
                playX = 10;
            }
            else{
                moveLeft();
            }
        }
    }

    private void moveRight(){
        play = true;
        playX = playX + 20;
    }

    private void moveLeft(){
        play = true;
        playX = playX - 20;
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
