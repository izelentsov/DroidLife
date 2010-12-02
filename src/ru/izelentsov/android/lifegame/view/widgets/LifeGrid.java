package ru.izelentsov.android.lifegame.view.widgets;

import ru.izelentsov.android.lifegame.model.Game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;




public class LifeGrid extends View {

	private Game game = null;
	private Paint linePaint = null;
	private Paint bgPaint = null;
	private IListener listener = null;
	private GestureDetector gestureDetector = null;
	private Point basePoint = null;
	
	
	
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
		
		basePoint = new Point (0, 0);
		
		gestureDetector = new GestureDetector (new GestListener ());
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
		 Rect rect = new Rect ();
		 getDrawingRect (rect);
		 canvas.drawRect (rect, bgPaint);
	 }
	 
	 
	 private void drawGrid (Canvas canvas) {
		 int width = getWidth ();
		 int height = getHeight ();
		 
		 float x = basePoint.x;
		 float xStep = (float)((float)width / (float)game.width ());
		 for (int i = 0; i < game.width () - 1; ++i) {
			 x += xStep; 
			 canvas.drawLine (x, 0, x, height, linePaint);
		 }
		 
		 float y = basePoint.y;
		 float yStep = (float)((float)height / (float)game.height ());
		 for (int j = 0; j < game.height () - 1; ++j) {
			 y += yStep; 
			 canvas.drawLine (0, y, width, y, linePaint);
		 }
	 }

	 
	 private void drawAliveCells (Canvas canvas) {
		 float xStep = (float)((float)getWidth () / (float)game.width ());
		 float yStep = (float)((float)getHeight () / (float)game.height ());
		 int xPos = 0;
		 int yPos = 0;
		 
		 for (int y = 0; y < game.height (); ++y) {
			 for (int x = 0; x < game.width (); ++ x) {
				 if (game.isAlive (x, y)) {
					 xPos = (int)(x * xStep) + basePoint.x;
					 yPos = (int)(y * yStep) + basePoint.y;
					 canvas.drawRect (
							 new Rect (xPos, yPos, 
									 (int)(xPos + xStep), (int)(yPos + yStep)), 
							 linePaint);
				 }
			 }
		 }
	 }

	 
	 
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		return gestureDetector.onTouchEvent (event);
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
			basePoint.offset (-(int)distanceX, -(int)distanceY);
			postInvalidate ();
			return true;
		}

		@Override
		public void onShowPress (MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp (MotionEvent e) {
			float xStep = (float)((float)getWidth () / (float)game.width ());
			float yStep = (float)((float)getHeight () / (float)game.height ());
			int xPos = (int)(e.getX () / xStep);
			int yPos = (int)(e.getY () / yStep);
			listener.cellTouched (xPos, yPos);
			return true;
		}
		
	}
	
}
