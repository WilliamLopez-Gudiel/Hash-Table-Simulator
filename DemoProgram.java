//********************************************************************************
//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
//********************************************************************************
/**
 * this class is used to run a demo program that displays a 
 * program to the user that uses ThreeTEnHashTable class.
 */
class DemoProgram {
	/**
	 * main method that consist or classes to run a display of the 
	 * ThreeTenHashTable.
	 * @param args user input
	 */
	public static void main(String[] args) {
		//I cannot emphasize how bad of a design it is to do a bunch of
		//if statements to choose a class type. On the other hand, this
		//doing this solves a _lot_ of problems with how students submit
		//their assignments... so we're just going to do this.
		
		//Anyone who is _really_ interested in why we're doing it this way,
		//or why this is normally a terrible design decision... feel free
		//to ask a UTA to flag me (prof. Russell) on Piazza.
		
		if (args.length==1 && (args[0].equals("1") || args[0].equals("2"))) {
			demoProgram(args[0].equals("1"));
			return;
		}
		else {
			System.out.println("Usage: java DemoProgram [1|2]");
		}
	}
	
	/**
	 * Runs a demo program that implements the ThreeTenHashtable class.
	 * @param useTable1 used to identify which table we use as there's 2 of them
	 */
	public static void demoProgram(boolean useTable1) {
		try(java.util.Scanner input = new java.util.Scanner(System.in)) {
			ThreeTenHashTable1<String,String> table1 = new ThreeTenHashTable1<>(2);
			ThreeTenHashTable2<String,String> table2 = new ThreeTenHashTable2<>(2);
			
			System.out.println("\nThis is a demo interactive program for your hash table. Be aware that both the keys and values in the table are Strings, so if you enter 1 as your key, you get the string \"1\" not the integer 1.");
			
			while(true) {
				System.out.println("\nOptions:\n\t1. Add/Replace a Key-Value Pair\n\t2. Get the value associated with a key\n\t3. Remove a key\n\t4. Resize the table\n\t5. Display the table\n\t6. Quit");
				
				//get user selection
				int choice = forceIntChoice(input, "Enter a menu choice: ", 1, 6);
				
				//menu actions
				String key, value;
				int size;
				
				switch(choice) {
					case 1: //put
						System.out.println("----------Adding/Updating a Key-Value Pair----------");
						
						System.out.print("Enter a key: ");
						key = input.nextLine();
						
						System.out.print("Enter a value: ");
						value = input.nextLine();
						
						size = useTable1 ? table1.size() : table2.size();
						if(useTable1) table1.put(key, value);
						else table2.put(key, value);
						
						System.out.println(((size == (useTable1 ? table1.size() : table2.size())) ? "Updated" : "Added") + " value at key.");
						pauseForUser(input);
						
						break;
					case 2: //get
						System.out.println("----------Getting a Value by Key----------");
						
						System.out.print("Enter a key: ");
						key = input.nextLine();
						
						value = useTable1 ? table1.get(key) : table2.get(key);
						System.out.println((value == null) ? "No such key" : "Associated value is " + value);
						pauseForUser(input);
						
						break;
					case 3: //remove
						System.out.println("----------Removing a Key-Value Pair----------");
						
						System.out.print("Enter a key: ");
						key = input.nextLine();
						
						
						value = useTable1 ? table1.remove(key) : table2.remove(key);
						
						System.out.println((value == null) ? "No such key" : "Removed pair was (" + key + "," + value + ")");
						pauseForUser(input);
						
						break;
					case 4: //resize
						System.out.println("----------Resizing the Table----------");
						
						size = forceIntChoice(input, "Enter a new size: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
						boolean done = useTable1 ? table1.rehash(size) : table2.rehash(size);
						
						System.out.println(done ? "Resized table" : "Unable to resize table to requested size");
						pauseForUser(input);
						
						break;
					case 5: //display
						break;
					case 6: //quit
					default:
						return;
				}
				
				System.out.println("******************************************");
				size = useTable1 ? table1.size() : table2.size();
				int capacity = useTable1 ? table1.getCapacity() : table2.getCapacity();
				System.out.println("Table Size: " + size + ", Capacity: " + capacity);
				System.out.println(useTable1 ? table1.toStringDebug() : table2.toStringDebug());
				System.out.println("******************************************");
				pauseForUser(input);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * pauses the program to give the user time to analyze the program.
	 * @param input the user input
	 */
	private static void pauseForUser(java.util.Scanner input) {
		System.out.println("(Hit <Enter> to Continue)");
		input.nextLine();
	}
	
	/**
	 * makes sure the user enters a reasonable input and handles if not.
	 * @param input user input
	 * @param prompt prompt a meesage if error
	 * @param min minimum val user can enter
	 * @param max maximum val user can enter
	 * @return the user's choice
	 */
	private static int forceIntChoice(java.util.Scanner input, String prompt, int min, int max) {
		int choice = -1;
		while(choice == -1) {
			try {
				System.out.print(prompt);
				choice = Integer.parseInt(input.nextLine());
				if(choice >= min && choice <= max) {
					return choice;
				}
				System.out.println("You must enter an integer between "+min+" and "+max+".");
			}
			catch(RuntimeException e) { }
			System.out.println("You must enter a valid integer.");
		}
		return choice;
	}
}