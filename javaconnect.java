package dashboard;

import java.sql.*;
import javax.swing.*;


//Justyna Bucko

public class javaconnect {
    
    Connection conn = null;
    
    //setting connection with databse
    public static Connection ConnecrDb(){
        
        try{
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:cordis2.db";
        Connection conn = DriverManager.getConnection(url);
        JOptionPane.showMessageDialog(null,"Connection Established");
        return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
}
