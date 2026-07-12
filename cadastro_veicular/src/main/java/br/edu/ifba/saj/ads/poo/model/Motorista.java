package br.edu.ifba.saj.ads.poo.model;

import java.time.LocalDate;

public class Motorista {

    private String nome;
    private String cnh;
    private CategoriaCnh categoriaCnh;
    private LocalDate validadeCnh;

    public Motorista (String nome, String cnh, CategoriaCnh categoriaCnh, LocalDate validadeCnh) {
        this.nome = nome;
        this.cnh = cnh;
        this.categoriaCnh = categoriaCnh;
        this.validadeCnh = validadeCnh;
    }

    public String getNome() {
        return nome;
    }

    public String getCnh() {
        return cnh;
    }

    public CategoriaCnh getCategoriaCnh() {
        return categoriaCnh;
    }

    public LocalDate getValidadeCnh() {
        return validadeCnh;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public void setCategoriaCnh(CategoriaCnh categoriaCnh) {
        this.categoriaCnh = categoriaCnh;
    }

    public void setValidadeCnh(LocalDate validadeCnh) {
        this.validadeCnh = validadeCnh;
    }
}
