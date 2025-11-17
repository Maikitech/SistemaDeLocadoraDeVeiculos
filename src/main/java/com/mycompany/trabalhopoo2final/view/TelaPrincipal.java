package com.mycompany.trabalhopoo2final.view;

// Imports de DAO e Entity
import com.mycompany.trabalhopoo2final.util.DateCellRenderer;
import com.mycompany.trabalhopoo2final.dao.AluguelDAO;
import com.mycompany.trabalhopoo2final.dao.UsuarioDAO;
import com.mycompany.trabalhopoo2final.dao.VeiculoDAO;
import com.mycompany.trabalhopoo2final.entity.Aluguel;
import com.mycompany.trabalhopoo2final.entity.Usuario;
import com.mycompany.trabalhopoo2final.entity.Veiculo;
import com.mycompany.trabalhopoo2final.util.ColorCellRenderer;
import com.mycompany.trabalhopoo2final.util.EntidadeListener;
import com.mycompany.trabalhopoo2final.util.StatusIconRenderer;

// Imports do Java
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.Map;
import javax.swing.JOptionPane;

public class TelaPrincipal extends javax.swing.JFrame implements EntidadeListener {

    // DAOs
    private final UsuarioDAO usuarioDAO;
    private final VeiculoDAO veiculoDAO; 
    private final AluguelDAO aluguelDAO; 

    // Índices das Abas
    private static final int ABA_USUARIOS = 0;
    private static final int ABA_VEICULOS = 1;
    private static final int ABA_ALUGUEIS = 2; 

    public TelaPrincipal() {
        initComponents(); 
        setLocationRelativeTo(null); 
        
        this.usuarioDAO = new UsuarioDAO(); 
        this.veiculoDAO = new VeiculoDAO();
        this.aluguelDAO = new AluguelDAO(); 
        
        jTableUsuarios.setAutoCreateRowSorter(true);
        jTableVeiculos.setAutoCreateRowSorter(true);
        jTableAlugueis.setAutoCreateRowSorter(true); 
        
        // --- ADICIONE ESTE BLOCO DE CÓDIGO AQUI ---
        //
        
        // Define larguras para a tabela USUARIOS
        // Coluna 0 (ID)
        jTableUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTableUsuarios.getColumnModel().getColumn(0).setMaxWidth(60); // Opcional: define largura máxima

        // Define larguras para a tabela VEICULOS
        // Coluna 0 (ID)
        jTableVeiculos.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTableVeiculos.getColumnModel().getColumn(0).setMaxWidth(60);
        // Coluna 5 (COR)
        jTableVeiculos.getColumnModel().getColumn(5).setPreferredWidth(50);
        
        // Define larguras para a tabela ALUGUEIS
        // Coluna 0 (ID)
        jTableAlugueis.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTableAlugueis.getColumnModel().getColumn(0).setMaxWidth(60);
        // Coluna 4 (COR)
        jTableAlugueis.getColumnModel().getColumn(4).setPreferredWidth(40);
        
        // --- FIM DO BLOCO NOVO ---
        
        carregarTodasAsTabelas();
        aplicarRenderizadores();
    }
    
    // --- MÉTODOS DE DADOS ---
    
    private void carregarTodasAsTabelas() {
        carregarTabelaUsuarios();
        carregarTabelaVeiculos();
        carregarTabelaAlugueis(); 
    }
    
    private void carregarTabelaUsuarios() {
        DefaultTableModel model = (DefaultTableModel) jTableUsuarios.getModel();
        model.setRowCount(0); 
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                model.addRow(new Object[]{
                    u.getId(), u.getNome(), u.getEmail(), u.getTelefone()
                });
            }
        }
    }
    
    private void carregarTabelaVeiculos() {
        DefaultTableModel model = (DefaultTableModel) jTableVeiculos.getModel();
        model.setRowCount(0); 
        List<Veiculo> veiculos = veiculoDAO.listarTodos();
        if (veiculos != null) {
            for (Veiculo v : veiculos) {
                model.addRow(new Object[]{
                    v.getId(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getAno(), v.getCor()
                });
            }
        }
    }
    
private void carregarTabelaAlugueis() { 
        DefaultTableModel model = (DefaultTableModel) jTableAlugueis.getModel();
        model.setRowCount(0); 
        List<Aluguel> alugueis = aluguelDAO.listarTodos();
        
        if (alugueis != null) {
            for (Aluguel a : alugueis) {
                model.addRow(new Object[]{
                    a.getId(),
                    a.getUsuario().getNome(), 
                    a.getVeiculo().getPlaca(),
                    a.getVeiculo().getModelo(),
                    a.getVeiculo().getCor(),       // <-- ADICIONADO AQUI
                    a.getDataInicio(),
                    a.getDataFim(),
                    a.getQuilometragemInicial(),
                    a.getQuilometragemFinal(),
                    a.getStatus() 
                });
            }
        }
    }
    
    // --- MÉTODOS DE RENDERER (RT6) --- 
    
    private void aplicarRenderizadores() {
        // 1. A paleta de cores
        Map<String, Color> palette = Map.of(
            "vermelho", new Color(0xC62828), "azul", new Color(0x1565C0),
            "preto", Color.BLACK, "branco", Color.WHITE, "prata", Color.LIGHT_GRAY,
            "amarelo", Color.YELLOW, "verde", new Color(0x2E7D32)
        );
        
        // 2. Tabela VEICULOS 
        jTableVeiculos.getColumnModel().getColumn(5).setCellRenderer(new ColorCellRenderer(palette));
        
        
        
        //  Aplica o renderer de Cor na nova coluna "COR" (índice 4) da tabela ALUGUEIS
        jTableAlugueis.getColumnModel().getColumn(4).setCellRenderer(new ColorCellRenderer(palette));

        // 4. Move o renderer de Data para os índices 5 e 6
        DateCellRenderer dateRenderer = new DateCellRenderer();
        jTableAlugueis.getColumnModel().getColumn(5).setCellRenderer(dateRenderer); // DATA INICIO (era 4)
        jTableAlugueis.getColumnModel().getColumn(6).setCellRenderer(dateRenderer); // DATA FIM (era 5)

        // 5.Move o renderer de Status para o índice 9
        // (Se você desativou, mantenha a linha comentada)
        jTableAlugueis.getColumnModel().getColumn(9).setCellRenderer(new StatusIconRenderer()); // (era 8)
    }

    // --- MÉTODO DE LISTENER (RT2) ---
    @Override
    public void entidadeSalva() {
        carregarTodasAsTabelas(); // Atualiza full
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnNovo = new javax.swing.JButton();
        jTabbedPaneAbas = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableVeiculos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableAlugueis = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 700));

        btnNovo.setBackground(new java.awt.Color(204, 255, 204));
        btnNovo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNovo.setText("NOVO");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        jTabbedPaneAbas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "E-MAIL", "TELEFONE"
            }
        ));
        jScrollPane1.setViewportView(jTableUsuarios);

        jTabbedPaneAbas.addTab("USUARIOS", jScrollPane1);

        jTableVeiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Placa", "Marca", "Modelo", "Ano", "Cor"
            }
        ));
        jScrollPane2.setViewportView(jTableVeiculos);

        jTabbedPaneAbas.addTab("VEICULOS", jScrollPane2);

        jTableAlugueis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CLIENTE", "PLACA", "VEICULO", "COR", "DATA INICIO", "DATA FIM", "KM INICIAL", "KM FINAL", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableAlugueis);

        jTabbedPaneAbas.addTab("ALUGUEIS", jScrollPane3);

        btnEditar.setBackground(new java.awt.Color(255, 255, 153));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 0, 0));
        btnExcluir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExcluir.setText("EXCLUIR");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("SISTEMA DE LOCAÇÃO DE VEÍCULOS MAIKI");

        jLabel2.setText("Copyright © 2025. Todos os direitos reservados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPaneAbas, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jTabbedPaneAbas, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        int abaSelecionada = jTabbedPaneAbas.getSelectedIndex();
        
        switch (abaSelecionada) {
            case ABA_USUARIOS:
                TelaCadastroUsuario telaCadUsuario = new TelaCadastroUsuario(this, true, this);
                telaCadUsuario.setVisible(true);
                break;
            case ABA_VEICULOS:
                TelaCadastroVeiculo telaCadVeiculo = new TelaCadastroVeiculo(this, true, this);
                telaCadVeiculo.setVisible(true);
                break;
            // --- CORREÇÃO AQUI ---
            case ABA_ALUGUEIS:
                TelaGestaoAluguel telaCadAluguel = new TelaGestaoAluguel(this, true, this);
                telaCadAluguel.setVisible(true);
                break;
            // --- FIM DA CORREÇÃO ---
        }
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
      int abaSelecionada = jTabbedPaneAbas.getSelectedIndex();
        
        switch (abaSelecionada) {
            case ABA_USUARIOS:
                editarUsuarioSelecionado();
                break;
            case ABA_VEICULOS:
                editarVeiculoSelecionado();
                break;
            case ABA_ALUGUEIS:
                editarAluguelSelecionado();
                break;
        
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int abaSelecionada = jTabbedPaneAbas.getSelectedIndex();
        
        switch (abaSelecionada) {
            case ABA_USUARIOS:
                excluirUsuarioSelecionado();
                break;
            case ABA_VEICULOS:
                excluirVeiculoSelecionado();
                break;
            case ABA_ALUGUEIS:
                excluirAluguelSelecionado();
                break;
        }
    }//GEN-LAST:event_btnExcluirActionPerformed
// --- MÉTODOS AUXILIARES PARA AS AÇÕES ---
    
    private void editarUsuarioSelecionado() {
        int selectedRow = jTableUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedRow = jTableUsuarios.convertRowIndexToModel(selectedRow);
        Long idUsuario = (Long) jTableUsuarios.getModel().getValueAt(selectedRow, 0);
        Usuario usuarioParaEditar = usuarioDAO.buscarPorId(idUsuario);
        
        if (usuarioParaEditar != null) {
            TelaCadastroUsuario telaCadastro = new TelaCadastroUsuario(this, true, this, usuarioParaEditar);
            telaCadastro.setVisible(true);
        }
    }
    
    private void excluirUsuarioSelecionado() {
        int selectedRow = jTableUsuarios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedRow = jTableUsuarios.convertRowIndexToModel(selectedRow);
        Long idUsuario = (Long) jTableUsuarios.getModel().getValueAt(selectedRow, 0);
        
        int resposta = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir este usuário?", "Confirmação", 
                JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            Usuario usuarioParaExcluir = usuarioDAO.buscarPorId(idUsuario);
            if (usuarioParaExcluir != null) {
                usuarioDAO.excluir(usuarioParaExcluir);
                entidadeSalva(); // Atualiza a tabela
            }
        }
    }

    private void editarVeiculoSelecionado() {
        int selectedRow = jTableVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para editar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedRow = jTableVeiculos.convertRowIndexToModel(selectedRow);
        Long idVeiculo = (Long) jTableVeiculos.getModel().getValueAt(selectedRow, 0);
        Veiculo veiculoParaEditar = veiculoDAO.buscarPorId(idVeiculo);
        
        if (veiculoParaEditar != null) {
            TelaCadastroVeiculo telaCadastro = new TelaCadastroVeiculo(this, true, this, veiculoParaEditar);
            telaCadastro.setVisible(true);
        }
    }
    
    private void excluirVeiculoSelecionado() {
        int selectedRow = jTableVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para excluir.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedRow = jTableVeiculos.convertRowIndexToModel(selectedRow);
        Long idVeiculo = (Long) jTableVeiculos.getModel().getValueAt(selectedRow, 0);
        
        int resposta = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir este veículo?", "Confirmação", 
                JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            Veiculo veiculoParaExcluir = veiculoDAO.buscarPorId(idVeiculo);
            if (veiculoParaExcluir != null) {
                veiculoDAO.excluir(veiculoParaExcluir);
                entidadeSalva(); // Atualiza a tabela
            }
        }
    }
    
    private void editarAluguelSelecionado() {
        int selectedRow = jTableAlugueis.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluguel para editar/fechar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedRow = jTableAlugueis.convertRowIndexToModel(selectedRow);
        Long idAluguel = (Long) jTableAlugueis.getModel().getValueAt(selectedRow, 0);
        
        Aluguel aluguelParaEditar = aluguelDAO.listarTodos().stream()
                .filter(a -> a.getId().equals(idAluguel))
                .findFirst().orElse(null);
        
        if (aluguelParaEditar != null) {
            TelaGestaoAluguel telaCadastro = new TelaGestaoAluguel(this, true, this, aluguelParaEditar);
            telaCadastro.setVisible(true);
        }
    }

    private void excluirAluguelSelecionado() {
        int selectedRow = jTableAlugueis.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluguel para excluir.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedRow = jTableAlugueis.convertRowIndexToModel(selectedRow);
        Long idAluguel = (Long) jTableAlugueis.getModel().getValueAt(selectedRow, 0);
        
        int resposta = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir este aluguel?", "Confirmação", 
                JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            Aluguel aluguelParaExcluir = new Aluguel();
            aluguelParaExcluir.setId(idAluguel);
            
            try {
                aluguelDAO.excluir(aluguelParaExcluir);
                entidadeSalva(); // Atualiza a tabela
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir. O aluguel pode estar sendo usado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPaneAbas;
    private javax.swing.JTable jTableAlugueis;
    private javax.swing.JTable jTableUsuarios;
    private javax.swing.JTable jTableVeiculos;
    // End of variables declaration//GEN-END:variables
}
