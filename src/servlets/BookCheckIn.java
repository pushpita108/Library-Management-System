package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.MyConnection;

/**
 * Servlet implementation class Book Check In
 */
@WebServlet(name = "BookCheckIn", urlPatterns = { "jsp/BookCheckIn" })

public class BookCheckIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookCheckIn() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String isbn = request.getParameter("isbn");
		String id = request.getParameter("id");
		MyConnection obj = new MyConnection();
		Connection conn = null;
		try {
			conn = obj.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			String updateBook = "Update book set available='YES' where isbn='" + isbn + "'";
			PreparedStatement preparedStmt2 = conn.prepareStatement(updateBook);
			preparedStmt2.executeUpdate();

		} catch (SQLException e) {
			response.getWriter().write("System Error. Book not checked in");
			e.printStackTrace();
		}

		try {
			java.sql.Date todayDate = new java.sql.Date(new java.util.Date().getTime());
			String updateBookLoans = "Update Book_Loans set date_in='" + todayDate + "' where isbn='" + isbn
					+ "' AND card_id='" + id + "'";
			PreparedStatement preparedStmt = conn.prepareStatement(updateBookLoans);
			preparedStmt.executeUpdate();
			response.getWriter().write("Book succesfully checked in");
		} catch (SQLException e) {
			response.getWriter().write("System Error. Book not checked in");
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
