package PaintBall;
import java.util.*;
public class Main {
	public static void main(String[]args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome To The PaintBall Game: ");
		System.out.println("Please Enter Your Name: ");
		String name = sc.next();
		int lives = 0;
		while(true) {
			System.out.println("Please Enter The Number Of Lives from 1 - 10");
			String str = sc.next();
			if((Character.isDigit(str.charAt(0)) && str.length() == 1) || str.equals("10")) {
				lives = Integer.parseInt(str);
				break;
			}
			else { 
				System.out.println("Sorry, Invalid Input");
			}
		}
		Player player = new Player(name, lives);
		Computer computer = new Computer(lives);
		Game game = new Game(player, computer);
		while(!game.gameEnd()) {
			game.displayStats();
			computer.pickSpot();
			 while(true) {
				System.out.println("Please Pick A Position:  1 / 2 / 3");
				String pos = sc.next();
				if(game.validInput(pos)) {
					player.pos = Integer.parseInt(pos);
					break;
				}
				else {
					System.out.println("Sorry, Invalid Input");
				}
			}
			while(true) {
				System.out.println("Please Pick A Position To Shoot At: 1 / 2 / 3 ");
					String pos = sc.next();
					if(game.validInput(pos)) {
						computer.shoot();
						System.out.println("Battle!");
						game.pause();
						
						System.out.print("Player Shot " + game.convert(Integer.parseInt(pos)) 
						+" And AI Shot " + game.convert(computer.shoot));
						System.out.println();
						System.out.println();
						
						if(player.gotHit(computer.shoot)) {
							System.out.println("The AI Shot You!");
							System.out.println();
							player.hit();
						}
						else {
							System.out.println("The AI Missed!");
							System.out.println();
						}
						if(computer.gotHit(Integer.parseInt(pos))) {
							System.out.println("You Shot The AI!");
							System.out.println();
							computer.hit();
						}
						else {
							System.out.println("You Missed!");
							System.out.println();
							System.out.println("The AI Was At The " + game.convert(computer.pos) + " / " + computer.pos +
						" Position");
							System.out.println();
						}
						break;
				}
				else {
					System.out.println("Sorry, Invalid Input");
				}
			}
		}
		System.out.println("Game Over!");
		if(player.win) 
			System.out.println("You Won!");
		else
			System.out.println("You Lost!");
		sc.close();
	}
}
