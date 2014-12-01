package edu.virginia.cs2110;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import edu.virginia.cs2110.R.layout;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	public static int id = 1;
	public float endX;
	public float endY;
	public Character c;
	public boolean leftFacing = false;
	public ArrayList<ImageView> ghosts = new ArrayList<ImageView>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		MediaPlayer mp = MediaPlayer.create(GameActivity.this, R.raw.creepycatacombs);
        mp.start();
        c = new Character(this, leftFacing, ghosts);
        c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        
        Button b = (Button) findViewById(R.id.button1);
        
        b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MediaPlayer mp2 = MediaPlayer.create(GameActivity.this, R.raw.gunsoundeffect);
				mp2.start();
				Bullets bullet = new Bullets(GameActivity.this, ghosts, c);
				bullet.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
        	
        });
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
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouchEvent(MotionEvent event) {	
		float startX = findViewById(R.id.character).getX();
		float startY = findViewById(R.id.character).getY();
		
		int action = event.getAction();
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			endX = event.getX();
			endY = event.getY();
			
			c.x = endX-40;
			c.y = endY-110;
			
			Drawable leftDrawable = getResources().getDrawable(R.drawable.leftcharacter);
			Drawable rightDrawable = getResources().getDrawable(R.drawable.rightcharacter);
			
			if (endX > startX) {
				findViewById(R.id.character).setBackground(rightDrawable);
				c.leftFacing = false;
			} 
			if (endX < startX) {
				findViewById(R.id.character).setBackground(leftDrawable);
				c.leftFacing = true;
			}
			
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
		return i;
	}
	
	Timer timer;
	TimerTask timerTask;
	final Handler handler = new Handler();
	
	@Override
	public void onResume() {
		double difficulty = 0;
		super.onResume();
		Bundle extras = getIntent().getExtras();
		String val = extras.getSerializable("difficulty").toString();
		if(val.equals("easy")) {
			difficulty = 1;
		} else if (val.equals("medium")) {
			difficulty = 2;
		} else if (val.equals("hard")) {
			difficulty = 3;
		}
		startTimer(difficulty);
	}
	
	public void startTimer(double val){
		timer = new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 5000, (long) (5000/val));
	}
	
	public void initializeTimerTask(){
		timerTask = new TimerTask() {
			public void run(){
				handler.post(new Runnable(){
					public void run(){
						ImageView img = makeGhost();
						int x = (int) (Math.random()*1100);
						int y = (int) (Math.random()*500);
						img.setX(x);
						img.setY(y);
						ghosts.add(img);
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
