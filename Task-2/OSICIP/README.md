# Number Guessing Game

A Java Swing application that lets the player guess a secret number within a limited number of attempts.

## Features

- Easy, Medium, and Hard difficulty levels
- Feedback for each guess: Too High, Too Low, Correct
- Round tracking with wins, losses, and history
- Play Again button to start new rounds rapidly

## Project Structure

- `src/com/osicip/guessing/GameDifficulty.java`
- `src/com/osicip/guessing/GuessingGame.java`
- `src/com/osicip/guessing/GuessingGameGUI.java`

## Run

From the project root:

```powershell
javac -d out src\com\osicip\guessing\*.java
java -cp out com.osicip.guessing.GuessingGameGUI
```

If you prefer an IDE, import the `src` folder as a Java project and run `GuessingGameGUI`.
