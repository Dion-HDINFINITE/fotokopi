package Logic;

import Konektor.Konektor;
import java.sql.Connection;   
import java.sql.PreparedStatement; 
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
    
    public void updateSaldo(int id){
        String sql = "UPDATE saldo SET saldo = ?, transaksi_terakhir = ?, status = ? WHERE id_transaksi = ?";
        try {
            Konektor myConn = new Konektor();
            
            Connection conn = myConn.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, this.saldo);
            ps.setInt(2, this.transaksi_terakhir);
            ps.setInt(3, this.status);
            ps.setInt(4, id);
            
            ps.executeUpdate();
            
            System.out.println("Debug : Update saldo berhasil");
            
            ps.close();
            
        } catch (Exception e) {
            System.out.println("Error saat Update Saldo "+ e);
        }
    }
}
