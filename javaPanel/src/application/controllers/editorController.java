package application.controllers;


import java.util.Hashtable;


import application.models.ControllerClass;
import application.models.Post;
import application.models.Staff;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;

public class editorController implements ControllerClass {
	@FXML
    private TextField Postname;

    @FXML
    private Button editHtmlEnable;

    @FXML
    private Button Tcancel;

    @FXML
    private Button TSave;

    @FXML
    private Button editText;

    @FXML
    private Button editHtml;

    @FXML
    private HTMLEditor HtmlEditor;

    @FXML
    private TextField tagsFiled;

    @FXML
    private ComboBox<String> TagsBox;

    @FXML
    private Button addTag;

    @FXML
    private ComboBox<String> CategeryBox;

    @FXML
    private TextField CategeryName;
    @FXML
    private ScrollPane ScrPane;
    
    @FXML
    private Button CancelCategery;

    @FXML
    private Button SaveCategery;

    @FXML
    private Button AddLocalImage;

    @FXML
    private Button SendImage;

    @FXML
    private TextField Link;

    @FXML
    private Button addWebImage;
    @FXML
    private TextArea htmlField;

    @FXML
    private Text errorText;
    private Hashtable<String , Integer> Cat = new Hashtable<>();
    private Staff staff;
    @FXML
    public void initialize() 
    {
    
    	preloadData(null);
    }
    @Override
	public void preloadData(Staff staff) {
    	this.staff = staff;
    	this.preloadData(this.staff,new Post("new post","<b> hello world</>",this.staff));;
    }

	@Override
	public void preloadData(Staff staff, Post post) {
		
		
		HtmlEditor.setHtmlText(post.getContent());
		Postname.setText(post.getName());
		errorText.setText("");
		
		post.getTags().forEach(t->{
		TagsBox.getItems().add(t);
		});
		
		editHtml.setOnAction(event->{
			htmlField.setDisable(false);
			htmlField.setVisible(true);
			HtmlEditor.setDisable(true);
			HtmlEditor.setVisible(false);
			htmlField.setText(HtmlEditor.getHtmlText());
		});
		
		editText.setOnAction(event->{
			htmlField.setDisable(true);
			htmlField.setVisible(false);
			HtmlEditor.setDisable(false);
			HtmlEditor.setVisible(true);
			HtmlEditor.setHtmlText(htmlField.getText());
		});
		
		
		editHtml.setDisable(true);
		editHtml.setVisible(false);
		
		
		editHtmlEnable.setOnAction(event->{
			editHtml.setDisable(false);
			editHtml.setVisible(true);
			editHtmlEnable.setDisable(true);
			editHtmlEnable.setVisible(false);
		});
		
		addTag.setOnAction(event->{
			if (tagsFiled.getText().length() != 0)
			{
				//post.getTags().add(tagsFiled.getText());
				TagsBox.getItems().add(tagsFiled.getText());
				TagsBox.setValue(tagsFiled.getText());
			}else 
				errorText.setText("you can't set an empty tag");
		});
//		Staff.getCategerys().forEach(c->{
//			CategeryBox.getItems().add(((Categery)c).getContent());
//			Cat.put(((Categery)c).getContent(), ((Categery)c).getId());
//		});
		
		
		}
}
