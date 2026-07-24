public enum Difficulty {
    EASY(1, 50, 10),
    MEDIUM(1, 100, 7),
    HARD(1, 200, 5);

    private final int minValue;
    private final int maxValue;
    private final int maxAttempts;

    Difficulty(int minValue, int maxValue, int maxAttempts) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.maxAttempts = maxAttempts;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
