package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.MyConnection;
import valueObj.SearchFields;

/**
 * Servlet implementation for Search
 */
@WebServlet(name = "BookSearch", urlPatterns = { "jsp/BookSearch" })

public class BookSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookSearch() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        HttpSession session=request.getSession();  
		String sc = request.getParameter("searchtextboxname");
		MyConnection obj = new MyConnection();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SearchFields searchVal = new SearchFields();
		ArrayList<String> isbn = new ArrayList<String>();
		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> available = new ArrayList<String>();
		Map<String, String> bookAndAuthors = new HashMap<>();

        try {
			// PreparedStatement ps = con.prepareStatement(
			// "select b.isbn, b.title, b.Available, a.name from book b join book_authors ba
			// on b.isbn = ba.isbn join authors a on a.author_id = ba.author_id where
			// (b.isbn LIKE '%"
			// + sc + "%' OR b.title LIKE '%" + sc + "%' OR a.name LIKE '%" + sc + "%') ");

			PreparedStatement ps = con.prepareStatement(
					"select b.isbn, b.title, b.Available, a.name from book b join book_authors ba on b.isbn = ba.isbn join authors a on a.author_id = ba.author_id where b.isbn in"
							+ " ( select b.isbn from book b join book_authors ba on b.isbn = ba.isbn join authors a on a.author_id = ba.author_id where b.isbn LIKE '%"
							+ sc + "%' OR b.title LIKE '%" + sc + "%' OR a.name LIKE '%" + sc + "%') ");

			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				String book = (String) rs.getString(1);
				String author = "";
				// As some book authors are null
				if (!rs.getString(4).isEmpty()) {
					author = (String) rs.getString(4);
					if (author.contains("&")) {
						author = author.replaceAll("&amp;", "and");
					}
				}

				if (!bookAndAuthors.containsKey(book)) {
					bookAndAuthors.put(book, author);
					isbn.add(rs.getString(1));
					title.add(rs.getString(2));
					available.add(rs.getString(3));
				}
				else {
					bookAndAuthors.put(book, bookAndAuthors.get(book) + ", " + author);
				}

            }
			System.out.println(bookAndAuthors);
			System.out.println(bookAndAuthors.size() + " " + title.size() + " " + available.size());
			// isbn = new ArrayList<String>(bookAndAuthors.keySet());
			for (int i = 0; i < isbn.size(); i++) {
				authors.add(bookAndAuthors.get(isbn.get(i)));
			}
			// authors = new ArrayList<String>(bookAndAuthors.values());
			
			if (isbn != null) {
				searchVal.setIsbn(isbn);
				searchVal.setTitle(title);
				searchVal.setAvailable(available);
				searchVal.setName(authors);
			}
			
			if (searchVal != null && !isbn.isEmpty()) {
				session.setAttribute("searchvalues", searchVal);
				session.setAttribute("searchRes", "gotResults");
		}
		else{
				session.setAttribute("searchRes", "emptyResultSet");
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
