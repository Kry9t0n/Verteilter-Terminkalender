package de.htwsaar.resttest;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("users")
@Singleton
public class UserResource {
	private final String ACCEPT_USERNAME = "Alejandro";
	private final String ACCEPT_PASSWD = "kdfakjdf";
	
	private User user;
	
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUser() {
		System.out.println("getUser called...");
		
		if(user == null) {
			return "User is currently null";
		}
		
		
		return this.user.toString();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User postUser(User u1) {
		System.out.println("postUser called...");
		
		System.out.println(u1);
		
		if(u1.getUsername().equals(ACCEPT_USERNAME) && u1.getPassword().equals(ACCEPT_PASSWD)) {
			this.user = new User();
			
			this.user.setUsername(u1.getUsername());
			this.user.setPassword(u1.getPassword());
			this.user.setAdmin(u1.isAdmin());
		}else {
			this.user = null;
			System.out.println("Login credentials don't match!");
		}
		
		
		
		System.out.println(this.user);
		
		return user;
	}
	
}
