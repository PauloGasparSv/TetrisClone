import javax.swing.JPanel;
import java.lang.Thread;
import java.lang.Runnable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Panel extends JPanel implements Runnable,KeyListener{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private boolean running;
	private Thread thread;
	private BufferedImage stage;
	private Graphics2D g;
	private GameStateManager gsm;

	public Panel(){
		super();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){	
			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}

	private void init(){
		stage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) stage.getGraphics();
		g.setBackground(Color.WHITE);
		gsm = new GameStateManager();
		running = true;
	}

	public void run(){
		init();
		long now = 0;
		long updateLenght = 0;
		int target_fps = 60;
		long optimal_time = 1000000000/target_fps;
		long lastLoopTime = System.nanoTime();
		double delta = 0;

		while(running){
			now = System.nanoTime();
			updateLenght = now - lastLoopTime;
			lastLoopTime = now;
			delta = updateLenght/(double) optimal_time;

			gsm.update(delta);
			gsm.draw(g);
			Graphics g2 = getGraphics();
			g2.drawImage(stage,0,0,WIDTH,HEIGHT,null);
			g2.dispose();

			try{
				Thread.sleep((lastLoopTime - System.nanoTime() + optimal_time) / 1000000);
			}catch(Exception e){}
		}
		System.exit(0);
	}

	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}
	public void keyTyped(KeyEvent e) {
	}
}