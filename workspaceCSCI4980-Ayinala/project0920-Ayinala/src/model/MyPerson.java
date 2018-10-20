package model;

public class MyPerson {
	private String firstName;
	private String lastName;
	private String phn;
	private String address;

	public MyPerson(String firstName, String lastName, String phn, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phn = phn;
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhn() {
		return phn;
	}

	public String getAddress() {
		return address;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhn(String phn) {
		this.phn = phn;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
