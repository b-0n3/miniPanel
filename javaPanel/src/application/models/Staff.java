package application.models;

public class Staff {
	private String firstName, lastName,email,passWord,phoneNumber,cookie;
	
	public Staff(String firstName , String lastName, String email, String passWord, String phoneNumber)
	{
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassWord(passWord);
		setPhoneNumber(phoneNumber);
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	  
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void handleCookie()
	{
		
	}
	 /**
     * area code    city    house 
     * NXX          -XXX    -XXXX
     * @param phoneNumber 
     */
	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber.matches("[0-9]\\d{2}[-.]?\\d{3}[-.]\\d{4}"))
            this.phoneNumber = phoneNumber;
        else
            throw new IllegalArgumentException("Phone numbers must be in the pattern NXX-XXX-XXXX");
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email.matches(""))
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
	
}
