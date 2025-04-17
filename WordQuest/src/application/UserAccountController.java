package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class UserAccountController implements Initializable {
	
	private User user;
	private int userid;
	private Program program;
	@FXML private ComboBox<String> levelComboBox;
	@FXML private TextField firstnameTxtField;
	@FXML private TextField lastnameTxtField;
	@FXML private TextField usernameTxtField;
	@FXML private TextField passwordTxtField;
	@FXML private PieChart pieChart;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
        	this.program = new Program();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        levelComboBox.getItems().addAll("Easy", "Medium", "Hard");
        levelComboBox.setValue("Easy");
        
	}
	
	public void setUserId(int userid) {
		this.userid = userid;
		this.user = program.getUsers().get(userid);
		user.setLevel(levelComboBox.getValue().toLowerCase());
		updateUsers();
		cancelBtn();
		fillPieChart();
	}
	
	public void fillPieChart() {
		int n=0, l=0, m=0;
		for (Word w : user.getWords().get(user.getLevel())) {
			if (w.getProficiency()==0) {
				n++;
			} else if (w.getProficiency()==1) {
				l++;
			} else {
				m++;
			}
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
				   new PieChart.Data("New", n), 
				   new PieChart.Data("Learning", l), 
				   new PieChart.Data("Mastered", m));
	    pieChart.setTitle("Learning Curve"); 
 		pieChart.setData(pieChartData);
	}
	
	public void updateUsers() {
		try {
			program.writeUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void comboBoxChanged() {
		user.setLevel(levelComboBox.getValue().toLowerCase());
		updateUsers();
		fillPieChart();
	}
	
	public void saveBtn() {
		user.setFirstname(firstnameTxtField.getText());
		user.setLastname(lastnameTxtField.getText());
		user.setUsername(usernameTxtField.getText());
		user.setPassword(passwordTxtField.getText());
		updateUsers();
        showAlert("Success", "Profile Updated", "Your profile has been updated successfully.", Alert.AlertType.INFORMATION);

	}
	
	public void cancelBtn() {
		firstnameTxtField.setText(user.getFirstname());
		lastnameTxtField.setText(user.getLastname());
		usernameTxtField.setText(user.getUsername());
		passwordTxtField.setText(user.getPassword());
	}
	
	public void resetBtn() {
		List<Word> t = new ArrayList<>();
		t.addAll(program.getWords().get(user.getLevel()));
		user.getWords().put(user.getLevel(), t);
		updateUsers();
        showAlert("Reset Completed", null, "Your learning words have been reset.", Alert.AlertType.INFORMATION);

	}
	
	public void deleteBtn(ActionEvent event) throws IOException {
		if (confirmAction("Delete Account", "Are you sure you want to delete your account? This cannot be undone.")) {
            program.getUsers().remove(userid);
            updateUsers();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
        }
	}
	
	public void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
		Parent root = loader.load();
		UserController controller = loader.getController();
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		controller.setUserId(userid);
		stage.show();
	}
	
	public void logoout(ActionEvent event) throws IOException {
		if (confirmAction("Logout", "Are you sure you want to logout? Unsaved changes will be lost.")) {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
        }
}
	private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean confirmAction(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}