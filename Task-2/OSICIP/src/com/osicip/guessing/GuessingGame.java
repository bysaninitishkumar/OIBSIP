package com.osicip.guessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GuessingGame {
    private GameDifficulty difficulty;
    private int secretNumber;
    private int currentAttempt;
    private int maxAttempts;
    private boolean roundOver;
    private boolean lastGuessCorrect;

    private int wins;
    private int losses;
    private int roundNumber;
    private final List<String> roundHistory;
    private final Random random;

    public GuessingGame() {
        this.random = new Random();
        this.roundHistory = new ArrayList<>();
        startNewRound(GameDifficulty.MEDIUM);
    }

    public void startNewRound(GameDifficulty difficulty) {
        this.difficulty = difficulty;
        this.maxAttempts = difficulty.getMaxAttempts();
        this.currentAttempt = 0;
        this.roundOver = false;
        this.lastGuessCorrect = false;
        this.roundNumber++;
        this.secretNumber = random.nextInt(difficulty.getMaxValue()) + 1;
    }

    public String makeGuess(int guess) {
        if (roundOver) {
            return "This round is over. Click Play Again to start a new round.";
        }

        if (guess < 1 || guess > difficulty.getMaxValue()) {
            return String.format("Please enter a number between 1 and %d.", difficulty.getMaxValue());
        }

        currentAttempt++;

        if (guess == secretNumber) {
            lastGuessCorrect = true;
            roundOver = true;
            wins++;
            recordRoundHistory(true);
            return String.format("Correct! You guessed the number in %d attempt%s.", currentAttempt, currentAttempt == 1 ? "" : "s");
        }

        if (currentAttempt >= maxAttempts) {
            roundOver = true;
            losses++;
            recordRoundHistory(false);
            return String.format("You Lost! The secret number was %d.", secretNumber);
        }

        if (guess > secretNumber) {
            return String.format("Too High! Attempts left: %d.", getAttemptsRemaining());
        }

        return String.format("Too Low! Attempts left: %d.", getAttemptsRemaining());
    }

    private void recordRoundHistory(boolean win) {
        String result = win ? "Win" : "Loss";
        roundHistory.add(String.format("Round %d: %s - %d/%d attempts (%s)",
                roundNumber,
                difficulty.getLabel(),
                currentAttempt,
                maxAttempts,
                result));
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsRemaining() {
        return Math.max(0, maxAttempts - currentAttempt);
    }

    public boolean isRoundOver() {
        return roundOver;
    }

    public boolean isLastGuessCorrect() {
        return lastGuessCorrect;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<String> getRoundHistory() {
        return Collections.unmodifiableList(roundHistory);
    }

    public String getStatusMessage() {
        if (roundOver) {
            return String.format("Round %d is complete. Click Play Again to continue.", roundNumber);
        }
        return String.format("Round %d: Guess a number from 1 to %d. You have %d attempts.",
                roundNumber,
                difficulty.getMaxValue(),
                maxAttempts);
    }
}
