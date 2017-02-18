package com.three38inc.xactitude;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;



public class MainActivity extends Activity {
	
	

	
	int mFlipping = 1;
	private float initialX;
	ViewFlipper flipper;
	Intent intent;
	Animation animPress;
	OnClickListener positiveOB = new OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {

			//Toast.makeText(RecogSpeechActivity.this, "working!", Toast.LENGTH_SHORT).show();

		}
	};
	
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
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipper = (ViewFlipper) findViewById(R.id.flipper1);
		flipper.setInAnimation(this,R.anim.slide_in_right);
		flipper.setOutAnimation(this, R.anim.slide_out_left);
		flipper.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v,MotionEvent touchevent) {
				
				 switch (touchevent.getAction()) {
				 
				 case MotionEvent.ACTION_DOWN:
					 //Toast.makeText(getApplicationContext(), "action down", Toast.LENGTH_SHORT).show();
					 flipper.stopFlipping();
					 mFlipping=0;
				 initialX = touchevent.getX();
				 break;
				 
				 case MotionEvent.ACTION_UP:
					 //Toast.makeText(getApplicationContext(), "action up", Toast.LENGTH_SHORT).show();
				 float finalX = touchevent.getX();
				 if (initialX > finalX) {
				 if (flipper.getDisplayedChild() == (flipper.getChildCount()-1))
				 break;
				 
				 flipper.setInAnimation(getApplicationContext(), R.anim.slide_in_right);
				 flipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_left);	 
				 flipper.showNext();
				 } 
				 else {
				 if (flipper.getDisplayedChild() == 0)		 
				 break;
				 
				 flipper.setInAnimation(getApplicationContext(), R.anim.slide_in_left);
				 flipper.setOutAnimation(getApplicationContext(), R.anim.slide_out_right);	 
				 flipper.showPrevious();
				 }
				 break;
				 
				 }
				 return true;
				}		

			
		});
		animPress= AnimationUtils.loadAnimation(this,R.anim.press_anim);
		animPress.setAnimationListener(animListener);
    }
    @Override
    protected void onResume() {
    	
            flipper.startFlipping(); 
    	super.onResume();
    }
    


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
    public static Intent newFacebookIntent(PackageManager pm, String url) {
	    Uri uri;
	    try {
	        pm.getPackageInfo("com.facebook.katana", 0);
	        // http://stackoverflow.com/a/24547437/1048340
	        uri = Uri.parse("fb://facewebmodal/f?href=" + url);
	    } catch (PackageManager.NameNotFoundException e) {
	        uri = Uri.parse(url);
	    }
	    return new Intent(Intent.ACTION_VIEW, uri);
	}
    public void openNext(View v)
    {
    	
    	v.startAnimation(animPress);
    	switch (v.getId()) {
		case R.id.speechIcon:
			intent= new Intent(this,SpeechActivity.class);
		break;
		case R.id.eventIcon:
			intent= new Intent(this,EventActivity.class);
		break;
		case R.id.facebookIcon:
			intent=newFacebookIntent(this.getPackageManager(), "https://www.facebook.com/xactitude");
		break;
		case R.id.scheduleIcon:
			//Toast.makeText(this, "Comming soon in the next Update !", Toast.LENGTH_LONG).show();
			//showInputDialog("Comming Soon!",R.layout.dialog_soon, positiveOB,false);
			intent= new Intent(this,ScheduleActivity.class);
		break;
		case R.id.mapsIcon:
			double latitude = 13.058273;
			double longitude = 77.642607;
			String label = "Kristu Jayanti College";
			String uriBegin = "geo:" + latitude + "," + longitude;
			//String query = latitude + "," + longitude + "(" + label + ")";
			String query = label+" banglore";
			String encodedQuery = Uri.encode(query);
			String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
			Uri uri = Uri.parse(uriString);
			intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
			
		break;
		case R.id.contactIcon:
			intent= new Intent(this,ContactActivity.class);
		break;
		case R.id.youtubeIcon:
			try{
			intent =new Intent(Intent.ACTION_VIEW);
	        intent.setPackage("com.google.android.youtube");
	        intent.setData(Uri.parse("http://www.youtube.com/user/kristujayanticollege"));
	        startActivity(intent);
	    } catch (ActivityNotFoundException e) {
	        intent = new Intent(Intent.ACTION_VIEW);
	        intent.setData(Uri.parse("http://www.youtube.com/user/kristujayanticollege"));
	    }
		break;
		case R.id.registerIcon:
			//String url = "http://three38inc.com/XactitudeForm/FullscreenForm/index.php";
			//intent = new Intent(Intent.ACTION_VIEW);
			//intent.setData(Uri.parse(url));
			intent= new Intent(this,InterestActivity.class);
			
		break;
		case R.id.instructionIcon:
			intent= new Intent(this,InstructionActivity.class);
		break;

		default:
			
			break;
		}
    	
    	
    	
    }
    
    protected void showInputDialog(String title,int layout,OnClickListener pListener,boolean cancel) {
		//pass int id eg r.layout.layout name
		// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
		View promptView = layoutInflater.inflate(layout, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MainActivity.this);

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

