import java.sql.*;

public class Loader {
    private static final String URL = "jdbc:mysql://localhost:3306/skillbox?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "testtest";

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();

            String requestCourseId = "SELECT id FROM courses;";
            ResultSet res = statement.executeQuery(requestCourseId);

            System.out.println("Course_id  |  Avarage in month");
            ResultSet out;
            while (res.next()) {
                String courseId = res.getString("id");
                String sql = "SELECT (count / month) as 'avarage' from " +
                        "(SELECT COUNT(DISTINCT MONTH(subscription_date)) as 'month'," +
                                " COUNT(*) AS 'count' FROM " +
                        "subscriptions where course_id = + " + courseId + ") as counts;";

                out = statement1.executeQuery(sql);
                out.next();
                System.out.println(courseId + " | " + out.getString("avarage"));
            }

            statement.close();
            statement1.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
