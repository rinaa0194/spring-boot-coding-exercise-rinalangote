package com.telstra.codechallenge.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
		Users userObj = new Users();
		List<Users.Items> list = Arrays.asList(new Users.Items(++idCounter, "abc", "url"),
				new Users.Items(++idCounter, "pqr", "url"));
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
	public void test_InternalServerexceptionInClass() {
		Users userObj = new Users();
		List<Users.Items> list = Arrays.asList(new Users.Items(++idCounter, "abc", "url"),
				new Users.Items(++idCounter, "pqr", "url"));
		userObj.setItems(list);
		when(env.getProperty("users.base.url")).thenReturn("https://api.githubrina.com");

		when(restTemplate.getForObject(
				env.getProperty("users.base.url") + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + 2,
				Users.class)).thenThrow(new InternalSeverException("Error while accessing Git API"));

		Users user = service.getUsers(2);
	}

	@Test(expected = com.telstra.codechallenge.util.MethodArgumentNotValidException.class)
	public void test_MethodArgumentNotValidExceptionInClass() {

		int limit = 0;
		when(service.getUsers(limit)).thenThrow(new com.telstra.codechallenge.util.MethodArgumentNotValidException(
				"Number of accounts to return should be greater than zero"));

		Users user = service.getUsers(limit);
	}

	@Test(expected = UserNotFoundException.class)
	public void test_UserNotFoundExceptionInClass() {
		Users userObj = new Users();
		List listMock = mock(List.class);
		userObj.setItems(listMock);
		when(env.getProperty("users.base.url")).thenReturn("https://api.github.com");

		when(restTemplate.getForObject(
				env.getProperty("users.base.url") + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + 2,
				Users.class)).thenReturn(userObj);
		when(service.getUsers(2)).thenThrow(new UserNotFoundException("Requested number of accounts"));

		Users user = service.getUsers(2);
	}

	@Test(expected = UserNotFoundException.class)
	public void test_UserNotFoundExceptionInClassforemptyObj() {
		Users userObj = new Users();

		when(env.getProperty("users.base.url")).thenReturn("https://api.github.com");

		when(restTemplate.getForObject(
				env.getProperty("users.base.url") + "/search/users?q=followers:0&sort=joined&order=asc&per_page=" + 2,
				Users.class)).thenReturn(userObj);
		when(service.getUsers(2)).thenThrow(new UserNotFoundException("Requested number of accounts"));

		Users user = service.getUsers(2);
	}

}
