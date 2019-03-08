package napier.coursework.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int score = SnakeEngine.getPlayerScore(); //getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

            if (score > highScore) {
                highScoreLabel.setText("High Score: " + score);

                //save
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("HIGH_SCORE", score);
                editor.commit();
            } else {
                highScoreLabel.setText("High Score: " + highScore);
            }

    }

    public void tryAgain(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), SnakeActivity.class));

    }

    @Override
    public void onBackPressed() {

        Intent snake = new Intent(Score.this, MainActivity.class);
        startActivity(snake);
    }
}
