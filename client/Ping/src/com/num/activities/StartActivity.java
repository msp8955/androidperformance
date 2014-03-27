package com.num.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.google.android.apps.analytics.easytracking.TrackedActivity;
import com.num.R;

public class StartActivity extends TrackedActivity 
{
	private Activity activity;
	private TextView title;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_start);
		title = (TextView) findViewById(R.id.start_title);
		final Animation in = new AlphaAnimation(0.0f, 1.0f);
		title.setAnimation(in);

		in.setAnimationListener(new AnimationListener() {

			public void onAnimationEnd(Animation animation) {
				finish();

				Intent myIntent = new Intent(activity, PrivacyActivity.class);
				startActivity(myIntent);

			}

			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
			}

			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
			}
		});


	}	



}