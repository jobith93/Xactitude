package com.three38inc.xactitude;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class InterestActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_interest);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		if(!isOnline()){
			showDialog(this, "Unable to Connect", "You need a network connection to use this application. Please turn on Mobile Network or Wi-Fi in Settings.");	
		}
		else
		{
		WebView browser = (WebView) findViewById(R.id.webView1);
		 browser.setWebViewClient(new MyBrowser());
		 browser.getSettings().setJavaScriptEnabled(true);
		 browser.loadUrl("http://three38inc.com/XactitudeForm/FullscreenForm/index.php");
		}
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
	
	private class MyBrowser extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	       view.loadUrl(url);
	       return true;
	    }
	 }
	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
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
}
