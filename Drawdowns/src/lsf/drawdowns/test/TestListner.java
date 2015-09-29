package lsf.drawdowns.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestListner")
public class TestListner extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TestListner() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Drawdown system on live...!!!!!!1");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Doget method running now........");
		db_connections obj=new db_connections();
		try {
			PrintWriter pwr=response.getWriter();
			pwr.print(obj.select());
			System.out.println(obj.select());
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("This is dopost method");
	}

}
