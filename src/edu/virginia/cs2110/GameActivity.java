package edu.virginia.cs2110;

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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {
	
	public static int id = 1;
	public float endX;
	public float endY;

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
			
			Character c = new Character(this);
			c.x = endX;
			c.y = endY;
			c.execute();
			
			Drawable leftDrawable = getResources().getDrawable(R.drawable.leftcharacter);
			Drawable rightDrawable = getResources().getDrawable(R.drawable.rightcharacter);
			
			if (endX > startX) {
				findViewById(R.id.character).setBackground(rightDrawable);
			} 
			if (endX < startX) {
				findViewById(R.id.character).setBackground(leftDrawable);
			}
			
//			findViewById(R.id.character).setX(endX-43);
//			findViewById(R.id.character).setY(endY-110);
//			
//			TranslateAnimation animation = new TranslateAnimation((-(endX - startX - 43)), 0, (-(endY - startY - 110)), 0);
//			animation.setDuration(1000);
//			animation.setFillAfter(false);
//			animation.setAnimationListener(new MyAnimationListener());
//			Log.d("AfterAnimation", "x: " + endX + "y: " + endY);
//			findViewById(R.id.character).startAnimation(animation);
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
						int x = (int) (Math.random()*1100);
						int y = (int) (Math.random()*500);
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
	
	private class MyAnimationListener implements AnimationListener{

	    @Override
	    public void onAnimationEnd(Animation animation) {
	    	Log.d("inAnimationListener", "x: " + endX + "y: " + endY);
	    	ImageView character = (ImageView) findViewById(R.id.character);
	        character.setX(endX-43);
	        character.setY(endY-110);
	    }

	    @Override
	    public void onAnimationRepeat(Animation animation) {
	    }

	    @Override
	    public void onAnimationStart(Animation animation) {
	    }

	}
}
