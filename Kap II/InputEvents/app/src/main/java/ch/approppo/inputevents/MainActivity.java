package ch.approppo.inputevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	private static final String NUMBER_KEY = "key";

	private static final String SHARED_PREF_NAME = "another.shared.pref";

	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.edittext);

		findViewById(R.id.button_inc).setOnClickListener(this);
		findViewById(R.id.button_dec).setOnClickListener(this);
		findViewById(R.id.button_save).setOnClickListener(this);
		findViewById(R.id.button_load).setOnClickListener(this);
		findViewById(R.id.button_save2).setOnClickListener(this);
		findViewById(R.id.button_load2).setOnClickListener(this);
	}

	@Override public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_inc:
				Integer numberInc = getIntegerFromEditText(editText);
				if (numberInc != null) {
					numberInc++;
					editText.setText(String.valueOf(numberInc));
				}
				break;
			case R.id.button_dec:
				Integer numberDec = getIntegerFromEditText(editText);
				if (numberDec != null) {
					numberDec--;
					editText.setText(String.valueOf(numberDec));
				}
				break;
			case R.id.button_save:
				saveNumberFromEditText(editText, null);
				break;
			case R.id.button_load:
				loadNumberToEditText(editText, null);
				break;
			case R.id.button_save2:
				saveNumberFromEditText(editText, SHARED_PREF_NAME);
				break;
			case R.id.button_load2:
				loadNumberToEditText(editText, SHARED_PREF_NAME);
				break;
		}
	}

	private void saveNumberFromEditText(EditText editText, String name) {
		Integer number = getIntegerFromEditText(editText);
		if (number != null) {
			if (name == null) {
				getPreferences(MODE_PRIVATE).edit().putInt(NUMBER_KEY, number).apply();
			}
			else {
				getSharedPreferences(name, MODE_PRIVATE).edit().putInt(NUMBER_KEY, number).apply();
			}
			Toast.makeText(this, "Integer " + String.valueOf(number) + " saved!", Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(this, "No number to save", Toast.LENGTH_SHORT).show();
		}
	}

	private void loadNumberToEditText(EditText editText, String name) {
		Integer number;
		if (name == null) {
			number = getPreferences(MODE_PRIVATE).getInt(NUMBER_KEY, 0);
		}
		else {
			number = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).getInt(NUMBER_KEY, 0);
		}
		editText.setText(String.valueOf(number));
		Toast.makeText(this, "Integer " + String.valueOf(number) + " loaded from SharedPrefs", Toast.LENGTH_SHORT).show();
	}

	private Integer getIntegerFromEditText(EditText editText) {
		String text = editText.getText().toString();
		if (text.length() != 0) {
			try {
				Integer number = Integer.parseInt(text);
				return number;
			}
			catch (NumberFormatException e) {
				Log.e(LOG_TAG, e.getLocalizedMessage());
				return null;
			}
		}
		return 0;
	}
}
