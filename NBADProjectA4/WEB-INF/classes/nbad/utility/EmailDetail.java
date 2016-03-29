package nbad.utility;

public class EmailDetail{
	
	String name;
	String emailFrom;
	String emailTo;
	String msg;
	
	
	public EmailDetail(String name, String emailFrom, String emailTo, String msg) {
		super();
		this.name = name;
		this.emailFrom = emailFrom;
		this.emailTo = emailTo;
		this.msg = msg;
	}
	public EmailDetail() {
		// TODO Auto-generated constructor stub
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
