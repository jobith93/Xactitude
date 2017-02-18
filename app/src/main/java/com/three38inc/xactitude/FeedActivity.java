package com.three38inc.xactitude;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class FeedActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_feed);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	static boolean active = false;
    @Override
    public void onStart() {
       super.onStart();
       active = true;
    }
     
    @Override
    public void onStop() {
       super.onStop();
       active = false;
    }
    public static boolean isActive(){
        return active;
    }

}
