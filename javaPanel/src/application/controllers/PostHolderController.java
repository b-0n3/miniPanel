package application.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLDocument.HTMLReader;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import application.models.ControllerClass;
import application.models.Post;
import application.models.SceneChanger;
import application.models.Staff;
import application.models.apiReq;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PostHolderController {

	
	   @FXML
	    private Circle PostImage;

	    @FXML
	    private Text Title;

	    @FXML
	    private Text tags;

	    @FXML
	    private Text Category;

	    @FXML
	    private Text Content;

	    @FXML
	    private Button EditMe;
	    
	public void preloadData(Staff staff,Post post,boolean toDelete)
	{
		
			Title.setText(post.getTitle());
			String tag =  " ";
			for(String ta : post.getTags())
				tag += " " + ta;
			tags.setText(tag);
			Category.setText(post.getCategery().getContent());
			Document doc = Jsoup.parse(post.getContent());
			Elements el = doc.select("p");
			String html;
			if(el.text().length() >50)
			 html = el.text().substring(0, 50);
			else
			html = el.text();

			String mimetype= new MimetypesFileTypeMap().getContentType(post.getImage());
	        String type = mimetype.split("/")[0];
	        if(type.equals("application") || type.equals("image")) {
			Image image;
			try {
				image = new Image(new FileInputStream(post.getImage()));
				this.PostImage.setFill(new ImagePattern(image));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
	        }else
	        {
	        	Image image;
				try {
					image = new Image(new FileInputStream(new File("./src/defaultImage.png")));
					this.PostImage.setFill(new ImagePattern(image));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
			Content.setText(html);
			System.out.println("adding post d");
			if(!toDelete)
			EditMe.setOnAction(event->{
				ControllerClass db = new dashboardController();
				SceneChanger sn = new SceneChanger();
				try {
					sn.changeScenes(event, "/application/view/Editor.fxml", "Editor", staff, db,post);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			else {
				EditMe.setText("Delete me");
				
				EditMe.setOnAction(event->{
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setContentText("are you sure ?");
					alert.setHeaderText("Confirmation.");
					
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								apiReq api = new apiReq(Staff.APILINK +"/post/delpost");
								//	api.addProperty("Authorization", "Bearer " + staff.getToken().getToken());
									staff.getToken().addAuthorization(api);
									//api.addProperty("page", String.valueOf(index));
									JSONObject jso = new JSONObject();
									jso.put("post_id", post.getId() );
									api.addPostParam("", jso.toString(), true);
									String responce = api.send("POST", false);
									if(new JSONObject(responce).has("error"))
											SceneChanger.showPopUP("you are not able to delete posts", "Error !");
									else
									{
										SceneChanger.showPopUP("post has been  deleted ", "Message <0_0>");
										Content.setText("Deleted");
									}
							}
						});
						
					}
				});
			}
			
			
			
		
	}
}
