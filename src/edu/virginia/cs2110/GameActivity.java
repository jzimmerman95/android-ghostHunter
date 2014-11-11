package edu.virginia.cs2110;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
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
}
