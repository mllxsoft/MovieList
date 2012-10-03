package ca.movielist.MvcImplementation;

import java.util.EventObject;

public class MovieListChangedEvent extends EventObject {

	private MovieModel model;
	
	public MovieListChangedEvent(Object source){
		super(source);
		model = (MovieModel) source;
	}
	
	public MovieModel getModel(){
		return model;
	}
}
