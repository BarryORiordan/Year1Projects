/* Project 4
   Student:  Barry O' Riordan - 13144278
*/
import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
public class Semester2Project4
{
	public static void main(String[] args) throws IOException
	{
		ArrayList<GameGenre>         genres = new ArrayList<GameGenre>();
		ArrayList<GameDeveloper> developers = new ArrayList<GameDeveloper>();
		ArrayList<GameDetail>         games = new ArrayList<GameDetail>();
		ArrayList<GameSale>           sales = new ArrayList<GameSale>();
  		File aFile;
		Scanner in;
		String filename1 = "GameGenres.txt";
		String filename2 = "GameDevelopers.txt";
		String filename3 = "GamesDetails.txt";
		String filename4 = "GamesSales.txt";
		String lineFromFile;
		String fileElements[];
		GameGenre     aGameGenre;
		GameDeveloper aGameDeveloper;
		GameDetail    aGameDetail;
		GameSale      aGameSale;
		aFile = new File(filename1);
		if(!aFile.exists())
			System.out.print(filename1 + " was not found.");
		else
		{
			in = new Scanner(aFile);
			while(in.hasNext())
			{
				lineFromFile = in.nextLine();
	 	 		fileElements = lineFromFile.split(",");
	 	 	 	aGameGenre   = new GameGenre(Integer.parseInt(fileElements[0]), fileElements[1]);
	 	 	  	genres.add(aGameGenre);
	 	 	}		
	  		in.close();
		}
		aFile = new File(filename2);
		if(!aFile.exists())
	  		System.out.print(filename2 + " was not found.");
		else
		{
			in = new Scanner(aFile);
	  		while(in.hasNext())
	  		{
	    		lineFromFile   = in.nextLine();
				fileElements   = lineFromFile.split(",");
	    		aGameDeveloper = new GameDeveloper(Integer.parseInt(fileElements[0]), fileElements[1]);		
	    		developers.add(aGameDeveloper);
      		}		
	  		in.close();
		}  
		aFile = new File(filename3);
		if(!aFile.exists())
	  		System.out.print(filename3 + " was not found.");
		else
		{
	  		in = new Scanner(aFile);
	  		while(in.hasNext())
	  		{
	    		lineFromFile = in.nextLine();
				fileElements = lineFromFile.split(",");
	    		aGameDetail  = new GameDetail(Integer.parseInt(fileElements[0]), fileElements[1], Integer.parseInt(fileElements[2]), Integer.parseInt(fileElements[3]),
									 Double.parseDouble(fileElements[4]));		
	    		games.add(aGameDetail);
      		}		
	  		in.close();
		}
		aFile = new File(filename4);
		if(!aFile.exists())
			System.out.print(filename4 + " was not found.");
		else
		{
			in = new Scanner(aFile);
			while(in.hasNext())
			{
				lineFromFile   = in.nextLine();
	 	 		fileElements   = lineFromFile.split(",");
				String date    = fileElements[0];
				String[] month = date.split("/");
	 	 	 	aGameSale = new GameSale(Integer.parseInt(fileElements[2]), Integer.parseInt(fileElements[1]), Integer.parseInt(month[1]));
	 	 	  	sales.add(aGameSale);
	 	 	}		
	  		in.close();
		}
		menu(genres,developers,games,sales);
	}

/*Menu Method
  Process: The user will choose an option from the Drop-down menu and the option selected will call the method related to the option.
  Output:  The menu will be outputted to the user to choose an option.
*/
	public static void menu(ArrayList<GameGenre> genres, ArrayList<GameDeveloper> developers, ArrayList<GameDetail> games, ArrayList<GameSale> sales) throws IOException
	{
		String which = "";
		boolean doNotQuit = true;
   		Object[] menuOptions = {"1) Obtain sales report for a game (for year to date)",
  					   	  		"2) Obtain sales report for a genre (for year to date)",
  					      		"3) Obtain total sales for each game (based on sales to date)",
  					      		"4) Obtain total sales for each genre (based on sales to date)"};
  		while(doNotQuit)
     	{
	  		Object selectedOption = JOptionPane.showInputDialog(null, "Please choose an option:", "Main Menu", 3, null, menuOptions, menuOptions[0]);
      		if(selectedOption == null)
				doNotQuit = areYouSure();
      		else if(selectedOption.equals(menuOptions[0]))
	       														salesByGame(games, sales);
      		else if(selectedOption.equals(menuOptions[1]))
	       														salesByGenre(games, sales, genres);
      		else if(selectedOption.equals(menuOptions[2]))
	       														totalGameSales(games, sales);
      		else if(selectedOption.equals(menuOptions[3]))
	      														totalGenreSales(games, sales, genres);
   		}
	}
		
/*Quit Prompt Method
  Process: The program will prompt to either continue or quit, then will display an are you sure box.
  Output:  A boolean variable will be returned, depending on the option chose, the boolean returned will either end the program or keep it running
*/
	public static boolean areYouSure() throws IOException
	{
		boolean quit;
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
		if(option == 0)			quit = false;
		else 					quit = true;
		return quit;
	}
	
/*Sales By Game Method
  Input:   The ArrayLists games and sales are passed into the method.
  Process: Gets the various values need from the ArrayLists and stores it in another ArrayList called gameSales.
			The three loops sorts the sales by Game and month and the totals for each.
  Output: It outputs each Game, their sales per month, annual total for each game and the annual total of all sales.
*/
	public static void salesByGame(ArrayList<GameDetail> games, ArrayList<GameSale> sales) throws IOException
	{
		double price = 0;
		String game  = "", monthString = "", result = "";
		ArrayList<String> gameSales    = new ArrayList<String>();
		ArrayList<String> results      = new ArrayList<String>();
		for(int i = 0; i < sales.size(); i++)
		{
			for(int j = 0; j < games.size(); j++)			//For loop for GameDetails.
			{
				if(games.get(j).getGameID() == sales.get(i).getGameID())
				{ 
					int currentGameID = games.get(j).getGameID();
					price = games.get(j).getGamePrice();
					for(int z = 0; z < games.size(); z++) 	//Loop for Game.
						if(games.get(z).getGameID() == currentGameID)
							game = games.get(z).getGameTitle();
				}
			}
			double monthlyTotal = price * sales.get(i).getSaleUnits();
			switch (sales.get(i).getMonthID())
			{
				case 1: monthString = "January" 			;break;
				case 2: monthString = "February" 			;break;
				case 3: monthString = "March" 				;break;
				case 4: monthString = "April" 				;break;
				case 5: monthString = "May" 				;break;
				case 6: monthString = "June"				;break;
				case 7: monthString = "July" 				;break;
				case 8: monthString = "August" 				;break;
				case 9: monthString = "September" 			;break;
				case 10: monthString = "October" 			;break;
				case 11: monthString = "November"			;break;
				case 12: monthString = "December" 	  		;break;
				default: monthString = "Invalid month" 		;break;
			} 
			gameSales.add(monthString + "," + game + "," + monthlyTotal );
		}
		ArrayList<String> revisedGames = new ArrayList<String>();
		for(int i = 0; i < gameSales.size(); i++)
		{
			String AllGenres   = "";
			String[] genreSort = gameSales.get(i).split(",");
			for(int j = i + 1; j < gameSales.size(); j++) 
			{
				String[] temp = gameSales.get(j).split(",");
				AllGenres    += temp[1];
			}
			if(!AllGenres.contains(genreSort[1]))
				revisedGames.add(genreSort[1]);
		}
		ArrayList<String> revisedMonthSales = new ArrayList<String>(); 	//Getting months and sales relative to each Game.
		for(int i = 0; i < revisedGames.size(); i++)
		{
			String games2 = revisedGames.get(i);
			String month  = "";
			for(int j = 0; j < gameSales.size(); j++)					//Going through gameSales once.
			{
				String[] games2Sort = gameSales.get(j).split(",");
				if(games2.equals(games2Sort[1]))
					month += games2Sort[0] + "/" + games2Sort[2] + ",";
			}
			revisedMonthSales.add(month);
		}
		double total = 0;						//Sorts the information and prints it to the screen.
		for(int i = 0; i < revisedGames.size(); i++)
		{
			String games3 = revisedGames.get(i);
			String [] eachMonth = revisedMonthSales.get(i).split(",");
			String amount = "", mnt = "";
			double gamesTotal = 0;
			result += games3 + ":\n";
			for(int j = 0; j < eachMonth.length; j++)
			{
				String[] monAmnt = eachMonth[j].split("/");
				amount = monAmnt[1];
				mnt    = monAmnt[0];
				total      += Double.parseDouble(monAmnt[1]);
				gamesTotal += Double.parseDouble(monAmnt[1]);
				for(int n = 1; n < monAmnt.length; n++)
					result += mnt + " - €" + amount + "\n";
			}
			result += "The total sales for the game \"" + games3 +"\" for the year was - €" + gamesTotal +"\n";
		} 
		result += "The total sales for this year is - €" + total;
		JOptionPane.showMessageDialog(null, result);
	}

/*Sales By Genre Method
  Input:   The ArrayLists games, sales and genres are passed into the method.
  Process: Gets the various values need from the ArrayLists and stores it in another ArrayList called genreSales.
			The three loops sorts the sales by Genre and month and the totals for each.
  Output: It outputs each Genre, their sales per month, annual total for each genre and the annual total of all sales.
*/
	public static void salesByGenre(ArrayList<GameDetail> games, ArrayList<GameSale> sales, ArrayList<GameGenre> genres)
	{
		double price = 0;
		String genre = "", monthString = "", result = ""; 
		ArrayList<String> genreSales = new ArrayList<String>();
		ArrayList<String> results    = new ArrayList<String>();
		
		for(int i = 0; i < sales.size(); i++)
		{
			for(int j = 0; j < games.size(); j++)				//For loop for GameDetails
			{
				if(games.get(j).getGameID() == sales.get(i).getGameID())
				{
					int	currentGenreID = games.get(j).getGenreID();
					price = games.get(j).getGamePrice();
					for(int z = 0; z < genres.size(); z++) 		//Loop for Genre. 
						if(genres.get(z).getGenreID() == currentGenreID)
							genre = genres.get(z).getGenreTitle();
				}
			}
			double monthlyTotal = price * sales.get(i).getSaleUnits();
			switch(sales.get(i).getMonthID())
			{
				case 1:  monthString = "January"			;break;
				case 2:  monthString = "February"			;break;
				case 3:  monthString = "March"				;break;
				case 4:  monthString = "April"				;break;
				case 5:  monthString = "May"				;break;
				case 6:  monthString = "June"				;break;
				case 7:  monthString = "July"				;break;
				case 8:  monthString = "August"				;break;
				case 9:  monthString = "September"			;break;
				case 10: monthString = "October"			;break;
				case 11: monthString = "November"			;break;
				case 12: monthString = "December"			;break;
				default: monthString = "Invalid month"		;break;
			}		 
			genreSales.add(monthString + "," + genre + "," + monthlyTotal );
		}
		
		ArrayList<String> revisedGenres = new ArrayList<String>();		//Loop that gets that gets the Genres that appear in multiple sales.
		for(int i = 0; i < genreSales.size(); i++)
		{
			String AllGenres   = "";
			String[] genreSort = genreSales.get(i).split(",");
			for(int j = i + 1; j < genreSales.size(); j++)	
			{
				String[] temp = genreSales.get(j).split(",");
				AllGenres    += temp[1];
			}
			if(!AllGenres.contains(genreSort[1]))
				revisedGenres.add(genreSort[1]);
		}
		ArrayList<String>  revisedMonthSales = new ArrayList<String>();	//Getting months and sales, relative to each Genre.
		for(int i = 0; i < revisedGenres.size(); i++)
		{
			String genra = revisedGenres.get(i);
			String month = "";
			for(int j = 0; j < genreSales.size(); j++)					//Going through Genre sales once.
			{
				String[] genraSort = genreSales.get(j).split(",");
				if(genra.equals(genraSort[1]))
					month += genraSort[0] + "/" + genraSort[2] + ",";
			}
			revisedMonthSales.add(month);
		}
		double total = 0;												//Sorts the information and prints it to the screen.
		
		for(int i = 0; i < revisedGenres.size(); i++)
		{
			String genra       = revisedGenres.get(i);
			String[] eachMonth = revisedMonthSales.get(i).split(",");
			String amount      = "", mnt = "";
			double genreTotal = 0;
			result += genra + ":\n";
			for(int j = 0; j < eachMonth.length; j++)
			{
				String[] monAmnt = eachMonth[j].split("/");
				amount = monAmnt[1];
				mnt    = monAmnt[0];
				total      += Double.parseDouble(monAmnt[1]);
				genreTotal += Double.parseDouble(monAmnt[1]);
				for(int n = 1; n < monAmnt.length; n++)
					result += mnt + " - €" + amount + "\n";
			}
			result += "The total sales in the genre \"" + genra +"\" for the year was - €" + genreTotal + "\n";
		}
		result += "The total sales for this year is - €" + total;
		JOptionPane.showMessageDialog(null, result, "Output", 1);
	}

/*Total Sales By Game Method
  Input:   The ArrayLists games and sales are passed into the method.
  Process: It gets the details/sales about the games from the ArrayLists and they are stored into a seperate ArrayList called gamesSales.
			The other for loops are then used for adding the unitss for a specific game together.
  Output: Prints to the screen, the name of the game, and the amount of units it sold to date.
*/
	public static void totalGameSales(ArrayList<GameDetail> games, ArrayList<GameSale> sales) throws IOException
	{
		String game = "", result = "";
		ArrayList<String> gameSales = new ArrayList<String>();
		ArrayList<String> results   = new ArrayList<String>();
		for(int i = 0; i < sales.size(); i++)
		{
			for(int j = 0; j < games.size(); j++)			//For loop for GameDetails
			{
				if(games.get(j).getGameID() == sales.get(i).getGameID())
				{	
					int	currentGameID = games.get(j).getGameID();
					for(int z = 0; z < games.size(); z++)	//Loop for Game
						if(games.get(z).getGameID() == currentGameID)
							game = games.get(z).getGameTitle();
				}
			}
			int dailySales = sales.get(i).getSaleUnits();
			gameSales.add(game + "," + dailySales );
		}
		ArrayList<String> revisedGames = new ArrayList<String>();
		for(int i = 0 ; i < gameSales.size() ; i++)
		{
			String AllGames   = "";
			String[] gameSort = gameSales.get(i).split(",");
			for(int j = i + 1; j < gameSales.size(); j++)	
			{
				String[] temp = gameSales.get(j).split(",");
				AllGames     += temp[0];
			}		
			if(!AllGames.contains(gameSort[0]))
				revisedGames.add(gameSort[0]);
		}
		for(int i = 0; i < revisedGames.size(); i++)
		{
			String game1   = revisedGames.get(i);
			int totalSales = 0;
			for(int j = 0; j < gameSales.size(); j++)		//Going through game sales once.
			{
				String[] game2Sort = gameSales.get(j).split(",");
				if(game1.equals(game2Sort[0]))
					totalSales += Integer.parseInt(game2Sort[1]);
			}
			result += "The total units sold for the game \"" + game1 + "\" is " + totalSales + "\n";
		}
		JOptionPane.showMessageDialog(null, result);
	}

/*Total Sales By Genre Method
  Input:   The ArrayLists games and sales are passed into the method.
  Process: It gets the details/sales about the games from the ArrayLists and they are stored into a seperate ArrayList called gamesSales.
			The other for loops are then used for adding the unitss for a specific game together.
  Output: Prints to the screen, the name of the game, and the amount of units it sold to date.
*/
	public static void totalGenreSales(ArrayList<GameDetail> games, ArrayList<GameSale> sales, ArrayList<GameGenre> genres) throws IOException
	{
		double price = 0;
		String genre = "", monthString = "";
		ArrayList<String> genreSales = new ArrayList<String>();
		ArrayList<String> results    = new ArrayList<String>();
		
		for(int i = 0; i < sales.size(); i++)
		{
			for(int j = 0; j < games.size(); j++)		//For loop for GameDetails.
			{
				if(games.get(j).getGameID() == sales.get(i).getGameID())
				{	
					int	currentGenreID = games.get(j).getGenreID();
					price = games.get(j).getGamePrice();
					for(int z =0 ; z < genres.size() ; z++)		//Loop for Genre .
						if(genres.get(z).getGenreID() == currentGenreID)
							genre = genres.get(z).getGenreTitle();
				}
			}
			int genreSalesTotal = sales.get(i).getSaleUnits();
			genreSales.add(genre + "," + genreSalesTotal );
		}
		ArrayList<String> revisedGenres = new ArrayList<String>();	//Loop that gets that gets the Genres that appear in multiple sales.
		for(int i = 0 ; i < genreSales.size() ; i++)
		{
			String AllGenres    = "";
			String[] genreSort  = genreSales.get(i).split(",");
			for(int j = i + 1; j < genreSales.size(); j++)	
			{
				String[] temp = genreSales.get(j).split(",");
				AllGenres    += temp[0];
			}
			if(!AllGenres.contains(genreSort[0]))
				revisedGenres.add(genreSort[0]);
		}
		String resultss = "";
		for(int i = 0; i < revisedGenres.size(); i++)
		{
			String game1 =  revisedGenres.get(i);
			int totalSales = 0;
			for(int j = 0; j < genreSales.size(); j++)		//Going through Genre sales once.
			{
				String[] genraSort = genreSales.get(j).split(",");
				if(game1.equals(genraSort[0]))
					totalSales += Integer.parseInt(genraSort[1]);
			}
			results.add("The total units sold for the genre \"" + game1 + "\" is " + totalSales);
		}
		for(int i = 0; i < results.size(); i++)
			resultss += results.get(i) + "\n";
		JOptionPane.showMessageDialog(null, resultss);
	}
}