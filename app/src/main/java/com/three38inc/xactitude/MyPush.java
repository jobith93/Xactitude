package com.three38inc.xactitude;

import android.app.Application;
import com.pushbots.push.Pushbots;
public class MyPush extends Application {
	
	public String SENDER_ID="114009507871";
	public String PUSHBOTS_APPLICATION_ID="548f26d21d0ab1f3228b456e";
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Pushbots.init(this, SENDER_ID,PUSHBOTS_APPLICATION_ID);
		Pushbots.getInstance().setMsgReceiver(pushHandler.class);
	}
}
