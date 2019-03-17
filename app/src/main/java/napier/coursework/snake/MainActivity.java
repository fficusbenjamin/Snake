package napier.coursework.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;



///-------------------------------------------------------------------
///   Class:          MainActivity (Activity)
///   Description:    This is the main activity for the application,
///                   it shows the logo and the three button that are
///                   needed to navigate the application itself,
///                   to access the game, the scores and the about
///                   section.
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///-------------------------------------------------------------------



public class MainActivity extends AppCompatActivity {

    //declare the 3 buttons in the activity
    private Button btnPlay, btnScore, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //associate the button with the one draw in the layout
        btnPlay = findViewById(R.id.btnPlay);

        //set a click listener for the play button
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //declare the popup menu that shows up when the button is clicked
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnPlay);

                //gather the menu items from the layout file in the res folder
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                //set a click listener for the popupmenu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                       //switch case for the popupmenu
                       switch(item.getItemId()){

                           //easy case clicked
                           case R.id.easy:

                               //starts the snake activity for the easy game
                               Intent snakeEasy = new Intent(MainActivity.this, SnakeActivityEasy.class);
                               startActivity(snakeEasy);
                               return true;

                           //medium case clicked
                           case R.id.medium:

                               //starts the snake activity for the medium game
                               Intent snakeMedium = new Intent(MainActivity.this, SnakeActivityMedium.class);
                               startActivity(snakeMedium);
                               return true;

                           //hard case clicked
                           case R.id.hard:

                               //starts the snake activity for the hard game
                               Intent snakeHard = new Intent(MainActivity.this, SnakeActivityHard.class);
                               startActivity(snakeHard);
                               return true;

                           default:
                               return false;
                       }
                    }
                });

                //shows the actual popupmenu
                popupMenu.show();
            }
        });

        //associate the button with the one draw in the layout
        btnScore = findViewById(R.id.btnScore);

        //set a click listener for the highscore button
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //starts the highscore activity
                Intent score = new Intent(MainActivity.this, Score.class);
                startActivity(score);
            }
        });

        //associate the button with the one draw in the layout
        btnAbout = findViewById(R.id.btnAbout);

        //set a click listener for the about button
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //starts the info activity
                Intent about = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(about);
            }
        });
    }

    //this method override the back button to restart the main activity
    @Override
    public void onBackPressed() {

        Intent snake = new Intent(MainActivity.this, MainActivity.class);
        startActivity(snake);
    }
}
