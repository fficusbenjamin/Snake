package napier.coursework.snake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;


public class SnakeActivityHard extends Activity {

    // Declare an instance of SnakeGame
    SnakeHard snakeHard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the SnakeGame class
        snakeHard = new SnakeHard(this, size);

        // Make snakeGame the view of the Activity
        setContentView(snakeHard);
    }



    // Start the thread in snakeGame
    @Override
    protected void onResume() {
        super.onResume();
        snakeHard.resume();
    }

    // Stop the thread in snakeGame
    @Override
    protected void onPause() {
        super.onPause();
        snakeHard.pause();
    }
    // Override the back button to go to the MainMenuF
    @Override
    public void onBackPressed() {
        Intent snakeHard = new Intent(SnakeActivityHard.this, MainActivity.class);
        startActivity(snakeHard);
    }



}