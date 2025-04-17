package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SceneController implements Initializable {
	
	private int userid;
	private Program program;
	
	@FXML private TextField usernameTxtField;
	@FXML private TextField passwordTxtField;
	@FXML private Label errorMsg;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			this.program = new Program();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void login(ActionEvent event) throws IOException {
		// Assume successful login by default
	    boolean loginSuccessful = false;
		if (usernameTxtField.getText().equals("admin") && passwordTxtField.getText().equals("admin")) {
			adminLogin(event);
	        loginSuccessful = true; // Admin login successful

		}else {
	        for (int i = 0; i < program.getUsers().size(); i++) {
	            User u = program.getUsers().get(i);
	            if (usernameTxtField.getText().equals(u.getUsername()) && passwordTxtField.getText().equals(u.getPassword())) {
	                userid = i;
	                u.updateWords();
	                program.writeUsers();
	                userLogin(event);
	                loginSuccessful = true; // User login successful
	                break;
	            }
	        }
	    }

	    if (!loginSuccessful) {
	        // Set error message if login fails
	    	showAlert();
	    }
	}
	private void showAlert() {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Login Failed");
	    alert.setHeaderText(null); // No header text
	    alert.setContentText("Invalid username or password. Please try again.");

	    alert.showAndWait(); // Display the alert and wait for the user to close it
	}
	public void userLogin(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
		Parent root = loader.load();
		UserController controller = loader.getController();
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		controller.setUserId(userid);
		stage.show();
	}
	
	public void adminLogin(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();  
	}
	
	public void signUp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();  
	}
}
