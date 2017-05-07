package com.dg.samples.rest.authentication;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.dg.samples.data.MemberRepository;
import com.dg.samples.model.Member;
import com.dg.samples.rest.annotation.AuthenticatedUser;
import com.dg.test.model.User;

@RequestScoped
public class AuthenticatedUserProducer {

	@Inject
	private MemberRepository repository;

	@Produces
	@RequestScoped
	@AuthenticatedUser
	private User authenticatedUser;

	public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
		this.authenticatedUser = findUser(username);
	}

	private User findUser(String username) {
		// Hit the the database or a service to find a user by its username and return it
		// Return the User instance

		Member account = repository.findByEmail(username);

		User user = new User();
		user.setEmail(account.getEmail());
		user.setFirstName(account.getName());

		return user;
	}
}