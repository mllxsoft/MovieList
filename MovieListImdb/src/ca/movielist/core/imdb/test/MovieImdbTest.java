package ca.movielist.core.imdb.test;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.movielist.core.imdb.MovieImdb;

public class MovieImdbTest {
	String referenceName = "MovieName";
	String newRefenreceName = "TheNewMovieName";
	
	String referenceId = "112233444";
	String newReferenceId = "22233344555";
	
	int referenceYear = 1999;
	int newReferenceYear = 2001;
	
	int referenceRating = 8;
	int newReferenceRating = 4;
	
	MovieImdb testedMovie;
	MovieImdb toChangeMovie;
	
	String referenceCountry = "Canada";
	String newReferenceCountry = "USA";
	
	String referenceGenre = "Horror";
	String newReferenceGenre = "Horror,Drame";
	
	URL referenceURL;
	URL newReferenceURL;
	
	@Before
	public void setUp() throws Exception {
		referenceURL = new URL("http://google.com");
		newReferenceURL = new URL("http://microsoft.com");
		
		testedMovie = new MovieImdb(referenceName);
		testedMovie.setYear(referenceYear);
		testedMovie.setRating(referenceRating);	
		testedMovie.setId(referenceId);
		testedMovie.setCountry(referenceCountry);
		testedMovie.setGenre(referenceGenre);
		testedMovie.setUrl(referenceURL);
		
		toChangeMovie = new MovieImdb(referenceName);
		toChangeMovie.setYear(referenceYear);
		toChangeMovie.setRating(referenceRating);
		toChangeMovie.setId(newReferenceId);
		toChangeMovie.setCountry(referenceCountry);
		toChangeMovie.setGenre(referenceGenre);
		toChangeMovie.setUrl(referenceURL);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetName() {
		toChangeMovie.setName(newRefenreceName);
		assertFalse(toChangeMovie.getName().equals(referenceName));
		assertEquals(toChangeMovie.getName(), newRefenreceName);
	}

	@Test
	public void testGetName() {
		assertEquals(testedMovie.getName(), referenceName);
	}

	@Test
	public void testSetRating() {
		toChangeMovie.setRating(newReferenceRating);
		assertFalse(toChangeMovie.getRating() == referenceRating);
		assertTrue(toChangeMovie.getRating() == newReferenceRating);
	}
	
	@Test
	public void testGetRating() {
		assertTrue(testedMovie.getRating() == referenceRating);
	}

	@Test
	public void testSetYear() {
		toChangeMovie.setYear(newReferenceYear);
		assertFalse(toChangeMovie.getYear() == referenceYear);
		assertEquals(toChangeMovie.getYear(), newReferenceYear);
	}
	
	@Test
	public void testGetYear() {
		assertEquals(testedMovie.getYear(), referenceYear);
	}

	@Test
	public void testSetId() {
		toChangeMovie.setId(newReferenceId);
		assertFalse(toChangeMovie.getId() == referenceId);
		assertEquals(toChangeMovie.getId(), newReferenceId);
	}
	
	@Test
	public void testGetId() {
		assertEquals(testedMovie.getId(), referenceId);
	}
	
	@Test
	public void testSetCountry() {
		toChangeMovie.setCountry(newReferenceCountry);
		assertFalse(toChangeMovie.getCountry() == referenceCountry);
		assertEquals(toChangeMovie.getCountry(), newReferenceCountry);
	}
	
	@Test
	public void testGetCountry() {
		assertEquals(testedMovie.getCountry(), referenceCountry);
	}
	
	@Test
	public void testSetGenre() {
		toChangeMovie.setGenre(newReferenceGenre);
		assertFalse(toChangeMovie.getGenre() == referenceGenre);
		assertEquals(toChangeMovie.getGenre(), newReferenceGenre);
	}
	
	@Test
	public void testGetGenre() {
		assertEquals(testedMovie.getGenre(), referenceGenre);
	}
	
	@Test
	public void testSetUrl() {
		toChangeMovie.setUrl(newReferenceURL);
		assertFalse(toChangeMovie.getUrl() == referenceURL);
		assertEquals(toChangeMovie.getUrl(), newReferenceURL);
	}
	
	@Test
	public void testGetUrl() {
		assertEquals(testedMovie.getUrl(), referenceURL);
	}
	
	@Test
	public void testMovieIMDB() {
		MovieImdb testMovie = new MovieImdb(referenceName);
		assertTrue(testMovie != null);
		assertEquals(testMovie.getName(), referenceName);
	}

}
