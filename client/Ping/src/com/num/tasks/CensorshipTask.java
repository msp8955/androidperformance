package com.num.tasks;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import android.content.Context;
import android.util.Log;

import com.num.Values;
import com.num.helpers.ThreadPoolHelper;
import com.num.listeners.ResponseListener;
import com.num.models.Censorship;

public class CensorshipTask extends ServerTask{
	
	ThreadPoolHelper serverhelper;
	private String[] targets = {"www.google.com","www.facebook.com","www.twitter.com"};
	public CensorshipTask(Context context, Map<String, String> reqParams, 
			ResponseListener listener) {
		super(context, new HashMap<String, String>(), listener);
		serverhelper = new ThreadPoolHelper(Values.THREADPOOL_MAX_SIZE,
				Values.THREADPOOL_KEEPALIVE_SEC);
	}

	@Override
	public void runTask() {
		Censorship c = new Censorship(this.getContext());
		HashMap<String,Boolean> res = new HashMap<String,Boolean>();
		
		for(int i=0; i<targets.length; i++){
			try {
				Lookup test = new Lookup(targets[i],Type.A);
				Lookup cont = new Lookup(targets[i],Type.A);
				cont.setResolver(new SimpleResolver("8.8.8.8"));
				Record[] testRecords = test.run();
				Record[] controlRecords = cont.run();
				if(test.getResult()==Lookup.SUCCESSFUL){
					List<Record> testList = Arrays.asList(testRecords);
					List<Record> controlList = Arrays.asList(controlRecords);
					testList.retainAll(controlList);
					res.put(targets[i], testList.size()>0);
				}
				
			} catch (TextParseException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		c.submitResults(res);
		Log.d("Debug","Rendering");
		getResponseListener().onCompleteCensorship(c);
	}

	@Override
	public String toString() {
		return "CensorshipTask";
	}
	
}
