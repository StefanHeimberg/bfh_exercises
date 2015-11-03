package ch.approppo.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_layout);
		DataObject dataObject = (DataObject) getIntent().getSerializableExtra(MainActivity.DATAOBJECT_KEY);
		((TextView) findViewById(R.id.item_name)).setText(dataObject.getName());
		((TextView) findViewById(R.id.item_sex)).setText(dataObject.getSex().toString());
		((TextView) findViewById(R.id.item_age)).setText(String.valueOf(dataObject.getAge()));
	}
}
