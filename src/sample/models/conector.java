package sample.models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conector {
    private static String server    = "localhost:1433";
    private static String user      = "sa";
    private static String password  = "martinez56.";
    private static String db        = "db_escuela";
    private static String loginTime = "30";
    public  static Connection conn = null;

    public static Connection getConection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://"+server+";database="+db+";user="+user+";password="+password+";loginTimeout="+loginTime+";";
            conn = DriverManager.getConnection(url);
            return conn;
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
}
