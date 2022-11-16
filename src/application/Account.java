package application;

public class Account {
	
	private String username;
	private String email;
	
	public String get_username() {
		return username;
	}
	
	public String get_email() {
		return email;
	}
	
	public Account(Account account) {
		username = account.get_username();
		email = account.get_email();
	}
	
	public Account(String username, String email) {
		this.username = username;
		this.email = email;
		
	}
	
}
