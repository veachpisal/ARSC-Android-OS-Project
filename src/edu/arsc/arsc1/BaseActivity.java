package edu.arsc.arsc1;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;



public class BaseActivity extends Activity
	{
	
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     
     switch(item.getItemId())
     {
     case 1:
    Intent a = new Intent(this, Main.class);
     this.startActivity(a);
     finish();
      return true;
     case 2:
    	 Intent b = new Intent(this, News.class);
    	 this.startActivity(b);;
         finish();
      return true;
     case 3:
    	 Intent c = new Intent(this, About.class);
    	 this.startActivity(c);
         finish();
      return true;
     case 4:
      Intent i = new Intent(this, ParsingXML.class);
      this.startActivity(i);
      finish();
      return true;
     case 5:
      Intent m = new Intent(this, Map.class);
      this.startActivity(m);
      finish();
      return true;
     case 6:
      Intent d = new Intent(this, Directory.class);
      this.startActivity(d);
      finish();

      return true;

     }
     return super.onOptionsItemSelected(item);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	menu.add(1, 1, 0, "Home");
        menu.add(1, 2, 1, "News");
        menu.add(1, 3, 2, "About");
        menu.add(1, 4, 3, "Media");
        menu.add(1, 5, 4, "Map");
        menu.add(1, 6, 5, "Contact");


     return true;
    } 
    
    public class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location loc){

        	//Get latitude and longitude
        	loc.getLatitude();
        	loc.getLongitude();

        	//Print coordinates to screen
        	String Text = "Current Location: " + "(" + loc.getLatitude() + "," + loc.getLongitude() + ")";
        	Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
        	
        	//WRRB Notification  	        	
        	if ((loc.getLatitude() > 64.859)  && (loc.getLatitude() < 64.860) && (loc.getLongitude() > 147.849) && (loc.getLongitude() < 147.850)){
        		String success = "You are close to the West Ridge Research Building!";
        		Toast.makeText( getApplicationContext(), success, Toast.LENGTH_SHORT).show();	        		
        	}
        	
        	//ARSC Lab Notification	(Works)        	
        	if ((loc.getLatitude() > 64.856)  && (loc.getLatitude() < 64.857) && (loc.getLongitude() > 147.818) && (loc.getLongitude() < 147.819)){
        		String success = "You are close to the ARSC Lab in Duckering!";
        		Toast.makeText( getApplicationContext(), success, Toast.LENGTH_SHORT).show();	        		
        	}
        		
        		
        
        }

        @Override
        public void onProviderDisabled(String provider){
        	
        	//Make toast if disabled
        	Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();

        }

        @Override
        public void onProviderEnabled(String provider){

        	//Toast if enabled
        	Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
        
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){
        	
        	//code if you want to	        	
        	
        }




  }
	

}
