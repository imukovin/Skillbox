import java.sql.*;

public class DBConnection
{
    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "root";

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                    "?user=" + dbUser + "&password=" + dbPass + "&useSSL=false" +
                            "&serverTimezone=GMT" +
                            "&rewriteBatchedStatements=true");
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void addIndex() throws SQLException {
        String query = "CREATE INDEX name_date ON voter_count (name(50), birthDate)";
        DBConnection.getConnection().createStatement().execute(query);

    }

    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, COUNT(*) as `count` FROM voter_count " +
                "GROUP BY name, birthDate HAVING `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") +
                    " (" + rs.getString("birthDate") + ") - " +
                    rs.getInt("count"));
        }
    }
}
