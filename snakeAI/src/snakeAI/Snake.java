package snakeAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Snake extends JFrame {

    /* Some properties. */
    private final int BOARD_WIDTH = 2 * 300; // Tile size * number of columns
    private final int BOARD_HEIGHT = 2 * 300;
    private final int TILE_SIZE = 5;
    private final int ALL_TILES = 90000; // Total number of tiles
    private final int DELAY = 100;

    /* The coordinates of the snake. */
    private int[] xCoor = new int[ALL_TILES];
    private int[] yCoor = new int[ALL_TILES];

    /* Coordinates for apple. */
    private int apple_x, apple_y;
    private int[] apple = {apple_x, apple_y};
    
    /*Apple counter*/
    public int appleCount = 0;
    /* Pressed Key. */
    int pressedKey = KeyEvent.VK_DOWN;
    int oldPressedKey;
    private int snakeSize = 5;
    private boolean inGame = true;
    
   //Randomize functionalities
    Random rand = new Random();
    
    public class Board extends JPanel implements KeyListener, ActionListener {
    Board(){
        setBackground(Color.black);

        // Set snake starting coordinates. 
        for(int i = 0; i < snakeSize; i++){
          yCoor[i] = 140 - (i * 30);
          xCoor[i] = 140;
        }

        spawnAppleCoor();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {

        /* Draw apple. */
        g.setColor(Color.red);
        g.fillRect(apple_x, apple_y, TILE_SIZE, TILE_SIZE);

        /* Draw snake. */
        for (int i = 0; i < snakeSize; i++) {

            if (i == 0) { 
              g.setColor(Color.yellow); // Snakes head yellow
            } else {
              g.setColor(Color.green);}

            g.fillRect(xCoor[i], yCoor[i], TILE_SIZE, TILE_SIZE);
        }

        /* Draw score */
        g.setFont(new Font("Sans serif", Font.BOLD, 20));
        g.drawString(getScore(), 550, 30);

        } else {
          gameOver(g);
        }
    }

    public void actionPerformed(ActionEvent e) {

        checkTile();
        moveSnakeCoor();
        repaint();

    }

    /* Saves pressedKeyCode to pressedKey. */
    public void keyPressed(KeyEvent e) {
        oldPressedKey = pressedKey;
        pressedKey = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    private void checkTile(){
        /* Check if outside of wall. */
//        if ( xCoor[0] > BOARD_WIDTH || xCoor[0] < 0 || yCoor[0] > BOARD_HEIGHT || yCoor[0] < 0 ) {
//             //inGame = false;
//           }
    	//Right
    	 if ( xCoor[0] > BOARD_WIDTH) {
    		 pressedKey = KeyEvent.VK_LEFT;
    	 }
    	 //Left
    	 if ( xCoor[0] < 0) {
    		 pressedKey = KeyEvent.VK_RIGHT;
    	 }
    	//Down
    	if (yCoor[0] > BOARD_HEIGHT) {
    		pressedKey = KeyEvent.VK_UP;
    	}
    	//Top
    	if (yCoor[0] < 0 ) {
    		pressedKey = KeyEvent.VK_DOWN;
    	}

        /* Check for collisions. */
//        for(int i = 1; i < xCoor.length; i++){
//          if (xCoor[0] == xCoor[i] && yCoor[0] == yCoor[i]){
//              inGame = false;
//          }
//        }

        /* Check for apples. */
        if ((xCoor[0] == apple_x) && (yCoor[0] == apple_y)) {
//          snakeSize++;
//          spawnAppleCoor();
        	appleCount++;
        	if(appleCount == 1) {
        		inGame = false;
        	}
        }
    }

    /** Generates random coordinates for apple. */
    private void spawnAppleCoor() {
       int r = (int) (Math.random() * Math.sqrt(ALL_TILES) - 1);
    	//int r = 50;
        //Divide by 3 because TILE_SIZE is 5 but 2 in 2*300
        apple_x = ((r * TILE_SIZE/3));

        r = (int) (Math.random() * Math.sqrt(ALL_TILES) - 1);
        apple_y = ((r * TILE_SIZE/3));
    }

    /** Simply prints a gameOver-message to screen when called. */
    private void gameOver(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Sans serif", Font.BOLD, 20));
        g.drawString(("Game Over! You ate " + appleCount + " apple!"),
             BOARD_WIDTH / 4, BOARD_HEIGHT / 2);
        g.drawString("Press space to restart",
             BOARD_WIDTH / 4 + 20, BOARD_HEIGHT / 2 + 30);

        /* Restart game if space is pressed. */
        if (pressedKey == KeyEvent.VK_SPACE){
          inGame = true;
          pressedKey = KeyEvent.VK_DOWN;
          setVisible(false); 
          dispose(); 
          Snake s = new Snake();
        }
    }

    private void moveSnakeCoor(){

        /* Move coordinates up one in the matrix.*/
        for (int i = snakeSize; i > 0; i--) {
          xCoor[i] = xCoor[(i - 1)];
          yCoor[i] = yCoor[(i - 1)];
        }
       
        if ((xCoor[0] != apple_x) && (yCoor[0] != apple_y)) {
        	
	        if(rand.nextInt(4) == 0){
	        	xCoor[0] += TILE_SIZE;
	        	yCoor[0] += TILE_SIZE;
	        	//pressedKey = KeyEvent.VK_RIGHT;
	        }
	        if(rand.nextInt(4) == 1){
	        	xCoor[0] -= TILE_SIZE;
	        	yCoor[0] -= TILE_SIZE;
	        	//pressedKey = KeyEvent.VK_LEFT;
	        }
	        if(rand.nextInt(4) == 2){
	        	xCoor[0] += TILE_SIZE*2;
	        	yCoor[0] -= TILE_SIZE*2;
	        	//pressedKey = KeyEvent.VK_UP;
	        }
	        if(rand.nextInt(4) == 3){
	        	xCoor[0] -= TILE_SIZE*2;
	        	yCoor[0] += TILE_SIZE*2;
	        	//pressedKey = KeyEvent.VK_DOWN;
	        }
        }
        /* Depending on what key was pressed, change coordinates
         * accordingly. */
        switch (pressedKey) {
        case KeyEvent.VK_DOWN:
          yCoor[0] += TILE_SIZE;
          break;
        case KeyEvent.VK_UP:
          yCoor[0] -= TILE_SIZE;
          break;
        case KeyEvent.VK_LEFT:
          xCoor[0] -= TILE_SIZE;
          break;
        case KeyEvent.VK_RIGHT:
          xCoor[0] += TILE_SIZE;
          break;
        }
    }

    private String getScore(){
        return "" + appleCount;
    }
  }

  public Snake(){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(BOARD_WIDTH, BOARD_HEIGHT);
    setResizable(false);
    setLocation(50, 50);

    Board b = new Board();
    addKeyListener(b);
    add(b);
    setVisible(true);

    Timer t = new Timer(DELAY, b);
    t.start();
  }
    
}
