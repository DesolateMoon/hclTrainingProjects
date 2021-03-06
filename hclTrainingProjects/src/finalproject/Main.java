package finalproject;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main implements Interface{
	static String path = System.getProperty("user.dir");
	static Scanner scanner = new Scanner(System.in);
	static Main main = new Main();
	
	public static void main(String[] args) {
		System.out.println("\n**************************************\n");
        System.out.println("Welcome to LockedMe.com \n");
        System.out.println("**************************************");
        
		System.out.println("Application Name: " + path);
		System.out.println("Developer: Austin Nguyen");
		System.out.println("Default Path: " + path + "\n");			
		
		int selection = 0;
		
		do {
			System.out.print("1. Change Default File Path\n2. Keep Default File Path\nSelect one: ");
			try {
				selection = Integer.parseInt(scanner.next());
			} catch (Exception e) {
				continue;
			}
		} while (selection != 1 && selection != 2);
		
		switch(selection) {
			case 1:
				System.out.println("Changing Default File Path.\n");
				main.changePath();
				break;
			case 2:
				System.out.println("Keeping Default File Path.\n");
				break;
		}
		
		mainMenu();
	}

/* 
 *---------------------------Menus-----------------------------------
 * mainMenu()
 * editMenu()
*/
	private static void mainMenu() {
		final String MM = "1. List Files\n2. Edit Files\n3. Close Application\nSelect one: ";
		final String LIST = "Listing Files.\n";
		final String EDIT = "Editing Files.\n";
		final String CLOSE = "Closing Application.";
		System.out.print(MM);
		
		int selection = 0;
		
		try {
			selection = Integer.parseInt(scanner.next());
		} catch (Exception e) {
			mainMenu();
		}
		
		switch(selection) {
			case 1:
				System.out.println(LIST);
				main.listFile();
				break;
			case 2:
				System.out.println(EDIT);
				editMenu();
				break;
			case 3:
				System.out.println(CLOSE);
				main.closeApplication();
				break;
			default: 
				mainMenu();
				break;
		}
		
		mainMenu();
	}
	
	private static void editMenu() {
		final String EM = "1. Add File\n2. Delete File\n3. Search File\n4. Return to Main Menu\nSelect one: ";
		final String ADD = "Adding Files.\n";
		final String DELETE = "Deleting Files.\n";
		final String SEARCH = "Searching Files.\n";
		final String RETURN = "Returning to Main Menu.\n";
		System.out.print(EM);
		
		int selection = 0;
		
		try {
			selection = Integer.parseInt(scanner.next());
		} catch (Exception e) {
			editMenu();
		}
		
		switch(selection) {
		case 1:
			System.out.println(ADD);
			main.addFile();
			break;
		case 2:
			System.out.println(DELETE);
			main.deleteFile();
			break;
		case 3:
			System.out.println(SEARCH);
			main.searchFile();
			break;
		case 4:
			System.out.println(RETURN);
			break;
		default: 
			editMenu();
			break;
		}
		
		mainMenu();
	}
	
/*
 * -------------Functional Logic (Override Interface)-------------------
 * changePath()
 * listFile()
 * addFile()
 * deleteFile()
 * searchFile()
 * closeApplication()
 */
	@Override
	public void changePath() {
		final String INPUT = "Input file path to be changed to: ";
		final String ERROR = "ERROR: File path has not been changed\n";
		String input = null;
		File file = null;
		
		do {
			
			System.out.print(INPUT);
			
			try {
				input = scanner.next();
				file = new File(input);
				
				if(!file.isDirectory()) {
					System.out.print(ERROR);
				}
			} catch (Exception e) {
				System.out.print(ERROR);
				changePath();
			}
		} while(!file.isDirectory());
		
		path = input;
		System.out.println("File path has been changed to: " + path + "\n");
	}
	
	@Override
	public void listFile() {
		File file = new File(path);
		String [] ls  = file.list();
		Arrays.sort(ls);
		System.out.println(Arrays.toString(ls) + "\n");
	}
	
	@Override
	public void addFile() {
		final String ADD = "Input file to be added: ";
		final String ERROR = "ERROR: File has not been added at: ";
		
		String input = null;
		
		System.out.print(ADD);
		
		try {
			input = scanner.next();
		} catch (Exception e) {
			editMenu();
		}

		input = path + "\\" + input;
		
		File file = new File(input);
		try {
			if(file.createNewFile()) {
				System.out.println("File has been added at: " + input + "\n");
			} else {
				throw new FileMismatchException(ERROR + input + "\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mainMenu();
		}
	}
	
	@Override
	public void deleteFile() {
		final String DELETE = "Input file to be deleted: ";
		final String ERROR = "ERROR: File has not been deleted at: ";
		final String FNF = "ERROR: File not found at: ";
		
		String input = null;
		
		System.out.print(DELETE);
		
		try {
			input = scanner.next();
		} catch (Exception e) {
			mainMenu();
		}

		input = path + "\\" + input;
		
		File file = new File(input);
		try {
			if(file.exists()) {
				if(file.delete()) {
					System.out.println("File has been deleted at: " + input + "\n");
				} else {
					throw new FileMismatchException(ERROR + input + "\n");
				}
			} else {
				throw new FileNotFoundException(FNF + input + "\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mainMenu();
		}
	}
	
	@Override
	public void searchFile() {
		final String SEARCH = "Input file to be searched: ";
		final String ERROR = "ERROR: File has not been found at: ";
		
		String input = null;
		
		System.out.print(SEARCH);
		
		try {
			input = scanner.next();
		} catch (Exception e) {
			mainMenu();
		}
		
		input = path + "\\" + input;
		
		File file = new File(input);
		
		try {
			if (file.exists()) {
				System.out.println("File has been found at: " + input + "\n");
			} else {
				throw new FileNotFoundException(ERROR + input + "\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mainMenu();
		}
	}
	
	@Override
	public void closeApplication() {
		scanner.close();
		System.exit(0);
	}
}
