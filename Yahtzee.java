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
		nPlayers = 2;// dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		playerNames[0] = "ann";
		playerNames[1] = "zoe";
		/*
		 * for (int i = 1; i <= nPlayers; i++) { playerNames[i - 1] =
		 * dialog.readLine("Enter name for player " + i); }
		 */
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		int[] totalScore = new int[nPlayers + 1];
		int[] upperScore = new int[nPlayers + 1];
		for (int j = 0; j < N_SCORING_CATEGORIES; j++) {
			for (int i = 1; i <= nPlayers; i++) {
				int player = i;
				int[] dice = rollDice();

				int category = display.waitForPlayerToSelectCategory();
				boolean p = YahtzeeMagicStub.checkCategory(dice, category);
				int score = getScore(p, category);
				totalScore[i] += score;
				display.updateScorecard(category, player, score);
				display.updateScorecard(TOTAL, player, totalScore[i]);
				
				if (category<=SIXES){
					upperScore[i] += score;
					display.updateScorecard(UPPER_SCORE, player, upperScore[i]);
				}
				if (upperScore[i]>3){
					display.updateScorecard(UPPER_BONUS, player, 35);
					totalScore[i] += 35;
					display.updateScorecard(TOTAL, player, totalScore[i]);
				}
			}
		}
	}

	/*private int getScore(boolean match, int category) {
		if (!match) return 0;
		switch(category){
		/*ONES 
		public static final int TWOS = 2;
		public static final int THREES = 3;
		public static final int FOURS = 4;
		public static final int FIVES = 5;
		public static final int SIXES = 6;
		public static final int UPPER_SCORE = 7;
		public static final int UPPER_BONUS = 8;
		public static final int THREE_OF_A_KIND = 9;
		public static final int FOUR_OF_A_KIND = 10;
		public static final int FULL_HOUSE = 11;
		public static final int SMALL_STRAIGHT = 12;
		public static final int LARGE_STRAIGHT = 13;
		public static final int YAHTZEE = 14;
		public static final int CHANCE = 15;
		public static final int LOWER_SCORE = 16;
		public static final int TOTAL = 17;
		}*/
	

	private int[] rollDice() {
		display.waitForPlayerToClickRoll(1);

		int[] dice = new int[5];
		for (int i = 0; i < 5; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		display.displayDice(dice);
		display.printMessage("1st time");

		display.waitForPlayerToSelectDice();
		for (int i = 0; i < 5; i++) {
			if (display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
			}
		}

		display.displayDice(dice);
		display.printMessage("2nd time");

		display.waitForPlayerToSelectDice();
		for (int i = 0; i < 5; i++) {
			if (display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
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
