package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SignUpController implements Initializable {
	
	private Program program;
	@FXML private TextField firstnameTxtField;
	@FXML private TextField lastnameTxtField;
	@FXML private TextField usernameTxtField;
	@FXML private TextField passwordTxtField;
	@FXML private TextField confirmTxtField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
        	this.program = new Program();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	public boolean checkFields() {
		
		// Basic field check: no empty fields and passwords must match and meet criteria
        if (firstnameTxtField.getText().isEmpty() || lastnameTxtField.getText().isEmpty() ||
            usernameTxtField.getText().isEmpty() || passwordTxtField.getText().isEmpty() ||
            !passwordTxtField.getText().equals(confirmTxtField.getText())) {
            return false;
        }
        // Add more password criteria checks here if necessary
        return true;
	}
	
	public void updateUsers() {
		try {
			program.writeUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createBtn(ActionEvent event) throws IOException {
	 if (!checkFields()) {
		    // If fields are not valid, show an error message and return
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setContentText("Please fill all fields correctly. Ensure the password meets the criteria.");
		    alert.showAndWait();
		    return;
		}
		
		for (User u : program.getUsers()) {
		    if (usernameTxtField.getText().equals(u.getUsername())) {
		        // Username exists
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setContentText("Username already exists!");
		        alert.showAndWait();
		        return;
			    }
			}
		
		// If all checks are passed, add the user and proceed
		program.getUsers().add(new User(usernameTxtField.getText(), passwordTxtField.getText(), firstnameTxtField.getText(), lastnameTxtField.getText()));
		updateUsers();
				Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show(); 
			}
	
	public void backBtn(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show(); 
	}
}
