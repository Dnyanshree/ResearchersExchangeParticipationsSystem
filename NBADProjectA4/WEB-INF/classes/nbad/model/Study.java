/**
 * 
 */
package nbad.model;

import java.io.Serializable;
import java.util.*;


/*
 Name
 Code
 DateCreated
 Email (Creator)
 Question
 String getImageURL() – URL that can be used in your pages, pointing to an image file 
 						within the project for your question. Generated from study code.
 Requestedparticipants : How many participants does the creator want
 Numofparticipants : How participants have finished this study thus far.
 Description
 Status
 List / Collection (your choice) of answer.
 */
public class Study implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String code;
	private String dateCreated;
	private String email;
	private String question;
	private int requestedParticipants;
	private int numOfParticipants;
	private String description;
	private String status;
	private ArrayList<Answer> answers;
	
	private String ImageURL;
	
	
	public Study() {
		super();
		name="";
		code="";
		dateCreated="";
		email="";
		question="";
		requestedParticipants=0;
		numOfParticipants=0;
		description="";
		status="";
	}
	
	
	
	
	@Override
	public String toString() {
		return "Study [name=" + name + ", code=" + code + ", dateCreated=" + dateCreated + ", email=" + email
				+ ", question=" + question + ", requestedParticipants=" + requestedParticipants + ", numOfParticipants="
				+ numOfParticipants + ", description=" + description + ", status=" + status + ", answers=" + answers
				+ ", ImageURL=" + ImageURL + "]";
	}




	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getRequestedParticipants() {
		return requestedParticipants;
	}
	public void setRequestedParticipants(int requestedParticipants) {
		this.requestedParticipants = requestedParticipants;
	}
	public int getNumOfParticipants() {
		return numOfParticipants;
	}
	public void setNumOfParticipants(int numOfParticipants) {
		this.numOfParticipants = numOfParticipants;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	public ArrayList<Study> genStudy(){
		ArrayList<Study> sList = new ArrayList<Study>();
		
		Study study = new Study();
		study.setName("StudyA1");
		study.setCode("101");
		study.setDateCreated((new java.util.Date()).toString());
		study.setEmail("Albie@mail.com");
		study.setQuestion("Question 1");
		study.setRequestedParticipants(10);
		study.setNumOfParticipants(2);
		study.setDescription("Description for Question 1");
		study.setStatus("START");
		
		sList.add(study);
		
		study.setName("StudyC1");
		study.setCode("102");
		study.setDateCreated((new java.util.Date()).toString());
		study.setEmail("Claire@mail.com");
		study.setQuestion("Question 2");
		study.setRequestedParticipants(10);
		study.setNumOfParticipants(2);
		study.setDescription("Description for Question 2");
		study.setStatus("START");
		
		sList.add(study);
		
		study.setName("StudyB1");
		study.setCode("103");
		study.setDateCreated((new java.util.Date()).toString());
		study.setEmail("Barney@mail.com");
		study.setQuestion("Question 3");
		study.setRequestedParticipants(10);
		study.setNumOfParticipants(2);
		study.setDescription("Description for Question 3");
		study.setStatus("STOP");
		
		sList.add(study);
		
		
		return sList;
	}
	public double getAverage(){
		double sum =0;
		for (int i = 0; i < this.getAnswers().size(); i++) {
			sum+=this.getAnswers().get(i).getChoice();
			
		}
		return sum/this.getAnswers().size();
		
	}
	
	public double getMinimum(){
		double min = 10;
		for (int i = 0; i < this.getAnswers().size(); i++) {
			if(min > this.getAnswers().get(i).getChoice()){
				min = this.getAnswers().get(i).getChoice();
			}
		}
		return min;
		
	}
	public double getMaximum(){
		double max = 0;
		for (int i = 0; i < this.getAnswers().size(); i++) {
			if(max < this.getAnswers().get(i).getChoice())
			{
				max = this.getAnswers().get(i).getChoice();
			}
		}
		return max;
		
	}
	
	public double getSD(){
		double meanCalc = this.getAverage();
		double varianceCalc =0;
		
		for (int i = 0; i < this.getAnswers().size(); i++) 
		{
			double val = this.getAnswers().get(i).getChoice();
			
			varianceCalc+=(val - meanCalc)*(val - meanCalc);
		}
		
		varianceCalc = varianceCalc/this.getAnswers().size();
		
		return Math.sqrt(varianceCalc);
	}



	public String getImageURL() {
		if(ImageURL == null){
			return "http://tinyurl.com/qjvrqmz";
		}else{
			return ImageURL;
		}
	}




	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}
	
}
