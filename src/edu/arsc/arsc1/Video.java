package edu.arsc.arsc1;



public class Video {
	
	 private String videoTitle;
	 private String videoGuid;
	 private String videoSummary;
	 private String videoFileName;
	 
	
	public Video(String xmlTitle, String xmlGuid, String xmlSummary, String xmlFileName){
		videoTitle = xmlTitle;
		videoGuid = xmlGuid;
		videoSummary = xmlSummary;
		videoFileName = xmlFileName;
	}
		
	public String getTitle(){
		return videoTitle;
	}
	
	public String videoGuid(){
		return videoGuid;
	}
	
	public String videoSummary(){
		return videoSummary;
	}
	
	public String videoFileName(){
		return videoFileName;
	}
	
}