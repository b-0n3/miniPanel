package application.controllers;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public abstract class DataHolder<T> extends HBox {
	T data ;
	Text content;
	Text name;
	ImageView image;

	public DataHolder(T Data) 
	{
		content = new Text();
		name = new Text();
		image = new ImageView();
		this.data = Data;
	}
	
	public abstract void show();
	public abstract void preloadData();
	
}
