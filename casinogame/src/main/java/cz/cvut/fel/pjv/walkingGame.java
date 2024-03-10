package cz.cvut.fel.pjv;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;




public class walkingGame extends JPanel implements ActionListener, KeyListener{
    int boardHeight;
    int boardWidth;
    int tileSize = 25;
    Tile charModel;
    Tile coin;
    Random random;
    int velocityX;
    int velocityY;
    Image charImage;
    Image coinImage;
    Image backgroundImage;
    ArrayList<Tile> score = new ArrayList<Tile>();
    int newWidth = 50;
    int newHeight = 50;
    URL url;
    Clip clip;
    AudioInputStream coinAudioInputStream;

    //game logic
    Timer gameLoop; //timer for the game loop
    

    walkingGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight)); //set the size of the panel
        setBackground(Color.LIGHT_GRAY); //set the background color of the panel
        addKeyListener(this);
        setFocusable(true);

        velocityX = 0;
        velocityY = 0;

        this.charModel = new Tile(10, 10);
        score = new ArrayList<Tile>();

        this.coin = new Tile(10, 10);

        random = new Random();
        spawnCoin();

        gameLoop = new Timer(100, this);
        gameLoop.start();

        try {
            charImage = ImageIO.read(getClass().getResource("images/bombman.png"));
            charImage = charImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
            coinImage = ImageIO.read(getClass().getResource("images/coin.gif"));
            coinImage = coinImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
            backgroundImage = ImageIO.read(getClass().getResource("images/casino_pixel_bg.jpg"));
            //URL url = this.getClass().getClassLoader().getResource("sounds/coin.wav");
            //coinAudioInputStream = AudioSystem.getAudioInputStream(url);
            //clip = AudioSystem.getClip();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
        draw(g);
    }

    

    public void draw(Graphics g){
        
        //coin
        g.drawImage(coinImage, coin.x * tileSize, coin.y * tileSize, tileSize, tileSize, null);

        //character
        g.drawImage(charImage, charModel.x * tileSize, charModel.y * tileSize, tileSize, tileSize, null);
        

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + (score.size()), 10, 30);

        
    }

    public void spawnCoin(){
        coin.x = random.nextInt(boardWidth / tileSize);
        coin.y = random.nextInt(boardHeight / tileSize);
    }
    
    public boolean checkCoinCollision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){

        if (checkCoinCollision(charModel, coin)){
            score.add(new Tile(coin.x, coin.y));
            spawnCoin();

            //try {
            //    // Open an audio input stream.
            //    URL url = this.getClass().getClassLoader().getResource("sounds/win.wav");
            //    System.out.println(url);
            //    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            //    // Get a sound clip resource.
            //    Clip clip = AudioSystem.getClip();
            //    // Open audio clip and load samples from the audio input stream.
            //    clip.open(audioIn);
            //    clip.start();
            //} catch (UnsupportedAudioFileException e) {
            //    e.printStackTrace();
            //} catch (IOException e) {
            //    e.printStackTrace();
            //} catch (LineUnavailableException e) {
            //    e.printStackTrace();
            //}
        }



        charModel.x += velocityX;
        charModel.y += velocityY;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){ //if the up arrow key is pressed
            velocityX = 0;
            velocityY = -1;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){ //if the down arrow key is pressed
            velocityX = 0;
            velocityY = 1;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT){ //if the left arrow key is pressed
            velocityX = -1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT){ //if the right arrow key is pressed
            velocityX = 1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityX = 0;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

   


}
