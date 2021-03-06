package com.telstra.codechallenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.dto.Users.Items;
import com.telstra.codechallenge.service.SpringBootUsersService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class: SpringBootUsersController
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
	public List<Items> getUsers(@PathVariable Integer limit) {
		log.info("Inside users controller @param limit:"+limit);
		return springBootUsersService.getUsers(limit).getItems();
	}
}
