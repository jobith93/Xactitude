package com.three38inc.xactitude;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.CalendarView;
import android.widget.TextView;

public class ContactActivity extends Activity {
	
	Intent intent;
	Animation animPress;
	AnimationListener animListener = new AnimationListener(){

		  @Override
		  public void onAnimationStart(Animation animation) {
		   // TODO Auto-generated method stub
		   
		  }

		  @Override
		  public void onAnimationEnd(Animation animation) {
		  
		        startActivity(intent);
		  }

		  @Override
		  public void onAnimationRepeat(Animation animation) {
		   // TODO Auto-generated method stub
		   
		  }};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_contact);
		animPress= AnimationUtils.loadAnimation(this,com.three38inc.xactitude.R.anim.press_anim);
		animPress.setAnimationListener(animListener);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String version = pInfo.versionName;
		((TextView)findViewById(R.id.versionText)).setText(version);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
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
	
	public void doAction(View v)
	{
		switch (v.getId()) {
		case R.id.call_btn:
			intent= new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:08904330374"));			
			break;
		case R.id.call_staff_1:
			intent= new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:09036967080"));			
			break;
		case R.id.call_staff_2:
			intent= new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:09986278137"));			
			break;
		case R.id.call_stud_1:
			intent= new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:08050611198"));			
			break;
		case R.id.call_stud_2:
			intent= new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:09739981417"));			
			break;
		case R.id.mail_btn:
			intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
		            "mailto","computeracademy@kristujayanti.com", null));
		intent.putExtra(Intent.EXTRA_SUBJECT, "Query Xactitude 2K15");
		intent=Intent.createChooser(intent, "Send email...");			
			break;
		case R.id.share_btn:
			intent = new Intent(Intent.ACTION_SEND);
			 intent.putExtra(Intent.EXTRA_TEXT, "Check This Out!\n XACTITUDE 2K15\n");	
			 intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.three38inc.xactitude");
			 intent.setType("text/plain");	
			 intent=Intent.createChooser(intent, "Spread The Word !");
			break;
		case R.id.rate_btn:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + getPackageName()));
			break;

		default:
			break;
		}
		v.startAnimation(animPress);
	}
	
}
