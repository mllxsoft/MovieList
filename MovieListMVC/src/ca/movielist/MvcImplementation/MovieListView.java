package ca.movielist.MvcImplementation;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;
import java.util.Iterator;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JTable;

import ca.movielist.core.Movie;
import ca.movielist.core.imdb.MovieImdb;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private TextArea label;
	
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
				new MigLayout("", "[778.00,grow][][][][][][][]", "[][][271.00,grow][grow][85.00,grow]"));

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
		
		JButton btnTorrent = new JButton("Torrent");
		btnTorrent.addActionListener((ActionListener)this);
		getContentPane().add(btnTorrent, "cell 0 1");
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
        table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				displayPosterImage("image.jpg");
			}
		});
		
		JLabel lblNewLabel = new JLabel("New label");
		getContentPane().add(lblNewLabel, "cell 1 2");
        
		label = new TextArea("Welcome to MovieList...");
		label.setMaximumSize(new Dimension(100000, 100));
		getContentPane().add(label, "flowx,cell 0 4");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpenDatabase = new JMenuItem("Open...");
		mntmOpenDatabase.addActionListener((ActionListener) this);
		mnFile.add(mntmOpenDatabase);

		JMenuItem mntmSaveAsDatabase = new JMenuItem("Save as...");
		mntmSaveAsDatabase.addActionListener((ActionListener) this);
		mnFile.add(mntmSaveAsDatabase);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener((ActionListener) this);
		mnFile.add(mntmExit);
		
		JButton btnRefreshInfo = new JButton("Refresh Info");
		btnRefreshInfo.addActionListener((ActionListener) this);
		getContentPane().add(btnRefreshInfo, "cell 0 1");
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener((ActionListener) this);
		getContentPane().add(btnAdd, "cell 0 0");
		
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
		} else if(arg0.getActionCommand().equals("Save as...")) {
			// save the database in a new thread... TODO
			// the loading should be threaded too
			controller.saveAs();
		} else if(arg0.getActionCommand().equals("Open...")) {
			// save the database in a new thread... TODO
			// the loading should be threaded too
			controller.openOther();
		} else if(arg0.getActionCommand().equals("Remove from list")) {
			removeFromListButtonPressed();
		} else if(arg0.getActionCommand().equals("Add")) {
			addButtonPressed();
		} else if(arg0.getActionCommand().equals("Open in IMDB")) {
			openWithIMDBButtonPressed();	
		} else if(arg0.getActionCommand().equals("Torrent")) {
			torrentButtonPressed();	
		} else if(arg0.getActionCommand().equals("Refresh Info")) {
			refreshInfoButtonPressed();	
		} 
	}

	private void removeFromListButtonPressed() {
		String selectedName = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
		controller.removeMovie(selectedName);
	}

	private void torrentButtonPressed() {
		String selectedName = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
		controller.torrentMovie(selectedName);
	}

	private void refreshInfoButtonPressed() {
		String selectedName = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
		controller.refreshMovie(selectedName);
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
	
	String chooseFile() {
		JFileChooser jfc=new JFileChooser(System.getProperty("user.dir","."));
		 
		FileFilter ff = new FileFilter(){
			public boolean accept(File f){
				if(f.isDirectory()) return true;
				else if(f.getName().endsWith(".db")) return true;
					else return false;
			}
			public String getDescription(){
				return "MovieList database files";
			}
		};
		 
		jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
		jfc.setFileFilter(ff);
		
		String fileName = "";
		if(jfc.showDialog(this,"Open Database")==JFileChooser.APPROVE_OPTION) {
	        fileName = jfc.getSelectedFile().getPath();
		}
		return fileName;
	}
	
	int askConfirmation(String fileName) {
		return JOptionPane.showConfirmDialog(null, "Do you wany to save all changes made to " + fileName);
	}
	
	void appendLineToStatus(String line) {
		label.setText(label.getText() + "\n" + line);
	}
	
	void displayPosterImage(String filePath) {
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File(filePath));
			JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
			getContentPane().add(picLabel, "cell 0 4");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
