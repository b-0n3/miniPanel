package application.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.models.ControllerClass;
import application.models.Post;
import application.models.SceneChanger;
import application.models.Staff;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class addStaffController implements ControllerClass{

   
	@FXML
    private TextField firstName;

    @FXML
    private TextField LastName;

    @FXML
    private TextField email;

    @FXML
    private TextField confirmEmail;

    @FXML
    private TextField facebook;

    @FXML
    private TextField linkedIn;

    @FXML
    private TextField google;

    @FXML
    private TextField twitter;

    @FXML
    private Circle staffImage;

    @FXML
    private PasswordField PassField;

    @FXML
    private PasswordField confirmPass;

    @FXML
    private CheckBox isAdmin;

    @FXML
    private Button Cancel;

    @FXML
    private Button save;
    private File staffimg;
    private Staff staff;
    @Override
   	public void preloadData(Staff staff) {
   		this.preloadData(staff, new Staff("new user", "email", -404, 1, new File("defaultImage.png"),
   				"www.facebook.com", "www.linkedin.com", "www.google.com", "www.twitter.com"));
   	}

   	@Override
   	public void preloadData(Staff staff, Post post) {
   		
   	}
   	@Override
   	public void preloadData(Staff staff, Staff toEdit) {
   		this.staff = staff;
   		staffimg = toEdit.getImage();
   		if (staff.getId() == 0 || staff.getId() == toEdit.getId())
   		{
   			save.setOnAction(event->{
   				if((email.getText().length() != 0 && confirmEmail.getText().length() != 0))
   				{
   					if(!email.getText().equals(confirmEmail.getText()))
   					{
   						showError("email do not match!", event);
   	   					return;
   					}
   				}else
   				{
   					showError("you can't set an empty email!", event);
   					return;
   				}
   				if((PassField.getText().length() >= 8 && confirmPass.getText().length() >= 8))
   				{
   					if(!PassField.getText().equals(confirmPass.getText()))
   					{
   						showError("password do not match!", event);
   	   					return;
   					}
   				}else
   				{
   					showError("password must contain at least 8 Characters or numbers!", event);
   					return;
   				}
   				
   				
   				});
   			Cancel.setOnAction(event->{
   				returntoDashboard();
   			});
   			
   			if(toEdit.getLevel() == 0)
   				isAdmin.setSelected(true);
   			else
   				isAdmin.setSelected(false);
   			staffImage.setOnMouseEntered(event->{
   				File uploadImg = new File("uploadimage.png");
   				setStaffImg(uploadImg);
   			});
   			
   			staffImage.setOnMouseExited(event->{
   				setStaffImg(staffimg);
   			});
   			
   			staffImage.setOnMouseClicked(event->{
   				File selectedImage = chooseImage(event);
   				if (selectedImage != null)
   				{
   					staffimg = selectedImage;
   					setStaffImg(staffimg);
   				}else
   				showError("you must select an image",
   							event);
   			});
   		}else
   		{
   			SceneChanger.showPopUP("you are not authorized to this page !",
   					(Stage)this.Cancel.getScene().getWindow());
   			returntoDashboard();
   			
   		
   	
   		}
   	}

	private void setStaffImg(File staffimg2) {
		try {
			Image image = new Image(new FileInputStream(staffimg2));

			staffImage.setFill(new ImagePattern(image));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}

	private void returntoDashboard() {
		ControllerClass db = new dashboardController();
		SceneChanger sn = new SceneChanger();
		try {
			sn.changeScenes((Stage) this.Cancel.getScene().getWindow(), "/application/view/dashboard.fxml", "Dashboard", staff, db);
		} catch (IOException e1) {
			// TODO Auto-generated catch 
			e1.printStackTrace();
		}
	}
	private File chooseImage(Event event) {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("choose image");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		ExtensionFilter ex = new ExtensionFilter("*.png", "*.png");
		ExtensionFilter ex2 = new ExtensionFilter("*.jpeg", "*.jpeg");
		ExtensionFilter ex3 = new ExtensionFilter("*.jpg", "*.jpg");
		ExtensionFilter ex4 = new ExtensionFilter("*.gif", "*.gif");
		fileChooser.getExtensionFilters().addAll(ex,ex2,ex3,ex4);
	
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		File selectedFile = fileChooser.showOpenDialog(stage);
		
		return selectedFile;
	}
	public void showError(String str , Event event) {
		SceneChanger.showPopUP(str,(Stage) ((Parent)((Node)event.getSource()).getParent()).getScene().getWindow());
			return;
	}
	
	
}
