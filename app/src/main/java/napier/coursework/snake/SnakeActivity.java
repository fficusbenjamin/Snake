package napier.coursework.snake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;


public class SnakeActivity extends Activity {

    // Declare an instance of SnakeGame
    SnakeGame snakeGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the SnakeGame class
        snakeGame = new SnakeGame(this, size);

        // Make snakeGame the view of the Activity
        setContentView(snakeGame);
    }



    // Start the thread in snakeGame
    @Override
    protected void onResume() {
        super.onResume();
        snakeGame.resume();
    }

    // Stop the thread in snakeGame
    @Override
    protected void onPause() {
        super.onPause();
        snakeGame.pause();
    }
    // Override the back button to go to the MainMenuF
    @Override
    public void onBackPressed() {
        Intent snake = new Intent(SnakeActivity.this, MainActivity.class);
        startActivity(snake);
    }



}