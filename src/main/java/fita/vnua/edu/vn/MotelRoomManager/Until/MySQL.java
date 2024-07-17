package fita.vnua.edu.vn.MotelRoomManager.Until;

public class MySQL {
    public static final String FIND_LOGIN = "select * from user\n" +
            "where username = ? and password = ?";
    public static final String FIND_REGISTER = "select * from user\n" +
            "where username = ?";
    public static final String MONTHLY_REVENUE = "SELECT DATE_FORMAT(o.check_in_date, '%m-%Y') AS month, SUM(o.total_cost) AS revenue \n" +
            "            FROM `order` o \n" +
            "            WHERE o.status = 4 \n" +
            "            AND MONTH(o.check_in_date) = :month \n" +
            "            AND YEAR(o.check_in_date) = :year \n" +
            "            GROUP BY DATE_FORMAT(o.check_in_date, '%m-%Y')";
}
