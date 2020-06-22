package com.telstra.codechallenge.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.telstra.codechallenge.dto.Users;
import com.telstra.codechallenge.service.SpringBootUsersService;
import com.telstra.codechallenge.util.InternalSeverException;
import com.telstra.codechallenge.util.UserNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class SpringBootUsersServiceTest {
	private static long idCounter = 1;
	@InjectMocks
	SpringBootUsersService service = new SpringBootUsersService();

	@Mock
	RestTemplate restTemplate;

	@Mock
	private Environment env;

	@Test
	public void testgetUsersGitApi() {
		Users userObj = new Users();
		List<Users.Items> list = Arrays.asList(new Users.Items(++idCounter, "Jon", "https://github.com/jon001"),
				new Users.Items(++idCounter, "Rina", "https://github.com/engineyard"));
		userObj.setItems(list);
		when(env.getProperty("users.base.url")).thenReturn("https://api.github.com");
		when(restTemplate.getForObject(
				env.getProperty("users.base.url") + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + 2,
				Users.class)).thenReturn(userObj);
		Users user = service.getUsers(2);
		assertEquals(userObj, user);
		assertEquals(2, user.getItems().size());
	}

	@Test(expected = InternalSeverException.class)
	public void testgetUsersGitApiInternalServerexception() {
		Users userObj = new Users();
		List<Users.Items> list = Arrays.asList(new Users.Items(++idCounter, "Jon", "https://github.com/jon001"),
				new Users.Items(++idCounter, "Rina", "https://github.com/engineyard"));
		userObj.setItems(list);
		when(env.getProperty("users.base.url")).thenReturn("https://api.githubrina.com");
		when(restTemplate.getForObject(
				env.getProperty("users.base.url") + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + 2,
				Users.class)).thenThrow(new InternalSeverException("Error while accessing Git API"));
		service.getUsers(2);
	}

	@Test(expected = com.telstra.codechallenge.util.MethodArgumentNotValidException.class)
	public void testgetUsersMethodArgumentNotValidException() {
		int limit = 0;
		service.getUsers(limit);
	}

	@Test(expected = UserNotFoundException.class)
	public void testUserNotFoundExceptionforgetUsersMethodtoCheckemptyObj() {
		Users userObj = new Users();
		List<Users.Items> list = Arrays.asList();

		userObj.setItems(list);
		when(env.getProperty("users.base.url")).thenReturn("https://api.github.com");

		when(restTemplate.getForObject(
				env.getProperty("users.base.url") + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + 2,
				Users.class)).thenReturn(userObj);
		service.getUsers(2);

	}

	@Test(expected = UserNotFoundException.class)
	public void testUserNotFoundExceptionforgetUsersMethodtoCheckemptyList() {
		Users userObj = null;
		when(restTemplate.getForObject(
				env.getProperty("users.base.url") + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + 2,
				Users.class)).thenReturn(userObj);
		service.getUsers(2);
	}

}
