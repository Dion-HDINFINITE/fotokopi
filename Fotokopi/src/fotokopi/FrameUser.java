package fotokopi;

import Konektor.Konektor;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrameUser extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrameUser.class.getName());
    private int idUser;
    private String namaUser;
    private DefaultTableModel modelDaftarBarang;
    private int totalTransaksi = 0;

    public FrameUser(int idUser, String namaUser) {
        this.idUser = idUser;
        this.namaUser = namaUser;
        initComponents();
        
        styleTableDashboard();
        loadComboBox();
        tampilKinerjaMingguan();
        
        modelDaftarBarang = (DefaultTableModel) daftarbTable.getModel();
        modelDaftarBarang.setRowCount(0);
        
        javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();
        group.add(fotokopiButton);
        group.add(jilidButton);
        group.add(barangButton);
        fotokopiButton.setSelected(true);
    }
    
    private void loadComboBox(){
        comboJenisb.removeAllItems();
        comboJenisb.addItem("-- Pilih Bahan --");
        
        String sql = "SELECT nama_bahan FROM bahan_baku";
    
        Konektor myConn = new Konektor();
        java.sql.ResultSet rs;

        try {
            rs = myConn.getData(sql);

            while (rs.next()) {
                String nama = rs.getString("nama_bahan");
                comboJenisb.addItem(nama);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error load combo box: " + e.getMessage());
        }
    }

    private void styleTableDashboard() {
        javax.swing.table.JTableHeader header = tabelKaryawan.getTableHeader();
        header.setOpaque(false);

        tabelKaryawan.setBackground(new java.awt.Color(255, 250, 255));
        tabelKaryawan.setForeground(new java.awt.Color(10, 36, 99));
        tabelKaryawan.setGridColor(new java.awt.Color(200, 200, 200));
        tabelKaryawan.setRowHeight(30);

        tabelKaryawan.setSelectionBackground(new java.awt.Color(255, 250, 255));
        tabelKaryawan.setSelectionForeground(new java.awt.Color(10, 36, 99));

        jScrollPane1.getViewport().setBackground(new java.awt.Color(255, 250, 255));

        tabelKaryawan.setShowVerticalLines(false);

        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                label.setBackground(new java.awt.Color(10, 36, 99)); 
                label.setForeground(new java.awt.Color(255, 250, 255));
                label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                label.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
                label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

                return label;
            }
        });

        header.setReorderingAllowed(false);
    }
    
    public void tampilKinerjaMingguan() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tabelKaryawan.getModel();
        model.setRowCount(0); 

        String sql = "SELECT id_transaksi, jenis_layanan, tanggal, total_biaya " +
                     "FROM transaksi " +
                     "WHERE id_user = " + idUser + " " +
                     "AND tanggal >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                     "ORDER BY tanggal DESC";
        
        java.sql.ResultSet rs;
        Konektor myConn = new Konektor();

        java.text.SimpleDateFormat formatTanggal = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
        java.text.NumberFormat kursIDR = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id", "ID"));

        try {
            rs = myConn.getData(sql);

            while (rs.next()) {
                int idTransaksi = rs.getInt("id_transaksi");
                String jenisLayanan = rs.getString("jenis_layanan");
                java.sql.Timestamp tanggal = rs.getTimestamp("tanggal");
                double totalBiaya = rs.getDouble("total_biaya");

                Object[] baris = {
                    idTransaksi,
                    jenisLayanan,
                    formatTanggal.format(tanggal),
                    kursIDR.format(totalBiaya)
                };

                model.addRow(baris);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Debug : Error sql saat mengambil data kinerja " + e.getMessage());
        }
    }
    
    private void hitungTotal() {
        totalTransaksi = 0;
        for (int i = 0; i < modelDaftarBarang.getRowCount(); i++) {
            String namaBarang = modelDaftarBarang.getValueAt(i, 0).toString();
            int jumlah = Integer.parseInt(modelDaftarBarang.getValueAt(i, 1).toString());
            
            int hargaSatuan = getHargaBarang(namaBarang);
            totalTransaksi += (hargaSatuan * jumlah);
        }
        
        java.text.NumberFormat kursIDR = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id", "ID"));
        totalLabel.setText("TOTAL : " + kursIDR.format(totalTransaksi));
    }
    
    private int getHargaBarang(String namaBarang) {
        int harga = 0;
        String sql = "SELECT harga_jual FROM bahan_baku WHERE nama_bahan = '" + namaBarang + "'";
        Konektor myConn = new Konektor();
        
        try {
            java.sql.ResultSet rs = myConn.getData(sql);
            if (rs.next()) {
                harga = rs.getInt("harga_jual");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error get harga: " + e.getMessage());
        }
        
        return harga;
    }
    
    private String getIdBahan(String namaBarang) {
        String idBahan = "";
        String sql = "SELECT id_bahan FROM bahan_baku WHERE nama_bahan = '" + namaBarang + "'";
        Konektor myConn = new Konektor();
        
        try {
            java.sql.ResultSet rs = myConn.getData(sql);
            if (rs.next()) {
                idBahan = rs.getString("id_bahan");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error get id bahan: " + e.getMessage());
        }
        
        return idBahan;
    }
    
    private void kurangiStok(String namaBarang, int jumlah) {
        String sql = "UPDATE bahan_baku SET stok = stok - ? WHERE nama_bahan = ?";
        Konektor myConn = new Konektor();
        
        try {
            java.sql.Connection conn = myConn.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, jumlah);
            ps.setString(2, namaBarang);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error kurangi stok: " + e.getMessage());
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ParentCard = new javax.swing.JPanel();
        panelKinerja = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKaryawan = new javax.swing.JTable();
        panelTransaksi = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        comboJenisb = new javax.swing.JComboBox();
        btnReset = new javax.swing.JButton();
        tambahButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        plusButton = new javax.swing.JButton();
        minusButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        daftarbTable = new javax.swing.JTable();
        totalLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        simpanButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        fotokopiButton = new javax.swing.JRadioButton();
        jilidButton = new javax.swing.JRadioButton();
        barangButton = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dashboardButton = new javax.swing.JButton();
        transaksiButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ParentCard.setBackground(new java.awt.Color(255, 250, 255));
        ParentCard.setLayout(new java.awt.CardLayout());

        panelKinerja.setBackground(new java.awt.Color(255, 250, 255));
        panelKinerja.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panelKinerjaComponentShown(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(10, 36, 99));
        jLabel2.setText("Dashboard");

        jPanel4.setBackground(new java.awt.Color(10, 36, 99));
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(10, 36, 99));
        jLabel5.setText("Kinerja mingguan mu");

        tabelKaryawan.setBackground(new java.awt.Color(255, 250, 255));
        tabelKaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id transaksi", "Jenis transaksi", "Tanggal Transaksi", "Total Transaksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelKaryawan.setGridColor(new java.awt.Color(255, 250, 255));
        tabelKaryawan.setSelectionBackground(new java.awt.Color(255, 250, 255));
        tabelKaryawan.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tabelKaryawanComponentShown(evt);
            }
        });
        jScrollPane1.setViewportView(tabelKaryawan);

        javax.swing.GroupLayout panelKinerjaLayout = new javax.swing.GroupLayout(panelKinerja);
        panelKinerja.setLayout(panelKinerjaLayout);
        panelKinerjaLayout.setHorizontalGroup(
            panelKinerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
            .addGroup(panelKinerjaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelKinerjaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelKinerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        panelKinerjaLayout.setVerticalGroup(
            panelKinerjaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKinerjaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ParentCard.add(panelKinerja, "card2");

        panelTransaksi.setBackground(new java.awt.Color(255, 250, 255));
        panelTransaksi.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panelTransaksiComponentShown(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(10, 36, 99));
        jLabel3.setText("Dashboard Transaksi");

        jPanel3.setBackground(new java.awt.Color(10, 36, 99));
        jPanel3.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(10, 36, 99));
        jLabel6.setText("Tambah Transaksi");

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel7.setForeground(new java.awt.Color(10, 36, 99));
        jLabel7.setText("Jenis Barang");

        comboJenisb.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                comboJenisbComponentShown(evt);
            }
        });
        comboJenisb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJenisbActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(137, 26, 54));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 250, 255));
        btnReset.setText("RESET");
        btnReset.setBorder(null);
        btnReset.setBorderPainted(false);
        btnReset.setContentAreaFilled(false);
        btnReset.setFocusPainted(false);
        btnReset.setOpaque(true);
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnResetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnResetMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnResetMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnResetMouseReleased(evt);
            }
        });
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        tambahButton.setText("tambah");
        tambahButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahButtonActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(10, 36, 99));
        jLabel10.setText("Jumlah Satuan");

        jTextField1.setText("0");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        plusButton.setText("+");
        plusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusButtonActionPerformed(evt);
            }
        });

        minusButton.setText("-");
        minusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusButtonActionPerformed(evt);
            }
        });

        daftarbTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "nama", "jumlah"
            }
        ));
        jScrollPane4.setViewportView(daftarbTable);

        totalLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totalLabel.setForeground(new java.awt.Color(10, 36, 99));
        totalLabel.setText("TOTAL :");

        jLabel11.setForeground(new java.awt.Color(10, 36, 99));
        jLabel11.setText("Daftar Barang");

        simpanButton.setText("SIMPAN");
        simpanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanButtonActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(10, 36, 99));
        jLabel8.setText("Jenis Layanan");

        fotokopiButton.setText("fotokopi");

        jilidButton.setText("jilid");

        barangButton.setText("barang");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(comboJenisb, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(plusButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minusButton))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tambahButton, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(fotokopiButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jilidButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barangButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(totalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(161, 161, 161)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboJenisb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(plusButton)
                            .addComponent(minusButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tambahButton)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fotokopiButton)
                            .addComponent(jilidButton)
                            .addComponent(barangButton)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(simpanButton))
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totalLabel)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout panelTransaksiLayout = new javax.swing.GroupLayout(panelTransaksi);
        panelTransaksi.setLayout(panelTransaksiLayout);
        panelTransaksiLayout.setHorizontalGroup(
            panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
            .addGroup(panelTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTransaksiLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel6)
                        .addGap(0, 378, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelTransaksiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTransaksiLayout.setVerticalGroup(
            panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        ParentCard.add(panelTransaksi, "card3");

        jPanel1.setBackground(new java.awt.Color(10, 36, 99));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 250, 255));
        jLabel1.setText("Dashboard User");

        dashboardButton.setBackground(new java.awt.Color(10, 36, 99));
        dashboardButton.setForeground(new java.awt.Color(255, 250, 255));
        dashboardButton.setText("Dashboard");
        dashboardButton.setBorder(null);
        dashboardButton.setBorderPainted(false);
        dashboardButton.setContentAreaFilled(false);
        dashboardButton.setFocusPainted(false);
        dashboardButton.setOpaque(true);
        dashboardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dashboardButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dashboardButtonMouseExited(evt);
            }
        });
        dashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardButtonActionPerformed(evt);
            }
        });

        transaksiButton.setBackground(new java.awt.Color(10, 36, 99));
        transaksiButton.setForeground(new java.awt.Color(255, 250, 255));
        transaksiButton.setText("Catat Transaksi");
        transaksiButton.setBorder(null);
        transaksiButton.setBorderPainted(false);
        transaksiButton.setContentAreaFilled(false);
        transaksiButton.setFocusPainted(false);
        transaksiButton.setOpaque(true);
        transaksiButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                transaksiButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                transaksiButtonMouseExited(evt);
            }
        });
        transaksiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiButtonActionPerformed(evt);
            }
        });

        logoutButton.setBackground(new java.awt.Color(10, 36, 99));
        logoutButton.setForeground(new java.awt.Color(255, 250, 255));
        logoutButton.setText("LOGOUT");
        logoutButton.setBorder(null);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButtonMouseExited(evt);
            }
        });
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(transaksiButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                .addComponent(dashboardButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(logoutButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(57, 57, 57)
                .addComponent(dashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transaksiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 103, Short.MAX_VALUE)
                .addComponent(ParentCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 506, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ParentCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardButtonMouseEntered
        dashboardButton.setBackground(new java.awt.Color(137, 26, 54));
    }//GEN-LAST:event_dashboardButtonMouseEntered

    private void dashboardButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardButtonMouseExited
        dashboardButton.setBackground(new java.awt.Color(10,36,99));
    }//GEN-LAST:event_dashboardButtonMouseExited

    private void transaksiButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiButtonMouseEntered
        transaksiButton.setBackground(new java.awt.Color(137, 26, 54));
    }//GEN-LAST:event_transaksiButtonMouseEntered

    private void transaksiButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiButtonMouseExited
        transaksiButton.setBackground(new java.awt.Color(10,36,99));
    }//GEN-LAST:event_transaksiButtonMouseExited

    private void dashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardButtonActionPerformed
        java.awt.CardLayout cl = (java.awt.CardLayout) (ParentCard.getLayout());
        cl.show(ParentCard, "card2");
    }//GEN-LAST:event_dashboardButtonActionPerformed

    private void transaksiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiButtonActionPerformed
        java.awt.CardLayout cl = (java.awt.CardLayout) (ParentCard.getLayout());
        cl.show(ParentCard, "card3");
    }//GEN-LAST:event_transaksiButtonActionPerformed

    public void tampilStokBarang() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tabelKaryawan.getModel();
        model.setRowCount(0); 

        String sql = "SELECT nama_bahan, stok, harga_jual, harga_beli FROM bahan_baku;";
        java.sql.ResultSet rs;
        Konektor myConn = new Konektor();

        try {
            rs = myConn.getData(sql);

            java.text.NumberFormat kursIDR = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id", "ID"));
            while (rs.next()) {
                double hJual = rs.getDouble("harga_jual");
                double hBeli = rs.getDouble("harga_beli");

                Object[] baris = {
                    rs.getString("nama_bahan"),
                    rs.getString("stok"),           
                    kursIDR.format(hJual),
                    kursIDR.format(hBeli)  
                };

                model.addRow(baris);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Debug : Error sql saat mengambil data stok " + e.getMessage());
        }
    }
    
    private void panelKinerjaComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelKinerjaComponentShown
        tampilKinerjaMingguan();
    }//GEN-LAST:event_panelKinerjaComponentShown

    private void tabelKaryawanComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelKaryawanComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelKaryawanComponentShown

    private void panelTransaksiComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelTransaksiComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_panelTransaksiComponentShown

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        comboJenisb.setSelectedIndex(0);
        jTextField1.setText("0");
        modelDaftarBarang.setRowCount(0);
        totalTransaksi = 0;
        totalLabel.setText("TOTAL : Rp 0");
        fotokopiButton.setSelected(true);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnResetMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseReleased
        btnReset.setBackground(new java.awt.Color(137,26,54));
    }//GEN-LAST:event_btnResetMouseReleased

    private void btnResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMousePressed
        btnReset.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_btnResetMousePressed

    private void btnResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseExited
        btnReset.setBackground(new java.awt.Color(137,26,54));
    }//GEN-LAST:event_btnResetMouseExited

    private void btnResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseEntered
        btnReset.setBackground(new java.awt.Color(10, 36, 99));
    }//GEN-LAST:event_btnResetMouseEntered

    private void comboJenisbComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_comboJenisbComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_comboJenisbComponentShown

    private void comboJenisbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboJenisbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboJenisbActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void tambahButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahButtonActionPerformed
        String bahanBaku = comboJenisb.getSelectedItem().toString();
        String textJumlah = jTextField1.getText();

        if (textJumlah.isEmpty() || bahanBaku.equals("-- Pilih Bahan --")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Mohon lengkapi data!");
            return;
        }

        try {
            int jumlah = Integer.parseInt(textJumlah);
            
            if (jumlah <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!");
                return;
            }
            
            boolean sudahAda = false;
            for (int i = 0; i < modelDaftarBarang.getRowCount(); i++) {
                String namaExisting = modelDaftarBarang.getValueAt(i, 0).toString();
                if (namaExisting.equals(bahanBaku)) {
                    int jumlahLama = Integer.parseInt(modelDaftarBarang.getValueAt(i, 1).toString());
                    modelDaftarBarang.setValueAt(jumlahLama + jumlah, i, 1);
                    sudahAda = true;
                    break;
                }
            }
            
            if (!sudahAda) {
                Object[] baris = {bahanBaku, jumlah};
                modelDaftarBarang.addRow(baris);
            }
            
            hitungTotal();
            
            comboJenisb.setSelectedIndex(0);
            jTextField1.setText("0");
            
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
        }
    }//GEN-LAST:event_tambahButtonActionPerformed

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
        if (modelDaftarBarang.getRowCount() == 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Belum ada barang yang ditambahkan!");
        return;
    }
    
    String jenisLayanan = "";
    if (fotokopiButton.isSelected()) {
        jenisLayanan = "fotokopi";
    } else if (jilidButton.isSelected()) {
        jenisLayanan = "jilid";
    } else if (barangButton.isSelected()) {
        jenisLayanan = "barang";
    }

    Konektor myConn = new Konektor();

    try {
        java.sql.Connection conn = myConn.getConnection();

        String sqlTransaksi = 
            "INSERT INTO transaksi (id_user, tanggal, jenis_layanan, jumlah_satuan, total_biaya) " +
            "VALUES (?, NOW(), ?, ?, ?)";

        PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi, java.sql.Statement.RETURN_GENERATED_KEYS);

        int totalSatuan = 0;
        for (int i = 0; i < modelDaftarBarang.getRowCount(); i++) {
            totalSatuan += Integer.parseInt(modelDaftarBarang.getValueAt(i, 1).toString());
        }

        psTransaksi.setInt(1, idUser);
        psTransaksi.setString(2, jenisLayanan);
        psTransaksi.setInt(3, totalSatuan);
        psTransaksi.setInt(4, totalTransaksi);

        psTransaksi.executeUpdate();

        java.sql.ResultSet generatedKeys = psTransaksi.getGeneratedKeys();
        int idTransaksi = 0;
        if (generatedKeys.next()) {
            idTransaksi = generatedKeys.getInt(1);
        }

        for (int i = 0; i < modelDaftarBarang.getRowCount(); i++) {
            String namaBarang = modelDaftarBarang.getValueAt(i, 0).toString();
            int jumlah = Integer.parseInt(modelDaftarBarang.getValueAt(i, 1).toString());
            kurangiStok(namaBarang, jumlah);
        }

        Logic.Saldo objSaldo = new Logic.Saldo();
        objSaldo.ambilIdKeuanganTerakhir(); 
        objSaldo.setStatus(2);
        objSaldo.updateSaldo(totalTransaksi, "masuk", idTransaksi);

psTransaksi.close();

        javax.swing.JOptionPane.showMessageDialog(null, "Transaksi berhasil disimpan!");

        btnResetActionPerformed(null);
        tampilKinerjaMingguan();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        System.out.println("Error simpan transaksi: " + e.getMessage());
    }
    }//GEN-LAST:event_simpanButtonActionPerformed

    private void plusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusButtonActionPerformed
        int currentValue = Integer.parseInt(jTextField1.getText());
        jTextField1.setText(String.valueOf(currentValue + 1));
    }//GEN-LAST:event_plusButtonActionPerformed

    private void minusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusButtonActionPerformed
        int currentValue = Integer.parseInt(jTextField1.getText());
        if (currentValue > 0) {
            jTextField1.setText(String.valueOf(currentValue - 1));
        }
    }//GEN-LAST:event_minusButtonActionPerformed

    private void logoutButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonMouseEntered

    private void logoutButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonMouseExited

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        new Login().setVisible(true);
        JOptionPane.showMessageDialog(null, "Successfully logged out!");
        this.dispose();
    }//GEN-LAST:event_logoutButtonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FrameUser(1, "Test User").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ParentCard;
    private javax.swing.JRadioButton barangButton;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox comboJenisb;
    private javax.swing.JTable daftarbTable;
    private javax.swing.JButton dashboardButton;
    private javax.swing.JRadioButton fotokopiButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton jilidButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton minusButton;
    private javax.swing.JPanel panelKinerja;
    private javax.swing.JPanel panelTransaksi;
    private javax.swing.JButton plusButton;
    private javax.swing.JButton simpanButton;
    private javax.swing.JTable tabelKaryawan;
    private javax.swing.JButton tambahButton;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JButton transaksiButton;
    // End of variables declaration//GEN-END:variables
}
