/* Group 19 - Project 1
   Student:   Barry O' Riordan - 13144278
*/

//Importing the JOptionPane package
import javax.swing.JOptionPane;

//Name of Class
public class Semester2Project1
{
//The vowels and consonants have been declared as global variable of String type to be used in multiple methods.
	public static final String vowels     = "aeiou";
	public static final String consonants = "bcdfghjklmnpqrstvwxyz";
  
/*Main Method - Barry O' Riordan
  Process: The user will choose an option from the Drop-down menu and the option selected will call the method related to the option.
  Output:  The menu will be outputted to the user to choose an option.
*/
	public static void main(String[] args)
	{
		boolean doNotQuit = true;
   		Object[] menuOptions = {"1) Analyze vowel content of word/phrase",
  					   	  		"2) Analyze consonant frequency of word/phrase",
  					      		"3) Analyze character frequency of word/phrase",
  					      		"4) Keyboard rows test",
  					      		"5) Alternating vowels/consonants",
  					  	 		"6) Phrase/sentence analysis (longest/shortest)",
  					      		"7) Anagram test",
  					     		"8) Palindrome test",
  					     		"0) Exit"};
		while(doNotQuit)
     	{
	  		Object selectedOption = JOptionPane.showInputDialog(null, "Please choose an option:", "Menu", 1, null, menuOptions, menuOptions[0]);
      		if(selectedOption == null || selectedOption.equals(menuOptions[8]))
				doNotQuit = areYouSure();
			else if(selectedOption.equals(menuOptions[7]))
				palindromeTest();
			else if(selectedOption.equals(menuOptions[6]))
				anagramTest();
			else if(selectedOption.equals(menuOptions[5]))
       			wordAnalysis();
      		else if(selectedOption.equals(menuOptions[4]))
       			alternateVowelsConsonants();
      		else if(selectedOption.equals(menuOptions[3]))
       			keyboardRowsTest();
      		else if(selectedOption.equals(menuOptions[2]))
       			freqOfChars();
      		else if(selectedOption.equals(menuOptions[1]))
       			consonantFrequency();
      		else if(selectedOption.equals(menuOptions[0]))
       			analyzeVowels();
       	}
	}

/*Quit Prompt Method
  Process: The program will prompt to either continue or quit, then will display an are you sure box.
  Output:  A boolean variable will be returned, depending on the option chose, the boolean returned will either end the program or keep it running
*/
	public static boolean areYouSure()
	{
		boolean quit;
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the program?", "Quit?", JOptionPane.YES_NO_OPTION);
		if(option == 0)
			quit = false;
		else 
			quit = true;
		return quit;
	}
	
/*User Input Method
  Input:   The method receives two Strings that are used in the Input dialog box.
  Process: The user input is stored into userInput.
		   The boolean isValid checks if the input is valid or not.
  Output:  userInput will be returned as a String.
*/
	public static String getUserInput(String message, String header)
	{
  		 boolean isValid = false;
  		 String userInput = new String();
   		 while(!isValid)
   		 {
			 userInput = JOptionPane.showInputDialog(null, message, header, 3);  
     		 if(userInput == null)
     		 {
      		 	 userInput = "";
     		 	 isValid = true;
     		 }
     		 else if(userInput.length() == 0)
	  		 	 JOptionPane.showMessageDialog(null, "No input entered.\nSelect Ok to retry.", "Input Error", 2);
			 else
	  			 isValid = true;   
   		 } 
   		 return userInput;	  
	}
  
/*Trim Input Method
  Input:   The user input of String type.
  Process: The method trims the input of all symbols and spaces while putting it to lower-case.
  Output:  The trimmed String is returned.
*/
	public static String trimSentence(String sentence)
	{
		String words = sentence.replaceAll("\\W", "").trim().toLowerCase();
		return words;
	}
  
/*Trim Input to Array Method
  Input:   The user input of String type.
  Process: The method trims the input of all symbols and spaces while putting it to lower-case.
  		   It will then be split the input into a String array where any spaces are present.
  Output:  The trimmed String array is returned
*/
	public static String[] trimSentenceToArray(String sentence)
	{
		String[] words = sentence.replaceAll("[^a-zA-Z ]", "").trim().toLowerCase().split("\\s+"); 
		return words;
	}
  
/*Analyze Vowel Content Method
  Process: getUserInput() is called and passed to trimSentence for trimming. 
	       The returned String is checked to see if it's null or empty.
  		   String "result" is generated by calling analyzeCharacters(), displayFrequency() and alphabeticalOrder().
  		   If analyzeCharacters() returns 0 zero, no vowels are in the user input, otherwise vowels are present.
  		   displayFrequency() returns the frequency of each vowel.
  		   alphabeticalOrder() returns if the input is a variety of alphabetic variations.
  Output:  The result is outputted to the user.
*/
	public static void analyzeVowels()
	{
		String phrase = getUserInput("Please enter a word/phrase", "Vowel Content");
		String result = "Word/Phrase:\n" + phrase + "\n";
		String trimmedPhrase = trimSentence(phrase);
		if(trimmedPhrase != null && trimmedPhrase != "")
		{
			int total = analyzeCharacters(trimmedPhrase, 'v');
			if (total == 0)
				result += "There are no vowels in the User Input.\n";
			else
				result += "The Input has " + total + " vowels.\n" + displayFrequency(trimmedPhrase, 'v');
			result += alphabeticalOrder(trimmedPhrase);
			JOptionPane.showMessageDialog(null, result, "Vowel Content Analysis", 1);
		}
		else
			JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);
	}
	
/*Analyze Characters Method
  Input:   userInput from getUserInput() and variable of type char.
  Process: The char variable is used to see if it's working on the global variables "vowels" or "consonants".
  		   If char = 'v', it works on vowels, otherwise it works on consonants.
  		   A for loop is used to go through the trimmed user input and see if each letter is contained in "character"(vowels or consonants).
  Output:  The total of vowels or consonants in the user input is returned as an integer data type.
*/
	public static int analyzeCharacters(String input, char vOrC)
	{
		String characters = "";
		if (vOrC == 'v')
			characters = vowels;
		else
		  	characters = consonants;
		int total = 0;
		
		for (int i = 0; i < input.length(); i++) 
			if (characters.contains(input.substring(i, i + 1)))
				total++;
		return total;
	}
	
/*Display Frequency Method
  Input:   The user input of String type and a char data type. 
  Process: The char passed in determines whether if the method is working on consonants or vowels.
  		   The char 'c' denotes the global variable consonants, while 'v' denotes the global variable vowels.
    	   The analyzeFrequency() method is called, passing down the user input and the vowels or consonants.
    	   The array generated by analyzeFrequency() and the String used are put in a for loop to generate the frequency of each character.
  Output:  The method returns the result in the String output.
*/
	public static String displayFrequency(String input, char vOrC)
	{
		String output = "";
		String characters = "";
		if (vOrC == 'v')
			characters = vowels;
		else if (vOrC == 'c')
			characters = consonants;
		
		int[] frequency = analyzeFrequency(input, characters);
		for (int i = 0; i < frequency.length; i++) {
			if (frequency[i] != 0)
				output += "Frequency of " + characters.charAt(i) + " is "+ frequency[i] + ".\n";
		}
		return output;
	}

/*Analyze Frequency Method
  Input:   Two inputs of String type.
  		   Input is the user input, while characters is either vowels or consonants depending on what is being analyzed.
  Process: An integer array is created to store the frequency of the String characters.
  		   Inside a for loop the user input is checked letter after letter.
  		   If the String characters contains the letter of the user input, the array value in the cell of that character index will be incremented.
  Output:  The generated array of integer type is returned.
*/
	public static int[] analyzeFrequency(String input, String characters) 
  	{
		int[] frequency = new int[characters.length()];
		for(int i = 0; i < input.length(); i++) 
		{
			int j = characters.indexOf(input.charAt(i));
			if (j != -1)
				frequency[j]++;
		}
		return frequency;
	}

/*Alphabetic Order Method
  Input:   The method the user input as String type.
  Process: Two variables of integer type are set to -1. 
  		   The integer "order" gets the order of the examined characters.
  		   An integer array is returned from analyzeFrequency() which stores the frequency of the characters examined.
  		   The first for loop goes through the frequency[] array, order is set to zero if a character has no frequency.
  		   If "order" = 0 not all the examined characters are in the input.
  		   The second for loop is goes through the examined character String length.
  		   The index of the vowels in the user input are compared with "temp" to see if the characters are in alphabetical or reverse alphabetical order.
  		   If "order" = 1 the vowels may be in reverse alphabetical, but another check is needed.
  		   If "order" = 2 the vowels are in alphabetical order.
  		   A final check is needed to see if the characters examined are in reverse order or in various areas in the String.
  		   The second for loop is similar to the previous one.
  		   If "order" = 3 all the vowels are present but not in order.
  		   A switch() statement checks "order" and then "output" is set to the String which is equal to "order".
  Output:  The String "output" is returned.
*/
	public static String alphabeticalOrder(String input)
	{
		String characters = vowels;
		int order = -1, temp = -1;
		int[] frequency = analyzeFrequency(input, characters);
		for(int i = 0; i < frequency.length && order != 0; i++)
			if (frequency[i] == 0)
				order = 0;

		for (int i = 0; i < characters.length() && order != 0 && order != 1; i++) 
		{
			int index = input.indexOf(characters.charAt(i));
			if (index < temp)
				order = 1;
			temp = index;
		}
		if (order != 1 && order != 0)
			order = 2;
		temp = input.length() + 1;
		
		for (int i = 0; i < characters.length() && order != 0 && order != 2; i++)
		{
			int index = input.indexOf(characters.charAt(i));
			if (index > temp)
				order = 3;
			temp = index;
		}
		String output = "";
		switch(order)
		{
			case 0:		output = "Not all vowels are in the input."										;break;
			case 1:		output = "All vowels in the input are in reverse alphabetical order."			;break;
			case 2:		output = "All vowels in the input are in the alphabetical order."				;break;
			case 3:		output = "All vowels are in the input, but they're not in alphabetical order."	;break;
		}
		return output;
	}

/*Consonant Frequency Method
  Process: getUserInput() gets the user input.
  		   The user input is passed to trimSentence() and checked if it returned null or an empty String.
  		   analyzeCharacters() returns the total consonants in the user input.
  		   displayFrequency() returns the frequency of each consonant.
  		   "result" is generated as a product of the called methods.
  Output:  The String "result" is outputted if the user input was valid, otherwise an error message would be outputted.
*/
	public static void consonantFrequency()
	{
		String phrase = getUserInput("Please enter a word/phrase", "Consonant Content");
		String result = "Word/Phrase:\n" + phrase + "\n";
		String trimmedPhrase = trimSentence(phrase);
		if(trimmedPhrase != null && trimmedPhrase != "")
		{
			result +="Input has " + analyzeCharacters(trimmedPhrase, 'c') + " consonants.\n" + displayFrequency(trimmedPhrase, 'c');
			JOptionPane.showMessageDialog(null, result, "Consonant Content Analysis", 1); 
   		}
   		else
     		JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);	  	  
	}

/*Frequency Of All Method
  Process: The method gets the input from getUserInput().
		   The variables of integer data type "cha", "other" and "num" count the frequency of each type of input.
		   The String "UniqueChars" gets each character that appears one or more times in the input and stores it in order of appearance.
		   The array "FreqInt[]" then uses the subscripts of the string "UniqueChars" and uses them as indexes, then increments each subscript accordingly as the characters appear.
	       Regular expressions are used to sort out which piece of code should be executed depending on the character type. (ie. Letters, Numbers and Symbols fall into the category "cha", "num", and "other", respectively).
		   The regular expressions used are "alpha", "digit" and "symbol".
		   All of this is executed in the first for loop.
		   The last for loop is used to format the result to be able to display it to the end user.
  Output:  The frequency of each type of character and the frequency of each individual character is stored in results.
		   "results" is then displayed to the end user.
		   If the input is invalid an error message will be displayed.
*/
	public static void freqOfChars()
	{
		String UniqueChars = "", alpha, digit, symbol ,result = "";
		int cha = 0, num = 0, other = 0;			// Variables to count frequencies.
				// Regular Expressions used.
		alpha  = "[A-Za-z]";
		digit  = "[0-9]";
		symbol = "[^A-Za-z0-9]";
		String input = getUserInput("Please enter your word/phrase/sentence?", "Frequency Of Characters");
		int[] freqInt = new int [input.length()];	//Array for frequencies of each character.
		if (input != null)
		{
			for(int i = 0 ; i < input.length() ; i++)
			{
				//Checks if character is already in the string UniqueChars and adds to the frequency.
				if(UniqueChars.indexOf(input.substring(i, i + 1)) != -1)
					freqInt[UniqueChars.indexOf(input.substring(i, i + 1))]++;
				else
				{
					if(input.substring(i, i + 1).matches(alpha))
					{
						cha++;
						UniqueChars += input.substring(i, i + 1);
						freqInt[UniqueChars.indexOf(input.substring(i, i + 1))]++;
					}
					else if(input.substring(i, i + 1).matches(digit)) 
					{
						num++;
						UniqueChars += input.substring(i, i + 1);
						freqInt[UniqueChars.indexOf(input.substring(i, i + 1))]++;
					}
					else if(input.substring(i, i + 1).matches(symbol))
					{
						other++;
						UniqueChars += input.substring(i, i + 1);
						freqInt[UniqueChars.indexOf(input.substring(i, i + 1))]++;
					}
				}
			}
			result = "Amount of alphabetic characters: " + cha + ".\n" + "Amount of numeric characters: " + num + ".\n" + "Amount of symbols: "
			+ other + ".\n" + "Frequency of Characters:\n";
			for(int i = 0 ; i < UniqueChars.length() ; i++)
			{
				if(UniqueChars.substring(i, i + 1).equals(" "))
					result += "The character " + "'White Space'" + " occurs " + freqInt[i];
				else
					result += "The character " + UniqueChars.substring(i,i+1) + " occurs " + freqInt[i];
				
				if(freqInt[i] == 1)
					result += " time.\n";
				else
					result += " times.\n";
			}
			JOptionPane.showMessageDialog(null, result, "Frequency of Characters Analysis", 1); 
		}
		else 
			JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);
	}

/*Keyboard Rows Method 
  Process: Input is taken in from getUserInput(), trimmed using the trimSentence() method, and is checked to see if it is null or empty.
  		   If it's a valid input it will begin checking each individual character using a while loop.
  		   The while loop sees if each character is found in either of the three Strings which contain all the letters of each row.
  		   If a letter is found to be in a row, it will turn the boolean statement true, and thus won't check for letters in that row as we know there is already one there.
		   It will also add a certain number to the int numcheck.
		   Then using a switch statement we can tell which specific rows were used based on the amount of numbers added to numcheck.
  Output:  It will display which rows were found to be used in the input.
  		   If the input is invalid an error message will be displayed.
 */
	public static void keyboardRowsTest()
	{
		String input = getUserInput("Please enter your word/phrase/sentence?","Keyboard Rows");
	 	boolean rowA = false, rowB = false, rowC = false, rowD = false;
	 	String row1 = "([qwertyuiop]+)", row2 = "([asdfghjkl]+)", row3 = "([zxcvbnm]+)";
  	 	String result = input + " uses the following keyboard rows:\n";
 	 	String piece = "";
	 	String trimmedInput = trimSentence(input);
	 	if((trimmedInput != null && trimmedInput != "")&&(trimmedInput.length() != 0))
  	  	{
      		int i = trimmedInput.length(), numCheck = 0;
      		while (i > 0 && rowD == false)
	    	{
	    		piece = trimmedInput.substring(i - 1, i);
	    		if (piece.matches(row1) && rowA != true)
				{
					numCheck = numCheck + 1;
					rowA = true;
				}
	    		if(piece.matches(row2) && rowB != true)
				{
					numCheck = numCheck + 2;
					rowB = true;
				}
	    		if(piece.matches(row3) && rowC != true)
				{
					numCheck = numCheck + 4;
					rowC = true;
				}
	    		if (rowA == true && rowB == true && rowB == true)
		 			rowD = true;
	 			i--;
        	}
			switch(numCheck)
        	{
    			case 1 : result += "Row 1."						;break;
    			case 2 : result += "Row 2."						;break;
     			case 3 : result += "Row 1 and Row 2."			;break;
     			case 4 : result += "Row 3."						;break;
      			case 5 : result += "Row 1 and Row 3."			;break;
     			case 6 : result += "Row 2 and Row 3."			;break;
				case 7 : result += "Row 1, Row 2 and Row 3."	;break;
      			default: result  = "No letter rows."			;break;
      		}
      		JOptionPane.showMessageDialog(null, result, "Keyboard Rows Analysis", 1);
 	  	}
      	else
      		JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);	  	  
	}

/*Alternating Vowels/Consonants Method
  Process: getUserInput() returns the user input.
  		   The user input is passed to trimSentence() to trim the input.
  		   The input is then checked to see if it is null or empty, if not it will proceed.
  		   doesAlternateis set to true (assumes it is alternating until proven otherwise)
  		   A while statement then loops from letter to letter to check if it aChar is a vowel or a consonant.
  		   Using two if statements, and modulus commands it checks odd/even letters for vowels/consonants.
  		   If it detects a vowel/consonant in an odd/even spot that it should not be in, doesAlternate is set to false.
  		   If doesAlternate = true, it alternates, otherwise it does not.
  Output:  The result will be displayed to the user if the word/phrase/sentence alternates or not.
  		   If the input is invalid an error message will be displayed.
*/
	public static void alternateVowelsConsonants()
  	{
		String input = getUserInput("Please enter your word/phrase/sentence?","Alternating Vowels/Consonants");
		int i = 0; 
  		boolean doesAlternate = true;								//Assumes that the input is alternating till otherwise.
   		String result = "", incase = trimSentence(input);
  		if((incase != null && incase != "") && ( incase.length() != 0))
		{
			String aChar = incase.substring(i, i + 1);
			if(vowels.contains(aChar))
			{
				while(i < incase.length() && doesAlternate == true) //Checking for vowels every odd letter.
				{
					aChar = incase.substring(i, i + 1);
					if ((!(vowels.contains(aChar))) && (i % 2 == 0))
					    doesAlternate = false;
					if ((vowels.contains(aChar)) && (i % 2 != 0))	
					    doesAlternate = false;
					i++;
				}		
			}
			else 
			{
		   		while (i < incase.length() && doesAlternate == true) //Checking for vowels every even letter.
	       		{
	       			aChar = incase.substring(i, i + 1);
	       			if((vowels.contains(aChar)) && (i % 2 == 0))
		       			doesAlternate = false;   
	       			if((!(vowels.contains(aChar))) && (i % 2 != 0))
		       			doesAlternate = false;   
	       			i++;
	   			}
		 	}
			if (doesAlternate == true)
				result = input + " does alternate between vowels and consononants.";
			else
				result = input + " does not alternate between vowels and consonants.";
			  
			JOptionPane.showMessageDialog(null, result, "Alternating Vowels/Consonants Analysis", 1);
		}
		else
			JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);		  
	}

/*Word Count Method
  Process: The user input is passed to trimSentenceToArray() and is returned as String array.
    	   The array is then checked at sub-string 0 to see if it consists of an empty String.
    	   If it is empty an appropriate message is displayed.
    	   The number of words is given by the length of the array.
    	   Within a for loop, two variables of integer type are used to store the min and max word length.
    	   These are used to check and see if all the words are of same length.
   		   Another for loop is used for scrolling through the array.
   		   Any word that matches the min or max length is stored in a String if the String does not contain the word already.
  Output:  If the input is valid, the analysis of the input is displayed.
  		   If the input is invalid, an error message will be returned.
*/
	public static void wordAnalysis()
	{
		String longest = new String();
   		String shortest = new String();
   		int max = 0, min = 0;
  		String phrase = getUserInput("Please enter a phrase/sentence:", "Phrase/Sentence");
   		String result = "Phrase/Sentence:\n" + phrase + "\n";
  		String trimmedPhrase[] = trimSentenceToArray(phrase);
   		if(trimmedPhrase[0] != "")
   		{
   			max = trimmedPhrase[0].length();
   			min = trimmedPhrase[0].length();
   			for(int i = 1; i < trimmedPhrase.length; i++)
   			{
    			if(trimmedPhrase[i].length() > max)
      				max = trimmedPhrase[i].length();
    			if(trimmedPhrase[i].length() < min)
      				min = trimmedPhrase[i].length();
   			}
   			result += "There are " + trimmedPhrase.length + " words in the phrase/sentence.\n";
  			result += "The longest word is of length " + max + ".\nThe shortest word is of length " + min + ".";
   			for(int i = 0; i < trimmedPhrase.length; i++)
   			{
     			if(trimmedPhrase[i].length() == max && !longest.contains(trimmedPhrase[i]))
       				longest  += trimmedPhrase[i]+ ", ";
     			if(trimmedPhrase[i].length() == min && !shortest.contains(trimmedPhrase[i]))
      				shortest += trimmedPhrase[i]+ ", ";
  			}
   			result += "\nLongest word(s): " + longest + "\nShortest word(s): "+ shortest;
   			JOptionPane.showMessageDialog(null, result, "Phrase/Sentence Analysis", 1);
   		}
   		else
    		JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);
  	}

/*Anagram Test Method
  Process: A boolean (isAnagram) is set to true, therefore assuming the two inputs are anagrams until proven otherwise.
  	       The length of the two inputs are analyzed.
  	       If the inputs are of different length (not equal) they're not anagrams.
  	       A for loop is used to check if the characters of the first input are contained in the second input.
  	       If they are not, the boolean set to false and the loop stops, otherwise the value stays as true.
  Output:  If the input is valid "result" will be generated after going through the method, it will then be outputted to the user.
  		   Otherwise, if the input is invalid an error message will be displayed.
*/
	public static void anagramTest()
	{
		boolean isAnagram = true;
   		String phrase1 = getUserInput("Please enter a first word/phrase/sentence:", "Anagram Test");
  		String trimmedPhrase1 = trimSentence(phrase1); //Removes everything except alphabetic and numeric characters.
  		
   		String phrase2 = getUserInput("Please enter a second word/phrase/sentence:", "Anagram Test");
  		String trimmedPhrase2 = trimSentence(phrase2);
  		
   		String result = "Input 1:\n" + phrase1 + "\n" +"Input 2:\n" + phrase2 + "\n";
  			   result += "Trimmed Input 1:\n" + trimmedPhrase1 + "\n" +"Trimmed Input 2:\n" + trimmedPhrase2 + "\n";
   		if(trimmedPhrase1 != null && trimmedPhrase1 != "" && trimmedPhrase2 != null && trimmedPhrase2 != "")
   		{
    		if(trimmedPhrase1.length() != trimmedPhrase2.length())
    			isAnagram = false;
    		else
	 			for(int i = 0; i < trimmedPhrase1.length() && isAnagram; i++)
	  				if(!trimmedPhrase2.contains(trimmedPhrase1.substring(i, i + 1)))
	     				isAnagram = false;
	     				
   			if(isAnagram)
    			result += "Trimmed inputs are anagrams.";
  			else
    			result +="Trimmed inputs are not anagrams.";
  		}
  		else
   			JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);
   			
 		JOptionPane.showMessageDialog(null, result, "Anagram Test  Analysis", 1);
	}

/*Palindrome Test Method
  Note:     The program will not compare a single word at word level.
  Process:  User input is got via the getUserInput() and trimmed trimSentence().
  			The int variables i and j are used for comparisons.
  			The first for loop (Letter Level) finds the characters at the front (i) and at the back (j) and works its way to the centre.
  			The second for loop (Word Level) finds the words at the front (i) and at the back (j) and works its way to the centre. 
  			trimSentenceToArray() is used to do this and placing it in the sentence[].
  			Both loops check to see if i and j are the same and stops if the comparison fails and adds the given amount to "letterPalindrome".
  			It figures out which palindromes are present by comparing "letterPalindrome" to the case statements.
  			It will then print out the palindrome levels used based on the switch statement.
  Output:   The output will say if it's a palindrome at which level(s).
  			If the input is invalid an error message will be displayed.
*/
	public static void palindromeTest()
	{
		char letterA, letterB;
		int letterPalindrome = 0;
		String wordA = "", wordB = "";
		String input = getUserInput("Please enter your word/phrase/sentence?", "Palindrome Test");
		String result = input + " is a palindrome at ";
		String trimmedInput = trimSentence(input);
		String []sentence = trimSentenceToArray(input);
		int i = 0, j = trimmedInput.length() - 1;
		if(input != null && input != "")
		{
			for (i = 0; i < j && (letterPalindrome == 0); i++)
			{
				letterA = trimmedInput.charAt(i);
				letterB = trimmedInput.charAt(j);
				if(letterA != letterB)
					letterPalindrome = 1;
				j--;
			}
			if(sentence.length > 1)
			{
				i = 0;
				j = sentence.length - 1;
				for (i = 0; i < j && (letterPalindrome < 2); i++)
				{
					wordA = sentence[i];
					wordB = sentence[j];
					if(!(wordA.equals(wordB)))
						letterPalindrome = letterPalindrome + 2;
					j--;
				}
			}
			else
				letterPalindrome = letterPalindrome + 2;
			switch(letterPalindrome)
			{
				case 1 	: result += "word level."												  ;break;
				case 2 	: result += "letter level."												  ;break;
				case 3 	: result = input + " is not a palindrome at either letter or word level." ;break;
				default : result += "letter and word level."									  ;break;
			}
			JOptionPane.showMessageDialog(null, result, "Palindrome Test Analysis", 1);
		}
		else
			JOptionPane.showMessageDialog(null, "No Input entered.\nReturning to the menu.", "Input Error", 2);
	}
}		 		