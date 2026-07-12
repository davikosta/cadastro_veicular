package br.edu.ifba.saj.ads.poo.business;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.saj.ads.poo.data.RepositorioTransporte;

import br.edu.ifba.saj.ads.poo.model.CategoriaCnh;
import br.edu.ifba.saj.ads.poo.model.Motorista;
import br.edu.ifba.saj.ads.poo.model.CategoriaVeiculo;
import br.edu.ifba.saj.ads.poo.model.Veiculo;
import br.edu.ifba.saj.ads.poo.model.Associacao;

public class ServicoTransporte {

    private static final ServicoTransporte INSTANCIA_COMPARTILHADA =
            new ServicoTransporte(
                    RepositorioTransporte.getInstancia()
            );

    private final RepositorioTransporte repositorio;

    public ServicoTransporte(
            RepositorioTransporte repositorio
    ) {
        if (repositorio == null) {
            throw new IllegalArgumentException(
                    "O repositório não pode ser nulo."
            );
        }

        this.repositorio = repositorio;
    }

    public static ServicoTransporte getInstancia() {
        return INSTANCIA_COMPARTILHADA;
    }

    public Motorista cadastrarMotorista(
            String nome,
            String cnh,
            CategoriaCnh categoriaCnh,
            LocalDate validadeCnh
    ) {
        String nomeValidado = validarNome(nome);
        String cnhValidada = validarCnh(cnh);

        validarCategoriaCnh(categoriaCnh);
        validarValidadeCnhInformada(validadeCnh);

        Motorista motorista = new Motorista(
                nomeValidado,
                cnhValidada,
                categoriaCnh,
                validadeCnh
        );

        repositorio.salvarMotorista(motorista);

        return motorista;
    }

    public Motorista atualizarMotorista(
            Motorista motoristaCadastrado,
            String nome,
            String cnh,
            CategoriaCnh categoriaCnh,
            LocalDate validadeCnh
    ) {
        if (motoristaCadastrado == null) {
            throw new IllegalArgumentException(
                    "Nenhum motorista foi selecionado para edição."
            );
        }

        String nomeValidado = validarNome(nome);
        String cnhValidada = validarCnh(cnh);

        validarCategoriaCnh(categoriaCnh);
        validarValidadeCnhInformada(validadeCnh);

        Motorista novosDados = new Motorista(
                nomeValidado,
                cnhValidada,
                categoriaCnh,
                validadeCnh
        );

        boolean atualizado = repositorio.atualizarMotorista(
                motoristaCadastrado,
                novosDados
        );

        if (!atualizado) {
            throw new IllegalArgumentException(
                    "O motorista não foi encontrado."
            );
        }

        return motoristaCadastrado;
    }

    public void excluirMotorista(Motorista motorista) {
        if (motorista == null) {
            throw new IllegalArgumentException(
                    "Nenhum motorista foi selecionado para exclusão."
            );
        }

        boolean excluido = repositorio.excluirMotorista(motorista);

        if (!excluido) {
            throw new IllegalArgumentException(
                    "O motorista não foi encontrado."
            );
        }
    }

    public List<Motorista> listarMotoristas() {
        return repositorio.listarMotoristas();
    }

    public Veiculo cadastrarVeiculo(
            String modelo,
            String placa,
            CategoriaVeiculo categoria
    ) {
        String modeloValidado = validarModelo(modelo);
        String placaValidada = validarPlaca(placa);

        validarCategoriaVeiculo(categoria);
        validarPlacaUnica(placaValidada);

        Veiculo veiculo = new Veiculo(
                modeloValidado,
                placaValidada,
                categoria
        );

        repositorio.salvarVeiculo(veiculo);

        return veiculo;
    }

    public Veiculo atualizarVeiculo(
            Veiculo veiculoCadastrado,
            String modelo,
            String placa,
            CategoriaVeiculo categoria
    ) {
        if (veiculoCadastrado == null) {
            throw new IllegalArgumentException(
                    "Nenhum veículo foi selecionado para edição."
            );
        }

        String modeloValidado = validarModelo(modelo);
        String placaValidada = validarPlaca(placa);

        validarCategoriaVeiculo(categoria);
        validarPlacaUnicaNaEdicao(
                placaValidada,
                veiculoCadastrado
        );

        Veiculo novosDados = new Veiculo(
                modeloValidado,
                placaValidada,
                categoria
        );

        boolean atualizado = repositorio.atualizarVeiculo(
                veiculoCadastrado,
                novosDados
        );

        if (!atualizado) {
            throw new IllegalArgumentException(
                    "O veículo não foi encontrado."
            );
        }

        return veiculoCadastrado;
    }

    public void excluirVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException(
                    "Nenhum veículo foi selecionado para exclusão."
            );
        }

        boolean excluido = repositorio.excluirVeiculo(veiculo);

        if (!excluido) {
            throw new IllegalArgumentException(
                    "O veículo não foi encontrado."
            );
        }
    }

    public List<Veiculo> listarVeiculos() {
        return repositorio.listarVeiculos();
    }

    public Associacao criarAssociacao(
            Motorista motorista,
            Veiculo veiculo
    ) {
        validarMotoristaSelecionado(motorista);
        validarVeiculoSelecionado(veiculo);
        validarCnhParaAssociacao(motorista);

        Associacao associacao = new Associacao(
                motorista,
                veiculo
        );

        repositorio.salvarAssociacao(associacao);

        return associacao;
    }

    public List<Associacao> listarAssociacoes() {
        return repositorio.listarAssociacoes();
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do motorista é obrigatório."
            );
        }

        return nome.trim();
    }

    private String validarCnh(String cnh) {
        if (cnh == null || cnh.isBlank()) {
            throw new IllegalArgumentException(
                    "A CNH é obrigatória."
            );
        }

        String cnhNormalizada = cnh.trim();

        if (!cnhNormalizada.matches("\\d{11}")) {
            throw new IllegalArgumentException(
                    "A CNH deve possuir exatamente 11 números."
            );
        }

        return cnhNormalizada;
    }

    private void validarCategoriaCnh(
            CategoriaCnh categoriaCnh
    ) {
        if (categoriaCnh == null) {
            throw new IllegalArgumentException(
                    "A categoria da CNH é obrigatória."
            );
        }
    }

    private void validarValidadeCnhInformada(
            LocalDate validadeCnh
    ) {
        if (validadeCnh == null) {
            throw new IllegalArgumentException(
                    "A validade da CNH é obrigatória."
            );
        }
    }

    private String validarModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException(
                    "O modelo do veículo é obrigatório."
            );
        }

        return modelo.trim();
    }

    private String validarPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException(
                    "A placa do veículo é obrigatória."
            );
        }

        String placaNormalizada = placa
                .trim()
                .toUpperCase();

        boolean placaAntiga =
                placaNormalizada.matches("[A-Z]{3}[0-9]{4}");

        boolean placaMercosul =
                placaNormalizada.matches(
                        "[A-Z]{3}[0-9][A-Z][0-9]{2}"
                );

        if (!placaAntiga && !placaMercosul) {
            throw new IllegalArgumentException(
                    "A placa deve seguir o formato ABC1234 ou ABC1D23."
            );
        }

        return placaNormalizada;
    }

    private void validarCategoriaVeiculo(
            CategoriaVeiculo categoria
    ) {
        if (categoria == null) {
            throw new IllegalArgumentException(
                    "A categoria do veículo é obrigatória."
            );
        }
    }

    private void validarPlacaUnica(String placa) {
        if (repositorio.existeVeiculoComPlaca(placa)) {
            throw new IllegalArgumentException(
                    "A placa informada já está em uso."
            );
        }
    }

    private void validarPlacaUnicaNaEdicao(
            String placa,
            Veiculo veiculoEmEdicao
    ) {
        boolean placaUtilizada =
                repositorio.existeVeiculoComPlacaExceto(
                        placa,
                        veiculoEmEdicao
                );

        if (placaUtilizada) {
            throw new IllegalArgumentException(
                    "A placa informada já está em uso."
            );
        }
    }

    private void validarMotoristaSelecionado(
            Motorista motorista
    ) {
        if (motorista == null) {
            throw new IllegalArgumentException(
                    "Selecione um motorista."
            );
        }
    }

    private void validarVeiculoSelecionado(
            Veiculo veiculo
    ) {
        if (veiculo == null) {
            throw new IllegalArgumentException(
                    "Selecione um veículo."
            );
        }
    }

    private void validarCnhParaAssociacao(
            Motorista motorista
    ) {
        LocalDate validadeCnh = motorista.getValidadeCnh();

        boolean cnhValida =
                validadeCnh != null
                        && validadeCnh.isAfter(LocalDate.now());

        if (!cnhValida) {
            throw new IllegalArgumentException(
                    "Não é possível criar a associação: "
                            + "a CNH do motorista está vencida."
            );
        }
    }
}