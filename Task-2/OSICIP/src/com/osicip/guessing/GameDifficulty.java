package com.osicip.guessing;

public enum GameDifficulty {
    EASY("Easy", 50, 10),
    MEDIUM("Medium", 100, 7),
    HARD("Hard", 200, 5);

    private final String label;
    private final int maxValue;
    private final int maxAttempts;

    GameDifficulty(String label, int maxValue, int maxAttempts) {
        this.label = label;
        this.maxValue = maxValue;
        this.maxAttempts = maxAttempts;
    }

    public String getLabel() {
        return label;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public String toString() {
        return label;
    }

    public static GameDifficulty fromLabel(String label) {
        for (GameDifficulty difficulty : values()) {
            if (difficulty.label.equalsIgnoreCase(label)) {
                return difficulty;
            }
        }
        return MEDIUM;
    }
}
