/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz_Grafica;

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
public class GestionUsuarios extends javax.swing.JFrame {
    
    Connection conn = null;
    ResultSet rs = null;
    Statement pst = null;
    int IDusu=0;// VARIABLE que representa la matricula, para poder guardar o modificar en la base de datos
     String cbTipoDeUsuario;
     String cbSexo;
     String cbCarrera;
    /**
     * Creates new form CrearUsuariosDemo
     */
    DefaultTableModel md;
    public GestionUsuarios() {

        initComponents();

        RefrescarTabla();
        
        inicio();
        //Tabla_Usuarios.setEnabled(false);

    }
  
    private void RefrescarTabla() {// metodo para que aparesca en la tabla los elementos de la base de datos

        try {

            BDConexion miconexion = new BDConexion();
            String cons = "SELECT * FROM Usuarios";
            ResultSet consultas = miconexion.consulta(cons);

            ResultSetMetaData rsMd = consultas.getMetaData();
            int numeroColumnas = rsMd.getColumnCount();

            String titulos[] = {"Matrícula", "Nombre", "Apellido","Contraseña", "Tipo de Usuario", "Fecha de Nacimiento", "Sexo", "Carrera"};
            DefaultTableModel modelo = new DefaultTableModel(null, titulos);
            this.Tabla_Usuarios.setModel(modelo);

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
    
    
    
    private void inicio(){
        
        txt_Matricula.setEnabled(false);
        txt_Nombre.setEnabled(false);
        txt_Apellido.setEnabled(false);
        txtContraseña.setEnabled(false);
        ComboBox_TipoU.setEnabled(false);
        dc_fechanac.setEnabled(false);
        ComboBox_Sexo.setEnabled(false);
        ComboBox_Carrera.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setText("Guardar");
        Tabla_Usuarios.setEnabled(true);
        txt_Matricula.setText(null);
        txt_Nombre.setText(null);
        txt_Apellido.setText(null);
        txtContraseña.setText(null);
        ComboBox_TipoU.setSelectedIndex(0);
        dc_fechanac.setDate(null);
        ComboBox_Sexo.setSelectedIndex(0);
        ComboBox_Carrera.setSelectedIndex(0);
        IDusu=0;
        
    }

    private void TocarJtabla(){
        
        if (this.Tabla_Usuarios.isEnabled()){
          String FechaNac;
         try {
            int row = Tabla_Usuarios.getSelectedRow();
             DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

            IDusu= (int) (Tabla_Usuarios.getModel().getValueAt(row, 0));
            txt_Matricula.setText(Tabla_Usuarios.getModel().getValueAt(row, 0).toString());
            txt_Nombre.setText(Tabla_Usuarios.getModel().getValueAt(row, 1).toString());
            txt_Apellido.setText(Tabla_Usuarios.getModel().getValueAt(row, 2).toString());
            txtContraseña.setText(Tabla_Usuarios.getModel().getValueAt(row, 3).toString());
            cbTipoDeUsuario = (Tabla_Usuarios.getModel().getValueAt(row, 4).toString());
            FechaNac = (Tabla_Usuarios.getModel().getValueAt(row, 5).toString());
            java.util.Date date = formatter.parse(FechaNac);
            dc_fechanac.setDate(date);
            cbSexo = (Tabla_Usuarios.getModel().getValueAt(row, 6).toString());
            cbCarrera = (Tabla_Usuarios.getModel().getValueAt(row, 7).toString());
            

            
            if (cbTipoDeUsuario.equals("Administrador")) {
                ComboBox_TipoU.setSelectedIndex(1);
            }
            if (cbTipoDeUsuario.equals("Estudiante")) {
                ComboBox_TipoU.setSelectedIndex(2);
            }
             if (cbSexo.equals("Masculino")) {
                ComboBox_Sexo.setSelectedIndex(1);
            }
               if (cbSexo.equals("Femenino")) {
                ComboBox_Sexo.setSelectedIndex(2);
            }
        
         if (cbCarrera.equals("Administración")) {
                ComboBox_Carrera.setSelectedIndex(1);
            }
         if (cbCarrera.equals("Mercadeo")) {
                ComboBox_Carrera.setSelectedIndex(2);
            }
         if (cbCarrera.equals("Publicidad")) {
                ComboBox_Carrera.setSelectedIndex(3);
            }
         if (cbCarrera.equals("Abogado")) {
                ComboBox_Carrera.setSelectedIndex(4);
            }
         if (cbCarrera.equals("Psicología")) {
                ComboBox_Carrera.setSelectedIndex(5);
            }
         if (cbCarrera.equals("TIC")) {
                ComboBox_Carrera.setSelectedIndex(6);
            }
         if (cbCarrera.equals("Ingeniería Civil")) {
                ComboBox_Carrera.setSelectedIndex(7);
            }
         if (cbCarrera.equals("Ingeniería Industrial")) {
                ComboBox_Carrera.setSelectedIndex(8);
            }
         if (cbCarrera.equals("")) {
                ComboBox_Carrera.setSelectedIndex(9);
            }
        
             this.btnModificar.setEnabled(true);
             this.btnEliminar.setEnabled(true);
             this.btnNuevo.setEnabled(false);
              this.btnCancelar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    }
    
    private void Guardar(){// boton Guardar
        
        objetos.Usuarios UsuariofulanoActualizado = null;// este se usara cuando se actualizen los datos
        
         String TipoDeUsuario = (String) ComboBox_TipoU.getSelectedItem();
         String Sexo = (String) ComboBox_Sexo.getSelectedItem();
         String Carrera = (String) ComboBox_Carrera.getSelectedItem();
        java.util.Date FechaParaComprobar = dc_fechanac.getDate();
         //dc_fechanac == null
    
         if (txt_Matricula.getText().equals("") || txt_Nombre.getText().equals("") || txt_Apellido.getText().equals("") || txtContraseña.getText().equals("") ||  TipoDeUsuario.equals("Seleccione")|| FechaParaComprobar==null || dc_fechanac == null|| Sexo.equals("Seleccione") || Carrera.equals("Seleccione")) {
                JOptionPane.showMessageDialog(null, "Llene las casillas en blanco");
                return;
            }
         
//metodo para comprobar si la matricula que se agregara esta ya almacenada en la tabla
         
          int totalRow = Tabla_Usuarios.getRowCount();//total de filas en la tabla
           
        //totalRow -= 1;
       //  JOptionPane.showMessageDialog(this,totalRow );
        int[]Matriculas=new int[totalRow];// array donde se ira almacenando todas las matriculas que estan en la tabla
          int MatriculaTabla;
        for (int i = 0; i < (totalRow); i++) {// aqui se ira almacenando las matriculas de la tabla en el array
            MatriculaTabla= Integer.parseInt(String.valueOf(Tabla_Usuarios.getValueAt(i, 0)));
            Matriculas[i]=MatriculaTabla;
        }
         int Matricula = Integer.parseInt(txt_Matricula.getText());
         String nombreBoton = btnGuardar.getText();
        
        for(int i=0;i<Matriculas.length;i++){
    if(Matricula==Matriculas[i] && nombreBoton.equals("Guardar") ){ //aqui se compara la matricula introducida con las de la tabla para ver si hay alguna igual
       
        JOptionPane.showMessageDialog(null, "No se Pueden repetir Matriculas/ID");
                return ;
                
        
    }}
         
        
        DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");// para convertir al formato de la fecha que acepta sqlite
        String FechaNac = fecha.format(dc_fechanac.getDate());
        objetos.Usuarios Usuariofulano = new objetos.Usuarios(Matricula, txt_Nombre.getText(), txt_Apellido.getText(), txtContraseña.getText(), TipoDeUsuario, FechaNac, Sexo, Carrera);
        BaseDeDatos.BDSentencias insert;
        BaseDeDatos.BDSentencias update;
         
         if (IDusu!=0)// esta variable es utilizada para saber si se va a guardar o actualizar los datos
        {
        UsuariofulanoActualizado = new objetos.Usuarios(IDusu , txt_Nombre.getText(), txt_Apellido.getText(),txtContraseña.getText(),TipoDeUsuario, FechaNac,Sexo,Carrera);
        }
         
       try {
             
            
            if(IDusu!=0){
            update = new BaseDeDatos.BDSentencias();
                update.update(Usuariofulano);
                JOptionPane.showMessageDialog(null, "Actualizado Correctamente");
                IDusu=0;
                txt_Matricula.setText(null);
                txt_Nombre.setText(null);
                txt_Apellido.setText(null);
                txtContraseña.setText(null);
                ComboBox_TipoU.setSelectedIndex(0);
                dc_fechanac.setDate(null);
                ComboBox_Sexo.setSelectedIndex(0);
                ComboBox_Carrera.setSelectedIndex(0);
                RefrescarTabla();
                inicio();
            }
            else {
                insert = new BaseDeDatos.BDSentencias();
                insert.insert(Usuariofulano);
                JOptionPane.showMessageDialog(null, "Guardado Correctamente");
                txt_Matricula.setText(null);
                txt_Nombre.setText(null);
                txt_Apellido.setText(null);
                txtContraseña.setText(null);
                ComboBox_TipoU.setSelectedIndex(0);
                dc_fechanac.setDate(null);
                ComboBox_Sexo.setSelectedIndex(0);
                ComboBox_Carrera.setSelectedIndex(0);
                RefrescarTabla();
                inicio();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Nuevo(){ // boton Nuevo
        
        txt_Matricula.setEnabled(true);
        txt_Nombre.setEnabled(true);
        txt_Apellido.setEnabled(true);
        txtContraseña.setEnabled(true);
        ComboBox_TipoU.setEnabled(true);
        dc_fechanac.setEnabled(true);
        ComboBox_Sexo.setEnabled(true);
        ComboBox_Carrera.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        Tabla_Usuarios.setEnabled(false);
    }
    
    private void Modificar(){ // boton Modificar
        
         txt_Matricula.setEnabled(false);
        txt_Nombre.setEnabled(true);
        txt_Apellido.setEnabled(true);
        txtContraseña.setEnabled(true);
        ComboBox_TipoU.setEnabled(true);
        dc_fechanac.setEnabled(true);
        ComboBox_Sexo.setEnabled(true);
        ComboBox_Carrera.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        btnGuardar.setText("Actualizar");
        Tabla_Usuarios.setEnabled(false);
    }
    
    private void Eliminar(){// boton Eliminar
        
        try {
            if (IDusu==0) {
                JOptionPane.showMessageDialog(null, "Seleccione un Usuario de la Tabla para eliminarlo");
            } else {
                String TipoDeUsuario = (String) ComboBox_TipoU.getSelectedItem();
         String Sexo = (String) ComboBox_Sexo.getSelectedItem();
         String Carrera = (String) ComboBox_Carrera.getSelectedItem();
         DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");// para convertir al formato de la fecha que acepta sqlite
		String FechaNac = fecha.format(dc_fechanac.getDate());
                int Matricula = Integer.parseInt(txt_Matricula.getText());
                objetos.Usuarios Usuariofulano  =  new objetos.Usuarios(Matricula, txt_Nombre.getText(), txt_Apellido.getText(), txtContraseña.getText(),TipoDeUsuario, FechaNac,Sexo,Carrera);
                BaseDeDatos.BDSentencias delete;
                delete = new BaseDeDatos.BDSentencias();
                delete.delete(Usuariofulano);
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
                IDusu=0;
                txt_Matricula.setText(null);
                txt_Nombre.setText(null);
                txt_Apellido.setText(null);
                txtContraseña.setText(null);
                ComboBox_TipoU.setSelectedIndex(0);
                dc_fechanac.setDate(null);
                ComboBox_Sexo.setSelectedIndex(0);
                ComboBox_Carrera.setSelectedIndex(0);
                RefrescarTabla();
                inicio();
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestionUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    
    private void CargarTabla(){
        
        try{
             String titulos[] = {"Matrícula", "Nombre", "Apellido", "Tipo de Usuario", "Fecha de Nacimiento", "Sexo","Carrera"};
             md = new DefaultTableModel(null, titulos);
             
             String fila[] = new String [7];
             BDConexion miconexion = new BDConexion();
             String cons = "select * from Usuarios";
             ResultSet r;
             r = miconexion.consulta(cons);
             int c = 1;
             while (r.next()){
                 fila[0] = String.valueOf(c) + "o";
                 fila[1] = r.getString(1);
                 fila[2] = r.getString(2);
                 fila[3] = r.getString(3);
                 fila[4] = r.getString(4);
                 fila[5] = r.getString(5);
                 fila[6] = r.getString(6);
                 fila[7] = r.getString(7);
                 md.addRow(fila);
                 c++;
             }
             
             Tabla_Usuarios.setModel(md);
             this.Tabla_Usuarios.setModel(md);
             
        } catch (Exception e) {
           // System.out.println(e.getMessage());
            System.out.println("hola");
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
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Usuarios = new javax.swing.JTable();
        txt_Matricula = new javax.swing.JTextField();
        txt_Nombre = new javax.swing.JTextField();
        txt_Apellido = new javax.swing.JTextField();
        ComboBox_TipoU = new javax.swing.JComboBox();
        ComboBox_Sexo = new javax.swing.JComboBox();
        ComboBox_Carrera = new javax.swing.JComboBox();
        dc_fechanac = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion del Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel8.setText("Sexo:");

        jLabel9.setText("Carrera:");

        jLabel2.setText("Matrícula:");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Apellido:");

        jLabel6.setText("Tipo de Usuario:");

        jLabel7.setText("Fecha de Nacimiento:");

        Tabla_Usuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Matrícula", "Nombre", "Apellido", "Contraseña", "Nombre de Usario", "Tipo de Usuario", "Fecha de Nacimiento", "Sexo", "Carrera"
            }
        ));
        Tabla_Usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_UsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Usuarios);

        txt_Matricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MatriculaActionPerformed(evt);
            }
        });
        txt_Matricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_MatriculaKeyTyped(evt);
            }
        });

        txt_Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NombreActionPerformed(evt);
            }
        });
        txt_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_NombreKeyTyped(evt);
            }
        });

        txt_Apellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ApellidoActionPerformed(evt);
            }
        });

        ComboBox_TipoU.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Administrador", "Estudiante" }));

        ComboBox_Sexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Masculino", "Femenino" }));

        ComboBox_Carrera.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Administración", "Mercadeo", "Publicidad", "Abogado", "Psicología", "TIC", "Ingeniería Civil", "Ingeniería Industrial", " " }));

        jLabel5.setText("Contraseña:");

        txtContraseña.setText("jPasswordField1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addGap(57, 57, 57)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ComboBox_Sexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Nombre)
                    .addComponent(txt_Matricula)
                    .addComponent(txt_Apellido)
                    .addComponent(ComboBox_TipoU, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dc_fechanac, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBox_Carrera, 0, 127, Short.MAX_VALUE)
                    .addComponent(txtContraseña))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_Matricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBox_TipoU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dc_fechanac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBox_Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBox_Carrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("Gestión de Usuarios");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_MatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MatriculaActionPerformed

    private void txt_NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NombreActionPerformed

    private void txt_ApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ApellidoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        
        Eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        
      Guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txt_MatriculaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_MatriculaKeyTyped
        // TODO add your handling code here:
       
        char c = evt.getKeyChar();

        if (Character.isLetter(c)) {
            getToolkit().beep();

            evt.consume();

              // JOptionPane.showMessageDialog(this, "Ingresa Solo Numeros");
        }
                     
       
    }//GEN-LAST:event_txt_MatriculaKeyTyped

    private void txt_NombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NombreKeyTyped
        // TODO add your handling code here:
        
         char c=evt.getKeyChar(); 
             
         
          if(Character.isDigit(c)) { 
              getToolkit().beep(); 
               
              evt.consume(); 
               
             // Error.setText("Ingresa Solo Letras"; 
               
          } 
    }//GEN-LAST:event_txt_NombreKeyTyped

    private void Tabla_UsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_UsuariosMouseClicked
        // TODO add your handling code here:
      TocarJtabla();
    }//GEN-LAST:event_Tabla_UsuariosMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        
     Nuevo();
      
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        
       Modificar();
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
inicio();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    new GestionUsuarios().setVisible(true);
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboBox_Carrera;
    private javax.swing.JComboBox ComboBox_Sexo;
    private javax.swing.JComboBox ComboBox_TipoU;
    private javax.swing.JTable Tabla_Usuarios;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private com.toedter.calendar.JDateChooser dc_fechanac;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txt_Apellido;
    private javax.swing.JTextField txt_Matricula;
    private javax.swing.JTextField txt_Nombre;
    // End of variables declaration//GEN-END:variables
}
