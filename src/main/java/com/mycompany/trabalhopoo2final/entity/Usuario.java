package com.mycompany.trabalhopoo2final.entity;

import jakarta.persistence.*; // Importante: usar jakarta.persistence.*
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity // Define que esta classe é uma entidade (Slide 11)
public class Usuario implements Serializable {

    @Id // Define a chave primária (Slide 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremento (Slide 11)
    private Long id;

    // Atributos do diagrama (Figura 1)
    private String nome;
    private String email;
    private String telefone;

    // Relacionamento (Figura 1: "possui" 1..* Aluguel)
    // Um Usuário pode ter muitos aluguéis
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Aluguel> alugueis = new ArrayList<>();

    // --- Getters e Setters (Obrigatórios para o Hibernate) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }
}