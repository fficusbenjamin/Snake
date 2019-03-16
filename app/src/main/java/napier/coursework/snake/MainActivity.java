package napier.coursework.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {


    private Button btnPlay, btnScore, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnPlay);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                       switch(item.getItemId()){
                           case R.id.easy:
                               Intent snakeEasy = new Intent(MainActivity.this, SnakeActivityEasy.class);
                               startActivity(snakeEasy);
                               return true;

                           case R.id.medium:
                               Intent snakeMedium = new Intent(MainActivity.this, SnakeActivityMedium.class);
                               startActivity(snakeMedium);
                               return true;

                           case R.id.hard:
                               Intent snakeHard = new Intent(MainActivity.this, SnakeActivityHard.class);
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

        btnScore = findViewById(R.id.btnScore);
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent score = new Intent(MainActivity.this, Score.class);
                startActivity(score);
            }
        });

        btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(about);
            }
        });
    }


    @Override
    public void onBackPressed() {

        Intent snake = new Intent(MainActivity.this, MainActivity.class);
        startActivity(snake);
    }
}
