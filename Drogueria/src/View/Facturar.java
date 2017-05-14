/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static Controllers.Multilista.lista;
import Model.Cliente;
import Model.ComboProducto;
import Model.Factura;
import Model.Inventario;
import Model.Sistema;
import NodosMultilista.NodoDrogueria;
import NodosMultilista.NodoHijoDrogueria;
import com.sun.glass.events.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author 201611277427
 */
public class Facturar extends javax.swing.JFrame {

    /**
     * Creates new form RegistrarFactura
     */
    private float TotalPagar;
    public static NodoHijoDrogueria aux;
    private NodoHijoDrogueria hijocliente;
    private NodoHijoDrogueria hijoproducto;
    private ArrayList<Integer> codigos = new ArrayList<>();

    public Facturar() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("FACTURACION");
        setIconImage(new ImageIcon(getClass().getResource("/Img_Ventanas/barcode.png")).getImage());
        Cerrar();
        LLenarComboProductos();
        AutoCompleteDecorator.decorate(ComboProducto);
        InicioTabla();
        Codigo();
        BtnQuitar.setEnabled(false);
        BtnSeleccionar.setEnabled(false);
        BtnActualizar.setVisible(false);

        ComboProducto action = new ComboProducto();
        ComboProducto.addItemListener(action);

        TxtIdeCliente.requestFocus();
        BtnAñadir.setEnabled(false);
        BtnFacturar.setEnabled(false);
        ComboProducto.setEnabled(false);
        SpinnerCant.setEnabled(false);
        BtnNuevo.setEnabled(true);
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
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Stan");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("SubTotal");
        TablaFactura.setModel(modelo);
        int[] anchos = {20, 80, 20, 20, 30, 30};
        for (int i = 0; i < TablaFactura.getColumnCount(); i++) {
            TablaFactura.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        TablaFactura.setRowHeight(20);
    }

    final void LLenarComboProductos() {
        NodoDrogueria buscar = lista.BuscarPadre(1);
        NodoHijoDrogueria q;
        if (buscar != null) {
            q = buscar.hijo;
            ComboProducto.addItem("");
            while (q != null) {
                Object stan = q.stan;
                codigos.add(q.codigo);
                Object nombre = q.nombre;
                ComboProducto.addItem(nombre.toString().toUpperCase() + " - " + stan);
                q = q.sig;
            }
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

    public void AgregarProducto(int codigo, int cantidad) {
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
            int can = Integer.parseInt(TablaFactura.getValueAt(fila, 3).toString()) + cantidad;
            if (inven.CantidadProducto(codigo, can)) {
                float precios = Float.parseFloat(TablaFactura.getValueAt(fila, 4).toString());
                float subtotal = (can * precios);
                TablaFactura.setValueAt(String.valueOf(can), fila, 3);
                TablaFactura.setValueAt(String.valueOf(subtotal), fila, 5);
                Total();
                TotalApagar();
            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar este prodcuto con esa cantidad " + can + "\n"
                        + "La cantidad a llevar debe ser menor a " + inven.ObtenerCantidad(codigo));
                SpinnerCant.setValue(1);
            }
        } else {
            DefaultTableModel model = (DefaultTableModel) this.TablaFactura.getModel();

            float precio;
            String[] datos = new String[6];

            NodoDrogueria buscar = lista.BuscarPadre(1);
            NodoHijoDrogueria q;
            if (buscar != null) {
                q = buscar.hijo;
                while (q != null) {
                    if (q.codigo == codigo) {
                        hijoproducto = q;
                        break;
                    }
                    q = q.sig;
                }
            }
            datos[0] = String.valueOf(hijoproducto.codigo);
            datos[1] = hijoproducto.nombre;
            datos[2] = hijoproducto.stan;
            datos[3] = String.valueOf(cantidad);
            datos[4] = String.valueOf(hijoproducto.precio);
            precio = hijoproducto.precio;
            precio = (float) precio * cantidad;
            datos[5] = String.valueOf(precio);
            model.addRow(datos);
            TablaFactura.setModel(model);
            SpinnerCant.requestFocus();
            Total();
            TotalApagar();
        }
    }

    public void Total() {
        float total = getTotal();
        TxtSubTotal.setText(String.valueOf(formato.format(total)));
    }

    public float getTotal() {
        float total = 0;
        for (int i = 0; i < TablaFactura.getRowCount(); i++) {
            total += Float.parseFloat(TablaFactura.getValueAt(i, 5).toString());
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
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaFactura = TablaFactura = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2 && colIndex != 4 && colIndex != 5;
            }
        };
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
        BtnNuevo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        TxtIdeCliente = new javax.swing.JTextField();
        BtnAceptar = new javax.swing.JButton();
        BtnSeleccionar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        TxtTotalPagar = new javax.swing.JTextField();
        Iva = new javax.swing.JLabel();
        TxtPrecioIva = new javax.swing.JTextField();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Stan", "Cantidad", "Precio", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaFacturaMouseClicked(evt);
            }
        });
        TablaFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaFacturaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TablaFactura);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 188, 550, 131));

        BtnFacturar.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        BtnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img_Botones/facturar.png"))); // NOI18N
        BtnFacturar.setText("Imprimir y Enviar Factura");
        BtnFacturar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        BtnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFacturarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnFacturar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 490, -1, 40));

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

        ComboProducto.setEditable(true);
        ComboProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboProductoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ComboProductoKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Cant :");

        SpinnerCant.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        SpinnerCant.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SpinnerCantMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SpinnerCantMouseReleased(evt);
            }
        });

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

        TxtIdeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtIdeClienteKeyTyped(evt);
            }
        });

        BtnAceptar.setText("Aceptar");
        BtnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAceptarActionPerformed(evt);
            }
        });

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ComboProducto, 0, 251, Short.MAX_VALUE)
                            .addComponent(TxtIdeCliente))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 32, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SpinnerCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(BtnAceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnNuevo))))
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                    .addComponent(jLabel2)
                    .addComponent(TxtIdeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnAceptar)
                    .addComponent(BtnNuevo))
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
        if (ComboProducto.getSelectedIndex() >= 1) {
            Inventario inven = new Inventario();
            int cantidad = (int) SpinnerCant.getValue();
            int codigo = codigos.get(ComboProducto.getSelectedIndex() - 1);

            if (inven.CantidadProducto(codigo, cantidad)) {
                AgregarProducto(codigo, cantidad);
                ComboProducto.setSelectedIndex(0);
                SpinnerCant.setValue(1);
                ComboProducto.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "No puedes agregar este prodcuto con esa cantidad " + cantidad + "\n"
                        + "La cantidad a llevar debe ser menor a " + inven.ObtenerCantidad(codigo));
                SpinnerCant.setValue(1);

            }
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
            Fecha = dia + "/" + mes + "/" + año;
            String empleado = Login.nombreEmpleado;
            String clientes = hijocliente.nombre + " " + hijocliente.apellido;
            String correo = hijocliente.correo;

            for (int i = 0; i < TablaFactura.getRowCount(); i++) {
                Codigos[i] = Integer.parseInt(TablaFactura.getValueAt(i, 0).toString());
                Cantidades[i] = Integer.parseInt(TablaFactura.getValueAt(i, 3).toString());
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
            
            aux = new NodoHijoDrogueria();
            aux.codigo = codigo;
            aux.cliente = clientes;
            aux.fecha = Fecha;
            aux.hora = hora;
            aux.empleado = empleado;
            aux.total = TotalPagar;

            lista.InsertarHijo(5, aux);
            //factura.EnviarFactura(correo, Productos);
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
        hijocliente = new NodoHijoDrogueria();
        hijocliente = aux;
        TxtIdeCliente.setText(hijocliente.nombre + "  " + hijocliente.apellido);
        TxtIdeCliente.setEditable(false);
        BtnAñadir.setEnabled(true);
        BtnFacturar.setEnabled(true);
        ComboProducto.setEnabled(true);
        ComboProducto.requestFocus();
        SpinnerCant.setEnabled(true);
        BtnNuevo.setEnabled(false);
        BtnAceptar.setText("Cancelar");
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAceptarActionPerformed
        String boton = BtnAceptar.getText();
        if (boton.equals("Aceptar")) {
            if (!TxtIdeCliente.getText().equals("")) {
                int id = Integer.parseInt(TxtIdeCliente.getText());
                hijocliente = lista.BuscarCliente(2, id);
                if (hijocliente != null) {
                    TxtIdeCliente.setText(hijocliente.nombre + "  " + hijocliente.apellido);
                    TxtIdeCliente.setEditable(false);
                    BtnAñadir.setEnabled(true);
                    BtnFacturar.setEnabled(true);
                    ComboProducto.setEnabled(true);
                    ComboProducto.requestFocus();
                    SpinnerCant.setEnabled(true);
                    BtnNuevo.setEnabled(false);
                    BtnAceptar.setText("Cancelar");
                } else {
                    JOptionPane.showMessageDialog(null, "Numero de Identificacion no Registrada");
                }
            }
        } else if (boton.equals("Cancelar")) {
            TxtIdeCliente.setText("");
            TxtIdeCliente.requestFocus();
            BtnAñadir.setEnabled(false);
            TxtIdeCliente.setEditable(true);
            BtnFacturar.setEnabled(false);
            ComboProducto.setEnabled(false);
            SpinnerCant.setEnabled(false);
            BtnNuevo.setEnabled(true);
            BtnAceptar.setText("Aceptar");
        }


    }//GEN-LAST:event_BtnAceptarActionPerformed

    private void TxtIdeClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtIdeClienteKeyTyped
        char tecla = evt.getKeyChar();
        if (tecla == KeyEvent.VK_ENTER) {
            BtnAceptar.doClick();
        }
    }//GEN-LAST:event_TxtIdeClienteKeyTyped

    private void SpinnerCantMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SpinnerCantMousePressed
        System.out.println(SpinnerCant.getValue().toString());
    }//GEN-LAST:event_SpinnerCantMousePressed

    private void SpinnerCantMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SpinnerCantMouseReleased
        int cantidad = (int) SpinnerCant.getValue();
        System.out.println(cantidad);
    }//GEN-LAST:event_SpinnerCantMouseReleased

    private void ComboProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboProductoKeyPressed
        char c = evt.getKeyChar();
        try {
            SpinnerCant.setValue(Integer.parseInt(String.valueOf(c)));
        } catch (Exception e) {
            System.out.println("");
        }

    }//GEN-LAST:event_ComboProductoKeyPressed

    private void ComboProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboProductoKeyTyped
        char tecla = evt.getKeyChar();
        if (tecla == KeyEvent.VK_SPACE) {
            BtnAñadir.doClick();
        }
    }//GEN-LAST:event_ComboProductoKeyTyped

    private void TablaFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaFacturaKeyPressed
        Total();
        TotalApagar();
    }//GEN-LAST:event_TablaFacturaKeyPressed

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
    private javax.swing.JButton BtnAceptar;
    public static javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAñadir;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnFacturar;
    public javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnQuitar;
    private javax.swing.JButton BtnSeleccionar;
    public static javax.swing.JComboBox<String> ComboProducto;
    private javax.swing.JLabel Iva;
    private javax.swing.JLabel PorcentajeIva;
    public static javax.swing.JSpinner SpinnerCant;
    public static javax.swing.JTable TablaFactura;
    public static javax.swing.JTextField TxtCodigo;
    private javax.swing.JTextField TxtIdeCliente;
    private javax.swing.JTextField TxtIva;
    private javax.swing.JTextField TxtPrecioIva;
    private javax.swing.JTextField TxtSubTotal;
    private javax.swing.JTextField TxtTotalPagar;
    private javax.swing.JInternalFrame jInternalFrame1;
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
