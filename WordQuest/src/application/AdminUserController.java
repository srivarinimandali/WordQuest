package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class AdminUserController  implements Initializable {
	
	private Program program;
	private int selectedRow = -1;
	@FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> firstnameColumn;
    @FXML private TableColumn<User, String> lastnameColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, String> passwordColumn;
    @FXML private Button deleteBtn;
    @FXML private TextField firstnameTxtField;
    @FXML private TextField lastnameTxtField;
    @FXML private TextField usernameTxtField;
    @FXML private TextField passwordTxtField;
	
	public AdminUserController() throws IOException {
		this.program = new Program();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

        // Fills in the values with all the words belonging to the level selected in the combo box
		populateTable();
		
		// Makes Cells editable
		userTable.setEditable(true);
        firstnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        usernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public void populateTable() {
		ObservableList<User> users = FXCollections.observableArrayList();
        for (User u: program.getUsers()) {
        	users.add(u);
        }
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
		lastnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		userTable.setItems(users);
	}
	
	public void updateUsers() {
		try {
			program.writeUsers();
		} catch (Exception e) {
			System.out.println("Error writing to json in AdminUser");
			e.printStackTrace();
		}
	}
	
	public void addUser() throws IOException {
		program.getUsers().add(new User(usernameTxtField.getText(), passwordTxtField.getText(), firstnameTxtField.getText(), lastnameTxtField.getText()));
		usernameTxtField.clear();
		passwordTxtField.clear();
		firstnameTxtField.clear();
		lastnameTxtField.clear();
		updateUsers();
		populateTable();
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("User Addition");
	    alert.setHeaderText(null);
	    alert.setContentText("New user added successfully!");
	    alert.showAndWait();
	}
	
	public void deleteUser() {
		if (selectedRow < 0) {
	        return;
	    }

	    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	    confirm.setTitle("Confirm Deletion");
	    confirm.setHeaderText("Are you sure you want to delete this user?");
	    confirm.setContentText("This action cannot be undone.");

	    Optional<ButtonType> result = confirm.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        program.getUsers().remove(selectedRow);
	        populateTable();
	        updateUsers();
	        selectedRow = -1;

	        Alert info = new Alert(Alert.AlertType.INFORMATION);
	        info.setTitle("Deletion Successful");
	        info.setHeaderText(null);
	        info.setContentText("User has been deleted.");
	        info.showAndWait();
	    }
	}
	
	public void selectRow() {
		selectedRow = userTable.getSelectionModel().getSelectedIndex();
	}
	
	public void changeWordCellEvent() {
		
	}
	
	public void changeFirstnameCellEvent(CellEditEvent<User, String> edittedCell) {
		 int ind = edittedCell.getTablePosition().getRow();
		    program.getUsers().get(ind).setFirstname(edittedCell.getNewValue().toString());
		    populateTable();
		    updateUsers();

		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Update Successful");
		    alert.setHeaderText(null);
		    alert.setContentText("User's first name updated successfully.");
		    alert.showAndWait();
	}
	
	public void changeLastnameCellEvent(CellEditEvent<User, String> edittedCell) {
		int ind =  edittedCell.getTablePosition().getRow();
        program.getUsers().get(ind).setLastname(edittedCell.getNewValue().toString());
        populateTable();
        updateUsers();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Update Successful");
	    alert.setHeaderText(null);
	    alert.setContentText("User's Last name updated successfully.");
	    alert.showAndWait();
	}
	
	public void changeUsernameCellEvent(CellEditEvent<User, String> edittedCell) {
		int ind =  edittedCell.getTablePosition().getRow();
        program.getUsers().get(ind).setUsername(edittedCell.getNewValue().toString());
        populateTable();
        updateUsers();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Update Successful");
	    alert.setHeaderText(null);
	    alert.setContentText("Username updated successfully.");
	    alert.showAndWait();
	}
	
	public void changePasswordCellEvent(CellEditEvent<User, String> edittedCell) {
		int ind =  edittedCell.getTablePosition().getRow();
        program.getUsers().get(ind).setPassword(edittedCell.getNewValue().toString());
        populateTable();
        updateUsers();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Update Successful");
	    alert.setHeaderText(null);
	    alert.setContentText("Password updated successfully.");
	    alert.showAndWait();
	}
	
	public void back(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();  
	}
	
	public void logoout(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Logout Confirmation");
	    alert.setHeaderText("Confirm Logout");
	    alert.setContentText("Are you sure you want to logout?");

	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
	        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    }
	}
}
