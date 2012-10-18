package ca.movielist.core.imdb.test;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.movielist.core.imdb.MovieImdb;
import ca.movielist.core.imdb.UrlBuilderOmdbApi;

public class UrlBuilderOmdbApiTest {

	private MovieImdb movie_1 = new MovieImdb("name");
	private MovieImdb movie_2 = new MovieImdb("name 2");
	private MovieImdb movie_3 = new MovieImdb("name3");
	private MovieImdb movie_4 = new MovieImdb("name3");
	
	private int year_movie_2 = 2001;
	private int year_movie_3 = 2010;
	
	private String movieId = "tt111111";
	
	private String expected_url_1 = "http://www.omdbapi.com/?t=name";
	private String expected_url_2 = "http://www.omdbapi.com/?t=name%202&y=2001";
	private String expected_url_3 = "http://www.omdbapi.com/?t=name3&y=2010";
	private String expected_url_4 = "http://www.omdbapi.com/?i=tt111111";
	
	private UrlBuilderOmdbApi urlBuilder = new UrlBuilderOmdbApi();
	
	@Before
	public void setUp() throws Exception {
		movie_2.setYear(year_movie_2);
		movie_3.setYear(year_movie_3);
		movie_4.setId(movieId);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildUrlByNameYear() {
		URL url1 = urlBuilder.buildUrl(movie_1);
		assertEquals(expected_url_1, url1.toString());
		// TODO test the throw to get better code coverage
		URL url2 = urlBuilder.buildUrl(movie_2);
		assertEquals(expected_url_2, url2.toString());
		
		URL url3 = urlBuilder.buildUrl(movie_3);
		assertEquals(expected_url_3, url3.toString());	
	}

	@Test
	public void testBuildUrlById() {
		URL url4 = urlBuilder.buildUrl(movie_4);
		assertEquals(expected_url_4, url4.toString());
	}
}
