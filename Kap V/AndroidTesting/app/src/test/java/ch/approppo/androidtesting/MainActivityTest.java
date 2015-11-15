package ch.approppo.androidtesting;

import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Martin Neff @approppo GmbH on 15.11.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "app/src/main/AndroidManifest.xml", sdk = 21)
public class MainActivityTest {

	private MainActivity testee;

	private Context context;

	@Before
	public void setup() {
		testee = Robolectric.buildActivity(MainActivity.class).create().get();
		context = RuntimeEnvironment.application;
	}

	@Test
	public void testContext(){
		assertNotNull(context);
	}

	@Test
	public void testViews() {
		assertNotNull(testee.findViewById(R.id.result));
	}
}
