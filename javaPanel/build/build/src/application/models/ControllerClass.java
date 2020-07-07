package  application.models;

import java.util.ArrayList;



public interface ControllerClass {
	public abstract  void preloadData( Staff staff);
	public abstract  void preloadData( Staff staff, Post post);
	void preloadData(Staff staff,   Staff toEdit);
}
