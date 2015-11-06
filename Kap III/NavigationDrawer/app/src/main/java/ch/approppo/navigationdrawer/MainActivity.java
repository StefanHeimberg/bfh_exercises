package ch.approppo.navigationdrawer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private NavigationView navigationView;
	private ActionBarDrawerToggle drawerToggle;
	private DrawerLayout drawerLayout;

	private FrameLayout fragmentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

		navigationView = (NavigationView) findViewById(R.id.navigation_view);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_closed);
		drawerLayout.setDrawerListener(drawerToggle);

		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				item.setChecked(true);
				Toast.makeText(MainActivity.this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
				drawerLayout.closeDrawer(navigationView);
				setFragment(item);
				return true;
			}
		});

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		drawerToggle.syncState();
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setFragment(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_item1:
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment1()).commit();
				break;
			case R.id.action_item2:
				getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment2()).commit();
				break;
		}
	}
}
