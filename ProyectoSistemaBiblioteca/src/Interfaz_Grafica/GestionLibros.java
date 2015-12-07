/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz_Grafica;

import BaseDeDatos.BDConexion;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author albe211
 */
public class GestionLibros extends javax.swing.JFrame {

   
int IDLibro;

    public GestionLibros() {
        initComponents();
        inicio();
        RefrescarTabla();
        
        
    }

    private void inicio(){
        
        idlibroTextField.setEnabled(false);
        tituloTextField.setEnabled(false);
        autorTextField.setEnabled(false);
        lugarPublicacionTextField.setEnabled(false);
        publicacionYearChooser.setEnabled(false);
        edicionTextField.setEnabled(false);
        editorialTextField.setEnabled(false);
        numPagTextField.setEnabled(false);
        cantSpinField.setEnabled(true);
        codUbiTextField.setEnabled(false);
        nuevoBtn.setEnabled(true);
        guardarBtn.setEnabled(false);
        modificarBtn.setEnabled(false);
        eliminarBtn.setEnabled(false);
        librosTabla.setEnabled(true);
        IDLibro=0;
        tituloTextField.setText(null);
        autorTextField.setText(null);
        publicacionYearChooser.setYear(2015);
        lugarPublicacionTextField.setText(null);
        edicionTextField.setText(null);
        editorialTextField.setText(null);
        numPagTextField.setText(null);
        cantSpinField.setValue(0);
        codUbiTextField.setText(null);
        
    }
    private void Nuevo(){ // boton Nuevo
        
        idlibroTextField.setEnabled(true);
        tituloTextField.setEnabled(true);
        autorTextField.setEnabled(true);
        lugarPublicacionTextField.setEnabled(true);
        publicacionYearChooser.setEnabled(true);
        edicionTextField.setEnabled(true);
        editorialTextField.setEnabled(true);
        numPagTextField.setEnabled(true);
        cantSpinField.setEnabled(true);
        codUbiTextField.setEnabled(true);
        guardarBtn.setEnabled(true);
        modificarBtn.setEnabled(false);
        eliminarBtn.setEnabled(false);
        librosTabla.setEnabled(true);
        nuevoBtn.setEnabled(false);
        
    }
    private void RefrescarTabla() {// metodo para que aparesca en la tabla los elementos de la base de datos

        try {

            BDConexion miconexion = new BDConexion();
            String cons = "SELECT * FROM Libros";
            ResultSet consultas = miconexion.consulta(cons);

            ResultSetMetaData rsMd = consultas.getMetaData();
            int numeroColumnas = rsMd.getColumnCount();

            String titulos[] = {"ID", "Titulo", "Autor/es", "Lugar de Publicación", "Año de Publicación", "Edicion", "Editorial", "Numero de Páginas", "Cantidad", "Codigo de Ubicación"};
            DefaultTableModel modelo = new DefaultTableModel(null, titulos);
            this.librosTabla.setModel(modelo);

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
    
    
    private void Guardar(){// boton Guardar
        
        objetos.Libros ActualizarLibro = null;  //Para actulizar libro 
         
         String anPub = Integer.toString(publicacionYearChooser.getYear());
         String cant = Integer.toString(cantSpinField.getValue());
         if (idlibroTextField.getText().equals("") || tituloTextField.getText().equals("") || autorTextField.getText().equals("") || lugarPublicacionTextField.getText().equals("") ||  edicionTextField.getText().equals("")|| editorialTextField.getText().equals("") || numPagTextField.getText().equals("") || anPub.equals("") || cant.equals("")) {
                JOptionPane.showMessageDialog(null, "Llene las casillas en blanco");
                return;
          }
       
        
        objetos.Libros libro = new objetos.Libros(Integer.parseInt(idlibroTextField.getText()), tituloTextField.getText(), autorTextField.getText(), anPub, lugarPublicacionTextField.getText(), edicionTextField.getText(), editorialTextField.getText(), numPagTextField.getText(), cant, codUbiTextField.getText());
        BaseDeDatos.BDSentenciasLIB insert;
        BaseDeDatos.BDSentenciasLIB update;
        
         
         if (IDLibro!=0)// esta variable es utilizada para saber si se va a guardar o actualizar los datos
        {
        objetos.Libros libroActualizado = new objetos.Libros(IDLibro, tituloTextField.getText(), autorTextField.getText(), anPub, lugarPublicacionTextField.getText(), edicionTextField.getText(), editorialTextField.getText(), numPagTextField.getText(), cant, codUbiTextField.getText());
        }
         
       try {           
            if(IDLibro!=0){
            update = new BaseDeDatos.BDSentenciasLIB();
                update.updateLibros(libro);
                JOptionPane.showMessageDialog(null, "Actualizado Correctamente");
                IDLibro=0;
                tituloTextField.setText(null);
                autorTextField.setText(null);
                publicacionYearChooser.setYear(2015);
                lugarPublicacionTextField.setText(null);
                edicionTextField.setText(null);
                editorialTextField.setText(null);
                numPagTextField.setText(null);
                cantSpinField.setValue(0);
                codUbiTextField.setText(null);
                RefrescarTabla();
                inicio();
            }
            else {
                insert = new BaseDeDatos.BDSentenciasLIB();
                insert.insertLibros(libro);
                IDLibro=0;
                tituloTextField.setText(null);
                autorTextField.setText(null);
                publicacionYearChooser.setYear(2015);
                lugarPublicacionTextField.setText(null);
                edicionTextField.setText(null);
                editorialTextField.setText(null);
                numPagTextField.setText(null);
                cantSpinField.setValue(0);
                codUbiTextField.setText(null);
                RefrescarTabla();
                inicio();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Modificar(){
        
        idlibroTextField.setEnabled(false);
        tituloTextField.setEnabled(true);
        autorTextField.setEnabled(true);
        lugarPublicacionTextField.setEnabled(true);
        publicacionYearChooser.setEnabled(true);
        edicionTextField.setEnabled(true);
        editorialTextField.setEnabled(true);
        numPagTextField.setEnabled(true);
        cantSpinField.setEnabled(true);
        codUbiTextField.setEnabled(true);
        nuevoBtn.setEnabled(false);
        guardarBtn.setEnabled(true);
        modificarBtn.setEnabled(false);
        eliminarBtn.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        guardarBtn.setText("Actualizar");
        librosTabla.setEnabled(true);
    }
    
    private void Eliminar(){// boton Eliminar
        
        try {
            if (IDLibro==0) {
                JOptionPane.showMessageDialog(null, "Seleccione un Usuario de la Tabla para eliminarlo");
            } else {
         String anPub = Integer.toString(publicacionYearChooser.getYear());
         String cant = Integer.toString(cantSpinField.getValue());
                int row = librosTabla.getSelectedRow();
                int IdLib = (int) (librosTabla.getModel().getValueAt(row, 0));
                objetos.Libros libro = new objetos.Libros(IdLib, tituloTextField.getText(), autorTextField.getText(), anPub, lugarPublicacionTextField.getText(), edicionTextField.getText(), editorialTextField.getText(), numPagTextField.getText(), cant, codUbiTextField.getText());
                BaseDeDatos.BDSentenciasLIB deleteLibro;
                deleteLibro = new BaseDeDatos.BDSentenciasLIB();
                deleteLibro.deleteLibro(libro);
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
                IDLibro=0;
                tituloTextField.setText(null);
                autorTextField.setText(null);
                publicacionYearChooser.setYear(2015);
                lugarPublicacionTextField.setText(null);
                edicionTextField.setText(null);
                editorialTextField.setText(null);
                numPagTextField.setText(null);
                cantSpinField.setValue(0);
                codUbiTextField.setText(null);
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
        
        if (this.librosTabla.isEnabled()){
          String FechaNac;
         try {
            int row = librosTabla.getSelectedRow();
             
            tituloTextField.setText(librosTabla.getModel().getValueAt(row, 1).toString());
            autorTextField.setText(librosTabla.getModel().getValueAt(row, 2).toString());
            lugarPublicacionTextField.setText(librosTabla.getModel().getValueAt(row, 3).toString());
            publicacionYearChooser.setYear(Integer.parseInt(librosTabla.getModel().getValueAt(row, 4).toString()));
            edicionTextField.setText(librosTabla.getModel().getValueAt(row, 5).toString());
            editorialTextField.setText(librosTabla.getModel().getValueAt(row, 6).toString());
            numPagTextField.setText(librosTabla.getModel().getValueAt(row, 7).toString());
            cantSpinField.setValue(Integer.parseInt(librosTabla.getModel().getValueAt(row, 8).toString()));
            codUbiTextField.setText(librosTabla.getModel().getValueAt(row, 9).toString());
            
        
             this.modificarBtn.setEnabled(true);
             this.eliminarBtn.setEnabled(true);
             this.nuevoBtn.setEnabled(false);
              this.btnCancelar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        modificarBtn = new javax.swing.JButton();
        guardarBtn = new javax.swing.JButton();
        nuevoBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        idlibroTextField = new javax.swing.JTextField();
        edicionTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        editorialTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tituloTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        autorTextField = new javax.swing.JTextField();
        numPagTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lugarPublicacionTextField = new javax.swing.JTextField();
        publicacionYearChooser = new com.toedter.calendar.JYearChooser();
        cantSpinField = new com.toedter.components.JSpinField();
        jLabel11 = new javax.swing.JLabel();
        codUbiTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        librosTabla = new javax.swing.JTable();
        eliminarBtn = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        salirBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        modificarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_editar.png"))); // NOI18N
        modificarBtn.setText("Modificar");
        modificarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarBtnActionPerformed(evt);
            }
        });

        guardarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_guardar.png"))); // NOI18N
        guardarBtn.setText("Guardar");
        guardarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarBtnActionPerformed(evt);
            }
        });

        nuevoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_nuevo.png"))); // NOI18N
        nuevoBtn.setText("Nuevo");
        nuevoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoBtnActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("Gestión de Libros");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información del libro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel6.setText("Año de publicación:");

        jLabel5.setText("Edición:");

        idlibroTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idlibroTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("ID");

        jLabel2.setText("Título:");

        jLabel7.setText("Editorial:");

        jLabel8.setText("Número de páginas:");

        jLabel3.setText("Autor/es:");

        jLabel4.setText("Lugar de publicación:");

        jLabel9.setText("Cantidad:");

        jLabel11.setText("Codigo Ubicación:");

        librosTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Titulo", "Autor/es", "Lugar de publicacion", "Año de Publicacion ", "Edicion", "Editorial", "Numero de paginas", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(librosTabla);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codUbiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(numPagTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(editorialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edicionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lugarPublicacionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(publicacionYearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cantSpinField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(autorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tituloTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(idlibroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(idlibroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tituloTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(autorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lugarPublicacionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(publicacionYearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edicionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editorialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numPagTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cantSpinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codUbiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        eliminarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_eliminar.png"))); // NOI18N
        eliminarBtn.setText("Eliminar");
        eliminarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBtnActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        salirBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir_opt.png"))); // NOI18N
        salirBtn.setText("Salir");
        salirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(nuevoBtn)
                        .addGap(18, 18, 18)
                        .addComponent(guardarBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modificarBtn)
                        .addGap(18, 18, 18)
                        .addComponent(eliminarBtn)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(salirBtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(387, 387, 387)
                        .addComponent(jLabel10)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modificarBtn)
                    .addComponent(guardarBtn)
                    .addComponent(nuevoBtn)
                    .addComponent(eliminarBtn)
                    .addComponent(btnCancelar)
                    .addComponent(salirBtn))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idlibroTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idlibroTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idlibroTextFieldActionPerformed

    private void eliminarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBtnActionPerformed
        // TODO add your handling code here:
        Eliminar();
    }//GEN-LAST:event_eliminarBtnActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        inicio();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void nuevoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoBtnActionPerformed
        // TODO add your handling code here:
        Nuevo();
    }//GEN-LAST:event_nuevoBtnActionPerformed

    private void guardarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarBtnActionPerformed
        // TODO add your handling code here:
        Guardar();
    }//GEN-LAST:event_guardarBtnActionPerformed

    private void modificarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarBtnActionPerformed
        // TODO add your handling code here:
        Modificar();
    }//GEN-LAST:event_modificarBtnActionPerformed

    private void salirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirBtnActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_salirBtnActionPerformed

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
            java.util.logging.Logger.getLogger(GestionLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionLibros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField autorTextField;
    private javax.swing.JButton btnCancelar;
    private com.toedter.components.JSpinField cantSpinField;
    private javax.swing.JTextField codUbiTextField;
    private javax.swing.JTextField edicionTextField;
    private javax.swing.JTextField editorialTextField;
    private javax.swing.JButton eliminarBtn;
    private javax.swing.JButton guardarBtn;
    private javax.swing.JTextField idlibroTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable librosTabla;
    private javax.swing.JTextField lugarPublicacionTextField;
    private javax.swing.JButton modificarBtn;
    private javax.swing.JButton nuevoBtn;
    private javax.swing.JTextField numPagTextField;
    private com.toedter.calendar.JYearChooser publicacionYearChooser;
    private javax.swing.JButton salirBtn;
    private javax.swing.JTextField tituloTextField;
    // End of variables declaration//GEN-END:variables
}
