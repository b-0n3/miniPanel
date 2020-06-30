package application.models;

public class Comment {
	private String website,ownoerName, OwnerEmail,content;
	private int postid;
	public Comment(String website, String ownoerName, String ownerEmail, String content) {
		
		this.website = website;
		this.ownoerName = ownoerName;
		OwnerEmail = ownerEmail;
		this.content = content;
		this.postid = postid;
	}
	@Override
	public String toString() {
		return "Comment [website=" + website + ", ownoerName=" + ownoerName + ", OwnerEmail=" + OwnerEmail
				+ ", content=" + content + ", postid=" + postid + "]";
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getOwnoerName() {
		return ownoerName;
	}
	public void setOwnoerName(String ownoerName) {
		this.ownoerName = ownoerName;
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
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	
	
}
