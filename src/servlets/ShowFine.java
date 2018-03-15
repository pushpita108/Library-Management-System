package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.MyConnection;
import valueObj.FineFields;

/**
 * Servlet implementation class PayFine
 */
@WebServlet(name = "ShowFine", urlPatterns = { "jsp/ShowFine" })

public class ShowFine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowFine() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String cardid = request.getParameter("payfinesearchfield");
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
			PreparedStatement ps = conn.prepareStatement(
					"Select card_id, sum(fine_amt) from fines f join book_loans bl on f.loan_id = bl.loan_id where date_in is not null and paid=0 "
							+ " group by card_id having card_id like '%" + cardid + "%'");
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			ArrayList<Float> fines = new ArrayList<Float>();
			ArrayList<String> cardId = new ArrayList<String>();
			// ArrayList<String> dateIn = new ArrayList<String>();
			while (rs.next()) {
				cardId.add(rs.getString(1));
				fines.add(rs.getFloat(2));
				// dateIn.add(rs.getString(3));
			}
			if (cardId != null) {
				fineRes.setCardId(cardId);
				fineRes.setFineAmt(fines);
				// fineRes.setDateIn(dateIn);
			}

			if (fineRes != null && !cardId.isEmpty()) {
				session.setAttribute("showFine", "success");
				session.setAttribute("showFineRes", fineRes);
			} else {
				session.setAttribute("showFine", "failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("jsp/index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
