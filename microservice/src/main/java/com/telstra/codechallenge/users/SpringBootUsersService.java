package com.telstra.codechallenge.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringBootUsersService
 * 
 * @author Rina
 *
 */
@Slf4j
@Service
public class SpringBootUsersService {

	@Value("${users.base.url}")
	private String usersBaseUrl;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @method :getUsers Returns a random spring boot oldest user
	 * accounts with zero followers. Taken from https://api.github.com/search/users.
	 * @param limit(number of accounts) 
	 * @return users
	 */
	public Users getUsers(String limit) {
		log.info("Inside getUsers @limit:" + limit);

		return restTemplate.getForObject(
				usersBaseUrl + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + limit, Users.class);
	}

}
