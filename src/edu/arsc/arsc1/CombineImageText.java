package edu.arsc.arsc1;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CombineImageText extends ArrayAdapter<String> {

	private LayoutInflater mInflater;	
	private String[] mStrings;
	private TypedArray mIcons;	
	private int mViewResourceId;
		
	public CombineImageText(Context context, int viewResourceId,
			String[] strings, TypedArray icons) {
		super(context, viewResourceId, strings);
		
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mStrings = strings;
		mIcons = icons;
		mViewResourceId = viewResourceId;		
		
		Log.i("info", "Public variables for ImageAndTextAdapter passed");
		
	}

	@Override
	public int getCount() {
		return mStrings.length;
	}

	@Override
	public String getItem(int position) {
		return mStrings[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.option_icon);
		iv.setImageDrawable(mIcons.getDrawable(position));
		
		Log.i("info", "String for text views"+mStrings[position]);

		TextView tv = (TextView)convertView.findViewById(R.id.option_text);
		tv.setText(mStrings[position]);
		
		Log.i("info", "View can find: "+(R.id.option_text));
			
		return convertView;

	}
}
