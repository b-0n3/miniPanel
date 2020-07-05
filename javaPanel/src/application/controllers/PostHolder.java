package application.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.models.Post;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

public class PostHolder<T> extends DataHolder<T> {
	Text category;
	
	public PostHolder(T Data) {
		super(Data);
		
		category = new Text();
	}

	@Override
	public void show() {
		preloadData();
		super.getChildren().add(this.image);
		VBox box = new VBox();
		box.getChildren().add(this.name);
		box.getChildren().add(this.category);
		box.setSpacing(5.0);
		box.setPrefSize(100, 30);

		box.setAlignment(Pos.CENTER_RIGHT);
	
		//box.getChildren().add(this.content);
		super.getChildren().add(box);
	}

	@Override
	public void preloadData() {
		Post post = (Post) super.data;
		
		
		this.category.setText(post.getCategery().getContent());
		
		name.setText(post.getTitle());
		this.setSpacing(100.0);
        this.setAlignment(Pos.CENTER_LEFT);
        this.image.setFitHeight(30);
        this.image.setFitWidth(30);
		try {
			Image image = new Image(new FileInputStream(post.getImage()));

			this.image.setImage(image);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

}
