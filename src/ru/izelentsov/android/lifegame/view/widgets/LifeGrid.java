package ru.izelentsov.android.lifegame.view.widgets;

import ru.izelentsov.android.lifegame.model.Game;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;




public class LifeGrid extends View {

	private Game game = null;
	private Paint linePaint = null;
	private Paint bgPaint = null;
	private IListener listener = null;
	
	
	
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
		
		setOnTouchListener (new TouchListener ());
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
		 
		 float x = 0;
		 float xStep = (float)((float)width / (float)game.width ());
		 for (int i = 0; i < game.width () - 1; ++i) {
			 x += xStep; 
			 canvas.drawLine (x, 0, x, height, linePaint);
		 }
		 
		 float y = 0;
		 float yStep = (float)((float)height / (float)game.height ());
		 for (int j = 0; j < game.height () - 1; ++j) {
			 y += yStep; 
			 canvas.drawLine (0, y, width, y, linePaint);
		 }
	 }

	 
	 private void drawAliveCells (Canvas canvas) {
		 float xStep = (float)((float)getWidth () / (float)game.width ());
		 float yStep = (float)((float)getHeight () / (float)game.height ());
		 
		 for (int y = 0; y < game.height (); ++y) {
			 for (int x = 0; x < game.width (); ++ x) {
				 if (game.isAlive (x, y)) {
					 canvas.drawRect (
							 new Rect ((int)(x * xStep), (int)(y * yStep), 
									 (int)((x + 1) * xStep), (int)((y + 1) * yStep)), 
							 linePaint);
				 }
			 }
		 }
	 }

	 
	 
	 private class TouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch (View aView, MotionEvent anEvent) {
			if (anEvent.getAction () == MotionEvent.ACTION_DOWN) {
				float xStep = (float)((float)getWidth () / (float)game.width ());
				float yStep = (float)((float)getHeight () / (float)game.height ());
				int xPos = (int)anEvent.getX () / (int)xStep;
				int yPos = (int)anEvent.getY () / (int)yStep;
				listener.cellTouched (xPos, yPos);
				return true;
			} else {
				return false;
			}
		}

		 
	 }
	 
	
}
