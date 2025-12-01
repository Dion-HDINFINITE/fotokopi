/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fotokopi;

import Konektor.Konektor;
import Logic.BahanBaku;

/**
 *
 * @author ACER
 */
public class FrameAdmin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrameAdmin.class.getName());

    /**
     * Creates new form FrameAdmin
     */
    public FrameAdmin() {
        initComponents();
        
        styleTableDashboard();
        styleTableRiwayat();
        loadComboBox();
        tampilStokBarang();
    }
    
    private void loadComboBox(){
        comboBB.removeAllItems();
        comboBB.addItem("-- Pilih Bahan --");
        
        String sql = "SELECT nama_bahan FROM bahan_baku";
    
        Konektor myConn = new Konektor();
        java.sql.ResultSet rs;

        try {
            rs = myConn.getData(sql);

            while (rs.next()) {
                String nama = rs.getString("nama_bahan");

                comboBB.addItem(nama);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error load combo box: " + e.getMessage());
        }
    }

    private void styleTableDashboard() {
        // 1. Ambil Objek Header Tabel
        javax.swing.table.JTableHeader header = tabelDashboard.getTableHeader();


        header.setOpaque(false); // Agar warna menimpa tema bawaan

        // 3. Ubah Warna Body Tabel
        tabelDashboard.setBackground(new java.awt.Color(255, 250, 255)); // Putih/Magnolia
        tabelDashboard.setForeground(new java.awt.Color(10, 36, 99));    // Teks Navy
        tabelDashboard.setGridColor(new java.awt.Color(200, 200, 200));  // Garis abu tipis
        tabelDashboard.setRowHeight(30); // Agar baris lebih lega dan nyaman dibaca

        // 4. Ubah Warna Saat Baris DIKLIK (Selection Color) - Pakai Sky Blue
        tabelDashboard.setSelectionBackground(new java.awt.Color(255, 250, 255));
        tabelDashboard.setSelectionForeground(new java.awt.Color(10, 36, 99));

        // 5. Hilangkan Background Abu-abu di sisa tabel yang kosong
        jScrollPane1.getViewport().setBackground(new java.awt.Color(255, 250, 255));

        // 6. Hilangkan Border fokus (garis kotak tipis saat diklik)
        tabelDashboard.setShowVerticalLines(false); // Opsional: Hilangkan garis vertikal biar clean

        // 2. TIMPA RENDERER BAWAAN (Ini kuncinya agar warna Navy muncul)
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Panggil komponen label dasar
                javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // --- SETTING WARNA ---
                // Background: Navy Blue
                label.setBackground(new java.awt.Color(10, 36, 99)); 

                // Teks: Putih
                label.setForeground(new java.awt.Color(255, 250, 255));

                // Font: Segoe UI Bold 12
                label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

                // (Opsional) Beri sedikit jarak (Padding) biar teks tidak mepet pinggir
                label.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

                // (Opsional) Rata Kiri
                label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

                return label;
            }
        });

        // 3. Pastikan Header tidak "Reorderable" (biar user gak iseng geser2 kolom) - Opsional
        header.setReorderingAllowed(false);
    }
    
    private void styleTableRiwayat() {
        // 1. Ambil Objek Header Tabel
        javax.swing.table.JTableHeader header = tabelRiwayat.getTableHeader();


        header.setOpaque(false); // Agar warna menimpa tema bawaan

        // 3. Ubah Warna Body Tabel
        tabelRiwayat.setBackground(new java.awt.Color(255, 250, 255)); // Putih/Magnolia
        tabelRiwayat.setForeground(new java.awt.Color(10, 36, 99));    // Teks Navy
        tabelRiwayat.setGridColor(new java.awt.Color(200, 200, 200));  // Garis abu tipis
        tabelRiwayat.setRowHeight(30); // Agar baris lebih lega dan nyaman dibaca

        // 4. Ubah Warna Saat Baris DIKLIK (Selection Color) - Pakai Sky Blue
        tabelRiwayat.setSelectionBackground(new java.awt.Color(255, 250, 255));
        tabelRiwayat.setSelectionForeground(new java.awt.Color(10, 36, 99));

        // 5. Hilangkan Background Abu-abu di sisa tabel yang kosong
        jScrollPane3.getViewport().setBackground(new java.awt.Color(255, 250, 255));

        // 6. Hilangkan Border fokus (garis kotak tipis saat diklik)
        tabelRiwayat.setShowVerticalLines(false); // Opsional: Hilangkan garis vertikal biar clean

        // 2. TIMPA RENDERER BAWAAN (Ini kuncinya agar warna Navy muncul)
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Panggil komponen label dasar
                javax.swing.JLabel label = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // --- SETTING WARNA ---
                // Background: Navy Blue
                label.setBackground(new java.awt.Color(10, 36, 99)); 

                // Teks: Putih
                label.setForeground(new java.awt.Color(255, 250, 255));

                // Font: Segoe UI Bold 12
                label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

                // (Opsional) Beri sedikit jarak (Padding) biar teks tidak mepet pinggir
                label.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

                // (Opsional) Rata Kiri
                label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

                return label;
            }
        });

        // 3. Pastikan Header tidak "Reorderable" (biar user gak iseng geser2 kolom) - Opsional
        header.setReorderingAllowed(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ParentCard = new javax.swing.JPanel();
        panelDashboard = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDashboard = new javax.swing.JTable();
        panelStok = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        comboBB = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        fieldJumlah = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ketUpdate = new javax.swing.JTextArea();
        panelRiwayat = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelRiwayat = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JButton();
        btnStok = new javax.swing.JButton();
        btnRiwayat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ParentCard.setBackground(new java.awt.Color(255, 250, 255));
        ParentCard.setLayout(new java.awt.CardLayout());

        panelDashboard.setBackground(new java.awt.Color(255, 250, 255));
        panelDashboard.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panelDashboardComponentShown(evt);
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
        jLabel5.setText("Stok Saat ini");

        tabelDashboard.setBackground(new java.awt.Color(255, 250, 255));
        tabelDashboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bahan Baku", "Jumlah", "Harga Jual", "Harga Beli"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelDashboard.setGridColor(new java.awt.Color(255, 250, 255));
        tabelDashboard.setSelectionBackground(new java.awt.Color(255, 250, 255));
        tabelDashboard.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tabelDashboardComponentShown(evt);
            }
        });
        jScrollPane1.setViewportView(tabelDashboard);

        javax.swing.GroupLayout panelDashboardLayout = new javax.swing.GroupLayout(panelDashboard);
        panelDashboard.setLayout(panelDashboardLayout);
        panelDashboardLayout.setHorizontalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelDashboardLayout.setVerticalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );

        ParentCard.add(panelDashboard, "card2");

        panelStok.setBackground(new java.awt.Color(255, 250, 255));
        panelStok.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panelStokComponentShown(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(10, 36, 99));
        jLabel3.setText("Dashboard Stok");

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
        jLabel6.setText("Tambah Stok Bahan Baku");

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel7.setForeground(new java.awt.Color(10, 36, 99));
        jLabel7.setText("Nama Bahan Baku");

        comboBB.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                comboBBComponentShown(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(10, 36, 99));
        jLabel8.setText("Jumlah (yang mau ditambahkan)");

        fieldJumlah.setText("0");

        btnUpdate.setBackground(new java.awt.Color(62, 146, 204));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 250, 255));
        btnUpdate.setText("UPDATE");
        btnUpdate.setBorder(null);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setOpaque(true);
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUpdateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpdateMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUpdateMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnUpdateMouseReleased(evt);
            }
        });
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
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

        jLabel10.setForeground(new java.awt.Color(10, 36, 99));
        jLabel10.setText("Keterangan");

        ketUpdate.setColumns(20);
        ketUpdate.setRows(5);
        jScrollPane2.setViewportView(ketUpdate);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(comboBB, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(fieldJumlah))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(158, 158, 158)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelStokLayout = new javax.swing.GroupLayout(panelStok);
        panelStok.setLayout(panelStokLayout);
        panelStokLayout.setHorizontalGroup(
            panelStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
            .addGroup(panelStokLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelStokLayout.createSequentialGroup()
                        .addGroup(panelStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelStokLayout.setVerticalGroup(
            panelStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStokLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        ParentCard.add(panelStok, "card3");

        panelRiwayat.setBackground(new java.awt.Color(255, 250, 255));
        panelRiwayat.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panelRiwayatComponentShown(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(10, 36, 99));
        jLabel4.setText("Dashboard Riwayat");

        jPanel2.setBackground(new java.awt.Color(10, 36, 99));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(10, 36, 99));
        jLabel9.setText("Riwayat Penggunaan Bahan Baku");

        tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Bahan Baku", "Tanggal", "Jumlah", "Harga Total", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelRiwayat);

        javax.swing.GroupLayout panelRiwayatLayout = new javax.swing.GroupLayout(panelRiwayat);
        panelRiwayat.setLayout(panelRiwayatLayout);
        panelRiwayatLayout.setHorizontalGroup(
            panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
            .addGroup(panelRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRiwayatLayout.createSequentialGroup()
                        .addGroup(panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelRiwayatLayout.setVerticalGroup(
            panelRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        ParentCard.add(panelRiwayat, "card4");

        jPanel1.setBackground(new java.awt.Color(10, 36, 99));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 250, 255));
        jLabel1.setText("Dashboard Admin");

        btnDashboard.setBackground(new java.awt.Color(10, 36, 99));
        btnDashboard.setForeground(new java.awt.Color(255, 250, 255));
        btnDashboard.setText("Dashboard");
        btnDashboard.setBorder(null);
        btnDashboard.setBorderPainted(false);
        btnDashboard.setContentAreaFilled(false);
        btnDashboard.setFocusPainted(false);
        btnDashboard.setOpaque(true);
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDashboardMouseExited(evt);
            }
        });
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        btnStok.setBackground(new java.awt.Color(10, 36, 99));
        btnStok.setForeground(new java.awt.Color(255, 250, 255));
        btnStok.setText("Kelola Stok");
        btnStok.setBorder(null);
        btnStok.setBorderPainted(false);
        btnStok.setContentAreaFilled(false);
        btnStok.setFocusPainted(false);
        btnStok.setOpaque(true);
        btnStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnStokMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnStokMouseExited(evt);
            }
        });
        btnStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStokActionPerformed(evt);
            }
        });

        btnRiwayat.setBackground(new java.awt.Color(10, 36, 99));
        btnRiwayat.setForeground(new java.awt.Color(255, 250, 255));
        btnRiwayat.setText("Riwayat");
        btnRiwayat.setBorder(null);
        btnRiwayat.setBorderPainted(false);
        btnRiwayat.setContentAreaFilled(false);
        btnRiwayat.setFocusPainted(false);
        btnRiwayat.setOpaque(true);
        btnRiwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRiwayatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRiwayatMouseExited(evt);
            }
        });
        btnRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(57, 57, 57)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStok, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(198, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 110, Short.MAX_VALUE)
                .addComponent(ParentCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 521, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ParentCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseEntered
        // TODO add your handling code here:
        btnDashboard.setBackground(new java.awt.Color(137, 26, 54));
    }//GEN-LAST:event_btnDashboardMouseEntered

    private void btnDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseExited
        // TODO add your handling code here:
        btnDashboard.setBackground(new java.awt.Color(10,36,99));
    }//GEN-LAST:event_btnDashboardMouseExited

    private void btnStokMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStokMouseEntered
        // TODO add your handling code here:
        btnStok.setBackground(new java.awt.Color(137, 26, 54));
    }//GEN-LAST:event_btnStokMouseEntered

    private void btnStokMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStokMouseExited
        // TODO add your handling code here:
        btnStok.setBackground(new java.awt.Color(10,36,99));
    }//GEN-LAST:event_btnStokMouseExited

    private void btnRiwayatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatMouseEntered
        // TODO add your handling code here:
        btnRiwayat.setBackground(new java.awt.Color(137, 26, 54));
    }//GEN-LAST:event_btnRiwayatMouseEntered

    private void btnRiwayatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatMouseExited
        // TODO add your handling code here:
        btnRiwayat.setBackground(new java.awt.Color(10,36,99));
    }//GEN-LAST:event_btnRiwayatMouseExited

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        java.awt.CardLayout cl = (java.awt.CardLayout) (ParentCard.getLayout());
        cl.show(ParentCard, "card2");
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStokActionPerformed
        // TODO add your handling code here:
        java.awt.CardLayout cl = (java.awt.CardLayout) (ParentCard.getLayout());
        cl.show(ParentCard, "card3");
    }//GEN-LAST:event_btnStokActionPerformed

    private void btnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatActionPerformed
        // TODO add your handling code here:
        java.awt.CardLayout cl = (java.awt.CardLayout) (ParentCard.getLayout());
        cl.show(ParentCard, "card4");
    }//GEN-LAST:event_btnRiwayatActionPerformed

    public void tampilStokBarang() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tabelDashboard.getModel();
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
    
    private void panelDashboardComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelDashboardComponentShown
        // TODO add your handling code here:
        tampilStokBarang();
    }//GEN-LAST:event_panelDashboardComponentShown

    private void tabelDashboardComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabelDashboardComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDashboardComponentShown

    private void btnUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseEntered
        // TODO add your handling code here:
        btnUpdate.setBackground(new java.awt.Color(10, 36, 99));
    }//GEN-LAST:event_btnUpdateMouseEntered

    private void btnUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseExited
        // TODO add your handling code here:
        btnUpdate.setBackground(new java.awt.Color(62, 146, 204));
    }//GEN-LAST:event_btnUpdateMouseExited

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        String bahanBaku = comboBB.getSelectedItem().toString();
        String textJumlah = fieldJumlah.getText();
        String textKeterangan = ketUpdate.getText(); 

        if (textJumlah.isEmpty() || bahanBaku.equals("-- Pilih Bahan --")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Mohon lengkapi data!");
            return;
        }

        String idBahan = "";
        int stokLama = 0;
        int hargaBeli = 0;

        String sqlSelect = "SELECT id_bahan, stok, harga_beli FROM bahan_baku WHERE nama_bahan = '" + bahanBaku + "'";

        Konektor myConn = new Konektor();
        java.sql.ResultSet rs;

        try {
            rs = myConn.getData(sqlSelect);
            if (rs.next()) {
                idBahan = rs.getString("id_bahan");
                stokLama = rs.getInt("stok");
                hargaBeli = rs.getInt("harga_beli");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Bahan tidak ditemukan!");
                return; 
            }
            rs.close(); 

        } catch (Exception e) {
            System.out.println("Debug : Error saat mengambil data bahan baku - " + e.getMessage());
            return;
        }

        try {
            int jumlahInput = Integer.parseInt(textJumlah);

            int stokTotal = stokLama + jumlahInput;

            int totalHargaRiwayat = hargaBeli * jumlahInput;

            BahanBaku updBB = new BahanBaku();
            updBB.setStok(stokTotal);
            updBB.setNama_bahan(bahanBaku);
            updBB.updateStok(); 

            try {
                java.sql.Connection conn = myConn.getConnection();
                String sqlInsert = "INSERT INTO riwayat_stok (id_bahan, tanggal, jumlah, harga_total, keterangan) VALUES (?, NOW(), ?, ?, ?)";

                java.sql.PreparedStatement ps = conn.prepareStatement(sqlInsert);
                ps.setString(1, idBahan);          
                ps.setInt(2, jumlahInput);         
                ps.setInt(3, totalHargaRiwayat);   
                ps.setString(4, textKeterangan);   

                ps.executeUpdate();
                ps.close();

            } catch (Exception e) {
                System.out.println("Debug : Gagal simpan riwayat - " + e.getMessage());
            }

            javax.swing.JOptionPane.showMessageDialog(null, "Stok Bertambah & Riwayat Tersimpan!");

            fieldJumlah.setText("");
            ketUpdate.setText("");

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnUpdateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMousePressed
        // TODO add your handling code here:
        btnUpdate.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_btnUpdateMousePressed

    private void btnUpdateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseReleased
        // TODO add your handling code here:
        btnUpdate.setBackground(new java.awt.Color(10, 36, 99));
    }//GEN-LAST:event_btnUpdateMouseReleased

    private void btnResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseEntered
        // TODO add your handling code here:
        btnReset.setBackground(new java.awt.Color(10, 36, 99));
    }//GEN-LAST:event_btnResetMouseEntered

    private void btnResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseExited
        // TODO add your handling code here:
        btnReset.setBackground(new java.awt.Color(137,26,54));
    }//GEN-LAST:event_btnResetMouseExited

    private void btnResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMousePressed
        // TODO add your handling code here:
        btnReset.setBackground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_btnResetMousePressed

    private void btnResetMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseReleased
        // TODO add your handling code here:
        btnReset.setBackground(new java.awt.Color(137,26,54));
    }//GEN-LAST:event_btnResetMouseReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        comboBB.setSelectedIndex(0);
        fieldJumlah.setText("0");
        ketUpdate.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void comboBBComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_comboBBComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBBComponentShown

    private void panelStokComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelStokComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_panelStokComponentShown

    public void tampilRiwayat() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tabelRiwayat.getModel();
        model.setRowCount(0);
        
        String sql = "SELECT b.nama_bahan, r.tanggal, r.jumlah, r.harga_total, r.keterangan " +
                     "FROM riwayat_stok r " +
                     "JOIN bahan_baku b ON r.id_bahan = b.id_bahan " +
                     "ORDER BY r.tanggal DESC";

        Konektor myConn = new Konektor();
        java.sql.ResultSet rs;

        java.text.SimpleDateFormat formatTanggal = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
        java.text.NumberFormat kursIDR = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id", "ID"));

        try {
            rs = myConn.getData(sql);

            while (rs.next()) {
                String nama = rs.getString("nama_bahan");
                java.sql.Timestamp tgl = rs.getTimestamp("tanggal");
                int jml = rs.getInt("jumlah");
                double total = rs.getDouble("harga_total");
                String ket = rs.getString("keterangan");

                Object[] baris = {
                    nama,
                    formatTanggal.format(tgl), 
                    jml,
                    kursIDR.format(total), 
                    ket
                };

                model.addRow(baris);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println("Error saat menampilkan Riwayat: " + e.getMessage());
        }
    }
    
    private void panelRiwayatComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelRiwayatComponentShown
        // TODO add your handling code here:
        tampilRiwayat();
    }//GEN-LAST:event_panelRiwayatComponentShown

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrameAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ParentCard;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRiwayat;
    private javax.swing.JButton btnStok;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> comboBB;
    private javax.swing.JTextField fieldJumlah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea ketUpdate;
    private javax.swing.JPanel panelDashboard;
    private javax.swing.JPanel panelRiwayat;
    private javax.swing.JPanel panelStok;
    private javax.swing.JTable tabelDashboard;
    private javax.swing.JTable tabelRiwayat;
    // End of variables declaration//GEN-END:variables
}
