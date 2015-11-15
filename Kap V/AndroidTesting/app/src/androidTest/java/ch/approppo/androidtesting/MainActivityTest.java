package ch.approppo.androidtesting;

import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Martin Neff @approppo GmbH on 15.11.15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity activity;


	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}

	public void testViewsNotNull() {
		allViewsNotNull();
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setActivity(null);
		activity = getActivity();

		allViewsNotNull();
	}

	private void allViewsNotNull(){
		assertNotNull(activity.findViewById(R.id.result));
		assertNotNull(activity.findViewById(R.id.one));
		assertNotNull(activity.findViewById(R.id.two));
		assertNotNull(activity.findViewById(R.id.sum));
		assertNotNull(activity.findViewById(R.id.subtract));
		assertNotNull(activity.findViewById(R.id.divide));
		assertNotNull(activity.findViewById(R.id.multiply));
		assertNotNull(activity.findViewById(R.id.equals));
	}

	public void testNumbers(){
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override public void run() {
				activity.findViewById(R.id.zero).performClick();
				assertEquals("0", ((TextView) activity.findViewById(R.id.result)).getText().toString());

				activity.findViewById(R.id.one).performClick();
				assertEquals("1", ((TextView) activity.findViewById(R.id.result)).getText().toString());

				activity.findViewById(R.id.two).performClick();
				assertEquals("2", ((TextView) activity.findViewById(R.id.result)).getText().toString());
			}
		});
	}

	public void testSum(){
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override public void run() {
				activity.findViewById(R.id.one).performClick();
				activity.findViewById(R.id.sum).performClick();
				assertEquals(1, activity.getStack().intValue());
				assertEquals(MainActivity.Operation.SUM, activity.getOperation());
			}
		});
	}

	public void testBackButton() {
		KeyEvent backEventDown = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
		activity.dispatchKeyEvent(backEventDown);
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				KeyEvent backEventUp = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK);
				activity.dispatchKeyEvent(backEventUp);
			}
		});

		getInstrumentation().waitForIdleSync();
		assertTrue(activity.isFinishing());
	}
}
