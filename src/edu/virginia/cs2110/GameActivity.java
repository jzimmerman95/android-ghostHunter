package edu.virginia.cs2110;

//hi

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

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
	
	public ImageView makeGhost() {
		ImageView ghost = new ImageView(this);
		ghost.setImageResource(R.drawable.ghost);
		ghost.setId(id++);
		return ghost;
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
						
						ImageView img = (ImageView) findViewById(R.id.ghost);
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
