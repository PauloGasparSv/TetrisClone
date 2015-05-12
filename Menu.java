import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Color;

public class Menu extends GameState{
	private BufferedImage background;
	private int mouseX,mouseY;
	private Rectangle newGame,credits;
	private boolean mousePress, mouseClick, showCredits;

	public Menu(GameStateManager gsm, Loader loader){
		super(gsm,loader);
		init(loader);
	}

	public void init(Loader l){
		background = l.image("assets/menu.png");
		mouseX = mouseY = 0;
		newGame = new Rectangle(105,205,350,100);
		credits = new Rectangle(105,356,350,100);

	}

	public void update(double delta){
		mouseX = (int)(MouseInfo.getPointerInfo().getLocation().x-
			gsm.frame.getLocation().x);
		mouseY = (int)(MouseInfo.getPointerInfo().getLocation().y-
			gsm.frame.getLocation().y);

		if(mouseClick){
			if(new Rectangle(mouseX,mouseY,4,4).intersects(newGame)){
				gsm.setState(GameStateManager.GAME);
			}
			if(new Rectangle(mouseX,mouseY,4,4).intersects(credits)){
				showCredits = true;
			}
			mouseClick = false;
		}

	}

	public void draw(Graphics2D g){
		g.clearRect(0,0,Panel.WIDTH+20,Panel.HEIGHT+20);
		g.drawImage(background,5,5,Panel.WIDTH,Panel.HEIGHT,null);

		if(showCredits){
			g.fillRect(75,280,435,70);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(78,283,429,64);
			g.setColor(Color.BLACK);
			g.drawString("Made by Paulo Gaspar Sena do Vale", 85,320);

		}

	}
	
	public void mousePressed(int e){
		if(e == MouseEvent.BUTTON1){
				if(!mouseClick && !mousePress)
					mouseClick = true;
			mousePress = true;
		}
		if(showCredits){
			showCredits = false;
		}
	}

	public void mouseReleased(int e){
		if(e == MouseEvent.BUTTON1){
			mousePress = false;
		}
	}

	public void keyPressed(int k){
		if(showCredits){
			showCredits = false;
		}

	}

	public void keyReleased(int k){
		
	}

}