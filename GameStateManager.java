import java.awt.Graphics2D;

public class GameStateManager{
	public static final int MENU = 0, GAME = 1, CREDITS = 2;
	private final int numStates = 3;
	private int currentState;
	private GameState gameState;
	private Loader loader;

	public GameStateManager(){
		loader = new Loader();
		setState(GAME);
	}

	public void update(double delta){
		if(gameState != null)
			gameState.update(delta);
	}
	
	public void draw(Graphics2D g){
		if(gameState != null)
			gameState.draw(g);
	}

	public void loadState(){
		switch(currentState){
			case MENU: gameState = new Menu(this,loader); break;
			case GAME: gameState = new Game(this,loader); break;
			case CREDITS: gameState = new Menu(this,loader); break;
		}
	}

	public void setState(int state){
		if(state >= 0 && state < numStates){
			currentState = state;
			loadState();
		}
	}

	public void keyPressed(int k){
		if(gameState != null)
			gameState.keyPressed(k);
	}

	public void keyReleased(int k){
		if(gameState != null)
			gameState.keyReleased(k);
	}
}