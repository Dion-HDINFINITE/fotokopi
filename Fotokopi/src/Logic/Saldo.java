package Logic;

import Konektor.Konektor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.Statement; 

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
        
        String sql = "SELECT id_keuangan, saldo FROM saldo ORDER BY id_keuangan DESC LIMIT 1";
        
        try {
            Konektor myConn = new Konektor();
            Connection conn = myConn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                this.saldo = rs.getInt("saldo"); 
                idPK = rs.getInt("id_keuangan");
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error ambil saldo: " + e);
        }
        return idPK;
    }
    
    public void updateSaldoRestock(int idRestockBaru){ 
        String sql = "UPDATE saldo SET id_transaksi = NULL, saldo = ?, transaksi_terakhir = NOW(), status = ?, id_transaksi = NULL, id_restock = ? WHERE id_keuangan = 1";

        try {
            Konektor myConn = new Konektor();
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, this.saldo);
            ps.setInt(2, this.status);
            ps.setInt(3, idRestockBaru);    

            int rows = ps.executeUpdate();
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
    
    public void updateSaldo(int nominal, String tipe, Integer idTransaksi) {

    String sql = "UPDATE saldo SET saldo = ?, transaksi_terakhir = NOW(), "
               + "status = ?, id_transaksi = ? "
               + "WHERE id_keuangan = 1";

    try {
        Konektor myConn = new Konektor();
        Connection conn = myConn.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        int saldoBaru;

        if (tipe.equalsIgnoreCase("keluar")) {
            saldoBaru = this.saldo - nominal;
            this.status = 1;
            idTransaksi = null;
        } 
        else {
            saldoBaru = this.saldo + nominal;
            this.status = 2;
        }

        ps.setInt(1, saldoBaru);
        ps.setInt(2, this.status);
        ps.setObject(3, idTransaksi);

        ps.executeUpdate();
        ps.close();

        System.out.println("Debug: Update saldo (" + tipe + ") berhasil");

    } catch (Exception e) {
        System.out.println("Error Update Saldo: " + e);
    }
}

}
