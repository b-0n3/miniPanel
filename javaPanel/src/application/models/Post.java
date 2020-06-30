package application.models;

import java.util.ArrayList;

public class Post {
	private String name , content;
	private int id;
	private Categery categery;
	private ArrayList<String> tags;
	private Staff owner;
	public Post(String name, String content, int id, Categery categery, ArrayList<String> tags, Staff owner) {
		super();
		this.name = name;
		this.content = content;
		this.id = id;
		this.categery = categery;
		this.tags = tags;
		this.owner = owner;
	}
	public Post(String name, String content, Staff owner) {
	
		this(name,content ,-1,new Categery(-1, "Categery"),new ArrayList<>(),owner);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Categery getCategery() {
		return categery;
	}
	public void setCategery(Categery categery) {
		this.categery = categery;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public Staff getOwner() {
		return owner;
	}
	public void setOwner(Staff owner) {
		this.owner = owner;
	}
	
}
