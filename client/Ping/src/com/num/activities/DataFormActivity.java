package com.num.activities;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.helpers.UserDataHelper;
import com.num.R;



public class DataFormActivity extends TrackedActivity 
{

	//private TableLayout table;

	private Activity activity;

	private Button saveButton;
	private RadioGroup rGroup;
	final RadioButton[] rb = new RadioButton[5];
	UserDataHelper userhelp;
	Boolean force = false;
	int[] limit_val= {1,0};


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		activity = this;
		userhelp = new UserDataHelper(activity);
		int old_val= userhelp.getDataEnable();


		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}



		setContentView(R.layout.dataform_screen);


		String[] limit_text = {"yes","no"};




		saveButton = (Button) this.findViewById(R.id.save);
		rGroup = (RadioGroup) findViewById(R.id.radio_group);

		LinearLayout.LayoutParams rg = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.WRAP_CONTENT,50);

		for (int i = 0; i < limit_text.length; i++) {
			RadioButton radiobutton = new RadioButton(this);

			radiobutton.setTextSize(18);
			radiobutton.setText(limit_text[i]);
			radiobutton.setTextColor(Color.GRAY);

			if(old_val == limit_val[i])
				radiobutton.setChecked(true);

			radiobutton.setId(i);

			rGroup.addView(radiobutton, rg);

		}


		try{		
			userhelp.setDataEnable(limit_val[1]);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		finish();
		if(!force){
			Intent myIntent = new Intent(this, MainActivity.class);
			startActivity(myIntent);
		}


		saveButton.setOnClickListener(new OnClickListener()  {
			public void onClick(View v) {	

				int checkedRadioButton = rGroup.getCheckedRadioButtonId();

				if(checkedRadioButton<0) return;


				try{		
					userhelp.setDataEnable(limit_val[checkedRadioButton]);
				}
				catch(Exception e){
					e.printStackTrace();
				}

				finish();
				if(!force){
					Intent myIntent = new Intent(v.getContext(), MainActivity.class);
					startActivity(myIntent);
				}

			}
		});


	}


}