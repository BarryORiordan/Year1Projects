/* Project 2
   Student:  Barry O' Riordan - 13144278
*/
import java.util.*;
import java.io.*;
import javax.swing.*;
public class Semester2Project2
{
	//All of the variables are initialised globally to be used throughout the program.
	public static File aFile;
	public static String userFile1 = "AllDivisionsTournamentsLeagues.txt";
	public static String nameOf, nameOf2;
	public static Object selectedTournament;
	public static int w, l, d;
	
/*Main Method
  Process: The user will choose an option from the Drop-down menu and the option selected will call the method related to the option.
  		   If option 1 is selected, 5 file names are created then the relevant methods to that option are called.
  		   If option 2 is selected, a dialog will ask the user to choose which League/Division/Tournament they wish to load.
  		   If there is, then it will ask them what the wish to do, then calling the appropriate methods.
  		   Otherwise an error message is displayed.
  Output:  The appropriate menus will be outputted to the user to choose options.
*/
	public static void main(String[] args) throws IOException
	{
		boolean doNotQuit = true;
		int howMany, numOfTeams;
		String nameOf = "", nameOf2 = "", LeaderBoardFileName, ResIndexFileName;
		String FixtureIndexFileName, FixtureFileName, ResultsFileName, TeamFileName = "", UpcomingFixFileName ,TeamsFileName ,nameOfFile;
   		Object[] menuOptions = {"1) Commence a new League/Division/Tournament",
  					   	  		"2) Load a current League/Division/Tournament",
  					     		"0) Exit"};
		while(doNotQuit)
     	{
	  		Object selectedOption = JOptionPane.showInputDialog(null, "Please choose an option:", "Menu", 1, null, menuOptions, menuOptions[0]);
      		if(selectedOption == null || selectedOption.equals(menuOptions[2]))
				doNotQuit = areYouSure();
			else if(selectedOption.equals(menuOptions[0]))
			{
				nameOf = AddTournament();
				FixtureIndexFileName = nameOf + " FixturesIndex.txt";
				FixtureFileName = nameOf + " Fixtures.txt";
				ResultsFileName = nameOf + " Results.txt";
				TeamsFileName = nameOf + " Teams.txt";
				UpcomingFixFileName = nameOf + " Upcoming Fixtures.txt";
				numOfTeams = getParticipants(TeamsFileName);
				howMany = onceOrTwice();
				getFixIndex(howMany, FixtureIndexFileName, numOfTeams);			  // Creates fixtures.
				getFixtures(FixtureFileName, TeamsFileName, FixtureIndexFileName);// Puts names instead of numbers.
				enterResult(ResultsFileName, FixtureFileName, UpcomingFixFileName);
			}
			else if(selectedOption.equals(menuOptions[1]))
			{
				File tempFile = new File(userFile1);
				if (tempFile.exists())
				{
					Object[] menuOptions3 = tournamentList().toArray();
					selectedTournament = JOptionPane.showInputDialog(null, "Please select an option:", "League/Division/Tournament", JOptionPane.QUESTION_MESSAGE, null, menuOptions3, menuOptions3[0]);
					String tournamentChoice = selectedTournament.toString();
					String parts[] = tournamentChoice.split(",");
					w = Integer.parseInt(parts[2]);
					d = Integer.parseInt(parts[3]);
					l = Integer.parseInt(parts[4]);
					nameOf2 = parts[1];
					FixtureIndexFileName = nameOf2 + " FixturesIndex.txt";
					FixtureFileName = nameOf2 + " Fixtures.txt";
					ResultsFileName = nameOf2 + " Results.txt";
					TeamsFileName = nameOf2 + " Teams.txt";				
					UpcomingFixFileName = nameOf2 + " Upcoming Fixtures.txt";
					LeaderBoardFileName = nameOf2 + " Table.txt";
					ResIndexFileName = nameOf2 + " ResIndex.txt";
					String header2 = "League/Division/Tournament Display";
					Object[] menuOptions2 = {"1) View outcomes of fixtures played",
											 "2) Enter outcome for a fixture",
											 "3) View list of fixtures yet to be played",
											 "4) View up-to-date leaderboard",
											 "0) Exit"};
					Object selectedOption2 = JOptionPane.showInputDialog(null, "Please choose an option:", header2, 1, null, menuOptions2, menuOptions2[0]);
					if(selectedOption2 == null || selectedOption2.equals(menuOptions2[4]))
						doNotQuit = areYouSure();
					else if(selectedOption2.equals(menuOptions2[0]))
						displayFile(ResultsFileName);
					else if(selectedOption2.equals(menuOptions2[1]))
						enterResult(ResultsFileName, FixtureFileName, UpcomingFixFileName);
					else if(selectedOption2.equals(menuOptions2[2]))
						displayFile(UpcomingFixFileName);
					else if(selectedOption2.equals(menuOptions2[3]))
					{
						getResIndex(ResultsFileName, ResIndexFileName);
						leaderboard(FixtureIndexFileName, ResIndexFileName, TeamsFileName, LeaderBoardFileName);
						displayFile(LeaderBoardFileName);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please create a League/Division/Tournament.");	
			}
     	}
	}

/*Tournament List Method
  Process: Reads the AllDivisionsTournamentsLeagues.txt file which contains all the tournaments created and the points for wins, loss, and draw.
           An array list is declared called tourneys. This will store the list of tournaments created.
		   The file is loaded into a scanner line by line, and using a while loop each of these lines are added into the array list. The scanner is then closed.
  Output:  It returns the tourneys array list, which is then used in the main method.
*/
	public static ArrayList<String> tournamentList() throws IOException
	{
		ArrayList<String> tourneys = new ArrayList<String>();
		File file1 = new File("AllDivisionsTournamentsLeagues.txt");
		Scanner scanner = new Scanner(file1);
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			tourneys.add(line);
		}
		scanner.close();
		return tourneys;
	}

/*Results Index Method
  Input:   2 Strings are passed into the method. They both denote names of individual files.
  Process: The method creates the resIndex file in the format fixture#,homeScore,awayScore.
  		   The results file is used to display the results to the end user for them to read correctly.
  		   This is created to be used in the leaderboard.
  Output:  The results are stored in resIndexFileName in the right format to be read to the leaderboard.
*/
	public static void getResIndex(String ResultsFileName,String resIndexFileName) throws IOException
	{
		String[] resPart;
		String prnt, res;
		File results = new File(ResultsFileName);
		FileWriter aFileWriter = new FileWriter(resIndexFileName);
		PrintWriter outResIndex = new PrintWriter(aFileWriter);
		Scanner resReader = new Scanner(results);
		while(resReader.hasNext())
		{
			res = resReader.nextLine();
			resPart = res.split(",");
			resPart[0] = resPart[0].replaceAll("[^\\d]","");
			resPart[1] = resPart[1].replaceAll("[^\\d]","");
			resPart[2] = resPart[2].replaceAll("[^\\d]","");
			prnt = resPart[0] + "," + resPart[1] + "," + resPart[2];
			outResIndex.println(prnt);
		}
		outResIndex.close();
		aFileWriter.close();
		resReader.close();
}

/*Leaderboard Method
  Input: The FixtureIndex, Results, and Teams files are loaded in and passed to a scanner. 
  Process: 1. An arrayList is declared, and the Teams are added to it numerically,
              and the main 2D array is created based on the amount of teams entered.
		   2. The teams file is read line by line, and the home team, and away team are
		      converted from strings to ints and stored as int variables. The same
              process happens to the results file. 
		   3. The array reads the home score, and the home goals for each fixture line by line, 
		      and increments the specific section on the 2D array. The same process happens for 
			  the away team, and away goals. 
		   4. Using the goals data loaded in, a series of if statements will read various spots
			  on the 2D array and use this information to increment the rest of the spots on the 
			  array.
		   5. The main array is sorted numerically.
		   6. The leaderboard is printed to a file in the format we want to display it in.
  Output: The leaderboard is displayed in a JFrame, along with a legend of the team names.
*/
	public static void leaderboard(String FixtureIndexFileName, String resIndexFileName, String TeamsFileName, String LeaderBoardFileName) throws IOException
	{
		String[] parts3 = {};
		int T1 = 0, T2 = 0, T3 = 0, T4 = 0;
		File file1 = new File(FixtureIndexFileName);
		File file2 = new File(resIndexFileName); 
		File file3 = new File(TeamsFileName);
		Scanner scanner = new Scanner(file1);
		Scanner scanner2 = new Scanner(file2);
		Scanner scanner3 = new Scanner(file3);
		ArrayList<String> teams = new ArrayList<String>(); //Array with team names.
		while (scanner3.hasNextLine())
		{
			String line3 = scanner3.nextLine();
			parts3 = line3.split(",");
			teams.add(parts3[1]);
		}
		String header = "Team" + "\t" + "GP" + "\t" + "HW" + "\t" + "HD" + "\t"
				 + "HL" + "\t" + "GF" + "\t" + "GA" + "\t" + "AW" + "\t" + "AD"
				 + "\t" + "AL" + "\t" + "GF" + "\t" + "GA" + "\t" + "GD" + "\t"
				 + "TP" + "\t";
		int[][] array = new int[teams.size()][14];
		while (scanner2.hasNextLine())
		{
			String line = scanner.nextLine();
			String line2 = scanner2.nextLine();
			String[] parts = line.split(",");
			String[] parts2 = line2.split(",");
			T1 = Integer.parseInt(parts[1]); 	//Gets the home team and converts to an int.
			T2 = Integer.parseInt(parts[2]); 	//Gets away team and converts to an int.
			T3 = Integer.parseInt(parts2[1]);	//Gets goals scored by home team and converts to int.
			T4 = Integer.parseInt(parts2[2]);	//Gets goals scored by away team and converts to int.

			array[T1 - 1][1]++; 				//Increments games played.
			array[T2 - 1][1]++;
			
			array[T1 - 1][5] += T3; 			//Home foals for.
			array[T2 - 1][10] += T4;			//Away goals for.

			array[T1 - 1][6] += T4; 			//Home goals against.
			array[T2 - 1][11] += T3;			//Away goals against.

			if (T3 > T4)
			{
				array[T1 - 1][2]++; 			//Home wins.
				array[T2 - 1][9]++; 			//Away losses.
			}
			if (T3 < T4)
			{
				array[T2 - 1][7]++; 			//Away wins.
				array[T1 - 1][4]++; 			//Home losses.
			}
			if (T3 == T4)
			{
				array[T1 - 1][3]++;
				array[T2 - 1][8]++;
			}

			for (int i = 0; i < teams.size(); i++)
			{
				array[i][13] = ((array[i][2] + array[i][7]) * w) + (array[i][3] + array[i][8]) * d;  //Total points.	
				array[i][12] = (array[i][5] + array[i][10]) - (array[i][6] + array[i][11]); 		 //Goal difference.
				array[i][0] = i + 1;
			}
		}
		{
			int[][] temp = new int[array.length][array[0].length];
			boolean finished = false;
			while (!finished)
			{
				finished = true;
				for (int i = 0; i < array.length - 1; i++)
					if (array[i][13] < array[i + 1][13])
					{
						for (int j = 0; j < array[i].length; j++)
						{
							temp[i][j] = array[i][j];
							array[i][j] = array[i + 1][j];
							array[i + 1][j] = temp[i][j];
						}
						finished = false;
					}
			}
		}
		scanner.close();
		scanner2.close();
		scanner3.close();
		FileWriter aFileWriter = new FileWriter(LeaderBoardFileName);
		PrintWriter outTable = new PrintWriter(aFileWriter);
		outTable.println(header);
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[i].length; j++)
				outTable.print(array[i][j] + "\t");
			outTable.println();
		}
		outTable.print("******** Legend for team names *********" + "\n");							//Code for the legend.
		for (int i = 0; i < teams.size(); i++)
			outTable.print("Team " + (i + 1) + ": " + teams.get(i) + "\n");
		outTable.close();
		aFileWriter.close();
	}

/*Quit Prompt Method
  Process: The program will prompt to either continue or quit, then will display an are you sure box.
  Output:  A boolean variable will be returned, depending on the option chose, the boolean returned will either end the program or keep it running
*/
	public static boolean areYouSure() throws IOException
	{
		boolean quit;
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the program?", "Quit?", JOptionPane.YES_NO_OPTION);
		if(option == 0)
			quit = false;
		else 
			quit = true;
		return quit;
	}

/*Add Tournament Method
  Process: The program gets the name for the League/Division/Tournament.
  		   Any spaces in the input are trimmed out.
  		   The user will then be asked to input points given for a win, draw and loss.
  		   An error is displayed if the wrong input is entered.
  		   If AllDivisionsTournamentsLeagues.txt doesn't exist, it calls createFile().
  		   If it already exists then appendToFile() is called.
  Output:  The trimmed name of the League/Division/Tournament is returned back to main().
*/
	public static String AddTournament() throws IOException
	{
		String nameOfTournament = JOptionPane.showInputDialog(null, "Enter name of League/Division/Tournament: ");
		String nameOf = nameOfTournament.replaceAll(" ", "");
		aFile = new File(userFile1);
		int win = 0, draw = 0, loss = 0;
		boolean isValid = true;
		while(isValid)
		{
			try
			{
				win  = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter win points:  "));
				draw = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter draw points: "));
				loss = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter loss points: "));
				isValid = false;
			}
			catch(Exception anError)
			{
				JOptionPane.showMessageDialog(null, "Error. Please enter a value of integer data type.");
			}
		}
		if(!aFile.exists())
			createFile(userFile1, nameOfTournament, win , draw, loss);
		else
			appendToFile(userFile1, nameOfTournament, win , draw, loss);
		return nameOf;
	}
	
/*Create File Method
  Input:   The name of the tournament and the points given are passed into the method.
  Process: It creates the file and stores the name of the tournament, the win points, the draw points, the loss points into the file.
  Output:  The file is created ("1,Name,wPoints,dPoints,lPoints").
*/
	public static void createFile(String filename, String nameOfTournament, int w, int d, int l) throws IOException
	{
		PrintWriter output = new PrintWriter(aFile);
		output.println(1 + "," + nameOfTournament + "," + w + "," + d + "," + l);
		output.close();
	}

/*Update File Method
  Input:   The name of the tournament and the points given are passed into the method.
  Process: The method splits what's stored in the file to check if the tournament name inputted matches an existing tournament name.
  		   If so, a dialog will be displayed.
  		   Otherwise, the int count, the name and the points given are entered into the file.
  Output:  The file is updated ("count,Name,wPoints,dPoints,lPoints").
*/
	public static void appendToFile(String filename, String nameOfTournament, int w, int d, int l) throws IOException
	{
		Scanner in = new Scanner(aFile);
		String aLineFromFile;
		String[] elements;
		boolean found = false;
		int count = 0;
		while(in.hasNext())
		{
			aLineFromFile = in.nextLine();
			elements = aLineFromFile.split(",");
			if(nameOfTournament.equalsIgnoreCase(elements[1]))
				found = true;
			count++;
		}
		in.close();
		if(found)
			JOptionPane.showMessageDialog(null, "A tournament by this name already exists.\nPlease enter a unique tournament name.");
		else
		{
			FileWriter aFileWriter = new FileWriter(filename, true);
			PrintWriter output = new PrintWriter(aFileWriter);
			output.println(++count + "," + nameOfTournament + "," + w + "," + d + "," + l);
			output.close();
			aFileWriter.close();
		}
	}

/*Get Participants Method
  Input:   The name of file denoting where the names will be stored is passed into the method.
  Process: The user is asked to enter the amount of participants.
  		   If any input is invalid, an error is shown.
  		   They are then requested to enter each participant name.
  		   The team name then is formatted have the first character in Upper-case and the rest in Lower-case.
  		   The data is inputted to the file in the format: TeamNumber,TeamName.
  Output:  The method reurns the number of particpants to main().
*/
	public static int getParticipants(String teamsFileName) throws IOException
	{
		boolean isValid = false;
		FileWriter aFileWriter = new FileWriter(teamsFileName);
		PrintWriter out = new PrintWriter(aFileWriter);
		String team = "";
		int numberOf = 0;
		while(!isValid)
		{
			try
			{
				numberOf = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of teams: "));
				if(numberOf < 4 || numberOf > 32)
					JOptionPane.showMessageDialog(null, "Please enter a number of teams in the range 4 to 32.");
				else
					isValid = true;
			}
			catch(Exception exception)
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid input for the number of teams.");
			}
		}
		for(int i = 1; i <= numberOf; i++)
		{
			boolean isTeam = true;
			while(isTeam)
			{
				team = JOptionPane.showInputDialog(null, "Enter team number " + i + ": ");
				if(team == null || team.equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a team name for team number " + i + ".");
				else
					isTeam = false;
			}
			String output = team.substring(0, 1).toUpperCase();
        	if(team.length() > 1)
            	output += team.substring(1,team.length()).toLowerCase();
        	out.println(i + "," + output);
		}
		out.close();
		aFileWriter.close();
		return numberOf;
	}
	
/*Play Once Or Twice Method
  Process: The user is asked to enter if they wish for each team to play each other Once or Home & Away.
  		   Whatever option is chosen, howMany is set to the value of how many games each team will have to play.
  Output:  The method returns howMany to the method which called it.
*/
	public static int onceOrTwice() throws IOException
	{
		int howMany = 0, numberOf = 0;
		Object[] menuOptions2 = {"1) Once",
  							   	 "2) Home and Away"};
	  	Object selectedOption2 = JOptionPane.showInputDialog(null, "Please choose the Play Frequency:", "Choose an option", 1, null, menuOptions2, menuOptions2[0]);
	  	if(selectedOption2.equals(menuOptions2[0]))
	  		howMany = numberOf - 1;
	  	else if(selectedOption2.equals(menuOptions2[1]))
	  		howMany = (numberOf - 1) * 2;
	  	return howMany;
	}

/*Get Fixture Index
  Input:   The Number of teams, the file IndexFileName is passed as a String, and how many times they play each other as in howMany.
  Process: This method creates the Fixtures for the tournament. It uses a 2D array to keep a size and location for the fixtures.
		   Each individual row is a round and each individual column is a match. the fixtures then get sorted accordingly, for instance 
		   if it finds that a team has every game away/home, it will update itself to remove this issue. Also if teams have a number of
		   consecutive games home/away, they get updated accordingly. Once it is finished sorting the 2D array is read through, index by
		   index and added into the file IndexFile.
  Output:  The output is the file Fixindex which has been updated by the method.
*/
	public static void getFixIndex(int howMany, String IndexFileName, int numOfTeams) throws IOException
	{
		int fixNo = 1, rnd, matchNo, numOfRnds, matchsPerRnd, homeTeamNo, awayTeamNo, odd, even;
		String fixture, fixAsText;
		String[] elementsOfFixtures;
		numOfRnds = (numOfTeams - 1);
		matchsPerRnd = (numOfTeams / 2);
		String [] [] fixtures = new String [numOfRnds][matchsPerRnd];
		for(rnd = 0 ; rnd < numOfRnds ; rnd++)
			for(matchNo = 0 ; matchNo < matchsPerRnd ; matchNo++)
			{
				homeTeamNo = ((rnd + matchNo) % (numOfTeams - 1));
				awayTeamNo = (((numOfTeams - 1)- matchNo + rnd) % (numOfTeams - 1));
				if(matchNo == 0)
					awayTeamNo = (numOfTeams - 1);
				fixtures[rnd][matchNo] = ((homeTeamNo + 1) + "," + (awayTeamNo + 1));
			}
		String [] [] fixRevised = new String [numOfRnds][matchsPerRnd];
		even = 0;
		odd = (numOfTeams / 2); 
		for(int i = 0 ; i < fixtures.length ; i++)			//Sorts team having consecutive home matches by swapping rounds.
		{
			if(i % 2 == 0)
				fixRevised[i] = fixtures[even++];
			else 
				fixRevised[i] = fixtures[odd++];
		}
		fixtures = fixRevised;
		for(rnd = 0 ; rnd < fixtures.length ; rnd++)		//Gives the team that have too many away matches even.
		{
			if(rnd % 2 == 1)
			{
				fixAsText = fixtures[rnd][0];
				elementsOfFixtures = fixAsText.split(",");
				fixtures[rnd][0] = elementsOfFixtures[1] + "," + elementsOfFixtures [0];
			}
		}
		FileWriter aFileWriter = new FileWriter(IndexFileName);
		PrintWriter out = new PrintWriter(aFileWriter);
		for(rnd = 0 ; rnd < numOfRnds ; rnd++)
			for(matchNo = 0 ; matchNo < matchsPerRnd ; matchNo++)
				out.println( fixNo++ + "," + fixtures[rnd][matchNo]);
		
		if(howMany == -2)
			for(rnd = 0 ; rnd < numOfRnds ; rnd++)
				for(matchNo = 0 ; matchNo < matchsPerRnd ; matchNo++)
					out.println( fixNo++ + "," + fixtures[rnd][matchNo]);
		out.close();
		aFileWriter.close();
	}

/*Get Fixtures Method
  Input:   Takes 3 String variables containing 3 file names. 
  Process: Reads from the FixIndexFile and the TeamFile which are both CSV file and uses data from them files to create a new file called fixtures.
		   The file that is created is a list of fixtures using team names instead of number. This file is then used through out to display fixtures.
		   This method is works with the system of naming each file in the main method.
  Output:  Creates a fixtures file with team names.
*/
	public static void getFixtures(String FixtureFileName ,String TeamsFileName, String FixIndexFileName) throws IOException
	{
		String [] names    = new String [2];
		String [] fixArray = new String [3];
		String [] team12   = new String [3];
		String temp, teamFix, fixFileName;
		int fixNo = 1;
		File fixIndexFile = new File(FixIndexFileName);
		File teamsFile = new File(TeamsFileName);
		
		FileWriter aFileWriter = new FileWriter(FixtureFileName);
		PrintWriter out = new PrintWriter(aFileWriter);
		Scanner fixRead = new Scanner(fixIndexFile);
		while(fixRead.hasNext())
		{
			temp = fixRead.nextLine();
			team12 = temp.split(",");
			fixArray = temp.split(",");
			
			Scanner teamRead = new Scanner(teamsFile);
			while(teamRead.hasNext())
			{
				names = teamRead.nextLine().split(",");
				if(names[0].equals(fixArray[1]))
					team12[1] = names[1];	
				else if(names[0].equals(fixArray[2]))
					team12[2] = names[1];
			}
			teamRead.close();
			teamFix = (team12[0]) + ", " + team12[1] + " Vs " + team12[2];
			out.println(teamFix);
		}	
		fixRead.close();
		out.close();
		aFileWriter.close();
	}

/*Enter Result Method
  Input:   Takes in the names of three files. Reads from "fixFile". Then depending on the entry state either writes, reads or adds to the other two.
  Process: The first loop gets the number of fixtures, and the number of results(if it exists) and uses them to exit the loops appropriately.
  	       The user is promoted to enter results. If the upComingfixtureFile does not exist it is created the first time results are entered for that league.
  		   If the upComingfixtureFile exists it reads from that file, taking off where the user stopped. 
  		   In both cases the results file is being added to using the users input for the score.
  		   Here the result file is in words, so a method is used to create a fixtures index which the leaderboard method can read results from.
  Output:  Creates and appends two files. One containing results and the other containing the fixtures left to play.
*/
	public static void enterResult(String ResultsFileName, String fixFileName ,String upComingfixFile) throws IOException
	{
		String fixture, result, fixResult;
		String currentFix = "", fixMsg = "";
		String [] lastFixNo = new String [2];
		String [] NoOfFix;
		String [] NoOfRes;
		String pattern = "[\\d]+/[\\d]+";
		String msg = "Enter score in the form of HomeScore/AwayScore.\nEnter the result for this fixture.\n";
		int numOfRes = 0, upComSrt = 0, resNo = 0, fixNo = 0, times = 1, lastResNo = 0;
		
		File fixFile = new File (fixFileName);
		File resFile = new File (ResultsFileName);
		File upComFile = new File (upComingfixFile);
		if(resFile.exists())
		{
			Scanner fixCheck = new Scanner(fixFile);
			Scanner resCheck = new Scanner(resFile);
			while(fixCheck.hasNext())
			{
				NoOfFix = fixCheck.nextLine().split(",");
				while(resCheck.hasNext())
				{
					NoOfRes = resCheck.nextLine().split(",");
					resNo = Integer.parseInt(NoOfRes[0]);
					if(resNo > lastResNo)	
						lastResNo = resNo;
				}
				fixNo = Integer.parseInt(NoOfFix[0]);
				System.out.println(lastResNo + " " + fixNo);
			}
			fixCheck.close();
			resCheck.close();
		}
		upComSrt = (fixNo - (fixNo - resNo));
		if(!(upComFile.exists()))
		{
			FileWriter aFileWriter = new FileWriter(ResultsFileName);
			PrintWriter outRes = new PrintWriter(aFileWriter);
			Scanner fixReader = new Scanner(fixFile);
			boolean fileHas = true;
			while(fileHas && times < fixNo)
			{
				fileHas = fixReader.hasNext();
				fixture = fixReader.nextLine();
				currentFix = fixture ;
				while(fixture == currentFix)
				{
					fixMsg = msg + currentFix;
					result = JOptionPane.showInputDialog(null, fixMsg, "Enter Results", 3);
					if(result == null)
					{	
						fileHas = false;
						currentFix = "";
					}
					else if(!result.matches(pattern))
					{
						JOptionPane.showMessageDialog(null, "The wrong format was entered, please try again.");
						currentFix = fixture;	
					}
					else
					{
						result = result.replace("/",",");
						fixResult = fixture.replaceAll("Vs","("+result+")");
						outRes.println(fixResult);
						currentFix = "";
						numOfRes++;
					}	
				}
				times++;
			}
			fixReader.close();
			outRes.close();
			aFileWriter.close();
		}
		else
		{
			FileWriter aFileWriter = new FileWriter(ResultsFileName,true); 
			PrintWriter outRes = new PrintWriter(aFileWriter);
			Scanner upComReader = new Scanner(upComFile);
			boolean fileHas = true;
			while(fileHas && upComSrt < fixNo)
			{
				fileHas = upComReader.hasNext();
				fixture = upComReader.nextLine();
				currentFix = fixture ;
				while(fixture == currentFix)
				{
					fixMsg = msg + currentFix;
					result = JOptionPane.showInputDialog(null, fixMsg, "Enter Results", 3);
					if(result == null)
					{	
						fileHas = false;
						currentFix = "";
					}
					else if(!result.matches(pattern))
					{
						JOptionPane.showMessageDialog(null, "The wrong format was entered, please try again.");
						currentFix = fixture;	
					}
					else
					{
						result = result.replace("/", ",");
						fixResult = fixture.replaceAll("Vs", "(" + result + ")");
						outRes.println(fixResult);
						currentFix = "";
						lastFixNo = fixture.split(",");
						numOfRes = Integer.parseInt(lastFixNo[0]);
					}	
				}
				upComSrt++;
			}
			upComReader.close();
			outRes.close();
			aFileWriter.close();
		}
		String [] fixNum = new String [2];						// This is the code that makes the upComingfixtureFile
		String fixToPlay;
		
		Scanner fixReader1 = new Scanner(fixFile);
		FileWriter aFileWriter1 = new FileWriter(upComingfixFile);
		PrintWriter outUpCom = new PrintWriter(aFileWriter1);
		while(fixReader1.hasNext())
		{
			fixToPlay = fixReader1.nextLine();
			fixNum = fixToPlay.split(",");
			 if(Integer.parseInt(fixNum[0]) > numOfRes)
				 outUpCom.println(fixToPlay);
		}
		outUpCom.close();
		aFileWriter1.close();
		fixReader1.close();
	}
		
/*Display File Method
  Input:   The name of the file as a String.
  Process: File gets it's connection to memory so that it can be read from. If the file is valid a JFrame will be created.
		   Then the file is read line by line and printed onto the screen. If the file is invalid an appropriate message is displayed.
  Output:  The file is displayed to the enduser, if file is invalid, an error message will be displayed.
*/
	public static void displayFile(String fileName) throws IOException
	{
		String lineFromFile, title;
		title = fileName.replaceAll(".txt", "");
		File afile = new File(fileName);
		Scanner in = new Scanner(afile);
		if(in.hasNext())
		{
			JTextArea textArea = new JTextArea(5, 20);
			textArea.setEditable(false);
			while(in.hasNext())
			{
				lineFromFile = in.nextLine() + "\n";
				textArea.append(lineFromFile);
			}
			in.close();
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.getVerticalScrollBar();
			JFrame f = new JFrame();
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(1250, 600);
			f.setLocationRelativeTo(null);
			f.setTitle(title);
			f.add(scrollPane);
		}
		else
				JOptionPane.showMessageDialog(null, "\tNo data found.\nPlease update fixture results.");	
	}
}

