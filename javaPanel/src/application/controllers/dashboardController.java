package application.controllers;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import application.models.Comment;
import application.models.CommentsJsonHandler;
import application.models.ControllerClass;
import application.models.Post;
import application.models.Staff;
import application.models.apiReq;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class dashboardController implements ControllerClass{
	private List<Comment>  comments;
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

	@Override
	public void preloadData(Staff staff) {
		this.staff = staff;
		welAdmin.setText(staff.getName());
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
			
		});
		
		delArticle.setOnAction(event->{
			
		});
		
		editArticle.setOnAction(event->{
			
		});
		
		addStaff.setOnAction(event->{
			
		});
	}
	@Override
	public void preloadData(Staff staff, Post post) {
		// TODO Auto-generated method stub
		
	}
	public void getComments() {
		comments.clear();
		apiReq api = new apiReq(Staff.APILINK+"/comments");
		CommentsJsonHandler<Comment> cjh = new CommentsJsonHandler<>();
		api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
		String responce = api.send("GET", false);
		System.out.println(responce);
		 comments = (List<Comment>)cjh.parse(responce);
		comments.forEach(c ->System.out.println(c));
		
	}

}
