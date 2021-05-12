package sample.models;
import java.sql.Connection;
import java.sql.DriverManager;

public class conector {
    private static String server    = "jdbc:mysql://localhost:3306/musica";
    private static String user      = "root";
    private static String password  = "root";
    public  static Connection conn;

    public static Connection getConection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(server, user, password);
            return conn;
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
}


