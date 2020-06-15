package com.telstra.codechallenge.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Users
 * @author Rina
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Users {

	@JsonProperty("items")
	private List<Items> items;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@Data
	public static class Items {
		private Long id;
		private String login;
		private String html_url;
	}
}
