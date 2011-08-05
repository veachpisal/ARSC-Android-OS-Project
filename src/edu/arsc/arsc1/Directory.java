package edu.arsc.arsc1;

import edu.arsc.arsc1.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;


public class Directory extends ListBaseActivity{
    /** Called when the activity is first created. */	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);          
        
        Log.i("info", "onCreate executed.");
        
        Context context = getApplicationContext();
		Resources resources = context.getResources();

		TypedArray icons = resources.obtainTypedArray(R.array.staff_pics);		
		String[] options = resources.getStringArray(R.array.staff_names);
				
		setListAdapter(new CombineImageText(context, R.layout.listitems,
				options, icons));
		
		 Log.i("info", "List Adapter(ImageAndTextAdapter) implemented.");
		 
		 
		  
    }
}