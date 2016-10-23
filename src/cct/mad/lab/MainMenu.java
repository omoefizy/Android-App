package cct.mad.lab;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity {

	private static final int SCORE_REQUEST_CODE = 1;// The request code for the intent

	TextView tvScore;
	TextView hiScore;
	String score;
	Intent gameIntent;
    int highScore = 0; 
    SoundPool sp;
	 MediaPlayer mp;
	 int shot = 0;
	 ImageView bird;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_start);
		
	}
	
	 public void shoot(View v){
       
         }
	public void startGame(View v){
		gameIntent = new Intent(this,GameActivity.class);
		gameIntent.putExtra("name", highScore);
	    startActivityForResult(gameIntent, SCORE_REQUEST_CODE );  
	    bird = (ImageView) findViewById(R.id.imageView2);
	       //Check parameters for the soundPool
			sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		    shot = sp.load(this, R.raw.gunshot, 1);
		    mp = MediaPlayer.create(this, R.raw.dove);
		    mp.setLooping(true);
		    mp.start();
		    
	}
    /* Create Options Menu */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	// Respond to item selected on OPTIONS MENU
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		//put data in Intent
		case R.id.easy:
			Toast.makeText(this, "Easy chosen", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.medium:
			Toast.makeText(this, "Medium chosen", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.hard:
			Toast.makeText(this, "Hard chosen", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.other:
			Toast.makeText(this, "Other chosen", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	protected void onActivityResult(int requestCode, int resultCode, Intent retIntent) {
	    // Check which request we're responding to
	    if (requestCode == SCORE_REQUEST_CODE) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	if (retIntent.hasExtra("GAME_SCORE")) {
					int scoreFromGame = retIntent.getExtras().getInt("GAME_SCORE");
					TextView tvScore = (TextView) findViewById(R.id.tvScore);
					tvScore.setText(Integer.toString(scoreFromGame));
	        	}
	        }	
	    }

	}


}
