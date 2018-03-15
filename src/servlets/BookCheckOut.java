package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.MyConnection;

/**
 * Servlet implementation class BookCheckOut
 */
@WebServlet(name = "BookCheckOut", urlPatterns = { "jsp/BookCheckOut" })

public class BookCheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookCheckOut() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("inside book checkout servlet. isbn: " + request.getParameter("isbn") + " card id : "
				+ request.getParameter("cardid"));

		MyConnection obj = new MyConnection();
		Connection conn = null;
		try {
			conn = obj.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		String bookIsbn = request.getParameter("isbn");
		String borrowerCardId = request.getParameter("cardid");

		try {
			PreparedStatement preparedStmtcheck;
			// For books which are currently loaned, Date_In would be Null.
			String loanCount = "select count(*) from book_loans where date_in IS NULL AND Card_id='" + borrowerCardId
					+ "'";
			preparedStmtcheck = conn.prepareStatement(loanCount);
			ResultSet rs = preparedStmtcheck.executeQuery();
			int loanBookcount = 0;
			if (rs.next()) {
				loanBookcount = rs.getInt(1);
			}

			// Restricting further checkout if a borrower has already 3 books checkout
			if (loanBookcount < 3) {
				PreparedStatement preparedStmt;
				String query = " insert into Book_Loans (isbn, card_id, date_out, due_date)" + " values (?, ?, ?, ?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, bookIsbn);
				preparedStmt.setString(2, borrowerCardId);
				Calendar cal = Calendar.getInstance();
				java.sql.Date todayDate = new java.sql.Date(cal.getTime().getTime());
				preparedStmt.setDate(3, todayDate);
				cal.add(Calendar.DATE, 14);
				java.sql.Date futureDate = new java.sql.Date(cal.getTime().getTime());
				preparedStmt.setDate(4, futureDate);
				preparedStmt.execute();

				// update the book table available to NO to note its already checked out
				PreparedStatement preparedStmt2;
				String updateAvailability = "update book set Available='NO' where isbn='" + bookIsbn + "'";
				preparedStmt2 = conn.prepareStatement(updateAvailability);
				preparedStmt2.executeUpdate();

				response.getWriter().write("Book Checked out");


			} else {
				response.getWriter()
						.write("Borrower has maxed out on book loans. Cannot checkout");
			}
		} catch (SQLException e) {
			response.getWriter().write("Cannot checkout book.Error occured");
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
