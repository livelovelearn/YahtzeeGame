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
				int score = getScore(p, category, dice);
				totalScore[i] += score;
				display.updateScorecard(category, player, score);
				display.updateScorecard(TOTAL, player, totalScore[i]);

				if (category <= SIXES) {
					upperScore[i] += score;
					display.updateScorecard(UPPER_SCORE, player, upperScore[i]);
				}
				if (upperScore[i] > 63) {
					display.updateScorecard(UPPER_BONUS, player, 35);
					totalScore[i] += 35;
					display.updateScorecard(TOTAL, player, totalScore[i]);
				}
			}
		}
	}

	private int getScore(boolean match, int category, int[] dice) {
		if (!match)
			return 0;
		int score = 0;
		int[] drawer = new int[7];
		switch (category) {
		case ONES:
			for (int i = 0; i < N_DICE; i++)
				if (dice[i] == 1)
					score += 1;
			return score;
		case TWOS:
			for (int i = 0; i < N_DICE; i++)
				if (dice[i] == 2)
					score += 2;
			return score;
		case THREES:
			for (int i = 0; i < N_DICE; i++)
				if (dice[i] == 3)
					score += 3;
			return score;
		case FOURS:
			for (int i = 0; i < N_DICE; i++)
				if (dice[i] == 4)
					score += 4;
			return score;
		case FIVES:
			for (int i = 0; i < N_DICE; i++)
				if (dice[i] == 5)
					score += 5;
			return score;
		case SIXES:
			for (int i = 0; i < N_DICE; i++)
				if (dice[i] == 6)
					score += 6;
			return score;
		case THREE_OF_A_KIND:
			for (int i = 0; i < N_DICE; i++) {
				drawer[dice[i]]++;
				score += dice[i];
			}
			for (int i = 1; i <= 5; i++) {
				if (drawer[i] >= 3)
					return score;
			}
			return 0;
		case FOUR_OF_A_KIND:
			for (int i = 0; i < N_DICE; i++) {
				drawer[dice[i]]++;
				score += dice[i];
			}
			for (int i = 1; i <= 5; i++) {
				if (drawer[i] >= 4)
					return score;
			}
			return 0;
		case FULL_HOUSE:
			for (int i = 0; i < N_DICE; i++) {
				drawer[dice[i]]++;
				score += dice[i];
			}
			int emptyDrawer = 0;
			for (int i = 1; i <= 5; i++) {
				if (drawer[i] == 0)
					emptyDrawer++;
			}
			for (int i = 1; i <= 5; i++) {
				if (drawer[i] == 4)
					emptyDrawer = -1;
			}
			if (emptyDrawer == 3)
				return 25;
			return 0;
		case SMALL_STRAIGHT:
			for (int i = 0; i < N_DICE; i++) {
				drawer[dice[i]]++;
			}
			if (linked4(1, drawer) || linked4(2, drawer) || linked4(1, drawer))
				return 30;
			return 0;
		case LARGE_STRAIGHT:
			for (int i = 0; i < N_DICE; i++) {
				drawer[dice[i]]++;
			}
			if (linked5(1, drawer) || linked5(2, drawer))
				return 30;
			return 0;
		case YAHTZEE:
			for (int i = 1; i < N_DICE; i++)
				if (dice[0] != dice[i])
					return 0;
			return 50;

		case CHANCE:
			for (int i = 0; i < N_DICE; i++)
				score += dice[i];
			return score;
		default:
			return 0;

		}
	}

	private boolean linked4(int start, int[] drawer) {
		return drawer[start++] == 0 || drawer[start++] == 0
				|| drawer[start++] == 0 || drawer[start++] == 0;
	}

	private boolean linked5(int start, int[] drawer) {
		return drawer[start++] == 0 || drawer[start++] == 0
				|| drawer[start++] == 0 || drawer[start++] == 0
				|| drawer[start++] == 0;
	}

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
