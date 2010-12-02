package ru.izelentsov.android.lifegame.view;

import ru.izelentsov.android.lifegame.R;
import ru.izelentsov.android.lifegame.logic.GameController;
import ru.izelentsov.android.lifegame.model.Game;
import ru.izelentsov.android.lifegame.view.widgets.LifeGrid;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;



public class GameView {

	
	private Game game = null;
	private GameController gameController = null;
	private GameListener gameListener = null;
	
//	private GridAdapter gridAdapter = null;
	private LifeGrid grid = null;
	
	private View rootView = null;
	private Button runButton = null;
	private Button stepButton = null;
	private Button clearButton = null;
	
	private TextView genCountValue = null;
	
	
	
	public GameView (Game aGame, GameController aGameController) {
		game = aGame;
		gameController = aGameController;
		gameListener = new GameListener ();
		game.setListener (gameListener);
	}
	
	
	public void activate (Activity anActivity) {
		anActivity.setContentView (R.layout.main);
		
//		gridAdapter = new GridAdapter (anActivity, 
//				(GridView) anActivity.findViewById(R.id.gridview));
//		gridAdapter.resetGrid ();
		
		grid = (LifeGrid) anActivity.findViewById (R.id.gridview);
		grid.setGame (game);
		grid.setListener (new GridListener ());
		
		
		rootView = (View) anActivity.findViewById (R.id.rootview);
		setupButtons (anActivity);
		
		genCountValue = (TextView) anActivity.findViewById (R.id.genCountValue);
	}
	
	
	
	private void setupButtons (Activity anActivity) {
    	stepButton = (Button) anActivity.findViewById (R.id.stepBtn);
    	stepButton.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				gameController.stepRequested ();
			}
		});
    	
    	clearButton = (Button) anActivity.findViewById (R.id.clearBtn);
    	clearButton.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				gameController.clearRequested ();
			}
		});
    	
    	runButton = (Button) anActivity.findViewById (R.id.runBtn);
    	runButton.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				if (gameController.isRunning ()) {
					runButton.setText (R.string.runBtnLabel);
					stepButton.setEnabled (true);
					clearButton.setEnabled (true);
				} else {
					runButton.setText (R.string.stopBtnLabel);
					stepButton.setEnabled (false);
					clearButton.setEnabled (false);
				}
				gameController.runToggleRequested ();
			}
		});
    }
	
	
	
	

	private class GameListener implements Game.IListener {

		@Override
		public void newGeneration () {
			rootView.post (new Runnable () {
				@Override
				public void run () {
					genCountValue.setText (String.valueOf (game.generationNumber ()));
//					gridAdapter.resetGrid ();
//					gridAdapter.notifyDataSetChanged ();
					grid.invalidate ();
				}
			});
		}

		@Override
		public void generationChanged () {
			rootView.post (new Runnable () {
				@Override
				public void run () {
					genCountValue.setText (String.valueOf (game.generationNumber ()));
//					gridAdapter.notifyDataSetChanged ();
					grid.invalidate ();
				}
			});
		}
	}
	
	
	
	private class GridAdapter extends BaseAdapter {
		
	    private Context context = null;
	    private GridView grid = null;
		private int curColumnsNum = 0;

	    
	    public GridAdapter (Context c, GridView aGridView) {
	        context = c;
	        grid = aGridView;
	        aGridView.setAdapter (this);
	        aGridView.setOnItemClickListener (new OnItemClickListener () {
		        public void onItemClick (AdapterView<?> parent, View v, 
		        		int position, long id) {
			        int xPos = position % game.width ();
			        int yPos = position / game.width ();	        	
		        	gameController.cellToggleRequested (xPos, yPos);
		        }
		    });
	    }

	    @Override
	    public int getCount() {
	        int count = game.height () * game.width ();
	    	System.out.println ("Count: " + count);
	    	return count;
	    }

	    @Override
	    public Object getItem(int position) {
	        return null;
	    }

	    @Override
	    public long getItemId(int position) {
	        int rowId = position / game.width ();
	    	System.out.println ("Row id: " + rowId);
	    	return rowId;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView view = null;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            view = new ImageView (context);
	            view.setPadding(0, 0, 0, 0);
	            view.setAdjustViewBounds (true);
	        } else {
	            view = (ImageView)convertView;
	        }

	        int xPos = position % game.width ();
	        int yPos = position / game.width ();
	        view.setImageResource (game.isAlive (xPos, yPos) ? 
	        		R.drawable.cell_alive : R.drawable.cell_blank);

	        view.setBackgroundColor (0xFFFF0000);
	        return view;
	    }

	    public void resetGrid () {
			if (game.width () != curColumnsNum) {
				curColumnsNum = game.width ();
				grid.setNumColumns (curColumnsNum);
			}
		}
	  
	}
	
	
	
	
	
	private class GridListener implements LifeGrid.IListener {

		@Override
		public void cellTouched (int xPos, int yPos) {
			gameController.cellToggleRequested (xPos, yPos);			
		}
		
	}
	
}
