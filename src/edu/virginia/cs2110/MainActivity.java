package edu.virginia.cs2110;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.creepycatacombs);
        mp.start();
    }
    
    public void gameView1(View view) {
    	Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", "easy");
    	startActivity(intent);
    }
    
    public void gameView2(View view) {
    	Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", "medium");
    	startActivity(intent);
    }
    
    public void gameView3(View view) {
    	Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("difficulty", "hard");
    	startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    
    
}
