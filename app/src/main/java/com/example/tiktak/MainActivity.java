package com.example.tiktak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout gridLayout;
    private TextView playerTurnTextView;
    private TextView winnerTextView;

    private char currentPlayer = 'X';
    private char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        playerTurnTextView = findViewById(R.id.playerTurnTextView);
        winnerTextView = findViewById(R.id.winnerTextView);

        initializeBoard();
        updatePlayerTurnText();
    }

    private void initializeBoard() {
        int buttonIds[] = {
                R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9
        };

        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            button.setOnClickListener(new CellClickListener(i / 3, i % 3));
        }
    }

    private class CellClickListener implements View.OnClickListener {
        private int row;
        private int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            if (board[row][col] == ' ' && !gameOver()) {
                board[row][col] = currentPlayer;
                button.setText(String.valueOf(currentPlayer));



                if (currentPlayer == 'X') {
                    button.setTextColor(getResources().getColor(android.R.color.holo_green_dark)); // Green color
                } else {
                    button.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // Red color
                }



                if (checkWin()) {
                    announceWinner();
                } else if (checkDraw()) {
                    announceDraw();
                } else {
                    switchPlayer();
                    updatePlayerTurnText();
                }
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true;
        }

        return board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0];
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean gameOver() {
        return checkWin() || checkDraw();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void updatePlayerTurnText() {
        playerTurnTextView.setText("Current Player: " + currentPlayer);
    }

    private void announceWinner() {
        winnerTextView.setText("Winner: Player " + currentPlayer);
        winnerTextView.setVisibility(View.VISIBLE);
        showToast("Player " + currentPlayer + " wins!");
        resetBoard();
    }

    private void announceDraw() {
        winnerTextView.setText("It's a draw!");
        winnerTextView.setVisibility(View.VISIBLE);
        showToast("It's a draw!");
        resetBoard();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                int buttonId = getResources().getIdentifier("button" + (i * 3 + j + 1), "id", getPackageName());
                Button button = findViewById(buttonId);
                button.setText("");
            }
        }
        currentPlayer = 'X';
        updatePlayerTurnText();
        winnerTextView.setVisibility(View.INVISIBLE);
    }
}