package application.models;

public class Comment {
	private String Owner, OwnerEmail,content;
	private Post post;

	public String getOwner() {
		return Owner;
	}

	public void setOwner(String owner) {
		Owner = owner;
	}

	public String getOwnerEmail() {
		return OwnerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		OwnerEmail = ownerEmail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
