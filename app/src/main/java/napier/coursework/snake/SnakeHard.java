package napier.coursework.snake;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;


///----------------------------------------------------------------------
///   Class:          SnakeHard (Class)
///   Description:    This is the actual game, the class draws the screen
///                   with a background and updates the movement of the
///                   snake for the hard game
///   Author:         Francesco Fico (40404272)     Date: 03/2019
///----------------------------------------------------------------------


class SnakeHard extends SurfaceView implements Runnable {

    //declaration of new game thread
    private Thread thread = null;

    //new heading for the movements
    public enum Heading {UP, RIGHT, DOWN, LEFT}

    //the snake starts heading to the right
    private Heading heading = Heading.RIGHT;

    //declaration of the variables that stores the screen axis
    private int sX;
    private int sY;

    //declaration of the variables that will store the snake lenght
    private int snkLght;

    //declaration of the axis for the position of the apple
    private int appleX;
    private int appleY;

    //declaration of the snake size in pixel for the segment
    private int snakeSize;

    //size in segment of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    //control pausing between updates
    private long nextFrameTime;

    //update the game 10 times per second (this is the hard mode, very slow)
    private final long FPS = 10;

    //there are 1000 milliseconds in a second
    private long MILLIS_PER_SECOND = 1000;

    //this int stores the player score
    private static int score;

    //this method returns the player score to other activities
    public static int getPlayerScore(){
        return score;
    }

    //the location in the grid of all the segments
    private int[] snkXs;
    private int[] snkYs;

    //boolean that stores the current state of the game
    private volatile boolean isPlaying;

    //canvas for our drawing
    private Canvas canvas;

    //required to use canvas
    private SurfaceHolder surfaceHolder;

    //paint for our drawing
    private Paint paint;

    //method that starts the game
    public SnakeHard(Context context, Point size) {
        super(context);

        //returns the dimension of the screen
        sX = size.x;
        sY = size.y;

        //work out how many pixels each block is
        snakeSize = sX / NUM_BLOCKS_WIDE;

        //how many blocks of the same size will fit into the height
        numBlocksHigh = sY / snakeSize;

        //initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //maximum snake dimension (higher than that the game will simply crash)
        snkXs = new int[200];
        snkYs = new int[200];

        // Start the game
        newGame();
    }

    //override the thread run method
    @Override
    public void run() {

        //while isPlaying is true
        while (isPlaying) {

            // Update 10 times a second
            if(updateRequired()) {
                update();
                draw();
            }
        }
    }

    //method that pauses the thread
    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            //error
        }
    }

    //method that resume the thread
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    //method that creates a new game
    public void newGame() {

        //start with a single snake segment
        snkLght = 1;
        snkXs[0] = NUM_BLOCKS_WIDE / 2;
        snkYs[0] = numBlocksHigh / 2;

        //reset the velocity
        MILLIS_PER_SECOND = 1000;

        //draws the apple
        spwnApple();

        //reset the Score
        score = 0;

        //setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    //method that spawn the apple
    public void spwnApple() {

        //declare a new random object
        Random random = new Random();

        //set the apple coordinate randomly
        appleX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        appleY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    //method that modify the snake when the apple is eat
    public void eatApple() {

        //increase the size of the snake by one
        snkLght++;

        //vibrate short
        VibrClass.vibrateBob(getContext());

        //replace apple
        spwnApple();

        //add to the Score
        score = score + 1;

        //increment the velocity of the snake
        MILLIS_PER_SECOND = MILLIS_PER_SECOND -30;
    }

    //method that move the snake on the screen
    private void moveSnake(){

        //for loop that moves the body
        for (int i = snkLght; i > 0; i--) {

            //start at the back and move to the position of the segment in front of it
            //exclude the head because the head has nothing in front of it
            snkXs[i] = snkXs[i - 1];
            snkYs[i] = snkYs[i - 1];
        }

        //move the head in the appropriate heading
        switch (heading) {
            case UP:
                snkYs[0]--;
                break;

            case RIGHT:
                snkXs[0]++;
                break;

            case DOWN:
                snkYs[0]++;
                break;

            case LEFT:
                snkXs[0]--;
                break;
        }
    }

    //method that determines if the snake died
    boolean ifDead(){

        //boolean set as false
        boolean dead = false;

        //hit the screen edge
        if (snkXs[0] == -1) dead = true;
        if (snkXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snkYs[0] == -1) dead = true;
        if (snkYs[0] == numBlocksHigh) dead = true;

        //eaten itself?
        for (int i = snkLght - 1; i > 0; i--) {
            if ((i > 4) && (snkXs[0] == snkXs[i]) && (snkYs[0] == snkYs[i])) {
                dead = true;
            }
        }
        return dead;
    }

    //method that updates the thread
    public void update() {

        //did the head of the snake eat apple?
        if (snkXs[0] == appleX && snkYs[0] == appleY) {
            eatApple();
        }

        moveSnake();

        //did the snake die?
        if (ifDead()) {

            //long vibrate for the death
            VibrClass.vibrateDeath(getContext());

            //start the activity too see the score
            Intent i = new Intent().setClass(getContext(), Score.class);
            (getContext()).startActivity(i);
        }
    }

    //method that draw the canvas
    public void draw() {

        //get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            //fill the screen with black background to save battery on amoled screen and enrich the contrast
            canvas.drawColor(Color.BLACK);

            //set the color of the paint to draw the snake green #007358
            paint.setColor(Color.argb(255,0,115,88));

            //scale the on screen score text
            paint.setTextSize(100);
            canvas.drawText("Score:" + score, 10, 70, paint);

            //draw the snake one block at a time
            for (int i = 0; i < snkLght; i++) {
                canvas.drawRect(snkXs[i] * snakeSize,
                        (snkYs[i] * snakeSize),
                        (snkXs[i] * snakeSize) + snakeSize,
                        (snkYs[i] * snakeSize) + snakeSize,
                        paint);
            }

            //set the color to draw the apple red
            paint.setColor(Color.RED);

            //draws apple
            canvas.drawRect(appleX * snakeSize,
                    (appleY * snakeSize),
                    (appleX * snakeSize) + snakeSize,
                    (appleY * snakeSize) + snakeSize,
                    paint);

            //unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    //method that determines if an updates is required
    public boolean updateRequired() {

        //if the set time has passed
        if(nextFrameTime <= System.currentTimeMillis()){

            //setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            //return true so that the update and draw functions are executed
            return true;
        }
        return false;
    }

    //method that override the touch event on the canvas
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= sX / 2) {
                    switch(heading){
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch(heading){
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }
}

