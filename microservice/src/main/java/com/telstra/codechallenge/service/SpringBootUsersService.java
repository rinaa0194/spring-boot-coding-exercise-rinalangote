package com.telstra.codechallenge.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.telstra.codechallenge.dto.Users;
import com.telstra.codechallenge.util.InternalSeverException;
import com.telstra.codechallenge.util.MethodArgumentNotValidException;
import com.telstra.codechallenge.util.UserNotFoundException;

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
	 * @return users(Returns a oldest user accounts with zero followers. 
	 * Taken from https://api.github.com/search/users.)
	 * @throws Exception
	 */
	public Users getUsers(Integer limit) {
		log.info("Inside @serviceMethod getUsers @param limit:" + limit);

		if (limit <= 0)
			throw new MethodArgumentNotValidException("Number of accounts to return should be greater than zero");

		Users user = null;
		try {
			user = restTemplate.getForObject(
					usersBaseUrl + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + limit, Users.class);
		} catch (Exception e) {
			throw new InternalSeverException("Error while accessing Git API");

		}

		if (Objects.isNull(user))
			throw new UserNotFoundException("Git API response is null or empty:" + limit);

		else if (CollectionUtils.isEmpty(user.getItems()))
			throw new UserNotFoundException("Requested number of accounts:" + limit);

		return user;
	}

}
