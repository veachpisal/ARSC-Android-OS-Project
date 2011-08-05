package edu.arsc.arsc1;

import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;



public class ListBaseActivity extends ListActivity
	{
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     
     Intent a = new Intent(this, Main.class);
     
     switch(item.getItemId())
     {
     case 1:
    
     this.startActivity(a);
     finish();
      return true;
     case 2:
    	 this.startActivity(a);
         finish();
      return true;
     case 3:
    	 this.startActivity(a);
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
	

}
