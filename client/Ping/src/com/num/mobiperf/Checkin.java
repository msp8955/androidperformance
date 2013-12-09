/* Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.num.mobiperf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Build;

import com.google.myjson.Gson;
import com.num.models.Battery;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Sim;
import com.num.models.State;
import com.num.models.Usage;
import com.num.models.WarmupExperiment;

/**
 * Handles checkins with the SpeedometerApp server.
 */
public class Checkin {
	private static final int POST_TIMEOUT_MILLISEC = 20 * 1000;
	private Context context;
	private Date lastCheckin;
	private volatile Cookie authCookie = null;
	private AccountSelector accountSelector = null;

	// PhoneUtils phoneUtils;

	public Checkin(Context context) {
		// phoneUtils = PhoneUtils.getPhoneUtils();
		this.context = context;
	}

	/** Shuts down the checkin thread */
	public void shutDown() {
		if (this.accountSelector != null) {
			this.accountSelector.shutDown();
		}
	}

	/** Return a fake authentication cookie for a test server instance */
	private Cookie getFakeAuthCookie() {
		BasicClientCookie cookie = new BasicClientCookie("dev_appserver_login",
				"test@nobody.com:False:185804764220139124118");
		cookie.setDomain(".google.com");
		cookie.setVersion(1);
		cookie.setPath("/");
		cookie.setSecure(false);
		return cookie;
	}

	public Date lastCheckinTime() {
		return this.lastCheckin;
	}

	// Modified for MySpeedTest
	public Measurement checkin(Measurement measurement) throws IOException {
		// Logger.i("Checkin.checkin() called");
		boolean checkinSuccess = false;
		/*
		 * try { JSONObject status = new JSONObject(); DeviceInfo info =
		 * phoneUtils.getDeviceInfo(); // TODO(Wenjie): There is duplicated info
		 * here, such as device ID. status.put("id", info.deviceId);
		 * status.put("manufacturer", info.manufacturer); status.put("model",
		 * info.model); status.put("os", info.os); status.put("properties",
		 * MeasurementJsonConvertor
		 * .encodeToJson(phoneUtils.getDeviceProperty()));
		 * 
		 * //Logger.d(status.toString()); sendStringMsg("Checking in");
		 * 
		 * String result = serviceRequest("checkin", status.toString());
		 * //Logger.d("Checkin result: " + result);
		 * 
		 * // Parse the result Vector<MeasurementTask> schedule = new
		 * Vector<MeasurementTask>(); JSONArray jsonArray = new
		 * JSONArray(result); sendStringMsg("Checkin got " + jsonArray.length()
		 * + " tasks.");
		 * 
		 * for (int i = 0; i < jsonArray.length(); i++) {
		 * //Logger.d("Parsing index " + i); JSONObject json =
		 * jsonArray.optJSONObject(i); //Logger.d("Value is " + json); //
		 * checkin task must support if (json != null &&
		 * MeasurementTask.getMeasurementTypes().contains(json.get("type"))) {
		 * try { MeasurementTask task =
		 * MeasurementJsonConvertor.makeMeasurementTaskFromJson(json,
		 * this.context);
		 * //Logger.i(MeasurementJsonConvertor.toJsonString(task.measurementDesc
		 * )); schedule.add(task); } catch (IllegalArgumentException e) {
		 * //Logger.w("Could not create task from JSON: " + e); // Just skip it,
		 * and try the next one } } }
		 * 
		 * this.lastCheckin = new Date(); //Logger.i("Checkin complete, got " +
		 * schedule.size() + " new tasks"); checkinSuccess = true; return
		 * schedule; } catch (JSONException e) {
		 * //Logger.e("Got exception during checkin", e); throw new
		 * IOException("There is exception during checkin()"); } catch
		 * (IOException e) { //Logger.e("Got exception during checkin", e);
		 * throw e; } finally { if (!checkinSuccess) { // Failure probably due
		 * to authToken expiration. Will authenticate upon next checkin.
		 * this.accountSelector.setAuthImmediately(true); this.authCookie =
		 * null; } }
		 */
		return null;
	}

	public void uploadMeasurementResult(Vector<Measurement> finishedTasks)
			throws IOException {
		JSONArray resultArray = new JSONArray();
		for (Measurement result : finishedTasks) {
			try {
				resultArray.put(result);
			} catch (Exception e1) {
				// Logger.e("Error when adding " + result);
			}
		}

		sendStringMsg("Uploading " + resultArray.length()
				+ " measurement results.");
		// Logger.i("TaskSchedule.uploadMeasurementResult() uploading: " +
		// resultArray.toString());
		String response = serviceRequest("postmeasurement",
				resultArray.toString());
		try {
			JSONObject responseJson = new JSONObject(response);
			if (!responseJson.getBoolean("success")) {
				throw new IOException("Failure posting measurement result");
			}
		} catch (JSONException e) {
			throw new IOException(e.getMessage());
		}
		// Logger.i("TaskSchedule.uploadMeasurementResult() complete");
		sendStringMsg("Result upload complete.");
	}

	/**
	 * Used to generate SSL sockets.
	 */
	class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// Do nothing
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// Do nothing
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	/**
	 * Return an appropriately-configured HTTP client.
	 */
	private HttpClient getNewHttpClient() {
		DefaultHttpClient client;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			HttpConnectionParams.setConnectionTimeout(params,
					POST_TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(params, POST_TIMEOUT_MILLISEC);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			client = new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			// Logger.w("Unable to create SSL HTTP client", e);
			client = new DefaultHttpClient();
		}

		// TODO(mdw): For some reason this is not sending the cookie to the
		// test server, probably because the cookie itself is not properly
		// initialized. Below I manually set the Cookie header instead.
		CookieStore store = new BasicCookieStore();
		store.addCookie(authCookie);
		client.setCookieStore(store);
		return client;
	}

	private String serviceRequest(String url, String jsonString)
			throws IOException {

		if (this.accountSelector == null) {
			accountSelector = new AccountSelector(context);
		}
		if (!accountSelector.isAnonymous()) {
			synchronized (this) {
				if (authCookie == null) {
					if (!checkGetCookie()) {
						throw new IOException("No authCookie yet");
					}
				}
			}
		}

		HttpClient client = getNewHttpClient();
		String fullurl = (accountSelector.isAnonymous() ? "https://openmobiledata.appspot.com/anonymous"
				: "https://openmobiledata.appspot.com")
				+ "/" + url;
		// Logger.i("Checking in to " + fullurl);
		HttpPost postMethod = new HttpPost(fullurl);

		StringEntity se;
		try {
			se = new StringEntity(jsonString);
		} catch (UnsupportedEncodingException e) {
			throw new IOException(e.getMessage());
		}
		postMethod.setEntity(se);
		postMethod.setHeader("Accept", "application/json");
		postMethod.setHeader("Content-type", "application/json");
		if (!accountSelector.isAnonymous()) {
			// TODO(mdw): This should not be needed
			postMethod.setHeader("Cookie", authCookie.getName() + "="
					+ authCookie.getValue());
		}

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		// Logger.i("Sending request: " + fullurl);
		String result = client.execute(postMethod, responseHandler);
		return result;
	}

	/**
	 * Initiates the process to get the authentication cookie for the user
	 * account. Returns immediately.
	 */
	public synchronized void getCookie() {
		/*
		 * if (false) { // NOT TESTING
		 * SERVERphoneUtils.isTestingServer(phoneUtils.getServerUrl())) {
		 * //Logger.i("Setting fakeAuthCookie"); authCookie =
		 * getFakeAuthCookie(); return; }
		 */
		if (this.accountSelector == null) {
			accountSelector = new AccountSelector(context);
		}

		try {
			// Authenticates if there are no ongoing ones
			if (accountSelector.getCheckinFuture() == null) {
				accountSelector.authenticate();
			}
		} catch (OperationCanceledException e) {
			// Logger.e("Unable to get auth cookie", e);
		} catch (AuthenticatorException e) {
			// Logger.e("Unable to get auth cookie", e);
		} catch (IOException e) {
			// Logger.e("Unable to get auth cookie", e);
		}
	}

	/**
	 * Resets the checkin variables in AccountSelector
	 * */
	public void initializeAccountSelector() {
		accountSelector.resetCheckinFuture();
		accountSelector.setAuthImmediately(false);
	}

	private synchronized boolean checkGetCookie() {
		if (false) { // NOT TESTING SERVER
			authCookie = getFakeAuthCookie();
			return true;
		}
		Future<Cookie> getCookieFuture = accountSelector.getCheckinFuture();
		if (getCookieFuture == null) {
			// Logger.i("checkGetCookie called too early");
			return false;
		}
		if (getCookieFuture.isDone()) {
			try {
				authCookie = getCookieFuture.get();
				// Logger.i("Got authCookie: " + authCookie);
				return true;
			} catch (InterruptedException e) {
				// Logger.e("Unable to get auth cookie", e);
				return false;
			} catch (ExecutionException e) {
				// Logger.e("Unable to get auth cookie", e);
				return false;
			}
		} else {
			// Logger.i("getCookieFuture is not yet finished");
			return false;
		}
	}

	private void sendStringMsg(String str) {
		// UpdateIntent intent = new UpdateIntent(str, UpdateIntent.MSG_ACTION);
		// context.sendBroadcast(intent);
	}

	private String getVersionStr() {
		return String.format("INCREMENTAL:%s, RELEASE:%s, SDK_INT:%s",
				Build.VERSION.INCREMENTAL, Build.VERSION.RELEASE,
				Build.VERSION.SDK_INT);
	}

	public void sendMeasurement(String string, Measurement measurement) {

		JSONObject status = new JSONObject();
		long timestamp = System.currentTimeMillis() * 1000;
		DeviceProperty dp = new DeviceProperty(measurement.getDeviceId()
				.toString(), "MySpeedTest", timestamp, getVersionStr(),
				"IPv4 only", "IPv4 only", (double) -84.38707806563192,
				(double) 33.81981981981981, "network", measurement.getNetwork()
						.getConnectionType().toString(), measurement
						.getDevice().getNetworkName().toString(), measurement
						.getBattery().getLevel(), (measurement.getBattery()
						.getPlugged() > 0) ? true : false, measurement
						.getNetwork().getCellType(),
				Integer.parseInt(measurement.getNetwork().getSignalStrength()));
		try {
			status.put("id", measurement.getDeviceId());
			status.put("manufacturer", Build.MANUFACTURER);
			status.put("model", Build.MODEL);
			status.put("os", getVersionStr());
			status.put("properties", MeasurementJsonConvertor.encodeToJson(dp));

			List<MeasurementResult> resultList = new ArrayList<MeasurementResult>();
			Device device = measurement.getDevice();
			Network network = measurement.getNetwork();
			Battery battery = measurement.getBattery();
			Sim sim = measurement.getSim();
			State state = measurement.getState();
			List<Ping> pings = measurement.getPings();
			WarmupExperiment we = measurement.getWarmupExperiment();
			List<LastMile> lm = measurement.getLastMiles();
			Usage usage = measurement.getUsage();
			GPS gps = measurement.getGps(); // NOT READY
			JSONObject devicejson;
			JSONObject networkjson;
			JSONObject batteryjson;
			JSONObject simjson;
			JSONObject statejson;
			JSONArray pingsarray;
			JSONObject wejson;
			JSONArray lastarray;
			JSONObject usagejson;
			JSONObject gpsjson;

			if (device != null) {
				devicejson = device.toJSON();
				resultList.add(createResultList("device_info", dp, measurement, devicejson));				
			}
			if (network != null) {
				networkjson = network.toJSON();
				resultList.add(createResultList("network_info", dp, measurement, networkjson));// networkjson));				
			}
			if (battery != null) {
				batteryjson = battery.toJSON();
				resultList.add(createResultList("battery_info", dp, measurement, batteryjson));
			}
			if (sim != null) {
				simjson = sim.toJSON();
				resultList.add(createResultList("sim_info", dp, measurement, simjson));
			}
			if (state != null) {
				statejson = state.toJSON();
				resultList.add(createResultList("state_info", dp, measurement, statejson));
			}
//			if (pings != null) { 
//				pingsarray = new JSONArray();
//				for (Ping p : pings) {
//					pingsarray.put(p.toJSON());
//				}
//				//resultList.add(createResultList("pings_info", dp, measurement, pingsarray));
//			}
//			if (we != null) {
//				wejson = we.toJSON();
//				resultList.add(createResultList("warmup_info", dp, measurement, wejson));				
//			}
//			if (lm != null) {
//				lastarray = new JSONArray();
//				for (LastMile l : lm) {
//					lastarray.put(l.toJSON());
//				}				
//				resultList.add(createResultList("lastmile_info", dp, measurement, lastarray));
//			}
			if (usage != null) {
				usagejson = usage.toJSON();
				resultList.add(createResultListUsage("usage_info", dp, measurement, usagejson));				
			}
			if (gps != null) {
				gpsjson = gps.toJSON();
				//resultList.add(createResultList("gps_info", dp, measurement, gpsjson));
			}

			// For each of the measurements create a status to send			
			
			
			JSONArray resultArray = new JSONArray();
			for (MeasurementResult result : resultList) {
				resultArray.put(MeasurementJsonConvertor.encodeToJson(result));
			}
			String response = serviceRequest("postmeasurement",
					resultArray.toString());
			System.out.println(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static MeasurementResult createResultList(String testType,
			DeviceProperty dp, Measurement measurement, JSONObject value)
			throws Exception {

		long timestamp = System.currentTimeMillis() * 1000;
		dp.timestamp = timestamp;
		MeasurementResult mr = new MeasurementResult(measurement.getDeviceId(),
				dp, testType, timestamp, true);
		mr.addParameters("type", testType);
		mr.addParameters(testType, "information");
		Iterator keys = value.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			Object valuepair = value.get(key);
			mr.addValue(key, valuepair);
		}
		return mr;
	}

	public static MeasurementResult createResultListUsage(String testType,
			DeviceProperty dp, Measurement measurement, JSONObject value)
			throws Exception {
		long timestamp = System.currentTimeMillis() * 1000;
		dp.timestamp = timestamp;
		MeasurementResult mr = new MeasurementResult(measurement.getDeviceId(),
				dp, testType, timestamp, true);
		mr.addParameters("type", testType);
		mr.addParameters(testType, "information");
		Iterator keys = value.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (key.equals("applications")) {
				Object valuepair = value.get(key);
				JSONArray array = ((JSONArray)valuepair);
				int arraylength = array.length();
				for (int i = 0; i < arraylength; i++) {
					mr.addValue(key + ":" + i, array.getJSONObject(i));					
				}
			} else {
				Object valuepair = value.get(key);
				mr.addValue(key, valuepair);				
			}
		}
		return mr;
	}
	
	public static JSONObject createStatus(String testType, DeviceProperty dp,
			Measurement measurement, JSONObject value) throws Exception {

		long timestamp = System.currentTimeMillis() * 1000;
		dp.timestamp = timestamp;
		JSONObject status = new JSONObject();
		JSONObject parameter = new JSONObject();
		parameter.put(testType, "information");
		status.put("timestamp", timestamp);
		status.put("value", value);
		status.put("task_key", JSONObject.NULL);
		status.put("device_id", measurement.getDeviceId());
		status.put("properties", MeasurementJsonConvertor.encodeToJson(dp));
		status.put("parameters", parameter);
		status.put("type", testType);
		status.put("success", true);
		return status;
	}

	public void sendPing(String string, Measurement measurement, Ping ping) {

		try {
			long timestamp = System.currentTimeMillis() * 1000;
			DeviceProperty dp = new DeviceProperty(measurement.getDeviceId()
					.toString(), "MySpeedTest", timestamp, getVersionStr(),
					"IPv4 only", "IPv4 only", (double) -84.38707806563192,
					(double) 33.81981981981981, "network", measurement.getNetwork()
							.getConnectionType().toString(), measurement
							.getDevice().getNetworkName().toString(), measurement
							.getBattery().getLevel(), (measurement.getBattery()
							.getPlugged() > 0) ? true : false, measurement
							.getNetwork().getCellType(),
					Integer.parseInt(measurement.getNetwork().getSignalStrength()));
			JSONObject pingjson = ping.toJSON();
			List<MeasurementResult> resultList = new ArrayList<MeasurementResult>();	
			MeasurementResult mr = createResultList("ping", dp, measurement, pingjson);
			mr.addParameters("options", "-n -s 56 -c 1 -t [ttl]");			
			resultList.add(mr);
			JSONArray resultArray = new JSONArray();
			for (MeasurementResult result : resultList) {
				resultArray.put(MeasurementJsonConvertor.encodeToJson(result));
			}		
			String response = serviceRequest("postmeasurement",resultArray.toString());
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

}
