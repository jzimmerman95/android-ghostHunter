package edu.virginia.cs2110;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Bullets extends AsyncTask<Void, Integer, Void> {
	ImageView bullet;

    public Bullets(Activity activity) {
        bullet = (ImageView) activity.findViewById(R.id.bullet);
    }

    protected Void doInBackground(Void... v) {
        while(true) {
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress();
        }
 
    }

    protected void onProgressUpdate(Integer... ints) {
        moveBullets();
    }

    protected void onPostExecute(Void e) {

    }

    public void moveBullets() {
    	
    }
}
