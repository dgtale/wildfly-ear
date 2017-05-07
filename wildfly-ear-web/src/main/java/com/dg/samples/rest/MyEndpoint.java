package com.dg.samples.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dg.samples.rest.annotation.AuthenticatedUser;
import com.dg.samples.rest.annotation.Secured;
import com.dg.test.model.User;

@Path("/")
@RequestScoped
public class MyEndpoint {
	
	@Inject
	@AuthenticatedUser
	User authenticatedUser;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response myUnsecuredMethod(@PathParam("id") Long id) {
		// This method is not annotated with @Secured
		// The authentication filter won't be executed before invoking this
		// method
		return Response.ok().build();
	}

	@DELETE
	@Secured
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response mySecuredMethod(@PathParam("id") Long id
//			, @Context SecurityContext securityContext
			) {

//		Principal principal = securityContext.getUserPrincipal();
//		String username = principal.getName();
//		System.out.println(">>> " + username);
		
		System.out.println(">>> "+authenticatedUser.getEmail());

		// This method is annotated with @Secured
		// The authentication filter will be executed before invoking this
		// method
		// The HTTP request must be performed with a valid token
		return Response.ok().build();
	}
}