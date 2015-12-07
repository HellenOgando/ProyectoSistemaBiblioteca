/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz_Grafica;

import BaseDeDatos.BDConexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import javax.swing.*;
import BaseDeDatos.BDConexion;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author albe211
 */
public class GestionCarrera extends javax.swing.JFrame {

    /**
     * Creates new form Carrera
     */
     Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;
      String IDusu="";
    
    public GestionCarrera() {
       initComponents();
        RefrescarTabla();
        inicio();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    private void inicio(){
        txt_busqueda.setText(null);
        txt_busqueda.setEnabled(false);
        txt_CodigoCarrera.setEnabled(false);
        TextArea_Descripcion.setEnabled(false);
        txt_CodigoCarrera.setText(null);
        TextArea_Descripcion.setText(null);
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setText("Guardar");
        Tabla_Carreras.setEnabled(true);
         ComboBox_busqueda.setSelectedIndex(0);
        IDusu="";
        btnBusqueda.setEnabled(false);
        ComboBox_busqueda.setEnabled(true);
        
    }
    
    private void RefrescarTabla(){
        
         try {

            BDConexion miconexion = new BDConexion();
            String cons = "SELECT * FROM Carreras";
            
            ResultSet consultas = miconexion.consulta(cons);

            ResultSetMetaData rsMd = consultas.getMetaData();
            int numeroColumnas = rsMd.getColumnCount();
            
             String titulos[] = {"Código Carrera", "Descripción"};
             DefaultTableModel modelo = new DefaultTableModel(null, titulos);
             this.Tabla_Carreras.setModel(modelo);
            Tabla_Carreras.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);//esto es para que la ultima columna se reautodimenzione mientras que las otras columnas sus dimensiones se ponen manuealmente
            Tabla_Carreras.getColumnModel().getColumn(0).setMaxWidth(1000);// parametros que miden la dimension de la columna 
             Tabla_Carreras.getColumnModel().getColumn(0).setPreferredWidth(100);//parametros que miden la dimension de la columna
             //Tabla_Carreras.getColumnModel().getColumn(1).setPreferredWidth(120);

            while (consultas.next()) {
                Object[] fila = new Object[numeroColumnas];
                for (int y = 0; y < numeroColumnas; y++) {
                    fila[y] = consultas.getObject(y + 1);
                }
                modelo.addRow(fila);
            }

        } catch (Exception e) {
             System.out.println(e.getMessage());
           // JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
    }
    
    private void Nuevo(){
        txt_CodigoCarrera.setEnabled(true);
        TextArea_Descripcion.setEnabled(true);
         btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        Tabla_Carreras.setEnabled(false);
        ComboBox_busqueda.setEnabled(false);
    }
    
    private void Modificar(){
        
        txt_CodigoCarrera.setEnabled(false);
        TextArea_Descripcion.setEnabled(true);
         btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        btnGuardar.setText("Actualizar");
        Tabla_Carreras.setEnabled(false);
        ComboBox_busqueda.setEnabled(false);
        
    }
    
    private void Guardar(){
        
        
        objetos.Carrera CarreraActualizado = null;// este se usara cuando se actualizen los datos
        
         //dc_fechanac == null
    
         if (txt_CodigoCarrera.getText().equals("") || TextArea_Descripcion.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Llene las casillas en blanco");
                return;
            }
         
//metodo para comprobar si la matricula que se agregara esta ya almacenada en la tabla
         
          int totalRow = Tabla_Carreras.getRowCount();//total de filas en la tabla
           
        //totalRow -= 1;
       //  JOptionPane.showMessageDialog(this,totalRow );
        String[]CodigoCarreras=new String[totalRow];// array donde se ira almacenando todas las matriculas que estan en la tabla
          String CodigoTabla;
        for (int i = 0; i < (totalRow); i++) {// aqui se ira almacenando las matriculas de la tabla en el array
             CodigoTabla= (String.valueOf(Tabla_Carreras.getValueAt(i, 0)));
            CodigoCarreras[i]= CodigoTabla;
        }
         String CodigoCarrera = txt_CodigoCarrera.getText();
         String nombreBoton = btnGuardar.getText();
        
        for(int i=0;i<CodigoCarreras.length;i++){
    if(CodigoCarrera.equalsIgnoreCase(CodigoCarreras[i]) && nombreBoton.equals("Guardar") ){ //aqui se compara la matricula introducida con las de la tabla para ver si hay alguna igual
       // este iquals comprueba si son igual aun este en mayuscula o minuscula
        JOptionPane.showMessageDialog(null, "No se Pueden repetir el Código de Carrera");
                return ;
                
        
    }}
         
      
       
        objetos.Carrera Carrera = new objetos.Carrera( txt_CodigoCarrera.getText(), TextArea_Descripcion.getText());
        BaseDeDatos.BDSentenciasEST insert;
        BaseDeDatos.BDSentenciasEST update;
         
         if (!IDusu.equals(""))// esta variable es utilizada para saber si se va a guardar o actualizar los datos
        {
        CarreraActualizado = new objetos.Carrera(txt_CodigoCarrera.getText(), TextArea_Descripcion.getText());
        }
         
       try {
             
            
            if(!IDusu.equals("")){
            update = new BaseDeDatos.BDSentenciasEST();
                update.update(Carrera);
                JOptionPane.showMessageDialog(null, "Actualizado Correctamente");
                IDusu="";
                RefrescarTabla();
                inicio();
            }
            else {
                insert = new BaseDeDatos.BDSentenciasEST();
                insert.insert(Carrera);
                JOptionPane.showMessageDialog(null, "Guardado Correctamente");
                RefrescarTabla();
                inicio();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    private void TocarJtabla(){
        
         
        if (this.Tabla_Carreras.isEnabled()){
          String FechaNac;
         try {
            
          //  int row = Tabla_Usuarios.getSelectedRow();//este se usa para seleccionar una fila de la tabla normalmente
             int row = Tabla_Carreras.convertRowIndexToModel(Tabla_Carreras.getSelectedRow());// este se usa para seleccionar una fila de la tabla aun cuando se organiza de mayor a menor, etc


            IDusu=  (Tabla_Carreras.getModel().getValueAt(row, 0).toString());
            txt_CodigoCarrera.setText(Tabla_Carreras.getModel().getValueAt(row, 0).toString());
            TextArea_Descripcion.setText(Tabla_Carreras.getModel().getValueAt(row, 1).toString());

        
             this.btnModificar.setEnabled(true);
             this.btnEliminar.setEnabled(true);
             this.btnNuevo.setEnabled(false);
              this.btnCancelar.setEnabled(true);
              ComboBox_busqueda.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        
        
    }
    
     private void Eliminar(){
             try {
            

                objetos.Carrera Carrera  =  new objetos.Carrera( txt_CodigoCarrera.getText(), TextArea_Descripcion.getText());
                BaseDeDatos.BDSentenciasEST delete;
                delete = new BaseDeDatos.BDSentenciasEST();
                delete.delete(Carrera);
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
                IDusu="";
                RefrescarTabla();
                inicio();
                
            }
         catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
     
     private void ComboBusqueda(){
           String SeleccioneBusqueda= (String) ComboBox_busqueda.getSelectedItem();
        
        if(SeleccioneBusqueda.equals("Seleccione")){
            
         //   btnBusqueda.setEnabled(false);
           // txt_busqueda.setEnabled(false);
            inicio();
        }else{
            btnBusqueda.setEnabled(true);
            txt_busqueda.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnNuevo.setEnabled(false);
        }
        
    }
     
      private void RefrescarTablaBusqueda( String sql){
        
        try {

            BDConexion miconexion = new BDConexion();
          //  String cons = "SELECT * FROM Usuarios WHERE Nombre ='"+nombre+"'";
          //    String cons = "SELECT * FROM Usuarios WHERE Nombre LIKE '%"+nombre+"%'";
            ResultSet consultas = miconexion.consulta(sql);

            ResultSetMetaData rsMd = consultas.getMetaData();
            int numeroColumnas = rsMd.getColumnCount();

             String titulos[] = {"Código Carrera", "Descripción"};
             DefaultTableModel modelo = new DefaultTableModel(null, titulos);
             this.Tabla_Carreras.setModel(modelo);
            Tabla_Carreras.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);//esto es para que la ultima columna se reautodimenzione mientras que las otras columnas sus dimensiones se ponen manuealmente
            Tabla_Carreras.getColumnModel().getColumn(0).setMaxWidth(1000);// parametros que miden la dimension de la columna 
             Tabla_Carreras.getColumnModel().getColumn(0).setPreferredWidth(100);//parametros que miden la dimension de la columna
             //Tabla_Carreras.getColumnModel().getColumn(1).setPreferredWidth(120);

            while (consultas.next()) {
                Object[] fila = new Object[numeroColumnas];
                for (int y = 0; y < numeroColumnas; y++) {
                    fila[y] = consultas.getObject(y + 1);
                }
                modelo.addRow(fila);
            }

        } catch (Exception e) {
             System.out.println(e.getMessage());
            
        }
        
    }
     
     private void busqueda (){
         String busquedaNombre =txt_busqueda.getText();
        
        String SeleccionBusqueda = (String) ComboBox_busqueda.getSelectedItem();
        
        if (busquedaNombre==null || busquedaNombre.isEmpty()){
            JOptionPane.showMessageDialog(this, "El Campo esta vacio");
            return;
        }
        
        if (SeleccionBusqueda.equals("Código Carrera")){
        
               
                RefrescarTablaBusqueda(busquedaNombre);
                
                String sql = "SELECT * FROM Carrera WHERE Matricula LIKE '%"+busquedaNombre+"%'";
                // String sql = "SELECT * FROM Carrera WHERE CodigoCarrera = '"+busquedaNombre+"'";
            RefrescarTablaBusqueda(sql);
                inicio();
                btnCancelar.setEnabled(true);
                btnNuevo.setEnabled(false);
                /*
               ComboBox_busqueda.setSelectedIndex(0);
               txt_busqueda.setText(null);
               btnBusqueda.setEnabled(false);
              */ 
            }
            
            
        
         if (SeleccionBusqueda.equals("Descripción")){
            
            String sql = "SELECT * FROM Carrera WHERE Descripcion LIKE '%"+busquedaNombre+"%'";
            //String sql = "SELECT * FROM Carrera WHERE Descripcion ='"+busquedaNombre+"'";
            RefrescarTablaBusqueda(sql);
            inicio();
            btnNuevo.setEnabled(false);
            btnCancelar.setEnabled(true);
        }
         
         
        
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Carreras = new javax.swing.JTable();
        txt_CodigoCarrera = new javax.swing.JTextField();
        txt_busqueda = new javax.swing.JTextField();
        btnBusqueda = new javax.swing.JButton();
        ComboBox_busqueda = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextArea_Descripcion = new javax.swing.JTextArea();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("Gestión de Carrera");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de las Carreras", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel2.setText("Código Carrera:");

        Tabla_Carreras.setAutoCreateRowSorter(true);
        Tabla_Carreras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código Carrera", "Descripción"
            }
        ));
        Tabla_Carreras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_CarrerasMouseClicked(evt);
            }
        });
        Tabla_Carreras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Tabla_CarrerasKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Carreras);

        txt_CodigoCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CodigoCarreraActionPerformed(evt);
            }
        });
        txt_CodigoCarrera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_CodigoCarreraKeyTyped(evt);
            }
        });

        btnBusqueda.setText("Busqueda");
        btnBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaActionPerformed(evt);
            }
        });

        ComboBox_busqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Código Carrera", "Descripción" }));
        ComboBox_busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_busquedaActionPerformed(evt);
            }
        });

        jLabel3.setText("Descripción:");

        TextArea_Descripcion.setColumns(20);
        TextArea_Descripcion.setRows(5);
        jScrollPane2.setViewportView(TextArea_Descripcion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_CodigoCarrera)
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ComboBox_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBusqueda)
                .addGap(159, 159, 159))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusqueda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_CodigoCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_editar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir_opt.png"))); // NOI18N
        btnCancelar1.setText("Salir");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar1)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar)
                    .addComponent(btnCancelar1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla_CarrerasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_CarrerasMouseClicked
        // TODO add your handling code here:

       TocarJtabla();
    }//GEN-LAST:event_Tabla_CarrerasMouseClicked

    private void Tabla_CarrerasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_CarrerasKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla_CarrerasKeyTyped

    private void txt_CodigoCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CodigoCarreraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CodigoCarreraActionPerformed

    private void txt_CodigoCarreraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CodigoCarreraKeyTyped
        // TODO add your handling code here:

             char c=evt.getKeyChar(); 
             
         
          if(Character.isDigit(c)) { 
              getToolkit().beep(); 
               
              evt.consume(); 

            // JOptionPane.showMessageDialog(this, "Ingresa Solo Numeros");
        }

    }//GEN-LAST:event_txt_CodigoCarreraKeyTyped

    private void btnBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaActionPerformed
        // TODO add your handling code here:
   busqueda();
    }//GEN-LAST:event_btnBusquedaActionPerformed

    private void ComboBox_busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_busquedaActionPerformed
        // TODO add your handling code here:

 ComboBusqueda();
    }//GEN-LAST:event_ComboBox_busquedaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:

        Nuevo();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:

        Guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        Modificar();

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:

        Eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        inicio();
        RefrescarTabla();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        // TODO add your handling code here:
        int reply = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cerrar la ventana?", "Mensaje Confirmación", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            this.dispose();
        }
        else {

        }

    }//GEN-LAST:event_btnCancelar1ActionPerformed

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
            java.util.logging.Logger.getLogger(GestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionCarrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionCarrera().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboBox_busqueda;
    private javax.swing.JTable Tabla_Carreras;
    private javax.swing.JTextArea TextArea_Descripcion;
    private javax.swing.JButton btnBusqueda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txt_CodigoCarrera;
    private javax.swing.JTextField txt_busqueda;
    // End of variables declaration//GEN-END:variables
}
