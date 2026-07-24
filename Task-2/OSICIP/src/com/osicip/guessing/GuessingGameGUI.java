package com.osicip.guessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessingGameGUI {
    private final GuessingGame game;
    private final JFrame frame;
    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel roundLabel;
    private JLabel attemptsLabel;
    private JLabel winsLabel;
    private JLabel lossesLabel;
    private JTextArea historyArea;
    private JComboBox<GameDifficulty> difficultyCombo;
    private JButton guessButton;
    private JButton playAgainButton;

    public GuessingGameGUI() {
        game = new GuessingGame();
        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 520);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = buildTopPanel();
        JPanel centerPanel = buildCenterPanel();
        JPanel bottomPanel = buildBottomPanel();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        updateUIState();
        frame.setVisible(true);
    }

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        roundLabel = new JLabel();
        attemptsLabel = new JLabel();
        winsLabel = new JLabel();
        lossesLabel = new JLabel();
        statusPanel.add(roundLabel);
        statusPanel.add(attemptsLabel);
        statusPanel.add(winsLabel);
        statusPanel.add(lossesLabel);

        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        difficultyPanel.add(new JLabel("Difficulty:"));
        difficultyCombo = new JComboBox<>(GameDifficulty.values());
        difficultyCombo.setSelectedItem(game.getDifficulty());
        difficultyCombo.addActionListener(this::onDifficultyChanged);
        difficultyPanel.add(difficultyCombo);

        topPanel.add(statusPanel);
        topPanel.add(difficultyPanel);
        return topPanel;
    }

    private JPanel buildCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.add(new JLabel("Your Guess:"));
        guessField = new JTextField(12);
        inputPanel.add(guessField);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(this::onGuessClicked);
        inputPanel.add(guessButton);
        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(this::onPlayAgainClicked);
        inputPanel.add(playAgainButton);

        messageLabel = new JLabel();
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD, 14f));

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(messageLabel, BorderLayout.CENTER);

        return centerPanel;
    }

    private JPanel buildBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Round History"));
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        return bottomPanel;
    }

    private void onDifficultyChanged(ActionEvent event) {
        GameDifficulty selectedDifficulty = (GameDifficulty) difficultyCombo.getSelectedItem();
        if (selectedDifficulty != null && selectedDifficulty != game.getDifficulty()) {
            game.startNewRound(selectedDifficulty);
            updateUIState();
            messageLabel.setText(String.format("Difficulty set to %s. New round started.", selectedDifficulty.getLabel()));
        }
    }

    private void onGuessClicked(ActionEvent event) {
        String text = guessField.getText().trim();
        if (text.isEmpty()) {
            messageLabel.setText("Please enter a valid number.");
            return;
        }

        try {
            int guess = Integer.parseInt(text);
            String result = game.makeGuess(guess);
            messageLabel.setText(result);
            guessField.setText("");
            updateUIState();
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid input. Enter an integer value.");
        }
    }

    private void onPlayAgainClicked(ActionEvent event) {
        GameDifficulty selectedDifficulty = (GameDifficulty) difficultyCombo.getSelectedItem();
        if (selectedDifficulty == null) {
            selectedDifficulty = GameDifficulty.MEDIUM;
        }
        game.startNewRound(selectedDifficulty);
        updateUIState();
        messageLabel.setText("A new round has begun. Good luck!");
    }

    private void updateUIState() {
        roundLabel.setText(String.format("Round: %d", game.getRoundNumber()));
        attemptsLabel.setText(String.format("Attempts: %d/%d", game.getCurrentAttempt(), game.getMaxAttempts()));
        winsLabel.setText(String.format("Wins: %d", game.getWins()));
        lossesLabel.setText(String.format("Losses: %d", game.getLosses()));
        difficultyCombo.setSelectedItem(game.getDifficulty());
        historyArea.setText(String.join("\n", game.getRoundHistory()));

        if (game.isRoundOver()) {
            guessButton.setEnabled(false);
            guessField.setEnabled(false);
        } else {
            guessButton.setEnabled(true);
            guessField.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessingGameGUI::new);
    }
}
