package napier.coursework.snake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class HighScoreActivity extends AppCompatActivity {


    int playerScore = SnakeEngine.getPlayerScore();
    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*public void SetHighScore(){
            if(playerScore > highScore){

            }

        }*/




    }

}
