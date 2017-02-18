package com.three38inc.xactitude;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {

	Calendar beginTime;
	int e_id;
	int img;
	String reminder_title;
	Calendar endTime;
	Intent intent;
	Animation animPress;
	AnimationListener animListener = new AnimationListener(){

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {		

			// Insert Event
			ContentResolver cr = getContentResolver();
			ContentValues values = new ContentValues();
			TimeZone timeZone = TimeZone.getDefault();
			values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
			values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
			values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
			values.put(CalendarContract.Events.TITLE, reminder_title);
			values.put(CalendarContract.Events.DESCRIPTION, "Xactitude 2K15");
			values.put(CalendarContract.Events.CALENDAR_ID, 1);
			values.put(CalendarContract.Events.EVENT_LOCATION, "Kristu Jayanti College");
			values.put(CalendarContract.Events.AVAILABILITY, Events.AVAILABILITY_BUSY);		
			Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
			String eventID = uri.getLastPathSegment();
			
			// reminder insert	
			
			values = new ContentValues();
			
			values.put( Reminders.EVENT_ID, eventID);
			values.put( Reminders.METHOD,1);
			values.put( Reminders.MINUTES,1);
			cr.insert( Reminders.CONTENT_URI, values );		
			//Toast.makeText(getApplication(), "Event Added to Calendar!", Toast.LENGTH_SHORT).show();
			showInputDialog("Reminder added !",R.layout.dialog_reminder, positiveOB,false);	
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}};
		
		OnClickListener positiveOB = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				//Toast.makeText(RecogSpeechActivity.this, "working!", Toast.LENGTH_SHORT).show();
				
			}
		};
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			setContentView(R.layout.activity_detail);
			getActionBar().setDisplayHomeAsUpEnabled(true);
			animPress= AnimationUtils.loadAnimation(this,com.three38inc.xactitude.R.anim.press_anim);
			animPress.setAnimationListener(animListener);
			Log.d("abc", "reach 1");
			Intent intent= getIntent();
			int staff=intent.getIntExtra("staff",0);
			int stud=intent.getIntExtra("stud",0);
			int desc=intent.getIntExtra("desc",0);
			int rules=intent.getIntExtra("rule", 0);
			String p_no=intent.getStringExtra("participant_no");
			String t_no=intent.getStringExtra("team_no");
			img=intent.getIntExtra("img", 0);
			int name=intent.getIntExtra("event_name", 0);
			Log.d("abc", "reach 2");
			ImageView imgView = (ImageView) findViewById(R.id.imageView);
			imgView.setImageResource(img);
			Log.d("abc", "reach 3");
			TextView descView = (TextView) findViewById(R.id.descView);
			descView.setText(desc);
			Log.d("abc", "reach 4");
			TextView staffView=(TextView) findViewById(R.id.staffView);
			staffView.setText(staff);
			Log.d("abc", "reach 5");
			TextView studView=(TextView) findViewById(R.id.studView);
			studView.setText(stud);
			Log.d("abc", "reach 6");
			TextView participantView=(TextView) findViewById(R.id.participantView);
			Log.d("abc", "reach 6a");
			participantView.setText(p_no);
			Log.d("abc", "reach 7");

			TextView teamView=(TextView) findViewById(R.id.teamView);
			teamView.setText(t_no);
			Log.d("abc", "reach 8a");
			TextView n= (TextView) findViewById(R.id.eName);
			Log.d("abc", "reach 8b");
			n.setText(name);
			TextView rule= (TextView) findViewById(R.id.txtrule);
			Log.d("abc", "reach 8b");
			rule.setText(rules);
			Log.d("abc", "reach 9");
			reminder_title=(String)n.getText(); 
			e_id=name;
			Log.d("abc", "reach 10");
			
			super.onCreate(savedInstanceState);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
				
			case R.id.action_share:
				 Uri imageUri = Uri.parse("android.resource://" + getPackageName()+ "/"+img);			
				 Intent shareIntent = new Intent();
				 shareIntent.setAction(Intent.ACTION_SEND);			 
				 shareIntent.putExtra(Intent.EXTRA_TEXT, "Check This Out! \nKristu Jayanti College \nXACTITUDE 2K15 - National Level Inter Collegiate IT Fest  \nwww.kristujayanti.edu.in/xactitude \nDownload the Android App @ \nhttp://play.google.com/store/apps/details?id=com.three38inc.xactitude");			 
				 shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);			
				 shareIntent.setType("image/png");			
				 shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);			 
				 startActivity(Intent.createChooser(shareIntent, "Spread The Word !"));
				 return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
		MenuItem shareItem;
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			
			getMenuInflater().inflate(R.menu.share, menu);	
			shareItem = menu.findItem(R.id.action_share);
			/*
			MenuItem shareItem = menu.findItem(R.id.action_share);			
			ShareActionProvider mShare = (ShareActionProvider) shareItem.getActionProvider();
			
			 
			 Intent shareIntent = new Intent();			 
			 shareIntent.setAction(Intent.ACTION_SEND);				
			 shareIntent.putExtra(Intent.EXTRA_TEXT, "Check This Out!");			 
			 shareIntent.setType("text/plain");	
			 if (mShare!=null)
			 mShare.setShareIntent(shareIntent);*/			 
			return super.onCreateOptionsMenu(menu);
		}
		
	



		public void addReminder(View v) {
			beginTime = Calendar.getInstance();
			endTime = Calendar.getInstance();
			
			switch (e_id) {
			case R.string.event1:
				beginTime.set(2015, 1, 5, 10, 30);
				endTime.set(2015, 1, 5, 11, 10);
				
			break;
			case R.string.event2:
				beginTime.set(2015, 1, 5, 11, 15);
				endTime.set(2015, 1, 5, 11, 55);
			break;
			case R.string.event3:
				beginTime.set(2015, 1, 6, 9, 00);
				endTime.set(2015, 1, 6, 11, 00);
			break;
			case R.string.event4:
				beginTime.set(2015, 1, 5, 10, 30);
				endTime.set(2015, 1, 5, 11, 10);
			break;
			case R.string.event5:
				beginTime.set(2015, 1, 5, 10, 30);
				endTime.set(2015, 1, 5, 11, 30);
			break;
			case R.string.event6:
				beginTime.set(2015, 1, 6, 9, 15);
				endTime.set(2015, 1, 6, 11, 00);
			break;
			case R.string.event7:
				beginTime.set(2015, 1, 5, 14, 15);
				endTime.set(2015, 1, 5, 16, 00);
			break;
			case R.string.event8:
				beginTime.set(2015, 1, 5, 12, 00);
				endTime.set(2015, 1, 5, 13, 00);
			break;
			case R.string.event9:
				beginTime.set(2015, 1, 5, 12, 00);
				endTime.set(2015, 1, 5, 13, 00);
			break;
			case R.string.event10:
				beginTime.set(2015, 1, 5, 10, 30);
				endTime.set(2015, 1, 5, 11, 00);
			break;
			case R.string.event11:
				beginTime.set(2015, 1, 5, 13, 45);
				endTime.set(2015, 1, 5, 16, 00);
			break;
			case R.string.event12:
				beginTime.set(2015, 1, 5, 13, 45);
				endTime.set(2015, 1, 5, 16, 00);
			break;
			case R.string.event13:
				beginTime.set(2015, 1, 5, 11, 15);
				endTime.set(2015, 1, 5, 11, 55);
			break;
			case R.string.event14:
				beginTime.set(2015, 1, 5, 13, 45);
				endTime.set(2015, 1, 5, 16, 00);
			break;
			case R.string.event15:
				beginTime.set(2015, 1, 6, 11, 00);
				endTime.set(2015, 1, 6, 12, 00);
			break;

			default:
				break;
			}			
			v.startAnimation(animPress);
		}
		
		protected void showInputDialog(String title,int layout,OnClickListener pListener,boolean cancel) {
			//pass int id eg r.layout.layout name
			// get prompts.xml view
			LayoutInflater layoutInflater = LayoutInflater.from(DetailActivity.this);
			View promptView = layoutInflater.inflate(layout, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					DetailActivity.this);
			
			alertDialogBuilder.setView(promptView);

			/*final EditText editText = (EditText) promptView
					.findViewById(R.id.editText1);*/
			// setup a dialog window
			alertDialogBuilder
					.setTitle(title)
					.setCancelable(false)
					.setPositiveButton("OK !", pListener);
					/*.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//resultText.setText("Hello, " + editText.getText());
							name=editText.getText().toString();
							
						}
					})*/
			if(cancel==true){
			alertDialogBuilder.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									
									dialog.cancel();
								}
							});
			}

			// create an alert dialog
			AlertDialog alert = alertDialogBuilder.create();
			alert.show();
			//return editText.getText().toString();

		}
		

}
