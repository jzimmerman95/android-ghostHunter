package edu.virginia.cs2110;

//hi

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {
	
	public static int id = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		MediaPlayer mp = MediaPlayer.create(GameActivity.this, R.raw.creepycatacombs);
        mp.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {	
		float tx = event.getX();
		float ty = event.getY();
		
		int action = event.getAction();
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			tx = event.getX();
			ty = event.getY();
			findViewById(R.id.character).setX(tx-45);
			findViewById(R.id.character).setY(ty-134);
			break;
		default:
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public ImageView makeGhost() {
		// Create a LinearLayout in which to add the ImageView
		RelativeLayout rL = (RelativeLayout) findViewById(R.id.gamelayout);

		// Instantiate an ImageView and define its properties
		ImageView i = new ImageView(this);
		i.setImageResource(R.drawable.ghost);
		i.setId(id++);
		i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
		i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
		LayoutParams.WRAP_CONTENT));

		// Add the ImageView to the layout and set the layout as the content view
		rL.addView(i);
		setContentView(rL);
		return i;
	}
	
	Timer timer;
	TimerTask timerTask;
	final Handler handler = new Handler();
	
	@Override
	public void onResume() {
		super.onResume();
		startTimer();
	}
	
	public void startTimer(){
		timer = new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 5000, 5000);
	}
	
	public void initializeTimerTask(){
		timerTask = new TimerTask() {
			public void run(){
				handler.post(new Runnable(){
					public void run(){
						
						ImageView img = makeGhost();
						int x = (int) (Math.random()*250);
						int y = (int) (Math.random()*300);
						img.setX(x);
						img.setY(y);
					}
				});
			}
		};
	}
	
	public void stopTimerTask(View v) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
}
