
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class xox {
	public static int countwin = 0; //counts how many times the user won.
	public static int countlose = 0;//counts how many times the user lost.

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("Welcome to the XOX Game");// welcome game
		Scanner console = new Scanner(System.in); // the console i use to take input from player
		Random rand = new Random(); // the random method to choose player
		char comp = ' '; //represents computer's symbol, now it has a temporary character.
		File f; //represents the file that is chosen by the user.
		String again; //represents what the user wants to play the game again.
		char Symbol = ' '; //represents the user's symbol, now it has a temporary character.
		System.out.print("Would you like to load the board from file or create a new one? (L or C) "); // i ask the player which file (s)he wants.
		String selectFile = console.nextLine().toUpperCase(); // This variable represents if player wants to create a new game or load from another file.
		while (!selectFile.equals("L") && !selectFile.equals("C")) { // This while loops wants new input until the users select L or C. Also it ignores lower case.
			System.out.print("Wrong input! Try again: ");
			selectFile = console.nextLine().toUpperCase();
		} // select file
		if (selectFile.equals("L")) { // if player selects to load from another file, this part will run.

			System.out.print("Please enter the file name: "); // It wants file name.
			String enterFile;
			
			while (true) {
				try { //if there is an error because of loading file this try-catch block will catch the error and want new file name.
					enterFile = console.nextLine(); 			// If the file is not valid, it wants new file name
					f = new File(enterFile);
					if(!f.canRead()) {//checks if this file exists and can be read.
						System.out.print("File is not valid. Please try again: "); // if it doesn't exist, program wants new file name.
						continue;
					}
					if (!filecon(enterFile)) { // after taking new file that exists and can be read, that condition checks if the text on the file is valid to play game.
						System.out.print("File is not valid. Please try again: ");
						continue;
					}
					break;
				} catch (Exception E) {
					System.out.print("File is not valid. Please try again: "); // catch block will catch the error.
					console.next();
					continue;
				}
			}// at the end of the while loop, we will have a new valid file.

			System.out.println("Load successful.");

			Scanner input = new Scanner(f); // we need scanner to read the file.
			String board = ""; // program keeps the game board in this string variable throughout the game.
			while (input.hasNextLine()) { // it runs till the last line of file is taken.
				board += input.nextLine() + "\n"; // with this while loop, program reads the file and keep it into the
													// board variable.
			} // we took the file into the string.
			Symbol = selectSymbol(); // Program calls to selectSymbol method the ask the player if she wants X or O
										// the Symbol variable keeps which one user wants and player plays with this
										// Symbol.
			if (Symbol == 'X')
				comp = 'O'; // This part decides which symbol computer will use according to symbol that player choose.
			else
				comp = 'X'; // If user chooses X, computer will play with O. Otherwise, just the opposite will happen.
			System.out.println("You are player " + Symbol + " and the computer is player " + comp);
			int temp=0;
			do { // If player wants to play again, the game starts to run again.
				temp++;
				if(temp>1) { 	//After first play, a new empty board will be loaded from this part.
					board = "| E | E | E | E |\n" +  		
							"| E | E | E | E |\n" + 
							"| E | E | E | E |\n" + //that's how i keep my board into a string.
							"| E | E | E | E |";
				}
				if (whoPlays(f) == comp && temp==1) { //if the file is a game which was played before. This part decides whose turn is by using whoPlays method. 
					compPlays(board, Symbol, comp);		//if whoPlays method returns Symbol, player will start, 
				} else if (whoPlays(f) == Symbol && temp==1) {//and vice versa.
					playerPlays(board, Symbol, comp);
				} else { //if there are equal X's and O's, player will be chosen randomly.
					boolean selectPlay = rand.nextBoolean();
					if (selectPlay == true) { //if true is chosen, player plays first.
						playerPlays(board, Symbol, comp); // the playerPlays method will run the game, if the player plays first.
					} else {
						compPlays(board, Symbol, comp);// the compPlays method will run the game, if the computer  plays first.
					}

				}
				System.out.print("Do you want to play again? (Y or N) "); //when the game finishes, program asks the player if (s)he wants to play again.
				again = console.nextLine().toUpperCase();
				while (!again.equals("Y") && !again.equals("N")) { //program takes input until users writes Y or N with ignoring lower case.
					System.out.print("Wrong input! Try again: ");
					again = console.nextLine().toUpperCase();
				}
			} while (again.equals("Y")); //if user writes "Y" the game will start by creating a new empty board.
		}
		//at the beginning, if player wants to create a new game, program will run this "else" part, with ignoring the first "if" part.
		else { 
			Symbol = selectSymbol(); //selectSymbol will ask the player which symbol (s)he wants.
			if (Symbol == 'X') // this if/else block decides which symbol computer plays.
				comp = 'O';		
			else
				comp = 'X';																					//now program selected the symbols.
			System.out.println("You are player " + Symbol + " and the computer is player " + comp); 		//and print them.
			
			do {// not file load from
				String board = "| E | E | E | E |\n" +  	//the empty board.It will change every move. If player wants to play again board will be empty again.
						"| E | E | E | E |\n" +   
						"| E | E | E | E |\n" +
						"| E | E | E | E |";

				boolean selectPlayer = rand.nextBoolean();  //first player is chosen randomly, at this part.
				if (selectPlayer == true) {
					playerPlays(board, Symbol, comp); //if selectPlayer is true, player will start first.
				} else {
					compPlays(board, Symbol, comp); // if selectPlayer is false, computer will start first.
				}

				System.out.print("Do you want to play again? (Y or N) "); //when the game finishes, the player is asked if (s)he wants to play again.
				again = console.nextLine().toUpperCase();
				while (!again.equals("Y") && !again.equals("N")) {  //this while loop takes input until the user print "Y" or "N".
					System.out.print("Wrong input! Try again: "); 
					again = console.nextLine().toUpperCase();
				}
			} while (again.equals("Y")); //if player selects "N", the game will finish, otherwise the game will start again with an empty board.

		}
		System.out.println("You: " + countwin + " Computer: " + countlose); //when the game completely finishes (users select "N"), the scores will be printed out at the screen.
		System.out.println("Thanks for playing!");//and program finishes.
	}

	public static String printCoordinate(String board, char symbol, int y, int x) { //This method changes the board according to coordinate which is selected by computer or player.
		Scanner sc = new Scanner(System.in); //program uses this scanner to want input from user.
		while (board.charAt(18 * y + 4 * x - 20) != 'E') { //This equation looks if in the coordinate which is taken is equal E or not. if 
			System.out.print("Wrong input! Try again: ");//if there is not E, program wants new input from user.
			while (true) { //this while block checks it the coordinate is valid. if it is out of board, wants new input.
				try { //try-catch block catches error if the user's input is a string and wants new input which is valid to play game.
					y = sc.nextInt();
					x = sc.nextInt();
					if (y > 4 || x > 4 || y < 1 || x < 1) { //if the user prints larger than these coordinates, program wants new input.
						System.out.print("Wrong input! Try again: ");
						continue;
					}
					break;
				} catch (Exception E) { //catches error and wants new input.
					System.out.print("Wrong input! Try again: ");
					sc.nextLine();
					continue;
				}
			}
		}
		board = board.substring(0, 18 * y + 4 * x - 20) + symbol + board.substring(18 * y + 4 * x - 19); //this part changes the 'E' as 'X' or 'O' when players select a valid coordinate. 
		return board; //finally method returns new board.

	}

	public static String Comp(String board, char comp) { //This method is called when computer plays. It decides how computer will play.
		Random rand = new Random();
		int a = rand.nextInt(4) + 1; //selects 2 coordinates inside the board.
		int b = rand.nextInt(4) + 1;
		while (board.charAt(18 * a + 4 * b - 20) != 'E') { //if these coordinates represent 'X' or 'O'. program selects new coordinates until find 'E'.
			a = rand.nextInt(4) + 1;
			b = rand.nextInt(4) + 1;
		}
		String boardfield = printCoordinate(board, comp, a, b);  //after choosing the coordinates, printCoordinate method will be called to print into the board. 
		return boardfield;  //then the game board will be changed.
	}

	public static String Player(String board, char Symbol) { //This method is called when the player plays. It takes input from user.
		Scanner console = new Scanner(System.in);
		int y, x;
		while (true) { //This part checks if user print valid coordinates.
			try { // try-catch block catches errors that stem from the user's input.
				y = console.nextInt(); //wants 2 coordinates.
				x = console.nextInt();
				if (y > 4 || x > 4 || y < 1 || x < 1) { //checks if the inputs are out of board. if it is, then wants new inputs.
					System.out.print("Wrong input! Try again: ");
					continue;
				}
				break;
			} catch (Exception E) { //catches errors that stem from if the user prints string instead of integer.
				System.out.print("Wrong input! Try again: ");
				console.nextLine();
				continue;
			}
		}

		String boardfield = printCoordinate(board, Symbol, y, x); //after taking valid coordinates, it changes the board by calling the printCoordinate method.
		return boardfield; //return new board.
	}

	public static boolean winCon(String board, char comp) {//This method checks if there is a win or not.
		int i = 2;//i keep 4 variable. i - 3 searching the characters at the board.
		char k;  // k- for the character at 'i'.
		int prev2 = 0; //prev2 keeps the 2 previous location before the 'i'.
		int prev1= 0;//prev1 keeps the 1 previous location before the 'i'.
		boolean con = false; //con -- represents condition. it starts as false. If there a win condition occurs, It returns true then the method stops.
		while (i < board.length()) {  //while loop run until i is equal to the board's length.
			k = board.charAt(i);
			if (k == '|') { i += 2;continue;} //if character is '|', it moves to another valid location.
			//This if part checks if there is a win condition for horizontal lines. 
			if ((board.charAt(prev2) == board.charAt(prev1) && board.charAt(prev2) == k) && (board.charAt(prev2) == 'X' || board.charAt(prev2) == 'O') && i-prev2==8) {
				if (board.charAt(prev2) == comp) {countlose++;} //if there is a win condition and its symbol is equal to the computer's symbol, player loses.
				else {countwin++;} //Also if its symbol is equal to the player's symbol, player wins.
				con = true; //when these conditions occur, condition becomes true which means there is a win.
			}
			if(con) break; //if there is a win, while loop stops.
			prev2 = prev1; prev1= i; i += 4; //otherwise, program changes characters by increasing 'i' and replacing the previous characters.

		}
		i = 2;prev2 = 0;prev1 = 0; //after checking horizontal lines, program resets the variables to check every vertical lines.
		int total = 0;//i keep total to be able to ignore '\n''s while searching the characters.
		while (i <= 32) {
			total++;
			if (total == 5) {i = 20;continue;} //after first line i becomes 20 to ignore \n.
			k = board.charAt(i);
			prev2 =i + 18;  prev1= i + 36; //these 2 variables represents 2 locations after i.
			//This if part checks vertical lines if there is a win condition.
			if ((board.charAt(prev2) == board.charAt(prev1) && board.charAt(prev2) == k) && (board.charAt(prev2) == 'X' || board.charAt(prev2) == 'O')) {
				if (board.charAt(prev2) == comp) {countlose++;} //then program searches who wins. if it is equal to computer's symbol, player loses, and vice versa.
				else {countwin++;}
				con = true; //con becomes true.
			}
			if(con) break; //if con is true, loop stops.
			i += 4; //otherwise, program increases 'i' to next character's location.
		}
		i = 2;prev2 = 0;prev1 = 0;total = 0;//after checking vertical lines, program resets the variables to check every cross conditions.
		while (i <= 32) {
			total++;
			if (total == 5) {i = 20;} //after first line i becomes 20 to ignore \n.
			if (i == 20 || i == 24 || i == 2 || i == 6) { //this part checks from (1,1),(1,2),(2,1) and (2,2) points for left to right diagonals.
				k = board.charAt(i); prev2 = i + 22; prev1 = i + 44; //for left to right diagonals other characters should be placed at these locations so program checks them.
				if ((board.charAt(prev2) == board.charAt(prev1) && board.charAt(prev2) == k) && (board.charAt(prev2) == 'X' || board.charAt(prev2) == 'O')) { 
					if (board.charAt(prev2) == comp) {countlose++;}  //if they're equal program checks who wins according to players' symbols.
					else {countwin++;}
					con = true; //condition becomes true, which means there is a win.
				}
			}
			if(con) break; //if there is a win, loop stops.
			if (i == 10 || i == 14 || i == 28 || i == 32) {			//this part checks from (1,3),(1,4),(2,3) and (2,4) points for right to left diagonals.
				k = board.charAt(i); prev2 = i + 14; prev1 = i + 28; //for right to left diagonals other characters should be placed at these locations so program checks them.
				if ((board.charAt(prev2) == board.charAt(prev1) && board.charAt(prev2) == k) && (board.charAt(prev2) == 'X' || board.charAt(prev2) == 'O')) {
					if (board.charAt(prev2)== comp) {countlose++;} //if they're equal program checks who wins according to players' symbols.
					else {countwin++;}
					con = true; //condition becomes true,which means there is a win.
				}
			} 				
			if(con) break;  //if there is a win, loop stops.
			i += 4; //else i continues to move to another location until loop stops.
		}
		return con; //finally method returns con. if it is true, there is a win. Otherwise it means there is no win. So the game continues.
	}

	public static boolean filecon(String filename) throws FileNotFoundException { //decides if the file that is loaded is valid or not.
		Scanner input = new Scanner(new File(filename)); //i use scanner to be able to read the file.
		int countx = 0;int counto = 0; //Program counts the X's and O's with these variables.
		boolean c = true; //decides if the file is valid or not. (represents condition)
		String boardf = ""; //keeps the data in the file
		while (input.hasNextLine()) {  // This while loop reads the file line by line.
			String line = input.nextLine();
			boardf += line + "\n"; //keeps all data in the file.
			if (line.length() != 17) {c = false;break;} //if line length is not equal to the length of the board's lines, method returns false and stops.
			for (int i = 0; i < line.length(); i++) { //else program reads every character of the line.
				//This if part checks if there is another characters at the coordinates different from X, O or E. If it is loop stops and method returns false.
				if (i % 2 == 0 && i % 4 != 0 && !(line.charAt(i) == 'E' || line.charAt(i) == 'X' || line.charAt(i) == 'O')) {c=false;break;}
				if (i % 4 == 0 && line.charAt(i) != '|') {c = false;break;} //checks if '|' characters are at valid locations.
				if (i % 2 == 1 && line.charAt(i) != ' ') {c = false;break;}  //checks if spaces are at valid locations.
				if (i % 2 == 0 && i % 4 != 0 && (line.charAt(i) == 'E' || line.charAt(i) == 'X' || line.charAt(i) == 'O')) { //counts all X's and O's
					if (line.charAt(i) == 'X')countx++;
					else if (line.charAt(i) == 'O')counto++;}
			}
			
			if (c == false)break;} //if condition is false, loop stops and program returns false
		if (c) { 											//if conditions are still true, 
			if(boardf.length()<71) return false;			//program checks the length of the board loaded from file. If it is not method returns false.
			if (countx - counto > 1 || counto - countx > 1) return false;
			if (isTie(boardf)) return false;				//program checks if the game on the file is finished or not.(the tie condition)
		}
		if(c) {//finally if condition is still true, program checks if there is a win or not. if there is, the game is finished so method returns false.
			if(winCon(boardf,'O') || winCon(boardf,'X')) return false;}
		return c; //if method returns true the file is valid.
}

	public static char whoPlays(File f) throws FileNotFoundException {//decides who plays first.
		Scanner input = new Scanner(f); //to read the file
		int countx = 0;int counto = 0;//These variables counts X's and O's.
		char ch = 'n'; //a temporary variable to keep the symbol.
		while (input.hasNextLine()) {
			String line = input.nextLine(); //reads file line by line.
			for (int i = 0; i < line.length(); i++) { //then reads every character of the line.
				if (i % 2 == 0 && i % 4 != 0 && (line.charAt(i) == 'E' || line.charAt(i) == 'X' || line.charAt(i) == 'O')) { //searches coordinates
					if (line.charAt(i) == 'X')countx++; //counts X's
					else if (line.charAt(i) == 'O') counto++; //counts O's
				}
			}
		}
		if (counto > countx) {ch = 'X';} //if O's are more than X's, the turn is the player's who plays with X.
		else if (counto < countx) {ch = 'O';} //if X's are more than O's, the turn is the player's who plays with O.
		return ch; //returns symbol.
	}

	public static void playerPlays(String board, char Symbol, char comp) {//This method runs if the user starts first.
		int keepcon = countlose; //keepcon keeps how many times the user lost.
		System.out.println("You will start:\n" + board); 
		while (winCon(board, comp) == false) { //this loop runs until there is a win.
			System.out.print("Enter coordinates: ");
			board = Player(board, Symbol); //User plays first and board changes according to his/her moves.
			if (winCon(board, comp) == true) { //after every move, program checks if there is win or not. if there is, it checks who wins. then loop stops.
				if(countlose>keepcon) System.out.println("Computer wins!"); //if countlose bigger than previous countlose, which means user lost.
				else System.out.println(board + "\nYou win!"); //else user won.
				break; //then loop stops.
			}
			if (isTie(board) == true) { //checks if there is tie or not.
				System.out.println(board + "\nTie!");//prints out new board.
				break; //if there is, loop stops.
				}
			board = Comp(board, comp); //Then computer plays and board changes according to its moves.
			System.out.println(board);//prints out new board.
			if (winCon(board, comp) == true) {  //after every move, program checks if there is win or not. if there is,it checks who wins. then loop stops.
				if(countlose>keepcon) System.out.println("Computer wins!"); //if countlose bigger than previous countlose, which means computer won.
				else System.out.println(board + "\nYou win!"); //else user won.
				break; //then loop stops.
				}
			if (isTie(board) == true) { //checks if there is tie or not.
				System.out.println("Tie!"); 
				break; //if there is, loop stops.
			}
		}
	}

	public static void compPlays(String board, char Symbol, char comp) {//This method runs if the computer starts first.
		System.out.println("Computer will start.");
		int keepcon=countlose; //keepcon keeps how many times the user lost.
		while (winCon(board, comp) == false) {//this loop runs until there is a win.

			board = Comp(board, comp); //Computer plays first and board changes according to its moves.
			System.out.println(board);//prints out new board.

			if (winCon(board, comp) == true) { //after every move, program checks if there is win or not. if there is, it checks who wins. then loop stops.
				if(countlose>keepcon) System.out.println("Computer wins!");//if countlose bigger than previous countlose, which means computer won.
				else System.out.println(board + "\nYou win!");//else user won.

				break; //then loop stops.
			}
			if (isTie(board) == true) {//checks if there is tie or not.
				System.out.println("Tie!");

				break;//if there is, loop stops.
			}
			System.out.print("Enter coordinates: ");
			board = Player(board, Symbol); //Then user plays and board changes according to his/her moves.
			if (winCon(board, comp) == true) {  //after every move, program checks if there is win or not. if there is,it checks who wins. then loop stops.
				if(countlose>keepcon) System.out.println("Computer wins!"); //if countlose bigger than previous countlose, which means user lost.
				else System.out.println(board + "\nYou win!"); //else user won


				break;//then loop stops.
			}
			if (isTie(board) == true) {//checks if there is tie or not.
				System.out.println(board + "\nTie!"); //prints out new board.
				break;//if there is, loop stops.
			}
		}

	}

	public static char selectSymbol() {//This method runs when user selects symbol.
		Scanner console = new Scanner(System.in); //to want input from user.
		String selectSymbol; //asks user which symbol (s)he wants.
		System.out.print("Enter your symbol: (X or O) ");
		selectSymbol = console.nextLine().toUpperCase(); //wants input from user.
		 while (!selectSymbol.equals("X") && !selectSymbol.equals("O")){ //This loop wants new input until the user selects X or O.
			System.out.print("Wrong input! Try again: ");
			selectSymbol = console.nextLine().toUpperCase();
		} 
		return selectSymbol.charAt(0); //finally it returns which symbol if player wants.
	}

	public static boolean isTie(String board) { //checks if there is tie or not.
		int i = 2; //represents characters'locations.
		boolean con = true; //decides the tie condition.
		while (i < board.length()) { 
			if (board.charAt(i) == 'E') { //if there is still E's, which means there is no tie, loop stops and method returns false.
				con = false;
				break;
			}
			i += 2; //else i continues to move next location.
			if (board.charAt(i) == '|') {i += 2;}//ignores | characters that stem from \n.
		} 
		return con; //if there is no tie, method returns true. else, returns false.
	}

}
