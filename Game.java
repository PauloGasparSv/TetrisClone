import java.awt.Graphics2D;

public class Game extends GameState{
	
	public Game(GameStateManager gsm, Loader loader){
		super(gsm,loader);
	}

	public void init(){

	}

	public void update(double delta){

	}

	public void draw(Graphics2D g){
		g.clearRect(0,0,Panel.WIDTH,Panel.HEIGHT);
	}

	public void keyPressed(int k){

	}

	public void keyReleased(int k){
		
	}

}