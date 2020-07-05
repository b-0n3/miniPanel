package application.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.border.TitledBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import application.models.Categery;
import application.models.ControllerClass;
import application.models.Crypto;
import application.models.Post;
import application.models.PostJsonHandler;
import application.models.SceneChanger;
import application.models.Staff;
import application.models.apiReq;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class editorController implements ControllerClass {
    @FXML
    private Button editText;

    @FXML
    private Button editHtml;

    @FXML
    private TextField Postname;

    @FXML
    private Button TSave;

    @FXML
    private Button Tcancel;

//    @FXML
//    private ScrollPane ScrPane;

    @FXML
    private TextArea htmlField;

    @FXML
    private HTMLEditor HtmlEditor;

    @FXML
    private TextField tagsFiled;

    @FXML
    private ComboBox<String> TagsBox;

    @FXML
    private Button addTag;

    @FXML
    private ComboBox<String> CategeryBox;

    @FXML
    private TextField CategeryName;

    @FXML
    private Button SaveCategery;

    @FXML
    private Button AddLocalImage;

    @FXML
    private TextField Link;

    @FXML
    private Button addWebImage;
    @FXML
    private Circle postImg;

    @FXML
    private Text errorText;
    
    private Hashtable<String, Integer> Cat = new Hashtable<>();
    private Staff staff;
    private boolean imageChanged;
    private File postImage;
    @FXML
    public void initialize() 
    {
    
    }
    @Override
	public void preloadData(Staff staff) {
    	this.staff = staff;
    	
    	this.preloadData(this.staff,new Post("new post","<b> hello world</b>",-404,null,null,this.staff.getId(),null));;
    }
    public void setPostImg(File img)
    {
    	try {
			Image image = new Image(new FileInputStream(img));

			postImg.setFill(new ImagePattern(image));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
    }

	@Override
	public void preloadData(Staff staff, Post post) {
		this.staff = staff;
		imageChanged = false;
		if(post.getImage() != null)
			postImage = post.getImage();
		else
		{
			postImage = new File("./src/defaultImage.png");
			imageChanged = true;
		}
		
		setPostImg(postImage);
		
		postImg.setOnMouseEntered(event->{
			errorText.setText("");
			File uploadImg = new File("./src/uploadimage.png");
			setPostImg(uploadImg);
		});
		
		postImg.setOnMouseExited(event->{
			errorText.setText("");
			setPostImg(postImage);
		});
		
		postImg.setOnMouseClicked(event->{
			errorText.setText("");
			File selectedImage = chooseImage(event);
			if (selectedImage != null)
			{
				postImage = selectedImage;
				setPostImg(postImage);
				imageChanged = true;
			}else
				SceneChanger.showPopUP("you must select an image !","Error !");
		});
		SaveCategery.setOnAction(event->{
			errorText.setText("");
			if (CategeryName.getText().length() > 0)
			{
				apiReq api  = new apiReq(Staff.APILINK + "/categories/addcategory");
				staff.getToken().addAuthorization(api);
				api.addPostParam("category_name", CategeryName.getText(),false);
				
				String responce = api.send("POST", false);
				JSONObject jso = new JSONObject(responce);
				if(!jso.has("error"))
				{
					Cat.put(CategeryName.getText(), jso.getInt("id"));
					CategeryBox.getItems().add(CategeryName.getText());
					CategeryBox.setValue(CategeryName.getText());
				}else
				SceneChanger.showPopUP("you are not Authorized to add Category !","Error !");
			}else	
			SceneChanger.showPopUP("you can not set an empty category !","Error !");
			
		});
		Tcancel.setOnAction(event->{
			errorText.setText("");
			ReturnToDashboard(event);
		});
			TSave.setOnAction(event->{
				System.out.println(HtmlEditor.getHtmlText());
				String[] htmltext = HtmlEditor.getHtmlText().split("contenteditable=\"true\"");
				StringBuilder str  = new StringBuilder();
				for (String htm : htmltext)
				{
					str.append(htm);
				}
				errorText.setText("");
				
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					Categery category ;
					StringBuilder tags = new StringBuilder();
					
		
					TagsBox.getItems().forEach(e->{
						
						tags.append(e);
						//if (i < TagsBox.getItems().size())
						tags.append(",");
					});
					
//					if( !Cat.contains(CategeryBox.getValue()))
//					{
//						category  = Categery.newCategery(CategeryBox.getValue(), staff.getToken());
//					}
//					else
					if (CategeryBox.getValue() == null )
					{
						SceneChanger.showPopUP("you must choose a category !","Error !");
						return;
					}
						category = Categery.fromId(Cat.get(CategeryBox.getValue()));
					Post ps;
//					if(post.getImage() == null)
//					ps = new Post(Postname.getText(), htmlField.getText(),
//						category	,Post.getTags(tags.toString()), staff.getId(),postImage);
//					else
					
						ps = new Post(Postname.getText(),str.toString(),
								post.getId(),category	,Post.getTags(tags.toString()), staff.getId(),postImage);
					
					PostJsonHandler<Post> pjs = new PostJsonHandler<>();
					ArrayList<Post> posts = new ArrayList<>();
					posts.add(ps);
				 String jso = null;
				try {
					jso = pjs.build(posts);
				} catch (JSONException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 System.out.println(jso);
				 apiReq api = new apiReq(Staff.APILINK + "/post/addpost");
				 staff.getToken().addAuthorization(api);
				 api.addPostParam("", jso, true);
				 String responce = api.send("POST", false);
				 System.out.println(responce);
				 JSONObject ob = new JSONObject(responce);
				 	
				 if(ob.has("error"))
				 	{
				 		
				 		SceneChanger.showPopUP("you are not authorized to add posts !","Error !");
				 	}
				 	else
				 		SceneChanger.showPopUP("post added "," Message (0_0)");
				 	
						ReturnToDashboard(event);
				
				 	
				 	}
			});
			HtmlEditor.setDisable(false);
		});
			
		if (CategeryBox.getItems().isEmpty())
			Staff.categerys.forEach(c->{
				
				CategeryBox.getItems().add(((Categery)c).getContent());
				Cat.put(((Categery)c).getContent(), ((Categery)c).getId());
			});
		if(post.getCategery() != null)
		CategeryBox.setValue(post.getCategery().getContent());
			
		HtmlEditor.setHtmlText(post.getContent());
		HtmlEditor.applyCss();
		
		Postname.setText(post.getTitle());
		errorText.setText("");
		if(post.getTags() != null)
		post.getTags().forEach(t->{
			TagsBox.getItems().add(t);
		});
		
		editHtml.setOnAction(event->{
			errorText.setText("");
			htmlField.setDisable(false);
			htmlField.setVisible(true);
			HtmlEditor.setDisable(true);
			HtmlEditor.setVisible(false);
			htmlField.setText(HtmlEditor.getHtmlText());
		});
		
		editText.setOnAction(event->{
			errorText.setText("");
			htmlField.setDisable(true);
			htmlField.setVisible(false);
			HtmlEditor.setDisable(false);
			HtmlEditor.setVisible(true);
			HtmlEditor.setHtmlText(htmlField.getText());
		});
		
		
	
		
		
		
		AddLocalImage.setOnAction(event->{
			errorText.setText("");
    		File selectedFile = chooseImage(event);
    		if (selectedFile != null) {
    			
    		
    		try {
     		
    			apiReq api = new apiReq(Staff.APILINK + "/images/addimage");
    			
    
    			staff.getToken().addAuthorization(api);
    			JSONObject imageJson = Post.getImageJson(selectedFile);
    			System.out.println(imageJson);	
    			api.addPostParam("", imageJson.toString(), true);
    		
    				String responce = api.send("POST", false);
    			SceneChanger.showPopUP("uploading image " ,"Message <0_0>");
    			JSONObject jso = new JSONObject(responce);
    		
    			if(!jso.has("error"))
    			{
    				if(jso.has("url")) {
    				String htmltext = "</br><img src='"+(jso.getString("url") ) + "'></img> </br>";
    				
        			HtmlEditor.setHtmlText(HtmlEditor.getHtmlText() + htmltext);
    				}
    				else
    					SceneChanger.showPopUP("invalid image \n allowed Extention : .png \n .jpg \n .jpeg \n .gif .", "Error !");
    			}else
    				SceneChanger.showPopUP("you are not Authorized to add images" ,"Error !");
    			
    			
    	
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			SceneChanger.showPopUP("connection Error try later!" ,"Error !");
    		}}
    		else 
    			SceneChanger.showPopUP("you must choose an  image" ,"Error !");
			
		});
		addWebImage.setOnAction(event->{
			if (Link.getText().length() > 0)
			{
				String htmltext = "</br><img src='"+ Link.getText() + "'></img> </br>";
    			HtmlEditor.setHtmlText(HtmlEditor.getHtmlText() + htmltext);
			}
			else
				SceneChanger.showPopUP("you must add Image link !" ,"Error !");
		});
		addTag.setOnAction(event->{
			if (tagsFiled.getText().length() != 0)
			{
				//post.getTags().add(tagsFiled.getText());
				TagsBox.getItems().add(tagsFiled.getText());
				TagsBox.setValue(tagsFiled.getText());
			}else 
				SceneChanger.showPopUP("you can't add an empty tag!" ,"Error");
		});
		
		
		
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
	public void ReturnToDashboard(Event event)
	{
		ControllerClass db = new dashboardController();
		SceneChanger sn = new SceneChanger();
		try {
			sn.changeScenes(event, "/application/view/dashboard.fxml", "Dashboard", this.staff, db);
		} catch (IOException e1) {
			// TODO Auto-generated catch 
			SceneChanger.showPopUP(e1.getMessage(),"Error!" );
		}
		
	}
	@Override
	public void preloadData(Staff staff, Staff toEdit) {
		// TODO Auto-generated method stub
		
	}
}
