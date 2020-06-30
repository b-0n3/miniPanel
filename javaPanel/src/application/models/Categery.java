package application.models;

public class Categery {
	private int id;
	private String content;
	public Categery(int id, String content) {
		
		this.id = id;
		this.content = content;
	}

	
	@Override
	public String toString() {
		return "Categery [id=" + id + ", content=" + content + "]";
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
