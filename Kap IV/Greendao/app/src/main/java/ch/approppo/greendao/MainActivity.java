package ch.approppo.greendao;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ch.approppo.greendao.srcgen.*;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private DaoSession daoSession;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DaoSession daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "db", null).getWritableDatabase()).newSession();

		ListView listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1, daoSession.getPersonDao().loadAll()));

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				insertPerson();
			}
		});
	}

	private void insertPerson() {
		Person person = new Person();
		person.setName("Krüger");
		person.setFirstname("Mike");
		person.setTimestamp(new Date());

		daoSession.getPersonDao().insert(person);
		Address address = new Address();
		address.setCity("Bern");
		address.setStreet("Spitalgasse");
		address.setZip("3011");
		address.setStreetNumber(10);
		address.setPerson(person);
		daoSession.getAddressDao().insert(address);
		daoSession.getDatabase().close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private static class MyAdapter extends ArrayAdapter<Person> {

		private int mLayout;

		MyAdapter(Context context, int layout, List<Person> data) {
			super(context, layout, data);
			mLayout = layout;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(mLayout, parent, false);
			}

			Person person = getItem(position);
			((TextView) convertView.findViewById(android.R.id.text1)).setText(person.getName() +
					" " + person.getFirstname() +
					" " + person.getAddresses().get(0).getCity());
			return convertView;
		}
	}

	@Override protected void onDestroy() {
		daoSession.getDatabase().close();
		super.onDestroy();
	}
}
