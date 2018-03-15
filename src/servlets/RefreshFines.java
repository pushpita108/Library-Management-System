package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.MyConnection;
import valueObj.FineFields;

/**
 * Servlet implementation class Refresh Fines
 */
@WebServlet(name = "RefreshFines", urlPatterns = { "jsp/RefreshFines" })

public class RefreshFines extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RefreshFines() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		FineFields fineRes = new FineFields();
		MyConnection obj = new MyConnection();
		Connection conn = null;
		try {
			conn = obj.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String loansForFine = "select loan_id, isbn, card_id, date_out, due_date, date_in from book_loans  where ( date_in is NULL and due_date < NOW() ) OR ( date_in IS NOT NULL and due_date < date_in ) ;";
			PreparedStatement ps = conn.prepareStatement(loansForFine);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Date dueDate = new SimpleDateFormat("yyyy-M-dd").parse((String) rs.getString(5));
				String today = new SimpleDateFormat("yyyy-M-dd").format(new Date());
				Date dateIn;
				if (rs.getString(6) == null) {
					// If fine is not paid yet, update fine as per today's balance
					dateIn = new SimpleDateFormat("yyyy-M-dd").parse(today);
				} else {
					dateIn = new SimpleDateFormat("yyyy-M-dd").parse((String) rs.getString(6));
				}

				// calculate fine amount
				long diffDays = Math.abs(dueDate.getTime() - dateIn.getTime()) / (24 * 60 * 60 * 1000);
				float fine = (float) (diffDays * (0.25));

				// Check if there is an existing loan id record
				String existingFines = "select loan_id, fine_amt, paid from fines where loan_id='" + rs.getString(1)
						+ "'";
				System.out.println(existingFines);
				PreparedStatement ps2 = conn.prepareStatement(existingFines);
				ResultSet existingFinesRes = ps2.executeQuery();
				// if (existingFinesRes.next() && existingFinesRes.getBoolean(3) == false) {
					// If fine record already exists, update the record
				if (existingFinesRes.next()) {
						PreparedStatement ps3 = conn.prepareStatement(
								"update fines set fine_amt='" + fine + "' where loan_id='" + rs.getString(1) + "';");
						System.out.println(ps3);
						ps3.executeUpdate();
						// If not, add a new row in the fine table
					} else {
						String query = "insert into fines (loan_id, fine_amt, paid)" + " values (?, ?, ?)";
						PreparedStatement ps4 = conn.prepareStatement(query);
						ps4.setString(1, rs.getString(1));
						ps4.setFloat(2, fine);
						ps4.setBoolean(3, false);
					System.out.println(ps4);
						ps4.execute();

					}
				// }

				ArrayList<Float> fines = new ArrayList<Float>();
				ArrayList<String> cardId = new ArrayList<String>();
				
				String displayFines = "select bl.card_id, sum(f.fine_amt) as Total_Fine from fines f join book_loans bl on bl.loan_id = f.loan_id "
						+ "where f.paid = 0 group by bl.card_id;";
				System.out.println(displayFines);
				PreparedStatement ps5 = conn.prepareStatement(displayFines);
				ResultSet rs5 = ps5.executeQuery();

				if (rs5.wasNull()) {
					session.setAttribute("refreshRes", "empty");
				}
				else {
					while (rs5.next()) {
						cardId.add(rs5.getString(1));
						fines.add(rs5.getFloat(2));
					}
					if (cardId != null) {
						fineRes.setCardId(cardId);
						fineRes.setFineAmt(fines);
					}
					session.setAttribute("refreshRes", "success");
				}
			}
			session.setAttribute("refreshedFinesRes", fineRes);
			response.sendRedirect("jsp/index.jsp");

		} catch (SQLException e) {
			session.setAttribute("refreshRes", "failed");
			e.printStackTrace();
		} catch (ParseException e) {
			session.setAttribute("refreshRes", "failed");
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
