package utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.List;


public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	boolean printlog = true;

	// constructor
	public JSONParser() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod
	public JSONObject makeHttpRequest(String url, String method,
			List<NameValuePair> params) {
		try {
			if (method == "POST") {
				HttpParams my_httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(my_httpParams, 20000);
				HttpConnectionParams.setSoTimeout(my_httpParams, 20000);
				DefaultHttpClient httpClient = new DefaultHttpClient(my_httpParams);
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			} else if (method == "GET") {
				HttpParams my_httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(my_httpParams, 20000);
				HttpConnectionParams.setSoTimeout(my_httpParams, 20000);
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			json = new String(
					"{\"status\":-1,\"message\":\"Server under maintanance, Please try After some time\"}");
			e.printStackTrace();
			Log.v("sample", e.toString());
			if (printlog) Log.e("Buffer Error", "Error " + e.toString());

		}
		catch (ConnectTimeoutException e){
			if (printlog) Log.e("Buffer Error", "Error " + e.toString());
			e.printStackTrace();
			json = new String(
					"{\"status\":-1,\"message\":\"Server under maintanance, Please try After some time\"}");
			Log.v("sample", e.toString());

		}
		catch (ConnectException e) {
			if (printlog) Log.e("Buffer Error", "Error " + e.toString());
			
			e.printStackTrace();
			json = new String(
					"{\"status\":-1,\"message\":\"Server under maintanance, Please try After some time\"}");
			Log.v("sample", e.toString());
		} catch (ClientProtocolException e) {
			if (printlog) Log.e("Buffer Error", "Error " + e.toString());

			e.printStackTrace();
			json = new String(
					"{\"status\":-1,\"message\":\"Server under maintanance, Please try After some time\"}");
			Log.v("sample", e.toString());
		} catch (IOException e) {
			if (printlog) Log.e("Buffer Error", "Error " + e.toString());

			e.printStackTrace();
			json = new String(
					"{\"status\":-1,\"message\":\"Server under maintanance, Please try After some time\"}");
			Log.v("sample", e.toString());
		}
		StringBuilder sb=new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
		sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			if (printlog)
			Log.e("Buffer Error",url+ " sample" + sb.toString());
			
			json = sb.toString();
			json = sb.substring(sb.indexOf("{"), sb.lastIndexOf("}") + 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			if (printlog)
				Log.e("Buffer Error", "Error converting result " + sb + e.toString());
			json = new String(
					"{\"status\":-1,\"message\":\"Server under maintanance, Please try After some time\"}");
		} catch (Exception e) {
			if (printlog)
				Log.e("Buffer Error", "Error converting result " + sb + e.toString());
			json = new String(
					"{\"status\":-1,\"message\":\"Server under maintanance, Please try After some time\"}");
		}
		if (printlog)
			Log.e("Buffer Error", "json String is " + json.toString());
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			if (printlog)
				Log.e("JSON Parser", "Error parsing data " + e.toString());
		} catch (Exception e) {
			if (printlog)
				Log.e("JSON Parser",
						"Error parsing data2222222 " + e.toString());
		}
		return jObj;
	}
}