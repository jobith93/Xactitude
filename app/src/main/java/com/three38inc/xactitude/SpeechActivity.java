package com.three38inc.xactitude;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.three38inc.xactitude.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.provider.ContactsContract.Profile;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SpeechActivity extends Activity implements OnInitListener {
	TextView txt,txt_spoken;
	TextToSpeech tts;
	SpeechRecognizer sr;
	String str2,name,query;	
	String str = new String();
	String TAG="RecogActivity";
	int score,match_score;
	ImageView img;
	int img0,img1,img2,img3,img4;
	InputStream is;
	List<QuestionAnswer> QA_list;

	private ProgressBar progBar;

	Intent intent;

	private Handler handler = new Handler();
	OnClickListener positiveOB = new OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {

			//Toast.makeText(RecogSpeechActivity.this, "working!", Toast.LENGTH_SHORT).show();

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{

		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_speech);
		super.onCreate(savedInstanceState);             

	}

	@Override
	protected void onResume() {
		if(!isOnline()){
			showDialog(this, "Unable to Connect", "You need a network connection to use this application. Please turn on Mobile Network or Wi-Fi in Settings.");	
		}

		new MyXML().execute("");

		txt = (TextView) findViewById(R.id.textView1);
		txt_spoken=(TextView) findViewById(R.id.textView2);
		tts= new TextToSpeech(this,this);
		int res = tts.isLanguageAvailable(Locale.US);
		if (res == TextToSpeech.LANG_COUNTRY_AVAILABLE) 
			tts.setLanguage(Locale.US);
		name="dummy";
		img=(ImageView) findViewById(R.id.img);
		img0=R.drawable.btn_0;
		img1=R.drawable.btn_1;
		img2=R.drawable.btn_2;
		img3=R.drawable.btn_3;
		img4=R.drawable.btn_4;


		//speakButton.setOnClickListener(this);
		if(SpeechRecognizer.isRecognitionAvailable(getApplicationContext()))
		{

			sr = SpeechRecognizer.createSpeechRecognizer(this);       
			sr.setRecognitionListener(new listener());

		}
		else
		{

			Toast.makeText(this, "Sorry buddy, feature not supported!", Toast.LENGTH_SHORT).show();
			finish();
			//findViewById(R.id.imageButton1).setVisibility(4);
		}

		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5); 

		//progress=0;
		progBar=(ProgressBar)findViewById(R.id.progressBar1);
		progBar.setMax(200);  
		progBar.setVisibility(4);


		showInputDialog("Let's Interact!",R.layout.dialog_help, positiveOB,false);

		ImageView photo= (ImageView)findViewById(R.id.pic1);

		String[] columnNames = new String[] {Profile.DISPLAY_NAME, Profile.PHOTO_ID};

		Cursor c = this.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, columnNames, null, null, null);

		int count = c.getCount();
		c.moveToFirst();

		int position = c.getPosition();

		if (count == 1 && position == 0) {
			for (int j = 0; j < columnNames.length; j++) {
				name = c.getString(0);
				long photoId = c.getLong(1);

				Bitmap photoBitmap = loadContactPhoto(photoId);
				if (photoBitmap != null) {
					photo.setImageBitmap(photoBitmap);
				}
				else
				{
					photo.setVisibility(4);
				}

			}
		}

		c.close();
		if(name.contains(" ")){

			name= name.substring(0, name.indexOf(" ")); 

		}

		txt.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.slide_out_right));
		txt.setText("Welcome, "+name +"!");
		txt.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.slide_in_left));

		super.onResume();
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
	class listener implements RecognitionListener          
	{
		public void onReadyForSpeech(Bundle params)
		{
			Log.d(TAG, "onReadyForSpeech"); 

			//Toast.makeText(this, "Speak!", Toast.LENGTH_SHORT).show();
		}
		public void onBeginningOfSpeech()
		{
			Log.d(TAG, "onBeginningOfSpeech");

		}
		public void onRmsChanged(float rmsdB)
		{
			/*img.setVisibility(View.VISIBLE);
			double r=10*Math.pow(10, ((double)rmsdB/(double)10));
			Log.d(TAG, "onRmsChanged -> "+(int)r);
			int rms=(int)r;

			if(rms>=0&&rms<=20)
			{
				img.setImageResource(img0);

			}
			else if(rms>=21&&rms<=40)
			{
				img.setImageResource(img1);
			}
			else if(rms>=41&&rms<=60)
			{
				img.setImageResource(img2);
			}
			else if(rms>=61&&rms<=80)
			{
				img.setImageResource(img3);
			}
			else if(rms>=81&&rms<=100)
			{
				img.setImageResource(img4);
			}*/
		}
		public void onBufferReceived(byte[] buffer)
		{
			Log.d(TAG, "onBufferReceived");
		}
		public void onEndOfSpeech()
		{
			Log.d(TAG, "onEndofSpeech");
			progBar.setVisibility(0);
			//img.setVisibility(View.INVISIBLE);

		}
		public void onError(int error)
		{
			String mError="";
			switch (error) {
			case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:                
				mError = " network timeout"; 
				//sr.startListening(intent);
				break;
			case SpeechRecognizer.ERROR_NETWORK: 
				mError = " network" ;
				//toast("Please check data bundle or network settings");
				return;
			case SpeechRecognizer.ERROR_AUDIO: 
				mError = " audio"; 
				break;
			case SpeechRecognizer.ERROR_SERVER: 
				mError = "This is embarassing! sorry, My servers are down. Try again, I promise this will not happen again."; 
				//sr.cancel();
				//sr.startListening(intent);
				break;
			case SpeechRecognizer.ERROR_CLIENT: 
				mError = " client"; 
				break;
			case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: 
				mError = " Looks like, you like to keep quiet, dont be shy. Talk to me." ; 
				break;
			case SpeechRecognizer.ERROR_NO_MATCH: 
				mError = " Sorry, i cant find any words to express myself. try again." ; 
				//sr.cancel();
				//sr.startListening(intent);
				break;
			case SpeechRecognizer.ERROR_RECOGNIZER_BUSY: 
				mError = "Slow down there... let me think, for a sec." ; 
				//sr.cancel();
				//sr.startListening(intent);

				break;
			case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: 
				mError = " insufficient permissions" ; 
				break;

			}
			Log.d(TAG,  "error " +  mError);
			str2=mError;
			/*txt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right));
			txt.setText("");
			txt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));
			 */handler.post(new Runnable()
			 {
				 public void run()
				 {
					 progBar.setVisibility(4);					
					 txt_spoken.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.fade_out));
					 txt_spoken.setText(str2);
					 txt_spoken.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.fade_in));

				 }
			 });



			 if (!tts.isSpeaking()) {	
				 tts.speak(str2, TextToSpeech.QUEUE_FLUSH,null );}

			 while(tts.isSpeaking())
			 {; }

			 sr.cancel();
			 //sr.startListening(intent);

		}
		public void onResults(Bundle results)                   
		{
			handler.post(new Runnable()
			{
				public void run()
				{
					progBar.setVisibility(4);
				}
			});

			Log.d(TAG, "onResults " + results);
			ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			for (int i = 0; i < data.size(); i++)
			{
				Log.d(TAG, "result " + data.get(i));
				str += data.get(i);
			}
			str=data.get(0);

			str2="You have tell me more than that...";
			/*
			if(str.contains("hello")||str.contains("hai")||str.contains("hi")||str.contains("whats up"))
			{
				Random randomGenerator = new Random();
				ArrayList<String> sample = new ArrayList<String>() {
					{ 
						add("What's up ?"); 
						add("Hello, to you too."); 
						add("Greetings!"); 
						add("Hello there, "+name+"!");
						add("Hello, "+name+"!");
					}};
					String item = (String) sample.get(randomGenerator.nextInt(sample.size()));
					str2=item; 
			}
			else if (str.contains("hello")&&str.contains("how")&&str.contains("you")&&str.contains("are"))
			{
				str2="i am fine. how are you?";
			}
			else if(str.contains("fine"))
			{
				str2="good to know.";
			}
			else if(str.contains("your")&&str.contains("age"))
			{
				str2="";
			}
			else if(str.contains("how")&&str.contains("are")&&str.contains("you"))
			{
				str2="i am fine. how are you?";
			}
			else if(str.contains("what")&&str.contains("name"))
			{
				str2="My name is Xile.";
			}
			else if(str.contains("made")&&str.contains("you")&&str.contains("who"))
			{
				str2="Three 38 I N C.";
			}
			else if(str.contains("tell")&&str.contains("about")&&str.contains("yourself"))
			{
				str2="I am xile, developed by three 38 i n c,in a small room in bangalore. I am in my early stages now, soon i'll be more active.";
			}
			else if(str.contains("ok")||str.contains("okay"))
			{
				str2="okay!";
			}
			else if (str.contains("bye"))
			{
				str2="good-bye, it was nice chatting to you.";
				txt_spoken.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.fade_out));
				txt_spoken.setText(str2);
				txt_spoken.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.fade_in));
				if (!tts.isSpeaking()) {	
					tts.speak(str2, TextToSpeech.QUEUE_FLUSH,null );}
				while(tts.isSpeaking())
				{;}
				finish();
			}
			else if (str.contains("f***"))
			{
				str2="please mind your language!";
			}
			else if (str.contains("who")&&str.contains("named")&&str.contains("you"))
			{
				str2="i was named by, Chotu.";
			}
			else if (str.contains("good night"))
			{
				str2="sleep well, sleep tight!";
			}  
			else if (str.contains("age")&&str.contains("your"))
			{
				Random randomGenerator = new Random();
				ArrayList<String> sample = new ArrayList<String>() {
					{ 
						add("I'd rather not say."); 
						add("i prefer not to say."); 
						add("your interest flatters me, but i can't tell you."); 
						add(name+", you are not supposed to ask me such things ! is there anything i can help you with.");

					}};
					String item = (String) sample.get(randomGenerator.nextInt(sample.size()));
					str2=item;
			}
			else if (str.contains("know")&&str.contains("who")&&str.contains("i")&&str.contains("am"))
			{
				str2="yes, You are a human being ! more precisely a Homo-sapien.";
			}
			else if (str.contains("love")&&str.contains("you"))
			{
				str2="You are lying, you have not even seen me.";
			}
			else if (str.contains("time"))
			{
				Time now = new Time();
				now.setToNow();
				str2="Time is :"+now.hour+","+now.minute;
			}
			else if (str.contains("date"))
			{
				Time now=new Time();
				now.setToNow();
				str2="Today's Date is :"+now.monthDay+"'"+now.month+"'"+now.year;

			}
			else
			{

				Random randomGenerator = new Random();
				ArrayList<String> sample = new ArrayList<String>() {
					{ add("Sorry, Please don't be mad at me.. I'm Learning right now.."); 
					add("I was distracted bythe noise, Could you please Repeat."); 
					add("Sorry, i didn't get you."); 
					add("Couldn't,find a match!. sorry.");
					}};
					String item = (String) sample.get(randomGenerator.nextInt(sample.size()));
					str2=item;
			}
			 */
			match_score=0;
			for (QuestionAnswer ob : QA_list) {
				score=0;
				String[] quest = null;
				String[] query=null;

				quest=ob.question.split(" ");
				query=str.split(" ");

				for (String qs : quest) {

					for (String qr : query) {
						if(qr.equalsIgnoreCase(qs))
							score++;
					}

				}
				if(score>match_score)
				{
					match_score=score;
					//str=ob.question;
					//str2=ob.answer;
					if (ob.answer.contains("|"))
					{
						String [] sample = ob.answer.split("\\|");
						Random randomGenerator = new Random();
						int n=randomGenerator.nextInt(sample.length);
						str2=sample[n];
						//Toast.makeText(getApplicationContext(), n+" "+str2, Toast.LENGTH_LONG).show();
					}
					else
						str2=ob.answer;
				} 

			}
			txt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
			txt.setText(str);
			txt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));
			if(str2.equalsIgnoreCase("You have tell me more than that..."))
			{
				//new MyError().execute("");
			}

			txt_spoken.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.fade_out));
			txt_spoken.setText(str2);
			txt_spoken.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.fade_in));

			if (!tts.isSpeaking()) {	
				tts.speak(str2, TextToSpeech.QUEUE_FLUSH,null );}



		}
		public void onPartialResults(Bundle partialResults)
		{
			Log.d(TAG, "onPartialResults");
		}
		public void onEvent(int eventType, Bundle params)
		{
			Log.d(TAG, "onEvent " + eventType);
		}
	}

	public void speakON(View v) {
		if (v.getId() == findViewById(R.id.imageButton1).getId()) 
		{
			if (tts.isSpeaking())
			{
				tts.stop();
			}
			//img.setVisibility(View.INVISIBLE);
			sr.startListening(intent);

			//progBar.setVisibility(0);

			//Log.i("111111","11111111");
		}
	}

	@Override
	public void onInit(int status) {
		if (status==TextToSpeech.SUCCESS) {
			tts.setLanguage(Locale.getDefault());
		}
	}

	@Override
	protected void onDestroy() {

		if (tts!=null) {
			tts.stop();

			tts.shutdown();


		}
		super.onDestroy();
	}

	public void showDialog(final Activity activity, String title, CharSequence message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		if (title != null) builder.setTitle(title);

		builder.setMessage(message);
		builder.setCancelable(false);

		builder.setPositiveButton("settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
			}
		});
		builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				finish();
			} });
		builder.show();
	}

	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

	protected void showInputDialog(String title,int layout,OnClickListener pListener,boolean cancel) {
		//pass int id eg r.layout.layout name
		// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(SpeechActivity.this);
		View promptView = layoutInflater.inflate(layout, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				SpeechActivity.this);

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
	public Bitmap loadContactPhoto(long id) {
		Uri contactUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, id);
		byte[] data = null;
		Cursor cursor = this.getContentResolver().query(
				contactUri, // Uri
				new String[] { ContactsContract.CommonDataKinds.Photo.PHOTO }, // projection, the contact photo
				ContactsContract.Contacts.PHOTO_ID + "!= 0", // where statement, only if the contact has a photo
				null, null);
		//Log.i(LOG_TAG, "cursorCount: " + cursor.getCount()); // returns 1
		if (cursor == null || !cursor.moveToNext()) {           
			return null;
		}
		data = cursor.getBlob(0);
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		return bitmap;
	}

	/*class getXML extends AsyncTask<String, String, String> {
		LinearLayout layout;
		@Override
		protected void onPreExecute() {
			layout=(LinearLayout) findViewById(R.id.layoutL);
			super.onPreExecute();
		}

	    protected String doInBackground(String... urls) {
	    	Log.d(TAG, "reach 1");
	    	try{
				URL url = new URL("http://www.csfarewell.site50.net/XactitudeForm/QuestionAnswer.xml");
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);
				urlConnection.connect();
				Log.d(TAG, "reach 2");
				File SDCardRoot = new File(Environment.getDataDirectory().getPath());
				Log.d(TAG, "reach 3");
				File file = new File(SDCardRoot,"QA.xml");
				//((getActivity().getApplicationContext().getFileStreamPath("FileName.xml").getPath()));
				Log.d(TAG, "reach 4");
				FileOutputStream fileOutput = new FileOutputStream(file);
				Log.d(TAG, "reach 5");
				InputStream inputStream = urlConnection.getInputStream();
				int totalSize = urlConnection.getContentLength();
				int downloadedSize = 0;
				byte[] buffer = new byte[1024];
				int bufferLength = 0; //used to store a temporary size of the buffer
				while ( (bufferLength = inputStream.read(buffer)) > 0 ) 
				{
					fileOutput.write(buffer, 0, bufferLength);
					downloadedSize += bufferLength;
					int progress=(int)(downloadedSize*100/totalSize);
				}

				fileOutput.close();

				is = new FileInputStream(Environment.getDataDirectory().getPath()+"/QA.xml");
			}
			catch(Exception e){
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			}

			QA_list = null;              
	        XMLPullParserHandler parser = new XMLPullParserHandler();
	        QA_list = parser.parse(is);


	        for (QuestionAnswer ob : QA_list) {
	        	Button btn= new Button(getApplicationContext());
	        	btn.setText(ob.question+" | "+ob.answer);
	        	layout.addView(btn);

			}


			return "";
	    }

	    protected void onPostExecute(String r) {
	        // TODO: check this.exception 
	        // TODO: do something with the feed
	    }
	}*/

	class MyXML extends AsyncTask<String, String, String>
	{
		String s="";

		@Override
		protected String doInBackground(String... params) {
			try {
				Log.d(TAG, "");
				URL url = new URL("http://three38inc.com/XactitudeForm/assets/naac.xml");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.connect();
				is = conn.getInputStream();
//				is = getAssets().open("naac.xml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d(TAG, e.getMessage());
				e.printStackTrace();
			}
			QA_list = null;              
			XMLPullParserHandler parser = new XMLPullParserHandler();
			QA_list = parser.parse(is);
			/*for (QuestionAnswer ob : QA_list) {
				s=s+" || "+ob.question+" || "+ob.answer;
			}
			 */
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			//Toast.makeText(getApplication(), s, Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}

	}
	class MyError extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute() {
			Log.d(TAG, "reach async");
			WebView wb =(WebView) findViewById(R.id.webView1);
			wb.setWebViewClient(new MyBrowser());
			Log.d(TAG, "set browser");
			//wb.getSettings().setJavaScriptEnabled(true);
			wb.loadUrl("http://three38inc.com/XactitudeForm/save_error.php?str="+str);
			Log.d(TAG, "load url");
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
	private class MyBrowser extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}
