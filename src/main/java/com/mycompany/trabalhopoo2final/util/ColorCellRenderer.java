package com.mycompany.trabalhopoo2final.util;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer personalizado para exibir cores na JTable (RT6).
 */
public class ColorCellRenderer extends DefaultTableCellRenderer {

    private final Map<String, Color> palette;

    public ColorCellRenderer(Map<String, Color> palette) {
        this.palette = palette;
        setOpaque(true); 
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color bg = table.getBackground();

        if (value instanceof Integer iARGB) {
            bg = new Color(iARGB, true);
        } else if (value instanceof String name) {
            bg = palette.getOrDefault(name.toLowerCase(), table.getBackground());
        }

        setBackground(isSelected ? table.getSelectionBackground() : bg);
        setText(""); // Mostra só a cor
        
        return this;
    }
}