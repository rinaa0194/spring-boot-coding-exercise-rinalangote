package com.telstra.codechallenge.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.users.Users.Items;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringBootUsersController
 * 
 * @author Rina
 *
 */
@Slf4j
@RestController
public class SpringBootUsersController {

	@Autowired
	private SpringBootUsersService springBootUsersService;

	@GetMapping("/users/{limit}")
	public List<Items> users(@PathVariable String limit) {
		log.info("Inside users controller");
		Users users = springBootUsersService.getUsers(limit);
		return users.getItems();
	}
}
