package br.edu.ifba.saj.ads.poo.business;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.saj.ads.poo.data.RepositorioTransporte;
import br.edu.ifba.saj.ads.poo.model.CategoriaCnh;
import br.edu.ifba.saj.ads.poo.model.Motorista;

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
}