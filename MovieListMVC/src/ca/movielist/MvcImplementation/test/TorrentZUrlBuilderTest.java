package ca.movielist.MvcImplementation.test;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.movielist.MvcImplementation.TorrentZUrlBuilder;
import ca.movielist.core.imdb.MovieImdb;

public class TorrentZUrlBuilderTest {

	private MovieImdb movie_1 = new MovieImdb("batman");
	private MovieImdb movie_2 = new MovieImdb("batman & robin");
	
	private String expected_url_1 = "http://torrentz.eu/search?f=batman+2005";
	private String expected_url_2 = "http://torrentz.eu/search?f=batman+%26+robin";
	
	private TorrentZUrlBuilder urlBuilder = new TorrentZUrlBuilder();
	
	@Before
	public void setUp() throws Exception {
		movie_1.setYear(2005);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildUrl() {
		URL url1 = urlBuilder.buildUrl(movie_1);
		assertEquals(expected_url_1, url1.toString());
	}

	@Test
	public void testBuildUrlWithYear() {
		URL url2 = urlBuilder.buildUrl(movie_2);
		assertEquals(expected_url_2, url2.toString());
	}

}
