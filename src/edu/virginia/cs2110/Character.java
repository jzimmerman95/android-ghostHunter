package edu.virginia.cs2110;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.virginia.cs2110.R.layout;

public class Character extends AsyncTask<Void, Integer, Void>{
    ImageView image;
    boolean gameOn;
    RelativeLayout layout;
    float imgx, imgy;
    boolean leftFacing = false;
    float x, y;
    float vx, vy;
    float gx, gy;
    int X_MIN, X_MAX, Y_MIN, Y_MAX, THRESH;
    int numOfCollisions;
    ArrayList<ImageView> ghosts;
    Activity a;

    public Character(Activity activity, Boolean bool, ArrayList<ImageView> g) {
        image = (ImageView) activity.findViewById(R.id.character);
        gameOn = true;
        imgx = image.getX();
        imgy = image.getY();
        leftFacing = bool;
        numOfCollisions = 0;
        a = activity;
        ghosts = g;
    }

    protected Void doInBackground(Void... v) {
    	Log.d("in doinbackground", "hi");
        while(gameOn) {
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress();
            
        }
        return null;
    }

    protected void onProgressUpdate(Integer... ints) {
        moveCharacter();
        detectCollisionWithGhost();
        updateScore();
    }

    protected void onPostExecute(Void e) {

    }

    public void moveCharacter() {
    	float d = (float) Math.sqrt((imgx-x)*(imgx-x) + (imgy-y)*(imgy-y));
    		if (d > 8) {
		    	vx = (x - imgx)/d;
		    	vy = (y - imgy)/d;
		    	
		    	imgx += vx*5;
		    	imgy += vy*5;
		    	
		        image.setX(imgx);
		        image.setY(imgy);
    		}
    }
    
    public void detectCollisionWithGhost() {
    	for (int i=0; i<ghosts.size(); i++) {
        	gx = ghosts.get(i).getX();
        	gy = ghosts.get(i).getY();
        	
        	// If there is a collision, return the position of the ghost that needs to removed 
        	// Need to adjust for dimensions for ghost (so it doesn't have to be exactly gx and gy)
        	if (((imgx > gx-20) && (imgx < gx+20)) && ((imgy > gy-40) && (imgy < gy+20))) {
        		gameOn = false;
        		Toast.makeText(a, "You lose! Final score: " + numOfCollisions*50,
        				   Toast.LENGTH_LONG).show();
        	}
        } 
    }
    
    public void updateScore() {
    	TextView score = (TextView) a.findViewById(R.id.scoretext);
		score.setText("Score: " + numOfCollisions*50);
    }
}
