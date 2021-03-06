import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.MouseInfo;

public class Game extends GameState{
	public final static int LBLOCK = 0, ILBLOCK =4, SWIGBLOCK = 3, ISWIGBLOCK =6, TBLOCK = 2, SQUAREBLOCK = 5, LINEBLOCK = 1;

	private int [][] blocks; 
	private Bloco block;
	private boolean rightKey,leftKey,turnKey,pressedTurn,downKey;
	private BufferedImage[] blockImg;
	private BufferedImage[] nextBlockImg;
	private BufferedImage background;

	private	long timerR, timerL, timerY, timerD, stopTimer;
	private boolean startedTimer;

	private int bottomR, bottomL, bottomSpin;

	private int points;
	private int lines; 
	private int level;
	private int next;

	public boolean end;

	private int nextBlock;

	private Rectangle quitGame, newGame;

	public Game(GameStateManager gsm, Loader loader){
		super(gsm,loader);
		init(loader);
	}

	public void init(Loader l){
		blocks = new int[20][10];
		end = false;
		for(int row = 0; row < 20; row++)
			for(int col = 0; col < 10; col ++)
				blocks[row][col] = -1;

		blockImg = new BufferedImage[7];
		nextBlockImg = new BufferedImage[7];

		for(int i = 0; i<7; i++)
			blockImg[i] = l.image("assets/block"+i+".png");
		
		nextBlockImg[LBLOCK] = l.image("assets/Lblock.png");
		nextBlockImg[ILBLOCK] = l.image("assets/ILblock.png");
		nextBlockImg[SWIGBLOCK] = l.image("assets/Sblock.png");
		nextBlockImg[ISWIGBLOCK] = l.image("assets/ISblock.png");
		nextBlockImg[TBLOCK] = l.image("assets/Tblock.png");
		nextBlockImg[SQUAREBLOCK] = l.image("assets/Sqblock.png");
		nextBlockImg[LINEBLOCK] = l.image("assets/Lnblock.png");

		background = l.image("assets/background.png");

		rightKey = leftKey = turnKey = startedTimer = pressedTurn = false;
		timerR = timerL = timerY = timerD = stopTimer = 0;

		Random rand = new Random();
		block = new Bloco(rand.nextInt(7));
		nextBlock = rand.nextInt(7);
		
		bottomL = bottomR = bottomSpin = 0;
		points = lines = 0;
		timerY = System.nanoTime();
		level = 1;
		next = 4;

		quitGame = new Rectangle(410,350,110,30);
		newGame = new Rectangle(410,405,110,30);
	}	

	public void update(double delta){
		if(!end){
			int t = (800000000 - level*2000000) > 100000000?  (800000000 - level*2000000) :100000000; 
			if(System.nanoTime() - timerY > 800000000 - level*2000000){
				timerY = System.nanoTime();
				block.goDown(blocks);
			}

			if(rightKey){
				if(block.canMove(1,blocks) && System.nanoTime() - timerR >= 65000000){
					block.move(1);
					timerR = System.nanoTime();
					
					if(!block.canGoDown(blocks)){
						bottomR++;
						stopTimer = System.nanoTime();
					}

				}
			}
			else if(leftKey){
				if(block.canMove(-1,blocks) && System.nanoTime() - timerL >= 65000000){
					block.move(-1);
					timerL = System.nanoTime();
				}
				if(!block.canGoDown(blocks)){
						bottomL++;
						stopTimer = System.nanoTime();
				}
			}

			if(pressedTurn){
				block.turnCw(blocks);
				pressedTurn = false;
				if(!block.canGoDown(blocks)){
					bottomSpin++;
					stopTimer = System.nanoTime();
				}
			}

			if(downKey && System.nanoTime() - timerD > 40000000){
				timerD = System.nanoTime();
				block.goDown(blocks);
			}


			//CHECK END
			if(!block.canGoDown(blocks)){
				if(!startedTimer){
					stopTimer = System.nanoTime();
					startedTimer = true;
				}
				if((System.nanoTime() - stopTimer)/10 > 90000000 || bottomR > 20 || bottomL > 20 || bottomSpin > 10){
					startedTimer = false;
					stopTimer = 0;

					checkLose();
					
					if(!end){
						for(int i = 0; i < 4; i++){
							blocks[block.getPos()[i].y][block.getPos()[i].x] = block.getType();
						}
						Random rand = new Random();
						block.setBlock(nextBlock);
						nextBlock = rand.nextInt(7);
						checkPoints();
					}
				
				}
			}
			else{
				bottomL = bottomR = bottomSpin = 0;
				startedTimer = false;
				stopTimer = 0;
			}
		}
	}
	private void checkLose(){
		int counter = 0;
	
		for(int i=0;i<4;i++)
			if(block.getPos()[i].y<0)
				end = true;
		if(!end){
			for(int col = 0; col < 10 ; col ++)
				if(blocks[0][col] != -1)
					counter ++;
			if(counter>8)
				end = true;
		}
	}
	private void checkPoints(){
		int [] rows = new int [4];
		rows[0] = rows[1] = rows[2] = rows[3] = -1;
		int cRow = 0;
		for(int row = 0; row < 20; row ++){
			int counter = 0;
			for(int col = 0; col < 10; col ++){
				if(blocks[row][col] != -1)
					counter ++;
			}
			if(counter == 10){
				rows[cRow] = row;
				cRow ++;
			}
		}

		if(cRow >0){
			for(int i = 0; i<cRow; i++)
				for(int col = 0; col < 10; col ++)
					blocks [rows[i]][col] = -1;

			int rowToLook;
			boolean [] isRow = {true,true,true,true,true,true,true
				,true,true,true,true,true,true,true,true,true,
				true,true,true,true};
			int loops = 0;


			rowToLook = rows[cRow - 1] - loops;
			for(int i=0; i<10; i++){
				for(int row = rowToLook; row > 0; row --){
					int counter = 0;
					for(int col = 0; col < 10; col ++){
						if(blocks[row][col] == -1)
							counter++;
					}
					if(counter == 10){
						for(int col = 0; col < 10; col ++){
							if(blocks[row-1][col] != -1){
								blocks[row][col] = blocks[row-1][col];
								blocks[row-1][col] = -1;
							}
						}
					}
					else{
						break;
					}
				}
			}


			

			lines += cRow;
			points += cRow*cRow;
			if(points > next)
				level ++;
			next = (level*4 + (int)((level*level)/2));
		}		
	}

	public void draw(Graphics2D g){
		g.clearRect(0,0,Panel.WIDTH+20,Panel.HEIGHT+20);
		g.drawImage(background,5,5,null);

		if(!end){
			g.drawImage(nextBlockImg[nextBlock],405,55,null);
			
			g.drawString("Points: "+points,410,220);
			g.drawString("Lines: "+lines,410,250);
			g.drawString("Level: "+level,410,280);
			g.drawString("Next: "+next,410,310);

			for(int row = 0; row < 20; row ++){
				for(int col = 0; col < 10; col ++){
					if(blocks[row][col] != -1){
						g.drawImage(blockImg[blocks[row][col]], 105+col*25,55+row*25,null);
					}
				}
			}

			for(int i = 0; i < 4; i ++)
				if(block.getPos()[i].y>-1)
					g.drawImage(blockImg[block.getType()],105+block.getPos()[i].x*25,55+block.getPos()[i].y*25,null);
		}
		else{
			g.drawString("YOU LOSE BUDDY",125,255);
		}
	}

	public void keyPressed(int k){
		if(!end){
			if(k == KeyEvent.VK_RIGHT){
				if(!rightKey){
					rightKey = true;
					timerR = System.nanoTime();
					if(block.canMove(1,blocks))
						block.move(1);
				}
			}
			else if(k == KeyEvent.VK_LEFT){
				if(!leftKey){
					leftKey = true;	
					timerL = System.nanoTime();
					if(block.canMove(-1,blocks))
						block.move(-1);
				}
			}

			if(k == KeyEvent.VK_SPACE){
				if(!turnKey){
					turnKey = true;
					pressedTurn = true;
				}
			}

			if(k == KeyEvent.VK_DOWN){
				if(!downKey){
					downKey = true;
					timerD = System.nanoTime();
					block.goDown(blocks);
				}
			}
		}
		

	}

	public void keyReleased(int k){
		if(k == KeyEvent.VK_RIGHT)
			rightKey = false;
		if(k == KeyEvent.VK_LEFT)
			leftKey = false;
		if(k == KeyEvent.VK_SPACE)
			turnKey = false;
		if(k == KeyEvent.VK_DOWN)
			downKey = false;
	}

	public void mousePressed(int e){
		if(new Rectangle( (int)(MouseInfo.getPointerInfo().getLocation().x-
			gsm.frame.getLocation().x),(int)(MouseInfo.getPointerInfo().getLocation().y-
			gsm.frame.getLocation().y),4,4).intersects(newGame)){
			gsm.setState(GameStateManager.MENU);
		}
		if(new Rectangle( (int)(MouseInfo.getPointerInfo().getLocation().x-
			gsm.frame.getLocation().x),(int)(MouseInfo.getPointerInfo().getLocation().y-
			gsm.frame.getLocation().y),4,4).intersects(quitGame)){
			System.exit(0);
		}
	}

	public void mouseReleased(int e){
		
	}

}