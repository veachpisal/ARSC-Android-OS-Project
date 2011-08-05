package edu.arsc.arsc1;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ExampleHandler extends DefaultHandler{
	
	//Store the videos- create index
	public ArrayList<Video> videos = new ArrayList<Video>();
	int i = 0;
	
	public ArrayList<Video> getVideos(){
		return videos;
	}
	
	//Add the tags you want, 
	String titleTag = "title";
	String guidTag = "guid";
	String summaryTag = "summary";


	//required fields for handler
	private boolean in_tag_title, in_tag_guid, in_tag_summary = false;
	
	//bools to determine video or info object
	private boolean isVideo, onVideo = false;
	
	//Variables for storing content from XML tags
	String titleContent, guidContent, summaryContent;
	
	//Methods for Handler
	
	@Override
	public void startDocument() throws SAXException {
		
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	//start your tags
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equals(titleTag))
			this.in_tag_title = true;
		if (localName.equals(guidTag))
			this.in_tag_guid = true;
		if (localName.equals(summaryTag))
			this.in_tag_summary = true;
	}
	
	//End your tags
	@Override
	public void endElement(String namespaceURI, String localName, String qName)	throws SAXException {
		if (localName.equals(titleTag))
			this.in_tag_title = false;
		if (localName.equals(guidTag))
			this.in_tag_guid = false;
		if (localName.equals(summaryTag))
			this.in_tag_summary = false;		
	}
	
	//Get everything that's in the tags
	@Override
    public void characters(char ch[], int start, int length) {
		if(this.in_tag_title){
			
			if ((isVideo == true) && (onVideo == true)){
				
				//determine if it's a mp4
				String fileName = guidContent.substring(guidContent.lastIndexOf('/') + 1, guidContent.length());
				
				//determine type
				if (fileName.contains("mp4")){					
				
					//create video object
					Video arscVid = new Video(titleContent, guidContent, summaryContent, fileName);
					videos.add(i, arscVid);
					i++; 
				}
				
				//reset
				isVideo = false;
				onVideo = false;				
				//Log.i("info","Video Object Created!");
			}
			
			titleContent = new String(ch, start, length);
		}
			
		if(this.in_tag_summary){
			summaryContent = new String(ch, start, length);			
			
		}
		
		if(this.in_tag_guid){
			guidContent = new String(ch, start, length);
			isVideo = true;
			onVideo = true;
			
		}
		
		
		
		//myParsedExampleDataSet.setExtractedString(new String(ch, start, length));

			

    }
	
	
}