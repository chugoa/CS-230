//TODO: REMOVING DOORS IS KINDA NECESSARY AND ISNT PROPERLY IMPLENTED SORT OUT WHEN PROPERLY ASSEMBLING
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Generate the UI for one level. Contains all functionality to allow a user to play a 
 * Level on it.
 *
 * @author George Manning, Cameron Altenkirch.
 * @version 3.0
 */
public class LevelUI extends Application {
	
	
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	
	// The size of each cell
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;
	
	//clip values
	private static final int CLAMP_RANGE_MIN = 0;
	
	
	private static final int TOOLBAR_HEIGHT = 25;
	private static final int TOOLBAR_CANVAS_WIDTH = 350;
	private static final int TOOLBAR_CELL_WIDTH = 25;

	private Level level;
	private UserProfile user;
	private Stage stage;
	private Stopwatch stopwatch;
	
	//invisible rectangle acts as a camera, focusing on the player -- used for scrolling
	private Rectangle camera;
	
	// The canvas in the GUI. This needs to be a global variable
	private Canvas canvas;
	private Canvas itemCanvas;
	
	/*
	public LevelUI(Stage stage, Level level, UserProfile user) {
		this.stage = stage;
		this.user = user;
		this.level = level;
		start(stage);
	}
	*/
	
	/**
	 * The start method for this application
	 */
	public void start(Stage primaryStage) {
		
		
		this.stage= primaryStage;
		
		//Creates a board -- There is probably a better place to put this
		Cell[][] grid = new Cell[10][10];
		
		//X = 0 first column --left hand side
		grid[0][0] = new Cell(CellType.WALL);
		grid[1][0] = new Cell(CellType.WALL);
		grid[2][0] = new Cell(CellType.WALL);
		grid[3][0] = new Cell(CellType.WALL);
		grid[4][0] = new Cell(CellType.WALL);
		grid[5][0] = new Cell(CellType.WALL);
		grid[6][0] = new Cell(CellType.WALL);
		grid[7][0] = new Cell(CellType.WALL);
		grid[8][0] = new Cell(CellType.WALL);
		grid[9][0] = new Cell(CellType.WALL);
		
		//X = 1 second column
		grid[0][1] = new Cell(CellType.WALL);
		grid[1][1] = new Cell(CellType.GROUND);
		grid[2][1] = new Cell(CellType.GROUND);
		grid[3][1] = new Cell(CellType.GROUND);
		grid[4][1] = new Cell(CellType.GROUND);
		grid[5][1] = new Cell(CellType.GROUND);
		grid[6][1] = new Cell(CellType.GROUND);
		grid[7][1] = new Cell(CellType.GROUND);
		grid[8][1] = new Cell(CellType.GROUND);
		grid[9][1] = new Cell(CellType.WALL);
		
		//X = 2 third column
		grid[0][2] = new Cell(CellType.WALL);
		grid[1][2] = new Cell(CellType.GROUND);
		grid[2][2] = new Cell(CellType.GROUND);
		grid[3][2] = new Cell(CellType.GROUND);
		grid[4][2] = new Cell(CellType.GROUND);
		grid[5][2] = new Cell(CellType.GROUND);
		grid[6][2] = new Cell(CellType.GROUND);
		grid[7][2] = new Cell(CellType.GROUND);
		grid[8][2] = new Cell(CellType.GROUND);
		grid[9][2] = new Cell(CellType.WALL);
		
		//X = 3 fourth column
		grid[0][3] = new Cell(CellType.WALL);
		grid[1][3] = new Cell(CellType.GROUND);
		grid[2][3] = new Cell(CellType.GROUND);
		grid[3][3] = new Cell(CellType.TOKEN);
		grid[4][3] = new Cell(CellType.GROUND);
		grid[5][3] = new Cell(CellType.GROUND);
		grid[6][3] = new Cell(CellType.GROUND);
		grid[7][3] = new Cell(CellType.GROUND);
		grid[8][3] = new Cell(CellType.GROUND);
		grid[9][3] = new Cell(CellType.WALL);
		
		//X= 4 fifth column
		grid[0][4] = new Cell(CellType.WALL);
		grid[1][4] = new Cell(CellType.GROUND);
		grid[2][4] = new Cell(CellType.GROUND);
		grid[3][4] = new Cell(CellType.GROUND);
		grid[4][4] = new Teleporter(7,5);
		grid[5][4] = new Cell(CellType.GROUND);
		grid[6][4] = new Cell(CellType.GROUND);
		grid[7][4] = new Cell(CellType.GROUND);
		grid[8][4] = new Cell(CellType.GROUND);
		grid[9][4] = new Cell(CellType.WALL);
		
		//X = 5 sixth column
		grid[0][5] = new Cell(CellType.WALL);
		grid[1][5] = new Cell(CellType.GROUND);
		grid[2][5] = new Cell(CellType.GROUND);
		grid[3][5] = new Cell(CellType.GROUND);
		grid[4][5] = new Cell(CellType.GROUND);
		grid[5][5] = new Cell(CellType.RED_DOOR);
		grid[6][5] = new Cell(CellType.GROUND);
		grid[7][5] = new Cell(CellType.GROUND);
		grid[8][5] = new Cell(CellType.GROUND);
		grid[9][5] = new Cell(CellType.WALL);
		
		//X = 6 seventh column
		grid[0][6] = new Cell(CellType.WALL);
		grid[1][6] = new Cell(CellType.GROUND);
		grid[2][6] = new Cell(CellType.GROUND);
		grid[3][6] = new Cell(CellType.GROUND);
		grid[4][6] = new Cell(CellType.GROUND);
		grid[5][6] = new Cell(CellType.RED_KEY);
		grid[6][6] = new Cell(CellType.GROUND);
		grid[7][6] = new Cell(CellType.GROUND);
		grid[8][6] = new Cell(CellType.GROUND);
		grid[9][6] = new Cell(CellType.WALL);
		
		//x = 7 eigth column
		grid[0][7] = new Cell(CellType.WALL);
		grid[1][7] = new Cell(CellType.GROUND);
		grid[2][7] = new Cell(CellType.GROUND);
		grid[3][7] = new Cell(CellType.GROUND);
		grid[4][7] = new Cell(CellType.GROUND);
		grid[5][7] = new Teleporter(4,4);
		grid[6][7] = new Cell(CellType.GROUND);
		grid[7][7] = new Cell(CellType.GROUND);
		grid[8][7] = new Cell(CellType.GROUND);
		grid[9][7] = new Cell(CellType.WALL);
		
		//x = 8 ninth column
		grid[0][8] = new Cell(CellType.WALL);
		grid[1][8] = new Cell(CellType.GROUND);
		grid[2][8] = new Cell(CellType.GROUND);
		grid[3][8] = new Cell(CellType.GROUND);
		grid[4][8] = new Cell(CellType.GROUND);
		grid[5][8] = new Cell(CellType.GROUND);
		grid[6][8] = new Cell(CellType.GROUND);
		grid[7][8] = new Cell(CellType.GROUND);
		grid[8][8] = new Cell(CellType.GOAL);
		grid[9][8] = new Cell(CellType.WALL);
		
		grid[0][9] = new Cell(CellType.WALL);
		grid[1][9] = new Cell(CellType.WALL);
		grid[2][9] = new Cell(CellType.WALL);
		grid[3][9] = new Cell(CellType.WALL);
		grid[4][9] = new Cell(CellType.WALL);
		grid[5][9] = new Cell(CellType.WALL);
		grid[6][9] = new Cell(CellType.WALL);
		grid[7][9] = new Cell(CellType.WALL);
		grid[8][9] = new Cell(CellType.WALL);
		grid[9][9] = new Cell(CellType.WALL);
		
		Enemy[] enemies = new Enemy[0];
		Player player = new Player (2,2,new Image("placeholder.png"));
		Board board = new Board(grid,10,10,8,8);
		this.level = new Level(board, player, enemies);
		
		
		// Build the GUI 
		BorderPane root = buildGUI();
		BorderPane canvasClip = new BorderPane();
		
		canvasClip.setMinSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		canvasClip.setMaxSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		canvasClip.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		Pane gameWindow = buildCanvas();
	
		//panes within panes.
		root.setCenter(gameWindow);
		//canvasClip.setCenter(gameWindow);
		this.stopwatch = new Stopwatch();
		this.stopwatch.startStopwatch();
		// Create a scene from the GUI
		Scene scene = new Scene(new BorderPane(root), WINDOW_HEIGHT, WINDOW_WIDTH);
		
		//Set up the camera to allow for a
		camera = new Rectangle();
		camera.setX(level.getPlayer().getXCoord()*GRID_CELL_WIDTH);
		camera.setY((level.getPlayer().getYCoord())*GRID_CELL_HEIGHT);
		gameWindow.getChildren().add(camera);
		//Creating a clip
		
        createClip(scene, gameWindow);
        
        drawGame();


        

		// Load images
		//player = p1.getImage();
		
		// Register an event handler for key presses
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event, root));

		
		// Display the scene on the stage
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	//Might move into level class -- have done on local copy need to redo when download master
	/**
	 * Process a key event due to a key being pressed, e.g., to the player.
	 * @param event The key event that was pressed.
	 */
	public void processKeyEvent(KeyEvent event, Pane root) {
		
		level.play(event);
		
		//re clamps the camera to the player.
    	camera.setX(clampRange(level.getPlayer().getXCoord()*GRID_CELL_WIDTH, CLAMP_RANGE_MIN,
    			root.getWidth() - GRID_CELL_WIDTH));
    	camera.setY(clampRange((level.getPlayer().getYCoord())*GRID_CELL_HEIGHT, CLAMP_RANGE_MIN,  
    			root.getWidth() - GRID_CELL_HEIGHT));
		
		drawGame();
		drawItemCanvas();
		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
		event.consume();
		
		if (level.hasPlayerWon()) {
			this.stopwatch.stopStopwatch();
			win();
		}
		if (level.isPlayerDead()) {
			this.stopwatch.stopStopwatch();
			gameOver();
		}
	}
	
	/**
	 * Draw the game on the canvas.
	 */
	public void drawGame() {
		// Get the Graphic Context of the canvas. This is what we draw on.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		// Clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Draw row of dirt images
		// We multiply by the cell width and height to turn a coordinate in our grid into a pixel coordinate.
		// We draw the row at y value 2.
		for (int y = 0; y < level.getBoard().getSizeY(); y++) {
			for (int x = 0; x < level.getBoard().getSizeX(); x++) {
				gc.drawImage(level.getBoard().getCell(x, y).getImage(), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);	
			}

		}
		
		
		// Draw player at current location
		gc.drawImage(level.getPlayer().getImage(), level.getPlayer().getXCoord() * GRID_CELL_WIDTH, 
						level.getPlayer().getYCoord() * GRID_CELL_HEIGHT);	
		
		for (Enemy elem: level.getEnemies()) {
			gc.drawImage(elem.getImage(), elem.getXCoord() * GRID_CELL_WIDTH, elem.getYCoord() * GRID_CELL_HEIGHT);
		}
		
	}
	
	
	/**
	 * Create the GUI.
	 * @return The panel that contains the created GUI.
	 */
	private BorderPane buildGUI() {
		BorderPane root = new BorderPane();
		
		// Create a toolbar with some nice padding and spacing
		HBox toolbar = new HBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10, 10, 10, 10)); 
		root.setTop(toolbar);
		buildToolBar(toolbar);
		
		return root;
	}
	
	private void buildToolBar(HBox toolbar) {
		// Create toolbar content
		Button backToLevelSelectButton = new Button("<-");
		toolbar.getChildren().add(backToLevelSelectButton);

		Button saveButton = new Button("SaveState");
		toolbar.getChildren().add(saveButton);
		
		// Add button event handlers
		backToLevelSelectButton.setOnAction(e -> {
			new LevelSelect(stage, user);
		});
		
		saveButton.setOnAction(e -> {
			FileHandling.saveProgress(level, user);
			new LevelSelect(stage, user);
		});
		
		itemCanvas = new Canvas(350, TOOLBAR_HEIGHT);
		toolbar.getChildren().add(itemCanvas);
		drawItemCanvas();
		
	}

	
	/**
	 * Builds The canvas that the game will be played on.
	 * @return The pane the canvas is in.
	 */
	private Pane buildCanvas() {
		
		canvas = new Canvas(level.getBoard().getSizeX()*GRID_CELL_WIDTH,
						level.getBoard().getSizeY()*GRID_CELL_HEIGHT);
		Pane gameWindow = new Pane(canvas);
		gameWindow.setMaxSize(canvas.getWidth(),canvas.getHeight());
		gameWindow.setPrefSize(canvas.getWidth(),canvas.getHeight());
		gameWindow.setMinSize(canvas.getWidth(),canvas.getHeight());
		
		return gameWindow;
	}
	
	/**
	 * Creates the clip that allows only part of the canvas to be displayed at any time. C
	 * @param scene The scene the UI is on.
	 * @param gameWindow The pane containing the canvas.
	 */
	private void createClip(Scene scene, Pane gameWindow) {
		final int BIND_MULT = -1;
		final int SCENE_DIVIDE = 2;
		
		Rectangle clip = new Rectangle();
		clip.widthProperty().bind(scene.widthProperty());
	    clip.heightProperty().bind(scene.heightProperty());
	    
	    clip.xProperty().bind(Bindings.createDoubleBinding(
	    		() -> clampRange(camera.getX() - scene.getWidth() / SCENE_DIVIDE, CLAMP_RANGE_MIN, 
	    				gameWindow.getWidth() - scene.getWidth()), camera.xProperty(), scene.widthProperty()));
	    clip.yProperty().bind(Bindings.createDoubleBinding(
	    		() -> clampRange(camera.getY() - (scene.getHeight() - GRID_CELL_HEIGHT) / SCENE_DIVIDE,
	    				CLAMP_RANGE_MIN, gameWindow.getHeight() - (scene.getHeight() - GRID_CELL_HEIGHT)),
	    				camera.yProperty(), scene.heightProperty().subtract(GRID_CELL_HEIGHT)));

	    gameWindow.setClip(clip);
	    gameWindow.translateXProperty().bind(clip.xProperty().multiply(BIND_MULT));
	    gameWindow.translateYProperty().bind(clip.yProperty().multiply(BIND_MULT));
	}
	
	private void drawItemCanvas() {
		final int TEXT_OFFSET = 5;
		
		GraphicsContext gc = itemCanvas.getGraphicsContext2D();
		
		gc.clearRect(0, 0, itemCanvas.getWidth(), itemCanvas.getHeight());
		
		//displays num of tokens
		gc.drawImage(new Image("token.png", TOOLBAR_CELL_WIDTH, TOOLBAR_HEIGHT,false,false), 0, 0);
		gc.strokeText(Integer.toString(level.getPlayer().getTokenNum()), TOOLBAR_CELL_WIDTH - TEXT_OFFSET, TOOLBAR_HEIGHT);
		//need to add implemetation for the rest
	}
	/** 
	 * Method to stop camera from going out of bounds.
	 * @param value The current x or y location of the camera.
	 * @param min The minimum edge.
	 * @param max The maximun edge.
	 * @return 
	 */
	private double clampRange(double value, double min, double max) {
		if (value < min) return min ;
		if (value > max) return max ;
	    return value ;
	}
	
	private void win() {
		
		FileHandling.completeLevel(this.user, this.level.getLevelNum(),
									this.stopwatch.calculateTime());
		
		BorderPane newWindow = new BorderPane();
		VBox messageBox = new VBox();
		Label winMess = new Label("Level Complete!");
		Label timeMess = new Label("Time: " + this.stopwatch.calculateTime());
		
		messageBox.getChildren().addAll(winMess, timeMess);
		
		Button nextLevelButton = new Button("Next Level!");
		nextLevelButton.setOnAction(e -> {
			loadLevel(level.getLevelNum() + 1);
		});
		

		newWindow.setTop(messageBox);
		newWindow.setCenter(nextLevelButton);
		
		//get rid of the numbers
        Scene secondScene = new Scene(newWindow, 230, 100);

        // New window (Stage)
        Stage newStage = new Stage();
        newStage.setTitle("Congratulations!");
        newStage.setScene(secondScene);

        // Set position of second window, related to primary window.
        newStage.setX(stage.getX() + 200);
        newStage.setY(stage.getY() + 200);

        newStage.show();
	}
	
	private void gameOver() {
		BorderPane newWindow = new BorderPane();
		
		Label winMess = new Label("You Died");
		
		Button resetLevelButton = new Button("Next Level!");
		resetLevelButton.setOnAction(e -> {
			loadLevel(level.getLevelNum());
		});
		

		newWindow.setTop(winMess);
		newWindow.setCenter(resetLevelButton);
		
		//get rid of the numbers
        Scene secondScene = new Scene(newWindow, 230, 100);

        // New window (Stage)
        Stage newStage = new Stage();
        newStage.setTitle(":)");
        newStage.setScene(secondScene);

        // Set position of second window, related to primary window.
        newStage.setX(stage.getX() + 200);
        newStage.setY(stage.getY() + 200);

        newStage.show();
	}
	
	private void loadLevel(int levelNum) {
		this.level = FileHandling.loadLevel(levelNum);
	}

	
	public static void main(String[] args) {
		launch(args);
		
	}
}