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
		
		int player =1;
		int[] dice = rollDice();
		
		int category = display.waitForPlayerToSelectCategory();
		boolean p = YahtzeeMagicStub.checkCategory(dice, category);
		int score = getScore(p, category);
		display.updateScorecard(category, player, score);
	}
	private int getScore(boolean match, int category){
		if(match){
			return 100;
		}
		return 0;
	}
	private int[] rollDice(){
		display.waitForPlayerToClickRoll(1);
		
		int[] dice = {1,1,1,1,1};
		for(int i=0;i<5;i++){
			dice[i]=rgen.nextInt(1,6);
		}
		display.displayDice(dice);
		display.printMessage("1st time");
		
		display.waitForPlayerToSelectDice();
		for(int i=0;i<5;i++){
			if(display.isDieSelected(i)){
			dice[i]=rgen.nextInt(1,6);		
			}
		}
		
		display.displayDice(dice);
		display.printMessage("2nd time");
			
		display.waitForPlayerToSelectDice();
		for(int i=0;i<5;i++){
			if(display.isDieSelected(i)){
			dice[i]=rgen.nextInt(1,6);		
			}
		}
		
		display.displayDice(dice);
		display.printMessage("3rd time. please select category");
		return dice;
		
	}
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
