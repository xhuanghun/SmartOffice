package com.example.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpServer {

	private static String TAG = "NET";

	/**
	 * 根据urlString从服务器得到json类型的字符串
	 * 
	 * @param urlString
	 *            服务器地址
	 * @return json类型字符串
	 * 
	 * @Notice 超时时间改为4秒了
	 */
	public String getData(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection(); // 打开连接
			connection.setConnectTimeout(4000);// 设置超时时间
			connection.connect(); // 连接
			if (connection.getResponseCode() == 200) {// 请求成功
				String currentUrlString = connection.getURL().toString();
				if (!urlString.equals(currentUrlString)) {// 防止cmcc_edu形的网络
					return null;
				}
				InputStream is = connection.getInputStream();
				Reader reader = new InputStreamReader(is, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(reader);
				String str = null;
				StringBuffer jsonStr = new StringBuffer();
				while ((str = bufferedReader.readLine()) != null) {
					jsonStr.append(str);
				}
				is.close();
				reader.close();
				bufferedReader.close();
				connection.disconnect();
				return jsonStr.toString();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * http post请求
	 * 
	 * @param urlString
	 * @param paraList
	 * @return
	 */
	public String httppost(String urlString, List<NameValuePair> paraList) {
		// BasicNameValuePair param = new BasicNameValuePair("name","GuangT");
		// paraList.add(param);

		try {
			String result = "";
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlString);
			// 设置参数
			post.setEntity(new UrlEncodedFormEntity(paraList, HTTP.UTF_8));
			// 发送httppost请求
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
			return result;
		} catch (Exception e) {
			return null;
		}

	}

	

	/*
	 * 判断是否有网络
	 */
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		} else {
			// 打印所有的网络状态
			NetworkInfo[] infos = cm.getAllNetworkInfo();
			if (infos != null) {
				for (int i = 0; i < infos.length; i++) {
					// Log.d(TAG, "isNetworkAvailable - info: " +
					// infos[i].toString());
					if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
						Log.d(TAG, "isNetworkAvailable -  I " + i);
					}
				}
			}

			// 如果仅仅是用来判断网络连接　　　　　　
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null) {
				Log.d(TAG,
						"isNetworkAvailable - 是否有网络： "
								+ networkInfo.isAvailable());
			} else {
				Log.d(TAG, "isNetworkAvailable - 完成没有网络！");
				return false;
			}

			// 1、判断是否有3G网络
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				Log.d(TAG, "isNetworkAvailable - 有3G网络");
				return true;
			} else {
				Log.d(TAG, "isNetworkAvailable - 没有3G网络");
			}

			// 2、判断是否有wifi连接
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				Log.d(TAG, "isNetworkAvailable - 有wifi连接");
				return true;
			} else {
				Log.d(TAG, "isNetworkAvailable - 没有wifi连接");
			}
		}
		return false;
	}
}
