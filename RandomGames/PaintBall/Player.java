package PaintBall;

public class Player {
	public int lives;
	public int pos;
	public boolean gotHit;
	public String name;
	public boolean win;
	public Player(String name, int lives) {
		this.lives = lives;
		this.name = name;
		this.gotHit = false;
		this.win = false;
	}
	public void minusLives() {
		this.lives--;
	}
	
	public void win() {
		this.win = true;
	}
	public boolean gotHit(int x) {
		if(pos == x)
			return true;
		return false;
	}
	public void hit() {
		this.gotHit = true;
		minusLives();
	}
}
