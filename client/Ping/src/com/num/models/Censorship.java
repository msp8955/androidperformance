package com.num.models;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.num.R;

import android.content.Context;
import android.os.Handler;

public class Censorship implements MainModel {
	private static String DESCRIPTION = "Detect Internet censorship";
	private HashMap<String,Boolean> DNSResult;
	private Context context;
	public Censorship(Context context){
		this.context = context;
	}
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		return obj;
	}

	public int getIcon() {
		return R.drawable.censorship;
	}

	public String getTitle() {
		return "Censorship";
	}

	public ArrayList<Row> getDisplayData(Context context) {
		ArrayList<Row> data = new ArrayList<Row>();
		data.add(new Row("DNS Censorship"));
		for(String domain:DNSResult.keySet()){
			if(DNSResult.get(domain)==Boolean.TRUE){
				data.add(new Row(new ActivityItem(domain,
						"No DNS modification detected",new Handler(){
					
				},R.drawable.check)));
			}
			else{
				data.add(new Row(new ActivityItem(domain,
						"DNS Modification detected",new Handler(){
					
				},R.drawable.delete)));
			}
		}
		return data;
	}

	public String getDescription() {
		return DESCRIPTION;
	}

	public void submitResults(HashMap<String,Boolean> r){
		this.DNSResult = r;
	}
}
