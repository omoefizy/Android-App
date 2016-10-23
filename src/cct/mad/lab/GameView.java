package cct.mad.lab;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

/**
 * This class takes care of surface for drawing and touches
 * 
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	/* Member (state) fields   */
	private GameLoopThread gameLoopThread;
	private Paint paint; //Reference a paint object 
    /** The drawable to use as the background of the animation canvas */
    private Bitmap mBackgroundImage = BitmapFactory.decodeResource(this.getResources(),
    		R.drawable.whitebacky);
   
		
    private Sprite sprite;
    private int hitCount;
    private boolean gameOver;
    private String djeasy;
    private ArrayList<Sprite> spritesArrayList;
	public GameView(Context context) 
	{
		super(context);
		// Focus must be on GameView so that events can be handled.
		this.setFocusable(true);
		// For intercepting events on the surface.
		this.getHolder().addCallback(this);
	}
	 /* Called immediately after the surface created */
	public void surfaceCreated(SurfaceHolder holder)
	{
		mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage, 
				getWidth(), getHeight(), true);
		// We can now safely setup the game start the game loop.
		ResetGame();//Set up a new game up - could be called by a 'play again option'
		gameLoopThread = new GameLoopThread(this.getHolder(), this);
		gameLoopThread.running = true;
		gameLoopThread.start();
	}
		
	//To initialise/reset game
	private void ResetGame()
	{
		/* Set paint details */
		sprite = new Sprite(this);
	    paint = new Paint();
		paint.setColor(Color.RED); 
		paint.setTextSize(20); 
		hitCount = 0;
		//Set timer
		startTime = 10;//Start at 10s to count down
		//Create new object - convert startTime to milliseconds
		countDownTimer=new MyCountDownTimer(startTime*1000,interval);
		countDownTimer.start();//Start it running
		timerRunning = true;
		gameOver = false;
		

	}
	//This class updates and manages the assets prior to drawing - called from the Thread
	public void update()
	{
			sprite.update();
	}
	
	/**
	 * To draw the game to the screen
	 * This is called from Thread, so synchronisation can be done
	 */
	public void doDraw(Canvas canvas) 
	{
		//Draw all the objects on the canvas
		canvas.drawBitmap(mBackgroundImage, 0, 0, null);
		sprite.draw(canvas); 
		canvas.drawText("The Game ",5, 25, paint);
		canvas.drawText("Numbers of hit: " + String.valueOf(hitCount), 
				100, 50, paint);
		canvas.drawText(displayTime, 120, 80, paint);
		canvas.drawText(djeasy, 50,400,paint);
		//if(gameOver = true ){
			//canvas.drawText("If Bird Stop Flying Then Gave Is Over", 
				//	100,400, paint);
			//canvas.drawText("Press Back Key To Return To Main Menu To View Scores",
					//100, 450, paint);
		}
				
	//}
	
	//To be used if we need to find where screen was touched
	public boolean onTouchEvent(MotionEvent event) 
	{
		if (sprite.wasItTouched(event.getX(), event.getY()))
		{
		/* For now, just renew the Sprite */
				sprite = new Sprite(this);
		    	   	hitCount++; 		
		    	}
			return true;
		}
	/* For the countdown timer */
	private long  startTime ;			//Timer to count down from
	private final long interval = 1 * 1000; 	//1 sec interval
	private CountDownTimer countDownTimer; 	//Reference to class
	private boolean timerRunning = false;
	private String displayTime; 		//To display time on the screen
   
	/* Countdown Timer - private class */
	private class MyCountDownTimer extends CountDownTimer
	{

	  public MyCountDownTimer(long startTime, long interval)
	  {
			super(startTime, interval);
	  }
	  public void onFinish() {
			displayTime = "Times Over!";
			djeasy= "Press Back Key To Return To Main Menu To View Scores";
			timerRunning = false;
			countDownTimer.cancel();
			gameOver = true;
			gameLoopThread.running = false;
			
	  }
	  public void onTick(long millisUntilFinished)
	  {
			displayTime = " " + millisUntilFinished / 1000;
	  }
	}//End of MyCountDownTimer


	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		gameLoopThread.running = false;
		
		// Shut down the game loop thread cleanly.
		boolean retry = true;
		while(retry) {
			try {
				gameLoopThread.join();
				retry = false;
			} catch (InterruptedException e) {}
		}
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}
	public int getHitCount() {
		
		return hitCount;
	}



}
