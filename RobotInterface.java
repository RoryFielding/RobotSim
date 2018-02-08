package robotsimulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RobotInterface extends Application implements Serializable {
	/**
	 * @author R J Fielding
	 */
	private static final long serialVersionUID = 3933468840275763787L;
	private GraphicsContext gc; // graphics context for drawing it
	private AnimationTimer timer; // timer used for animation
	private VBox rtPane; // vertical box for putting info
	private RobotArena arena; // robot arena to display in GUI
	private Stage stage; // stage declaration for save/load

	/**
	 * Function to show the about box
	 */
	private void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION); // define what box is
		alert.setTitle("About"); // say is About
		alert.setHeaderText(null);
		alert.setContentText("Robot Simulator Program by Rory John Fielding \n\n"
				+ "Press 'Start' to being the simulation. \n"
				+ "Press 'Pause to stop the simulation. \n"
				+ "Press 'Clear All' to clear objects from the screen. \n"
				+ "Add new robots to the arena from the buttons below.\n"
				+ "Save and load different arenas from the file menu."); // give
																			// text
		alert.showAndWait(); // show box and wait for user to close
	}

	/**
	 * Function for inputting a value to alter x size of the arena
	 */
	private void showXinput() {

		TextInputDialog dialog = new TextInputDialog("X Size");
		dialog.setTitle("Input X Size");
		dialog.setHeaderText("X Size");
		dialog.setContentText("Please enter a value for x:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("X Value: " + result.get());
			String xbuffer = result.get();
			int xbufferparsed = Integer.parseInt(xbuffer);
			arena.setXSize(xbufferparsed);
		}
	}

	/**
	 * Function for inputting a value to alter y size of the arena
	 */
	private void showYinput() {
		TextInputDialog dialog = new TextInputDialog("Y Size");
		dialog.setTitle("Input Y Size");
		dialog.setHeaderText("Y Size");
		dialog.setContentText("Please enter a value for Y:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Y Value: " + result.get());
			String ybuffer = result.get();
			int ybufferparsed = Integer.parseInt(ybuffer);
			arena.setYSize(ybufferparsed);
		}
	}

	/**
	 * Function for allowing a user to alter the maximum robots in the arena
	 */
	private void showmaxRobotsinput() {
		TextInputDialog dialog = new TextInputDialog("Maximum Robots");
		dialog.setTitle("Input a maximum number of robots");
		dialog.setHeaderText("Max Robots");
		dialog.setContentText("Please enter a value for the maximum robots:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("MaxRobots: " + result.get());
			String maxrobotsbuffer = result.get();
			int maxrobotsbufferparsed = Integer.parseInt(maxrobotsbuffer);
			arena.setMaxRobots(maxrobotsbufferparsed);
		}
	}

	/**
	 * Function to return the graphics context
	 * 
	 * @return gc
	 */
	public GraphicsContext getgc() {
		return gc;
	}

	/**
	 * This function allows a user to save their current robot arena to a file.
	 * By utilising FileChooser, the user is able to specificy the location that
	 * they would like to save their file. If the location and file type and
	 * correct, this will write the arena object to this file and output to the
	 * user that the file has been written.
	 */
	public void saveFile() {

		FileChooser fileChooser = new FileChooser();

		File file = fileChooser.showSaveDialog(stage); // Show save file dialog

		FileOutputStream fileOs = null;
		ObjectOutputStream objectOs = null;
		try {
			fileOs = new FileOutputStream(file);
			objectOs = new ObjectOutputStream(fileOs);
			objectOs.writeObject(this.arena);
			objectOs.flush();
			objectOs.close();
			fileOs.flush();
			fileOs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (file != null) {
			System.out.println("File written: " + file);
		}
	}

	/**
	 * This function allows a user to load a certain file when called. By
	 * utilising FileChooser, the user is able to select which file they would
	 * like to load. This file is then set to be the current robot arena. If the
	 * file type a user is attempting to load is of an invalid type, such as a
	 * word document, the user will be informed that this is an invalid option.
	 */

	public void loadFile() {

		FileChooser fileChooser = new FileChooser();

		File file = fileChooser.showOpenDialog(stage); // Show load file dialog
		try {
			FileInputStream inFile = new FileInputStream(file);
			ObjectInputStream inObj = new ObjectInputStream(inFile);
			this.arena = (RobotArena) inObj.readObject();
			inFile.close();
			inObj.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("This is an invalid file type");
		}
		if (file != null) {
			System.out.println("Chosen file: " + file);
		}
		drawWorld();

	}

	/**
	 * This is a function which initialises the robot arena with all of the
	 * objects loading from a file. If the file cannot be found, a new arena
	 * will be created with each object inside it.
	 */
	public void startUp() {

		int ctr = 0; // initialise counter
		try {
			File file = new File(
					"StartUp.bin");
			FileInputStream inFile = new FileInputStream(file);
			ObjectInputStream inObj = new ObjectInputStream(inFile);
			this.arena = (RobotArena) inObj.readObject();
			inFile.close();
			inObj.close();
			drawWorld();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ctr++;
		} catch (IOException e) {
			e.printStackTrace();
			ctr++;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			ctr++;
		}
		if (ctr != 0) {
			System.out.println("File cannot be loaded");
			this.arena = new RobotArena(400, 500);
			arena.addLightBot();
			arena.addLightSensorBot();
			arena.addTravelBot();
			arena.addWhiskerBot();
			arena.addWallBot();
			drawWorld();
		}
	}

	/**
	 * Set up the mouse events for the canvas
	 * 
	 * @param canvas
	 */
	public void setMouseEvents(Canvas canvas) {
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, // for MOUSE PRESSED
															// event
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						drawWorld();
						drawStatus();
					}
				});
	}

	/**
	 * Initialisation for stage required for save/load file
	 * 
	 * @param primaryStage
	 *            stage
	 */
	public void init(Stage primaryStage) {
		this.stage = primaryStage;
	}

	/**
	 * Set up the menu of commands for the GUI
	 * 
	 * @return the menu bar
	 */
	private MenuBar setMenu() {
		MenuBar menuBar = new MenuBar(); // create main menu

		Menu mFile = new Menu("File"); // add File main menu

		MenuItem mExit = new MenuItem("Exit"); // whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) { // action on exit is
				timer.stop(); // stop timer
				System.exit(0); // exit program
			}
		});

		MenuItem mSave = new MenuItem("Save"); // whose sub menu has save
		mSave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				saveFile();
			}
		});

		MenuItem mLoad = new MenuItem("Load"); // whose sub menu has load
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				loadFile();
			}
		});

		mFile.getItems().addAll(mSave, mLoad, mExit); // add exit to File menu

		Menu mHelp = new Menu("Help"); // create Help menu
		MenuItem mAbout = new MenuItem("About"); // add About sub men item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				showAbout(); // and its action to print about
			}
		});

		mHelp.getItems().addAll(mAbout); // add About to Help main item

		Menu mConfig = new Menu("Configure"); // add Configure main menu

		MenuItem msetX = new MenuItem("Set X Size of Arena"); // set size of
																// arena
		msetX.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showXinput();
			}
		});

		MenuItem msetY = new MenuItem("Set Y Size of Arena"); // set size of
																// arena
		msetY.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg1) {
				showYinput();
			}
		});

		MenuItem msetmaxrobots = new MenuItem("Set Max Robots"); // set robot
																	// parameters
		msetmaxrobots.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg1) {
				showmaxRobotsinput();
			}
		});

		mConfig.getItems().addAll(msetX, msetY, msetmaxrobots); // add these to
																// menu

		Menu mRun = new Menu("Run"); // create new menu run
		MenuItem mStart = new MenuItem("Start Simulation");
		mStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg1) {
				timer.start(); // start simulation

			}
		});
		MenuItem mStop = new MenuItem("Pause Simulation");
		mStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg1) {
				timer.stop(); // pause simulation

			}
		});
		mRun.getItems().addAll(mStart, mStop); // add these to menu item

		menuBar.getMenus().addAll(mFile, mHelp, mConfig, mRun); // set main menu

		return menuBar; // return the menu
	}

	/**
	 * Set up the horizontal box for the bottom with relevant buttons
	 * 
	 * @return HBox with all buttons
	 */
	private HBox setButtons() {

		Button btnCls = new Button("Clear All");
		btnCls.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				arena = new RobotArena(400, 500);
				drawWorld();
				drawStatus();
			}
		});

		Button btnStart = new Button("Start"); // create button for starting
		btnStart.setOnAction(new EventHandler<ActionEvent>() { // now define
																// event when it
																// is pressed
			@Override
			public void handle(ActionEvent event) {
				timer.start(); // its action is to start the timer
			}
		});

		Button btnStop = new Button("Pause"); // now button for stop
		btnStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timer.stop(); // and its action to stop the timer
			}
		});

		Button TravelBot = new Button("TravelBot"); // button to add a robot
		TravelBot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addTravelBot(); // and its action to add
				drawWorld();
				drawStatus();
			}
		});

		Button WhiskerBot = new Button("WhiskerBot"); // button to add a robot
														// w/ whiskers
		WhiskerBot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addWhiskerBot();
				drawWorld();
				drawStatus();
			}
		});

		Button WallBot = new Button("Wall"); // button to add a wall object
		WallBot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addWallBot(); // uncomment when bot made
				drawWorld();
				drawStatus();
			}
		});

		Button LightBot = new Button("Light"); // button to add a light object
		LightBot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addLightBot(); // uncomment when bot made
				drawWorld();
				drawStatus();
			}
		});

		Button LightSensorBot = new Button("LightBot"); // button to add a light
														// sensing robot
		LightSensorBot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addLightSensorBot(); // uncomment when bot made
				drawWorld();
				drawStatus();
			}
		});

		// now add these buttons + labels to a HBox
		return new HBox(new Label("Clear Screen: "), btnCls,
				new Label("Run: "), btnStart, btnStop, new Label("Add: "),
				TravelBot, WhiskerBot, WallBot, LightBot, LightSensorBot);

	}

	/**
	 * Function to convert char c to actual colour used
	 * 
	 * @param c
	 * @return Color
	 */
	protected Color colFromChar(char c) {
		Color ans = Color.BLACK;
		switch (c) {
		case 'y':
			ans = Color.YELLOW;
			break;
		case 'r':
			ans = Color.RED;
			break;
		case 'g':
			ans = Color.GREEN;
			break;
		case 'b':
			ans = Color.BLUE;
			break;
		case 'o':
			ans = Color.ORANGE;
			break;
		}
		return ans;
	}

	/**
	 * Draw the world with robot in it
	 */
	public void drawWorld() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, arena.getXSize(), arena.getYSize());
		arena.drawArena(this);
	}

	/**
	 * Show where robot is, in pane on right with information
	 */
	public void drawStatus() {
		rtPane.getChildren().clear(); // clear rtpane
		ArrayList<String> allRs = arena.describeAll();
		for (String s : allRs) {
			Label l = new Label(s); // turn description into a label
			rtPane.getChildren().add(l); // add label
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("RJF's Robot Simulator");
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10, 20, 10, 20));

		bp.setTop(setMenu()); // put menu at the top

		Group root = new Group(); // create group with canvas
		Canvas canvas = new Canvas(400, 500);
		root.getChildren().add(canvas);
		bp.setLeft(root); // load canvas to left area

		gc = canvas.getGraphicsContext2D(); // context for drawing

		setMouseEvents(canvas); // set up mouse events

		startUp(); // load from file or create new arena if file not found

		timer = new AnimationTimer() { // set up timer
			public void handle(long currentNanoTime) { // and its action when on
				arena.adjustRobots(); // move bots
				drawWorld(); // visualise arena
				drawStatus(); // indicate where robots are in pane on right

			}
		};

		rtPane = new VBox(); // set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT); // set alignment
		rtPane.setPadding(new Insets(5, 50, 50, 5)); // padding
		bp.setRight(rtPane); // add rtPane to borderpane right

		bp.setBottom(setButtons()); // set bottom pane with buttons

		Scene scene = new Scene(bp, 700, 600); // set overall scene
		bp.prefHeightProperty().bind(scene.heightProperty());
		bp.prefWidthProperty().bind(scene.widthProperty());

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args); // launch the GUI

	}

}