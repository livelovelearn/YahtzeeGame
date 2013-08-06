/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = 1;//dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		playerNames[0]="ann";
		/*for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}*/
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		int[] dice = rollDice();
		boolean p = YahtzeeMagicStub.checkCategory(dice, FULL_HOUSE);
		
		display.displayDice(dice);
	}
		
	private int[] rollDice(){
		display.waitForPlayerToClickRoll(1);
		//int random = rgen.nextInt();
		int[] dice={rgen.nextInt(1,7),rgen.nextInt(1,7),rgen.nextInt(1,7),rgen.nextInt(1,7),rgen.nextInt(1,7)};
		//int[] dice={2,3,4,5,6};
		return dice;
	}
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
