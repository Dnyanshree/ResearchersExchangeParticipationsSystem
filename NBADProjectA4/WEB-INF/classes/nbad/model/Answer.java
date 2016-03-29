package nbad.model;

import java.io.Serializable;

/**
 * 
 */

public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;
       
	private String email;
	private int choice;
	private java.util.Date DateSubmitted;
	private String studyCode;
	
	
	
    public java.util.Date getDateSubmitted() {
		return DateSubmitted;
	}

	public void setDateSubmitted(java.util.Date dateSubmitted) {
		DateSubmitted = dateSubmitted;
	}

	public Answer() {
        super();
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public String getStudyCode() {
		return studyCode;
	}

	public void setStudyCode(String studyCode) {
		this.studyCode = studyCode;
	}

	


}
