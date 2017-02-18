package com.three38inc.xactitude;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.widget.Toast;

public class XMLPullParserHandler {

	List<QuestionAnswer> quest_ans_list;
	
	private QuestionAnswer QA_obj;
	//QuestionAnswer obj;
	private String text;
	
	public XMLPullParserHandler() {
		quest_ans_list = new ArrayList<QuestionAnswer>();
		//obj=new QuestionAnswer();
	}
	
	public List<QuestionAnswer> getEmployees() {
		return quest_ans_list;
	}

	
	public List<QuestionAnswer> parse(InputStream is) {
		
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		String s="";
		
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			
			parser = factory.newPullParser();
			parser.setInput(is, null);
			
			int eventType = parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				String tagname = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					
					if(tagname.equalsIgnoreCase("QA")) {
						QA_obj = new QuestionAnswer();
					}
					break;
					
				case XmlPullParser.TEXT:					
					text = parser.getText();
					Log.d("abc", text);
					break;
					
				case XmlPullParser.END_TAG:					
					if(tagname.equalsIgnoreCase("QA")) {
						quest_ans_list.add(QA_obj);
					}else if(tagname.equalsIgnoreCase("question")) {
						QA_obj.setQuestion(text);
					}else if(tagname.equalsIgnoreCase("answer")) {
						QA_obj.setAnswer(text);
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return quest_ans_list;
	}
	
}
