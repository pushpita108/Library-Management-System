package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.MyConnection;
import valueObj.CheckInFields;

/**
 * Servlet implementation class Book Check in Search
 */
@WebServlet(name = "BookCheckInSearch", urlPatterns = { "jsp/BookCheckInSearch" })

public class BookCheckInSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BookCheckInSearch() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String searchval = request.getParameter("checkinsearchfield");

		MyConnection obj = new MyConnection();
		Connection conn = null;
		try {
			conn = obj.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		CheckInFields searchchcheckin = new CheckInFields();
		ArrayList<String> isbn = new ArrayList<String>();
		ArrayList<String> fname = new ArrayList<String>();
		ArrayList<String> lname = new ArrayList<String>();
		ArrayList<String> borrowerid = new ArrayList<String>();

        try {
			PreparedStatement ps = conn.prepareStatement(
					"select bl.isbn, bl.card_id, b.first_name, b.last_name from book_loans bl join borrower b where bl.Card_id=b.Card_id AND (bl.isbn LIKE '%"
							+ searchval + "%' OR bl.card_id LIKE '%" + searchval + "%' OR b.first_name LIKE '%"
							+ searchval + "%' OR b.last_name LIKE '%" + searchval + "%') AND date_in IS NULL");
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
            {
				isbn.add(rs.getString(1));
				borrowerid.add(rs.getString(2));
				fname.add(rs.getString(3));
				lname.add(rs.getString(4));
            }
			if (isbn != null && borrowerid != null && fname != null && lname != null) {
				searchchcheckin.setIsbn(isbn);
				searchchcheckin.setBorrowerId(borrowerid);
				searchchcheckin.setfname(fname);
				searchchcheckin.setlname(lname);
			}
			
			if (searchchcheckin != null && !isbn.isEmpty()) {
				session.setAttribute("searchcheckinval", searchchcheckin);
				session.setAttribute("searchcheckin", "found");
			} else {
				session.setAttribute("searchcheckin", "empty");
			}
		
        } catch (SQLException e) {
			e.printStackTrace();
		}
		
        response.sendRedirect("jsp/index.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
