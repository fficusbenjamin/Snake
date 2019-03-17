package napier.coursework.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


///-------------------------------------------------------------------
///   Class:          InfoActivity (Activity)
///   Description:    This is the info activity for the app, it just
///                   diplays the information contained into the layout
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///-------------------------------------------------------------------

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //it displays the info from the layout
        setContentView(R.layout.activity_info);
    }
}
