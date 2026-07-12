package br.edu.ifba.saj.ads.poo.presentation;

import java.util.function.UnaryOperator;
import javafx.scene.control.DatePicker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class MotoristaController {

    @FXML
    private TextField txNome;

    @FXML
    private TextField txCnh;

    @FXML
    private ChoiceBox<String> cbCategoriaCnh;

    @FXML
    private DatePicker dpValidadeCnh;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Object> tbMotoristas;

    @FXML
    private TableColumn<Object, String> clmNome;

    @FXML
    private TableColumn<Object, String> clmCnh;

    @FXML
    private TableColumn<Object, String> clmCategoria;

    @FXML
    private TableColumn<Object, String> clmValidadeCnh;

    @FXML
    private TableColumn<Object, String> clmCarroAssociado;

    @FXML
    private TableColumn<Object, Void> clmEditar;

    @FXML
    private TableColumn<Object, Void> clmApagar;

    @FXML
    private void initialize() {
        carregarCategoriasCnh();
        configurarCampoCnh();

        Platform.runLater(() -> txNome.requestFocus());
    }

    private void carregarCategoriasCnh() {
        cbCategoriaCnh.getItems().addAll(
                "A",
                "B",
                "C",
                "D",
                "E",
                "AB"
        );
    }

    private void configurarCampoCnh() {
        UnaryOperator<TextFormatter.Change> filtroCnh = alteracao -> {
            String novoTexto = alteracao.getControlNewText();

            if (novoTexto.matches("\\d{0,11}")) {
                return alteracao;
            }

            return null;
        };

        txCnh.setTextFormatter(new TextFormatter<>(filtroCnh));
    }

    @FXML
    private void cadastrarMotorista() {
        /*
         * O cadastro será implementado posteriormente.
         *
         * Nesta etapa estamos criando somente a apresentação
         * e preparando os componentes da tela.
         */
    }
}