/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import Konektor.Konektor;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author ACER
 */
public class RiwayatStok {
    String id_bahan;
    int jumlah;
    int harga_total;
    String keterangan;
    
    public String getId_bahan() {
        return id_bahan;
    }

    public void setId_bahan(String id_bahan) {
        this.id_bahan = id_bahan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga_total() {
        return harga_total;
    }

    public void setHarga_total(int harga_total) {
        this.harga_total = harga_total;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public void tambahRiwayat(){
        String sql = "INSERT INTO riwayat_stok (id_bahan, jumlah, harga_total, keterangan) VALUES(?,?,?,?)";
        
        try {
            Konektor myConn = new Konektor();
            
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, this.id_bahan);
            ps.setInt(2, this.jumlah);
            ps.setInt(3, this.harga_total);
            ps.setString(4, this.keterangan);
            
            ps.executeUpdate();
            
            System.out.println("Debug : Insert riwayat stok berhasil");
            
            ps.close();
            
        } catch (Exception e) {
            System.out.println("Error saat Insert riwayat stok "+ e.getMessage());
        }
    }
}
