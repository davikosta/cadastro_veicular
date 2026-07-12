package br.edu.ifba.saj.ads.poo.data;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.saj.ads.poo.model.Associacao;
import br.edu.ifba.saj.ads.poo.model.Motorista;
import br.edu.ifba.saj.ads.poo.model.Veiculo;

public class RepositorioTransporte {

    private static final RepositorioTransporte INSTANCIA_COMPARTILHADA =
            new RepositorioTransporte();

    private final ArrayList<Motorista> motoristas;
    private final ArrayList<Veiculo> veiculos;
    private final ArrayList<Associacao> associacoes;

    public RepositorioTransporte() {
        motoristas = new ArrayList<>();
        veiculos = new ArrayList<>();
        associacoes = new ArrayList<>();
    }

    public static RepositorioTransporte getInstancia() {
        return INSTANCIA_COMPARTILHADA;
    }

    // CRUD de motoristas

    public void salvarMotorista(Motorista motorista) {
        motoristas.add(motorista);
    }

    public List<Motorista> listarMotoristas() {
        return new ArrayList<>(motoristas);
    }

    public boolean atualizarMotorista(
            Motorista motoristaCadastrado,
            Motorista novosDados
    ) {
        if (!motoristas.contains(motoristaCadastrado)) {
            return false;
        }

        motoristaCadastrado.setNome(novosDados.getNome());
        motoristaCadastrado.setCnh(novosDados.getCnh());
        motoristaCadastrado.setCategoriaCnh(
                novosDados.getCategoriaCnh()
        );
        motoristaCadastrado.setValidadeCnh(
                novosDados.getValidadeCnh()
        );

        return true;
    }

    public boolean excluirMotorista(Motorista motorista) {
        return motoristas.remove(motorista);
    }

    // CRUD de veículos

    public void salvarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return new ArrayList<>(veiculos);
    }

    public boolean atualizarVeiculo(
            Veiculo veiculoCadastrado,
            Veiculo novosDados
    ) {
        if (!veiculos.contains(veiculoCadastrado)) {
            return false;
        }

        veiculoCadastrado.setModelo(novosDados.getModelo());
        veiculoCadastrado.setPlaca(novosDados.getPlaca());
        veiculoCadastrado.setCategoria(novosDados.getCategoria());

        return true;
    }

    public boolean excluirVeiculo(Veiculo veiculo) {
        return veiculos.remove(veiculo);
    }

    // CRUD de associações

    public void salvarAssociacao(Associacao associacao) {
        associacoes.add(associacao);
    }

    public List<Associacao> listarAssociacoes() {
        return new ArrayList<>(associacoes);
    }

    public boolean atualizarAssociacao(
            Associacao associacaoCadastrada,
            Associacao novosDados
    ) {
        if (!associacoes.contains(associacaoCadastrada)) {
            return false;
        }

        associacaoCadastrada.setMotorista(
                novosDados.getMotorista()
        );
        associacaoCadastrada.setVeiculo(
                novosDados.getVeiculo()
        );

        return true;
    }

    public boolean excluirAssociacao(Associacao associacao) {
        return associacoes.remove(associacao);
    }
}