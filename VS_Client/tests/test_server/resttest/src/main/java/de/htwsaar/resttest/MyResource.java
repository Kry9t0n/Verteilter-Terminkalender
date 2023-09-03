package de.htwsaar.resttest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getJSON() {
    	return "\"Username\":\"Alejandro\", \"Password\":\"dkdfjal\"";
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHTML() {
    	return "<html>"
    			+ "<head>"
    			+ "<titel>Got it!</titel>"
    			+ "</head>"
    			+ "<body"
    			+ "<p>Got it!</p>"
    			+ "</body>"
    			+ "</html>";
    }
}
