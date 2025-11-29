/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Konektor;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class Konektor {
    private String dbuser = "root";
    private String dbpassword = "";
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
    
    public void query(String SQLString){
        try {
            stmt.executeUpdate(SQLString);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Communication Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public Connection getConnection() {
        return con;
    }
}
