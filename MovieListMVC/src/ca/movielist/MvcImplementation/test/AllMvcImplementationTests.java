package ca.movielist.MvcImplementation.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MovieListChangedEventTest.class, MovieListControllerTest.class,
		MovieListViewTest.class, MovieModelTest.class,
		TorrentZUrlBuilderTest.class })
public class AllMvcImplementationTests {

}
