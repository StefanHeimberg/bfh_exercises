package ch.approppo.listview;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

	public static final String DATAOBJECT_KEY = "dataobject.key";

	private static final String NAME = "name";

	private static final String SEX = "sex";

	private static final String AGE = "age";

	private List<Map<String, String>> mMapList;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		ListView listView = (ListView) findViewById(R.id.listview);
		mMapList = convertListToMapList(createDataList());
		final SimpleAdapter simpleAdapter = new SimpleAdapter(this, mMapList, R.layout.item_layout, new String[] { NAME , SEX , AGE }, new int[] { R.id
				.item_name , R.id
				.item_sex , R.id
				.item_age });
		final DataObjectAdapter dataObjectAdapter = new DataObjectAdapter(this,R.layout.item_layout,createDataList());

		listView.setAdapter(dataObjectAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// umständlich weil mit SimpleAdapter
				Map<String, String> map = (Map<String, String>) simpleAdapter.getItem(position);
				DataObject dataObject = new DataObject();
				dataObject.setName(map.get(NAME));
				dataObject.setSex(DataObject.Sex.valueOf(map.get(SEX)));
				dataObject.setAge(Integer.parseInt(map.get(AGE)));

				// mit DataObjectAdapter würde das so aussehen:
//				dataObject = dataObjectAdapter.getItem(position);

				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				intent.putExtra(DATAOBJECT_KEY, dataObject);
				startActivity(intent);
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				mMapList.remove(position);
				simpleAdapter.notifyDataSetChanged();

//				dataObjectAdapter.remove(dataObjectAdapter.getItem(position));
//				dataObjectAdapter.notifyDataSetChanged();
				return true;
			}
		});
	}

	private List<Map<String, String>> convertListToMapList(List<DataObject> dataObjectLists) {
		List<Map<String, String>> mapList = new ArrayList<>();
		for (DataObject dataObject : dataObjectLists) {
			Map<String, String> map = new HashMap<>();
			map.put(NAME, dataObject.getName());
			map.put(SEX, dataObject.getSex().toString());
			map.put(AGE, String.valueOf(dataObject.getAge()));
			mapList.add(map);
		}
		return mapList;
	}

	private List<DataObject> createDataList() {
		List<DataObject> list = new ArrayList<>();
		Map<String, DataObject> map;
		for (int i = 0 ; i < 1000 ; i++) {
			DataObject dataObject = new DataObject();
			if (i % 2 == 0) {
				dataObject.setSex(DataObject.Sex.FEMALE);
				dataObject.setName("Käthi " + i);
				dataObject.setAge(30);
			}
			else {
				dataObject.setSex(DataObject.Sex.MALE);
				dataObject.setName("Franz " + i);
				dataObject.setAge(45);
			}
			list.add(dataObject);
		}
		return list;
	}

	private static class DataObjectAdapter extends ArrayAdapter<DataObject> {

		private int mResource;

		public DataObjectAdapter(Context context, int resource, List<DataObject> data) {
			super(context, resource, data);
			this.mResource = resource;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			// es wurden noch nicht genügend item_layouts erstellt
			if (convertView == null) {
				// unbedingt die inflate mehtode mit false am schluss verwenden, ansonsten wird versucht die view zweimal ins layout einzubinden
				convertView = LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
			}

			DataObject dataObject = getItem(position);
			((TextView) convertView.findViewById(R.id.item_name)).setText(dataObject.getName());
			((TextView) convertView.findViewById(R.id.item_sex)).setText(dataObject.getSex().toString());
			((TextView) convertView.findViewById(R.id.item_age)).setText(String.valueOf(dataObject.getAge()));

			return convertView;
		}
	}
}
