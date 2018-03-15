package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.MyConnection;

/**
 * Servlet implementation for adding a new borrower 
 */
@WebServlet(name = "AddBorrower", urlPatterns = { "jsp/AddBorrower" })
public class AddBorrower extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddBorrower() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		MyConnection obj = new MyConnection();
		Connection conn = null;
		try {
			conn = obj.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// Get the table count and prepare the CARD_ID value
			PreparedStatement preparedStmtCount;
			String rowcount = "";
			String countquery = "select count(*) from Borrower";
			preparedStmtCount = conn.prepareStatement(countquery);
			ResultSet theCount = preparedStmtCount.executeQuery();
			// append zeros to make 6 digit
			while (theCount.next()) {
				rowcount = "ID" + String.format("%06d", (theCount.getInt(1) + 1));
			}

			boolean okInsert = false;
			PreparedStatement checkUniqueSsn;
			String ssnexists = "Select card_id from Borrower where SSN='" + request.getParameter("ssn") + "'";
			checkUniqueSsn = conn.prepareStatement(ssnexists);
			ResultSet existsCount = checkUniqueSsn.executeQuery();
			if (existsCount.next()) {
				session.setAttribute("insertRes", "alreadyExists");
				session.setAttribute("cardid", existsCount.getString(1));
			} else {
				okInsert = true;
			}

			// Try to insert only if the SSN does not already exist
			if (okInsert) {
				// mysql insert borrower statement
				String query = "insert into Borrower (CARD_ID, SSN, first_name, last_name, ADDRESS, CITY, STATE, PHONE)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt;
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, rowcount);
				preparedStmt.setString(2, request.getParameter("ssn"));
				preparedStmt.setString(3, request.getParameter("fname"));
				preparedStmt.setString(4, request.getParameter("lname"));
				preparedStmt.setString(5, request.getParameter("address"));
				preparedStmt.setString(6, request.getParameter("city"));
				preparedStmt.setString(7, request.getParameter("state"));
				String phoneno = request.getParameter("phone");
				phoneno = "(" + phoneno.substring(0, 3) + ") " + phoneno.substring(3, 6) + "-" + phoneno.substring(6);
				preparedStmt.setString(8, phoneno);
				preparedStmt.execute();

				// Check if the borrower was inserted or not
				// and fetch the auto increment column value card_id
				PreparedStatement preparedStmt2;
				String query2 = "Select CARD_ID from Borrower where SSN='" + request.getParameter("ssn") + "'";
				preparedStmt2 = conn.prepareStatement(query2);
				ResultSet rs = preparedStmt2.executeQuery();
				String card_id = "";
				while (rs.next()) {
					card_id = rs.getString(1);
				}
				System.out.println("card id : " + card_id);
				if (!card_id.isEmpty()) {
					session.setAttribute("insertRes", "added");
					session.setAttribute("cardid", card_id);
				} else {
					session.setAttribute("insertRes", "notAdded");
				}
			}
		} catch (SQLException e) {
			session.setAttribute("insertRes", "notAdded");
			e.printStackTrace();
		}

		response.sendRedirect("jsp/index.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
