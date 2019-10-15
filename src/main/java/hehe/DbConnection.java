package hehe;

import java.sql.*;

public class DbConnection {

    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        return ourInstance;
    }


    private final String JDBC = "jdbc:derby:./DB/loginGitLab_db;create=true";
    public final String TABLE = "APP.LOGINGITLAB_ZNAKI_DROGOWE_TB";

    public Connection conn = null;
    public Statement statement = null;

    private DbConnection() {

    }

    public static void main(String[] args) {

        DbConnection db = DbConnection.getInstance();

        db.open();

        try {
            db.statement = db.conn.createStatement();
            ResultSet rs = db.statement.executeQuery("select * from " + db.TABLE);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnLabel(i) + "\t\t");
            }

            System.out.println("");

            while(rs.next()) {
//                System.out.println(rs.getRow());
                for(int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.close();
    }

    public void open() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(JDBC);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if(statement != null) {
                statement.close();
            }
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
