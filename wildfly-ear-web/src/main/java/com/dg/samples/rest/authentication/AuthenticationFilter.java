package com.dg.samples.rest.authentication;

import java.io.IOException;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.dg.samples.rest.annotation.AuthenticatedUser;
import com.dg.samples.rest.annotation.Secured;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	@Inject
	@AuthenticatedUser
	Event<String> userAuthenticatedEvent;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check if the HTTP Authorization header is present and formatted
		// correctly
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {

			// Validate the token
			validateToken(token);

//			setUserPrincipal(requestContext);
		} catch (Exception e) {
			e.printStackTrace();
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

//	private void setUserPrincipal(ContainerRequestContext requestContext) {
//		final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
//		requestContext.setSecurityContext(new SecurityContext() {
//
//			@Override
//			public Principal getUserPrincipal() {
//
//				return new Principal() {
//
//					@Override
//					public String getName() {
//						return username;
//					}
//				};
//			}
//
//			@Override
//			public boolean isUserInRole(String role) {
//				return true;
//			}
//
//			@Override
//			public boolean isSecure() {
//				return currentSecurityContext.isSecure();
//			}
//
//			@Override
//			public String getAuthenticationScheme() {
//				return "Bearer";
//			}
//		});
//	}

	private void validateToken(String token) throws Exception {
		// Check if it was issued by the server and if it's not expired
		// Throw an Exception if the token is invalid
		
		String username = "john.smith@mailinator.com";
		
		userAuthenticatedEvent.fire(username);
	}
}
