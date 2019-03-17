package napier.coursework.snake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;


///-------------------------------------------------------------------
///   Class:          SnakeActivityEasy (Activity)
///   Description:    This is the activity that start the java class
///                   that is basically the game, in this case the easy
///                   difficulty instance
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///-------------------------------------------------------------------



public class SnakeActivityEasy extends Activity {

    //declare an instance of SnakeEasy game
    SnakeEasy snakeEasy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the actual dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        //initialize the result into a new point called size
        Point size = new Point();
        display.getSize(size);

        //declare the previous created instance into a new game
        snakeEasy = new SnakeEasy(this, size);

        //make snakeEasy the view of the Activity
        setContentView(snakeEasy);
    }



    //start the thread in snakeEasy
    @Override
    protected void onResume() {
        super.onResume();
        snakeEasy.resume();
    }

    //stop the thread in snakeEasy
    @Override
    protected void onPause() {
        super.onPause();
        snakeEasy.pause();
    }

    //this method override the back button to restart the main activity
    @Override
    public void onBackPressed() {
        Intent snakeEasy = new Intent(SnakeActivityEasy.this, MainActivity.class);
        startActivity(snakeEasy);
    }
}