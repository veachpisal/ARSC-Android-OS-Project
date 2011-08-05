package edu.arsc.arsc1;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


public class Map extends BaseActivity {
	
	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);	
		
		
		Log.i("info", "Was successfuly created");
		
		//create webview
		 webview = (WebView) findViewById(R.id.webview);
	     webview.getSettings().setJavaScriptEnabled(true);
	     webview.getSettings().setAllowFileAccess(true);
	     //webview.loadUrl("file:///android_asset/map.html");
	     final String mimeType = "text/html";
	     final String encoding = "utf-8";
	     final String html = "<img src=\"file:///android_asset/map.jpg\" />";
	     webview.loadDataWithBaseURL("fake://not/needed", html, mimeType, encoding, "");

	        


		
	        
	        
	}
	
	

}
