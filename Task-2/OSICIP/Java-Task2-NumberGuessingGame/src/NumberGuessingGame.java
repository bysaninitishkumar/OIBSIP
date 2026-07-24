import java.util.Scanner;

public class NumberGuessingGame {
    private final Scanner scanner;
    private GameLogic currentGame;
    private int roundNumber;
    private int wins;
    private int losses;

    public NumberGuessingGame() {
        this.scanner = new Scanner(System.in);
        this.roundNumber = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public void start() {
        System.out.println("Welcome to the Number Guessing Game!");

        while (true) {
            Difficulty difficulty = promptDifficulty();
            currentGame = new GameLogic(difficulty);
            roundNumber++;

            playRound();
            if (!promptPlayAgain()) {
                break;
            }
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private Difficulty promptDifficulty() {
        System.out.println("Select difficulty level:");
        for (Difficulty level : Difficulty.values()) {
            System.out.printf("%d. %s (1-%d, %d attempts)%n",
                    level.ordinal() + 1,
                    level,
                    level.getMaxValue(),
                    level.getMaxAttempts());
        }

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= Difficulty.values().length) {
                    return Difficulty.values()[choice - 1];
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid selection. Please choose 1, 2, or 3.");
        }
    }

    private void playRound() {
        System.out.printf("Round %d - Difficulty: %s%n", roundNumber, currentGame.getDifficulty());
        System.out.printf("Guess a number from %d to %d. You have %d attempts.%n",
                currentGame.getDifficulty().getMinValue(),
                currentGame.getDifficulty().getMaxValue(),
                currentGame.getMaxAttempts());

        while (!currentGame.isRoundOver()) {
            System.out.print("Enter your guess: ");
            try {
                int guess = Integer.parseInt(scanner.nextLine().trim());
                String response = currentGame.makeGuess(guess);
                System.out.println(response);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if (currentGame.getHistory().get(currentGame.getHistory().size() - 1).startsWith("Correct")) {
            wins++;
        } else {
            losses++;
        }

        displayRoundSummary();
    }

    private boolean promptPlayAgain() {
        System.out.print("Would you like to play again? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.startsWith("y");
    }

    private void displayRoundSummary() {
        System.out.printf("Round %d complete. Attempts used: %d/%d.%n",
                roundNumber,
                currentGame.getAttemptsUsed(),
                currentGame.getMaxAttempts());
        System.out.printf("Wins: %d, Losses: %d.%n", wins, losses);
        System.out.println("Round History:");
        for (String entry : currentGame.getHistory()) {
            System.out.println(entry);
        }
        System.out.println();
    }
}
