package ch.approppo.communication;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Martin Neff @approppo GmbH on 08.11.15.
 */
public class HttpGetTask extends AsyncTask<String, Void, String> {

	public static interface IAsyncResponseListener {
		void onResult(String string);
	}

	private final IAsyncResponseListener listener;

	public HttpGetTask(IAsyncResponseListener listener) {
		this.listener = listener;
	}

	@Override protected String doInBackground(String... params) {

		String name = params[0];

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
				return jsonField;
			}
		}

		catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override protected void onPostExecute(String s) {
		if (listener != null) {
			listener.onResult(s);
		}
	}
}
