package napier.coursework.snake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;


public class SnakeActivity extends Activity {

    // Declare an instance of SnakeEngine
    SnakeEngine snakeEngine;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();


        //vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //vibrator.vibrate(50);

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the SnakeEngine class
        snakeEngine = new SnakeEngine(this, size);

        // Make snakeEngine the view of the Activity
        setContentView(snakeEngine);




    }



    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        snakeEngine.resume();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        snakeEngine.pause();
    }

    @Override
    public void onBackPressed() {
        Intent snake = new Intent(SnakeActivity.this, MainActivity.class);
        startActivity(snake);
    }



}