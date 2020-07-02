package application.controllers;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation;

import application.models.Comment;
import application.models.CommentsJsonHandler;
import application.models.ControllerClass;
import application.models.Post;
import application.models.PostJsonHandler;
import application.models.SceneChanger;
import application.models.Staff;
import application.models.apiReq;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Popup;


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
    private Button addCategery;

    @FXML
    private Button logOut;
    private Post currentPost = null;

	@Override
	public void preloadData(Staff staff) {
		this.staff = staff;
		welAdmin.setText(staff.getName());
		posts = new ArrayList<>();
		comments =new ArrayList();
		getComments();
		DataHolder<Comment> cmtFields;
		infBox.getChildren().removeIf(e->true);
		
		for(Comment cmt : comments)
		{
			cmtFields = new CommentHolder<Comment>(cmt);
			cmtFields.preloadData();
			cmtFields.show();
			infBox.getChildren().add(cmtFields);
		}
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
			if (posts.isEmpty())
				getPosts();
			
		this.posts.forEach(pp->{
			DataHolder<Post> ps = new PostHolder<Post>(pp);
			Button bt = new Button();
			bt.setText("me");
			ps.show();
			
			bt.setOnAction(ev->{
				deletePost(pp);
			});
			ps.getChildren().add(bt);
				this.infBox.getChildren().add(ps);
			
		});
		});
		
		editArticle.setOnAction(event->{
			this.infBox.getChildren().removeIf(e->e.isVisible());
			if(posts.isEmpty())
			getPosts();
			
			this.posts.forEach(pp->{
				DataHolder<Post> ps = new PostHolder<Post>(pp);
				Button bt = new Button();
				bt.setText("me");
				ps.show();
				
				bt.setOnAction(ev->{
					ControllerClass db = new dashboardController();
					SceneChanger sn = new SceneChanger();
					try {
						sn.changeScenes(event, "/application/view/Editor.fxml", "Editor", this.staff, db,pp);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				ps.getChildren().add(bt);
				this.infBox.getChildren().add(ps);
				
			});
		});
		
		addStaff.setOnAction(event->{
			if (staff.getLevel() == 1)
			{
			ControllerClass db = new dashboardController();
			SceneChanger sn = new SceneChanger();
			try {
				sn.changeScenes(event, "/application/view/Editor.fxml", "Editor", staff, db);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}else
			{
				Popup pop = new Popup();
				
				
			}
		});
	}
	private void deletePost(Post pp) {
		
	}
	public void getPosts()
	{
		apiReq api = new apiReq(Staff.APILINK +"/post/posts");
	//	api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
		staff.getToken().addAuthorization(api);
		String responce = api.send("GET", false);
		PostJsonHandler<Post> pjs = new PostJsonHandler<>();
		posts = pjs.parse(responce);
		
		System.out.println(responce);
		
	}
	@Override
	public void preloadData(Staff staff, Post post) {
		// TODO Auto-generated method stub
		
	}
	public void getComments() {
		comments.clear();
		apiReq api = new apiReq(Staff.APILINK+"/comments");
		CommentsJsonHandler<Comment> cjh = new CommentsJsonHandler<>();
		//api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
		staff.getToken().addAuthorization(api);
		String responce = api.send("GET", false);
		System.out.println(responce);
		 comments = (List<Comment>)cjh.parse(responce);
		comments.forEach(c ->System.out.println(c));
		
	}
	@Override
	public void preloadData(Staff staff, Staff toEdit) {
		// TODO Auto-generated method stub
		
	}

}
