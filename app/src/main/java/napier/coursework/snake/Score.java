package napier.coursework.snake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

///-------------------------------------------------------------------
///   Class:          MainActivity (Activity)
///   Description:    This is the score activity, it shows messages
///                   from the game about the latest game score and
///                   sores the highscore of previous game stored on the
///                   device. It also diplays buttons to retry to play
///                   or navigate on the main menu.
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///-------------------------------------------------------------------

public class Score extends AppCompatActivity {

    //declare a new button
    private Button tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //declaration of the textviews and relative assignment from the layout file
        TextView EasyScoreLabel = findViewById(R.id.easyScoreLabel);
        TextView MediumScoreLabel = findViewById(R.id.mediumScoreLabel);
        TextView HardScoreLabel = findViewById(R.id.hardScoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        //declaration for the score integers and assignment from the method that return them from the game activity
        int scoreEasy = SnakeEasy.getPlayerScore();
        int scoreMedium = SnakeMedium.getPlayerScore();
        int scoreHard = SnakeHard.getPlayerScore();

        //set the content of the previous created textlabels
        EasyScoreLabel.setText("Latest Easy Game Score: "+scoreEasy);
        MediumScoreLabel.setText("Latest Medium Game Score: "+scoreMedium);
        HardScoreLabel.setText("Latest Hard Game Score: "+scoreHard);

        //declared a new instance of SharedPreferences
        SharedPreferences settings = getSharedPreferences("GAME_DATA", MODE_PRIVATE);

        // creates a new int highscore and stores it into the shared preferences on the device
        int highScore = settings.getInt("HIGH_SCORE", 0);

            //below the 3 checks for the highscore against the game's current score
            if (scoreEasy > highScore) {

                //if the easygame score is higher than the highscore
                highScoreLabel.setText("High Score: " + scoreEasy);

                //save
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("HIGH_SCORE", scoreEasy);
                editor.commit();

                //if the mediumgame score is higher than the highscore
            } else if (scoreMedium > highScore) {
                highScoreLabel.setText("High Score: " + scoreMedium);

                //save
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("HIGH_SCORE", scoreMedium);
                editor.commit();

                //if the hardgame score is higher than the highscore
            } else if (scoreHard > highScore) {
                highScoreLabel.setText("High Score: " + scoreHard);

                //save
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("HIGH_SCORE", scoreHard);
                editor.commit();

                //if none of them is higher the score stored remains the same
            }else {
                highScoreLabel.setText("High Score: " + highScore);
            }



        //assign the new created button to the one in the layout
        tryAgain = findViewById(R.id.tryAgain);

        //creates a click listener on the button
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //declare the popup menu that shows up when the button is clicked
                PopupMenu popupMenu = new PopupMenu(Score.this, tryAgain);

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
                                Intent snakeEasy = new Intent(Score.this, SnakeActivityEasy.class);
                                startActivity(snakeEasy);
                                return true;

                            //medium case clicked
                            case R.id.medium:

                                //starts the snake activity for the medium game
                                Intent snakeMedium = new Intent(Score.this, SnakeActivityMedium.class);
                                startActivity(snakeMedium);
                                return true;

                            //hard case clicked
                            case R.id.hard:

                                //starts the snake activity for the hard game
                                Intent snakeHard = new Intent(Score.this, SnakeActivityHard.class);
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
    }

    //method that assign the action for the the mainMenu button in the layout
    public void mainMenu(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    //this method override the back button to restart the main activity
    @Override
    public void onBackPressed() {
        finish();
        Intent snake = new Intent(Score.this, MainActivity.class);
        startActivity(snake);
    }
}
