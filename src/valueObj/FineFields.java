package valueObj;

import java.util.ArrayList;

public class FineFields {

	public ArrayList<String> loanId = new ArrayList<String>();
	public ArrayList<String> paid = new ArrayList<String>();
	public ArrayList<Float> fineAmt = new ArrayList<Float>();
	public ArrayList<String> isbn = new ArrayList<String>();
	public ArrayList<String> borrower = new ArrayList<String>();
	public ArrayList<String> dateIn = new ArrayList<String>();
	public ArrayList<String> cardId = new ArrayList<String>();

	public ArrayList<String> getLoanId() {
		return loanId;
	}

	public void setLoanId(ArrayList<String> loanId) {
		this.loanId = loanId;
	}

	public ArrayList<String> getPaid() {
		return paid;
	}

	public void setPaid(ArrayList<String> paid) {
		this.paid = paid;
	}

	public ArrayList<Float> getFineAmt() {
		return fineAmt;
	}

	public void setFineAmt(ArrayList<Float> fineAmt) {
		this.fineAmt = fineAmt;
	}

	public ArrayList<String> getIsbn() {
		return isbn;
	}

	public void setIsbn(ArrayList<String> isbn) {
		this.isbn = isbn;
	}

	public ArrayList<String> getBorrower() {
		return borrower;
	}

	public void setBorrower(ArrayList<String> borrower) {
		this.borrower = borrower;
	}

	public ArrayList<String> getCardId() {
		return cardId;
	}

	public void setCardId(ArrayList<String> cardId) {
		this.cardId = cardId;
	}

	public ArrayList<String> getDateIn() {
		return dateIn;
	}

	public void setDateIn(ArrayList<String> dateIn) {
		this.dateIn = dateIn;
	}

}
