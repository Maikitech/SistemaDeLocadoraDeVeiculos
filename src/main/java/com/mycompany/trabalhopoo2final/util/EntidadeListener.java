package com.mycompany.trabalhopoo2final.util;

/**
 * Interface de notificação para desacoplar as telas (RT2).
 */
public interface EntidadeListener {
    
    /**
     * Chamado quando uma entidade é salva (criada ou atualizada)
     * e a tela de listagem precisa ser atualizada.
     */
    void entidadeSalva();
}