package com.telstra.codechallenge.quotes;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.users.Users;
import com.telstra.codechallenge.users.Users.Items;

@RestController
public class SpringBootQuotesController {

  private SpringBootQuotesService springBootQuotesService;

  public SpringBootQuotesController(
      SpringBootQuotesService springBootQuotesService) {
    this.springBootQuotesService = springBootQuotesService;
  }

  @RequestMapping(path = "/quotes", method = RequestMethod.GET)
  public List<Quote> quotes() {
    return Arrays.asList(springBootQuotesService.getQuotes());
  }

  @RequestMapping(path = "/quotes/random", method = RequestMethod.GET)
  public Quote quote() {
     Quote quote=springBootQuotesService.getRandomQuote();
     
     return quote;
  }
  
	/*
	 * @GetMapping("/users/{limit}") public List<Items> users(@PathVariable String
	 * limit) { Users users=springBootQuotesService.getUsers(limit); return
	 * users.getItems(); }
	 */
}
