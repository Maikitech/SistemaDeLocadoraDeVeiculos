package com.mycompany.trabalhopoo2final.view;

import com.github.lgooddatepicker.components.DateTimePicker; // IMPORT (RT5)
import com.mycompany.trabalhopoo2final.dao.AluguelDAO;
import com.mycompany.trabalhopoo2final.dao.UsuarioDAO;
import com.mycompany.trabalhopoo2final.dao.VeiculoDAO;
import com.mycompany.trabalhopoo2final.entity.Aluguel;
import com.mycompany.trabalhopoo2final.entity.Usuario;
import com.mycompany.trabalhopoo2final.entity.Veiculo;
import com.mycompany.trabalhopoo2final.util.EntidadeListener;
import java.awt.Component;

import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class TelaGestaoAluguel extends javax.swing.JDialog {

    private EntidadeListener listener;
    private AluguelDAO aluguelDAO;
    private UsuarioDAO usuarioDAO;
    private VeiculoDAO veiculoDAO;
    
    private Aluguel aluguelParaEditar;
    
    // Componentes do DatePicker (RT5)
    private DateTimePicker pickerDataInicio;
    private DateTimePicker pickerDataFim;

    /**
     * Construtor para NOVO ALUGUEL (RF3)
     */
    public TelaGestaoAluguel(java.awt.Frame parent, boolean modal, EntidadeListener listener) {
        super(parent, modal);
        initComponents(); // Carrega o design
        
        this.listener = listener;
        this.aluguelDAO = new AluguelDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.veiculoDAO = new VeiculoDAO();
        this.aluguelParaEditar = null;
        
        inicializarComponentesExternos(); // Configura o DatePicker
        carregarCombos(); // Carrega usuários e veículos
        
        // --- Define padrões (RF3) ---
        pickerDataInicio.setDateTimePermissive(LocalDateTime.now());
        pickerDataFim.setDateTimePermissive(LocalDateTime.now());
        comboStatus.setSelectedItem("ABERTO");
        
        // --- Esconde campos de fechamento (RF3) ---
        jLabelKmFinal.setVisible(false);
        txtKmFinal.setVisible(false);
        comboStatus.setEnabled(false); // Trava o status
        
        pack(); // <-- Ajusta o tamanho da janela
        setLocationRelativeTo(parent); // <-- Re-centraliza
    }
    
    /**
     * Construtor para EDITAR/FECHAR ALUGUEL (RF4)
     */
    public TelaGestaoAluguel(java.awt.Frame parent, boolean modal, EntidadeListener listener, Aluguel aluguelParaEditar) {
        super(parent, modal);
        initComponents(); // Carrega o design
        
        this.listener = listener;
        this.aluguelDAO = new AluguelDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.veiculoDAO = new VeiculoDAO();
        this.aluguelParaEditar = aluguelParaEditar;
        
        inicializarComponentesExternos();
        carregarCombos();
        preencherCampos();
        
        // --- Mostra campos de fechamento (RF4) ---
        jLabelKmFinal.setVisible(true);
        txtKmFinal.setVisible(true);
        comboStatus.setEnabled(true); // Destrava o status
        setTitle("Editar / Fechar Aluguel");
        
        pack(); // <-- Ajusta o tamanho da janela
        setLocationRelativeTo(parent); // <-- Re-centraliza
    }
    
    private void carregarCombos() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        DefaultComboBoxModel<Usuario> modelUsuario = new DefaultComboBoxModel<>();
        modelUsuario.addAll(usuarios);
        comboUsuario.setModel(modelUsuario); 
        
        comboUsuario.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Usuario u) {
                    setText(u.getNome());
                }
                return this;
            }
        });
        
        List<Veiculo> veiculos = veiculoDAO.listarTodos();
        DefaultComboBoxModel<Veiculo> modelVeiculo = new DefaultComboBoxModel<>();
        modelVeiculo.addAll(veiculos);
        comboVeiculo.setModel(modelVeiculo); 
        
        comboVeiculo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Veiculo v) {
                    setText(v.getPlaca() + " (" + v.getModelo() + ")");
                }
                return this;
            }
        });
    }
    
    private void preencherCampos() {
        if (aluguelParaEditar != null) {
            for (int i = 0; i < comboUsuario.getModel().getSize(); i++) {
                if (comboUsuario.getModel().getElementAt(i).getId().equals(aluguelParaEditar.getUsuario().getId())) {
                    comboUsuario.setSelectedIndex(i);
                    break;
                }
            }
            
            for (int i = 0; i < comboVeiculo.getModel().getSize(); i++) {
                if (comboVeiculo.getModel().getElementAt(i).getId().equals(aluguelParaEditar.getVeiculo().getId())) {
                    comboVeiculo.setSelectedIndex(i);
                    break;
                }
            }
            
            pickerDataInicio.setDateTimePermissive(aluguelParaEditar.getDataInicio().atStartOfDay());
            pickerDataFim.setDateTimePermissive(aluguelParaEditar.getDataFim().atStartOfDay());
            
            txtKmInicial.setText(String.valueOf(aluguelParaEditar.getQuilometragemInicial()));
            
            if (aluguelParaEditar.getQuilometragemFinal() > 0) {
                 txtKmFinal.setText(String.valueOf(aluguelParaEditar.getQuilometragemFinal()));
            }
            comboStatus.setSelectedItem(aluguelParaEditar.getStatus());
        }
    }
    
    private void inicializarComponentesExternos() {
        pickerDataInicio = new DateTimePicker();
        pickerDataFim = new DateTimePicker();
        
        panelDataInicio.add(pickerDataInicio);
        panelDataFim.add(pickerDataFim);
    }
    
    // Construtor padrão (main)
    public TelaGestaoAluguel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicializarComponentesExternos();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalvar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboUsuario = new javax.swing.JComboBox<>();
        comboVeiculo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        comboStatus = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelDataInicio = new javax.swing.JPanel();
        panelDataFim = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelKmFinal = new javax.swing.JLabel();
        txtKmInicial = new javax.swing.JTextField();
        txtKmFinal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 700));

        btnSalvar.setBackground(new java.awt.Color(153, 255, 153));
        btnSalvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalvar.setText("SALVAR");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("CLIENTE:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("VEICULO:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("STATUS:");

        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ABERTO", "FECHADO", "ATRASADO" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("DATA INICIO:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("DATA FINAL:");

        panelDataInicio.setLayout(new java.awt.BorderLayout());

        panelDataFim.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("KM INICIAL:");

        jLabelKmFinal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelKmFinal.setText("KM FINAL:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("CADASTRO DE ALUGUEL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelKmFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                                .addGap(184, 184, 184))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(comboVeiculo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(panelDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(panelDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtKmFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtKmInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(41, 41, 41)))
                .addComponent(btnSalvar)
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(279, 279, 279))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(panelDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtKmInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelKmFinal)
                    .addComponent(txtKmFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addGap(20, 20, 20))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            // 1. Obter os dados da tela
            Usuario usuarioSelecionado = (Usuario) comboUsuario.getSelectedItem();
            Veiculo veiculoSelecionado = (Veiculo) comboVeiculo.getSelectedItem();
            
            LocalDate dataInicio = pickerDataInicio.getDateTimePermissive().toLocalDate();
            LocalDate dataFim = pickerDataFim.getDateTimePermissive().toLocalDate();
            
            // CORREÇÃO: Valida o KM INICIAL primeiro
            if (txtKmInicial.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Km Inicial é obrigatório.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int kmInicial = Integer.parseInt(txtKmInicial.getText());
            String status = (String) comboStatus.getSelectedItem();

            // 2. Validações
            if (usuarioSelecionado == null || veiculoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Cliente e Veículo são obrigatórios.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (dataFim.isBefore(dataInicio)) {
                JOptionPane.showMessageDialog(this, "A Data Fim não pode ser anterior à Data Início.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Verifica se é NOVO ou EDIÇÃO
            Aluguel aluguel;
            if (aluguelParaEditar != null) {
                aluguel = aluguelParaEditar; 
            } else {
                aluguel = new Aluguel(); 
            }
            
            // 4. Define os dados no objeto
            aluguel.setUsuario(usuarioSelecionado);
            aluguel.setVeiculo(veiculoSelecionado);
            aluguel.setDataInicio(dataInicio);
            aluguel.setDataFim(dataFim);
            aluguel.setQuilometragemInicial(kmInicial);
            aluguel.setStatus(status);
            
            // 5. Lógica de Fechamento (RF4)
            if (aluguelParaEditar != null && !txtKmFinal.getText().isEmpty()) {
                int kmFinal = Integer.parseInt(txtKmFinal.getText()); 
                if (kmFinal < kmInicial) {
                    JOptionPane.showMessageDialog(this, "Km Final não pode ser menor que a Inicial.", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                aluguel.setQuilometragemFinal(kmFinal);
            }

            // 6. Salva no banco
            aluguelDAO.salvar(aluguel);
            
            if (listener != null) {
                listener.entidadeSalva(); 
            }
            
            this.dispose();

        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "Quilometragem inválida! Insira apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluguel: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed
/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGestaoAluguel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaGestaoAluguel dialog = new TelaGestaoAluguel(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JComboBox<Usuario> comboUsuario;
    private javax.swing.JComboBox<Veiculo> comboVeiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelKmFinal;
    private javax.swing.JPanel panelDataFim;
    private javax.swing.JPanel panelDataInicio;
    private javax.swing.JTextField txtKmFinal;
    private javax.swing.JTextField txtKmInicial;
    // End of variables declaration//GEN-END:variables
}
