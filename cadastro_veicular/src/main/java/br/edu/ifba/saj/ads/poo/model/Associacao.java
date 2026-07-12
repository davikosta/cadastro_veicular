package br.edu.ifba.saj.ads.poo.model;

public class Associacao {

    private Motorista motorista;
    private Veiculo veiculo;

    public Associacao(Motorista motorista, Veiculo veiculo) {
        this.motorista = motorista;
        this.veiculo = veiculo;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}