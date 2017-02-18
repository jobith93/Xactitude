package com.three38inc.xactitude;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;


public class EventActivity extends Activity {
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(com.three38inc.xactitude.R.layout.activity_event);
		animPress= AnimationUtils.loadAnimation(this,com.three38inc.xactitude.R.anim.press_anim);
		animPress.setAnimationListener(animListener);
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
	public void openEvent(View v)
    {
    	intent= new Intent(EventActivity.this,DetailActivity.class);
    	switch (v.getId()) {
		case R.id.event1:
			intent.putExtra("event_name", R.string.event1);
	    	intent.putExtra("img", R.drawable.it_manager);
	    	intent.putExtra("desc", R.string.desc_1);
	    	intent.putExtra("participant_no", "one");	    	
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_1);
	    	intent.putExtra("stud", R.string.stud_cod_1);
	    	intent.putExtra("rule", R.string.rule_1);
	    	
			break;
		case R.id.event2:
			intent.putExtra("event_name", R.string.event2);
	    	intent.putExtra("img", R.drawable.it_quiz);
	    	intent.putExtra("desc", R.string.desc_2);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_2);
	    	intent.putExtra("stud", R.string.stud_cod_2);
	    	intent.putExtra("rule", R.string.rule_2);
			break;
		case R.id.event3:
			intent.putExtra("event_name", R.string.event3);
	    	intent.putExtra("img", R.drawable.ic_lecture);
	    	intent.putExtra("desc", R.string.desc_3);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_3);
	    	intent.putExtra("stud", R.string.stud_cod_3);
	    	intent.putExtra("rule", R.string.rule_3);
			break;
		case R.id.event4:
			intent.putExtra("event_name", R.string.event4);
	    	intent.putExtra("img", R.drawable.ic_best_tech_team);
	    	intent.putExtra("desc", R.string.desc_4);
	    	intent.putExtra("participant_no", "three");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_4);
	    	intent.putExtra("stud", R.string.stud_cod_4);
	    	intent.putExtra("rule", R.string.rule_4);
			break;
		case R.id.event5:
			intent.putExtra("event_name", R.string.event5);
	    	intent.putExtra("img", R.drawable.ic_coding);
	    	intent.putExtra("desc", R.string.desc_5);
	    	intent.putExtra("participant_no", "one");
	    	intent.putExtra("team_no", "three");
	    	intent.putExtra("staff", R.string.staff_cod_5);
	    	intent.putExtra("stud", R.string.stud_cod_5);
	    	intent.putExtra("rule", R.string.rule_5);
			break;
		case R.id.event6:
			intent.putExtra("event_name", R.string.event6);
	    	intent.putExtra("img", R.drawable.ic_webdesign);
	    	intent.putExtra("desc", R.string.desc_6);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_6);
	    	intent.putExtra("stud", R.string.stud_cod_6);
	    	intent.putExtra("rule", R.string.rule_6);
			break;
		case R.id.event7:
			intent.putExtra("event_name", R.string.event7);
	    	intent.putExtra("img", R.drawable.ic_photoshop);
	    	intent.putExtra("desc", R.string.desc_7);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_7);
	    	intent.putExtra("stud", R.string.stud_cod_7);
	    	intent.putExtra("rule", R.string.rule_7);
			break;
		case R.id.event8:
			intent.putExtra("event_name", R.string.event8);
	    	intent.putExtra("img", R.drawable.ic_treasure);
	    	intent.putExtra("desc", R.string.desc_8);
	    	intent.putExtra("participant_no", "one");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_8);
	    	intent.putExtra("stud", R.string.stud_cod_8);
	    	intent.putExtra("rule", R.string.rule_8);
			break;
		case R.id.event9:
			intent.putExtra("event_name", R.string.event9);
	    	intent.putExtra("img", R.drawable.ic_gaming);
	    	intent.putExtra("desc", R.string.desc_9);
	    	intent.putExtra("participant_no", "one");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_9);
	    	intent.putExtra("stud", R.string.stud_cod_9);
	    	intent.putExtra("rule", R.string.rule_9);
			break;
		case R.id.event10:
			intent.putExtra("event_name", R.string.event10);
	    	intent.putExtra("img", R.drawable.ic_movie);
	    	intent.putExtra("desc", R.string.desc_10);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_10);
	    	intent.putExtra("stud", R.string.stud_cod_10);
	    	intent.putExtra("rule", R.string.rule_10);
			break;
		case R.id.event11:
			intent.putExtra("event_name", R.string.event11);
	    	intent.putExtra("img", R.drawable.ic_electronics);
	    	intent.putExtra("desc", R.string.desc_11);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_11);
	    	intent.putExtra("stud", R.string.stud_cod_11);
	    	intent.putExtra("rule", R.string.rule_11);
			break;
		case R.id.event12:
			intent.putExtra("event_name", R.string.event12);
	    	intent.putExtra("img", R.drawable.ic_stat);
	    	intent.putExtra("desc", R.string.desc_12);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_12);
	    	intent.putExtra("stud", R.string.stud_cod_12);
	    	intent.putExtra("rule", R.string.rule_12);
			break;
		case R.id.event13:
			intent.putExtra("event_name", R.string.event13);
	    	intent.putExtra("img", R.drawable.ic_math);
	    	intent.putExtra("desc", R.string.desc_13);
	    	intent.putExtra("participant_no", "two");
	    	intent.putExtra("team_no", "one");
	    	intent.putExtra("staff", R.string.staff_cod_13);
	    	intent.putExtra("stud", R.string.stud_cod_13);
	    	intent.putExtra("rule", R.string.rule_13);
			break;
		case R.id.event14:
			intent.putExtra("event_name", R.string.event14);
	    	intent.putExtra("img", R.drawable.ic_pick);
	    	intent.putExtra("desc", R.string.desc_14);
	    	intent.putExtra("participant_no", "1");
	    	intent.putExtra("team_no", "1");
	    	intent.putExtra("staff", R.string.staff_cod_14);
	    	intent.putExtra("stud", R.string.stud_cod_14);
	    	intent.putExtra("rule", R.string.rule_14);
			break;
		case R.id.event15:
			intent.putExtra("event_name", R.string.event15);
	    	intent.putExtra("img", R.drawable.ic_exhibit);
	    	intent.putExtra("desc", R.string.desc_15);
	    	intent.putExtra("participant_no", "Maximum 3");
	    	intent.putExtra("team_no", "two");
	    	intent.putExtra("staff", R.string.staff_cod_15);
	    	intent.putExtra("stud", R.string.stud_cod_15);
	    	intent.putExtra("rule", R.string.rule_15);
			break;		

		default:
			break;
		}
    	v.startAnimation(animPress);
    	
    	
    }
}
