package com.telstra.codechallenge.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class: SpringBootUsersService
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
	 * @method :getUsers
	 * @param limit(number of accounts)
	 * @return users(Returns a oldest user accounts with zero followers. Taken from
	 *         https://api.github.com/search/users.)
	 * @throws Exception 
	 */
	public Users getUsers(Integer limit) {
		log.info("Inside @serviceMethod getUsers @limit:" + limit);

		if (limit == 0)
			throw new UserMethodArgumentNotValidException(" number of accounts to return should be greater than zero");

		Users user = restTemplate.getForObject(
				usersBaseUrl + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + limit, Users.class);

		if (CollectionUtils.isEmpty(user.getItems()))
			throw new UserNotFoundException("with number of accounts:" + limit);
		return user;
	}

}
