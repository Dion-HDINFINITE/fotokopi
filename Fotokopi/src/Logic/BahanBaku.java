package Logic;

import Konektor.Konektor;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BahanBaku {
    String id_bahan;
    String nama_bahan;
    int stok;
    int plusminStok;
    int harga_jual;
    int harga_beli;
    
    public String getId_bahan() {
        return id_bahan;
    }

    public void setId_bahan(String id_bahan) {
        this.id_bahan = id_bahan;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }

    public int getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(int harga_beli) {
        this.harga_beli = harga_beli;
    }
    
    public void insertBB(){
        String sql = "INSERT INTO bahan_baku (id_bahan, nama_bahan, stok, harga_jual, harga_beli) VALUES(?,?,?,?,?)";
        
        try {
            Konektor myConn = new Konektor();
            
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, this.id_bahan);
            ps.setString(2, this.nama_bahan);
            ps.setInt(3, this.stok);
            ps.setInt(4, this.harga_jual);
            ps.setInt(4, this.harga_beli);
            
            ps.executeUpdate();
            
            System.out.println("Debug : Insert bahan baku berhasil");
            
            ps.close();
            
        } catch (Exception e) {
            System.out.println("Error saat Insert Bahan Baku "+ e.getMessage());
        }
    }
    
    public void updateStok(){
        String sql = "UPDATE bahan_baku SET stok = ? WHERE nama_bahan = ?";
        
        try {
            Konektor myConn = new Konektor();
            
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, this.stok);
            ps.setString(2, this.nama_bahan);
            
            ps.executeUpdate();
            
            System.out.println("Debug : Update stok berhasil dilakukan");
            
            ps.close();
            
        } catch (Exception e) {
            System.out.println("Error saat Update stok bahan baku "+ e.getMessage());
        }
    }
    
    public void deleteStok() {
        String sql = "DELETE FROM bahan_baku WHERE id_bahan = '" + this.id_bahan + "'";
        try {
            Konektor myConn = new Konektor();
            
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saat menghapus stok " + e.getMessage());
        }
    }
}
