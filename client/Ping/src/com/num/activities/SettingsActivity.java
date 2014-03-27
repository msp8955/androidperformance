package com.num.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.num.R;
import com.num.helpers.UserDataHelper;

public class SettingsActivity extends ListActivity {
	private UserDataHelper userhelp;
	private boolean force = false;
	private static final String[] SETTINGS = new String[] {"Edit data plan", "Edit monthly billing cycle end date", "Edit monthly billing cost"};
	private int[] limit_val= {-1,0,250,500,750,1000,2000,9999};
	private String[] limit_text = {"Don't have one","Don't know","250 MB","500 MB","750 MB","1 GB","2 GB","More than 2GB"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.settings_row,SETTINGS));
		Bundle extras = getIntent().getExtras();
		userhelp = new UserDataHelper(this);
		try{
			force = extras.getBoolean("force");
		}
		catch (Exception e){
			force = false;
		}
		if(!force && userhelp.isFilled()){
			finish();
			Intent myIntent = new Intent(SettingsActivity.this, AnalysisActivity.class);
			startActivity(myIntent);
		}
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				switch(pos){
					case 0:
						AlertDialog.Builder dialog1 = new AlertDialog.Builder(SettingsActivity.this);
						dialog1.setTitle(SETTINGS[pos]);
						dialog1.setCancelable(false);
						dialog1.setItems(limit_text, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int pos) {
								userhelp.setDataCap(limit_val[pos]);
								dialog.dismiss();
							}
						});
						dialog1.show();
						break;
					case 1:
						AlertDialog.Builder dialog2 = new AlertDialog.Builder(SettingsActivity.this);
						final EditText cycle_date = new EditText(SettingsActivity.this);
						cycle_date.setInputType(InputType.TYPE_CLASS_NUMBER);
						dialog2.setView(cycle_date);
						dialog2.setTitle(SETTINGS[pos]);
						dialog2.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {	
							public void onClick(DialogInterface dialog, int pos) {
								userhelp.setBillingCycle(Integer.parseInt(cycle_date.getText().toString()));
								dialog.dismiss();
							}
						});
						dialog2.setCancelable(false);
						dialog2.show();
						break;
					case 2:
						AlertDialog.Builder dialog3 = new AlertDialog.Builder(SettingsActivity.this);
						final EditText bill_cost = new EditText(SettingsActivity.this);
						bill_cost.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
						dialog3.setView(bill_cost);
						dialog3.setTitle(SETTINGS[pos]);
						dialog3.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {	
							public void onClick(DialogInterface dialog, int pos) {
								userhelp.setBillingCost(bill_cost.getText().toString());
								dialog.dismiss();
							}
						});
						dialog3.setCancelable(false);
						dialog3.show();
						break;
				}
			}
		});
	}
}
