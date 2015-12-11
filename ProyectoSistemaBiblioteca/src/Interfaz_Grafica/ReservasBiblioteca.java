/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz_Grafica;

import BaseDeDatos.BDConexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ReservasBiblioteca extends javax.swing.JFrame {

    
    BDConexion miconexion = new BDConexion();
    Connection cnn = miconexion.Conexion1();
    
    ResultSet rsDatos ;
    PreparedStatement psPrepararSentencias ;
    
    public ReservasBiblioteca() {
        initComponents();
        RefrescarTablaReservas();
         inicio();
    }

    public void inicio (){
       IDTransaccionTextField.setEditable(false);
       UsuarioTextField.setEditable(false); 
       LibroTextField.setEditable(false);
        
    }
    private void RefrescarTablaReservas() {// metodo para que aparesca en la tabla los elementos de la base de datos
        
        try {

            
            String cons = "SELECT * FROM Historial";
            try {
            
            psPrepararSentencias = cnn.prepareStatement(cons);
            rsDatos = psPrepararSentencias.executeQuery();
           
        } catch(SQLException ex){throw ex;}
            ResultSet consultas = rsDatos;
            
    int IDUsuario = consultas.getInt("IDUsuario");
            int IDLibro = consultas.getInt("IDLibro");
            
            String cons2 = "SELECT Nombre, Apellido FROM Estudiantes WHERE Matricula= "+IDUsuario;
            ResultSet consultas2 = miconexion.consulta(cons2);
            
            String cons3 = "SELECT Titulo, CodigoUbicacion FROM Libros WHERE IDLibro= "+IDLibro;
            ResultSet consultas3 = miconexion.consulta(cons3);
            
          
            

            String titulos[] = {"IDTransaccion", "Usuario", "Libro", "CULibro", "FechaEntrega", "FechaDevolución", "EstadoPréstamo","UltimaActuaización"};
            DefaultTableModel modelo = new DefaultTableModel(null, titulos);
            this.prestamoTabla.setModel(modelo);
            

            while (consultas.next()) {
             IDUsuario = consultas.getInt("IDUsuario");
            IDLibro = consultas.getInt("IDLibro");
            
             cons2 = "SELECT Nombre, Apellido FROM Estudiantes WHERE Matricula= "+IDUsuario;
            consultas2 = miconexion.consulta(cons2);
            
            cons3 = "SELECT Titulo, CodigoUbicacion FROM Libros WHERE IDLibro= "+IDLibro;
            consultas3 = miconexion.consulta(cons3);
            
            
                String[] fila = new String[8];
                
                
                    fila[0] = String.valueOf(consultas.getInt("IDTransaccion"));
                    fila[1] = consultas2.getString("Nombre")+" "+consultas2.getString("Apellido");
                    fila[2] = consultas3.getString("Titulo");
                    fila[3] = consultas3.getString("CodigoUbicacion");
                    fila[4] = consultas.getString("FechaEntrega");
                    fila[5] = consultas.getString("FechaDevolucion");
                    fila[6] = consultas.getString("Estado");
                    fila[7] = consultas.getString("UltimaActualizacion");
                   
                modelo.addRow(fila);
            }

        } catch (Exception e) {
             System.out.println(e.getMessage());
            
        }finally{
           try{
           
           rsDatos.close();
           }catch(Exception e){
           
           }
           }
    }
        
        
    private void TocarPrestamoTabla(){
        
         if (this.prestamoTabla.isEnabled()){
           IDTransaccionTextField.setEnabled(false);
            LibroTextField.setEnabled(false);
            UsuarioTextField.setEnabled(false);
         try {
            int row = prestamoTabla.getSelectedRow();
             
            IDTransaccionTextField.setText(prestamoTabla.getModel().getValueAt(row, 0).toString());
            LibroTextField.setText(prestamoTabla.getModel().getValueAt(row, 2).toString());
            UsuarioTextField.setText(prestamoTabla.getModel().getValueAt(row, 1).toString());
            
            String estado = prestamoTabla.getModel().getValueAt(row, 6).toString();
            
            if (estado.equals("Reservado")){
            entregadoBtn.setEnabled(true);
            devolverBtn.setEnabled(false);
            }
            if (estado.equals("Entregado")){
            devolucionFecha.setEnabled(false);
            entregadoBtn.setEnabled(false);
            devolverBtn.setEnabled(true);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    }
    
    private void Entregar() throws SQLException, ClassNotFoundException{
        
        
        Statement stmt = null;
        
        
        DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date FechaDev = devolucionFecha.getDate();
       
        
        if(IDTransaccionTextField.getText().isEmpty()){
            
        JOptionPane.showMessageDialog(null, "Debe de sleccionar una transaccion del historial.");
        
        }else if(FechaDev == null){
            JOptionPane.showMessageDialog(null, "Debe indicar la fecha de devolucion.");
            
        }else{
           
            String IDtrans = IDTransaccionTextField.getText();
            
            Date fechaActual = new Date(System.currentTimeMillis());
            String FechaDevolucion = fecha.format(FechaDev);
           try{ 
            
            String sql = "UPDATE Historial SET FechaEntrega="+fecha.format(fechaActual)+", Estado="+"'Entregado'"+ ", FechaDevolucion="+FechaDevolucion+", UltimaActualizacion="+fecha.format(fechaActual)+" WHERE IDTransaccion= "+IDtrans;
           
            stmt = cnn.createStatement();
            stmt.executeUpdate(sql);
           // stmt.close();
           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }finally{
           try{
              
           stmt.close();
           }catch(Exception e){
           
           }
           }
        }
    
            RefrescarTablaReservas();
            
    }
    
private void Devolver() throws SQLException, ClassNotFoundException{
        
        
        Statement stmt = null;
        
        
        DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date FechaDev = devolucionFecha.getDate();
       
        String estado = "Devuelto";
        if(IDTransaccionTextField.getText().isEmpty()){
            
        JOptionPane.showMessageDialog(null, "Debe de sleccionar una transaccion del historial.");
        
        }else{
           
            String IDtrans = IDTransaccionTextField.getText();
            
            Date fechaActual = new Date(System.currentTimeMillis());
            String FechaDevolucion = fecha.format(FechaDev);
           try{ 
            
            String sql = "UPDATE Historial SET Estado="+estado+", UltimaActualizacion="+fecha.format(fechaActual)+" WHERE IDTransaccion= "+IDtrans;
           
            stmt = cnn.createStatement();
            stmt.executeUpdate(sql);
           // stmt.close();
           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }finally{
           try{
              
           stmt.close();
           }catch(Exception e){
           
           }
           }
        }
    
            RefrescarTablaReservas();
            
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        dateChooserDialog2 = new datechooser.beans.DateChooserDialog();
        dc_fechanac = new com.toedter.calendar.JDateChooser();
        dc_fechanac1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        prestamoTabla = new javax.swing.JTable();
        TextField_UsuarioReserva = new javax.swing.JTextField();
        buscarBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IDTransaccionTextField = new javax.swing.JTextField();
        LibroTextField = new javax.swing.JTextField();
        UsuarioTextField = new javax.swing.JTextField();
        devolucionFecha = new com.toedter.calendar.JDateChooser();
        entregadoBtn = new javax.swing.JButton();
        devolverBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("Gestión de Reservas");

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion de las Reservas\n\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel2.setText("Usuario:");

        jLabel5.setText("Fecha para devolucion:");

        prestamoTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IDTransaccion", "Usuario", "Libro", "CULibro", "FechaEntrega", "FechaDevolucion", "EstadoPréstamo", "UltimaActualizacionEstado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        prestamoTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prestamoTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(prestamoTabla);
        if (prestamoTabla.getColumnModel().getColumnCount() > 0) {
            prestamoTabla.getColumnModel().getColumn(0).setResizable(false);
            prestamoTabla.getColumnModel().getColumn(1).setResizable(false);
            prestamoTabla.getColumnModel().getColumn(2).setResizable(false);
            prestamoTabla.getColumnModel().getColumn(3).setResizable(false);
            prestamoTabla.getColumnModel().getColumn(4).setResizable(false);
            prestamoTabla.getColumnModel().getColumn(5).setResizable(false);
            prestamoTabla.getColumnModel().getColumn(6).setResizable(false);
            prestamoTabla.getColumnModel().getColumn(7).setResizable(false);
        }

        TextField_UsuarioReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextField_UsuarioReservaActionPerformed(evt);
            }
        });

        buscarBtn.setText("Buscar");
        buscarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Libro");

        jLabel3.setText("Usuario");

        jLabel4.setText("IDTransaccion");

        IDTransaccionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDTransaccionTextFieldActionPerformed(evt);
            }
        });

        LibroTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LibroTextFieldActionPerformed(evt);
            }
        });

        UsuarioTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(454, 454, 454)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(TextField_UsuarioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(buscarBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(devolucionFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UsuarioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(LibroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IDTransaccionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextField_UsuarioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarBtn)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(devolucionFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IDTransaccionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(LibroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UsuarioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(49, 49, 49))))
        );

        entregadoBtn.setText("Entregar");
        entregadoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entregadoBtnActionPerformed(evt);
            }
        });

        devolverBtn.setText("Devolver");
        devolverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devolverBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(379, 379, 379))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(entregadoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(devolverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entregadoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(devolverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 949, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void entregadoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entregadoBtnActionPerformed
        try {
            // TODO add your handling code here:
            Entregar();
        } catch (SQLException ex) {
            Logger.getLogger(ReservasBiblioteca.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservasBiblioteca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_entregadoBtnActionPerformed

    private void devolverBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devolverBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_devolverBtnActionPerformed

    private void TextField_UsuarioReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextField_UsuarioReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextField_UsuarioReservaActionPerformed

    private void buscarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarBtnActionPerformed

    private void IDTransaccionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDTransaccionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDTransaccionTextFieldActionPerformed

    private void LibroTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LibroTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LibroTextFieldActionPerformed

    private void UsuarioTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsuarioTextFieldActionPerformed

    private void prestamoTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prestamoTablaMouseClicked
        // TODO add your handling code here:
        TocarPrestamoTabla();
    }//GEN-LAST:event_prestamoTablaMouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReservasBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReservasBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReservasBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReservasBiblioteca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReservasBiblioteca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IDTransaccionTextField;
    private javax.swing.JTextField LibroTextField;
    private javax.swing.JTextField TextField_UsuarioReserva;
    private javax.swing.JTextField UsuarioTextField;
    private javax.swing.JButton buscarBtn;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private datechooser.beans.DateChooserDialog dateChooserDialog2;
    private com.toedter.calendar.JDateChooser dc_fechanac;
    private com.toedter.calendar.JDateChooser dc_fechanac1;
    private com.toedter.calendar.JDateChooser devolucionFecha;
    private javax.swing.JButton devolverBtn;
    private javax.swing.JButton entregadoBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable prestamoTabla;
    // End of variables declaration//GEN-END:variables
}
