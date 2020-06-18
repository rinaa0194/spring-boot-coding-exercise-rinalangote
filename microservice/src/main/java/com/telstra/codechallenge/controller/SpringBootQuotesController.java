package com.telstra.codechallenge.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.dto.Quote;
import com.telstra.codechallenge.service.SpringBootQuotesService;

@RestController
public class SpringBootQuotesController {
	@Autowired
	private SpringBootQuotesService springBootQuotesService;

	@GetMapping(path = "/quotes")
	public List<Quote> quotes() {
		return Arrays.asList(springBootQuotesService.getQuotes());
	}

	@GetMapping(path = "/quotes/random")
	public Quote quote() {
		return springBootQuotesService.getRandomQuote();
	}

}
