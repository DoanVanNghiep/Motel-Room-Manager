package fita.vnua.edu.vn.MotelRoomManager.Until;

public class MySQL {
    public static final String FIND_LOGIN = "select * from user\n" +
            "where username = ? and password = ?";
    public static final String FIND_REGISTER = "select * from user\n" +
            "where username = ?";
}
