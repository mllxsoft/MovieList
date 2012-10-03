package ca.movielist.MvcImplementation;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Label;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;

import ca.movielist.core.Movie;
import ca.movielist.core.imdb.MovieImdb;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MovieListView extends JFrame implements MovieListListener, 
		ActionListener { // TODO create custom actionlistner instead of implementing it...
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Object columnNames[] = { "Name", "Year", "Rating", "Genre", "Country", "Id"};
	private static final int columnWidths[] = { 150, 45, 45, 20, 20, 20 }; 
	private JTextField txtEnterMovieName;
	private JScrollPane scrollPane;
	private JTable table;

	private MovieListController controller;

	public MovieListView(MovieListController controller) {
		super();

		// Keep reference on the controller
		this.controller = controller;

		// Create the UI
		Dimension screenSize = getToolkit().getScreenSize();

		setBounds(screenSize.width / 4, screenSize.height / 4,
				screenSize.width / 2, screenSize.height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Movie list...");
		getContentPane().setLayout(
				new MigLayout("", "[grow]", "[][][271.00,grow][grow][85.00,grow]"));

		txtEnterMovieName = new JTextField();
		txtEnterMovieName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtEnterMovieName.setForeground(Color.black);
				if(txtEnterMovieName.getText().equals("Enter movie name here")) {
					txtEnterMovieName.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				txtEnterMovieName.setForeground(Color.GRAY);
				if(txtEnterMovieName.getText().isEmpty()) {
					txtEnterMovieName.setText("Enter movie name here");
				}
			}
		});
		txtEnterMovieName.setForeground(Color.GRAY);
		txtEnterMovieName.setText("Enter movie name here");
		getContentPane().add(txtEnterMovieName, "flowx,cell 0 0,growx");
		txtEnterMovieName.setColumns(10);

		JButton btnNewButton = new JButton("Open in IMDB");
		btnNewButton.addActionListener((ActionListener)this);
		getContentPane().add(btnNewButton, "flowx,cell 0 1");

		JButton btnNewButton_1 = new JButton("Remove from list");
		btnNewButton_1.addActionListener((ActionListener) this);
		getContentPane().add(btnNewButton_1, "cell 0 1");

		JButton btnMoveUp = new JButton("Up");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		getContentPane().add(btnMoveUp, "cell 0 1");

		JButton btnMoveDown = new JButton("Down");
		getContentPane().add(btnMoveDown, "cell 0 1");
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 2,grow");
		
           
		table = new JTable(new Object[0][4], columnNames);
		scrollPane.setViewportView(table);
		table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
			TitledBorder.TOP, null, null));
        
		Label label = new Label("Status...");
		getContentPane().add(label, "cell 0 4");

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener((ActionListener) this);
		getContentPane().add(btnAdd, "cell 0 0");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpenDatabase = new JMenuItem("Open database");
		mnFile.add(mntmOpenDatabase);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener((ActionListener) this);
		mnFile.add(mntmExit);
		
		getRootPane().setDefaultButton(btnAdd);  
	}

	// TODO may be I could couple the controller with the view very tightly so
	// the controller would manipulate each Textbox/Buttond
	// almost directly

	// Create nested class ?
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0.getActionCommand());
		
		if(arg0.getActionCommand().equals("Exit")) {
			// save the database in a new thread... TODO
			// the loading should be threaded too
			controller.exitApp();
		} else if(arg0.getActionCommand().equals("Remove from list")) {
			removeFromListButtonPressed();
		} else if(arg0.getActionCommand().equals("Add")) {
			addButtonPressed();
		} else if(arg0.getActionCommand().equals("Open in IMDB")) {
			openWithIMDBButtonPressed();	
		}
	}

	private void removeFromListButtonPressed() {
		String selectedName = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
		controller.removeMovie(selectedName);
	}

	private void openWithIMDBButtonPressed() {
		String selectedName = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
		controller.browseMovie(selectedName);
	}

	private void addButtonPressed() {
		String text = txtEnterMovieName.getText();
		if (text.equals("") == false) {
			controller.searchMovie(text);
			txtEnterMovieName.setText("");
			System.out.println("Add movie to list..." + text);
			txtEnterMovieName.requestFocus();
		} else {
			System.out.println("Nothing added to the list...");
		}
	}

	public void display() {
		setVisible(true);
        table.requestFocus();
        table.getColumnModel().getColumn(0).setPreferredWidth(columnWidths[0]);
        table.getColumnModel().getColumn(1).setMaxWidth(columnWidths[1]);
        table.getColumnModel().getColumn(2).setMaxWidth(columnWidths[2]);
        table.getColumnModel().getColumn(3).setPreferredWidth(columnWidths[3]);
        table.getColumnModel().getColumn(4).setPreferredWidth(columnWidths[4]);
	}

	@Override
	public void movieListChanged(MovieListChangedEvent event) {
		System.out.println("movieListChanged called...");
		MovieModel model = event.getModel();
		// TODO change the event to only notify the movie that has changed... instead of clearing the whole table
		//table.removeAll();

		Object [][] lines = new Object[model.getMovieQty()][6];
		int currentLine = 0;
		
		Iterator<Movie> iterator = model.iterator();
		while(iterator.hasNext()) {
			MovieImdb movieImdb = (MovieImdb)iterator.next();
			lines[currentLine][0] = movieImdb.getName();
			lines[currentLine][1] = movieImdb.getYear();
			lines[currentLine][2] = movieImdb.getRating();
			lines[currentLine][3] = movieImdb.getGenre();
			lines[currentLine][4] = movieImdb.getCountry();
			lines[currentLine][5] = movieImdb.getId();	
			currentLine++;
		}
		
		// TODO completely redo this by looking at JTable sample
		table = new JTable(lines, columnNames);
		scrollPane.setViewportView(table);
	}
}
