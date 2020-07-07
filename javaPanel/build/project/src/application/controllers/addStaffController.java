package application.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.activation.MimetypesFileTypeMap;

import org.json.JSONObject;

import application.models.ControllerClass;
import application.models.Post;
import application.models.SceneChanger;
import application.models.Staff;
import application.models.StaffJsonHandler;
import application.models.apiReq;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
    boolean isUpdating = false;
    @Override
   	public void preloadData(Staff staff) {
   		this.preloadData(staff, new Staff("new user", "email", -404, 1, new File("./src/defaultImage.png"),
   				"www.facebook.com", "www.linkedin.com", "www.google.com", "www.twitter.com"));
   	}

   	@Override
   	public void preloadData(Staff staff, Post post) {
   		
   	}
   	@Override
   	public void preloadData(Staff staff, Staff toEdit) {
   		staffImage.setOpacity(100.0);
		staffImage.setStroke(Color.SEAGREEN); 
    	staffImage.setFill(Color.SNOW);
    	staffImage.setEffect(new DropShadow(+50d, 0d, +5d, Color.DARKSEAGREEN));
   		
   		this.staff = staff;
   		//staff.setId(0);
   		staffimg = toEdit.getImage();
   		//staffimg = new File("./src/defaultImage.png");
   		if (staff.getEmail().equals(toEdit.getEmail()))
   			isUpdating = true;
   		if (staff.getLevel() == 0 || staff.getId() == toEdit.getId())
   		{
   			
   			
   			firstName.setText(toEdit.getName());
   			email.setText(toEdit.getEmail());
   			confirmEmail.setText(toEdit.getEmail());
   			facebook.setText(toEdit.getFacebookLink());
   			twitter.setText(toEdit.getTwitterLink());
   			linkedIn.setText(toEdit.getLinkinLink());
   			google.setText(toEdit.getgPlusLink());
   			
   			setStaffImg(toEdit.getImage());
   			if (isUpdating)
   				isAdmin.setDisable(true);
   			save.setOnAction(event->{
   				save.setDisable(true);
   				if((email.getText().length() != 0 && confirmEmail.getText().length() != 0))
   				{
   					if(!email.getText().equals(confirmEmail.getText()))
   					{
   						showError("email do not match!", "Error");
   						save.setDisable(false);
   						return;
   					}
   				}else
   				{
   					showError("you can't set an empty email!", "Error");
   					save.setDisable(false);
   					return;
   				}
   				if((PassField.getText().length() >= 8 && confirmPass.getText().length() >= 8))
   				{
   					if(!PassField.getText().equals(confirmPass.getText()))
   					{
   						showError("password do not match!", "Error");
   						save.setDisable(false);
   	   					return;
   					}
   				}else
   				{
   					showError("password must contain at least 8 Characters or numbers!", "Error");
   					save.setDisable(false);
   					return;
   				}
   				if(firstName.getText().length() == 0 )
   				{
   					showError("you must add your name!", "Error !");
   					save.setDisable(false);
   					return ;
   				}
   				int level = isAdmin.isSelected() ? 0:1;
   			Staff stf = new Staff(firstName.getText() , email.getText()
   						, toEdit.getId(), 
   						level, staffimg, null, facebook.getText()
   						, linkedIn.getText(),
   						google.getText(), twitter.getText());
   			ArrayList<Staff> staffs = new ArrayList<>();
   			stf.setPassword(PassField.getText());
   			staffs.add(stf);
   				StaffJsonHandler<Staff > stj = new StaffJsonHandler<>();
   				String request =stj.build(staffs);
   				apiReq api = new apiReq(Staff.APILINK + "/user/adduser");
   				staff.getToken().addAuthorization(api);
   					
   				api.addPostParam("",request, true);
   				String responce = api.send("POST",false);
   				JSONObject resJson = new JSONObject(responce);
   				if (resJson.has("error"))
   				{
   					showError("there was an error try later!", "Error !");
   					save.setDisable(false);
   					return;
   				}
   			
   				if (isUpdating)
   				{
   					showError("profile updated  !", "Message <0_0>");
   					save.setDisable(false);
   					SceneChanger sn = new SceneChanger();
   					try {
   						sn.changeScenes(event, "/application/view/login.fxml", "login");
   					} catch (Exception e1) {
   						e1.printStackTrace();
   					}
   				}else
   				{
   					showError("staff added !", "Message <0_0>");
   					returntoDashboard();
   				
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
   		
   				File uploadImg = new File("./src/uploadimage.png");
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
   							"Error");
   			});
   		}else
   		{
   			SceneChanger.showPopUP("you are not authorized to this page !",
   					"Error !");
   			returntoDashboard();
   			
   		
   	
   		}
   	}

	private void setStaffImg(File staffimg2) {
		String mimetype= new MimetypesFileTypeMap().getContentType(staffimg2);
        String type = mimetype.split("/")[0];
        if(type.equals("application") || type.equals("image")){
		try {
			Image image = new Image(new FileInputStream(staffimg2));

			staffImage.setFill(new ImagePattern(image));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
        }
        else {
        	Image image;
			try {
				image = new Image(new FileInputStream(new File("./src/defaultImage.png")));
				this.staffImage.setFill(new ImagePattern(image));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public void showError(String str , String type) {
		SceneChanger.showPopUP(str,type);
			
		
			
	}
	
	
}

