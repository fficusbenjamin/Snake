package napier.coursework.snake;

import android.content.Context;
import android.os.Vibrator;

public class vibrClass{

    public static void vibrateBob(Context mContext){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 50 milliseconds
        v.vibrate(50);
    }



    public static void vibrateDeath(Context mContext){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }
}
