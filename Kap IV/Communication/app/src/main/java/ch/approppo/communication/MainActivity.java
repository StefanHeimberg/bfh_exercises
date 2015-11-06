package ch.approppo.communication;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

	public static final String LOG_TAG = "Communication";

	private TextView textView;

	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		editText = (EditText) findViewById(R.id.input);
		textView = (TextView) findViewById(R.id.textview);
		findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				String name = editText.getText().toString();
				startConnection(name);
			}
		});

	}

	private void startConnection(final String name) {
		new Thread(new Runnable() {
			@Override public void run() {
				InputStream is = null;
				try {
					URL url = new URL("http://www.approppo.ch/mad/echojson.php");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);

					DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
					dataOutputStream.writeBytes("name=".concat(name));
					dataOutputStream.flush();
					dataOutputStream.close();

					if (200 == connection.getResponseCode()) {
						is = connection.getInputStream();
						//						final String response = getResponseString(is);

						//						MyResponse myResponse = getResponseWithGSON(is);
						//					    final String response = myResponse.getResponse();

						JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
						jsonReader.beginObject();
						String jsonField = null;
						while (jsonReader.hasNext()) {
							String key = jsonReader.nextName();
							if (key.equals("response")) {
								jsonField = jsonReader.nextString();
								break;
							}
						}
						final String response = jsonField;

						textView.post(new Runnable() {
							@Override public void run() {
								textView.setText(response);
							}
						});
						Log.d(LOG_TAG, response);
					}

				}
				catch (MalformedURLException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				//				catch (JSONException e) {
				//					e.printStackTrace();
				//				}
				finally {
					if (is != null) {
						try {
							is.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}).start();
	}

	private MyResponse getResponseWithGSON(InputStream is) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

		StringBuilder stringBuilder = new StringBuilder();
		String s;
		while ((s = bufferedReader.readLine()) != null) {
			stringBuilder.append(s);
		}

		Gson gson = new Gson();
		MyResponse myResponse = gson.fromJson(stringBuilder.toString(), MyResponse.class);
		return myResponse;
	}

	private String getResponseString(InputStream inputStream) throws IOException, JSONException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		StringBuilder stringBuilder = new StringBuilder();
		String s;
		while ((s = bufferedReader.readLine()) != null) {
			stringBuilder.append(s);
		}
		JSONObject jsonObject = new JSONObject(stringBuilder.toString());
		return jsonObject.getString("response");
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
}
