package edu.virginia.cs2110;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Joystick extends SurfaceView implements
		SurfaceHolder.Callback {

	// For Joystick top and bottom
	Bitmap top;
	Bitmap bottom;
	
	// For "x" and "y" position text
	Paint paint1;
	
	int zeroX, zeroY, radius;
	float x, y, dx, dy, c, angle;
	boolean run = true;
	String tag = "debugging";
	
	// For ASyncTask
	MySurfaceThread thread;
	
	
	// surfaceView constructors
	public Joystick(Context context) {
		super(context);
		init();
	}

	public Joystick(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public Joystick(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	// Initializes our surfaceView
	public void init() {
		// Starts ASyncTask
		thread = new MySurfaceThread(getHolder(), this);
		getHolder().addCallback(this);
		
		// Creates the "x" and "y" position text
		paint1 = new Paint();
		paint1.setTextSize(10);
		paint1.setColor(Color.rgb(255, 0, 0));
		
		/*
		 * Defines how far the center of the Joystick may travel
		 * relative to the center of the base
		 */
		radius = 75;	
		dx = dy = 0;
		
		// Defines our Joystick top and bottom pictures
		top = BitmapFactory.decodeResource(getResources(), R.drawable.top);
		bottom = BitmapFactory
				.decodeResource(getResources(), R.drawable.bottom);
	}

	// For implement callbacks
	public void surfaceCreated(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder arg0) {
		thread.execute((Void[]) null);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceDestroyed(SurfaceHolder arg0) {

	}

	// Creates our Joystick
	public void onDraw(Canvas canvas, float x, float y, int zx, int zy) {

		canvas.drawRGB(255, 255, 255);
		canvas.drawBitmap(bottom,
				canvas.getWidth() / 2 - bottom.getWidth() / 2,
				canvas.getHeight() / 2 - bottom.getHeight() / 2, null);
		canvas.drawText(Float.toString(x), 20, 20, paint1);
		canvas.drawText(Float.toString(y), 20, 30, paint1);
		if (dx == 0 && dy == 0) {
			canvas.drawBitmap(top, canvas.getWidth() / 2 - top.getWidth() / 2,
					canvas.getHeight() / 2 - top.getHeight() / 2, null);
		} else {
			canvas.drawBitmap(top, x - top.getWidth() / 2, y - top.getHeight()
					/ 2, null);
		}
		// Log.i(tag, "onDraw " + Float.toString(x) + " " + Float.toString(y));
	}

	// ASyncTask for handling if the user touches the Joystick view or not
	public class MySurfaceThread extends AsyncTask<Void, Void, Void> {
		SurfaceHolder mSurfaceHolder;
		Joystick jStick;

		public MySurfaceThread(SurfaceHolder sh, Joystick jst) {
			mSurfaceHolder = sh;
			jStick = jst;
			x = y = 0;
			jst.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent e) {
					x = e.getX();
					y = e.getY();

					calculateValues(x, y);

					switch (e.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_DOWN:
						break;
					case MotionEvent.ACTION_UP:
						x = y = 0;
						dx = dy = 0;
						break;
					case MotionEvent.ACTION_CANCEL:
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					}
					return true;
				}

				private void calculateValues(float xx, float yy) {
					dx = xx - zeroX;
					dy = yy - zeroY;
					angle = (float) Math.atan(Math.abs(dy / dx));
					c = FloatMath.sqrt(dx * dx + dy * dy);
					if (c > radius) {
						if (dx > 0 && dy > 0) { // Bottom Right
							xx = (float) (zeroX + (radius * FloatMath
									.cos(angle)));
							yy = (float) (zeroY + (radius * FloatMath
									.sin(angle)));
						} else if (dx > 0 && dy < 0) { // Top Right
							xx = (float) (zeroX + (radius * FloatMath
									.cos(angle)));
							yy = (float) (zeroY - (radius * FloatMath
									.sin(angle)));
						} else if (dx < 0 && dy > 0) { // Bottom Left
							xx = (float) (zeroX - (radius * FloatMath
									.cos(angle)));
							yy = (float) (zeroY + (radius * FloatMath
									.sin(angle)));
						} else if (dx < 0 && dy < 0) { // Top Left
							xx = (float) (zeroX - (radius * FloatMath
									.cos(angle)));
							yy = (float) (zeroY - (radius * FloatMath
									.sin(angle)));
						}
					} else {
						xx = zeroX + dx;
						yy = zeroY + dy;
					}
					x = xx;
					y = yy;
				}
			});
		}

		@Override
		protected Void doInBackground(Void... params) {
			while (run) {
				Canvas canvas = null;
				try {
					canvas = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						zeroX = canvas.getWidth() / 2;
						zeroY = canvas.getHeight() / 2;
						jStick.onDraw(canvas, x, y, zeroX, zeroY);
					}
					Thread.sleep(10);
				} catch (InterruptedException e) {

				} finally {
					if (canvas != null) {
						mSurfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
			return null;
		}
	}
}
