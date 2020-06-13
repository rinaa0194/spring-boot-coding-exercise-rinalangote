package com.telstra.codechallenge.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpringBootUsersService {

	@Value("${users.base.url}")
	private String usersBaseUrl;

	@Autowired
	private RestTemplate restTemplate;

	public Users getUsers(String limit) {
		return restTemplate.getForObject(usersBaseUrl + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + limit,
				Users.class);
	}

}
