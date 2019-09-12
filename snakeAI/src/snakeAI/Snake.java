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
    private static int apple_x;
	private static int apple_y;

    /* Pressed Key. */
    int pressedKey = KeyEvent.VK_DOWN;
    private int snakeSize = 1;
    private boolean inGame = true;

    
    //Randomize directions
    public static char input(String percept) {
   
        SecureRandom rand = new SecureRandom();
        char directions = percept.charAt(rand.nextInt(4));
        //System.out.println(directions);
        return directions;
        
    }
    
    
   //Guide snake to food conditions
    public static int[] rules(char directions) {
      	 
    	//Stores xCoor and yCoor of snake
    	int[] position = new int[2];
    	
    	if(directions == 'l' && ((position[0] != apple_x) && (position[1] != apple_y))) {
  
    		//If x-coordinate of snake is less than x-coordinate of apple, add tile size till xCoor sums up to apple_x
			if ((xCoor[0] - apple_x) < 0) {
				
					position[0] += TILE_SIZE;
					position[1] = 0;
    		}
			//If x-coordinate of snake is less than x-coordinate of apple, subtract tile size till xCoor equals to apple_x
			else {
				position[0] -= TILE_SIZE;
				position[1] = 0;
			}
    	}
    	else if(directions == 'r' && ((position[0] != apple_x) && (position[1] != apple_y))) {
    		
			if ((xCoor[0] - apple_x) < 0) {
				position[0] += TILE_SIZE;
				position[1] = 0;
    		}
			else {
				position[0] -= TILE_SIZE;
				position[1] = 0;
			}
    	}
    	else if(directions == 'u' && ((position[0] != apple_x) && (position[1] != apple_y))) {
    		
    		//If y-coordinate of snake is less than y-coordinate of apple, add tile size till yCoor sums up to apple_y
			if ((yCoor[0] - apple_y) < 0) {
				position[1] += TILE_SIZE;
				position[0] = 0;
    		}
			//If y-coordinate of snake is less than y-coordinate of apple, subtract tile size till xCoor equals to apple_y
			else {
				position[1] -= TILE_SIZE;
				position[0] = 0;
			}
    	}
        else if(directions == 'd' && ((position[0] != apple_x) && (position[1] != apple_y))) {

        	if ((yCoor[0] - apple_y) < 0) {
				position[1] += TILE_SIZE;
				position[0] = 0;
    		}
			else {
				position[1] -= TILE_SIZE;
				position[0] = 0;
			}
    	}
    	
        
        return position;
    }
    
    //Actions on xCoor and yCoor
    public static void action(int[] move) {
		
		xCoor[0] += move[0];
		yCoor[0] += move[1];
	}
    
    //Agent function
    public static void goalBasedAgent(String percept) {
    	
    	char state = input(percept);
    	int[] actions = rules(state); 
    	action(actions);
    }


	public class Board extends JPanel implements ActionListener {
	    
	    public Board(){
	        setBackground(Color.black);
	
	        // Set snake starting coordinates.
	        yCoor[0] = 140; 
	        xCoor[0] = 140;
//	        for(int i = 0; i < snakeSize; i++){
//	          yCoor[i] = 140 - (i * 30); 
//	          xCoor[i] = 140;
//	        }
	        
	
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
	    		
	      			inGame = false;
	      
	    	}
	    	
	    	//Rebound against walls
	    	//Right
	    	 if ( xCoor[0] > BOARD_WIDTH) {
	    		 //Rebound left
	    		 pressedKey = KeyEvent.VK_LEFT;
	    	 }
	    	 //Left
	    	 if ( xCoor[0] < 0) {
	    		//Rebound right
	    		 pressedKey = KeyEvent.VK_RIGHT;
	    	 }
	    	//Down
	    	if (yCoor[0] > BOARD_HEIGHT) {
	    		//Rebound up
	    		pressedKey = KeyEvent.VK_UP;
	    	}
	    	//Top
	    	if (yCoor[0] < 0 ) {
	    		//Rebound down
	    		pressedKey = KeyEvent.VK_DOWN;
	    	}
	      	
	        
	    }
	 
	    /** Generates random coordinates for apple. */
	    private void spawnAppleCoor() {
	        int r = (int) (Math.random() * Math.sqrt(ALL_TILES) - 1);
	     	//int r = 20;
	         apple_x = ((r * TILE_SIZE));

	         r = (int) (Math.random() * Math.sqrt(ALL_TILES) - 1);
	         apple_y = ((r * TILE_SIZE));
	         
	         System.out.println(apple_x);
	         System.out.println(apple_y);
	     }

	    
		/** Simply prints a gameOver-message to screen when called. */
		private void gameOver(Graphics g) {
	    	g.setColor(Color.white);
	    	g.setFont(new Font("Sans serif", Font.BOLD, 18));
	    	g.drawString(("Game Over! You ate the apple!"), BOARD_WIDTH / 4, BOARD_HEIGHT / 2);
//	    	g.drawString("Press space to restart",
//	         	BOARD_WIDTH / 2 + 10, BOARD_HEIGHT / 2 + 30);

	    	/* Restart game if space is pressed. */
//	    	if (pressedKey == KeyEvent.VK_SPACE){
//	      	inGame = true;
//	      	pressedKey = KeyEvent.VK_DOWN;
//	      	setVisible(false);
//	      	dispose();
//	      	Snake s = new Snake();
//	    	}
		}

	    
	    private void moveSnakeCoor(){
	
	        /* Move coordinates up one in the matrix.*/
	        for (int i = snakeSize; i >0; i--) {
	          xCoor[i] = xCoor[(i - 1)];
	          yCoor[i] = yCoor[(i - 1)];
	        }
	
	        //Up down left right 
	        String moves = "udlr";
	        
	        //All moves
	        goalBasedAgent(moves);
	            
	       
	    }
	  
	    
	    private String getScore(){
	    	return "" + scoreCount;
		}
  }

   //Constructor
   public Snake(){
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(BOARD_WIDTH, BOARD_HEIGHT);
	setResizable(false);
	setLocation(50, 50);
	
	Board b = new Board();
	//addKeyListener(b);
	add(b);
	setVisible(true);
	
	Timer t = new Timer(DELAY, b);
	t.start();
   }
  
}