/* Project 3
   Student:  Barry O' Riordan - 13144278	  
*/
import java.io.*;
import java.util.*;
import java.text.*;
public class Semester2Project3
{
	//The variable is initialised globally to be used throughout the program.
	public static File aFile;
	
/*Main Method
  Process: The user will input what command line arguments they wish to enter.
  		    If the input is valid the correct method relating to the option they chose will be called.
  		    If invalid input is entered, approriate error messages will be displayed.
  Output:  If the user's input is invalid, accompanying the error message will be a set of examples to show the user what the desired input is.
*/
	public static void main(String [] args)throws IOException
	{
		String options = "\njava Semester2Project3 AA Lisbon LIS\n- add a new airport with the airport name of Lisbon and the airport code of LIS (if an airport with this code does not already exist)";
		options += "\n\njava Semester2Project3 EA BHD Belfast\n- edit the name of an airport if it exists - in this case, change the name of the airport with the code BHD to Belfast";
		options += "\n\njava Semester2Project3 DA SNN\n- delete entry in the Airports.txt file if it exists and also delete any entries in Flights.txt if they exist";
		options += "\n\njava Semester2Project3 EF EI3240 -TWTF-- 1/5/2015 31/10/2015\n- edit flight details for EI3240 changing the days and the times to those specified here as command-line arguments";
		options += "\n\njava Semester2Project3 DF EI3240\n- delete flight details for flight number EI3240";
		options += "\n\njava Semester2Project3 SF DUB SNN\n- display details of all flights departing from Dublin and arriving in Shannon";
		options += "\n\njava Semester2Project3 SD DUB SNN 1/12/2015\n- display details of all flights departing from Dublin on 1/12/2014 and arriving in Shannon";
		String filename = "Airports.txt";
		aFile = new File(filename);
		String AirportCode = "";
		String flightNumber = "";
		
		if(args.length == 0 || args[0].equals(null))
			System.out.println("\nPlease enter valid command line arguments as described below:\n" + options);
		else if(args[0].equals("AA"))
		{
			if(!aFile.exists())
				createFile(args[1], args[2]);
			else
				appendToFile(filename, args[1], args[2]);
		}
		else if(args[0].equals("EA"))
		{
			String x = args[0], y = args[1], z = args[2];
			editAirport(x, y, z);
		}
		else if(args[0].equals("DA"))
		{
			AirportCode = args[1];
			deleteAirport(AirportCode);
		}
		else if(args[0].equals("EF"))
		{
			String v1 = args[0], w1 = args[1], x1 = args[2], y1 = args[3], z1 = args[4];
			editFlight(v1, w1, x1, y1, z1);
		}
		else if(args[0].equals("DF"))
		{
			flightNumber = args[0];
			deleteFlight(flightNumber);
		}
		else if(args[0].equals("SF"))
		{
			searchFlightPorts(args[1], args[2]);
		}
		else if(args[0].equals("SD"))
		{
			searchFlightDate(args[1], args[2], args[3]);
		}
		else if(args.length < 2 || args.length > 5)
			System.out.println("\nPlease enter a minimum 2 command line arguments and a maximum of 5. For example:\n" + options);
		else
			System.out.println("\nPlease enter valid command line argument as described below:\n" + options);
	}
	
/*Create File Method
  Input:   The name of the airport and the airport code are passed in.
  Process: It creates the file and stores the airport name and the airport code into the file.
  Output:  The file is created ("AirportName,AirportCode").
*/
	public static void createFile(String airportName, String airportCode) throws IOException
	{
		PrintWriter output = new PrintWriter(aFile);
		output.println(airportName + "," + airportCode);
		output.close();
	}
	
/*Update File Method
  Input:   The name of the file, the airport name and the airport code are passed into the method.
  Process: The method splits what's stored in the file to check if the airport code inputted matches an existing airport code.
  		    If so, a message will be displayed.
  		    Otherwise, the airport name and the airport code are entered into the file.
  Output:  The file is updated ("AirportName,AirportCode").
*/
	public static void appendToFile(String filename, String airportName, String airportCode) throws IOException
	{
		Scanner in = new Scanner(aFile);
		String aLineFromFile;
		String[] elements;
		boolean found = false;
		while(in.hasNext())
		{
			aLineFromFile = in.nextLine();
			elements = aLineFromFile.split(",");
			if(airportCode.equalsIgnoreCase(elements[1]))
				found = true;
		}
		in.close();
		if(found)
			System.out.println("An airport code by this name already exists.\nPlease enter a unique airport code.");
		else
		{
			FileWriter aFileWriter = new FileWriter(filename, true);
			PrintWriter output = new PrintWriter(aFileWriter);
			output.println(airportName + "," + airportCode);
			output.close();
			aFileWriter.close();
		}
	}

/*Edit Airport Method
  Input:   The first 3 command-line arguments are passed in as Strings.
  Process: Checks if file exists, displays message if not. Checks if airport code matches a pattern (All alphabetic characters), if not displays message.
  		    Reads a line from the file, puts it into an array, checks if code is already used, and if name of airport is in the file.
  		    Displays messages should name not be found or if code is already used.
  		    Creates an arraylist which will store the name,code. a line is read from the file, if the name equals the name of the name of the airport
  		    that is to be edited, the new code replaces the old one and is added to arraylist, else it is just added.
  		    Arraylist gets sorted in ascending sequence.
  		    File gets updated with the arraylist
  Output:  The file gets updated by the Arraylist
*/
	public static void editAirport(String x, String y, String z) throws IOException
	{
		String pattern = "[A-Z]{3}";
		String line, line1, line2;
		String[] parts, parts1, parts2;
		boolean found1 = false, found2 = false;
		File file = new File("Airports.txt"); //I created the file using the examples in the outline.
		Scanner in = new Scanner(file);
		Scanner in1 = new Scanner(file);
		Scanner in2 = new Scanner(file);
		if(!(file.exists())) //If "Airports.txt" doesn't exist end program.
			System.out.println("Airports.txt does not exist");
		else
		{
			if(!(y.matches(pattern)))
				System.out.println("Airport Code is invalid");
			
			while(in.hasNext())
			{
				line = in.nextLine();
				parts = line.split(",");
				if(y.equalsIgnoreCase(parts[1]))
					found1 = true;	//Checking if Airport code already is in use.
				if(z.equalsIgnoreCase(parts[0]))
					found2 = true;  //Checking if Airport name is in the file.
			}
			if(found1)
				System.out.println("Airport Code already exists, Enter a different one.");
			else if(found2 = false)
				System.out.println("Airport Name not found. Enter it again.");	
			else
			{
				/*
				Creating the ArrayList to store the name,code.
				1st while adds the names and coses to arraylist.
				Checks if the name of the airport that is being edited is in the line.
				Then it adds the new code onto the name.
				Sorting the ArrayList.
				2nd for/while is printing the ArrayList into the file.
				*/
				ArrayList<String> airport = new ArrayList<String>();
				while(in1.hasNext()) //1st while.
				{
					line1 = in1.nextLine();
					if(line1.contains(z))
					{
						parts1 = line1.split(",");
						parts1[1] = y;
						airport.add(parts1[0] + "," + parts1[1]);
					}
					else
						airport.add(line1);
				}
				Collections.sort(airport); //Sorts ArrayList.
				PrintWriter output = new PrintWriter(file);
				for(int i = 0; i < airport.size(); i++)
				{
					output.println(airport.get(i));
				}
				output.close();	
			}	
		}
	}

/*Delete Airport Method
  Input:   The airport source code
  Process: Reads through both files and rewrites the files, filtering out any line with the matching airport code.
  Output:  returns nothing. But it does alter a pre existing file.
*/
	public static void deleteAirport(String airportCode) throws IOException
	{
		String airPortCode, aline,pattern;
		pattern = "[A-Z]{3}";
		if(airportCode.matches(pattern))
		{
			ArrayList cleanLines = new ArrayList<String>();
			File airports = new File("Airports.txt");
			File flights = new File("Flights.txt");
			Scanner readPorts = new Scanner(airports);		//For deleting airport.
			while(readPorts.hasNext())
			{
				aline = readPorts.nextLine();
				if(aline.indexOf(airportCode) == -1)
					cleanLines.add(aline);
			}
			readPorts.close();
			if(!cleanLines.isEmpty())
			{
				FileWriter writer = new FileWriter(airports);
				PrintWriter outPort = new PrintWriter(writer);
				for( int i = 0; i <= cleanLines.size() - 1; i++)
				{
					outPort.println(cleanLines.get(i));
				}
				outPort.close();
				writer.close();
			}
			cleanLines.clear();								//For deleting flight.
			Scanner readFlights = new Scanner(flights);
			while(readFlights.hasNext())
			{
				aline = readFlights.nextLine();
				if(aline.indexOf(airportCode) == -1)
					cleanLines.add(aline);
			}
			readFlights.close();
			if(cleanLines.isEmpty())
			{
				FileWriter writer2 = new FileWriter(flights);
				PrintWriter outFlights = new PrintWriter(writer2);
				for(int i = 0; i<=cleanLines.size()-1 ; i++)
					outFlights.println("");					//Code for what needs to happen when no flights are left.
				outFlights.close();
				writer2.close();
			}
			else
			{
				FileWriter writer2 = new FileWriter(flights);
				PrintWriter outFlights = new PrintWriter(writer2);
				for(int i = 0 ; i <= cleanLines.size() - 1; i++)
				{
					outFlights.println(cleanLines.get(i));
				}
				outFlights.close();
				writer2.close();
			}
		}
		else 
		{
			System.out.println("The airport source code entered is of the wrong format");
		}
	}

/* Delete Flight Method
  Input:   The code of the flight to be deleted.
  Process: Reads through the flights.txt file and filters out the flight with the matching code. 
			Then rewrites the flights file with the lines not containing the flight code.
  Output:  No output. But alters the flights file.
*/
	public static void deleteFlight(String flightCode) throws IOException
	{
		String flight,pattern;
		pattern = "[A-Z]{2}[0-9]{4}";
		
		if(flightCode.matches(pattern))
		{
			boolean notFound = true;
			ArrayList newFlights = new ArrayList<String>();
			
			File flights = new File("Flights.txt");
			Scanner flightReader = new Scanner(flights);
			while(flightReader.hasNext())
			{
				flight = flightReader.nextLine();
				if(flight.indexOf(flightCode) == -1)
					newFlights.add(flight);
				else
					notFound = false;
			}
			flightReader.close();
			
			FileWriter writer3 = new FileWriter(flights);
			PrintWriter outFlights = new PrintWriter(writer3);
			for(int i = 0; i <= newFlights.size()-1; i++)
				outFlights.println(newFlights.get(i));
			outFlights.close();
			writer3.close();
			if(notFound)
				System.out.println("No flight was found. So none were deleted");
		}
		else
			System.out.println("The format for the flight code doesn't match.");
	}
	
/* Search Flight by Date Method
  Input:   Needs the arguments from the main method.
  Process: The first loop gets flights matching the source airport and destination airport.
			It also stores the start date and end date in two other arrayLists.
			After that I created 3 objects of Date type and used javas API to determine where they are in relation to each other.
			using the .before and the .after nmethod
  Output:  Prints out the flights matching the search.
*/
	public static void searchFlightDate(String srcAirport, String desAirport, String checkDate) throws IOException
	{
		String option,flightStr,pattern1,pattern2;
		
		pattern1 = "[A-Z]{3}";
		pattern2 = "^((0?[13578]|10|12)(-|\\/)(([1-9])|(0[1-9])|([12])([0-9]?)|(3[01]?))(-|\\/)((19)([2-9])(\\d{1})|(20)([01])(\\d{1})|([8901])(\\d{1}))|(0?[2469]|11)(-|\\/)(([1-9])|(0[1-9])|([12])([0-9]?)|(3[0]?))(-|\\/)((19)([2-9])(\\d{1})|(20)([01])(\\d{1})|([8901])(\\d{1})))$";
		
		String [] flight;
		ArrayList matches = new ArrayList<String>();
		ArrayList startDates = new ArrayList<String>();
		ArrayList endDates = new ArrayList<String>();
		
		File flightFile = new File("Flights.txt");

		if(srcAirport.matches(pattern1) && desAirport.matches(pattern1))
		{
			//This code gets all the flights with matching srcAirports and desAiports and puts them in to the ArrayList.
			// and also gets the start and end dates for the flight.
				Scanner readFlight = new Scanner(flightFile);
					while(readFlight.hasNext())
					{
						flightStr = readFlight.nextLine();
						flight = flightStr.split(",");
						if(flight[1].contains(srcAirport) && flight[2].contains(desAirport))
						{
							matches.add(flightStr);
							startDates.add(flight[6]);
							endDates.add(flight[7]);
						}
					}
				readFlight.close();
				
			//From here is where I try to check the dates using Date objects.
			String [] valuesCheck;
			String [] valuesStart;
			String [] valuesEnd;
			
			String start, end;
			valuesCheck = checkDate.split("/");
			Date checkD8 = new Date(Integer.parseInt(valuesCheck[2])-1900,Integer.parseInt(valuesCheck[1]),Integer.parseInt(valuesCheck[1]));
			
			for(int i = 0 ; i<matches.size() ; i++)
			{
				start = (String) startDates.get(i);
				end = (String) endDates.get(i);
				
				valuesStart = start.split("/");
				valuesEnd = end.split("/");
				
				Date startD8 = new Date(Integer.parseInt(valuesStart[2]) - 1900, Integer.parseInt(valuesStart[1]), Integer.parseInt(valuesStart[1]));
				Date endD8 = new Date(Integer.parseInt(valuesEnd[2]) - 1900, Integer.parseInt(valuesEnd[1]), Integer.parseInt(valuesEnd[1]));
				
				if(!checkD8.after(startD8) | !checkD8.before(endD8))
					matches.remove(i);
					
			}
			if(matches.size() != 0)
				for(int i = 0; i < matches.size(); i++)	
					System.out.println(matches.get(i) + "\n");
			else 
				System.out.println("No flights were found matching this search");
		}
		else
			System.out.println("One of arguments was in the wrong format");
	}

/* Edit Flight method
Input:   This method recieves 5 command-line arguments, eg. EF EI507 -T-TFS- 12/06/2015 14/06/2016
Process: Checks if file exists, displays message if not. Checks if flight code matches a pattern (2 alphabetic characters followed by 3 or 4 numberic 
		  characters),if not displays message. It reads the dates entered, it sends them one by one into  a validation method with a boolean, it uses
		  Zeller Congruence to check if it is valid, returns true if valid or false if invalid.
  		  Reads a line from the file, puts it into an array, checks if code exists and displays messages should name not be found or if code already exists.
  		  Creates an ArrayList which will store the line from the file eg, code, DepAirport, ArrivAirport, LeaveTime, ArriveTime, Days, Date1, Date2.
          A line is read from the file, if the code is equivalent to the code for the edited flight, it will be updated and added to the ArrayList, else it is just added.
  		  ArrayList gets sorted in ascending sequence.
  		  File gets updated with the ArrayList.
Output:  The File gets updated with the ArrayList.
*/
	public static void editFlight(String v, String w, String x, String y, String z) throws IOException
	{
		String pattern = "[A-Z]{2}[0-9]{3,4}";
		String pattern1 = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
		String line, line1;
		String[] parts;
		String[] parts1;
		boolean found = false, validDate = true;
		File file = new File("Flights.txt");
		Scanner in = new Scanner(file);
		Scanner in1 = new Scanner(file);
		Scanner in2 = new Scanner(file);
		// v = EF, w = Flight Code, x = Days, y = Date1, z = Date2
		//---------------------------------Validation--------------------------------------------------------
		if(!(file.exists())) 				//If "Flights.txt" doesn't exist, end program.
			System.out.println("Flights.txt does not exist");
			
		else
		{		
			if(!(w.matches(pattern))) 		//If the flight code doesn't match the pattern a message will be dislayed.
				System.out.println("Flight is invalid");
			else
			{	
				if(!(y.matches(pattern1))) 			//If Start Date does not match pattern display message.
				System.out.println("Invalid date");
		
				else
				{
					date(y, validDate);             //Else go to date method to validate date.
					if(validDate == false) 
						System.out.println("invalid date");
				}
				
				if(!(z.matches(pattern1))) 			//If End Date does not match pattern display message.
				System.out.println("Invalid date");
			
				else
				{
					date(z, validDate);             //Else go to date method to validate the date.
					if(validDate == false) 
						System.out.println("Invalid date");
				}
			}
			
			//-----------------------------------End of Validation---------------------------------------------------	
			if(validDate == false)
				System.out.println("date is invalid");
			else
			{	
				while(in.hasNext())
				{
					line = in.nextLine();
					parts = line.split(",");
					if(w.equalsIgnoreCase(parts[1]))
						found = true;				//Checking if Flight code exists.
				}
				if(found = false)
					System.out.println("Flight Code does not exist, Enter a different one.");
				else
				{
					ArrayList<String> flight = new ArrayList<String>();
					while(in1.hasNext()) 			//Checks if read line contains the flight code, it updates the line and
					{								//pass it to Arraylist or just add it to the ArrayList.
						line1 = in1.nextLine();
						if(line1.contains(w))
						{
							parts1 = line1.split(",");
							parts1[5] = x;
							parts1[6] = y; parts1[7] = z;
							flight.add(parts1[0] + "," + parts1[1] + "," + parts1[2] + "," + parts1[3] + "," + parts1[4] + "," + parts1[5] + "," + parts1[6] + "," + parts1[7]);
						}
						else
							flight.add(line1);
					}
					Collections.sort(flight); 						//Sorts ArrayList.
					PrintWriter out = new PrintWriter(file);
					for(int i = 0; i < flight.size(); i++) 			//Updates the file with ArrayList.
						out.println(flight.get(i));
					out.close();
				}	
			}	
		}
	}

/* Date Method
  Input:   The user input and a boolean value is passed into the method
  Process: The date gets split at each "/".
  		    If the date's valid, the Zeller Congruence commences.
  		    If the date's valid, "valid" is set to true.
  		    Otherwise it is set to false.
  Output:  The boolean "valid" is returned to the method which called it.
*/
	public static boolean date(String userInput, boolean valid) throws IOException
  	{
    	String[] array = userInput.split("/");
		int d = Integer.parseInt(array[0]), m = Integer.parseInt(array[1]), y = Integer.parseInt(array[2]);
		int a, b, dayOfWeek;                             //Puts date into array, gets each number and saves them as day, month and year.
    	Scanner in = new Scanner(System.in);
    	if(d < 0 || d >= 31)							 //Validate the dates, if valid do Zeller Congruence.
    	{
	    	System.out.println("Invalid date");
	    	valid = false; return valid;	
    	}
  		else
  		{
	  		if(m < 0 || m >= 12)
	  		{
		  		System.out.println("Invalid date");
		  		valid = false; return valid;
  			}
	  		else
	  		{
		  		a = y % 100;
        		b = y / 100;
        		dayOfWeek = ((d + (((m + 1) * 26) / 10) + a + (a / 4) + (b / 4)) + (5 * b)) % 7; //Zeller Congruence calculations.
        		if(dayOfWeek >= 0 && dayOfWeek <= 6) valid = true; 								 //If valid return true.
        		else			   valid = false;                  								 //Else return false.
        		return valid;
		  		
	  		}	
  		}
  	}
	
/* Search Flight by Date Method
  Input:   	Takes the source airport and destination airport as args from main method.
  Process: 	First it checks to make sure that the airport codes enter are the right format. Then
  			 it'll load the flights.txt file and drop into a while loop. It first reads each line, and splits them
 			 into parts so the specific airport parts can be read. Then it'll check if both the airports match.
  Output:  	It prints any flights that match the search.
*/
	public static void searchFlightPorts(String srcAirport, String desAirport) throws IOException
	{
		String flightStr,pattern1;
		
		pattern1 = "[A-Z]{3}";
	
		String [] flight;
		ArrayList<String> matches = new ArrayList<String>();
		File flightFile = new File("Flights.txt");

		if(srcAirport.matches(pattern1) && desAirport.matches(pattern1))
		{
				Scanner readFlight = new Scanner(flightFile);
					while(readFlight.hasNext())
					{
						flightStr = readFlight.nextLine();
						flight = flightStr.split(",");
						if(flight[1].contains(srcAirport) && flight[2].contains(desAirport))
						{
							matches.add(flightStr);
						}
					}
				readFlight.close();
				
			if(matches.size() != 0)
				for(int i = 0; i < matches.size(); i++)	
					System.out.println(matches.get(i) + "\n");
			else 
				System.out.println("There are no flights that match this search");
		}
		else
			System.out.println("Please use the correct format.");
	}
}