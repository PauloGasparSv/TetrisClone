import java.awt.Point;

public class Bloco{
	private int type;
	private Point [] pos;
	private int lock;

	public Bloco(int type){
		pos = new Point[4];
		setBlock(type);
	}

	public void setBlock(int type){
		this.type = type;
		if(type == Game.LBLOCK){
			pos[0] = new Point(4,0);
			pos[1] = new Point(4,1);
			pos[2] = new Point(4,2);
			pos[3] = new Point(5,2);
			lock = 2;
		}
		if(type == Game.ILBLOCK){
			pos[0] = new Point(4,0);
			pos[1] = new Point(4,1);
			pos[2] = new Point(4,2);
			pos[3] = new Point(3,2);
			lock = 2;
		}
		if(type == Game.SWIGBLOCK){
			pos[0] = new Point(3,0);
			pos[1] = new Point(4,0);
			pos[2] = new Point(4,1);
			pos[3] = new Point(5,1);
			lock = 2;
		}
		if(type == Game.ISWIGBLOCK){
			pos[0] = new Point(5,0);
			pos[1] = new Point(4,0);
			pos[2] = new Point(4,1);
			pos[3] = new Point(3,1);
			lock = 2;
		}
		if(type == Game.TBLOCK){
			pos[0] = new Point(3,0);
			pos[1] = new Point(4,0);
			pos[2] = new Point(5,0);
			pos[3] = new Point(4,1);
			lock = 1;
		}
		if(type == Game.SQUAREBLOCK){
			pos[0] = new Point(4,0);
			pos[1] = new Point(5,0);
			pos[2] = new Point(4,1);
			pos[3] = new Point(5,1);
			lock = -1;
		}
		if(type == Game.LINEBLOCK){
			pos[0] = new Point(4,0);
			pos[1] = new Point(4,1);
			pos[2] = new Point(4,2);
			pos[3] = new Point(4,3);
			lock = 1;
		}
	}
	public int getType(){
		return type;
	}

	public void getDown(){
		for(int i = 0; i < 4; i++)
			pos[i].y ++;
	}
	
	public void move(int dir){
		for(int i = 0; i < 4; i++)
			pos[i].x += dir;
	}
	
	public boolean canMove(int dir, int [][]map){
		for(int i = 0; i < 4; i++)
			if(pos[i].x + dir >-1 && pos[i].x + dir <10)
				if(map[pos[i].y][pos[i].x + dir] == -1)
					return true;
		return false;
	}

	public Point [] getPos(){
		return pos;
	}

	private void turn(){
		if(lock != -1){
			Point [] temp = turnAcw();
			if(temp != null)
				pos = temp;
		}
	}

	private Point [] turnAcw(){
		Point [] temp = new Point[4];
		temp[lock] = pos[lock];

		int tempX = -1;
		int tempY = -1;

		for(int i = 0; i < 4 && i != lock; i++){
			tempX = pos[i].y - pos[lock].y + pos[lock].x;
			tempY = pos[i].x - pos[lock].x + pos[lock].y;
			temp[i] = tempX >= 0 && tempX <=9 && tempY >= 0 && tempY <=19 ? new Point(tempX,tempY) : null;
			if(temp[i] == null)
				return null;
		}

		return temp;
	}
}