package com.telstra.codechallenge.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Class: Users
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
		@JsonCreator
		public Items(@JsonProperty("idCounter")long idCounter,@JsonProperty("login") String login,@JsonProperty("html_url") String htmlUrl) {
			super();
			this.id = idCounter;
			this.login = login;
			this.htmlUrl = htmlUrl;
		}

		private Long id;
		private String login;
		@JsonProperty("html_url")
		private String htmlUrl;
	}

	
}
