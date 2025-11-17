package com.mycompany.trabalhopoo2final.main;

import com.formdev.flatlaf.FlatLightLaf; // Tema claro (RT1)
// import com.formdev.flatlaf.FlatDarkLaf; // Tema escuro
import com.mycompany.trabalhopoo2final.view.TelaPrincipal;

/**
 * Ponto de entrada da aplicação.
 * Configura o Look and Feel (Tema) FlatLaf (RT1).
 */
public class PontoDeEntrada {

    public static void main(String[] args) {
        // 1. Configura o tema FlatLaf
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Inicia a tela principal na thread de eventos do Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
