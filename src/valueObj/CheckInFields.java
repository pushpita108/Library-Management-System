package valueObj;

import java.util.ArrayList;
public class CheckInFields {

	public ArrayList<String> isbn = new ArrayList<String>();
	public ArrayList<String> borrowerId = new ArrayList<String>();
	public ArrayList<String> fname = new ArrayList<String>();
	public ArrayList<String> lname = new ArrayList<String>();

	public ArrayList<String> getIsbn() {
		return isbn;
	}

	public void setIsbn(ArrayList<String> isbn) {
		this.isbn = isbn;
	}

	public ArrayList<String> getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(ArrayList<String> borrowerId) {
		this.borrowerId = borrowerId;
	}

	public ArrayList<String> getfname() {
		return fname;
	}

	public void setfname(ArrayList<String> fname) {
		this.fname = fname;
	}

	public ArrayList<String> getlname() {
		return lname;
	}

	public void setlname(ArrayList<String> lname) {
		this.lname = lname;
	}

}
