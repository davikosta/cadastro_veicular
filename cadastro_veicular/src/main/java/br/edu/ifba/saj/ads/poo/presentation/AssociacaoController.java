package br.edu.ifba.saj.ads.poo.presentation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AssociacaoController {

    @FXML
    private ChoiceBox<Object> cbMotorista;

    @FXML
    private ChoiceBox<Object> cbVeiculo;

    @FXML
    private Button btnCriarAssociacao;

    @FXML
    private TableView<Object> tbAssociacoes;

    @FXML
    private TableColumn<Object, String> clmMotorista;

    @FXML
    private TableColumn<Object, String> clmCnh;

    @FXML
    private TableColumn<Object, String> clmPlaca;

    @FXML
    private TableColumn<Object, String> clmVeiculo;

    @FXML
    private TableColumn<Object, Void> clmEditar;

    @FXML
    private TableColumn<Object, Void> clmApagar;

    @FXML
    private void initialize() {
        Platform.runLater(() -> cbMotorista.requestFocus());

        /*
         * Os ChoiceBox serão preenchidos posteriormente
         * com os motoristas e veículos cadastrados.
         */
    }

    @FXML
    private void criarAssociacao() {
        /*
         * A criação da associação será implementada
         * posteriormente.
         *
         * O controller enviará o motorista e o veículo
         * selecionados para a camada de negócio.
         *
         * A camada de negócio verificará a validade da CNH.
         */
    }
}