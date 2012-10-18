package ca.movielist.core.imdb.test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.movielist.core.imdb.ContentFetcher;

public class ContentFetcherTest {
	ContentFetcher contentFetcher = new ContentFetcher();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFetchStringContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testFetchImageContent() {
		try {
			contentFetcher.fetchImageContent(new URL("http://ia.media-imdb.com/images/M/MV5BMjExNjMzMTUxNV5BMl5BanBnXkFtZTYwNzQyMTg4._V1_SX300.jpg"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

}
