package ru.izelentsov.android.lifegame.view.widgets;

import ru.izelentsov.android.lifegame.R;
import ru.izelentsov.android.lifegame.model.Game;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;



public class GridViewAdapter extends BaseAdapter {

    private GridView grid = null;
	private int curColumnsNum = 0;
	private Game game = null;
	private IListener listener = null;
	
	
	public interface IListener {
		public void cellTouched (int xPos, int yPos);
	}
	
	
	private class VoidListener implements IListener {
		@Override
		public void cellTouched (int xPos, int yPos) {
		}
	}
	

    
    public GridViewAdapter (GridView aGridView, Game aGame) {
        grid = aGridView;
        game = aGame;
        listener = new VoidListener ();
        aGridView.setAdapter (this);
        aGridView.setOnItemClickListener (new OnItemClickListener () {
	        public void onItemClick (AdapterView<?> parent, View v, 
	        		int position, long id) {
		        int xPos = position % game.width ();
		        int yPos = position / game.width ();	
		        listener.cellTouched (xPos, yPos);
//	        	gameController.cellToggleRequested (xPos, yPos);
	        }
	    });
    }
    
    
    public void setGame (Game aGame) {
    	game = aGame;
    }
    
    
    public void setListener (IListener aListener) {
		listener = aListener;
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
            view = new ImageView (parent.getContext ());
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
