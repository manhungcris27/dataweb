package murach.sql;

import java.sql.*;

public class SQLUtil {

    public static String getHtmlTable(ResultSet results)
            throws SQLException {

        StringBuilder htmlTable = new StringBuilder();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        htmlTable.append("<table>");

        // Header row
        htmlTable.append("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            htmlTable.append("<th>")
                     .append(metaData.getColumnName(i))
                     .append("</th>");
        }
        htmlTable.append("</tr>");

        // Data rows
        while (results.next()) {
            htmlTable.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                htmlTable.append("<td>")
                         .append(results.getString(i))
                         .append("</td>");
            }
            htmlTable.append("</tr>");
        }

        htmlTable.append("</table>");

        return htmlTable.toString();
    }
}
