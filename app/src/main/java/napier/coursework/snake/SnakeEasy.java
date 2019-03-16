package napier.coursework.snake;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


class SnakeEasy extends SurfaceView implements Runnable {

    //Game thread declaration
    private Thread thread = null;

    // For tracking movement Heading
    //
    public enum Heading {UP, RIGHT, DOWN, LEFT}

    // Start by heading to the right
    private Heading heading = Heading.RIGHT;

    // To hold the screen size in pixels
    private int sX;
    private int sY;

    // How long is the snake
    private int snkLght;

    // Where is Bob hiding?
    private int appleX;
    private int appleY;

    // The size in pixels of a snake segment
    private int snakeSize;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    // Control pausing between updates
    private long nextFrameTime;
    // Update the game 60 times per second
    private final long FPS = 1;
    // There are 1000 milliseconds in a second
    private long MILLIS_PER_SECOND = 1000;
    // We will draw the frame much more often

    // How many points does the player have
    private static int score;

    public static int getPlayerScore(){
        return score;
    }


    // The location in the grid of all the segments
    private int[] snkXs;
    private int[] snkYs;

    // Everything we need for drawing
    // Is the game currently playing?
    private volatile boolean isPlaying;

    // A canvas for our paint
    private Canvas canvas;

    // Required to use canvas
    private SurfaceHolder surfaceHolder;

    // Some paint for our canvas
    private Paint paint;

    public SnakeEasy(Context context, Point size) {
        super(context);




        //context = context;

        sX = size.x;
        sY = size.y;

        // Work out how many pixels each block is
        snakeSize = sX / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        numBlocksHigh = sY / snakeSize;

        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // If you Score 200 you are rewarded with a crash achievement!
        snkXs = new int[200];
        snkYs = new int[200];

        // Start the game
        newGame();

    }

    @Override
    public void run() {

        while (isPlaying) {
            // Update 10 times a second
            if(updateRequired()) {
                update();
                draw();

            }
        }
    }


    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {
        // Start with a single snake segment
        snkLght = 1;
        snkXs[0] = NUM_BLOCKS_WIDE / 2;
        snkYs[0] = numBlocksHigh / 2;
        MILLIS_PER_SECOND = 1000;

        // Get Bob ready for dinner
        spwnApple();

        // Reset the Score
        score = 0;

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    public void spwnApple() {
        Random random = new Random();
        appleX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        appleY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    public void eatApple() {


        // Increase the size of the snake
        snkLght++;
        //vibrate short
        VibrClass.vibrateBob(getContext());

        //replace Bob
        spwnApple();
        //add to the Score
        score = score + 1;
        MILLIS_PER_SECOND = MILLIS_PER_SECOND -30;

    }

    private void moveSnake(){
        // Move the body
        for (int i = snkLght; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            snkXs[i] = snkXs[i - 1];
            snkYs[i] = snkYs[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
        }

        // Move the head in the appropriate heading
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

    boolean ifDead(){
        // Has the snake died?
        boolean dead = false;

        // Hit the screen edge
        if (snkXs[0] == -1) dead = true;
        if (snkXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snkYs[0] == -1) dead = true;
        if (snkYs[0] == numBlocksHigh) dead = true;

        // Eaten itself?
        for (int i = snkLght - 1; i > 0; i--) {
            if ((i > 4) && (snkXs[0] == snkXs[i]) && (snkYs[0] == snkYs[i])) {
                dead = true;

            }
        }

        return dead;

    }

    public void update() {
        // Did the head of the snake eat Bob?
        if (snkXs[0] == appleX && snkYs[0] == appleY) {
            eatApple();
        }

        moveSnake();

        if (ifDead()) {

            Intent i = new Intent().setClass(getContext(), Score.class);
            (getContext()).startActivity(i);
            VibrClass.vibrateDeath(getContext());
        }
    }

    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Fill the screen with black background to save battery on amoled screen and enrich the contrast
            canvas.drawColor(Color.BLACK);

            // Set the color of the paint to draw the snake green #007358
            paint.setColor(Color.argb(255,0,115,88));

            // Scale the HUD text
            paint.setTextSize(100);
            canvas.drawText("Score:" + score, 10, 70, paint);

            // Draw the snake one block at a time
            for (int i = 0; i < snkLght; i++) {
                canvas.drawRect(snkXs[i] * snakeSize,
                        (snkYs[i] * snakeSize),
                        (snkXs[i] * snakeSize) + snakeSize,
                        (snkYs[i] * snakeSize) + snakeSize,
                        paint);
            }

            // Set the color of the paint to draw apple red
            paint.setColor(Color.RED);

            // Draw apple
            canvas.drawRect(appleX * snakeSize,
                    (appleY * snakeSize),
                    (appleX * snakeSize) + snakeSize,
                    (appleY * snakeSize) + snakeSize,
                    paint);

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {

        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

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

