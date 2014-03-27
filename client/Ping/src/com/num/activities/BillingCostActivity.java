package com.num.activities;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import com.num.R;
import com.num.helpers.UserDataHelper;
import com.num.utils.PreferencesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class BillingCostActivity extends Activity {
	private UserDataHelper userhelp;
	private final String currency[] = {"USD","CNY","EUR","GBP","INR","JPY","KRW","RUB","TND","ZAR"};
	private WheelView wheel;
	private boolean force;
	private Button saveButton, skipButton;
	private EditText costInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_billing_cost);
		
		Bundle extras = getIntent().getExtras();
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}
		
		//Skip if data is already present
		if(!force && PreferencesUtil.contains("billingCost", this)){
			finish();
			Intent myIntent = new Intent(this, MainActivity.class);
			startActivity(myIntent);
		}
		
		userhelp = new UserDataHelper(this);
		costInput = (EditText) findViewById(R.id.billing_cost_input);
		wheel = (WheelView) findViewById(R.id.billing_cost_wheel);
		saveButton = (Button) findViewById(R.id.billing_cost_save_button);
		skipButton = (Button) findViewById(R.id.billing_cost_skip_button);
		ArrayWheelAdapter<String> adapter =
	            new ArrayWheelAdapter<String>(this, currency);
		adapter.setTextSize(48);
		wheel.setVisibleItems(10);
		wheel.setViewAdapter(adapter);
		saveButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				String curr;
				curr = currency[wheel.getCurrentItem()];
				userhelp.setCurrency(curr);
				String cost = costInput.getText().toString();
				if(cost==null) return;
				userhelp.setBillingCost(cost);
				finish();
				Intent myIntent;
				if(force){
					myIntent = new Intent(v.getContext(), SettingsActivity.class);					
				}
				else{
					myIntent = new Intent(v.getContext(), DataFormActivity.class);
				}
				myIntent.putExtra("force", force);
				startActivity(myIntent);
			}
		});
		skipButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				userhelp.setCurrency("USD");
				if(!PreferencesUtil.contains("currency", BillingCostActivity.this))
				userhelp.setBillingCost("-1");
				finish();
				Intent myIntent;
				if(force){
					myIntent = new Intent(v.getContext(), SettingsActivity.class);			
				}
				else{
					myIntent = new Intent(v.getContext(), DataFormActivity.class);
				}
				myIntent.putExtra("force", force);
				startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.billing_cost, menu);
		return true;
	}
}
