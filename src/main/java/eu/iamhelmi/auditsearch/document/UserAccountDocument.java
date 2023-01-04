package eu.iamhelmi.auditsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "useraccount")
public class UserAccountDocument {
	
	@Id
	private String id;

	@Field(type = FieldType.Text, name = "userAccountUUID")
	String userAccountUUID;
	
	@Field(type = FieldType.Text, name = "login")
	String login;
	
	@Field(type = FieldType.Text, name = "pin")
	String pin;
	
	@Field(type = FieldType.Text, name = "password")
	String password;
	
	@Field(type = FieldType.Text, name = "role")
	String role;
	
	@Field(type = FieldType.Text, name = "organizationUUID")
	String organizationUUID;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
