package PaintBall;

import java.util.concurrent.TimeUnit;

public class Game {
	public Player player;
	public Computer computer;
	public Game(Player p, Computer c) {
		this.player = p;
		this.computer = c;
	}
	
	public boolean validInput(String s) {
		if(s.equals("1") ||s.equals("2") || s.equals("3"))
			return true;
		return false;
	}
	public boolean gameEnd() {
		if(player.lives == 0 || computer.lives == 0) {
			if(computer.lives == 0)
				player.win();
			return true;
		}
		return false;
	}
	
	public void displayStats() {
		System.out.println("Player Lives: " + player.lives);
		System.out.println("AI Lives: " + computer.lives);
	}
	public String convert(int pos) {
		switch(pos) {
		case 1:
			return "Left";
		case 2:
			return "Mid";
		case 3:
			return "Right";
		default:
			return "Up";
		}
	}
	public void pause() throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep(250);
	}
}
