package br.edu.ifba.saj.ads.poo.model;

public class Veiculo {

    private String modelo;
    private String placa;
    private CategoriaVeiculo categoria;

    public Veiculo(
            String modelo,
            String placa,
            CategoriaVeiculo categoria
    ) {
        this.modelo = modelo;
        this.placa = placa;
        this.categoria = categoria;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public CategoriaVeiculo getCategoria() {
        return categoria;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setCategoria(CategoriaVeiculo categoria) {
        this.categoria = categoria;
    }
}