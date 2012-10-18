package ca.movielist.core.imdb.test;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.movielist.core.imdb.ContentFetcher;
import ca.movielist.core.imdb.MovieImdb;
import ca.movielist.core.imdb.MovieLookupImdb;

public class MovieLookupImdbTest {

	private MovieLookupImdb lookup = null;
	
	// Create a content fetcher mock which doesn't fetch data from Internet
	public class MockContentFetcher extends ContentFetcher {
		private String mockContent;
		
		public MockContentFetcher(String mockContent) {
			super();
			this.mockContent = mockContent;
		}
		
		@Override
		public String fetchStringContent(URL url) {
			return mockContent;
		}
	}
	
	private MovieImdb movie_1 = new MovieImdb("True Grit");
	private String content_1 = new String( "{\"imdbid\":\"tt0065126\",\"imdburl\":\"http:\\/\\/www.imdb.com\\/title\\/tt0065126\\/\",\"genres\":\"Adventure,Western,Drama\",\"languages\":\"English\",\"country\":\"USA\",\"votes\":\"20576\",\"stv\":0,\"series\":0,\"rating\":\"7.3\",\"runtime\":\"128min\",\"title\":\"True Grit\",\"year\":\"1969\",\"usascreens\":0,\"ukscreens\":0}");
		
	private int year = 1969;
	private String name = "True Grit";
	private String id = "tt0065126";
	private float rating = 7.3f;
	
	@Before
	public void setUp() throws Exception {
		
		movie_1.setYear(year);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMovieLookupImdb() {
		lookup = new MovieLookupImdb(new MockContentFetcher(content_1));
		assertTrue(lookup != null);
	}

	@Test
	public void testLookupInformations() {
		if (lookup == null) 
			testMovieLookupImdb();
		// TODO the two passes should be separated into to lookup class
		lookup.lookupInformations(movie_1);
		
		assertFalse(movie_1.getRating() == -1);
		assertEquals(id, movie_1.getId());
		assertTrue(rating == movie_1.getRating());
		assertEquals(name, movie_1.getName());
		assertEquals(year, movie_1.getYear());
	}
}
