package com.three38inc.xactitude;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScheduleOne extends Fragment {
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View android = inflater.inflate(R.layout.activity_schedule_one, container, false);
	        //((TextView)android.findViewById(R.id.textView)).setText("Android");
	        return android;
}}
