package edu.arsc.arsc1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;


public class VideoManager extends BaseActivity {
	
	//Code for progress bar	
	private static final int PROGRESS = 0x1;
	private ProgressBar mProgress;
	private int mProgressStatus = 0;	
	private Handler mHandler = new Handler();
	private int fileSize;
	private long systemTime;
	
	//code for button looping
	private int idIndex = 1;
	
	//file name
	String fileName;
	

	
	private class LongOperation extends AsyncTask<String, Integer, String> {
		
		
		TextView update = (TextView)findViewById(R.id.textView1);


		@Override
		protected String doInBackground(String... params) {
			
			String[] paramaters = params;
			String fileName = paramaters[0];
			
			try
	        {
	           /*
	            * Get a connection to the URL and start up
	            * a buffered reader.
	            */
	           long startTime = System.currentTimeMillis();
	    
	           //text1.setText("Connecting to: http://people.arsc.edu/~rsimon/podmedia ");
	           Log.i("info","Connecting to: http://people.arsc.edu/~rsimon/podmedia");
	    
	           URL url = new URL("http://people.arsc.edu/~rsimon/podmedia/"+fileName);
	           url.openConnection();
	           InputStream reader = url.openStream();
	           
	           //get the file size
	    
	           /*
	            * Setup a buffered file writer to write
	            * out what we read from the website.
	            */
	           
	           File root = Environment.getExternalStorageDirectory();
	           
	           FileOutputStream writer = new FileOutputStream(new File(root, fileName));
	           byte[] buffer = new byte[153600];
	           int totalBytesRead = 0;
	           int bytesRead = 0;
	    
	           //text1.setText("Reading " + fileName + " 150kb blocks at a time.\n");
	           
	           systemTime = System.currentTimeMillis();
	    
	           while ((bytesRead = reader.read(buffer)) > 0)
	           {  
	              writer.write(buffer, 0, bytesRead);
	              buffer = new byte[153600];
	              totalBytesRead += bytesRead;
	              publishProgress(totalBytesRead);

	           }
	    
	           long endTime = System.currentTimeMillis();
	    
	           //text1.setText("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
	           writer.close();
	           reader.close();
	        }
	        catch (MalformedURLException e)
	        {
	           e.printStackTrace();
	        }
	        catch (IOException e)
	        {
	           e.printStackTrace();
	        }
			
			
			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			update.setText("Download Completed!");
			updateProgressBar(1, 1);
			MediaScannerWrapper myScanner = new MediaScannerWrapper(getBaseContext(), (Environment.getExternalStorageDirectory() + "/" + fileName), "*/*");
			myScanner.scan();
			// execution of result of Long time consuming operation
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
		// Things to be done before execution of long running operation. For example showing ProgessDialog
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
		@Override
		protected void onProgressUpdate(Integer... progress) {
	      // Things to be done while execution of long running operation is in progress. For example updating ProgessDialog
			setProgress(progress[0]);

			
			final int size = fileSize;	
			//Log.i("info","onProgressUpdate method used.  System time:	" + systemTime);
			if (System.currentTimeMillis() > systemTime + 500){	
			int updateProgress = progress[0];	
			//format
			DecimalFormat df = new DecimalFormat("#.##");			
			float downloaded = (float)progress[0]/1000000;
			float totalSize = (float)size/1000000;
			update.setText("Downloading: " + df.format(downloaded) + " of " + df.format(totalSize) + " mbs");
			updateProgressBar(updateProgress, size);
			systemTime = System.currentTimeMillis();
			
			}
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoview);
		
		/*
		RelativeLayout relativeLayout  = (RelativeLayout)findViewById(R.id.AnotherRelativeLayout);
		
		 //Create a text view for the header (temporarily holding vid info)
        TextView tv = new TextView(this);
        tv.setTextAppearance(getApplicationContext(), R.style.Header);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        tv.setId(idIndex);

        //Setup layout parameters for first element
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //Set parameters for textview and add it
        tv.setLayoutParams(lp);
        relativeLayout.addView(tv);
        tv.setText("Available Videos"); */
		
		//debug text view
		final TextView update = (TextView)findViewById(R.id.textView1);  
		
		//progress bar
		mProgress = (ProgressBar) findViewById(R.id.progressbar);
		mProgress.setVisibility(mProgress.INVISIBLE);
		
		//Get the filename passed from previous activity
		Bundle bundle = getIntent().getExtras();
		
		//setup video buttons
		ArrayList<String> videoName = bundle.getStringArrayList("videoName");
		ArrayList<String> videoInfo = bundle.getStringArrayList("videoInfo");
		Log.i("info","File Names: " + videoName.toString() +"\nFile Summaries: " + videoInfo.toString());
		Log.i("info","" + videoInfo.get(3));
				
		//loop video buttons
		//Loop over array- create buttons for video objects
		/*
		String display;
		int length = videoName.size();
		int count = 0;
		while (count < length){
			
			display =  "Title: " + videoInfo.get(count);
			Button video = addButton(videoName.get(count), display);	
			video.setText(display);
			relativeLayout.addView(video);	
			count++;			
		}		
		
		*/
		
		//make overvid invisible
		
		final TextView cover = (TextView)findViewById(R.id.covervid);
		

		//Next extract the values using the key as		
		fileName = bundle.getString("fileName");
		//fileSummary = bundle.getString("fileSummary");
		final VideoView videoView = (VideoView) findViewById(R.id.VideoView);
		videoView.setVisibility(videoView.GONE);
		final MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		
		//Single heading
		//TextView info = (TextView)findViewById(R.id.textView2);
		//info.setText(fileSummary);
		
		//Stream video from web:		
		//Uri video = Uri.parse("http://people.arsc.edu/~rsimon/podmedia/kayla.mp4");
		
		//Get video from SDCard			
		String movieurl = Environment.getExternalStorageDirectory() + "/" + fileName;
		Uri video = Uri.parse(movieurl);
		
		//play it
		//videoView.setMediaController(mediaController);
		//videoView.setVideoURI(video);
		//videoView.start();
		
		final TextView text = (TextView)findViewById(R.id.textView1);	

		
		//setup button
		Button stream = (Button) findViewById(R.id.button);
		stream.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Button Actions
				
				Log.i("info","The stream button was clicked.");
				Uri video = Uri.parse("http://people.arsc.edu/~rsimon/podmedia/"+fileName);
				
				videoView.setVisibility(videoView.VISIBLE);				
				videoView.setMediaController(mediaController);
				videoView.setVideoURI(video);
				videoView.start();
				cover.setVisibility(cover.INVISIBLE);
				
			}
		});
		
		Button download = (Button) findViewById(R.id.button1);
		download.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Button Actions
				Log.i("info","The dl button was clicked.");
				
				//Download to SDCard
				update.setTextAppearance(getApplicationContext(), R.style.small);
				update.setText("Downloading: ");
				mProgress.setVisibility(mProgress.VISIBLE);
				fileSize = getFileSize(fileName);
				new LongOperation().execute(fileName);	
				
			}
		});
		
		Button playFromSD = (Button) findViewById(R.id.button3);
		playFromSD.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Button Actions
				
				Log.i("info","The dl button was clicked.");
				
				//Play from SDCard
				String movieurl = Environment.getExternalStorageDirectory() + "/" + fileName;
				Uri video = Uri.parse(movieurl);
				
				videoView.setMediaController(mediaController);
				videoView.setVideoURI(video);
				videoView.start();
				//text.setText("Video is from SD card");				
				
			}
		});
		
		Button back = (Button) findViewById(R.id.button2);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Go Back
				back();
				
				
				
			}
		});
	
	}
	
	public int getFileSize(String fileName){
		
		int fileLength = 0;		
		try {	
			 URL url = new URL("http://people.arsc.edu/~rsimon/podmedia/"+fileName);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			int contentLength = httpConn.getContentLength();
			if (contentLength == -1) {
				Log.i("info","Url for " + fileName + " is not avaialable.");
			} else {
				fileLength = contentLength;
			}
			InputStream inStream = httpConn.getInputStream();
			// now read data
			// ...
 
			// close connection
			httpConn.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return fileLength;
	}
	
	public void updateProgressBar(int updateProgress, int size){
	
		//Use float math to avoid weirdness
		float floatProgress = 100 * ((float)updateProgress/size);	
		mProgressStatus = (int) Math.rint(floatProgress);
		
		// Update the progress bar
        mHandler.post(new Runnable() {
            public void run() { 	
            	mProgress.setProgress(mProgressStatus);
            	Log.i("info", "mProgress Status: " + mProgressStatus);
            }
        });
       
	}
	
	public int fileSize(){
		return fileSize;
	}
	
	//Kill activity on upon pressing back - Will continue asynchronous task (finish downloading video)	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    
		if ((keyCode == KeyEvent.KEYCODE_BACK))
	        finish();
	    return super.onKeyDown(keyCode, event);
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
				changeFileName(file);
				streamVideo();
				//switchActivity(file, fileInfo);		
				
			}
		});	
        
      //Parameters to setup elements below each other
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, idIndex);		
		
        vid.setLayoutParams(lp);	
		idIndex++;
		
		vid.setText("Button idIndex: " + idIndex);
		
		return vid;
	}
	
	public void changeFileName(String file){
		fileName = file;
		
	}
	
	public void streamVideo(){
		Uri video = Uri.parse("http://people.arsc.edu/~rsimon/podmedia/"+fileName);	
		VideoView videoView = (VideoView) findViewById(R.id.VideoView);
		videoView.setVisibility(videoView.VISIBLE);
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(video);
		videoView.start();
		final TextView cover = (TextView)findViewById(R.id.covervid);
		cover.setVisibility(cover.INVISIBLE);
	}
	
	public void back(){
		Intent i = new Intent(this, ParsingXML.class);
	      this.startActivity(i);
	      finish();
		
	}

}