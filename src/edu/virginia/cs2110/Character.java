package edu.virginia.cs2110;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    int X_MIN, X_MAX, Y_MIN, Y_MAX, THRESH;
    int numOfCollisions;

    public Character(Activity activity, Boolean bool) {
        image = (ImageView) activity.findViewById(R.id.character);
        gameOn = true;
        imgx = image.getX();
        imgy = image.getY();
        leftFacing = bool;
        numOfCollisions = 0;
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

//    //update the scoreboard
//    public void incrementScore() {
//        final String STUB = "Score: ";
//        String score_text = (String) scoreboard.getText();
//        int score = Integer.parseInt(score_text.substring(STUB.length()));
//        score++;
//        scoreboard.setText(STUB + score);
//    }
    
//    public boolean detectCollisionWithGhost() {
//        float paddle_x_min = ghosts.getX()  ;
//        float paddle_x_max = paddle.getX() + paddle.getMeasuredWidth() ;
//        float ball_x = image.getX() + image.getMeasuredWidth()/2;
//        //Log.d("paddle:", paddle_x_min + " to " + paddle_x_max);
//        //Log.d("ball:", ball_x+ "");
//        return ( (ball_x > paddle_x_min) && (ball_x < paddle_x_max));
//    }
}
