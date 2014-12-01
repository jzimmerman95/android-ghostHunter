package edu.virginia.cs2110;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Bullets extends AsyncTask<Void, Void, Integer> {
	ImageView bullet;
	ArrayList<ImageView> ghosts;
	ArrayList<ImageView> bullets = new ArrayList<ImageView>();
	Character c;
	Boolean bulletOn = true;
	float bx, by;
	float gx, gy;
	float vx;
	int score = 0;
	Activity activity;
	TextView scoreView;
	

    public Bullets(Activity a, ArrayList<ImageView> g, Character character) {
    	ghosts = g;
        bullet = (ImageView) a.findViewById(R.id.bullet);
        bullet.setVisibility(View.VISIBLE);
        c = character;
        bx = c.imgx;
        by = c.imgy;
        bullet.setX(bx);
        bullet.setY(by);
        activity = a;
        scoreView = (TextView) activity.findViewById(R.id.scoretext);
    }

    protected Integer doInBackground(Void...v) {
    	// Dean's Comments: Check for collisions
        // If it finds a collision, then just return a number to the onPostExecute, 
    	// that will signify the position of the ghost to remove
        // Check if bullet if off screen, if it is then cancel this task and return -1
        // Otherwise, it will move the bullet
        while(bulletOn) {
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
        

    protected void onProgressUpdate(Void...e) {
    	// Dean's Comment: Just need to move the bullet
    	moveBullets();
    	detectCollisionsWithGhosts();
    }

    protected void onPostExecute(Integer i) {
//    	// Dean's Comment: Remove bullet always
//    	// If collision, remove ghost at int i
//    	// Get relative layout and remove view from the screen
//    	doInBackground(); // need to associate the value returned in this method with i?
//    	
//    	bullet.setVisibility(View.GONE); // Removing bullet?
//
//    	// If there is a collision, remove the ghost
//    	if ( i >= 0 ) {
//    		ImageView currentGhost = ghosts.get(i);
//    		currentGhost.setVisibility(View.GONE);
//    		ghosts.remove(i);
//    	} 
    }

    public void moveBullets() {
    	Log.d("bullet coordinates", "x: " + bx + " y: " + by);
    	// Dean's Comments: How you want to move the bullet
    	if (c.leftFacing == true) 
    		vx = -10;
    	else
    		vx = 10;
    	bx += vx;
    	bullet.setX(bx);
    }
    
    public void detectCollisionsWithGhosts() {
    	Log.d("in detect collisions method", "hi");
    	// For loop to check all of the ghosts in the array list
        for (int i=0; i<ghosts.size(); i++) {
        	Log.d("in detect collisions for loop", "hi");
        	gx = ghosts.get(i).getX();
        	gy = ghosts.get(i).getY();
        	
        	// If there is a collision, return the position of the ghost that needs to removed 
        	// Need to adjust for dimensions for ghost (so it doesn't have to be exactly gx and gy)
        	if (((bx > gx-20) && (bx < gx+20)) && ((by > gy-20) && (by < gy+20))) {
        		Log.d("Collision detected!", "hi");
        		ghosts.get(i).setVisibility(View.GONE);
        		ghosts.remove(i);
        		bullet.setVisibility(View.GONE);
        		bulletOn = false;
        		c.numOfCollisions++;
        	}
        	// if the bullet if off screen
        	else if ( bx < -50 || bx > 2000) {
        		bullet.setVisibility(View.GONE);
        		bulletOn = false;
        	}
        }
    }
}
