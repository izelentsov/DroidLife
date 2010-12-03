package ru.izelentsov.android.lifegame.view.widgets;

import ru.izelentsov.android.lifegame.model.Game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;




public class LifeGrid extends View {

	private Game game = null;
	private Paint linePaint = null;
	private Paint bgPaint = null;
	private IListener listener = null;
	private GestureDetector gestureDetector = null;
	private ScaleGestureDetector scaleDetector = null;
	private Rect boundsRect = null;
	
	
	
	public interface IListener {
		public void cellTouched (int xPos, int yPos);
	}
	
	
	
	private class VoidListener implements IListener {
		@Override
		public void cellTouched (int xPos, int yPos) {
		}
	}
	
	
	
	public LifeGrid (Context context) {
		super (context);
		init ();
	}

	public LifeGrid (Context context, AttributeSet attrs) {
		super (context, attrs);
		init ();
	}

	public LifeGrid (Context context, AttributeSet attrs, int defStyle) {
		super (context, attrs, defStyle);
		init ();
	}
	
	
	
	
	public void setGame (Game aGame) {
		game = aGame;
	}
	
	
	public void setListener (IListener aListener) {
		listener = aListener;
	}
	
	
	private void init () {
		linePaint = new Paint ();
		linePaint.setColor (Color.BLACK);
		linePaint.setStrokeWidth (0);
		
		bgPaint = new Paint ();
		bgPaint.setColor (Color.WHITE);
		
		listener = new VoidListener ();
		
		boundsRect = new Rect (0, 0, 0, 0);
		
		gestureDetector = new GestureDetector (new GestListener ());
		scaleDetector = new ScaleGestureDetector (getContext (), new ScaleListener ());
	}
	
	
	
	@Override
	protected void onLayout (boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			int side = Math.min (right - left, bottom - top);
			boundsRect = new Rect (boundsRect.left, boundsRect.top, side, side);
		}
		super.onLayout (changed, left, top, right, bottom);
	}
	
	
	
	 @Override
	 protected void onDraw (Canvas canvas) {
		 drawBG (canvas);
		 if (game != null) {
			 drawGrid (canvas);
			 drawAliveCells (canvas);
		 }
	 }
	 
	 
	 
	 
	 private void drawBG (Canvas canvas) {
		 canvas.drawRect (boundsRect, bgPaint);
	 }
	 
	 
	 private void drawGrid (Canvas canvas) {
//		 updateBounds ();
		 
		 float x = boundsRect.left;
		 float xStep = (float)((float)boundsRect.width () / (float)game.width ());
		 for (int i = 0; i < game.width () - 1; ++i) {
			 x += xStep; 
			 canvas.drawLine (x, boundsRect.top, x, boundsRect.bottom, linePaint);
		 }
		 
		 float y = boundsRect.top;
		 float yStep = (float)((float)boundsRect.height () / (float)game.height ());
		 for (int j = 0; j < game.height () - 1; ++j) {
			 y += yStep; 
			 canvas.drawLine (boundsRect.left, y, boundsRect.right, y, linePaint);
		 }
	 }
	 
	 
	 private void drawAliveCells (Canvas canvas) {
		 float xStep = (float)((float)boundsRect.width () / (float)game.width ());
		 float yStep = (float)((float)boundsRect.height () / (float)game.height ());
		 int xPos = 0;
		 int yPos = 0;
		 
		 for (int y = 0; y < game.height (); ++y) {
			 for (int x = 0; x < game.width (); ++ x) {
				 if (game.isAlive (x, y)) {
					 xPos = (int)(x * xStep) + boundsRect.left;
					 yPos = (int)(y * yStep) + boundsRect.top;
					 canvas.drawRect (
							 new Rect (xPos, yPos, 
									 (int)(xPos + xStep + 1), (int)(yPos + yStep + 1)), 
							 linePaint);
				 }
			 }
		 }
	 }

	 
	 
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		scaleDetector.onTouchEvent (event);
		boolean ret = false; 
		if (!ret) {
			ret = gestureDetector.onTouchEvent (event);
		}
		return ret;
	}

	
	
	
	private class GestListener implements OnGestureListener {

		@Override
		public boolean onDown (MotionEvent e) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean onFling (MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress (MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll (MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			boundsRect.offset (-(int)distanceX, -(int)distanceY);
			postInvalidate ();
			return true;
		}

		@Override
		public void onShowPress (MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp (MotionEvent e) {
			float xStep = (float)((float)boundsRect.width () / (float)game.width ());
			float yStep = (float)((float)boundsRect.height () / (float)game.height ());
			float gameX = e.getX () - boundsRect.left;
			float gameY = e.getY () - boundsRect.top;
			int xPos = (int)(gameX / xStep);
			int yPos = (int)(gameY / yStep);
			listener.cellTouched (xPos, yPos);
			return true;
		}
		
	}
	
	
	
	
	private class ScaleListener extends 
	ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public void onScaleEnd (ScaleGestureDetector detector) {
			int delta = (int)(boundsRect.width () * (detector.getScaleFactor () - 1));
			boundsRect.inset (delta, delta);
			
		}
		
	}




	
}
