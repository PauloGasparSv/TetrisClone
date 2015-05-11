import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;

public class Game extends GameState{
	public final static int LBLOCK = 0, ILBLOCK =1, SWIGBLOCK = 2, ISWIGBLOCK =3, TBLOCK = 4, SQUAREBLOCK = 5, LINEBLOCK = 6;

	int [][] blocks; 
	Bloco block;
	boolean rightKey,leftKey,turnKey;
	BufferedImage[] blockImg;
	BufferedImage[] nextBlockImg;
	BufferedImage background;

	long timerR, timerL, timerY;

	public Game(GameStateManager gsm, Loader loader){
		super(gsm,loader);
		init(loader);
	}

	public void init(Loader l){
		blocks = new int[20][10];
		block = new Bloco(LBLOCK);

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

		rightKey = leftKey = turnKey = false;
		timerR = timerL = timerY = 0;


	}	

	public void update(double delta){
		if(rightKey){
			if(block.canMove(1,blocks))
				block.move(1);
		}
		else if(leftKey){
			if(block.canMove(-1,blocks))
				block.move(-1);
		}

	}

	public void draw(Graphics2D g){
		g.clearRect(0,0,Panel.WIDTH+20,Panel.HEIGHT+20);
		g.drawImage(background,0,0,null);

		for(int row = 0; row < 20; row ++){
			for(int col = 0; col < 10; col ++){
				if(blocks[row][col] != -1){
					System.out.println("DRAW");
				}
			}
		}

		for(int i = 0; i < 4; i ++)
			g.drawImage(blockImg[block.getType()],100+block.getPos()[i].x*25,50+block.getPos()[i].y*25,null);

	}

	public void keyPressed(int k){
		if(k == KeyEvent.VK_RIGHT){
			if(!rightKey){
				rightKey = true;
				if(block.canMove(1,blocks))
					block.move(1);
			}
		}
		else if(k == KeyEvent.VK_LEFT){
			if(!leftKey){
				leftKey = true;	
				if(block.canMove(-1,blocks))
					block.move(-1);
			}
		}
	}

	public void keyReleased(int k){
		if(k == KeyEvent.VK_RIGHT)
			rightKey = false;
		if(k == KeyEvent.VK_LEFT)
			leftKey = false;
	}

}