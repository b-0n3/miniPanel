package application.controllers;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
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
    private Post currentPost = null;
    private int postIndex;
    private int commentindex;
    private int maxpages = 1;
	@Override
	public void preloadData(Staff staff) {
		this.staff = staff;
		postIndex = 1;
		commentindex = 1;
		Comments.setOnAction(event->{
			getComments(commentindex);
		});
		welAdmin.setText(staff.getName());
		posts = new ArrayList<>();
		comments =new ArrayList();
		getComments(commentindex);
		try {
			Image image = new Image(new FileInputStream(staff.getImage()));

			this.staffImage.setFill(new ImagePattern(image));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		addArticle.setOnAction(event->{
			ControllerClass db = new dashboardController();
			SceneChanger sn = new SceneChanger();
			try {
				sn.changeScenes(event, "/application/view/Editor.fxml", "Editor", staff, db);
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
				sn.changeScenes(event, "/application/view/addStaff.fxml", "add User", staff,staff, db);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		logOut.setOnAction(event->{
			
			SceneChanger sn = new SceneChanger();
			try {
				sn.changeScenes(event, "/application/view/login.fxml", "login");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		addStaff.setOnAction(event->{
			
			ControllerClass db = new dashboardController();
			SceneChanger sn = new SceneChanger();
			try {
				sn.changeScenes(event, "/application/view/addStaff.fxml", "add User", staff,null, db);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
	}
	private void deletePost(Post pp) {
		
		apiReq api = new apiReq(Staff.APILINK +"/post/delpost");
		//	api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
			staff.getToken().addAuthorization(api);
			//api.addProperty("page", String.valueOf(index));
			JSONObject jso = new JSONObject();
			jso.put("post_id", pp.getId() );
			api.addPostParam("", jso.toString(), true);
			String responce = api.send("POST", false);
			if(new JSONObject(responce).has("error"))
					SceneChanger.showPopUP("you are not able to delete posts", null);
			else
				SceneChanger.showPopUP("post has been  deleted ", null);
				
	}
	public void getPosts(int index,boolean toDel)
	{


    		infBox.getChildren().removeIf(e->true);
    		infBox.getChildren().add(new Text("loading posts"));
    		Button showMore  = new Button();
 
                    showMore.setText("More");
            		
            		showMore.setOnAction(event->{
            			postIndex++;
            			postIndex %= maxpages;
            			if (postIndex <= maxpages)
            				getPosts(postIndex , false);
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
            					maxpages = jso.getInt("last_page") + 1;
            					PostJsonHandler<Post> pjs = new PostJsonHandler<>();
            					posts = pjs.parse(responce);

            					infBox.getChildren().removeIf(e->true);
            					posts.forEach(pp->{
            						DataHolder<Post> ps = new PostHolder<Post>(pp);
            						Button bt = new Button();
            						bt.setText("me");
            						ps.show();
            						if (!toDel)
            						bt.setOnAction(ev->{
            							ControllerClass db = new dashboardController();
            							SceneChanger sn = new SceneChanger();
            							try {
            								sn.changeScenes(ev, "/application/view/Editor.fxml", "Editor", staff, db,pp);
            							} catch (IOException e1) {
            								// TODO Auto-generated catch block
            								e1.printStackTrace();
            							}
            						});
            						else
            							bt.setOnAction(ev->{
                							deletePost(pp);
                						});
            							
            						ps.getChildren().add(bt);
            						infBox.getChildren().add(ps);
            						
            						
            					});
            					
            					infBox.getChildren().add(showMore);
            					
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
		System.out.println(responce);
		 comments = (List<Comment>)cjh.parse(responce);
		comments.forEach(c ->System.out.println(c));
		DataHolder<Comment> cmtFields;
		
		
		for(Comment cmt : comments)
		{
			cmtFields = new CommentHolder<Comment>(cmt);
			cmtFields.preloadData();
			cmtFields.show();
			infBox.getChildren().add(cmtFields);
		}
		
	}
	
	@Override
	public void preloadData(Staff staff, Staff toEdit) {
		// TODO Auto-generated method stub
		
	}

}
