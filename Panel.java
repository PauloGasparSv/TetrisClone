import javax.swing.JPanel;
import java.lang.Thread;
import java.lang.Runnable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener
import java.awt.image.BufferedImage;

@SuppresWarnings("serial")
public class Panel extends JPanel implements Runnable,KeyListener{
	public static final WIDTH = 800, HEIGHT = 600;
	private boolean running;
	private Thread thread;
	private BufferedImage stage;
	private Graphics2D g;

	public Panel(){
		super();
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){	
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run(){

		


	}

}