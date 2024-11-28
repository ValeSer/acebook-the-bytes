package com.makersacademy.acebook.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PostTest {

	private Post post = new Post("hello", "/images/something.jpg", LocalDateTime.of(2023, 11, 27, 12, 0), 1L);

	@Test
	public void postHasContent() {
		assertThat(post.getContent(), containsString("hello"));
	}

}
