/*
 * Written by Zachary Goldberg
 */
//Packages
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//Creating the class
public class helper {
	public static String fileName = "./Collection.txt";
	public static final String DELIM = "\t";
	public static final int FIELD_AMT = 2;
	
	private static ListNode head;
	private static ListNode current;
	private static ListNode previous;
	private static String gameSearch;
	private static String consoleSearch;
	
	//Constructor
	public helper() {
		head = new ListNode();
		current = head;
		previous = head;
	}
	
	
	//Count all the lines in the file
	public int countLinesInFile(String inputFile) {
		try {
			Scanner fileScanner = new Scanner(new File(inputFile));
			int counter = 0;
			while(fileScanner.hasNextLine()) {
				counter++;
				fileScanner.nextLine();
			}
			return counter;
		}
		catch(IOException e) {
			System.out.println(e);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	
	//Start of the text message
	public void textMessage() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("");
		System.out.println("Enter 1 to load the video game database");
		System.out.println("Enter 2 to search the database");
		System.out.println("Enter 3 to print current results to the console");
		System.out.println("Enter 4 to print current results to file");
		System.out.println("Enter 0 to quit");
		int answer = keyboard.nextInt();
		if(answer == 1) {
			System.out.println("Enter the file name");
			String file = keyboard.next();
			if(file != null) {
				loadVideoGames(fileName);
			}
		}else if(answer == 2) {
			System.out.println("Enter the name of the game or \'*\' for all");
			gameSearch = keyboard.next();
			if(gameSearch != null) {
				System.out.println("Enter the name of the console or \'*\' for all");
				consoleSearch = keyboard.next();
				if(consoleSearch != null) {
					System.out.println(consoleSearch);
					searchDatabase();
				}
			}
		}else if(answer == 3) {
			searchDatabase();
		}else if(answer == 4) {
			System.out.println("Enter the file name");
			String a1 = keyboard.next();
			if(a1 != null) {
				System.out.println("Would you like to append? True or false?");
				String a2 = keyboard.next();
				if(a2 != null) {
					writeFile();
				}
			}
		}else if(answer == 0) {
			System.out.println("Goodbye!");
		}
		
	}
	
	//Creating list node class
	public class ListNode {
		public String gameNames;
		public String consoleNames;
		public ListNode node;
		
		//Default Constructor
		public ListNode() {
			gameNames = null;
			consoleNames = null;
			node = null;
		}
		
		//Constructor
		public ListNode(String gn,String cn,ListNode m) {
			gameNames = gn;
			consoleNames = cn;
			node = m;
		}
		
		public String toString() {
			return gameNames + "\t" + consoleNames;
		}
		
		
	}
	
	//Moves onto the next node in the linked list
	public void gotoNext() {
		if(current.node != null) {
			if(current == head) {
				current = head.node;
			}else {
				previous = previous.node;
				current = current.node;
			}
		}
	}
		
	//Returns current item in linked list
	public String getGameCurrent() {
		if(current.gameNames == null) {
			current = head;
			previous = head;
		}
		return current.gameNames;
	}
		
	//Returns current item in linked list
	public String getConsoleCurrent() {
		if(current.consoleNames == null) {
			current = head;
			previous = head;
		}
		return current.consoleNames;
	}
	//Adds an item at the end of the linked list
	public void addItem(String gn, String cn) {
		if(head.gameNames == null) {
			ListNode newOne = new ListNode(gn,cn,null);
			head = newOne;
		}else {
			ListNode end = head;
			ListNode newOne = new ListNode(gn,cn,null);
			while(end.node != null) {
				end = end.node;
			}
			end.node = newOne;
		}
	}
	
	public void showList() {
		ListNode end = head;
		while(end.node != null) {
			System.out.println(end);
			end = end.node;
		}
		System.out.println(end);
	}
	
	public void writeFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("./SuperGames.txt");
			ListNode end = head;
			while(end.node != null) {
				if(gameSearch.equals("*") || consoleSearch.equals("*")) {
					if(gameSearch.equals("*") && consoleSearch.equals("*")) {
						writer.println(end);
					}else if(gameSearch.equals("*") && end.consoleNames.toLowerCase().contains(consoleSearch.toLowerCase())) {
						writer.println(end);
					}else if(end.gameNames.toLowerCase().contains(gameSearch.toLowerCase()) && consoleSearch.equals("*")) {
						writer.println(end);
					}
				}else {
					if(end.gameNames.toLowerCase().contains(gameSearch.toLowerCase()) && end.consoleNames.toLowerCase().contains(consoleSearch.toLowerCase())) {
						writer.println(end);
					}
				}
				end = end.node;
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		writer.close();
		textMessage();
	}
	
	public void searchDatabase() {
		ListNode end = head;
		while(end.node != null) {
			if(gameSearch.equals("*") || consoleSearch.equals("*")) {
				if(gameSearch.equals("*") && consoleSearch.equals("*")) {
					System.out.println(end);
				}else if(gameSearch.equals("*") && end.consoleNames.toLowerCase().contains(consoleSearch.toLowerCase())) {
					System.out.println(end);
				}else if(end.gameNames.toLowerCase().contains(gameSearch.toLowerCase()) && consoleSearch.equals("*")) {
					System.out.println(end);
				}
			}else {
				if(end.gameNames.toLowerCase().contains(gameSearch.toLowerCase()) && end.consoleNames.toLowerCase().contains(consoleSearch.toLowerCase())) {
					System.out.println(end);
				}
			}
			end = end.node;
		}
		textMessage();
	}
	
	public void loadVideoGames(String fn) {
		try {
			//Creates the scanner to read the file
			Scanner fileScanner = new Scanner(new File(fileName));
			//While there is a next line
			int i = 0;
			while(fileScanner.hasNextLine())
			{
				String fileLine = fileScanner.nextLine();
				String[] splitLines = fileLine.split(DELIM);
				if(splitLines.length != FIELD_AMT)
					continue;
				
				addItem(splitLines[0],splitLines[1]);
			}
			fileScanner.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		textMessage();
	}
}
