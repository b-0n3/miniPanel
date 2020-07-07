package application.controllers;

import application.models.Comment;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CommentsHolderController {
	 @FXML
	    private Text name;

	    @FXML
	    private Text email;

	    @FXML
	    private Text webSite;

	    @FXML
	    private Text Content;
	    public void preloadData(Comment com)
	    {
	    	name.setText(com.getOwnoerName());
	    	email.setText(com.getOwnerEmail());
	    	webSite.setText(com.getWebsite());
	    	Content.setText(com.getContent());
	    }
}
