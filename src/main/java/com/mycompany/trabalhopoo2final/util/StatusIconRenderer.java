package com.mycompany.trabalhopoo2final.util;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer personalizado para exibir ícones de status na JTable (RT6).
 */
public class StatusIconRenderer extends DefaultTableCellRenderer {

    // ATENÇÃO: Você precisa criar esta pasta "icons" e adicionar os arquivos!
    private final Icon iconAberto;
    private final Icon iconFechado;
    private final Icon iconAtraso;

    public StatusIconRenderer() {
        iconAberto = carregaIcone("/icons/open.png");
        iconFechado = carregaIcone("/icons/closed.png");
        iconAtraso = carregaIcone("/icons/late.png");
        setHorizontalAlignment(CENTER); // Centraliza o ícone
    }
    
    private Icon carregaIcone(String path) {
        try {
            // Isso procura o ícone dentro da pasta 'resources'
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícone: " + path);
            return null;
        }
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setText(null); // Remove o texto

        setIcon(switch (String.valueOf(value).toUpperCase()) {
            case "ABERTO" -> iconAberto;
            case "FECHADO" -> iconFechado;
            case "ATRASADO" -> iconAtraso;
            default -> null;
        });
        
        return this;
    }
}