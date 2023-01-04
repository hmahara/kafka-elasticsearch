package eu.iamhelmi.auditsearch.dto;

import java.io.Serializable;




@SuppressWarnings("serial")
public class UserAccount implements Serializable{
	String userAccountUUID;
	String login;
	String pin;
	String password;
	String role;
	String organizationUUID;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOrganizationUUID() {
		return organizationUUID;
	}
	public void setOrganizationUUID(String organizationUUID) {
		this.organizationUUID = organizationUUID;
	}
	public String getUserAccountUUID() {
		return userAccountUUID;
	}
	public void setUserAccountUUID(String userAccountUUID) {
		this.userAccountUUID = userAccountUUID;
	}
	
	
}
