package ca.movielist.MvcImplementation;

import javax.swing.SwingUtilities;

public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MovieModel model = new MovieModel(); // TODO that sucks
                MovieListController controller = new MovieListController(model);
                controller.displayView();
            }
        });
	}
}
