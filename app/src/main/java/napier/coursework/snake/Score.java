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

public class Score extends AppCompatActivity {

    private Button tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int scoreEasy = SnakeEasy.getPlayerScore();
        int scoreMedium = SnakeMedium.getPlayerScore();
        int scoreHard = SnakeHard.getPlayerScore();


        scoreLabel.setText("Latest Game Score: "+score);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
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



        tryAgain = findViewById(R.id.tryAgain);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Score.this, tryAgain);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()){
                            case R.id.easy:
                                Intent snakeEasy = new Intent(Score.this, SnakeActivityEasy.class);
                                startActivity(snakeEasy);
                                return true;

                            case R.id.medium:
                                Intent snakeMedium = new Intent(Score.this, SnakeActivityMedium.class);
                                startActivity(snakeMedium);
                                return true;

                            case R.id.hard:
                                Intent snakeHard = new Intent(Score.this, SnakeActivityHard.class);
                                startActivity(snakeHard);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();

            }
        });



    }





    public void mainMenu(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent snake = new Intent(Score.this, MainActivity.class);
        startActivity(snake);
    }
}
