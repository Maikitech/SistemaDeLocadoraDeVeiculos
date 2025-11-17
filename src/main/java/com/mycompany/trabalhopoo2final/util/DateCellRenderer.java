package com.mycompany.trabalhopoo2final.util;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer para formatar datas (LocalDate) no formato dd/MM/yyyy.
 */
public class DateCellRenderer extends DefaultTableCellRenderer {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Chama o método pai
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Se o valor for uma LocalDate, formata
        if (value instanceof LocalDate) {
            LocalDate date = (LocalDate) value;
            setText(date.format(formatter));
        }

        return this;
    }
}