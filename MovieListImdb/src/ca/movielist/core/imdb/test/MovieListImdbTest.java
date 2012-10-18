package ca.movielist.core.imdb.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.movielist.core.imdb.MovieImdb;
import ca.movielist.core.imdb.MovieListImdb;

public class MovieListImdbTest {

	private MovieListImdb movieList_load;
	
	private MovieListImdb movieList_static;
	private MovieListImdb movieList_add;
	private MovieListImdb movieList_remove;
	
	String referenceName_add = "AddedMovie";
	String referenceName_remove = "ToRemoveMovie";
	
	String referenceFilename = "testDB.csv";
	String newFilename = "outDB.csv";
	
	MovieImdb testedMovie_add;
	MovieImdb testedMovie_remove;
	
	@Before
	public void setUp() throws Exception {
		movieList_load = new MovieListImdb();
		
		movieList_static = new MovieListImdb();
		movieList_add = new MovieListImdb();
		movieList_remove = new MovieListImdb();
		
		testedMovie_add = new MovieImdb(referenceName_add);
		testedMovie_remove = new MovieImdb(referenceName_remove);
		
		movieList_remove.addMovie(testedMovie_remove);
		movieList_static.addMovie(testedMovie_add);
		movieList_static.addMovie(testedMovie_remove);
		
		movieList_static.SaveMovies(referenceFilename);
	}

	@After
	public void tearDown() throws Exception {
		// delete databases
		File referenceFile = new File(referenceFilename);
		referenceFile.delete();
		
		File newFile = new File(newFilename);
		newFile.delete();
	}

	@Test
	public void testAddMovie() {
		assertFalse(movieList_add.containMovie(testedMovie_add.getName()));
		movieList_add.addMovie(testedMovie_add);
		assertTrue(movieList_add.containMovie(testedMovie_add.getName()));
	}

	@Test
	public void testRemoveMovie() {
		assertTrue(movieList_remove.containMovie(testedMovie_remove.getName()));
		movieList_remove.removeMovie(testedMovie_remove);
		assertFalse(movieList_remove.containMovie(testedMovie_remove.getName()));
	}

	@Test
	public void testGetMovieQty() {
		assertEquals(movieList_static.getMovieQty(), 2);
	}

	@Test
	public void testGetMovie() {
		assertSame(movieList_static.getMovie(referenceName_add), testedMovie_add);
		assertSame(movieList_static.getMovie(referenceName_remove), testedMovie_remove);
	}

	@Test
	public void testLoadMovies() {
		assertEquals(movieList_load.getMovieQty(), 0);
		movieList_load.LoadMovies(referenceFilename);
		assertEquals(movieList_load.getMovieQty(), 2);
		assertTrue(movieList_load.containMovie(referenceName_add));
		assertTrue(movieList_load.containMovie(referenceName_remove));
	}

	@Test
	public void testSaveMovies() {
		movieList_static.SaveMovies(newFilename);
		// TODO using the size for save validation totally sucks
		File referenceFile = new File(referenceFilename);
		File newFile = new File(newFilename);
		
		assertEquals(referenceFile.length(), newFile.length());
		
		movieList_add.addMovie(testedMovie_add);
		movieList_add.addMovie(testedMovie_remove);
		movieList_add.addMovie(testedMovie_add);
		movieList_add.addMovie(testedMovie_add);
		movieList_add.addMovie(testedMovie_add);
		movieList_add.addMovie(testedMovie_add);
		
		movieList_add.SaveMovies(newFilename);
		
		File newFile2 = new File(newFilename);
		
		assertTrue(referenceFile.length() < newFile2.length());
		
		newFile2.delete();
	}

	@Test
	public void testGetMovieNames() {
		String[] movieNames = movieList_static.getMovieNames();
		assertEquals(movieNames[0], referenceName_add);
		assertEquals(movieNames[1], referenceName_remove);
	}

	@Test
	public void testContainsMovie() {
		assertTrue(movieList_static.containMovie(referenceName_add));
		assertFalse(movieList_static.containMovie("noname"));
	}
}
