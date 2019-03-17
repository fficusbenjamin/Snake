package napier.coursework.snake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;


///-------------------------------------------------------------------
///   Class:          SnakeActivityHard (Activity)
///   Description:    This is the activity that start the java class
///                   that is basically the game, in this case the hard
///                   difficulty instance
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///-------------------------------------------------------------------



public class SnakeActivityHard extends Activity {

    //declare an instance of SnakeHard game
    SnakeHard snakeHard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the actual dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        //initialize the result into a new point called size
        Point size = new Point();
        display.getSize(size);

        //declare the previous created instance into a new game
        snakeHard = new SnakeHard(this, size);

        //make snakeHard the view of the Activity
        setContentView(snakeHard);
    }



    //start the thread in snakeHard
    @Override
    protected void onResume() {
        super.onResume();
        snakeHard.resume();
    }

    //stop the thread in snakeHard
    @Override
    protected void onPause() {
        super.onPause();
        snakeHard.pause();
    }

    //this method override the back button to restart the main activity
    @Override
    public void onBackPressed() {
        Intent snakeHard = new Intent(SnakeActivityHard.this, MainActivity.class);
        startActivity(snakeHard);
    }
}