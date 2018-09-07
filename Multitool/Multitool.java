import java.util.*;
import java.math.*;
import java.util.concurrent.*;
public class Methods {
	static LinkedList<String>shipnames = new LinkedList<String>();
	static LinkedList<String> password = new LinkedList<String>();
	static LinkedList<String> nicknames = new LinkedList<String>();
	static LinkedList<String> options = new LinkedList<String>();
	static LinkedList<String> multitool = new LinkedList<String>();
	static LinkedList<String> games = new LinkedList<String>();
	static LinkedList<String> anime = new LinkedList<String>();
	static LinkedList<String> animeuser = new LinkedList<String>();
	static LinkedList<String> encryption = new LinkedList<String>();
	static LinkedList<String> usernames = new LinkedList<String>();
	static LinkedList<String> prime = new LinkedList<String>();
	static LinkedList<String> BTS = new LinkedList<String>();
	static int cnt = 0;
	static boolean ids;
	static boolean loginmain = false;
	static boolean animein = false;
	static boolean animein2 = false;
	static int array[];
	static int primes[];
public static void main(String[]args) throws Exception {
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(System.in);
	multitooloptionslib();
	encryptionlib();
	usernameslib();
	PasswordData();
	optionslib();
	animelib();
    nicknames();
	library();
	gamelib();
	primelib();
	BTSlib();
	boolean guest = false;
	boolean admin = false;
	menu : for(;;) {
	System.out.println("Welcome to the multitool!");
	if(!loginmain) {
		main1 :for(;;) {
	System.out.println("Please enter your username");
	System.out.println("If you don't have one, please enter 'guest'");
	String id = sc.next();
	if(id.equalsIgnoreCase("guest")) {
		System.out.println("the password is 1234");
		loginmain = true;
		guest = true;
		System.out.println("You are a guest");
	}
	id(id);
			System.out.println("Please Enter Password:");
	String password = sc.next();
	if(!password(password)) {
		System.out.println("Please try again:");
		continue main1;
	}
	else {
		loginmain = true;
		if(!guest) {
			System.out.println("You are an admin");
			admin = true;
		}
		break main1;
		}
	}
	}
	System.out.println("Please enter your preferance");
	System.out.println("Please enter the number that corresponds to the tool that you want");
	multitooloptions();
	int choose = sc.nextInt();
	if(choose==-1) {
		if(!admin||guest) {
			System.out.println("Sorry, admins only");
			TimeUnit.MILLISECONDS.sleep(1200);
			continue menu;
		}
		System.out.println("Do you want to see the usernames?");
		System.out.println("Yes:No");
		String ans = sc.next();
		if(ans.equalsIgnoreCase("yes")) 
			printusernames();
		System.out.println("Do you want to see the passwords?");
		System.out.println("Yes:No");
		String ans2 = sc.next();
		if(ans2.equalsIgnoreCase("yes"))
		printpasswords();
	}
	if(choose==0) {
		System.out.println("Exiting Program, Are you sure?");
		System.out.println("Yes:No");
		String last = sc.next();
		if(last.equalsIgnoreCase("no"))
			continue menu;
		else {
			System.out.println("Bye");
			System.out.println("Exited Program");
			System.exit(0);
		}
	}
	if(choose==1) {
		tool : for(;;) {
	System.out.println("Welcome to the Ships Library!");
	main :for(;;) {
		System.out.println("Would you like to check for people that are part of ships?");
		String reply = sc.next();
		if(reply.equalsIgnoreCase("yes")) {
	System.out.println("Please Enter The Name Of The First Person:");
	String name1 = sc.next();
	System.out.println("Please Enter the Name of The Second Person");
	String name2 = sc.next();
	if(ship(convert(name1),convert(name2))) {
		System.out.println("Yes, That's a ship");
	}
	else {
		System.out.println("Sorry, Not Existing or not in database");
		System.out.println("Would you like to search shipnames instead?");
		System.out.println("Yes:No?");
		String ans = sc.next();
		if(ans.equalsIgnoreCase("yes")) {
			System.out.println("Please enter a shipname:");
			String shipname = sc.next();
			if(shipname(convert(shipname))){
				System.out.println("Yes, That's a ship");
			}
			else {
				System.out.println("Sorry, Not Existing or not in database");
			}
		}
	}
}
		else {
			break main;
		}
	while(true) {
		System.out.println("Are you done?");
		System.out.println("Yes:No?");
		 String ans = sc.next();
		 if(ans.equalsIgnoreCase("yes"))
			 break main;
		 else 
		 continue main;
			}
		}
	if(!guest) {
		System.out.println("Would you like to look at the list of ships and shipnames?");
		System.out.println("Yes:No?");
		String ans = sc.next();
		if(ans.equalsIgnoreCase("yes")) {
			System.out.println("Would you like to print the names,shipnames or both?");
			String decsion = sc.next();
			if(decsion.equalsIgnoreCase("names"))
				printlib();
			if(decsion.equalsIgnoreCase("shipnames"))
				printnick();
			if(decsion.equalsIgnoreCase("both")) {
				printlib();
				printnick();
			}
	}
	}
		System.out.println("Would you like to check if you are part of a ship?");
		System.out.println("Yes:No?");
		String ans2 = sc.next();
		if(ans2.equalsIgnoreCase("yes")) {
			System.out.println("please enter your name and the person you are shipped with:" );
			String name1 = sc.next();
			String name2 = sc.next();
			System.out.println(name1+" "+name2);
		search(name1,name2);	
		}
		System.out.println("Go to Tool or Main Menu or Logout?");
		System.out.println("Tool:Main:Logout");
		String reply = sc.next();
		if(reply.equalsIgnoreCase("main"))
			continue menu;
		else if(reply.equalsIgnoreCase("tool"))
		continue tool;
		else if(reply.equalsIgnoreCase("logout")) {
	System.out.println("Thank you for your time");
	loginmain = false;
	admin = false;
	guest = false;
	continue menu;
	}
		}
}
	if(choose==2) {
	tool :for(;;) {
		System.out.println("Please choose from these options:");
		options();
		System.out.println("please enter the number that corresponds with the option");
		int num = sc.nextInt();
		if(num==1) {
		main :for(;;) {
		System.out.println("Please enter the arithmetic you want to preform: ");
		System.out.println("add:subtract:divide:multiply");
		String arithmetic = sc.next();
		System.out.println("Please Enter two numbers: ");
		BigInteger a = sc.nextBigInteger();
		BigInteger b = sc.nextBigInteger();
			System.out.println(cal(a,b,arithmetic));
		System.out.println("Do you want to calculate again?");
		String ans = sc.next();
		if(ans.equalsIgnoreCase("yes"))
			continue main;
		else
			break main;
		}
		}
		if(num==2) {
	main :for (;;){
			System.out.println("Welcome to the intrest calculator!");
			System.out.println("Please enter the rate in percentage");
			double rate = sc.nextDouble();
			System.out.println("Please enter how long in years");
			int year = sc.nextInt();
			System.out.println("Please enter the amount of money");
			int money = sc.nextInt();
			System.out.println("This is the result intrest: $"+intrest(rate,year,money));
			System.out.println("Do you want to calculate again?");
			String ans = sc.next();
			if(ans.equalsIgnoreCase("yes"))
				continue main;
			else
			break main;
		}
		}
		if(num==3) {
			main :for(;;) {
			System.out.println("Welcome to the total + intrest calculator!");
			System.out.println("Please enter the rate in percentage");
			double rate = sc.nextDouble();
			System.out.println("Please enter how long in years");
			int year = sc.nextInt();
			System.out.println("Please enter the amount of money");
			int money = sc.nextInt();
			System.out.println("This is the result total: $"+total(rate,year,money));
			System.out.println("Do you want to calculate again?");
			String ans = sc.next();
			if(ans.equalsIgnoreCase("yes"))
				continue main;
			else
			break main;
		}
		}
		if(num==4) {
				System.out.println("Welcome to the Square Root Calculator!");
			main : for(;;) {
				System.out.println("Please enter a number");
				double number = sc.nextDouble();
				System.out.println("Please set a precision from 1 to 10");
				String pres = sc.next();
				while((pres.length()>2)&&(pres.charAt(0)!='1'&&pres.charAt(1)!='0')) {
					System.out.println("Number too big, please ReEnter");
					pres = sc.next();
				}
				int pre = Integer.parseInt(pres);
				double ans = Math.sqrt(number);
				precision(pre,ans);
				System.out.println();
				System.out.println("Check Again?");
				System.out.println("Yes:No");
				String reply = sc.next();
				if(reply.equalsIgnoreCase("yes"))
					continue main;
				else
					break main;
				
			}
		}
		if(num==5) {
			System.out.println("Welcome to the Factorial Calculator!");
			System.out.println("The calculator cannot handle numbers beyond "+Long.MAX_VALUE);
			System.out.println("Its best if you don't input numbers beyond 1000,as it can't fit on the screen");
			System.out.println("It takes about the computer 1 second to handel numbers about 2^26 - 2^30");
			System.out.println("The bigger the number, the longer it takes");
			main : for(;;) {
				System.out.println("Please enter a number");
				long number = sc.nextLong();
				System.out.println(howlong(number)+" seconds");
				System.out.println("This is the factorial");
				System.out.println(factorial(number));
				System.out.println("Calculate Again?");
				System.out.println("Yes:No");
				String ans = sc.next();
				if(ans.equalsIgnoreCase("yes"))
					continue main;
				else
					break main;
			}
		}
		if(num==6) {
			System.out.println("Welcome to the arithmetic calculator!");
			main : for(;;) {
				System.out.println("Do you want to use recursion, loop or formula?");
				System.out.println("Loop:Formula:Recursion");
				String ans = sc.next();
				System.out.println("Please enter the starting number");
				int start = sc.nextInt();
				System.out.println("Please enter the end number");
				int end = sc.nextInt();
				System.out.println("Please enter the absolute difference between each number eg. 1,2 the difference is 1");
				int diff = sc.nextInt();
				if(ans.equalsIgnoreCase("loop"))
					System.out.println(loop(start,end,diff));
				if(ans.equalsIgnoreCase("recursion")) 
					System.out.println(recur(start,end,diff));
				if(ans.equalsIgnoreCase("formula")) {
					System.out.println(formula(start,end,diff));
				}
				System.out.println("Do you want to check again?");
				System.out.println("Yes:No");
				String reply = sc.next();
				if(reply.equalsIgnoreCase("yes"))
					continue main;
				else
					break main;
			}
		}
		if(num==7) {
			continue menu;
		}
		System.out.println("Go to Tool or Main Menu or Logout?");
		System.out.println("Tool:Main:Logout");
		String reply = sc.next();
		if(reply.equalsIgnoreCase("main"))
			continue menu;
		else if(reply.equalsIgnoreCase("tool"))
		continue tool;
		else if(reply.equalsIgnoreCase("logout")){
			System.out.println("Thank you for your time");
			loginmain = false;
			admin = false;
			guest = false;
			continue menu;
	}
	}
	}
	if(choose==3) {
		tool:for(;;) {
		System.out.println("Welcome to the game tool");
		System.out.println("These are the games: ");
		game();
		System.out.println("Please pick the number that corresponds to the option");
		int num = sc.nextInt();
		if(num==1) {
			System.out.println("Welcome to palindrome checker!");
			System.out.println("please enter a string, and the computer will check if it's a palindrome or not");
			main :for(;;) {
			String palindrome = sc.next();
			if(palindrome(palindrome)) {
				System.out.println("Yes, this string is a palindrome");
			}
			else {
				System.out.println("No, this string is not a palindrome");
				}
			System.out.println("continue?");
			String ans = sc.next();
			if(ans.equalsIgnoreCase("yes"))
				continue main;
				else
			    break main;
			}
		}
		if(num==2) {
			System.out.println("Welcome to guess the number!");
			System.out.println("The computer will print it's guessed number, it want to go lower, enter 'lower' if you want to go higher, enter 'higher'");
			System.out.println("If the number is the same as what you guessed, enter 'correct'");
			System.out.println("The Computer will roughly take about log(range)tries until it gets your number");
			main : for(;;) {
			System.out.println("Please pick a range thats smaller than to 10,000,000");
			String range1 = "";
			while(true) {
			 range1 = sc.next();
			int t = range1.length();
			if(t>7)
				System.out.println("Range Too Large, Please ReEnter");
			else 
				break;
			}
			int range = Integer.parseInt(range1);
			System.out.println("Number of tries: "+(int)(Math.ceil(Math.log(range))));
			System.out.println("please pick a number from 1 to "+range);
			int lo = 1;
			int hi = range;
			int arr[] = new int[range+1];
			for(int i=1;i<=range;i++) {
				arr[i] = i;
			}
			boolean guessed = false;
			while(lo<=hi) {
				int mid = (lo+hi)/2;
				System.out.println(arr[mid]);
				String command = sc.next();
				if(command.equalsIgnoreCase("higher"))
					lo = mid+1;
				else if(command.equalsIgnoreCase("lower"))
					hi = mid-1;
				else if(command.equalsIgnoreCase("correct")) {
					System.out.println("This is your number: "+arr[mid]);
					guessed = true;
					break;
				}
			}
			if(!guessed) {
				System.out.println("Your higher/lower commands were not correct, try again?");
				System.out.println("Yes:No");
				String reply = sc.next();
				if(reply.equalsIgnoreCase("yes"))
					continue main;
				else
					break main;
			}
			System.out.println("Do you want to play Again?");
			String ans = sc.next();
			if(ans.equalsIgnoreCase("yes"))
				continue main;
			else
				break main;
			}
		}
			if(num==3) {
				if(!admin) {
					System.out.println("sorry, admins only");
					TimeUnit.MILLISECONDS.sleep(1200);
					continue tool;
				}
			System.out.println("Hello Players! I am the GM and welcome to Tic-Tac-Toe! ^_^");
			System.out.println("You will be playing on a 3 by 3 grid. The first person to make 3 in a row wins!");
			System.out.println("In order to tell me where you want to put your icon, you'll have to enter coordinates.");
			System.out.println("\n"+"The coordinates work as follows:"+"\n");
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 2; j++) {
					System.out.print((i+1)+","+(j+1)+" | ");
				}
				System.out.println((i+1)+","+3);
				if(i<2) {
					System.out.println("____ _____ _____");
				}
			}
			System.out.println("\n"+ "Player 1: What is your name?");
			String pn1 = sc.next();
			System.out.println("Nice to meet you, " + pn1+"!");
			System.out.println("What is your icon? Please pick 1 letter or number");
			String p1 = sc.next();
			System.out.println("\n"+"Player 2: What is your name?");
			String pn2 = sc.next();
			System.out.println("Hello " + pn2+"! I bet you are waay better than " + pn1+"! Hehheh, jk ;)");
			System.out.println("What is your icon? Please pick 1 letter or number");
			String p2 = sc.next();
			System.out.println();
			System.out.println("Player 1 is " + pn1 + " and their icon is " + p1);
			System.out.println("Player 2 is " + pn2 + " and their icon is " + p2);
			System.out.println("GOOD LUCK Y'ALL! Can't cheat on this program ;)");
			 main : for(;;) {
			int cnt = 1;
			String arr[][] = new String[3][3];
			memsetstr(arr," ");
			while(grid(arr)==false) {
				if(p1win(arr, p1)) {
					System.out.println("Player 1 Wins!");
					System.out.println("Congratulations, "+pn1+"!");
					System.out.println("Play Again? Yes:No");
					String ans = sc.next();
					if(ans.equalsIgnoreCase("yes"))
						continue main;
					else
						break main;
				}
				else if(p2win(arr, p2)) {
					System.out.println("Player 2 Wins!");
					System.out.println("Congratulations, "+pn2+"!");
					System.out.println("Play Again? Yes:No");
					String ans = sc.next();
					if(ans.equalsIgnoreCase("yes"))
						continue main;
					else
						break main;
				}
				if(turn(cnt)) {
					System.out.println("Player 2's Turn. Please enter the coordinates");
					int c1 = sc.nextInt()-1;
					int c2 = sc.nextInt()-1;
					while(override(arr, c1, c2)) {
						System.out.println("Oops! Looks like this square is already taken. :) Please enter some new coordinates");
						c1 = sc.nextInt()-1;
						c2 = sc.nextInt()-1;
					}
					arr[c1][c2] = p2;
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 2; j++) {
							System.out.print(arr[i][j]+" | ");
						}
						System.out.println(arr[i][2]);
						if(i<2) {
							System.out.println("-- --- ---");
						}
					}
				}
				else if(turn(cnt)==false) {
					System.out.println("Player 1's Turn. Please enter the coordinates");
					int c1 = sc.nextInt()-1;
					int c2 = sc.nextInt()-1;
					while(override(arr, c1, c2)) {
						System.out.println("Oops! Looks like this square is already taken. :) Please enter some new coordinates");
						c1 = sc.nextInt()-1;
						c2 = sc.nextInt()-1;
					}
					arr[c1][c2] = p1;
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 2; j++) {
							System.out.print(arr[i][j]+" | ");
						}
						System.out.println(arr[i][2]);
						if(i<2) {
						System.out.println("-- --- ---");
						}
					}
				}
				cnt++;
				
			}
			System.out.println("Tie! HAHAHA I WIN y'all losers!");
			System.out.println("Try again? ;)	YES : NO");
			String ans = sc.next();
			if(ans.equals("YES")) {
				continue main;
			}
			else if(ans.equals("NO")) {
				System.out.println("Are you sure???? I still wanna play	YES : NO");
				ans = sc.next();
				if(ans.equals("YES")) {
					continue main;
				}
			}
			else {
				System.out.println("I can't understand what you're saying. Shutting down, sorry. -_-");
				break main;
			}
			}

		}
			if(num==4) {
				if(!admin) {
					System.out.println("sorry, admins only");
					TimeUnit.MILLISECONDS.sleep(1200);
					continue tool;
				}
				System.out.println("Welcome to Guess the Closer Number!");
				System.out.print("This is classic betting game involving 2 players! ");
				System.out.println("Each player bets a certain amount of money, and guesses my number. ");
				System.out.println("If you guess the closest to my number, you receive the money; but if you don't, you lose money!");
				System.out.println("Player 1: What would you like your username to be?");
				String p1name = sc.next();
				System.out.println("Player 2: What would you like your username to be?");
				sc.nextLine();
				String p2name = sc.next();
				System.out.println("Hello " +p1name+" and " + p2name+"!");
				System.out.println("I'm Jessica, the GameMaster. Let the fun commence!");
				System.out.println("What is the range? Your range is the amount of money you each start with.");
				int rangemoney = sc.nextInt();
				int p1 = rangemoney;
				int p2 = rangemoney;
				int min = minmoney(rangemoney);
				System.out.println("Minimum bet is " + min+".");
				 main :for(;;) {
				int pot = 0;
					System.out.println("Player 1: How much do you want to bet?");
					int pm1 = 0;
					for(;;) {
						pm1 = sc.nextInt();
						if(pm1<min) {
							System.out.println("Too low. Bets must be higher than "+min+".");
							continue;
						}
						if(pm1>rangemoney) {
							System.out.println("Dude what are you doing?? Wanna go into debt??? TRY AGAIN");
							continue;
						}
						else {
							break;
						}
					}
					pot = pm1;
					System.out.println("Which number do you bet on?");
					int num1 = 0;
					for(;;) {
					num1 = sc.nextInt();
					if(num1>rangemoney||num1<0) {
						System.out.println("Are you trying to lose??? Try AGAIN");
						continue;
					}
					else 
						break;
					}
					//player 2 turn
					System.out.println("Player 2 Turn:"); 
					int num2 = 0;
					int bet = 0;
					System.out.println("How much are you betting?");
					for(;;) {
						bet = sc.nextInt();
						if(bet<min) {
							System.out.println("Too low. Bets must be higher than "+min+".");
							continue;
						}
						if(bet>rangemoney) {
							System.out.println("Dude what are you doing?? Wanna go into debt??? TRY AGAIN");
							continue;
						}
						else {
							break;
						}
					}
					System.out.println("Which number do you bet on?");
					for(;;) {
						num2 = sc.nextInt();
						if(num2>rangemoney||num2<0) {
							System.out.println("Are you trying to lose??? Try AGAIN");
							continue;
						}
						else 
							break;
						}
					pot = pot+bet;
					int rannum = random(rangemoney);
					System.out.println("And the number is: ");
					TimeUnit.MILLISECONDS.sleep(1500);
					System.out.print(rannum+"!");
					TimeUnit.SECONDS.sleep(1);
					System.out.println();
					int p1pot = p1(num1, rannum);
					int p2pot = p2(num2, rannum);
					if(p1pot<p2pot) {
						System.out.println(p1name+ " receives $" + pot + "!");
						System.out.println(p1name+": $" + (pot+p1));
						pm1 = pot + p1;
						System.out.println(p2name+": $" + (p2-bet));
						p2 = p2-bet;
					}
					else if(p2pot<p1pot) {
						System.out.println(p2name+" receives $" + pot+"!");
						System.out.println(p1name+": $" + (p1-pm1));
						p1 = p1-pm1;
						System.out.println(p2name+": $" + (p2+pot));
						p2 = p2 + pot;
					}
					else {
						System.out.println("Ha! Since both of you betted on the same number, none of you guys win! IT'S MINE. ALL MINE.");
						System.out.println(p1name+": $" + (p1-pm1));
						System.out.println(p2name+": $" + (p2-bet));
						p1 = p1-pm1;
						p2 = p2-bet;
					}
					if(p1<=0||p2<=0) {
						 if(p2<=0) {
								System.out.println(p2name+" ran out of money! " +p1name+" wins!");
							}
							else if(p1<=0) {
								System.out.println(p1name+" ran out of money! " +p2name+" wins!");
							}
				System.out.println("Play Again?");
				System.out.println("Yes:No");
				String ans = sc.next();
				if(ans.equalsIgnoreCase("yes"))
					continue main;
				else
					break main;
				 }
				 }
			}
			if(num==5) {
				System.out.println("Welcome to Hangman!");
				System.out.println("I'm Jessica, the GameMaster. This is a classic game in which you need to guess the correct word entered by your friend!");
				main :for(;;) {
				System.out.println("How many lives do you want to start with?");
				int lives = sc.nextInt();
				while(lives>10) {
					System.out.println("That's too much; lives must be smaller than 10");
					lives = sc.nextInt();
					sc.nextLine();
					if(lives<=10) {
						break;
					}
				}
				sc.nextLine();
				System.out.println("Do you want a computer-generated word?");
				String myans = sc.next();
				String a = "";
				if(myans.equalsIgnoreCase("no")) {
				System.out.println("Please turn away if you are guessing. We will now enter the string"+"\n");
				System.out.println("Enter String");
				a = sc.nextLine();
				while(a.length()<=1) {
					System.out.println("Hmmm... that's too short. Please enter a different string");
					a = sc.nextLine();
				}
				a = a.toLowerCase();
				for(int i = 0; i < 20; i++) {
					System.out.println("Loading...");
					System.out.println("\n"+"\n"+"\n");
				}
				}
				else {
					a = hangmanlibrary();
				}
				System.out.println("Alright. You can start guessing letters now");
				String arr[] = new String[a.length()];
				String arr2[] = new String[26];
				for(int i =0; i < a.length(); i++) {
					char ch = a.charAt(i);
					String c = ""+ch;
					if(c.equals(" ")) {
						arr[i] = " ";
						continue;
					}
					arr[i] = "_";
				}
				for(int i = 0; i < 26; i++) {
					arr2[i] = "`";
				}
				
				while(true) {
					if(checkagain(arr)) {
						System.out.println("\n"+"Yay you guessed the word!");
						System.out.println("\n"+"\n"+"Play Again?");
						System.out.println("Yes:No");
						String x = sc.next();
						if(x.equalsIgnoreCase("no")) {
						break main;
						}
						else {
							break;
						}
					}
					String letter = sc.next();
					while(checkletter(letter)) {
						System.out.println("Oops! Please enter only 1 letter");
						letter = sc.next();
					}
					letter = letter.toLowerCase();
					if(check(letter, a)) {
						System.out.println("Correct!");
					}
					else {
						lives--;
						arr2 = incorrect(arr2, letter);
						if(lives<=0) {
							System.out.println("Sorry. You ran out of lives :(");
							System.out.println("The word was "+a);
							System.exit(0);
						}
					}
					arr = word(arr, letter, a);
					for(int i = 0; i < a.length(); i++) {
						System.out.print(arr[i]+" ");
					}
					System.out.println();
					System.out.print("Incorrect Letters: ");
					for(int i =0; i < arr2.length; i++) {
						if(!arr2[i].equals("`")) {
							System.out.print(arr2[i]+" ");
						}
					}
					System.out.println();
					System.out.println("Current Lives: " + lives);
				}
				continue;
				}
			}
		if(num==6) {
			continue menu;
		}
		System.out.println("Go to Tool or Main Menu or Logout?");
		System.out.println("Tool:Main:Logout");
		String reply = sc.next();
		if(reply.equalsIgnoreCase("main"))
			continue menu;
		else if(reply.equalsIgnoreCase("tool"))
		continue tool;
		else if(reply.equalsIgnoreCase("logout")){
			System.out.println("Thank you for your time");
			admin = false;
			guest = false;
			loginmain = false;
			continue menu;
		}
	}
}
	if(choose==4) {
		tool : for(;;) {
		System.out.println("Welcome to the anime/weeb/weeaboo/otaku tool!");
		if(guest) {
			System.out.println("Here are some popular animes:");
			printanime();
		}
		if(!guest&&admin) {
		System.out.println("This is an anime game specifically for admins! ^_^");
		System.out.println("Play Game? Yes:No");
		String ans = sc.next();
		if(ans.equalsIgnoreCase("yes")) {
		System.out.println("There will be 5 questions, try to answer all of them!");
		System.out.println("Here are the anime being used in this game:");
		printanime();
		System.out.println("Please, no spaces when entering names such as one punch man, enter 'onepunchman' instead");
		cnt =0;
		for(int i=0;i<5;i++) {
		final int t = (int)(Math.random()*10+1);
		System.out.print((i+1)+": ");
		animegame(t);
		String input = sc.next();
		check(t,input);
		}
		System.out.println("Your Final Score is: "+cnt);
		if(cnt==5) {
			System.out.println("Congradulations! you really are a weeb");
		System.out.println();
		}
		}
		System.out.println("Do you want to see the popular anime list?");
		System.out.println("Yes:No");
		String answer = sc.next();
		if(answer.equalsIgnoreCase("yes"))
			printanime();
		}
	System.out.println("Go to Tool or Main Menu or Logout?");
	System.out.println("Tool:Main:Logout");
	String reply = sc.next();
	if(reply.equalsIgnoreCase("main"))
		continue menu;
	else if(reply.equalsIgnoreCase("tool"))
	continue tool;
	else if(reply.equalsIgnoreCase("logout")){
		System.out.println("Thank you for your time");
		admin = false;
		guest = false;
		loginmain = false;
		continue menu;
				}
			}
		}
	if(choose==5) {
		tool :for(;;) {
		System.out.println("Welcome to the encryption tool!");
		System.out.println("Please choose with the number that corresponds to the option:");
		printencryption();
		int num = sc.nextInt();
		if(num==1) {
			 System.out.println("Welcome to matrix encode!");
			 main : for(;;) {
				 System.out.println("Please enter a String(no spaces):");
				 String input = sc.next();
				 System.out.println("Please enter the dimensions for your matrix (row, column");
				 int r = sc.nextInt();
				 int c = sc.nextInt();
					System.out.println("Your new Encoded String is: "+code2(input, r, c));
					System.out.println("Encode again?");
					String ans = sc.next();
					if(ans.equalsIgnoreCase("yes"))
					continue main;
					else
						break main;
			 }
		}
				if(num==2) {
					System.out.println("Welcome to the ceaser cypher!");
					main : for(;;) {
						System.out.println("Please enter a string(no spaces) and the number of shifts(shifts cannot exceed 64)");
						String str = sc.nextLine();
						int shift = sc.nextInt();
						System.out.println("The encoded string: "+code1(str,shift));
						System.out.println("Encode again?");
						String ans = sc.next();
						if(ans.equalsIgnoreCase("yes"))
							continue main;
						else
							break main;
					}
				}
			 System.out.println("Go to Main Menu or Tool or Logout?");
			 System.out.println("Main:Tool:Logout");
			 String ans = sc.next();
			 if(ans.equalsIgnoreCase("main"))
				 continue menu;
			 else if(ans.equalsIgnoreCase("tool"))
				 continue tool;
			 else if(ans.equalsIgnoreCase("logout")){
				 System.out.println("Thank you for your time");
				 loginmain = false;
				 continue menu;
			 }
		}
	}
	if(choose==6) {
		tool:for(;;) {
			System.out.println("Welcome to the Prime Calculator!");
			System.out.println("Here are the options: ");
			printprimelib();
			System.out.println("Please choose the number that corresponds to the option:");
			int num = sc.nextInt();
			if(num==1) {
				System.out.println("Welcome to the simple prime checker!");
				main:for(;;) {
				System.out.println("Please enter a number that is smaller than 1,000,000,000");
				int number = 0;
				input :for(;;) {
					String input = sc.next();
				if(input.length()>=9) {
					System.out.println("Number too large");
					System.out.println("Please ReEnter");
				continue input;
				}
				else {
					number = Integer.parseInt(input);
					break input;
				}
				}
				if(simpleprime(number))
					System.out.println("Number is prime");
				else
					System.out.println("Number is not prime");
				System.out.println("Check another number?");
				String answer = sc.next();
				if(answer.equalsIgnoreCase("yes"))
					continue main;
				else {
					break main;
				}
				}
			}
			if(num==2) {
			System.out.println("Welcome to hard prime checker");
			main : for(;;) {
				System.out.println("You can enter any number, but the larger the number the longer it takes");
				System.out.println("Please Enter a Number");
				BigInteger a = sc.nextBigInteger();
				if(hardprime(a))
					System.out.println("Prime");
				else
					System.out.println("Not Prime");
				System.out.println("Check again?");
				String ans = sc.next();
				if(ans.equalsIgnoreCase("yes"))
					continue main;
				else {
					break main;
				}
			}
			}
			if(num==3) {
				System.out.println("Welcome to the prime generator!");
				System.out.println("This generator generates primes between two ranges");
				System.out.println("The absolute difference between the ranges must be smaller than 100000");
				main: for(;;) {
					System.out.println("Please enter the starting range");
					int start = sc.nextInt();
					System.out.println("Please enter the end range");
					int end = sc.nextInt();
					System.out.println("These are the primes in the given range");
					generateprime(start,end);
					System.out.println();
					System.out.println("Generate again?");
					String ans = sc.next();
					if(ans.equalsIgnoreCase("yes"))
						continue main;
					else
						break main;
				}
			}
			if(num==4) {
				System.out.println("Welcome to the sieve generator!");
				System.out.println("Enter two ranges, the absolute difference between the ranges cannot exceed 5,000,000");
				main:for(;;) {
					System.out.println("Please enter the first range");
					int range1 = sc.nextInt();
					System.out.println("Please enter the second range");
					int range2 = sc.nextInt();
					sieve(range1,range2);
					System.out.println();
					System.out.println("These are the primes in the given range");
					System.out.println("Do you want to generate again?");
					String ans = sc.next();
					if(ans.equalsIgnoreCase("yes"))
						continue main;
					else
						break main;
				}
			}
			if(num==5) {
				System.out.println("Welcome to the prime fixer!");
				System.out.println("you can enter any number with ONE underscore");
				System.out.println("The program will check for numbers to be replaced");
				System.out.println("for the undersocres for the number to be prime");
				main :for(;;) {
				String s, space;
				int number,blank,prime= 0;
				char [] nums = {'0','1','2','3','4','5','6','7','8','9',};
				System.out.println("Please enter the number");
				s = sc.next();
					prime =0;
					for(int i=0;i<10;i++) {
						space = s.replace('_', nums[i]);
						number = Integer.parseInt(space);
						blank = Integer.parseInt(Character.toString(nums[i]));
						if(simpleprime(number)) {
							System.out.print(blank+" ");
						prime++;
						}
					}
						if(prime==0) 
							System.out.println("Not possible");
						else if(prime>0)
							System.out.println();
						System.out.println("Check again?");
						String ans = sc.next();
						if(ans.equalsIgnoreCase("yes"))
							continue main;
						else
							break main;
				}
			}
			System.out.println("Go to Main Menu or Tool or Logout?");
			System.out.println("Main:Tool:Logout");
			String ans = sc.next();
			if(ans.equalsIgnoreCase("main"))
				continue menu;
			else if(ans.equalsIgnoreCase("tool"))
				continue tool;
			else if(ans.equalsIgnoreCase("logout")){
				System.out.println("Thank you for your time");
				admin = false;
				guest = false;
				loginmain = false;
				continue menu;
				}
			}
		}
	}
}
public static void multitooloptionslib() {
	multitool.add("1: Ships");
	multitool.add("2: Calculator");
	multitool.add("3: Games");
	multitool.add("4: Anime");
	multitool.add("5: Encryption");
	multitool.add("6: Prime generator");
	multitool.add("0: Exit Program");
	multitool.add("-1: Edits (admins only)");
}
public static void multitooloptions() {
	for(String i: multitool)
		System.out.println(i);
	
	System.out.println();
}
public static boolean password(String key) {
	if(password.contains(key)&&ids)
		return true;
	return false;
}
public static void printpasswords() {
	for(String i:password)
		System.out.println(i);
	
	System.out.println();
}
public static void printusernames() {
	for(String i:usernames)
		System.out.println(i);
	
	System.out.println();
}
public static boolean ship(String name1,String name2) {
	String ship = convert(name1+"+"+name2);
	String ship2 = convert(name2+"+"+name1);
	if(shipnames.contains(ship)||shipnames.contains(ship2))
		return true;
	
		return false;
}
public static boolean shipname(String ship) {
	if(nicknames.contains(ship))
		return true;
	
	return false;
}
public static void library() {
	shipnames.add("jessica+hudson");
	shipnames.add("sarah+hudson");
	shipnames.add("kiyoon+sisi");
	shipnames.add("ryan+rachel");
	shipnames.add("annika+enming");
	shipnames.add("jackie+ann");
	shipnames.add("shanshan+kiyoon");
	shipnames.add("sisi+kiyoon");
	shipnames.add("jennifer+timmy");
	shipnames.add("peter+sherry");
	shipnames.add("peter+shanshan");
	shipnames.add("karanvir+rachel");
}
public static void nicknames() {
	nicknames.add("shanter");//shanshan+peter
	nicknames.add("hessica");//jessica+hudson
	nicknames.add("shanyoon");//shanshan+ki-yoon
	nicknames.add("sadson");//sarah+hudson
	nicknames.add("kisi");//ki-yoon+sisi;
	nicknames.add("timmifer");//timmy+jennifer
	nicknames.add("perry");//peter+sherry
	nicknames.add("ryachel");//ryan+rachel
	nicknames.add("jackann");//jackie+ann
}
public static void PasswordData() {
	password.add("lol");
	password.add("69");
	password.add("1234");
	password.add("shanyoon");
	password.add("Iamgay");
}
public static void id(String id) {
	id = convert(id);
	if(usernames.contains(id)) {
		ids = true;
	}
	else {
		ids = false;
	}
}
public static void usernameslib() {
	usernames.add("james");
	usernames.add("ryan");
	usernames.add("admin");
	usernames.add("guest");
	usernames.add("nettlespike");
	usernames.add("annika");
	usernames.add("A$IAN_CLAN");
}
public static String convert(String name) {
	name = name.replaceAll("-", "");
	name = name.toLowerCase();
	return name;
}
public static void printlib() {
	System.out.println("These are the people: ");
	for(String i:shipnames) {
		System.out.println(i);
	}
	System.out.println();
}
public static void printnick() {
	System.out.println("These are the ship names: ");
	for(String i:nicknames) {
		System.out.println(i);
	}
	System.out.println();
}
public static void search(String name1,String name2) {
if(ship(name1,name2)) {
	System.out.println("Yes, You are part of a ship");
	System.out.println(name1+"+"+name2);
}
else {
	System.out.println("No, you are not a ship or not found in this current database");
}
}
public static void optionslib() {
	options.add("1: +-*/ calculator");
	options.add("2: intrest calculator");
	options.add("3: total+intrest calculator");
	options.add("4: Square Root calculator");
	options.add("5: Factorial Calculator");
	options.add("6: Arithmetic Calculator");
	options.add("7: main menu");
}
public static void options() {
	for(String i:options)
		System.out.println(i);
	
	System.out.println();
}
public static BigInteger cal(BigInteger a,BigInteger b,String arithmetic) {
	if(arithmetic.equalsIgnoreCase("add"))
	return a.add(b);
	else if(arithmetic.equals("subtract"))
	return a.subtract(b);
	else if(arithmetic.equals("divide"))
	return a.divide(b);
	else 
	return a.multiply(b);
}
public static double intrest(double rate,int years,int money) {
	return money*(rate/100.0)*years;
}
public static double total(double rate,int years,int money) {
	for(int i=0;i<years;i++) {
		money*=(1+(rate/100.0));
	}
	return money;
}
public static void gamelib() {
	games.add("1: Palindrome");
	games.add("2: Guess The number");
	games.add("3: Tic tac toe (admins only)");
	games.add("4: Betting game (admins only)");
	games.add("5: Hangman (admins only)");
	games.add("6: main menu");
}
public static void game() {
	for(String i:games) 
		System.out.println(i);
		
	System.out.println();
}
public static boolean palindrome(String str) {
		String res = "";
		for(int i=str.length()-1;i>=0;i--) {
			res+=str.charAt(i);
		}
		if(res.equals(str))
			return true;
		else
			return false;
}
public static void animelib() {
	anime.add("my hero academia");
	anime.add("attack on titan");
	anime.add("assasination classroom");
	anime.add("death note");
	anime.add("one punch man");
	anime.add("your lie in april");
	anime.add("charlotte");
	anime.add("fairy tail");
	anime.add("naruto");
	anime.add("bleach");
	anime.add("dragonball");
}
public static void printanime() {
	for(String i:anime)
		System.out.println(i);
	
	System.out.println();
}
public static void animegame(int t) {
	switch(t) {
	case 1:
		System.out.println("what anime involves a alien? hint: Sayains are not aliens in this game");
		break;
	case 2:
		System.out.println("what anime tells fairy tales?");
		break;
	case 3:
		System.out.println("what anime does the protaganist kill stuff with one punch?");
		break;
	case 4:
		System.out.println("what anime has its name as a suicidal product?");
		break;
	case 5:
		System.out.println("what anime 'lies'? in april?");
		break;
	case 6:
		System.out.println("what anime is all about yaoi ships, and cannabilism? hint: its not tokoyo ghoul");
		break;
	case 7:
		System.out.println("what anime introduced a new running stlye to reality?");
		break;
	case 8:
		System.out.println("what anime likes 'balls'");
		break;
	case 9:
		System.out.println("what anime is everyone watching right now? hint: 'hero'");
		break;
	case 10:
		System.out.println("what anime kills people with a notebook");
		break;
	}
}
public static void check(int t,String input) {
	switch(t) {
	case 1:
		if(input.equalsIgnoreCase("assasinationclassroom"))
			cnt++;
		break;
	case 2:
		if(input.equalsIgnoreCase("fairytail"))
			cnt++;
		break;
	case 3:
		if(input.equalsIgnoreCase("onepunchman"))
			cnt++;
	break;
	case 4: 
		if(input.equalsIgnoreCase("bleach"))
			cnt++;
		break;
	case 5: 
		if(input.equalsIgnoreCase("yourlieinapril"))
			cnt++;
		break;
	case 6:
		if(input.equalsIgnoreCase("attackontitan"))
			cnt++;
		break;
	case 7:
		if(input.equalsIgnoreCase("naruto"))
			cnt++;
		break;
	case 8: 
		if(input.equalsIgnoreCase("dragonball"))
			cnt++;
		break;
	case 9:
		if(input.equalsIgnoreCase("myheroacademia"))
			cnt++;
		break;
	case 10:
		if(input.equalsIgnoreCase("deathnote"))
			cnt++;	
	break;
	}
}
public static boolean simpleprime(int n) {
	if(n<=1)
		return false;
	if(n==2)
		return true;
	for(int i=2;i<=Math.sqrt(n);i++) {
		if(n%i==0)
			return false;
	}
	return true;
}
public static boolean hardprime(BigInteger num) {
	return num.isProbablePrime(100);
}
public static void generateprime(int range1,int range2) {
	for(int i=range1;i<=range2;i++) {
		if(simpleprime(i))
			System.out.println(i);
	}
}
public static void sieve(int n, int m) {
	 int j = 0;
	 int sqt = (int) Math.sqrt(m);
	 array = new int[sqt + 1];
	 primes = new int[sqt + 1];
	 initialise(sqt + 1);
	 for (int i = 2; i <= sqt; i++) {
	   if (array[i] == 1) {
	   primes[j] = i;
	   j++;
	     for (int k = i + i; k <= sqt; k += i) {
	       array[k] = 0;
	     }
	 }
	 }
	 int diff = (m - n + 1);
	 array = new int[diff];
	 initialise(diff);
	 for (int k = 0; k < j; k++) {
	 int div = n / primes[k];
	 div *= primes[k];
	 while (div <= m) {
	   if(div>=n && primes[k]!=div)
	   array[div-n] = 0;
	   div += primes[k];
	 }
	 }
	for (int i = 0; i < diff; i++) {
	 if (array[i] == 1 && (i+n) !=1)
	 System.out.println(i + n);
	 }
	 }
	public static void initialise(int sqt) {
	 for (int i = 0; i < sqt; i++) {
	 array[i] = 1;
	 }
	 }
public static void primelib() {
	prime.add("1: Simple prime checker");
	prime.add("2: Advanced prime checker");
	prime.add("3: Generate small primes");
	prime.add("4: Generate large primes");
	prime.add("5: Check for possible primes");
}
public static void printprimelib() {
	for(String i:prime)
		System.out.println(i);
	
	System.out.println();
}
public static boolean override(String arr[][], int c1, int c2) {
	if(!arr[c1][c2].equals(" ")) {
		return true;
	}
	return false;
}
public static boolean turn(int cnt) {
	if(cnt%2==0) {
		return true; //player 2;
	}
	return false; //player 1
}
public static boolean p1win(String arr[][], String p1) {
	if(winrow(arr, p1)||wincol(arr, p1)||windiag(arr, p1)) {
		return true;
	}
	return false;
}
public static boolean p2win(String arr[][], String p2) {
	if(winrow(arr, p2)||wincol(arr, p2)||windiag(arr, p2)) {
		return true;
	}
	return false;
}
public static boolean windiag(String arr[][], String p1) {
	boolean t = false;
	for(int i = 0; i < 3; i++) {
		if(arr[i][i].equals(p1)) {
			t = true;
		}
		else {
			t = false;
			break;
		}
	}
	if(t == true) {
		return true;
	}
	int cnt = 2;
	for(int i = 0; i < 3; i++, cnt--) {
		if(arr[i][cnt].equals(p1)) {
			t = true;
		}
		else {
			t = false;
			return false;
		}
	}
	return true;
}
public static boolean wincol(String arr[][], String p1) {
	for(int j = 0; j < 3; j++) {
		boolean t= false;
		for(int i = 0; i < 3; i++) {
			if(arr[i][j].equals(p1)) {
				if(i==2&&t==true) {
					return true;
				}
				t = true;
			}
			else {
				t = false;
				break;
			}
		}
	}
	return false;
}
public static boolean winrow(String arr[][], String p1) {
	boolean t = false;
	for(int i = 0; i < 3; i++) {
		for(int j = 0; j < 3; j++) {
			if(arr[i][j].equals(p1)) {
				if(j==2&&t==true) {
					return true;
				}
				t = true;
			}
			else {
				t = false;
				break;
			}
		}
	}
	return false;
}
public static boolean grid(String arr[][]) {
	for(int i = 0; i < 3; i++) {
		for(int j = 0; j < 3; j++) {
			if(arr[i][j].equals(" ")) {
				return false;
			}
		}
	}
	return true;
}
public static void memsetstr(String arr[][],String str) {
	for(int i=0;i<arr.length;i++) {
		for(int j=0;j<arr[i].length;j++) {
			arr[i][j] = str;
		}
	}
}
public static int p1(int num, int rannum) {
	int a = num - rannum;
	return Math.abs(a);
}
public static int p2(int num2, int rannum) {
	int a = num2 - rannum;
	return Math.abs(a);
}
public static int random(int range) {
	int a = (int)(Math.random()*range);
	return a;
}
public static int minmoney(int bet) {
	double sum = 0.1*bet;
	return (int)sum;
}
public static String code2(String a, int r, int c) {
	String arr[][] = new String[r][c];
	int cnt = 0;
	for(int i = 0; i < r; i++) {
		for(int j = 0; j < c; j++,cnt++) {
			char ch ='a';
			if(cnt<a.length()) {
			ch = a.charAt(cnt);
			}
			else {
				break;
			}
			arr[i][j] = ch+"";
		}
	}
	String str = "";
	for(int j = 0; j < c; j++) {
		for(int i = 0; i < r; i++) {
			if(arr[i][j]!=null) {
			str = str+arr[i][j];
			}
			else if(arr[i][j]==null) {
				break;
			}
			
		}
	}
	return str;
}
public static String code1(String a, int b) {
	/*
	 *  A==65, a = 97
	 *  Z==90, z = 122
	 */ 
	String str = "";
	for(int i = 0; i < a.length(); i++) {
		char c = a.charAt(i);
		if(c!=' ') {
		int d = c + b;
		if(d>90&&c<=90) {
			int e = 90-c;
			int f = b - e;
			d = 65 + f;
		}			
		else if(d>122&&c<=122) {
			int e = 122-c;
			int f = b- e;
			d = 97+f;
		}
		str = str+(char)d;
		}
		else
			str+=' ';
	}
return str;
}	
public static void encryptionlib() {
	encryption.add("1: Matrix encode");
	encryption.add("2: Ceaser cypher");
}
public static void printencryption() {
	for(String i:encryption) 
		System.out.println(i);
	
	System.out.println();
}
public static String convertstr(String arr[]) {
	String res = "";
	for(int i=0;i<arr.length;i++) {
		res+=arr[i];
	}
	return res;
}
public static void precision(int pres,double ans) {
	switch(pres) {
	case 1:
		System.out.println(String.format("%.1f",ans ));
		break;
	case 2:
		System.out.println(String.format("%.2f",ans ));
		break;
	case 3:
		System.out.println(String.format("%.3f",ans ));
	    break;
	case 4:
	    System.out.println(String.format("%.4f",ans ));
	    break;
	case 5:
		System.out.println(String.format("%.5f",ans ));
	    break;
	case 6:
	    System.out.println(String.format("%.6f",ans ));
	    break;
	case 7:
		System.out.println(String.format("%.7f",ans ));
	    break;
	case 8:
	    System.out.println(String.format("%.8f",ans ));
	    break;
	case 9:
		System.out.println(String.format("%.9f",ans ));
	    break;    
	case 10:
	    System.out.println(String.format("%.10f",ans));
	    break;
	}
}
public static BigInteger factorial(long times) {
	BigInteger one = new BigInteger("1");
	for(long i =1;i<=times;i++) {
		 one = one.multiply(BigInteger.valueOf(i));
	}
	return one;
}
public static long howlong(long num) {
	int number = (int)(Math.log(num));
	long seconds = (long)(Math.pow(2, number-26));
	return seconds;
}
public static String hangmanlibrary() {
	String arr[] = new String[10];
	arr[0] = "cat";
	arr[1] = "abracadbra";
	arr[2] = "knowledge";
	arr[3] = "shanyoon";
	arr[4] = "confusion";
	arr[5] = "peter";
	arr[6] = "fatigue";
	arr[7] = "verisimilitude"; 
	arr[8] = "altitude";
	arr[9] = "pandemonium";
	int a = (int)(Math.random()*9+1);
	return arr[a];
}
public static boolean checkletter(String letter) {
	if(letter.length()>1) {
		return true;
	}
	return false;
}
public static boolean checkagain(String arr[]) {
	for(int i = 0; i < arr.length; i++) {
		if(arr[i].equals("_")) {
			return false;
		}
	}
	return true;
}
public static String[] incorrect(String arr2[], String letter) {
	for(int i = 0; i < arr2.length; i++) {
		if(arr2[i].equals(letter)) {
			return arr2;
		}
	}
	for(int i =0; i < arr2.length; i++) {
		if(arr2[i].equals("`")) {
			arr2[i] = letter;
			break;
		}
	}
	Arrays.sort(arr2);
	return arr2;
}
public static String[] word(String arr[], String letter, String a) {
	for(int i = 0; i < a.length(); i++) {
		char ch = a.charAt(i);
		String c = ch+"";
		if(letter.equals(c)) {
			arr[i] = letter;
		}
	}
	return arr;
}
public static boolean check(String letter, String a) {
	for(int i =0; i < a.length(); i++) {
		char c = a.charAt(i);
		String ch = ""+c; 
		if(letter.equals(ch)) {
			return true;
		}
	}
	return false;
}
public static void BTSlib() {
	BTS.add("V");
	BTS.add("Jungkook");
	BTS.add("Jimin");
	BTS.add("Suga");
	BTS.add("Jin");
	BTS.add("RapMonster");
	BTS.add("J-Hope");
}
public static int recur(int start,int n,int t) {
	if(n==start)
		return start;
	return recur(start,n-t,t)+n;
}
public static int loop(int start,int n,int t) {
	int sum =0;
	for(int i=start;i<=n;i+=t) {
		sum+=i;
	}
	return sum;
}
public static long formula(long a1,long an,long n) {
	return ((a1+an)*nums(a1,an,n))/2;
}
public static long nums(long a1,long an,long d) {
	long n = (an-a1)/d+1;
	return n;
	
}
}
