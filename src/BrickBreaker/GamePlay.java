package BrickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//import java.util.Timer;



public class GamePlay extends JPanel implements KeyListener, ActionListener {
    Image img;

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

    private MapGenerator ourMap;

   /** constructor */
    public GamePlay(){
        img = Toolkit.getDefaultToolkit().getImage("/Users/xinyi.babs/github/Brick Breaker/src/BrickBreaker/background2.png");

        ourMap = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics g){

        /*
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);
        */

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, 0,0,690,558,this);


        // draw map
        ourMap.draw((Graphics2D)g);

        // borders
        g.setColor(new Color(140, 203, 203));
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // scores
        g.setColor(new Color(75, 47, 11));
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score,590,300);

        // paddle
        g.setColor(new Color(172, 132, 94));
        g.fillRect(playX, 550,100,8);

        // ball
        g.setColor(new Color(42, 65, 1));
        g.fillOval(ballPoxX, ballPosY,20,20);

        if(totalBricks <= 0){
            play = false;
            ballPoxX = 0;
            ballPosY = 0;
            g.setColor(new Color(0,0,0,200));
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Victory!", 280, 300 );
            g.setFont(new Font("serif",Font.BOLD,18));

            g.setColor(Color.white);
            g.drawString("This Game is Developed as Birthday Gift for Xinyi Bai's friend",100,530);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart.", 200, 350 );

        }


        if(ballPosY > 570){
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(new Color(0,0,0,200));
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Game Over, Scores : "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter To Restart",230,350);
            g.setColor(Color.white);
            g.drawString("This Game is Developed as Birthday Gift for Xinyi Bai's friend",80,530);
        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){
            if(new Rectangle(ballPoxX,ballPosY,20,20).intersects(new Rectangle(playX,550,100,8))){
                ballDirY = -ballDirY;
            }

            outerloop:for (int i = 0;i<ourMap.map.length;i++){
                for (int j=0;j<ourMap.map[0].length;j++){
                    if(ourMap.map[i][j] > 0){
                        int brickX = j * ourMap.brickWidth + 80;
                        int brickY = i * ourMap.brickHeight +50;
                        int brickWidth = ourMap.brickWidth;
                        int brickHeight = ourMap.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPoxX,ballPosY,20,20);
                        Rectangle brickRec = rect;

                        if(ballRect.intersects(brickRec)){
                            ourMap.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 5;
                            if(ballPoxX + 19 <= brickRec.x || ballPoxX +1 >= brickRec.x + brickRec.width ){
                                ballDirX = - ballDirX;

                            }else{
                                ballDirY = -ballDirY;
                            }
                            break outerloop;
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
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playX >= 600){
                playX = 600;
            }
            else{
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playX < 10){
                playX = 10;
            }
            else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPoxX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                playX = 310;
                score = 0;
                totalBricks = 21;
                ourMap = new MapGenerator(3,7);

                repaint();

            }
        }
    }

    public void moveRight(){
        play = true;
        playX = playX + 20;
    }

    public void moveLeft(){
        play = true;
        playX = playX - 20;
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
