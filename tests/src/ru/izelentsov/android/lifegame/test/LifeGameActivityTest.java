package ru.izelentsov.android.lifegame.test;

import ru.izelentsov.android.lifegame.LifeGameActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;




public class LifeGameActivityTest extends
		ActivityInstrumentationTestCase2<LifeGameActivity> {

	private LifeGameActivity activity = null;
	private TextView genCountValueView = null;
	
	
	public LifeGameActivityTest () {
		super ("ru.izelentsov.android.lifegame", LifeGameActivity.class);
	}

	@Override
	protected void setUp () throws Exception {
		super.setUp ();
		activity = this.getActivity ();
		genCountValueView = (TextView) activity.findViewById (
				ru.izelentsov.android.lifegame.R.id.genCountValue);
	}
	
	
	
	public void testPreconditions () {
		assertNotNull (genCountValueView);
	}
	
	
	public void testGenCountValue () {
		assertEquals ("1", (String)genCountValueView.getText ());
	}
}
