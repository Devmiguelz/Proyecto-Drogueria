/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexion.Conexion;
import Model.Cliente;
import Model.ComboProducto;
import Model.Factura;
import Model.Inventario;
import Model.Sistema;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 201611277427
 */
public class Facturar extends javax.swing.JFrame {

    /**
     * Creates new form RegistrarFactura
     */
    private String[] correos;
    private float TotalPagar;
    private String tecla;

    public Facturar() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("FACTURACION");
        setIconImage(new ImageIcon(getClass().getResource("/Img_Ventanas/barcode.png")).getImage());
        Cerrar();
        LLenarComboClientes();
        LLenarComboProductos();
        InicioTabla();
        Codigo();
        BtnQuitar.setEnabled(false);
        BtnSeleccionar.setEnabled(false);
        BtnActualizar.setVisible(false);
        ComboProducto action = new ComboProducto();
        ComboProducto.addItemListener(action);
    }
    DecimalFormat formato = new DecimalFormat("###,###.##");
    Cliente cliente = new Cliente();
    DefaultTableModel modelo = new DefaultTableModel();

    public final void Codigo() {
        Random ale = new Random();
        String codigo = "";
        for (int i = 0; i < 5; i++) {
            codigo += String.valueOf(ale.nextInt(9) + 1);
        }
        TxtCodigo.setText(codigo);

        Sistema venta = new Sistema();
        TxtIva.setText(String.valueOf(venta.ObtenerIva()));
    }

    public void TotalApagar() {
        float iva = ((getTotal() * Float.parseFloat(TxtIva.getText()) / 100.0f));
        float totalpagar = getTotal() + iva;
        this.TotalPagar = totalpagar;
        TxtPrecioIva.setText(String.valueOf(formato.format(iva)));
        TxtTotalPagar.setText(String.valueOf(formato.format(totalpagar)));
    }

    public final void InicioTabla() {
        modelo.addColumn("codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("SubTotal");
        TablaFactura.setModel(modelo);
        int[] anchos = {20, 90, 20, 30, 30};
        for (int i = 0; i < TablaFactura.getColumnCount(); i++) {
            TablaFactura.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaFactura.setRowHeight(20);
    }

    //Instancia Conexion BD
    Conexion conexion = new Conexion();
    Connection cn = conexion.conexion();

    public final void LLenarComboClientes() {
        String sql = "SELECT nombre,apellido,correo  FROM  clientes";
        int i = 0;
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            ComboCliente.addItem("....");
            correos = new String[cliente.CantidadCliente()];
            while (rs.next()) {
                correos[i] = rs.getString("correo");
                Object nombre = rs.getObject("nombre");
                Object apellido = rs.getObject("apellido");
                ComboCliente.addItem(nombre + " " + apellido);
                i++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }

    final void LLenarComboProductos() {
        String sql = "SELECT descripcion,cod_producto FROM  productos WHERE estado = 1";
        try {
            Statement consult = cn.createStatement();
            ResultSet rs = consult.executeQuery(sql);
            ComboProducto.addItem("....");
            while (rs.next()) {
                Object codigo = rs.getObject("cod_producto");
                Object nombre = rs.getObject("descripcion");
                ComboProducto.addItem(nombre.toString().toUpperCase() + " - " + codigo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }

    public final void Cerrar() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    comfirmar();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
        }
    }

    public void comfirmar() {
        this.dispose();
    }

    public int ObtenerCodigo(String producto) {
        int cod;
        String codigo = "";

        for (int x = 0; x < producto.length(); x++) {
            if (producto.charAt(x) == '-') {
                for (int i = x + 2; i < producto.length(); i++) {
                    codigo = codigo + producto.charAt(i);
                }
                break;
            }
        }
        cod = Integer.parseInt(codigo);
        return cod;
    }

    public void AgregarProducto(String producto, int cantidad) {
        int pro_codigo = ObtenerCodigo(producto);
        TablaFactura(pro_codigo, cantidad);
    }

    public void TablaFactura(int codigo, int cantidad) {
        int filas = TablaFactura.getRowCount();
        int fila = -1;
        if (filas > 0) {
            for (int i = 0; i < filas; i++) {
                int cod = Integer.parseInt(TablaFactura.getValueAt(i, 0).toString());
                if (codigo == cod) {
                    fila = i;
                }
            }
        }
        if (fila != -1) {
            Inventario inven = new Inventario();
            int can = Integer.parseInt(TablaFactura.getValueAt(fila, 2).toString()) + cantidad;
            if (inven.CantidadProducto(codigo, can)) {
                float precios = Float.parseFloat(TablaFactura.getValueAt(fila, 3).toString());
                float subtotal = (can * precios);
                TablaFactura.setValueAt(String.valueOf(can), fila, 2);
                TablaFactura.setValueAt(String.valueOf(subtotal), fila, 4);
            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar este prodcuto con esa cantidad " + can + "\n"
                        + "La cantidad a llevar debe ser menor a " + inven.ObtenerCantidad(codigo));
                SpinnerCant.setValue(1);
            }
        } else {
            DefaultTableModel model = (DefaultTableModel) this.TablaFactura.getModel();
            String sql = "SELECT * FROM productos WHERE cod_producto = '" + codigo + "' ";
            float precio;
            String[] datos = new String[5];
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    datos[1] = rs.getString(2);
                    datos[2] = String.valueOf(cantidad);
                    datos[3] = rs.getString(4);
                    precio = Float.parseFloat(rs.getString(4));
                    precio = (float) precio * cantidad;
                    datos[4] = String.valueOf(precio);
                    model.addRow(datos);
                }
                TablaFactura.setModel(model);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Error" + ex);
            }
        }
        SpinnerCant.requestFocus();
        Total();
        TotalApagar();

    }

    public void Total() {
        float total = getTotal();
        TxtSubTotal.setText(String.valueOf(formato.format(total)));
    }

    public float getTotal() {
        float total = 0;
        for (int i = 0; i < TablaFactura.getRowCount(); i++) {
            total += Float.parseFloat(TablaFactura.getValueAt(i, 4).toString());
        }
        return total;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner2 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaFactura = new javax.swing.JTable();
        BtnFacturar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        PorcentajeIva = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TxtIva = new javax.swing.JTextField();
        TxtSubTotal = new javax.swing.JTextField();
        BtnQuitar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        ComboProducto = new javax.swing.JComboBox<String>();
        jLabel4 = new javax.swing.JLabel();
        SpinnerCant = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtCodigo = new javax.swing.JTextField();
        BtnAñadir = new javax.swing.JButton();
        ComboCliente = new javax.swing.JComboBox<String>();
        BtnNuevo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        BtnSeleccionar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        TxtTotalPagar = new javax.swing.JTextField();
        Iva = new javax.swing.JLabel();
        TxtPrecioIva = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaFacturaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaFactura);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 188, 550, 131));

        BtnFacturar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        BtnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/facturar.png"))); // NOI18N
        BtnFacturar.setText("Guardar y Enviar Factura");
        BtnFacturar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFacturarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnFacturar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 490, -1, 40));

        BtnCancelar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        BtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/error.png"))); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.setMargin(new java.awt.Insets(5, 5, 5, 5));
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, -1, 40));

        PorcentajeIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PorcentajeIva.setText("% IVA :");
        getContentPane().add(PorcentajeIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 340, -1, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("SUBTOTAL :");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, -1, -1));

        TxtIva.setEditable(false);
        TxtIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        getContentPane().add(TxtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 340, 44, -1));

        TxtSubTotal.setEditable(false);
        TxtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TxtSubTotal.setForeground(new java.awt.Color(102, 102, 102));
        getContentPane().add(TxtSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 380, 115, -1));

        BtnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/quitar.png"))); // NOI18N
        BtnQuitar.setText("Quitar");
        BtnQuitar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        BtnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnQuitarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnQuitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 339, -1, 29));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Cant :");

        SpinnerCant.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Cod. Factura ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Cliente ");

        TxtCodigo.setEditable(false);

        BtnAñadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/carro.png"))); // NOI18N
        BtnAñadir.setToolTipText("Añadir Producto");
        BtnAñadir.setBorder(null);
        BtnAñadir.setBorderPainted(false);
        BtnAñadir.setContentAreaFilled(false);
        BtnAñadir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BtnAñadir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAñadir.setIconTextGap(-3);
        BtnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAñadirActionPerformed(evt);
            }
        });

        BtnNuevo.setText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Producto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ComboProducto, 0, 251, Short.MAX_VALUE)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 32, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SpinnerCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(BtnNuevo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnNuevo)
                    .addComponent(jLabel2))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SpinnerCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ComboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 14, 550, 160));

        BtnSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/all.png"))); // NOI18N
        BtnSeleccionar.setText("Seleccionar Todo");
        BtnSeleccionar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeleccionarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSeleccionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, -1, 29));

        BtnActualizar.setText(".");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("TOTAL A PAGAR :");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 440, -1, 20));

        TxtTotalPagar.setEditable(false);
        TxtTotalPagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TxtTotalPagar.setForeground(new java.awt.Color(51, 153, 0));
        getContentPane().add(TxtTotalPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 115, -1));

        Iva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Iva.setText("IVA :");
        getContentPane().add(Iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, -1, -1));

        TxtPrecioIva.setEditable(false);
        TxtPrecioIva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TxtPrecioIva.setForeground(new java.awt.Color(102, 102, 102));
        getContentPane().add(TxtPrecioIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 410, 115, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        comfirmar();
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void BtnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAñadirActionPerformed
        if (ComboCliente.getSelectedIndex() > 0 && ComboProducto.getSelectedIndex() > 0) {

            Inventario inven = new Inventario();
            int cantidad = (int) SpinnerCant.getValue();
            String producto = ComboProducto.getSelectedItem().toString();
            int codigo = ObtenerCodigo(producto);

            if (inven.CantidadProducto(codigo, cantidad)) {
                AgregarProducto(producto, cantidad);
                ComboProducto.setSelectedIndex(0);
                SpinnerCant.setValue(1);
            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar este prodcuto con esa cantidad " + cantidad + "\n"
                        + "La cantidad a llevar debe ser menor a " + inven.ObtenerCantidad(codigo));
                SpinnerCant.setValue(1);

            }
        } else {
            //Seleccione cliente o producto
        }
    }//GEN-LAST:event_BtnAñadirActionPerformed

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        new NuevoCliente().setVisible(true);
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void TablaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaFacturaMouseClicked
        if (TablaFactura.getRowCount() > 0) {
            BtnQuitar.setEnabled(true);
            BtnSeleccionar.setEnabled(true);
            BtnSeleccionar.setText("Seleccionar Todo");
        } else {
            BtnQuitar.setEnabled(false);
            BtnSeleccionar.setText("Seleccionar Todo");
            BtnSeleccionar.setEnabled(false);
        }
    }//GEN-LAST:event_TablaFacturaMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        BtnQuitar.setEnabled(false);
        BtnSeleccionar.setEnabled(false);
        TablaFactura.clearSelection();
        BtnSeleccionar.setText("Seleccionar Todo");
    }//GEN-LAST:event_formMouseClicked

    private void BtnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFacturarActionPerformed
        if (TablaFactura.getRowCount() > 0) {
            Calendar fecha;
            String Fecha;
            int codigo;
            int año, mes, dia;
            fecha = Calendar.getInstance();
            String Productos[][] = new String[TablaFactura.getRowCount()][5];
            int Codigos[] = new int[TablaFactura.getRowCount()];
            int Cantidades[] = new int[TablaFactura.getRowCount()];

            Date now = new Date(System.currentTimeMillis());
            SimpleDateFormat hour = new SimpleDateFormat("hh:mm:ss a");
            String hora = hour.format(now);

            año = fecha.get(Calendar.YEAR);
            mes = fecha.get(Calendar.MONTH) + 1;
            dia = fecha.get(Calendar.DAY_OF_MONTH);

            codigo = Integer.parseInt(TxtCodigo.getText());
            String clientes = ComboCliente.getSelectedItem().toString();
            Fecha = dia + "/" + mes + "/" + año;
            String empleado = Login.nombreEmpleado;

            String correo = correos[ComboCliente.getSelectedIndex() - 1];

            for (int i = 0; i < TablaFactura.getRowCount(); i++) {
                Codigos[i] = Integer.parseInt(TablaFactura.getValueAt(i, 0).toString());
                Cantidades[i] = Integer.parseInt(TablaFactura.getValueAt(i, 2).toString());
            }

            for (int i = 0; i < TablaFactura.getRowCount(); i++) {
                for (int j = 0; j < 5; j++) {
                    Productos[i][j] = TablaFactura.getValueAt(i, j).toString();
                }
            }
            Factura factura = new Factura(codigo, clientes, Fecha, hora, empleado, TotalPagar);
            factura.Insertar();
            factura.DetalleFactura(Codigos, Cantidades);
            factura.MenosProducto(Codigos, Cantidades);
            factura.EnviarFactura(correo, Productos);
            Sistema sistema = new Sistema();
            sistema.InsertarHistorial("Ha Registrado una Factura");
            this.dispose();
        } else {

        }
    }//GEN-LAST:event_BtnFacturarActionPerformed

    private void BtnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnQuitarActionPerformed
        int fila = TablaFactura.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) this.TablaFactura.getModel();
        if (fila >= 0) {
            int[] filasselec = TablaFactura.getSelectedRows();

            for (int i = filasselec.length - 1; i >= 0; i--) {
                model.removeRow(filasselec[i]);
            }
            Total();
            TotalApagar();
            BtnQuitar.setEnabled(false);
            BtnSeleccionar.setText("Seleccionar Todo");
            BtnSeleccionar.setEnabled(false);
        }
    }//GEN-LAST:event_BtnQuitarActionPerformed

    private void BtnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeleccionarActionPerformed
        if (BtnSeleccionar.getText().equals("Seleccionar Todo")) {
            for (int i = 0; i < TablaFactura.getRowCount(); i++) {
                TablaFactura.selectAll();
            }
            BtnSeleccionar.setText("Quitar Seleccion");
        } else {
            TablaFactura.clearSelection();
            BtnQuitar.setEnabled(false);
            BtnSeleccionar.setEnabled(false);
            BtnSeleccionar.setText("Seleccionar Todo");
        }

    }//GEN-LAST:event_BtnSeleccionarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        ComboCliente.removeAllItems();
        LLenarComboClientes();
        int item = ComboCliente.getItemCount() - 1;
        ComboCliente.setSelectedIndex(item);
    }//GEN-LAST:event_BtnActualizarActionPerformed

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
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Facturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Facturar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAñadir;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnFacturar;
    public javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnQuitar;
    private javax.swing.JButton BtnSeleccionar;
    public static javax.swing.JComboBox<String> ComboCliente;
    public static javax.swing.JComboBox<String> ComboProducto;
    private javax.swing.JLabel Iva;
    private javax.swing.JLabel PorcentajeIva;
    public static javax.swing.JSpinner SpinnerCant;
    public static javax.swing.JTable TablaFactura;
    public static javax.swing.JTextField TxtCodigo;
    private javax.swing.JTextField TxtIva;
    private javax.swing.JTextField TxtPrecioIva;
    private javax.swing.JTextField TxtSubTotal;
    private javax.swing.JTextField TxtTotalPagar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner2;
    // End of variables declaration//GEN-END:variables

}
