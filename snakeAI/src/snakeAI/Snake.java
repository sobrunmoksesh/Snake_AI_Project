package snakeAI;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;

@SuppressWarnings("serial")
public class Snake extends JFrame {

    /* Some properties. */
	private final static int colCount = 30;
	private final static int TILE_SIZE = 20;
    private final static int BOARD_WIDTH = TILE_SIZE * colCount; // Tile size * number of columns
    private final static int BOARD_HEIGHT = TILE_SIZE * colCount;
    private final static int ALL_TILES = colCount*colCount; // Total number of tiles
    private final int DELAY = 100;
    private int scoreCount = 0;

    /* The coordinates of the snake. */
    private static int[] xCoor = new int[ALL_TILES];
    private static int[] yCoor = new int[ALL_TILES];

    /* Coordinates for apple. */
    private int apple_x, apple_y;

    /* Pressed Key. */
    int pressedKey = KeyEvent.VK_DOWN;
    int oldPressedKey;
    private int snakeSize = 5;
    private boolean inGame = true;

    
    //Keep track of previous characters
    static char previous;
    
    /*
     * Knowing the possible action of the snake
     * Determining the next movement of the snake
     */
    public static char input(String percept) {
   
        SecureRandom num = new SecureRandom();
        char character = percept.charAt(num.nextInt(4));
        System.out.println(character);
        return character;
        
    }
    
    /* 
     * Base on next movement of snake 
     * Establish some rules to make some decision
     * and calculate the next best movement
     */
    public static int[] rules(char character) {
      	 
    	
    	int[] movement = new int[2];
    	//Randomize
    	SecureRandom num = new SecureRandom();
    	
    	/*
         * if random variable generate s it will move to the left
         * however if previous position was w, s will not be able
         * to turn to w.
        */
    	if(character=='s' &&  yCoor[0] > BOARD_HEIGHT) {// Down
        	String re = "ad";
        	char char1 = re.charAt(num.nextInt(2));
        	if(char1=='a'){
        		///xCoor[0] -=  TILE_SIZE;
        		movement[0]-=TILE_SIZE;
        		movement[1]=0;
        		previous=char1;
        	}else if(char1=='d') {
        		//xCoor[0] += TILE_SIZE;
        		movement[0]=TILE_SIZE;
        		movement[1]=0;
        		previous=char1;
        	}
        }else if(character=='s') {
        	if(previous=='w'){
        		String re = "ad";
	        	char char1 = re.charAt(num.nextInt(2));
	        	if(char1=='a'){
	        		//xCoor[0] -=  TILE_SIZE;
	        		movement[0]-=TILE_SIZE;
	        		movement[1]=0;
	        		previous=char1;
	        	}else if(char1=='d') {
	        		//xCoor[0] += TILE_SIZE;
	        		movement[0]=TILE_SIZE;
	        		movement[1]=0;
	        		previous=char1;
	        	}
        	}else {
        		//yCoor[0] +=TILE_SIZE;
        		movement[0]=0;
        		movement[1]=TILE_SIZE;
        		previous=character;
        	}
        	
        }
        
    	/*
         * if random variable generate w it will move to the left
         * however if previous position was s, w will not be able
         * to turn to s.
        */
    	//Up
        if(character=='w' &&  yCoor[0] < 0) {
        	String re = "ad";
        	char char1 = re.charAt(num.nextInt(2));
        	if(char1=='a'){
        		//xCoor[0] -=  TILE_SIZE;
        		movement[0]-=TILE_SIZE;
        		movement[1]=0;
        		previous=char1;
        	}else if(char1=='d') {
        		//xCoor[0] += TILE_SIZE;
        		movement[0]=TILE_SIZE;
        		movement[1]=0;
        		previous=char1;
        	}
        }else if(character=='w') {
        	if(previous=='s'){
        		String re = "ad";
	        	char char1 = re.charAt(num.nextInt(2));
	        	if(char1=='a'){
	        		//xCoor[0] -=  TILE_SIZE;
	        		movement[0]-=TILE_SIZE;
	        		movement[1]=0;
	        		previous=char1;
	        	}else if(char1=='d') {
	        		//xCoor[0] += TILE_SIZE;
	        		movement[0]=TILE_SIZE;
	        		movement[1]=0;
	        		previous=char1;
	        	}
        	}else {
        		//yCoor[0] -=TILE_SIZE;
        		movement[0]=0;
        		movement[1]=-TILE_SIZE;
        		previous=character;
        	}
        }
        
        /*
         * if random variable generate a it will move to the left
         * however if previous position was d, a will not be able
         * to turn to d.
        */
        //Left
        if(character=='a' &&  xCoor[0] < 0) {
        	String re = "ws";
        	char char1 = re.charAt(num.nextInt(2));
        	if(char1=='w'){
        		//yCoor[0] -= TILE_SIZE;
        		movement[0]=0;
        		movement[1]-=TILE_SIZE;
        		previous=char1;
        	}else if(char1=='s') {
        		//yCoor[0] +=TILE_SIZE;
        		movement[0]=0;
        		movement[1]=TILE_SIZE;
        		previous=char1;
        	}
        }else if(character=='a') {
        	if(previous=='d'){
        		String re = "ws";
	        	char char1 = re.charAt(num.nextInt(2));
	        	if(char1=='w'){
	        		//yCoor[0] -=TILE_SIZE;
	        		movement[0]=0;
	        		movement[1]-=TILE_SIZE;
	        		previous=char1;
	        	}else if(char1=='s') {
	        		//yCoor[0] +=TILE_SIZE;
	        		movement[0]=0;
	        		movement[1]=TILE_SIZE;
	        		previous=char1;
	        	}
        	}else {
        		//xCoor[0] -=TILE_SIZE;
        		movement[0]=-TILE_SIZE;
        		movement[1]=0;
        		previous=character;
        	}
        }
        
        /*if random variable generate d it will move to the right
         * however if previous position was a, d will not be able
         * to turn to a.
         */
        //Right
        if(character=='d' &&  xCoor[0] > BOARD_WIDTH) {
        	String re = "ws";
        	char char1 = re.charAt(num.nextInt(2));
        	if(char1=='w'){
        		//yCoor[0] -= TILE_SIZE;
        		movement[0]=0;
        		movement[1]-=TILE_SIZE;
        		previous=char1;
        	}else if(char1=='s') {
        		//yCoor[0] +=TILE_SIZE;
        		movement[0]=0;
        		movement[1]=TILE_SIZE;
        		previous=char1;
        	}
        }else if(character=='d') {
        	if(previous=='a'){
        		String re = "ws";
	        	char char1 = re.charAt(num.nextInt(2));
	        	if(char1=='w'){
	        		//yCoor[0] -=TILE_SIZE;
	        		movement[0]=0;
	        		movement[1]-=TILE_SIZE;
	        		previous=char1;
	        	}else if(char1=='s') {
	        		//yCoor[0] +=TILE_SIZE;
	        		movement[0]=0;
	        		movement[1]=TILE_SIZE;
	        		previous=char1;
	        	}
        	}else {
        		//xCoor[0] +=TILE_SIZE;
        		movement[0]=TILE_SIZE;
        		movement[1]=0;
        		previous=character;
        	}	
        }
        
        return movement;
    }
    
    //Actions
    public static void action(int[] movement) {
		
		xCoor[0] += movement[0];
		yCoor[0] += movement[1];
	}
    
    public static void simpleReflexAgent(String percept) {
    	
    	char character = input(percept);
    	int[] actions = rules(character); 
    	action(actions);
    }

    public class Board extends JPanel implements ActionListener {
	    
	    public Board(){
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
		              g.setColor(Color.yellow);
		            } else {
		              g.setColor(Color.green);}
		
		            g.fillRect(xCoor[i], yCoor[i], TILE_SIZE, TILE_SIZE);
		        }
		
		        
		        /* Draw score */
		    	g.setFont(new Font("Sans serif", Font.BOLD, 20));
		    	g.drawString(getScore(), 400, 20);
		   	 
	        }
	        else {
	        	gameOver(g);
	        }
	    }

	    public void actionPerformed(ActionEvent e) {
	
	        checkTile();
	        moveSnakeCoor();
	        repaint();
	
	    }
	    
	    private void checkTile(){
	    	
	   
	    	/* Check for apples. */
	    	if ((xCoor[0] == apple_x) && (yCoor[0] == apple_y)) {
	    		scoreCount++;
	      		if (scoreCount == 1) {
	      			inGame = false;
	      	
	      		}
	    	}
	      	
	        
	    }
	 
	    /** Generates random coordinates for apple. */
	    private void spawnAppleCoor() {
	        int r = (int) (Math.random() * Math.sqrt(ALL_TILES) - 1);
	     	//int r = 20;
	         apple_x = ((r * TILE_SIZE));

	         r = (int) (Math.random() * Math.sqrt(ALL_TILES) - 1);
	         apple_y = ((r * TILE_SIZE));
	     }

	    
		/** Simply prints a gameOver-message to screen when called. */
		private void gameOver(Graphics g) {
	    	g.setColor(Color.white);
	    	g.setFont(new Font("Sans serif", Font.BOLD, 15));
	    	g.drawString(("Game Over! You ate " + (getScore()) + " apple!"),
	         	BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
//	    	g.drawString("Press space to restart",
//	         	BOARD_WIDTH / 2 + 10, BOARD_HEIGHT / 2 + 30);

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
	
	        String moves = "wsad";
	        simpleReflexAgent(moves);
	        
	 
	        
	    }
	    
	    private String getScore(){
	    	return "" + scoreCount;
		}
  }

  public Snake(){
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(BOARD_WIDTH+200, BOARD_HEIGHT+200);
	setResizable(false);
	setLocation(50, 50);
	
	Board b = new Board();
	//addKeyListener(b);
	add(b);
	setVisible(true);
	
	Timer t = new Timer(DELAY, b);
	t.start();
  }

  @SuppressWarnings("unused")
  public static void main (String[] args) {
    Snake s = new Snake();
  }    
  
}