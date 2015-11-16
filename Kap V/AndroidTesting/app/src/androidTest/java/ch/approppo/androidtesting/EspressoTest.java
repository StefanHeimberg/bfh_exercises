package ch.approppo.androidtesting;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.ActivityInstrumentationTestCase2;
import org.junit.runner.RunWith;

/**
 * Created by Martin Neff @approppo GmbH on 15.11.15.
 */
public class EspressoTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public EspressoTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getActivity();
	}

	public void testButtons() {
		onView(withId(R.id.zero)).perform(click());
		onView(withId(R.id.result)).check(matches(withText("0")));
		onView(withId(R.id.result)).check(matches(notNullValue()));
	}
}
