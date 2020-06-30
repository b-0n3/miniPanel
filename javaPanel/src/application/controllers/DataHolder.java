package application.controllers;

import java.util.ArrayList;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public abstract class DataHolder<T> extends HBox {
	T data ;
	Text content;
	Text name;
	Circle image;

	public DataHolder(T Data) 
	{
		this.data = Data;
	}
	
	public abstract void show();
	public abstract void preloadData();
	
}
