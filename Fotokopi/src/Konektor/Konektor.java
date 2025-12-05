package Konektor;
import java.sql.*;
import javax.swing.JOptionPane;

public class Konektor {
    private String dbuser = "root";
    private String dbpassword = "mysql";
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    
    public Konektor(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "" + e.getMessage(), "JDBC Driver Error", JOptionPane.WARNING_MESSAGE);
        } 
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/fotokopidb", dbuser, dbpassword);
            stmt = (Statement) con.createStatement();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Connection error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public ResultSet getData(String SQLString){
        try {
            rs = stmt.executeQuery(SQLString);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(),"Communication Error", JOptionPane.WARNING_MESSAGE); 
        }
        return rs;
    }
    
    public boolean query(String SQLString){
        try {
            stmt.executeUpdate(SQLString);
            return true;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Communication Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public boolean isConnected() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public void closeConnection() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}