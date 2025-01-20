import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class snakegame extends JPanel implements ActionListener,KeyListener {
    private class tile {
        int x, y;

        tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int boardheight;
    private int boardwidth;
    private int tilesize = 25;

    //snake
    protected tile snakehead;
    ArrayList<tile> snakebody;

    
    //food
    protected tile food;

    //random
    Random random;

    //game logic
    Timer gameloop;
    int velocityX;
    int velocityY;
    Boolean gameover=false;


    snakegame(int boardwidth, int boardheight) {
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakehead = new tile(5, 5);
        snakebody=new ArrayList<tile>();

        food=new tile(10, 10);

        random=new Random();
        PlaceFood();

        velocityX=0;
        velocityY=0;

        gameloop=new Timer(100,this);
        gameloop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // System.out.println("paintComponent called");
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // System.out.println("Drawing rectangle at: " + (snakehead.x * tilesize) + ", " + (snakehead.y * tilesize));

        //grid lines
        // for(int i=0;i<boardwidth/tilesize;i++){
        //     g.drawLine(i*tilesize, 0, i*tilesize, boardheight);
        //     g.drawLine(0, i*tilesize, boardwidth, i*tilesize );
        // }

        //food
        g.setColor(Color.red);
        // g.fillRect(food.x*tilesize, food.y*tilesize, tilesize, tilesize);
        g.fill3DRect(food.x*tilesize, food.y*tilesize, tilesize, tilesize,true);

        //snake head
        g.setColor(Color.green);
        // g.fillRect(snakehead.x * tilesize, snakehead.y * tilesize, tilesize, tilesize);
        g.fill3DRect(snakehead.x * tilesize, snakehead.y * tilesize, tilesize, tilesize,true);

        //snakebody 
        for(int i=0;i<snakebody.size();i++){
            tile snakepart=snakebody.get(i);
            // g.fillRect(snakepart.x*tilesize, snakepart.y*tilesize, tilesize, tilesize);
            g.fill3DRect(snakepart.x*tilesize, snakepart.y*tilesize, tilesize, tilesize,true);
        }

        //Score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameover){
            g.setColor(Color.red);
            g.drawString("Game Over: "+String.valueOf(snakebody.size()), tilesize-16, tilesize);
        }else{
            g.drawString("Score: "+ String.valueOf(snakebody.size()), tilesize-16, tilesize);
        }
    }

    public void PlaceFood(){
        food.x=random.nextInt(boardwidth/tilesize);//600/25=24 x will be a random number from 0 to 24
        food.y=random.nextInt(boardheight/tilesize);//600/25=24 y will be a random number from 0 to 24
    }

    public boolean collision(tile tile1, tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }

    public void move(){
       //eat food
       if(collision(snakehead, food)){
        snakebody.add(new tile(food.x, food.y));
        PlaceFood();
       }

       //snake body
       for(int i=snakebody.size()-1;i>=0;i--){
        tile snakepart=snakebody.get(i);
        if(i==0){//first member of snake body(one after tbe head)
           snakepart.x=snakehead.x;
           snakepart.y=snakehead.y;
        }else{
            tile prevsnakepart=snakebody.get(i-1);
            snakepart.x=prevsnakepart.x;
            snakepart.y=prevsnakepart.y;
        }
       }


        //snakehead
        snakehead.x+=velocityX;
        snakehead.y+=velocityY;

        //game over condition
        //hits its own body
        for(int i=0;i<snakebody.size();i++){
            tile snakepart=snakebody.get(i);
            if(collision(snakehead, snakepart)){
                gameover=true;
            }
        }

        //hits the wall
        if(snakehead.x*tilesize<0 || snakehead.x*tilesize>boardwidth || snakehead.y*tilesize<0 || snakehead.y*tilesize >boardheight){
            gameover=true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameover){
            gameloop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
        velocityX=0;
        velocityY=-1;
       }else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
        velocityX=0;
        velocityY=1;
       }else if (e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1) {
        velocityX=1;
        velocityY=0;
       }else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
        velocityX=-1;
        velocityY=0;
       }
    }
 
    // NOT NEEDED
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
