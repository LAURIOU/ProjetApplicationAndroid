/**
 * 
 */
package com.esaip.projetcpi;

/**
 * @author Matthieu LAURIOU
 * @author Christian DONGMO
 *
 */
public class Message {
	/** Attribut **/
	private String user;
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	private String message;
	
	public Message(String user, String message){
		this.setUser(user);
		this.setMessage(message);
	}
}
