package com.num.activities;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import com.num.R;
import com.num.helpers.UserDataHelper;
import com.num.utils.PreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class PrepaidActivity extends Activity{
	private boolean force;
	private final String currency[] = {"USD","CNY","EUR","GBP","INR","JPY","KRW","RUB","TND","ZAR"};
	private WheelView wheel;
	private Button saveButton, skipButton;
	private EditText costInput, dataInput;
	private UserDataHelper userhelp;
	private Activity activity;
	private RadioGroup radioGroup; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prepaid);
		activity = this;
		userhelp = new UserDataHelper(this);
		Bundle extras = getIntent().getExtras();
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}
		//Skip if data is already present
		if(!force && PreferencesUtil.contains("prepaidData", this) && PreferencesUtil.contains("billingCost", this)){
			finish();
			Intent myIntent = null;
			if(!PreferencesUtil.isAccepted(activity)){
				myIntent = new Intent(activity, PrivacyActivity.class);
			}
			else if(!PreferencesUtil.contains("dataLimit",activity)){
				myIntent = new Intent(activity, DataCapActivity.class);
			}
			else if(!PreferencesUtil.contains("billingCost",activity) && userhelp.getDataCap() == UserDataHelper.PREPAID){
				myIntent = new Intent(activity, PrepaidActivity.class);
			}
			else if(!PreferencesUtil.contains("billingCycle",activity) && userhelp.getDataCap()!=UserDataHelper.NONE){
				myIntent = new Intent(activity, BillingCycleActivity.class);
			}
			else if(!PreferencesUtil.contains("billingCost",activity) && userhelp.getDataCap()!=UserDataHelper.NONE){
				myIntent = new Intent(activity, BillingCostActivity.class);
			}
			else {
				myIntent = new Intent(activity, MainActivity.class);
			}
			startActivity(myIntent);
		}
		saveButton = (Button) findViewById(R.id.prepaid_cost_save_button);
		skipButton = (Button) findViewById(R.id.prepaid_cost_skip_button);
		wheel = (WheelView) findViewById(R.id.prepaid_cost_wheel);
		costInput = (EditText) findViewById(R.id.prepaid_cost_input);
		dataInput = (EditText) findViewById(R.id.prepaid_data_input);
		radioGroup = (RadioGroup) findViewById(R.id.prepaid_radio_group);
		ArrayWheelAdapter<String> adapter =
	            new ArrayWheelAdapter<String>(this, currency);
		adapter.setTextSize(48);
		wheel.setVisibleItems(10);
		wheel.setViewAdapter(adapter);
		
		saveButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				String costString = costInput.getText().toString();
				String dataString = dataInput.getText().toString();
				if(costString.length()==0 || dataString.length()==0) return;
				int cost = Integer.parseInt(costString);
				int data = Integer.parseInt(dataString);
				if(radioGroup.getCheckedRadioButtonId()==1){
					data = data*1000;
				}
				String curr = currency[wheel.getCurrentItem()];
				userhelp.setCurrency(curr);
				userhelp.setBillingCost(cost);
				userhelp.setPrepaidData(data);
				finish();
				if(force){
					finishActivity(0);
					return;
				}
				else{
					Intent myIntent = new Intent(v.getContext(), DataFormActivity.class);
					myIntent.putExtra("force", force);
					startActivity(myIntent);
				}
			}
		});
		skipButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(!PreferencesUtil.contains("currency", PrepaidActivity.this))
					userhelp.setCurrency("USD");
				if(!PreferencesUtil.contains("billingCost", PrepaidActivity.this))
					userhelp.setBillingCost(-1);
				if(!PreferencesUtil.contains("prepaidData", PrepaidActivity.this))
					userhelp.setPrepaidData(0);
				finish();
				if(force){
					finishActivity(0);
					return;		
				}
				else{
					Intent myIntent = new Intent(v.getContext(), DataFormActivity.class);
					myIntent.putExtra("force", force);
					startActivity(myIntent);
				}
			}
		});
	}
	
	
}
