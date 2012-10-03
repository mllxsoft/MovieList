package ca.movielist.MvcImplementation;

import java.util.EventListener;

// This kind of class is like a "protocol" class in objectiveC++

public interface MovieListListener extends EventListener {
	public void movieListChanged(MovieListChangedEvent event);
}
