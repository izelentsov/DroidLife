package ru.izelentsov.android.lifegame.view;

import ru.izelentsov.android.lifegame.R;
import ru.izelentsov.android.lifegame.logic.GameController;
import ru.izelentsov.android.lifegame.model.Game;
import ru.izelentsov.android.lifegame.view.widgets.LifeGrid;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class GameView {

	
	private interface IGameViewController {
		public void modelChanged ();
		public void modelUpdated ();
	}
	
	
	private Game game = null;
	private GameController gameController = null;
	private GameListener gameListener = null;
	
	private IGameViewController gameViewController = null;
	
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
		
		gameViewController = new LifeGridController (
				(LifeGrid) anActivity.findViewById (R.id.gridview));
//		gameViewController = new GridAdapterController (anActivity, 
//				(GridView) anActivity.findViewById(R.id.gridview));
		
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
					gameViewController.modelChanged ();
				}
			});
		}

		@Override
		public void generationChanged () {
			rootView.post (new Runnable () {
				@Override
				public void run () {
					genCountValue.setText (String.valueOf (game.generationNumber ()));
					gameViewController.modelUpdated ();
				}
			});
		}
	}
	
	

	
	private class LifeGridController 
	implements IGameViewController, LifeGrid.IListener {

		private LifeGrid grid = null;
		
		public LifeGridController (LifeGrid aGrid) {
			grid = aGrid;
			grid.setGame (game);
			grid.setListener (this);
		}
		
		@Override
		public void modelChanged () {
			grid.invalidate ();
		}

		@Override
		public void modelUpdated () {
			grid.invalidate ();
		}
		
		@Override
		public void cellTouched (int xPos, int yPos) {
			gameController.cellToggleRequested (xPos, yPos);			
		}
	}
	
	
	
//	private class GridAdapterController
//	implements IGameViewController, GridViewAdapter.IListener {
//
//		private GridViewAdapter gridAdapter = null;
//		
//		
//		public GridAdapterController (Activity anActivity, GridView aGrid) {
//			gridAdapter = new GridViewAdapter (anActivity, aGrid, game);
//			gridAdapter.setListener (this);
//			gridAdapter.resetGrid ();
//		}
//		
//		
//		@Override
//		public void cellTouched (int xPos, int yPos) {
//			gameController.cellToggleRequested (xPos, yPos);
//		}
//
//		@Override
//		public void modelChanged () {
//			gridAdapter.resetGrid ();
//			gridAdapter.notifyDataSetChanged ();
//		}
//
//		@Override
//		public void modelUpdated () {
//			gridAdapter.notifyDataSetChanged ();
//		}
//	}
	
}
