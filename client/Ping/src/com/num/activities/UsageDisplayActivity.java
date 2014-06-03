package com.num.activities;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.R;
import com.num.Values;
import com.num.activities.FullDisplayActivity.MeasurementListener;
import com.num.helpers.GAnalytics;
import com.num.helpers.TaskHelper;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.BaseResponseListener;
import com.num.models.Battery;
import com.num.models.Censorship;
import com.num.models.Device;
import com.num.models.GPS;
import com.num.models.LastMile;
import com.num.models.Link;
import com.num.models.Loss;
import com.num.models.MainModel;
import com.num.models.Measurement;
import com.num.models.Network;
import com.num.models.Ping;
import com.num.models.Row;
import com.num.models.Sim;
import com.num.models.Throughput;
import com.num.models.Traceroute;
import com.num.models.TracerouteEntry;
import com.num.models.Usage;
import com.num.models.WarmupExperiment;
import com.num.models.Wifi;
import com.num.ui.UIUtil;
import com.num.ui.adapter.ItemAdapter;

public class UsageDisplayActivity extends TrackedActivity{

	Values session;
	TextView title;
	ListView listview;
	
	//ImageView imageview;
	TextView description;
	TextView description_sub;
	Activity activity;
	
	private ThreadPoolHelper serverhelper;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		session = (Values) this.getApplicationContext();
		showDisplayPage();
		
		final Bundle extras = getIntent().getExtras();
		final String key = extras.getString("model_key");
		String desc = extras.getString("model_description");
		String desc_sub = extras.getString("model_description_sub");;
		
		title.setText(key.toUpperCase());
		description.setText(desc);
		description_sub.setText(desc_sub);
		
		serverhelper = new ThreadPoolHelper(5,10);
		serverhelper.execute(TaskHelper.getTask(key, activity, new MeasurementListener()));
		GAnalytics.log(GAnalytics.ACTION, "Click",key);
		
	}
	
	public void showDisplayPage() {
		setContentView(R.layout.item_view_usage);
		title =  (TextView) findViewById(R.id.title_item_view_usage);
		listview = (ListView) findViewById(R.id.main_list_view_item_view_usage);	
		description = (TextView) findViewById(R.id.description_item_view_usage);
		description_sub = (TextView) findViewById(R.id.description_sub_item_view_usage);
	}

	@Override
	public void finish(){
		super.finish();
	
		try{
		serverhelper.shutdown();
		
		} catch (Exception e) {	}
	}

	public class MeasurementListener extends BaseResponseListener{

		public void onCompletePing(Ping response) {

			//onCompleteOutput(response);
		}

		public void onCompleteDevice(Device response) {

			onCompleteOutput(response);
		}

		public void onCompleteMeasurement(Measurement response) {
			//LoadBarHandler.sendEmptyMessage(0);
			onCompleteOutput(response);
		}

		public void onCompleteOutput(MainModel model){

			Message msg2=Message.obtain(UIHandler, 0, model);
			UIHandler.sendMessage(msg2);
		}

		public void onComplete(String response) {

		}

		public void onUpdateProgress(int val){
			//Message msg=Message.obtain(progressBarHandler, 0, val);
			//progressBarHandler.sendMessage(msg);
		}

		public void onCompleteGPS(GPS response) {
			onCompleteOutput(response);

		}

		public void makeToast(String text) {
			//Message msg=Message.obtain(toastHandler, 0, text);
			//toastHandler.sendMessage(msg);

		}

		public void onCompleteSignal(String signalStrength) {

		}
		public void onCompleteUsage(Usage response) {
			System.out.println("usage completed");
			onCompleteOutput(response);

		}

		public void onCompleteThroughput(Throughput response) {
			onCompleteOutput(response);

		}

		public void onCompleteWifi(Wifi response) {
			onCompleteOutput(response);

		}

		public void onCompleteBattery(Battery response) {
			onCompleteOutput(response);

		}

		public void onCompleteNetwork(Network response) {
			onCompleteOutput(response);

		}

		public void onCompleteSIM(Sim response) {
			//onCompleteOutput(response);

		}
		
		public void onCompleteCensorship(Censorship response){
			onCompleteOutput(response);
		}

		public void onCompleteSummary(JSONObject Object) {
			// TODO Auto-generated method stub

		}

		public void onFail(String response) {
			// TODO Auto-generated method stub

		}

		public void onCompleteLastMile(LastMile lastMile) {
			// TODO Auto-generated method stub

		}

		public void onUpdateUpLink(Link link) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateDownLink(Link link) {
			// TODO Auto-generated method stub
			
		}

		public void onUpdateThroughput(Throughput throughput) {

			onCompleteOutput(throughput);
			
			
		}

		public void onCompleteTraceroute(Traceroute traceroute) {
			
			onCompleteOutput(traceroute);
			
		}

		public void onCompleteTracerouteHop(TracerouteEntry traceroute) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteWarmupExperiment(WarmupExperiment experiment) {
			// TODO Auto-generated method stub
			
		}

		public void onCompleteLoss(Loss loss) {
			// TODO Auto-generated method stub
			
		}
	}


	private Handler UIHandler = new Handler(){
		public void  handleMessage(Message msg) {
			
			MainModel item = (MainModel)msg.obj;

			ArrayList<Row> cells = item.getDisplayData(activity);

			if(cells.size()!=0){
				ItemAdapter itemadapter = new ItemAdapter(activity,cells);
				for(Row cell: cells)
					itemadapter.add(cell);
				listview.setAdapter(itemadapter);
				itemadapter.notifyDataSetChanged();
				UIUtil.setListViewHeightBasedOnChildren(listview,itemadapter);
			}


		}
	};
}
