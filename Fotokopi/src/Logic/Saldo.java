package Logic;

import Konektor.Konektor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.Statement; 
import java.sql.SQLException;

public class Saldo {
    private int saldo;
    private int transaksi_terakhir;
    private int status;
    
    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getTransaksi_terakhir() {
        return transaksi_terakhir;
    }

    public void setTransaksi_terakhir(int transaksi_terakhir) {
        this.transaksi_terakhir = transaksi_terakhir;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public int ambilIdKeuanganTerakhir() {
        int idPK = 0;
        
        // Ambil ID Keuangan (PK) dan Saldo
        String sql = "SELECT id_keuangan, saldo FROM saldo ORDER BY id_keuangan DESC LIMIT 1";
        
        try {
            Konektor myConn = new Konektor();
            Connection conn = myConn.getConnection();
            Statement stmt = conn.createStatement(); // Sekarang ini aman karena sudah pakai java.sql.Statement
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                this.saldo = rs.getInt("saldo"); 
                idPK = rs.getInt("id_keuangan"); // Kita ambil Primary Key-nya
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error ambil saldo: " + e);
        }
        return idPK;
    }
    
    public void updateSaldoRestock(int idRestockBaru){ 
        String sql = "UPDATE saldo SET saldo = ?, transaksi_terakhir = NOW(), status = ?, id_transaksi = NULL, id_restock = ? WHERE id_keuangan = 1";

        try {
            Konektor myConn = new Konektor();
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, this.saldo);
            ps.setInt(2, this.status);
            ps.setInt(3, idRestockBaru);    

            int rows = ps.executeUpdate(); // Tambahkan int rows untuk cek debug
            if (rows > 0) {
                System.out.println("Debug : Update saldo pengeluaran berhasil");
            } else {
                System.out.println("Debug : Update GAGAL, ID Keuangan tidak ditemukan");
            }
            
            ps.close();

        } catch (Exception e) {
            System.out.println("Error saat Update Saldo Pengeluaran "+ e);
        }
    }
    
    public void updateSaldo(int id){
        String sql = "UPDATE saldo SET saldo = ?, status = ? WHERE id_transaksi = ?";
        try {
            Konektor myConn = new Konektor();
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, this.saldo);
            ps.setInt(2, this.status);
            ps.setInt(3, id); // 
            
            ps.executeUpdate();
            System.out.println("Debug : Update saldo berhasil");
            ps.close();
            
        } catch (Exception e) {
            System.out.println("Error saat Update Saldo "+ e);
        }
    }
}
