package application.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;

import org.json.JSONObject;

import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation;
import com.sun.media.jfxmediaimpl.platform.Platform;

import application.models.Comment;
import application.models.CommentsJsonHandler;
import application.models.ControllerClass;
import application.models.Post;
import application.models.PostJsonHandler;
import application.models.SceneChanger;
import application.models.Staff;
import application.models.apiReq;
import application.models.osFinder;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class dashboardController implements ControllerClass{
	private List<Comment>  comments;
	private List<Post>  posts;
	private Staff staff;
	@FXML
    private Circle staffImage;

    @FXML
    private Text welAdmin;

    @FXML
    private Button addArticle;

    @FXML
    private Button delArticle;

    @FXML
    private Button editArticle;

    @FXML
    private Button addStaff;

    @FXML
    private VBox infBox;
    @FXML
    private Button editProfilr;
    @FXML
    private Button Comments;

    @FXML
    private Button logOut;
   
    private int postIndex = 1;
    private int commentindex;
    private int maxpages = 1;
	@Override
	public void preloadData(Staff staff) {
		
		this.staff = staff;
		//staffImage.setOpacity(100.0);
	//	staffImage.setStroke(Color.SEAGREEN); 
    	//staffImage.setFill(Color.SNOW);
    //	staffImage.setEffect(new DropShadow(+50d, 0d, +5d, Color.DARKSEAGREEN));
		postIndex = 1;
		commentindex = 1;
		Comments.setOnAction(event->{
			getComments(commentindex);
		});
		welAdmin.setText(staff.getName());
		posts = new ArrayList<>();
		comments = new ArrayList();
		getComments(commentindex);
		
		try {
			
			String mimetype= new MimetypesFileTypeMap().getContentType(staff.getImage());
	        String type = mimetype.split("/")[0];
	        if(type.equals("application") || type.equals("image")) {
			Image image = new Image(new FileInputStream(staff.getImage()));

			this.staffImage.setFill(new ImagePattern(image));
	        }else
	        {
	        	
	        	Image image;
				try {
					String  path ;
					if (osFinder.isWindwos)
					 {
						 path = osFinder.currentPath + "\\src\\defaultImage.png";
						 
					 }else
						 path = osFinder.currentPath + "/src/defaultImage.png";
					image = new Image(new FileInputStream(new File(path)));
					this.staffImage.setFill(new ImagePattern(image));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	        } catch (Exception e) {

			

			
			e.printStackTrace();
		}
		
		addArticle.setOnAction(event->{
			ControllerClass db = new dashboardController();
			SceneChanger sn = new SceneChanger();
			try {
				String path;
				if (osFinder.isWindwos)
				 {
					 path =  "Editor.fxml";
					 
				 }else
					 path =  "/application/view/Editor.fxml";
				sn.changeScenes(event, path, "Editor", staff, db);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		delArticle.setOnAction(event->{
			this.infBox.getChildren().removeIf(e->e.isVisible());
		
				getPosts(postIndex,true);
			
		
		});
		
		editArticle.setOnAction(event->{
			this.infBox.getChildren().removeIf(e->e.isVisible());
			
			getPosts(postIndex,false);
			
		});
		editProfilr.setOnAction(event->{

			ControllerClass db = new dashboardController();
			SceneChanger sn = new SceneChanger();
			try {
				String path;
				if (osFinder.isWindwos)
				 {
					 path =  "addStaff.fxml";
					 
				 }else
					 path =  "/application/view/addStaff.fxml";
				sn.changeScenes(event, path, "add User", staff,staff, db);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		logOut.setOnAction(event->{
			
			SceneChanger sn = new SceneChanger();
			try {
				String path;
				if (osFinder.isWindwos)
				 {
					 path =  "login.fxml";
					 
				 }else
					 path =  "/application/view/login.fxml";
				sn.changeScenes(event, path, "login");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		addStaff.setOnAction(event->{
			
			ControllerClass db = new dashboardController();
			SceneChanger sn = new SceneChanger();
			try {
				String path;
				if (osFinder.isWindwos)
				 {
					 path =  "addStaff.fxml";
					 
				 }else
					 path =  "/application/view/addStaff.fxml";
				sn.changeScenes(event, path, "add User", staff,null, db);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
	}

	public void getPosts(int index,boolean toDel)
	{


    		infBox.getChildren().removeIf(e->true);
    		infBox.getChildren().add(new Text("loading posts"));
    		Button showMore  = new Button();
    		Button showLess = new Button();
    		showLess.setText("Less");
                    showMore.setText("More");
                    if(postIndex <= 1)
                    showLess.setDisable(true);
                    
                    showLess.setOnAction(event->{
                    	showMore.setDisable(false);
                    	if(postIndex > 1)
                    	{
                    		postIndex--;
                    		getPosts(postIndex , toDel);
                    	}
                    	else 
                    		showLess.setDisable(true);
                    });
            		
            		showMore.setOnAction(event->{
            			
            			showLess.setDisable(false);
            			if (postIndex < maxpages)
            			{
            				postIndex++;
            				//showLess.setDisable(false);
            				getPosts(postIndex , toDel);
            			}
            			else
            				showMore.setDisable(true);
            		});
            		
            		//alert.showAndWait();
            		javafx.application.Platform.runLater(new Runnable() {

            			
            			@Override
            			public void run() {

            				// TODO Auto-generated method stub
            				apiReq api = new apiReq(Staff.APILINK +"/post/posts?page="+postIndex);
            				//	api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
            					staff.getToken().addAuthorization(api);
            					//api.addProperty("page", String.valueOf(index));
            					String responce = api.send("GET", false);
            					JSONObject jso = new JSONObject(responce);
            					maxpages = jso.getInt("last_page");
            					PostJsonHandler<Post> pjs = new PostJsonHandler<>();
            					posts = pjs.parse(responce);

            					infBox.getChildren().removeIf(e->true);
            					infBox.getChildren().add(new Text("Posts :"));
            					infBox.setSpacing(15.0);
            					//infBox.setPrefSize(100, 30);
            					infBox.setPadding(new Insets(10.0, 0, 0, 10.0));
            					infBox.setAlignment(Pos.TOP_LEFT);
            					
            					 int ind =0;
            					for(Post pp : posts)
            					{
            						HBox box = null;
            					
            							pjs.setPostImage(pp, pp.getImageurl());
            						 
            						if (!toDel)
            						{
            						try {
            							FXMLLoader loader = new FXMLLoader();
            							String path;
            							if (osFinder.isWindwos)
            							 {
            								 path =  "PostHolder.fxml";
            								 
            							 }else
            								 path =  "/application/view/PostHolder.fxml";
            						        loader.setLocation(getClass().getResource(path));
            						        
            						box = loader.load();
            						box.setId("id" + ind);
            						ind++;
            						PostHolderController cm = loader.getController();
            						cm.preloadData(staff, pp,false);
            						
            						
            						}catch(Exception e)
            						{
            							e.printStackTrace();
            						}
            						}
            						else
            						{
            							try {
                							FXMLLoader loader = new FXMLLoader();
                							String path;
                							if (osFinder.isWindwos)
               							 {
               								 path =  "PostHolder.fxml";
               								 
               							 }else
               								 path =  "/application/view/PostHolder.fxml";
               						        loader.setLocation(getClass().getResource(path));
                						        
                						box = loader.load();
                						box.setId("id" + ind);
                						ind++;
                						PostHolderController cm = loader.getController();
                						cm.preloadData(staff, pp,true);
                						
                						
                						}catch(Exception e)
                						{
                							e.printStackTrace();
                						}
            						}
            							
            						if(box != null)
            						infBox.getChildren().add( box);
            						
            						
            					}
            					
            					HBox buttonHolder = new HBox();
            					buttonHolder.setSpacing(20);
            					buttonHolder.setAlignment(Pos.CENTER);
            					buttonHolder.getChildren().addAll(showLess, showMore);
            					
            					infBox.getChildren().add(buttonHolder);
            					
            			}
            		});

 
                     
                    
 		
		
		
		//Alert alert = new Alert(Alert.AlertType.INFORMATION,"loading Posts",ButtonType.OK) ;

//		alert.setTitle("loading");
//		alert.setContentText("loading Posts");
				
		//alert.show();
		
		
		
	}
	@Override
	public void preloadData(Staff staff, Post post) {
		// TODO Auto-generated method stub
		
	}
	public void getComments(int pageindex) {
		
		comments.clear();
		
		infBox.getChildren().removeIf(e->e.isVisible());
		
		
		
		apiReq api = new apiReq(Staff.APILINK+"/comments");
		CommentsJsonHandler<Comment> cjh = new CommentsJsonHandler<>();
		//api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
		staff.getToken().addAuthorization(api);
	api.addProperty("page", String.valueOf(pageindex));
		String responce = api.send("GET", false);
	///	System.out.println(responce);
		 comments = (List<Comment>)cjh.parse(responce);
	
		if (comments.size() > 0) {
			infBox.setSpacing(15.0);
			//infBox.setPrefSize(100, 30);
			infBox.setPadding(new Insets(10.0, 0, 0, 10.0));
			infBox.setAlignment(Pos.TOP_LEFT);
		infBox.getChildren().add(new Text("Comments :"));
		for(Comment cmt : comments)
		{
			try {
				 FXMLLoader loader = new FXMLLoader();
				 String path;
				 if (osFinder.isWindwos)
				 {
					 path =  "CommentsHolder.fxml";
					 
				 }else
					 path =  "/application/view/CommentsHolder.fxml";
			        loader.setLocation(getClass().getResource(path));
			        
			        
			VBox cmtField = loader.load();
			CommentsHolderController cm = loader.getController();
			cm.preloadData(cmt);
			
			infBox.getChildren().add(cmtField);
			}catch(Exception e)
			{
				
			}
			
		}
		}
		
	}
	
	@Override
	public void preloadData(Staff staff, Staff toEdit) {
		// TODO Auto-generated method stub
		
	}

}
