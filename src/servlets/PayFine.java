package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.MyConnection;

/**
 * Servlet implementation class PayFine
 */
@WebServlet(name = "PayFine", urlPatterns = { "jsp/PayFine" })

public class PayFine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PayFine() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cardid = request.getParameter("cardid");
		response.setContentType("text/html;charset=UTF-8");

		MyConnection obj = new MyConnection();
		Connection conn = null;
		try {
			conn = obj.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String updateFinePayment = "update fines f join book_loans bl on bl.loan_id = f.loan_id set paid=1 where card_id like '%"
					+ cardid + "%'";

			System.out.println(updateFinePayment);
			PreparedStatement ps = conn.prepareStatement(updateFinePayment);
			ps.executeUpdate();
			response.getWriter().write("Payment Successful.");
		} catch (Exception e) {
			response.getWriter().write("System error. Payment failed.");
			e.printStackTrace();
		}

		response.sendRedirect("jsp/index.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
