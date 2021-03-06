package com.three38inc.xactitude;



import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.support.v4.app.FragmentActivity;

public class ScheduleActivity extends FragmentActivity {

	ViewPager Tab;
	TabPagerAdapter TabAdapter;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_schedule);
		getActionBar().setDisplayHomeAsUpEnabled(true);		
		super.onCreate(savedInstanceState);


		TabAdapter = new TabPagerAdapter(getSupportFragmentManager());

		Tab = (ViewPager)findViewById(R.id.pager);
		Tab.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {

						actionBar = getActionBar();
						actionBar.setSelectedNavigationItem(position);                    }
				});
		Tab.setAdapter(TabAdapter);

		actionBar = getActionBar();
		//Enable Tabs on Action Bar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener(){

			@Override
			public void onTabReselected(android.app.ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

				Tab.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(android.app.ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}};
			//Add New Tab
			actionBar.addTab(actionBar.newTab().setText("Day One").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("Day Two").setTabListener(tabListener));
			
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

}
