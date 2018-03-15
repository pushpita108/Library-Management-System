package valueObj;

import java.util.ArrayList;

public class SearchFields {

	public ArrayList<String> isbn = new ArrayList<String>();
	public ArrayList<String> title = new ArrayList<String>();
	public ArrayList<String> name = new ArrayList<String>();
	public ArrayList<String> available = new ArrayList<String>();

	public ArrayList<String> getIsbn() {
		return isbn;
	}

	public void setIsbn(ArrayList<String> isbn) {
		this.isbn = isbn;
	}

	public ArrayList<String> getTitle() {
		return title;
	}

	public void setTitle(ArrayList<String> title) {
		this.title = title;
	}

	public ArrayList<String> getName() {
		return name;
	}

	public void setName(ArrayList<String> name) {
		this.name = name;
	}

	public ArrayList<String> getAvailable() {
		return available;
	}

	public void setAvailable(ArrayList<String> available) {
		this.available = available;
	}


}
