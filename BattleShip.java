import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.lang.Integer;
import java.util.Vector;
import java.net.*;
import java.text.NumberFormat;

@SuppressWarnings("serial")
public class Battleship extends JFrame
{		
	private static JButton ok = new JButton("OK"),//closes stats menu
						   done =new JButton("Done");//closes options menu
	private static JFrame statistics= new JFrame("Statistics"),//holds stats
						  options=new JFrame("Options");//holds opts
	private static JLabel data,//used for stats menu
						  title;//used for options menu
	private static JPanel stats=new JPanel(),//used for stats menu
						  opts,//used for options menu
						  inputpanel;//for manually inputting ships
	private static Container b,c,d;//board and input panel 
	private JPanel input;//input bar	
	private static JMenuItem m,pvp,pvc,cvc;//menu items	
	private static String[] cletters = {" ","A","B","C","D","E","F","G","H","I","J"},
	//array of letters used for combo boxes
			    	 cnumbers = {" ","1","2","3","4","5","6","7","8","9","10"},
	//array of numbers used for combo boxes
					 ships = {"Carrier","Battleship","Submarine","Destroyer",
					 "Patrol Boat"},//strings used for ship combo box
					 direction = {"Horizontal","Vertical"},//directions
					 level={"Normal", "Ridiculously Hard"}, 
					 layout={"Manual","Automatic"},
					 colors={"Cyan", "Green", "Yellow", "Magenta", "Pink", "Red",
					 "White"},
					 first={"Player 1", "Player 2", "Random"};//used for options
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JComboBox cshi = new JComboBox(ships),//ships
					  cdir = new JComboBox(direction);//directions
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static JComboBox aiLevel=new JComboBox(level),
						     shipLayout=new JComboBox(layout),
							 shipColor=new JComboBox(colors),
							 playsFirst=new JComboBox(first);//used
					  			//for options menu
	private JTextField mbar = new JTextField();//message bar	
	private static int enemy=1,
				i,j,//counters							
				length=5,
				you=0,
				prevcolor=0,//index of previous color
				prevFirst=0,
				prevLayout=0,
				prevLevel=0,//tracks changes in corresponding comboboxes
				ready=0,
				sindex=0,//stores index of array
				dindex=0;//direction	
	private static Player players[]=new Player[2];
	private static JButton deploy=new JButton("DEPLOY");
	private static int w=0,a=0,s=0,t=0,e=0;//counters to track the use of all ships
	@SuppressWarnings("unused")
	private static String[][] shiphit=new String[10][10];
	private static String user,user2;
	private static Color[] color={Color.cyan,Color.green,Color.yellow,Color.magenta,
									Color.pink,	Color.red,	Color.white};		 	
	private static Object selectedValue=" ",
						  gametype;
	private static BattleshipClient me;
	private static boolean gameover=false;
	
	public Battleship()
	{	
		setTitle("Battleship");		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());
		setResizable(true);			
		
		//gets user to input name
		user=JOptionPane.showInputDialog("Enter your name.");		
		int dummy=0;
		while (((user==null)||(user.equals("")))&&(dummy<3))
		{				
			user=JOptionPane.showInputDialog("You have to input something.");
			if ((user!=null)&&(!user.equals("")))
				dummy=4;
			else
				dummy++;
		}
		if (dummy==3)
		{
			JOptionPane.showMessageDialog(null,"Since you're having trouble inp"
			+"utting your name, I'll just call you stupid.","",JOptionPane.INFORMATION_MESSAGE);
			user="Stupid";
		}
		players[you]=new Player (user);
		players[enemy]=new Player ("Computer");						
		b=getContentPane();		
		b.add(setBoard(you),BorderLayout.CENTER);			
		c=getContentPane();
		d = getContentPane();
		inputpanel=shipinput();
		d.add(inputpanel,BorderLayout.NORTH);			
		pack();		
		setVisible(true);
		
	}	
	
	public static boolean getGameOver()
	{
	 	return gameover;	  
	}

	public static void setGameOver(boolean b)
	{
	 	gameover=b;	  
	}
	
	//method to determine who plays first
	public void whoGoesFirst()
	{
		int x=0;
		if (playsFirst.getSelectedIndex()!=2)
		{
			if (playsFirst.getSelectedIndex()!=you)
				flipYou();	
			players[playsFirst.getSelectedIndex()].getTimer().start();
			x=playsFirst.getSelectedIndex();
		}
		else
		{		
			int rand=(int)(Math.random()*2);					
			JOptionPane.showMessageDialog(null,players[rand].getUser()+" will "
			+"go first.","",JOptionPane.PLAIN_MESSAGE);
			if (rand!=you)
				flipYou();	
			players[rand].getTimer().start();
			x=rand;
		}
		if
		((!players[x].getUser().equals("Computer"))||(!players[x].getUser().equals("CPU1"))||(!players[x].getUser().equals("CPU2")))
			players[x].setMove(true);
	}
	
	//returns ship color, as selected by the user
	public static Color getColor()
	{			
		return (color[shipColor.getSelectedIndex()]);	
	}
	
	//asks if two players are playing on the same computer or over the web
	public static boolean isLocal()
	{
		if ((gametype==pvp)&&(selectedValue.equals("Local")))
				return true;
		else
			return false;
	}
	
	
	public static void flipYou()
	{
		if (you==1)
		{	
			you=0;
			enemy=1;
		}
		else
		{
			you=1;
			enemy=0;
		}	
	}
	
	//determines whether or not is shipLayout is set to automatic
	public static boolean isAutoSet()
	{
		if (shipLayout.getSelectedIndex()==0)
			return false;
		else
			return true;
	}
	
	
	//variable that determines whether or not a carrier has been placed
	public static int getW()
	{
		return w;	
	}
	
	//variable that determines whether or not a battleship has been placed
	public static int getA()
	{
		return a;	
	}
	
	//variable that determines whether or not a submarine has been placed
	public static int getS()
	{
		return s;	
	}
	
	//variable that determines whether or not a destroyer has been placed
	public static int getT()
	{
		return t;	
	}
	
	//variable that determines whether or not a patrol boat has been placed
	public static int getE()
	{
		return e;	
	}		
	
	public static int getReady()
	{
		return ready;	
	}
	
	public static JFrame getStatistics()
	{
		return statistics;	
	}
	
	public static void setData(JLabel x)
	{
		data=x;	
	}
	
	public static JLabel getData()
	{
		return data;	
	}
	
	public static JPanel getStats()
	{
		return stats;	
	}	
	
	public static void setDeploy(boolean k)
	{
		deploy.setEnabled(k);	
	}	
	
	public static Player getPlayers(int x)
	{
		return players[x];	
	}
	
	public static String getDirection(int i)
	{
		return direction[i];	
	}
	
	public static String getCletters(int i)
	{
		return cletters[i];	
	}	
	
	public static String getShips(int i)
	{
		return ships[i];	
	}
	
	public static String getCnumbers(int i)
	{
		return cnumbers[i];	
	}	
	
	public static int getSIndex()
	{
		return sindex;	
	}
	
	public static int getDIndex()
	{
		return dindex;	
	}	
	
	public static int getYou()
	{
		return you;	
	}
	
	public static int getEnemy()
	{
		return enemy;	
	}	
	
	public static void setYou(int x)
	{
		you=x;	
	}
	
	public static void setEnemy(int x)
	{
		enemy=x;	
	}
	
	//creates Game menu and submenus
	public JMenuBar createMenuBar()
	{
		JMenu menu;//menu
      
		// create the menu bar
		JMenuBar menuBar = new JMenuBar();

		// build the Game menu
		menu = new JMenu("Game");
		menuBar.add(menu);
		m = new JMenu("New Game");		
		menu.add(m);
		
		//submenu of New Game
		GameListener stuff = new GameListener();
		pvp = new JMenuItem("Player vs. Player");		
		pvp.addActionListener(stuff);
		m.add(pvp);
		pvc = new JMenuItem("Player vs. Computer");
		pvc.addActionListener(stuff);
		m.add(pvc);
		cvc = new JMenuItem("Computer vs. Computer");
		cvc.addActionListener(stuff);
		m.add(cvc);
		
		m = new JMenuItem("Rules");
		m.addActionListener(new RulesListener());
		menu.add(m);
		m = new JMenuItem("Statistics");
		m.addActionListener(new StatsListener());		
		menu.add(m);
		m = new JMenuItem("Options");
		m.addActionListener(new OptionsListener());		
		menu.add(m);
		m = new JMenuItem("Exit");
		m.addActionListener(new ExitListener());
		menu.add(m);	
		return menuBar;
	}
	
	//creates panels that used to place ships
	public JPanel shipinput()
	{
		input= new JPanel();
		mbar.setText("Select a ship, its front position and direction.");
		mbar.setFont(new Font("Courier New", Font.BOLD, 14));
		mbar.setEditable(false);
		//input.add(mbar);
		cshi.setSelectedIndex(0);	
		cshi.addActionListener(new ShipsListener());
		TitledBorder title;//used for titles around combo boxes
		title = BorderFactory.createTitledBorder("Ships");
		cshi.setBorder(title);	
		input.add(cshi);		
		cdir.setSelectedIndex(0);	
		cdir.addActionListener(new DirectListener());	
		input.add(cdir);
		title = BorderFactory.createTitledBorder("Direction");
		cdir.setBorder(title);		
		deploy.setEnabled(false);
		deploy.addActionListener(new DeployListener());
		input.add(deploy);
		return input;
	}	
	
	//creates board for manual ship placement
	public JPanel setBoard(int n)
	{
		players[n].setMyBoard(new JPanel(new GridLayout(11,11)));//panel to store board		
		JTextField k;		
		for (i=0;i<11;i++)
		{			
			for (j=0;j<11;j++)
			{
				if ((j!=0)&&(i!=0))
				{					
					players[n].getBboard(i-1,j-1).addActionListener(new BoardListener());
					players[n].getMyBoard().add(players[n].getBboard(i-1,j-1));
				}				
				if (i==0)
				{				
					if (j!=0)
					{	
						//used to display row of numbers
						k= new JTextField(Battleship.getCnumbers(j));
						k.setEditable(false);
						k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					}									
					else 
					{	
						//used to display column of numbers
						k= new JTextField();
						k.setEditable(false);						
					}
					players[n].getMyBoard().add(k);
				}
				else if (j==0)					
				{	
					k= new JTextField(Battleship.getCletters(i));	
					k.setEditable(false);
					k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					players[n].getMyBoard().add(k);
				}				
			}
		}
		return players[n].getMyBoard();		
	}
	
	//creates board and automatically places ship
	public JPanel autoBoard(int u,int t) 
	{
		players[u].setGBoard(new JPanel(new GridLayout(11,11)));//panel to store board		
		JTextField k;	
		if (!players[u].getUser().equals("Unknown"))
			for (i=0;i<5;i++)
			{				
				players[u].setBoats(i,players[u].getBoats(i).compinput(i,u));
			}		
		for (i=0;i<11;i++)
		{			
			for (j=0;j<11;j++)
			{
				if ((j!=0)&&(i!=0))
				{								
					if ((players[u].getUser().equals("Computer"))||(isLocal()))						
					{						
						players[u].getBboard(i-1,j-1).addActionListener(new AttackListener());									
					}						
					else if
						((players[t].getUser().equals("Computer"))||(players[t].getUser().equals("CPU1"))||(players[t].getUser().equals("CPU2"))||(players[t].getUser().equals("Unknown")))			
					{
						if (players[u].getHitOrMiss(i-1,j-1))
							players[u].setBboard(i-1,j-1,getColor());						
					}
					else
					{
						players[u].getBboard(i-1,j-1).addActionListener(new InternetListener());		
					}
					players[u].getGBoard().add(players[u].getBboard(i-1,j-1));
				}				
				if (i==0)
				{				
					if (j!=0)
					{	
						//used to display row of numbers
						k= new JTextField(Battleship.getCnumbers(j));
						k.setEditable(false);
						k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					}									
					else 
					{	
						//used to display column of numbers
						k= new JTextField();
						k.setEditable(false);						
					}
					players[u].getGBoard().add(k);
				}
				else if (j==0)					
				{	
					k= new JTextField(Battleship.getCletters(i));	
					k.setEditable(false);
					k.setHorizontalAlignment((int)JFrame.CENTER_ALIGNMENT); 
					players[u].getGBoard().add(k);
				}				
			}			
		}
		return players[u].getGBoard();		
	}
	
	//Listener for combo boxes used to layout ships 
	private class ShipsListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{				
			sindex=cshi.getSelectedIndex();
			if (players[you].getBoats(sindex)!=null)
				cdir.setSelectedIndex(players[you].getBoats(sindex).getDirect());
			switch (sindex)
			{
				case 0:		length=5;
				break;
				case 1:		length=4;
				break;
				case 2:		length=3;	
				break;
				case 3:		length=3;
				break;
				case 4:		length=2;
				break;							
			}
			if (players[you].getBoats(sindex) != null)
			{
				Ship boat=new Ship(ships[sindex],players[you].getBoats(sindex).getDirect()
				,length,players[you].getBoats(sindex).getX(),players[you].getBoats(sindex).getY());		
				players[you].getBoats(sindex).clearship();
				players[you].setBoats(sindex,boat);
				players[you].getBoats(sindex).placeship();			
			}							
		}
	}			
			
	//Listener for the Direction combo box		
	private class DirectListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{						
			dindex = cdir.getSelectedIndex();					
			if (players[you].getBoats(sindex) != null)
			{
				Ship boat=new Ship(ships[sindex],dindex,players[you].getBoats(sindex).getLength(),
			   	players[you].getBoats(sindex).getX(),players[you].getBoats(sindex).getY());		   
				players[you].getBoats(sindex).clearship();
				players[you].setBoats(sindex,boat);
				players[you].getBoats(sindex).placeship();		   
			}						
		}
	}				
	
	//Listener for the buttons on the board		
	private class BoardListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{				
			if (ready==0)
			{
				if (players[you].getBoats(sindex)!=null)
					players[you].getBoats(sindex).clearship();
				Object source = v.getSource();
				outer:						
				for (i=0;i<10;i++)
				{				
					for (j=0;j<10;j++)
					{
						if (source==players[you].getBboard(i,j))
						{						
							switch (sindex)
							{
								case 0:	{											
											if (w==0)
												w++;														
										}
								break;						
								case 1:	{											
											if (a==0)
												a++;														
										}
								break;
								case 2:	{								
											if (s==0)								
												s++;
										}
								break;
								case 3:	{									
											if (t==0)
												t++;													
										}
							break;
							case 4:	{								
										if (e==0)
											e++;															
									}
							break;							
						}	
						players[you].setBoats(sindex,new Ship(ships[sindex],dindex,length,i,j));																									
						break outer;						
					}					
				}
			}			
			players[you].getBoats(sindex).placeship();
			}						
		}
    }
	
	//creates a panel that tells whose board is which
	private JPanel whoseBoard()
	{
		JPanel panel=new JPanel(new BorderLayout());
		panel.add(new JLabel(players[you].getUser()+"'s Board",SwingConstants.LEFT),BorderLayout.WEST);
		panel.add(new JLabel(players[enemy].getUser()+"'s Board",SwingConstants.RIGHT),BorderLayout.EAST);
		return panel;
	}
	
	//Listener for exit choice on Game menu	
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int r= JOptionPane.showConfirmDialog(null,"Are you sure you would l"
			+"ike to exit Battleship?", "Exit?", JOptionPane.YES_NO_OPTION);
			if (r==0)
				System.exit(0);	
		}	
	}

	//listener for New Game submenu		
	private class GameListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{	
			int q= JOptionPane.showConfirmDialog(null,"Are you sure you would l"
			+"ike to start a new game?", "New Game?", JOptionPane.YES_NO_OPTION);
			if (q==0)
			{					
				//resets variables
				b.removeAll();
				c.removeAll();
				d.removeAll();				
				you=0;
				enemy=1;
				ready=0;
				
				if (players[you].getTimer()!=null)
					if (players[you].getTimer().isRunning())
						players[you].getTimer().stop();
				if (players[enemy].getTimer()!=null)
					if (players[enemy].getTimer().isRunning())
						players[enemy].getTimer().stop();									
				
				gametype = e.getSource();			
			
				if (gametype==pvp)
				{
					if (!selectedValue.equals("no server"))
					{
						String[] possibleValues = { "Local", "Online"};
						selectedValue = JOptionPane.showInputDialog(null, 
						"Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null,
						possibleValues, possibleValues[0]);
					}
					if (!players[you].getUser().equals("CPU1"))
					{
						if (players[you].getUser().equals("Stupid"))
						{
							int w=JOptionPane.showConfirmDialog(null,"Would you"
							+" like to try inputting your name again?","",
							JOptionPane.YES_NO_OPTION);
							if (w==JOptionPane.YES_OPTION)
							{	
								user=JOptionPane.showInputDialog("Enter your name.");
								int dummy=0;
								while (((user==null)||(user.equals("")))&&(dummy<3))
								{				
									user=JOptionPane.showInputDialog("You have to input something.");
									if ((user!=null)&&(!user.equals("")))
										dummy=4;
									else
										dummy++;
								}
								if (dummy==3)
								{
									JOptionPane.showMessageDialog(null,"Still a"
									+"cting stupid.  Oh well, we'll run with it."
									,"",JOptionPane.INFORMATION_MESSAGE);
									user="Stupid";
								}
								else
									JOptionPane.showMessageDialog(null,"That wasn't"
									+" so hard now, was it?","YEAH!",
									JOptionPane.INFORMATION_MESSAGE);									
							}							
						}
						players[you]=new Player (players[you].getUser());
					}
					else									
						players[you]=new Player (user);								
					if (selectedValue.equals("Online"))
					{
						players[enemy]=new Player ("Unknown");
						if (!isAutoSet())
						{
							b.add(setBoard(you),BorderLayout.CENTER);							
							deploy.setEnabled(false);
							d.add(inputpanel,BorderLayout.NORTH);					
						}
						else
						{
							b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
							c.add(autoBoard(enemy,you),BorderLayout.EAST);
							ready=1;																
						}					
					}
					else
					{
						//gets user to input name
						if((players[enemy].getUser().equals("Computer"))||(players[enemy].getUser().equals("CPU2"))||(players[enemy].getUser().equals("Unknown")))
						{							
							user2=JOptionPane.showInputDialog("Enter your name.");					
							while ((user2==null)||(user2.equals("")))
							{				
								user2=JOptionPane.showInputDialog("You have to input something.");							
							}						
						}
						else
							user2=players[enemy].getUser();
						players[enemy]=new Player (user2);	
						b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
						c.add(autoBoard(enemy,you),BorderLayout.EAST);
						d.add(whoseBoard(),BorderLayout.NORTH);						
						whoGoesFirst();
						ready=1;
					}
					//ready=1;
				}
				else if (gametype==pvc)//Player vs Computer
				{						
					if (!players[you].getUser().equals("CPU1"))
					{
						if (players[you].getUser().equals("Stupid"))
						{
							int w=JOptionPane.showConfirmDialog(null,"Would you"
							+" like to try inputting your name again?","",
							JOptionPane.YES_NO_OPTION);
							if (w==JOptionPane.YES_OPTION)
							{	
								user=JOptionPane.showInputDialog("Enter your name.");
								int dummy=0;
								while (((user==null)||(user.equals("")))&&(dummy<3))
								{				
									user=JOptionPane.showInputDialog("You have to input something.");
									if ((user!=null)&&(!user.equals("")))
										dummy=4;
									else
										dummy++;
								}
								if (dummy==3)
								{
									JOptionPane.showMessageDialog(null,"Still a"
									+"cting stupid.  Oh well, we'll run with it."
									,"",JOptionPane.INFORMATION_MESSAGE);
									user="Stupid";
								}
								else
									JOptionPane.showMessageDialog(null,"That wasn't"
									+" so hard now, was it?","YEAH!",
									JOptionPane.INFORMATION_MESSAGE);									
							}							
						}
						players[you]=new Player (players[you].getUser());
					}
					else									
						players[you]=new Player (user);								
					players[enemy]=new Player ("Computer");			
					if (!isAutoSet())
					{
						b.add(setBoard(you),BorderLayout.CENTER);							
						deploy.setEnabled(false);
						d.add(inputpanel,BorderLayout.NORTH);					
					}
					else
					{
						b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
						c.add(autoBoard(enemy,you),BorderLayout.EAST);
						whoGoesFirst();	
					}
				}
				else if (gametype==cvc)//Computer vs Computer
				{										
					mbar.setText("Battleship Demo");					
					mbar.setEditable(false);					
					d.add(mbar,BorderLayout.NORTH);
					players[you]=new Player ("CPU1");
					players[enemy]=new Player ("CPU2");					
					b.add(autoBoard(you,enemy),BorderLayout.WEST);																				
					c.add(autoBoard(enemy,you),BorderLayout.EAST);					
					whoGoesFirst();		
				}				
				pack();		
				repaint();
			}									
		}	
	}	
	
	//Listener for Rules menu
	private class RulesListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{	
			
		}	
	}
	
	
	//Listener for ok button in statistics menu
	private class OkListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{	
			statistics.dispose();
		}	
	}
	
	//Listener for Stats menu
	private class StatsListener implements ActionListener
	{	
		//
		public void setup()
		{			
			stats=new JPanel();
			ok.addActionListener(new OkListener());		
			statistics.setSize(300,300);
			statistics.setResizable(true);
			statistics.getContentPane().add(ok,BorderLayout.SOUTH);
			//statistics.setLocation(700,200);				
		}	
		
		public void actionPerformed(ActionEvent e)
		{				
			if (data==null)
				setup();
			else
				stats.removeAll();
			stats.setLayout(new GridLayout(6,3));					
			data=new JLabel("");
			stats.add(data);
			data=new JLabel("Player 1",SwingConstants.CENTER);
			stats.add(data);
			data=new JLabel("Player 2",SwingConstants.CENTER);
			stats.add(data);				
			data=new JLabel("Names");
			stats.add(data);
			if (you == 0)
			{								
				data=new JLabel(players[you].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[enemy].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shots Taken");
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Hits");
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shot Accuracy");
				stats.add(data);
				data=new JLabel(players[you].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[enemy].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Ships Left");
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
			}		
			else 
			{					
				data=new JLabel(players[enemy].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[you].getUser(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shots Taken");
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShots()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Hits");
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getHits()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Shot Accuracy");
				stats.add(data);
				data=new JLabel(players[enemy].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(players[you].getAcc(),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel("Ships Left");
				stats.add(data);
				data=new JLabel(Integer.toString(players[enemy].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
				data=new JLabel(Integer.toString(players[you].getShipsLeft()),SwingConstants.CENTER);
				stats.add(data);
			}
			statistics.getContentPane().add(stats);			
			statistics.pack();
			statistics.setVisible(true);			
		}	
	}
	
	//Listener for Deploy Button 
	private class DeployListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{	
			int r= JOptionPane.showConfirmDialog(null,"Are you sure you would l"
			+"ike to deploy your ships?", "Deploy Ships?", 
			JOptionPane.YES_NO_OPTION);
			if (r==0)
			{	
				w=0;
				a=0;
				s=0;
				t=0;
				e=0;									
				d.remove(input);						
				b.add(players[you].getMyBoard(),BorderLayout.WEST);
				ready=1;	
				c.add(autoBoard(enemy,you),BorderLayout.EAST);													
				d.add(new JPanel(),BorderLayout.CENTER);
				if (!selectedValue.equals("Online"))
					whoGoesFirst();						
				pack();
				repaint();										
			}
		}	
	}

	//Listener for Options menu
	public class OptionsListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{		
			if (opts==null)
				setup();
			else
				options.setVisible(true);					
		}		
		
		public void setup()
		{			
			opts=new JPanel(new GridLayout(4,2));
			title=new JLabel("Computer AI");
			opts.add(title);			
			aiLevel.setSelectedIndex(0);			
			opts.add(aiLevel);
			title=new JLabel("Ship Layout");
			opts.add(title);			
			shipLayout.setSelectedIndex(0);			
			opts.add(shipLayout);
			title=new JLabel("Ship Color");
			opts.add(title);				
			shipColor.addActionListener(new SColorListener());
			shipColor.setSelectedIndex(0);	
			opts.add(shipColor);
			title=new JLabel("Who Plays First?");
			opts.add(title);			
			playsFirst.setSelectedIndex(0);			
			opts.add(playsFirst);		
			options.getContentPane().add(opts,BorderLayout.CENTER);
			options.setSize(600,800);
			options.setResizable(true);
			done.addActionListener(new DoneListener());		
			options.getContentPane().add(done,BorderLayout.SOUTH);
			options.setLocation(200,200);
			options.pack();
			options.setVisible(true);		
		}
		
		//Listener for the Colors combo box		
		private class SColorListener implements ActionListener
		{	
			public void actionPerformed(ActionEvent v)
			{	
				for (i=0;i<10;i++)
					for (j=0;j<10;j++)
					{
						if (players[you].getBboard(i,j).getBackground()==color[prevcolor])
							players[you].setBboard(i,j,color[shipColor.getSelectedIndex()]);				
						if (players[enemy].getBboard(i,j).getBackground()
							==color[prevcolor])
							players[enemy].setBboard(i,j,color[shipColor.getSelectedIndex()]);		
					}
				prevcolor=shipColor.getSelectedIndex();	
			}
		}	
		
		//Listener for ok button in statistics menu
		private class DoneListener implements ActionListener
		{	
			public void actionPerformed(ActionEvent e)
			{	
				if ((shipLayout.getSelectedIndex()!=prevLayout)||
					(aiLevel.getSelectedIndex()!=prevLevel)||
					(playsFirst.getSelectedIndex()!=prevFirst))
				{
					JOptionPane.showMessageDialog(null,"Changes will take"+
					" place at the start of a new game.",""
					,JOptionPane.PLAIN_MESSAGE);
					if (shipLayout.getSelectedIndex()!=prevLayout)
						prevLayout=shipLayout.getSelectedIndex();
					if (playsFirst.getSelectedIndex()!=prevFirst)
						prevFirst=playsFirst.getSelectedIndex();
					if (aiLevel.getSelectedIndex()!=prevLevel)
						prevLevel=aiLevel.getSelectedIndex();
				}
				options.dispose();
			}	
		}	
	}	
	
	public static BattleshipClient getClient()
	{
		return me;		
	}
	
	public static void main(String[] args){		
	
		Battleship gui= new Battleship();
		
		while (gui.isActive())
		{
			while (selectedValue.equals(" "))
				{	}
			System.out.println("xenophobia");
			System.out.println("Object = "+selectedValue);
			if (selectedValue.equals("Online"))
			{	
				selectedValue=" ";
				while (ready!=1)
				{ }			
				
				try
				{
					me=new BattleshipClient();
					if (!me.getServerName().equals("invalid"))
					{
						me.sendShips();
						while (!gameover)
						{
							if (!players[you].getMove())	
							{
								try
								{
									me.listen();							
								}
								catch (IOException e){ System.out.println("Aw naw."); }					
							}
							while (players[you].getMove())
								{ }
							me.results();
						}								
					}
					else
					{
						b.removeAll();
						c.removeAll();
						d.removeAll();
						players[you]=new Player (user);
						players[enemy]=new Player ("Computer");					
						b.add(gui.setBoard(you),BorderLayout.CENTER);					
						inputpanel=gui.shipinput();
						d.add(inputpanel,BorderLayout.NORTH);			
						gui.pack();		
						gui.repaint();
					}					
				}					
				catch (IOException e)
				{ System.out.println("You Suck"); }
			}			
		}		//System.out.println("okay");		
	}
}	
class Player
{
	private int hits;
	private int i,j;
	private int r,c;//row and column for comp attack	
	private Ship boats[] = new Ship[5];	
	private String user;//user name
	//private JPanel board;//panel to store game board
	private int shipsleft;
	private int shots;// shots taken
	private boolean[][] hitormiss=new boolean[10][10];
	private boolean chit=false;//checks if computer hit ship or not		
	private JButton[][] bboard = new JButton [10][10];
						//gbutton=new JButton [10][10];
	private int[][] mhs=new int[10][10];//used by computer to track miss(0)
	//, hit(1), or sunk(2); default is (3)
	private boolean move;
	private JPanel gboard,myboard;
	private Vector<Integer> rows=new Vector<Integer>();
	private Vector<Integer> cols=new Vector<Integer>();	
	private Timer timeleft;//
	private String[][] whatship=new String[10][10];//stores name of ships or " "
	private int go=2;//direction for the computer to look for ships
	private int fr,fc;//first hits made by computer					
	private int lastship;//length of the last ship left
	private NumberFormat nf = NumberFormat.getPercentInstance();
	//private Board games
		
	public Player(String name)
	{			
		user=name;
		shipsleft=5;
		lastship=0;	
				
		if
		((user.equals("Computer"))||(user.equals("CPU1"))||(user.equals("CPU2"))||(Battleship.isAutoSet())||(Battleship.isLocal()))
			for (i=0;i<5;i++)
				boats[i]=new Ship(Battleship.getShips(i),0,0,0,0);		
		if((user.equals("Computer"))||(user.equals("CPU1"))||(user.equals("CPU2")))
		{
			for (i=0;i<10;i++)
				for (j=0;j<10;j++)
					mhs[i][j]=3;						
			
			timeleft= new Timer(1000,new CompAttack());
		}
		else
			timeleft= new Timer(10000,new AttackListener());				
		move=false;
		shots=0;
		hits=0;
		for (i=0;i<10;i++)
		{			
			for (j=0;j<10;j++)
			{
				this.bboard[i][j]=new JButton();
				this.bboard[i][j].setBackground(null);				
				hitormiss[i][j]=false;
				this.whatship[i][j]=" ";				
			}
		}			
	}
	
	public void setUser(String m)
	{
		this.user=m;	
	}
	
	//returns player's game board with ap
	public JPanel getMyBoard()
	{
		return this.myboard;	
	}	
	
	//returns player's game board with ap
	public JPanel getGBoard()
	{
		return this.gboard;	
	}	
	
	public void setMyBoard(JPanel r)
	{
		this.myboard=r;		
	}

	public void setGBoard(JPanel r)
	{
		this.gboard=r;		
	}		
		
	public void setBoats(int i, Ship r)
	{
		this.boats[i]=r;		
	}	
	
	/*public void setGames(Board k)
	{
		this.games=k;
	}	
	
	public Board getGames()
	{
		return this.games;
	}*/
	
	public Ship getBoats(int x)
	{
		return this.boats[x];
	}
		
	public void setShots()
	{
		this.shots+=1;	
	}		
		
	public void setHits()
	{
		this.hits+=1;	
	}		
	
	public int getShots()
	{
		return this.shots;	
	}		
	
	public int getHits()
	{
		return this.hits;	
	}		
		
	public String getAcc()
	{
		if (this.getShots()>0)
			return nf.format(((double)(this.getHits())/(double)(this.getShots())));
		else
			return "";
	}		
		
	public Timer getTimer()
	{
		return timeleft;
	}
	
	public JButton getBboard(int i,int j)
	{
		return this.bboard[i][j];	
	}	
	
	public void setBboard(int i,int j, Color k)
	{
		this.bboard[i][j].setBackground(k);	
	}		
		
	public void setMove(boolean x)
	{
		this.move=x;
	}
		
	public boolean getMove()
	{
		return this.move;
	}
		
	//returns user name
	public String getUser()
	{
		return user;				
	}
	
	//checks if Statistics frame is open
	public static void isStatsOpen()
	{
		if (Battleship.getStats().isShowing())
		{	
			Battleship.getStats().removeAll();
			Battleship.getStats().setLayout(new GridLayout(6,3));					
			Battleship.setData(new JLabel(""));
			Battleship.getStats().add(Battleship.getData());
			Battleship.setData(new JLabel("Player 1",SwingConstants.CENTER));
			Battleship.getStats().add(Battleship.getData());
			Battleship.setData(new JLabel("Player 2",SwingConstants.CENTER));
			Battleship.getStats().add(Battleship.getData());				
			Battleship.setData(new JLabel("Names"));
			Battleship.getStats().add(Battleship.getData());
			if (Battleship.getYou() == 0)			
				resetStats(Battleship.getYou(),Battleship.getEnemy());			
			else 
				resetStats(Battleship.getEnemy(),Battleship.getYou());			
			Battleship.getStatistics().getContentPane().add(Battleship.getStats());
			Battleship.getStatistics().pack();
			Battleship.getStatistics().repaint();
		}				
	}	
			
	public static void resetStats(int x,int y)
	{
		Battleship.setData(new JLabel(Battleship.getPlayers(x).getUser(),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Battleship.getPlayers(y).getUser(),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel("Shots Taken"));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Integer.toString(Battleship.getPlayers(x).getShots()),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Integer.toString(Battleship.getPlayers(y).getShots()),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel("Hits"));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Integer.toString(Battleship.getPlayers(x).getHits()),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Integer.toString(Battleship.getPlayers(y).getHits()),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel("Shot Accuracy"));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Battleship.getPlayers(x).getAcc(),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Battleship.getPlayers(y).getAcc(),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel("Ships Left"));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Integer.toString(Battleship.getPlayers(x).getShipsLeft()),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());
		Battleship.setData(new JLabel(Integer.toString(Battleship.getPlayers(y).getShipsLeft()),SwingConstants.CENTER));
		Battleship.getStats().add(Battleship.getData());	
	}
	
	public String getWhatShip(int x,int y)
	{
		return this.whatship[x][y];			
	}		
	
	public boolean getChit()
	{
		return this.chit;	
	}
	
	public void setChit(boolean x)
	{
		this.chit=x;	
	}
	
	public void setFC(int x)
	{
		this.fc=x;	
	}
	
	public void setFR(int x)
	{
		this.fr=x;	
	}
	
	public void setC(int x)
	{
		this.c=x;	
	}
	
	public void setR(int x)
	{
		this.r=x;	
	}
	
	public int getGo()
	{
		return this.go;	
	}		
	
	//sets direction for comp to look(2=anywhere,1=horizontal,0=vertical)
	public void setGo(int x)
	{
		this.go=x;	
	}	
	
	//returns column of first hit
	public int getFC()
	{
		return this.fc;	
	}		
	
	//column 
	public int getC()
	{
		return this.c;	
	}	
	
	//returns row of first hit
	public int getFR()
	{
		return this.fr;	
	}		
	
	//row 
	public int getR()
	{
		return this.r;	
	}		
	
	public void setLastShip(int x)
	{
		this.lastship=x;	
	}
	
	public int getLastShip()
	{
		return this.lastship;	
	}
	
	public int getShipsLeft()
	{
		return this.shipsleft;	
	}
	
	public void setShipsLeft()
	{
		this.shipsleft-=1;			
	}	
	
	public void setWhatShip(int x,int y,String u)
	{
		this.whatship[x][y]=u;			
	}	
	
	public void setMHS(int x,int y,int z)
	{
		this.mhs[x][y]=z;			
	}
		
	public int getMHS(int x, int y)
	{
		return this.mhs[x][y];				
	}	
	
	//method that determines if hit ship is sunk or not
	public boolean isSunk(int x, int y)
	{
		int f=0;			
		
		//finds which ship was sunk
		while (!this.boats[f].getName().equals(this.getWhatShip(x,y)))
			f++;
		this.boats[f].setHitsLeft();		
		if (this.boats[f].getHitsLeft()==0)
		{
			Battleship.getPlayers(Battleship.getEnemy()).setShipsLeft();
			if
			((Battleship.getPlayers(Battleship.getYou()).getUser().equals("Computer"))||(Battleship.getPlayers(Battleship.getYou()).getUser().equals("CPU1"))||(Battleship.getPlayers(Battleship.getYou()).getUser().equals("CPU2")))
			{	
				for (int k=0;k<10;k++)
					for (int m=0;m<10;m++)
						if
				(this.boats[f].getName().equals(this.getWhatShip(k
							,m)))
						{
							Battleship.getPlayers(Battleship.getYou()).setMHS(k,m,2);
							this.setBboard(k,m,Color.black);
						}
				Battleship.getPlayers(Battleship.getYou()).setGo(2);				
				Battleship.getPlayers(Battleship.getYou()).setChit(false);
				if ((!this.getUser().equals("CPU1"))
					&&(!this.getUser().equals("CPU2")))
					JOptionPane.showMessageDialog(null,"You just lost your "+
					this.boats[f].getName()+"!","Ship Destroyed",
					JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You sank the "+
				this.boats[f].getName()+"!","Good Job!",
				JOptionPane.INFORMATION_MESSAGE);
				for (int k=0;k<10;k++)
					for (int m=0;m<10;m++)
						if(this.boats[f].getName().equals(this.getWhatShip(k
							,m)))
						{								
							this.setBboard(k,m,Color.black);
						}					
			}
			return true;
		}
		else
			return false;
	}
	
	//method that determines if hit ship is sunk or not
	public boolean isSunk(int x, int y, String z)
	{
		int f=0;			
		
		while (!z.equals(this.boats[f].getName()))
			f++;
		this.boats[f].setHitsLeft();
		System.out.println(z+":  "+this.boats[f].getHitsLeft());
		if (this.boats[f].getHitsLeft()==0)
		{
			this.setShipsLeft();
			JOptionPane.showMessageDialog(null,"You just lost your "+
			this.boats[f].getName()+"!","Ship Destroyed",
					JOptionPane.WARNING_MESSAGE);
			for (int k=0;k<10;k++)
				for (int m=0;m<10;m++)
					if(z.equals(this.getWhatShip(k,m)))
						this.setBboard(k,m,Color.black);											
			return true;
		}
		else
			return false;
	}

	//sets hitormiss[x][y] to k 				
	public void setHitOrMiss (int x, int y, boolean k)
	{
		this.hitormiss[x][y]=k;
	}
			
	public boolean getHitOrMiss (int x, int y)
	{
		return this.hitormiss[x][y];
	}
			
	//checks if any of the surrounding points are plausible
	public boolean isSurrounded(int x, int y)
	{
		if (this.isPlausible(x+1,y))				
			return false;		
		else if (this.isPlausible(x-1,y))
			return false;				
		else if (this.isPlausible(x,y+1))
			return false;	
		else if (this.isPlausible(x,y-1))
			return false;	
		else 
			return true;
	}
	
	//checks if shot is possible and hasn't been tried before
	public boolean isPlausible(int x, int y)
	{
		if ((isValid(x,y))&&(this.getMHS(x,y)==3))
			return true;
		else
			return false;
	}
	
	//checks if selected position is a plausible location for the remaining	ships
	public boolean rshipsv(int x,int y)
	{
		int u=0;
		int g=0;
					
	if (((isValid(x+1,y))&&((Battleship.getPlayers(Battleship.getYou()).getMHS(x+1,y)==3)||
							(Battleship.getPlayers(Battleship.getYou()).getMHS(x+1,y)==1)))||
			((isValid(x-1,y))&&((Battleship.getPlayers(Battleship.getYou()).getMHS(x-1,y)==3)||
							(Battleship.getPlayers(Battleship.getYou()).getMHS(x-1,y)==1))))		
			u=0;			
		else
			u=5;			
		found:			
		while (u<5)				
		{
			g=0;						
			if (this.boats[u].getHitsLeft()!=0)
			{						
				daloop:
				for (i=(x-(this.boats[u].getLength()));i<(x+(this.boats[u].getLength()));i++)
				{							
					if ((isValid(i,y))&&((Battleship.getPlayers(Battleship.getYou()).getMHS(i,y)==3)||
						(Battleship.getPlayers(Battleship.getYou()).getMHS(i,y)==1)))
					{
						g+=1;
						if (g==(this.boats[u].getLength()))
								
							break daloop;					
					}	
					else	
						g=0;
				}						
				if (g==(this.boats[u].getLength()))
					break found;
				else
				{
					u++;
					if (u==5)
						g=0;
				}											
			}
			else
				u++;				
		}				
		if (u!=5)
		{						
			return true;			
		}			
		else			
			return false;			
	}
		
	//checks if selected position is a plausible location for the remaining	ships
	public boolean rshipsh(int x,int y)
	{
		int u=0;
		int g=0;
					
		if (((isValid(x,y+1))&&((Battleship.getPlayers(Battleship.getYou()).getMHS(x,y+1)==3)||
							(Battleship.getPlayers(Battleship.getYou()).getMHS(x,y+1)==1)))||
			((isValid(x,y-1))&&((Battleship.getPlayers(Battleship.getYou()).getMHS(x,y-1)==3)||
							(Battleship.getPlayers(Battleship.getYou()).getMHS(x,y-1)==1))))		
			u=0;			
		else
			u=5;			
		alright:
		while (u<5)
		{
			g=0;			
			if (this.boats[u].getHitsLeft()!=0) 
			{	
				daloop:
				for (i=(y-this.boats[u].getLength());i<(y+this.boats[u].getLength());i++)
				{												
					if ((isValid(x,i))&&((Battleship.getPlayers(Battleship.getYou()).getMHS(x,i)==3)||
						(Battleship.getPlayers(Battleship.getYou()).getMHS(x,i)==1)))
					{
						g+=1;
						if (g==this.boats[u].getLength())
							break daloop;
					}	
					else
						g=0;
				}						
				if (g==(this.boats[u].getLength()))
					break alright;
				else
				{
					u++;
					if (u==5)
						g=0;
				}										
			}
			else
				u++;				
		}			
		if (u!=5)					
		{						
			return true;			
		}		
		else			
			return false;			
	}		
	
	//checks if point (x,y) is valid		
	public boolean isValid(int x, int y)
	{			
		if ((x<0)||(y<0)||(x>9)||(y>9))
			return false;	
		else
			return true;		
	}

	
	//used by computer to scan area around last hit			
	public void scanArea(int x, int y)
	{
		if (this.getGo()==2)
		{
			if (this.isPlausible(x,y+1))
				{
					if (!Battleship.getPlayers(Battleship.getEnemy()).rshipsh(x,y+1))
					{	
						this.setMHS(x,y+1,0);
						this.scanArea(x,y);
					}								
					else
						this.fireShot(x,y+1,1);					
				}			
			else if (this.isPlausible(x+1,y))
				{
					if (!Battleship.getPlayers(Battleship.getEnemy()).rshipsv(x+1,y))
					{	
						this.setMHS(x+1,y,0);
						this.scanArea(x,y);
					}							
					else
						this.fireShot(x+1,y,0);									
				}
			else if (this.isPlausible(x,y-1))
				{						
					if (!Battleship.getPlayers(Battleship.getEnemy()).rshipsh(x,y-1))
					{	
						this.setMHS(x,y-1,0);
						this.scanArea(x,y);
					}						
					else
						this.fireShot(x,y-1,1);											
				}
			else if (this.isPlausible(x-1,y))
				{						
					if (!Battleship.getPlayers(Battleship.getEnemy()).rshipsv(x-1,y))
					{	
						this.setMHS(x-1,y,0);
						this.scanArea(x,y);
					}						
					else
						this.fireShot(x-1,y,0);					
				}
		}
		else if (this.getGo()==1)//means that ship is horizontal
		{
			if (this.getChit())
			{
				if (this.isPlausible(x,y+1))
					this.fireShot(x,y+1);
				else if (this.isPlausible(x,y-1))						
					this.fireShot(x,y-1);
				else if (this.isPlausible(x,this.getFC()+1))
					this.fireShot(x,this.getFC()+1);
				else if (this.isPlausible(x,this.getFC()-1))
					this.fireShot(x,this.getFC()-1);
				else
				{
					this.setGo(2);
					this.scanArea(this.getFR(),this.getFC());
				}					
			}
			else 
			{
				if (this.isPlausible(x,y+1))
					this.fireShot(x,y+1);
				else if (this.isPlausible(x,y-1))
				{
					this.fireShot(x,y-1);
					if (!this.getChit())
						this.setGo(2);			
				}
				else if (this.isPlausible(x,this.getFC()+1))
				{
					this.fireShot(x,this.getFC()+1);
					if (!this.getChit())
						this.setGo(2);			
				}
				else if (this.isPlausible(x,this.getFC()-1))
				{
					this.fireShot(x,this.getFC()-1);
					if (!this.getChit())
						this.setGo(2);							
				}
				else
				{
					this.setGo(2);
					this.scanArea(this.getFR(),this.getFC());
				}						
			}
		}
		else if (go==0) //means that ship is vertical
		{
			if (this.getChit())
			{
				if (this.isPlausible(x+1,y))
					this.fireShot(x+1,y);					
				else if (this.isPlausible(x-1,y))
					this.fireShot(x-1,y);					
				else if (this.isPlausible(this.getFR()+1,y))						
					this.fireShot(this.getFR()+1,y);					
				else if (this.isPlausible(this.getFR()-1,y))						
					this.fireShot(this.getFR()-1,y);					
				else
				{
					this.setGo(2);
					this.scanArea(this.getFR(),this.getFC());
				}
			}
			else
			{
				if (this.isPlausible(x+1,y))
					this.fireShot(x+1,y);				
				else if (this.isPlausible(x-1,y))
				{
					this.fireShot(x-1,y);	
					if (!this.getChit())
						this.setGo(2);					
				}
				else if (this.isPlausible(this.getFR()+1,y))						
				{
					this.fireShot(this.getFR()+1,y);	
					if (!this.getChit())
						this.setGo(2);								
				}
				else if (this.isPlausible(this.getFR()-1,y))	
				{
					this.fireShot(this.getFR()-1,y);	
					if (!this.getChit())
						this.setGo(2);			
				}
				else
				{
					this.setGo(2);
					this.scanArea(this.getFR(),this.getFC());
				}
			}
		}
	}
	
	private void fireShot(int x, int y, int z)
	{
		this.takeShot(x,y);
		if (this.getChit())
		{
			this.setGo(z);
			this.setR(x);
			this.setC(y);							
		}	
	}
	
	private void fireShot(int x, int y)
	{
		this.takeShot(x,y);
		if (this.getChit())
		{			
			this.setR(x);
			this.setC(y);							
		}	
	}

		
	public void takeShot(int x,int y)
	{				
		this.setShots();
		if (Battleship.getPlayers(Battleship.getEnemy()).getHitOrMiss(x,y))
		{
			this.setHits();
			if (!Battleship.getPlayers(Battleship.getEnemy()).isSunk(x,y))
			{
				Battleship.getPlayers(Battleship.getEnemy()).setBboard(x,y,Color.orange);
				if ((this.getUser().equals("Computer"))||(this.getUser().equals("CPU1"))||(this.getUser().equals("CPU2")))
				{	
					this.setMHS(x,y,1);
					this.setChit(true);
				}										
			}											
		}
		else	
		{	
			Battleship.getPlayers(Battleship.getEnemy()).setBboard(x,y,Color.blue);
			if ((this.getUser().equals("Computer"))||(this.getUser().equals("CPU1"))||(this.getUser().equals("CPU2")))
			{	
				this.setMHS(x,y,0);
				this.setChit(false);
			}
		}					
	}		
	
	public void humanAttack(ActionEvent v)
	{
		if (this.getMove())
		{				
			Object source = v.getSource();
			outer:						
			for (i=0;i<10;i++)
			{				
				for (j=0;j<10;j++)
				{					
					if (source==Battleship.getPlayers(Battleship.getEnemy()).getBboard(i,j))
					{								
						if ((Battleship.getPlayers(Battleship.getEnemy()).getBboard(i,j).getBackground()==Color.black)||
							(Battleship.getPlayers(Battleship.getEnemy()).getBboard(i,j).getBackground()==Color.orange)||
							(Battleship.getPlayers(Battleship.getEnemy()).getBboard(i,j).getBackground()==Color.blue))
						{
							JOptionPane.showMessageDialog(null,"You tri"
							+"ed that spot already.","Wasted Shot",
							JOptionPane.ERROR_MESSAGE);								
						}
						else
							this.takeShot(i,j);								
						break outer;						
					}
					else if (source==this.getBboard(i,j))
					{
						JOptionPane.showMessageDialog(null,"You are not suppose"
						+"d to fire on your own board!","Lost Turn",
						JOptionPane.WARNING_MESSAGE);
						break outer;							
					}						
				}
			}
			
			if ((i==10)&&(j==10))
				JOptionPane.showMessageDialog(null,"You took too long!",
				"Lost Turn",JOptionPane.INFORMATION_MESSAGE);				
			Player.isStatsOpen();
			this.setMove(false);
			this.getTimer().stop();				
			if (Battleship.getPlayers(Battleship.getEnemy()).getShipsLeft()!=0)
			{						
				if (!Battleship.getPlayers(Battleship.getEnemy()).getUser().equals("Computer"))
					Battleship.getPlayers(Battleship.getEnemy()).setMove(true);					
				Battleship.getPlayers(Battleship.getEnemy()).getTimer().start();
				Battleship.flipYou();
			}
			else
			{
				if (Battleship.getPlayers(Battleship.getEnemy()).getUser().equals("Computer"))//change once
					//menu options work
				{
					JOptionPane.showMessageDialog(null,"YOU WON!",
					"It's A Celebration!",JOptionPane.INFORMATION_MESSAGE);
					if (this.getUser().equals("Stupid"))
						JOptionPane.showMessageDialog(null,"Maybe you're no"
						+"t that stupid after all!","",JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null,
					this.getUser()+" won!!!","It's A Celebration"
					+"!",JOptionPane.INFORMATION_MESSAGE);
					if (this.getUser().equals("Stupid"))
						JOptionPane.showMessageDialog(null,"Maybe you're no"
						+"t that stupid after all!","",JOptionPane.INFORMATION_MESSAGE);									
				}						
			}									
		}						
	}
	//Listener for the buttons on the board	while playing game		
	public class CompAttack implements ActionListener
	{	
		public void actionPerformed(ActionEvent v)
		{				
			Battleship.getPlayers(Battleship.getYou()).compattack();			
		}
	}
	
	public void compattack()
	{					
		if (this.getChit())
			this.scanArea(this.getR(),this.getC());					
		else
		{
			if (this.getGo()!=2)
				this.scanArea(this.getFR(),this.getFC());			
			else
			{
				blah:
				for (i=0;i<10;i++)
				{
					for (j=0;j<10;j++)
						if (this.getMHS(i,j)==1)
						{
							if (this.getMHS(this.getFR(),this.getFC())==2)
							{
								if ((this.isPlausible(i+1,j))||(this.isPlausible(i,j+1))
								||(this.isPlausible(i-1,j))||(this.isPlausible(i,j-1)))
								{
									this.scanArea(i,j);
									this.setFR(i);
									this.setFC(j);										
									break blah;
								}
							}
							else 
							{
								this.scanArea(this.getFR(),this.getFC());									
								break blah;																		
							}								
						}
				}
				if (i==10)
				{							
					do
					{									
						for (i=0;i<10;i++)
						{
							for (j=0;j<10;j++)
								if (this.getMHS(i,j)==3)
								{										
									rows.add(new Integer(i));
									break;
								}
						}									
						do
						{
							r=(int)(Math.random()*10);
						}
						while(r>=rows.size());												
						r=((Integer)rows.elementAt(r)).intValue();						
						for (i=0;i<10;i++)
						{
							if (this.getMHS(r,i)==3)
								cols.add(new Integer(i));														
						}											
						do
						{
							c=(int)(Math.random()*10);
						}
						while(c>=cols.size());											
						c=((Integer)cols.elementAt(c)).intValue();										
						if (this.isSurrounded(r,c))							
							this.setMHS(r,c,0);							
						else if ((!Battleship.getPlayers(Battleship.getEnemy()).rshipsh(r,c))
							&&(!Battleship.getPlayers(Battleship.getEnemy()).rshipsv(r,c)))
						{							
							this.setMHS(r,c,0);
						}															
						else if ((Battleship.getPlayers(Battleship.getEnemy()).getShipsLeft()==1)
							&&(this.getLastShip()==0))
						{								
							for (int i=0;i<5;i++)
								if (Battleship.getPlayers(Battleship.getEnemy()).boats[i].getHitsLeft()!=0)
									this.setLastShip(Battleship.getPlayers(Battleship.getEnemy()).boats[i].getLength());								
						}							
						rows.clear();
						cols.clear();
					}
					while(this.getMHS(r,c)!=3);						
					this.takeShot(r,c);
					if (this.getChit())
					{	
						this.setFR(r);	
						this.setFC(c);
					}					
				}
			}
		}
		isStatsOpen();
		this.getTimer().stop();
		if (Battleship.getPlayers(Battleship.getEnemy()).getShipsLeft()>0)
		{
			if ((!Battleship.getPlayers(Battleship.getEnemy()).getUser().equals("CPU1"))
				&&(!this.getUser().equals("CPU2")))
				Battleship.getPlayers(Battleship.getEnemy()).setMove(true);	
			Battleship.getPlayers(Battleship.getEnemy()).getTimer().start();
			Battleship.flipYou();
		}
		else
		{
			if (this.getUser().equals("Computer"))
			{
				JOptionPane.showMessageDialog(null,"You Lost!","Sorry!",
				JOptionPane.INFORMATION_MESSAGE);
				if (Battleship.getPlayers(Battleship.getEnemy()).getUser().equals("Stupid"))
					JOptionPane.showMessageDialog(null,"Stupid!","Sorry!",
					JOptionPane.INFORMATION_MESSAGE);
				for (i=0;i<10;i++)
				{
					for (j=0;j<10;j++)
					{
						if ((!this.getWhatShip(i,j).equals(" "))
							&&(((this.getBboard(i,j
						).getBackground())!=Color.black)&&
									((this.getBboard(i,j
								).getBackground())!=Color.orange)))							
							{
								this.setBboard(i,j,Battleship.getColor());
							}
					}
				}									
			}
			else
				JOptionPane.showMessageDialog(null,this.getUser()+
				" won!!!","It's A Celebration!",JOptionPane.INFORMATION_MESSAGE);			
		}					
	}	
}
class AttackListener implements ActionListener
{	
	int i,j;
	
	public void actionPerformed(ActionEvent v)
	{						
		Battleship.getPlayers(Battleship.getYou()).humanAttack(v);				
	}
}	
class Ship
{		
	private String name;
	private int dir=5,
			   length,				   
			   i,
			   j,				   
			   x1,
			   y1,
			   x2,
			   y2;
	private int hitsleft;
	private boolean invalid;
	
	public Ship(String n, int d, int ln, int x, int y)
	{
		name=n;
		length=ln;
		dir=d;
		x1=x;
		y1=y;
		invalid=false;
		hitsleft=ln;			
	}
	
	public Ship(String n, int d, int ln, int x, int y, int ex, int ey)
	{
		name=n;
		length=ln;
		dir=d;
		x1=x;
		y1=y;
		x2=ex;
		y2=ey;
		invalid=false;
		hitsleft=ln;			
	}
	
	public String getName()
	{
		return this.name;
	}		
	
	public int getLength()
	{
		return this.length;
	}
	
	public int getDirect()
	{
		return this.dir;
	}
	
	public int getX()
	{
		return this.x1;
	}
	
	public int getY()
	{
		return this.y1;
	}
	
	//returns the end x-point for this ship 
	public int getEndX()
	{
		return this.x2;
	}
	
	//returns the end y-point for this ship 
	public int getEndY()
	{
		return this.y2;
	}

	public void setInvalid(boolean c)
	{				
		this.invalid=c;
	}
			
	public void setHitsLeft()
	{				
		this.hitsleft-=1;
	}
	
	public int getHitsLeft()
	{				
		return this.hitsleft;
	}
	
	public void clearship ()
	{				
		switch (this.dir)
		{
			case 0:	{													
						if  (!this.invalid)
							for (j=this.y1;j<this.y2;j++)
							{
								Battleship.getPlayers(Battleship.getYou()).setBboard(this.x1,j,null);
								Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(this.x1,j,false);
								Battleship.getPlayers(Battleship.getYou()).setWhatShip(this.x1,j," ");	
							}
					}
			break;
			case 1:	{	
						if (!this.invalid)	
							for (i=this.x1;i<this.x2;i++)
							{
								Battleship.getPlayers(Battleship.getYou()).setBboard(i,this.y1,null);
								Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(i,this.y1,false);
								Battleship.getPlayers(Battleship.getYou()).setWhatShip(i,this.y1," ");	
							}								
					}
			break;				
		}
	}
	
	//Method to place the ships	
	public void placeship()
	{				
		switch (this.dir)
		{
			case 0:	{												
						if ((this.length+this.y1)>10)								
						{
							JOptionPane.showMessageDialog(null,"A "+
							this.name+" placed in a "+Battleship.getDirection(this.dir)+
							" direction will not fit at position "
							+Battleship.getCletters(this.x1+1)+Battleship.getCnumbers(this.y1+1)+".",
							"Invalid Placement",JOptionPane.ERROR_MESSAGE);
							this.invalid=true;
						}   								
						else
						{												
							j=0;
							while ((j!=this.length)&&(!Battleship.getPlayers(Battleship.getYou()).getHitOrMiss(this.x1,this.y1+j)))
								j++;
							if (j!=this.length)
							{
								JOptionPane.showMessageDialog(null,"Positio"
								+"n "+Battleship.getCletters(this.x1+1)+
								Battleship.getCnumbers(this.y1+j+1)+" is already occupied.",
								"Invalid Placement",JOptionPane.ERROR_MESSAGE);
								this.invalid=true;
							}
							else
							{
								this.x2=this.x1;
								this.y2=this.y1+this.length;								
								for (j=this.y1;j<this.y2;j++)
								{
									Battleship.getPlayers(Battleship.getYou()).setBboard(this.x1,j,Battleship.getColor());
									Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(this.x1,j,true);
									Battleship.getPlayers(Battleship.getYou()).setWhatShip(this.x1,j,this.name);										
								}
								this.invalid=false;
							}
						}
					}
			break;
			case 1:	{		
						if ((this.x1+this.length)>10)								
						{
							JOptionPane.showMessageDialog(null,"A "+
							this.name+" placed in a "+Battleship.getDirection(this.dir)+
							" direction will not fit at position "
							+Battleship.getCletters(this.x1+1)+Battleship.getCnumbers(this.y1+1)+".",
							"Invalid Placement",JOptionPane.ERROR_MESSAGE);
							this.invalid=true;
						}
						else
						{							
							j=0;
							while ((j!=this.length)
								&&(!Battleship.getPlayers(Battleship.getYou()).getHitOrMiss(this.x1+j,this.y1)))
								j++;
							if (j!=this.length)
							{
								JOptionPane.showMessageDialog(null,"Positio"
								+"n "+Battleship.getCletters(this.x1+j+1)+
								Battleship.getCnumbers(this.y1+1)+" is already occupied.",
								"Invalid Placement",JOptionPane.ERROR_MESSAGE);
								this.invalid=true;
							}
							else
							{
								this.y2=this.y1;
								this.x2=this.x1+this.length;										
								for (i=this.x1;i<this.x2;i++)
								{
									Battleship.getPlayers(Battleship.getYou()).setBboard(i,this.y1,Battleship.getColor());
									Battleship.getPlayers(Battleship.getYou()).setHitOrMiss(i,this.y1,true);
									Battleship.getPlayers(Battleship.getYou()).setWhatShip(i,this.y1,this.name);				
								}
								this.invalid=false;
							}
						}
					}
			break;							
		}			
		if ((Battleship.getW()>0)&&(Battleship.getA()>0)
			&&(Battleship.getS()>0)&&(Battleship.getT()>0)
		&&(Battleship.getE()>0)&&(!this.invalid))				
		{	
			if ((!Battleship.getPlayers(Battleship.getYou()).getBoats(0).invalid)&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(1).invalid)&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(2).invalid)
				&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(3).invalid)&&(!Battleship.getPlayers(Battleship.getYou()).getBoats(4).invalid))
				Battleship.setDeploy(true);
			else
				Battleship.setDeploy(false);
		}
		else
			Battleship.setDeploy(false);
	}
	
	public Ship compinput(int u, int n)
	{			
		Ship boat;
		
		int i=0,
			j=0,
			x,
			y,
			shipl=0,	
			dir;
		
		switch (u)
		{	
			case 0:		shipl=5;
			break;
			case 1:		shipl=4;
			break;
			case 2:			
			case 3:		shipl=3;
			break;
			case 4:		shipl=2;
			break;							
		}		
		
		do
		{
			x=(int)(Math.random()*10);
			y=(int)(Math.random()*10);				
			dir=(int)(Math.random()*2);//generates random direction within range			
			boat=new Ship(Battleship.getShips(u),dir,shipl,x,y);				
			switch (dir)
			{
				case 0:	{												
							if (((boat.getLength()+y)>10)||(x==0)||(y==0))								
								boat.setInvalid(true);																				
							else
							{												
								j=0;									
								while ((j!=boat.getLength())&&(!Battleship.getPlayers(n).getHitOrMiss(x,y+j)))
									j++;								
								if (j!=boat.getLength())
									boat.setInvalid(true);																		
								else
								{
									boat.x2=x;
									boat.y2=y+boat.getLength();								
									for (j=y;j<boat.y2;j++)
									{										
										Battleship.getPlayers(n).setHitOrMiss(x,j,true);
										Battleship.getPlayers(n).setWhatShip(x,j,Battleship.getShips(u));				
									}
									boat.setInvalid(false);																
								}
							}
						}
				break;
				case 1:	{		
							if (((x+boat.getLength())>10)||(x==0)||(y==0))						
								boat.setInvalid(true);							
							else
							{							
								j=0;									
								while ((j!=boat.getLength())&&(!Battleship.getPlayers(n).getHitOrMiss(x+j,y)))
									j++;
								if (j!=boat.getLength())
									boat.setInvalid(true);							
								else
								{
									boat.y2=y;
									boat.x2=x+boat.getLength();										
									for (i=x;i<boat.x2;i++)
									{
										Battleship.getPlayers(n).setHitOrMiss(i,y,true);
										Battleship.getPlayers(n).setWhatShip(i,y,Battleship.getShips(u));			
									}
									boat.setInvalid(false);									
								}
							}
						}
				break;						
			}			
		}			
		while (boat.invalid);		
		return boat;
	}
}
class BattleshipClient {   
	
	int i,j,//counter
		x,y,endp;//coordinates
	boolean gameover=false;
	String servername=" ";
	String start=" ";
	Socket bsSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
		
	public BattleshipClient () throws IOException {
		try {
			servername=JOptionPane.showInputDialog(null,"Input the name of"
			+" the server you wish to connect to.\n(ie. PC3873.princeton.edu)",
			"Server Name",JOptionPane.PLAIN_MESSAGE);
			if (servername==null)
				servername=" ";
			System.out.println("Server Name: "+servername);
            bsSocket = new Socket(servername, 4444);
            this.out = new PrintWriter(bsSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(bsSocket.getInputStream()));
			//System.out.println("Job");
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null,"Don't know about host: "
			+servername+".","Error",JOptionPane.WARNING_MESSAGE);
			servername="invalid";  
        } catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Couldn't get I/O for the connec"
			+"tion to: "+servername+".","Error",JOptionPane.WARNING_MESSAGE);
			servername="invalid";           
        }	
    }
	
	public void listen() throws IOException 
	{		
		while(!(start=this.in.readLine()).equals("play"))
		{			
			System.out.println("start ="+start);
			if (start.equals("getmove"))
				break;
			else if (start.equals("opponent"))
			{
				Battleship.getPlayers(1).setUser(in.readLine());	
			}				
			System.out.println("I'm still in the loop man.");
		}
		if (!start.equals("getmove"))			
		{
			Battleship.getPlayers(0).setMove(true);
			JOptionPane.showMessageDialog(null,Battleship.getPlayers(0).getUser()
			+" move.","",JOptionPane.INFORMATION_MESSAGE);
		}
		//Battleship.getPlayers(0).getTimer().start();
	}	
	
	public void results()
	{
		String results=null;
		
		try
		{
			while(!(results=in.readLine()).equals("results"))
			{	
				//System.out.println("I'm still in the loop man.");
			}
			results=in.readLine();
			System.out.println("results:  "+results);			
		}
		catch (IOException e){ System.out.println("Nothing's there.");}
		if (results.startsWith("miss"))
		{
			Battleship.getPlayers(1).setBboard(x,y,Color.blue);
			Battleship.getPlayers(0).setShots();
		}
		else if (results.startsWith("hit"))
		{
			Battleship.getPlayers(0).setHits();			
			results=results.substring(results.indexOf(" ")+1);			
			if (results.startsWith("shipsunk"))
			{
				Battleship.getPlayers(1).setShipsLeft();
				results=results.substring(results.indexOf(" ")+1);
				if (!results.startsWith("Patrol"))
					JOptionPane.showMessageDialog(null,"You sank the "+
					results.substring(0,results.indexOf(" "))+"!","Good Job!",				
					JOptionPane.INFORMATION_MESSAGE);
				else
				{
				  	JOptionPane.showMessageDialog(null,"You sank the Patrol Boa"
				  	+"t!","Good Job!",				
					JOptionPane.INFORMATION_MESSAGE);
					results=results.substring(results.indexOf(" ")+1);
				}
				results=results.substring(results.indexOf(" ")+1);
				x=Integer.parseInt(results.substring(0,1));
				y=Integer.parseInt(results.substring(2,3));
				endp=Integer.parseInt(results.substring(6,7));				
				if (Integer.parseInt(results.substring(4,5))==0)
				{					
					for (i=x;i<=endp;i++)
					{
						Battleship.getPlayers(1).setBboard(i,y,Color.black);
					}
				}
				else
				{
					for (i=y;i<=endp;i++)
					{
						Battleship.getPlayers(1).setBboard(x,i,Color.black);
					}					
				}
			}			
			else
			{			
				Battleship.getPlayers(1).setBboard(x,y,Color.orange);
			}
			Battleship.getPlayers(0).setShots();			
		}
		else if (results.startsWith("theirshot"))
		{
			results=results.substring(results.indexOf(" ")+1);
			System.out.println("results:  "+results);
			x=Integer.parseInt(results.substring(0,1));
			y=Integer.parseInt(results.substring(2,3));
			if (!Battleship.getPlayers(0).getWhatShip(x,y).equals(" "))
			{
				Battleship.getPlayers(1).setHits();
				if (!Battleship.getPlayers(0).isSunk(x,y
					,Battleship.getPlayers(0).getWhatShip(x,y)))
					Battleship.getPlayers(0).setBboard(x,y,Color.orange);
			}
			else
			  Battleship.getPlayers(0).setBboard(x,y,Color.blue);
			Battleship.getPlayers(1).setShots();				
		}
		else if (results.startsWith("wastedturn"))
		{			
			
		}		
		else if (results.startsWith("lostturn"))
		{
			JOptionPane.showMessageDialog(null,"You took too long!",
			"Lost Turn",JOptionPane.INFORMATION_MESSAGE);				
		}	
		try
		{
		  if (in.readLine().equals("gameover"))
		  {
		  		Battleship.setGameOver(true);
				if (Battleship.getPlayers(0).getShipsLeft()!=0)
					JOptionPane.showMessageDialog(null,"YOU WON!",
					"It's A Celebration!",JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"You Lost",
					"Sorry!",JOptionPane.INFORMATION_MESSAGE);
		  }
		}
		catch (IOException g){}
		Player.isStatsOpen();	
		
		System.out.println("results received");		
	}
	
	public String getServerName()
	{
		return servername;	
	}
		
	public void fireShot(int a, int b)
	{
		x=a;
		y=b;		
		out.println(x);
		out.println(y);
		System.out.println("shot fired:  "+Battleship.getCletters(x+1)+","+Battleship.getCnumbers(y+1));		
	}
	
	public void fireShot()
	{
		out.println("wastedturn");		
	}
	
	public void sendShips()
	{		
		out.println(Battleship.getPlayers(Battleship.getYou()).getUser());			
		for (i=0;i<10;i++)
			for (j=0;j<10;j++)
				out.println(Battleship.getPlayers(Battleship.getYou()).getWhatShip(i,j));		
	}
}
class InternetListener implements ActionListener
{	
	int i,j;
	
	public void actionPerformed(ActionEvent v)
	{						
		
		System.out.println(Battleship.getPlayers(0).getMove());
		if
		(Battleship.getPlayers(0).getMove())			
		{				
			System.out.println("WooHoo");
			Object source = v.getSource();
			outer:						
			for (i=0;i<10;i++)
			{				
				for (j=0;j<10;j++)
				{					
					if (source==Battleship.getPlayers(1).getBboard(i,j))
					{								
						if ((Battleship.getPlayers(1).getBboard(i,j).getBackground()==Color.black)||
							(Battleship.getPlayers(1).getBboard(i,j).getBackground()==Color.orange)||
							(Battleship.getPlayers(1).getBboard(i,j).getBackground()==Color.blue))
						{
							JOptionPane.showMessageDialog(null,"You tri"
							+"ed that spot already.","Wasted Shot",
							JOptionPane.ERROR_MESSAGE);
							Battleship.getClient().fireShot();								
						}
						else
						{
							Battleship.getClient().fireShot(i,j);							
						}
						break outer;						
					}
					else if (source==(Battleship.getPlayers(0).getBboard(i
						,j)))
					{
						JOptionPane.showMessageDialog(null,"You are not suppose"
						+"d to fire on your own board!","Lost Turn",
						JOptionPane.WARNING_MESSAGE);
						Battleship.getClient().fireShot();		
						break outer;							
					}			
				}
			}						
			Player.isStatsOpen();
			Battleship.getPlayers(0).setMove(false);													
		}
		else
		{
			if (!Battleship.getGameOver())
			{
				JOptionPane.showMessageDialog(null,"You cannot not play yet.",
				"Wait",JOptionPane.WARNING_MESSAGE);				
			}
			else
			{
				for (i=0;i<10;i++)
				{				
					for (j=0;j<10;j++)
					{
						Battleship.getPlayers(0).getBboard(i
						,j).setEnabled(false);
						Battleship.getPlayers(0).getBboard(i,j).setEnabled(false);
					}
				}
			}
		}								
	}
}	
