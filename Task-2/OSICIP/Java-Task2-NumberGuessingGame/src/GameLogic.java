import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private final Difficulty difficulty;
    private final int secretNumber;
    private final int maxAttempts;
    private int attemptsUsed;
    private boolean roundOver;
    private final List<String> history;

    public GameLogic(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.maxAttempts = difficulty.getMaxAttempts();
        this.secretNumber = new Random().nextInt(difficulty.getMaxValue() - difficulty.getMinValue() + 1) + difficulty.getMinValue();
        this.attemptsUsed = 0;
        this.roundOver = false;
        this.history = new ArrayList<>();
    }

    public String makeGuess(int guess) {
        if (roundOver) {
            return "The round is over. Start a new game to continue.";
        }

        attemptsUsed++;

        if (guess == secretNumber) {
            roundOver = true;
            history.add(String.format("Correct in %d attempt%s.", attemptsUsed, attemptsUsed == 1 ? "" : "s"));
            return "Correct! You won this round.";
        }

        if (attemptsUsed >= maxAttempts) {
            roundOver = true;
            history.add(String.format("Lost after %d attempts. Secret number was %d.", attemptsUsed, secretNumber));
            return String.format("You Lost! The correct number was %d.", secretNumber);
        }

        if (guess > secretNumber) {
            return String.format("Too High! Attempts left: %d.", maxAttempts - attemptsUsed);
        }
        return String.format("Too Low! Attempts left: %d.", maxAttempts - attemptsUsed);
    }

    public boolean isRoundOver() {
        return roundOver;
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
}
