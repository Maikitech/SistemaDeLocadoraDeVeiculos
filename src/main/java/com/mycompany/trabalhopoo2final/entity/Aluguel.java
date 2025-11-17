package com.mycompany.trabalhopoo2final.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate; // Diagrama usa LocalDate

@Entity
public class Aluguel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos do diagrama (Figura 1)
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status; // Usado no RT6 (Renderer)
    private int quilometragemInicial;
    private int quilometragemFinal;

    // --- Relacionamentos (Chaves Estrangeiras) ---

    // Muitos Aluguéis para Um Usuário (Slide 13/15)
    @ManyToOne
    @JoinColumn(name = "usuario_id") // Coluna FK no banco
    private Usuario usuario;

    // Muitos Aluguéis para Um Veículo
    @ManyToOne
    @JoinColumn(name = "veiculo_id") // Coluna FK no banco
    private Veiculo veiculo;

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuilometragemInicial() {
        return quilometragemInicial;
    }

    public void setQuilometragemInicial(int quilometragemInicial) {
        this.quilometragemInicial = quilometragemInicial;
    }

    public int getQuilometragemFinal() {
        return quilometragemFinal;
    }

    public void setQuilometragemFinal(int quilometragemFinal) {
        this.quilometragemFinal = quilometragemFinal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}