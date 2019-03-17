package napier.coursework.snake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;


///-------------------------------------------------------------------
///   Class:          SnakeActivityMedium (Activity)
///   Description:    This is the activity that start the java class
///                   that is basically the game, in this case the medium
///                   difficulty instance
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///-------------------------------------------------------------------



public class SnakeActivityMedium extends Activity {

    //declare an instance of SnakeMedium game
    SnakeMedium snakeMedium;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the actual dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        //initialize the result into a new point called size
        Point size = new Point();
        display.getSize(size);

        //declare the previous created instance into a new game
        snakeMedium = new SnakeMedium(this, size);

        //make snakeGame the view of the Activity
        setContentView(snakeMedium);
    }



    //start the thread in snakeMedium
    @Override
    protected void onResume() {
        super.onResume();
        snakeMedium.resume();
    }

    //stop the thread in snakeMedium
    @Override
    protected void onPause() {
        super.onPause();
        snakeMedium.pause();
    }

    //this method override the back button to restart the main activity
    @Override
    public void onBackPressed() {
        Intent snakeMedium = new Intent(SnakeActivityMedium.this, MainActivity.class);
        startActivity(snakeMedium);
    }
}