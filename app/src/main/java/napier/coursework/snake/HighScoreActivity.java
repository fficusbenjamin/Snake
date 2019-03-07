package napier.coursework.snake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {



    public static int highScore, playerScore;
    String scoreAsString = Integer.toString(playerScore);
    String highScoreAsString = Integer.toString(highScore);


    public static void SetHighScore(){
        playerScore = SnakeEngine.getPlayerScore();

        if (playerScore > highScore) {
            highScore = playerScore;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        TextView txtScoreView = findViewById(R.id.txtScoreView);
        TextView txtHighScoreView = findViewById(R.id.txtHighScoreView);
        txtScoreView.setText(scoreAsString);
        txtHighScoreView.setText(highScoreAsString);









    }

}
