package br.edu.ifba.saj.ads.poo.presentation;

import java.util.function.UnaryOperator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class VeiculoController {

    @FXML
    private TextField txModelo;

    @FXML
    private TextField txPlaca;

    @FXML
    private ChoiceBox<String> cbCategoriaVeiculo;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Object> tbVeiculos;

    @FXML
    private TableColumn<Object, String> clmModelo;

    @FXML
    private TableColumn<Object, String> clmPlaca;

    @FXML
    private TableColumn<Object, String> clmCategoria;

    @FXML
    private TableColumn<Object, String> clmMotoristaAssociado;

    @FXML
    private TableColumn<Object, Void> clmEditar;

    @FXML
    private TableColumn<Object, Void> clmApagar;

    @FXML
    private void initialize() {
        carregarCategorias();
        configurarCampoPlaca();

        Platform.runLater(() -> txModelo.requestFocus());
    }

    private void carregarCategorias() {
        cbCategoriaVeiculo.getItems().addAll(
                "A",
                "B",
                "C",
                "D",
                "E"
        );
    }

    private void configurarCampoPlaca() {
        UnaryOperator<TextFormatter.Change> filtroPlaca = alteracao -> {
            String novoTexto = alteracao
                    .getControlNewText()
                    .toUpperCase();

            if (novoTexto.matches("[A-Z0-9]{0,7}")) {
                alteracao.setText(
                        alteracao.getText().toUpperCase()
                );

                return alteracao;
            }

            return null;
        };

        txPlaca.setTextFormatter(
                new TextFormatter<>(filtroPlaca)
        );
    }

    @FXML
    private void cadastrarVeiculo() {
        /*
         * O cadastro será implementado posteriormente.
         *
         * A validação de placa única deverá ficar na
         * camada de negócio.
         */
    }
}