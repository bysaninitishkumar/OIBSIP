# Number Guessing Game

A Java console application that lets you guess a secret number using three difficulty levels.

## Project Structure

- `src/Main.java`
- `src/NumberGuessingGame.java`
- `src/GameLogic.java`
- `src/Difficulty.java`
- `screenshots/`
- `README.md`
- `.gitignore`

## Difficulty Levels

- Easy: 1–50 with 10 attempts
- Medium: 1–100 with 7 attempts
- Hard: 1–200 with 5 attempts

## How to Run

From the project root:

```powershell
javac -d out src\*.java
java -cp out Main
```

## Notes

This application tracks rounds, wins, losses, and displays a history for each round.
