package edu.arsc.arsc1;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ParsingXML extends BaseActivity {
	
	//create and pass arrays
	public  ArrayList<String> videoInfo = new ArrayList<String>();
	public  ArrayList<String> videoName = new ArrayList<String>();
	
	
	//Setup idIndex;		
	int idIndex = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		//get screen size

		
		//Setup relative layout
		RelativeLayout relativeLayout = new RelativeLayout(this);	
		
		//Setup layout parameters for the relative view
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);

        //Create a text view for the header (temporarily holding vid info)
        TextView tv = new TextView(this);
        tv.setId(idIndex);

        //Setup layout parameters for first element
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //Set parameters for textview and add it
        tv.setLayoutParams(lp);
        relativeLayout.addView(tv);
        
        //Set Content View- Note: If UI interaction is before this there is exception
        setContentView(R.layout.select);  //was (relativeLayout, rlp);
        
        //get the relative view for videoes
        RelativeLayout videosInScroll = (RelativeLayout)findViewById(R.id.addvids);
		
        //Begin Parsing
        
		try {
			//The URL we want to load the xml data from
			
			//test feed on snowy: http://snowy.arsc.alaska.edu/wp/test.xml
			//Rijo's feed: http://people.arsc.edu/~rsimon/rssfeed.xml
			
			URL url = new URL("http://people.arsc.edu/~rsimon/rssfeed.xml");
			
			//Get SaxParser from factory
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			//Get the xml reader
			XMLReader xr = sp.getXMLReader();
			//Create content handler 
			ExampleHandler myExampleHandler = new ExampleHandler();
			xr.setContentHandler(myExampleHandler);
			
			//Begin Parsing
			xr.parse(new InputSource(url.openStream()));
			//Finished
			
			//New switch
			//switchActivity("kayla.mp4", "Kayla's Presentation on WRF");
			
			//setup a List Array to display all the video objects
			ArrayList<Video> videos = myExampleHandler.getVideos();
			
			//Loop over array- create buttons for video objects
			String display;
			int length = videos.size();
			int count = 0;
			while (count < length){
				
				
				videoName.add(videos.get(count).videoFileName());
				videoInfo.add(videos.get(count).videoSummary());
				display =  "Title: " + videos.get(count).getTitle() + "\nSummary: "	+
				videos.get(count).videoSummary() /*+ "\nLocation:	" + videos.get(count).videoGuid() +
				"\nFile Name: " + videos.get(count).videoFileName()*/;
				Button video = addButton(videos.get(count).videoFileName(), display);	
				video.setText(display);
				videosInScroll.addView(video);	
				count++;			
			}
			
			tv.setText("Video Selection:");
			
		} catch (Exception e) {
			//Will display error in GUI
			tv.setText("Error: " + e.getMessage());
		}	
		
		//switchActivity("kayla.mp4", "Kayla's Presentation on WRF");
		//finish();
		
	}
	
	//Adds a button with a relative layout to be below the previous button
	public Button addButton(String fileName, String info){
		
		Button vid = new Button(this);
        vid.setId(idIndex + 1);
        final String file = fileName;
        final String fileInfo = info;
        vid.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Button Actions- Stream, Download, new Activity?
				Log.i("info","File Name: " + file);
				switchActivity(file, fileInfo);
				finish();
				
				
			}
		});	
        
      //Parameters to setup elements below each other
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, idIndex);		
		
        vid.setLayoutParams(lp);	
		idIndex++;
		
		vid.setText("Button idIndex: " + idIndex);
		
		return vid;
	}
	
	public void switchActivity(String file, String summary){
		
		Intent m = new Intent(this, VideoManager.class);
		Bundle bundle = new Bundle();
		bundle.putString("fileName",file);
		bundle.putString("fileSummary",summary);
		bundle.putStringArrayList("videoName", videoName);
		bundle.putStringArrayList("videoInfo", videoInfo);
		m.putExtras(bundle);
	    this.startActivity(m);
	
	}

}