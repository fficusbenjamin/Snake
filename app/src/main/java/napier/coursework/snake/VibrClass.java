package napier.coursework.snake;

import android.content.Context;
import android.os.Vibrator;


///-------------------------------------------------------------------
///   Class:          VibrClass (Class)
///   Description:    This is the class that sets the two used
///                   vibration settings in the game
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///-------------------------------------------------------------------



public class VibrClass {

    //method that sets the vibration for when an apple is eat
    public static void vibrateBob(Context mContext){

        //creates a new instance of the android vibration class
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 50 milliseconds
        v.vibrate(50);
    }


    //method that sets the vibration for when the snake dies
    public static void vibrateDeath(Context mContext){

        //creates a new instance of the android vibration class
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }
}
