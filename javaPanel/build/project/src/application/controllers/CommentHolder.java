package application.controllers;

import java.util.ArrayList;

import application.models.Comment;
import javafx.scene.text.Text;

public class CommentHolder<T> extends DataHolder<T> {

	private Text ownerEmail;
	private Text website;
	
	public CommentHolder(T Data) {
		super(Data);
	}

	@Override
	public void show() {
		this.getChildren().addAll(this.name,this.website
				,this.ownerEmail,this.content);
	}

	@Override
	public void preloadData() {
		
		Comment cmt = (Comment) this.data;
		
		ownerEmail = new Text();
		website = new Text();
		this.content.setText(cmt.getContent());
		this.name.setText(cmt.getOwnoerName());
		ownerEmail.setText(cmt.getOwnerEmail());
		website.setText(cmt.getWebsite());
		
	}

}
