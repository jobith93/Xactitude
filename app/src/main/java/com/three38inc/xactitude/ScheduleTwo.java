package com.three38inc.xactitude;


import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScheduleTwo extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View dayTwo = inflater.inflate(R.layout.activity_schedule_two, container, false);
        //((TextView)one.findViewById(R.id.textView)).setText("Day One");
        return dayTwo;
	}
}
