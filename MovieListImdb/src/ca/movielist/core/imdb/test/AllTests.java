package ca.movielist.core.imdb.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MovieImdbTest.class, MovieListImdbTest.class, UrlBuilderImdbTest.class, MovieLookupImdbTest.class})
public class AllTests {

}
