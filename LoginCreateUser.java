import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The login window for an existing user
 * @author George Manning
 * @version 1.0
 */
public class LoginCreateUser extends Application {
	
	private boolean newUser;
	private Stage stage;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	
	/**
	 * The constructor for the LoginCreateUser UI.
	 * @param stage The stage this application will be shown on.
	 * @param newUser True is creating a new user, false if logging in as 
	 * existing user.
	 */
	public LoginCreateUser(Stage stage, Boolean newUser) {
		this.newUser = newUser;
		this.stage = stage;
		try {
			start(stage);
		} catch(IOException e) {
			System.out.println("Controller not found");
			System.out.println(e);
		}
	}
	/**
	 * The start method for this application.
	 */
	public void start(Stage primaryStage) throws IOException{
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("usernamemenu.fxml"));
		Pane root = (Pane) loader.load();
		UsernameMenuController controller = (UsernameMenuController) loader.getController();
		controller.setStage(stage, newUser);
		
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
