package nbad.model;

import java.io.Serializable;

/*
 * User
 Name
 Email
 Coins
 Participants : how many studies have he/she created
 Participation : how many studies have he/she participated in
1 participation = 1 coin
1 participant = 1 coin
 */

public class User implements Serializable{
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", coins=" + coins + ", studies=" + studies
				+ ", participation=" + participation + "]";
	}
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private int coins;
	private int studies;
	private int participation;
	
	
	
	
	
	public User() {
		super();
		name="";
		email="";
		coins=0;
		studies=0;
		participation=0;
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public int getStudies() {
		return studies;
	}
	public void setStudies(int studies) {
		this.studies = studies;
	}
	public int getParticipation() {
		return participation;
	}
	public void setParticipation(int participation) {
		this.participation = participation;
	}
	
	
	
}
