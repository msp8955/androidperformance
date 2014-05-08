package com.num.activities;

import kankan.wheel.widget.WheelView;

import com.num.R;
import com.num.helpers.UserDataHelper;
import com.num.utils.PreferencesUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PrepaidActivity extends Activity{
	private boolean force;
	private final String currency[] = {"USD","CNY","EUR","GBP","INR","JPY","KRW","RUB","TND","ZAR"};
	private WheelView wheel;
	private Button saveButton;
	private EditText costInput, dataInput;
	private UserDataHelper userhelp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prepaid);
		
		Bundle extras = getIntent().getExtras();
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}
		
		//Skip if data is already present
		if(!force && PreferencesUtil.contains("prepaidData", this) && PreferencesUtil.contains("prepaidPrice", this)){
			finish();
			Intent myIntent = new Intent(this, MainActivity.class);
			startActivity(myIntent);
		}
		userhelp = new UserDataHelper(this);
		saveButton = (Button) findViewById(R.id.prepaid_cost_save_button);
		wheel = (WheelView) findViewById(R.id.prepaid_cost_wheel);
		costInput = (EditText) findViewById(R.id.prepaid_cost_input);
		dataInput = (EditText) findViewById(R.id.prepaid_data_input);
	}
	
	
}
