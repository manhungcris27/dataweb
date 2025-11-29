package murach.sql;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import murach.data.ConnectionPool;

@WebServlet("/sqlGateway")
public class SQLGatewayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/murach?useSSL=false&serverTimezone=UTC";


    String user = "murach_user";
    String pass = "sesame";

    Connection conn = DriverManager.getConnection(url, user, pass);
    System.out.println("Kết nối MySQL thành công!");

    conn.close();
} catch (Exception e) {
    e.printStackTrace();
}


        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";

        try {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();

    Statement statement = connection.createStatement();

    sqlStatement = sqlStatement.trim();

    if (sqlStatement.length() >= 6) {
        String sqlType = sqlStatement.substring(0, 6);

        if (sqlType.equalsIgnoreCase("select")) {
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            sqlResult = SQLUtil.getHtmlTable(resultSet);
            resultSet.close();
        } else {
            int i = statement.executeUpdate(sqlStatement);

            if (i == 0) { 
                sqlResult = "<p>The statement executed successfully.</p>";
            } else {
                sqlResult = "<p>The statement executed successfully.<br>"
                        + i + " row(s) affected.</p>";
            }
        }
    }

    statement.close();
    pool.freeConnection(connection);

} catch (SQLException e) {
    sqlResult = "<p>Error executing the SQL statement:<br>"
               + e.getMessage() + "</p>";
}


        // save to session
        HttpSession session = request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        getServletContext()
                .getRequestDispatcher("/index.jsp")
                .forward(request, response);
    }
}
