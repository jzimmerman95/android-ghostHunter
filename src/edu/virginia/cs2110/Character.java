package edu.virginia.cs2110;

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
    float x, y;
    float vx, vy;
    int X_MIN, X_MAX, Y_MIN, Y_MAX, THRESH;

    public Character(Activity activity) {
        image = (ImageView) activity.findViewById(R.id.character);
        gameOn = true;
        imgx = image.getX();
        imgy = image.getY();
        vx = 5;
        vy = 5;
    }

    protected Void doInBackground(Void... v) {
    	Log.d("in doinbackground", "hi");
        while(gameOn) {
            THRESH = 40;
            X_MIN = THRESH;
            X_MAX = layout.getMeasuredWidth() - image.getMeasuredWidth() - THRESH;
            Y_MIN = THRESH;
            Y_MAX = layout.getMeasuredHeight() - image.getMeasuredHeight() - THRESH;
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress();
            Log.d("x,y", imgx + ", " + imgy);
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
    	Log.d("distance:", "" + d);
    		if (d > 8) {
		    	vx = (x - imgx)/d;
		    	vy = (y - imgy)/d;
		    	
		    	imgx += vx*0.5;
		    	imgy += vy*0.5;
		    	
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
//    public boolean detectCollisionWithPaddle() {
//        float paddle_x_min = paddle.getX()  ;
//        float paddle_x_max = paddle.getX() + paddle.getMeasuredWidth() ;
//        float ball_x = image.getX() + image.getMeasuredWidth()/2;
//        //Log.d("paddle:", paddle_x_min + " to " + paddle_x_max);
//        //Log.d("ball:", ball_x+ "");
//        return ( (ball_x > paddle_x_min) && (ball_x < paddle_x_max));
//    }
}
