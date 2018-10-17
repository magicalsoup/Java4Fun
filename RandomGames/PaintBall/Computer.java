package PaintBall;

public class Computer {
	public int lives;
	public boolean gotHit;
	public String arr[] = {"left", "mid", "right"};
	public int shoot;
	public int pos;
	public Computer(int lives) {
		this.lives = lives;
	}
	public void minusLives() {
		this.lives--;
		this.gotHit = false;
	}
	public void shoot() {
		int rand = (int)(Math.random() * 3);
		this.shoot = rand + 1;
	}
	
	public boolean gotHit(int x) {
		if(pos == x) 
			return true;
		return false;
	}
	
	public void pickSpot() {
		int rand = (int)(Math.random() * 3);
		pos = rand + 1;
	}
	
	public void hit() {
		this.gotHit = true;
		minusLives();
	}
}
