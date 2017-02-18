package com.three38inc.xactitude;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
	setContentView(R.layout.acitivity_splash);
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Intent intent = new Intent(getApplicationContext(),MainActivity.class);
	startActivity(intent);
    //finish();
	super.onCreate(savedInstanceState);
}
}
