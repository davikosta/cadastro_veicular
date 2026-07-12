package br.edu.ifba.saj.ads.poo.presentation;

import br.edu.ifba.saj.ads.poo.business.ServicoTransporte;
import br.edu.ifba.saj.ads.poo.model.Associacao;
import br.edu.ifba.saj.ads.poo.model.Motorista;
import br.edu.ifba.saj.ads.poo.model.Veiculo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AssociacaoController {

    private final ServicoTransporte servico =
            ServicoTransporte.getInstancia();

    @FXML
    private ChoiceBox<Motorista> cbMotorista;

    @FXML
    private ChoiceBox<Veiculo> cbVeiculo;

    @FXML
    private Button btnCriarAssociacao;

    @FXML
    private TableView<Associacao> tbAssociacoes;

    @FXML
    private TableColumn<Associacao, String> clmMotorista;

    @FXML
    private TableColumn<Associacao, String> clmCnh;

    @FXML
    private TableColumn<Associacao, String> clmPlaca;

    @FXML
    private TableColumn<Associacao, String> clmVeiculo;

    @FXML
    private TableColumn<Associacao, Void> clmEditar;

    @FXML
    private TableColumn<Associacao, Void> clmApagar;

    @FXML
    private void initialize() {
        configurarColunas();
        configurarChoiceBoxes();
        carregarOpcoes();
        carregarListaAssociacoes();

        Platform.runLater(
                () -> cbMotorista.requestFocus()
        );
    }

    @FXML
    private void criarAssociacao() {
        try {
            Associacao associacao =
                    servico.criarAssociacao(
                            cbMotorista.getValue(),
                            cbVeiculo.getValue()
                    );

            carregarListaAssociacoes();
            limparSelecoes();

            new Alert(
                    AlertType.INFORMATION,
                    String.format(
                            "Motorista %s associado ao veículo %s.",
                            associacao.getMotorista().getNome(),
                            associacao.getVeiculo().getModelo()
                    )
            ).showAndWait();

        } catch (IllegalArgumentException erro) {
            new Alert(
                    AlertType.ERROR,
                    erro.getMessage()
            ).showAndWait();
        }
    }

    private void configurarColunas() {
        clmMotorista.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue()
                                .getMotorista()
                                .getNome()
                )
        );

        clmCnh.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue()
                                .getMotorista()
                                .getCnh()
                )
        );

        clmPlaca.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue()
                                .getVeiculo()
                                .getPlaca()
                )
        );

        clmVeiculo.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue()
                                .getVeiculo()
                                .getModelo()
                )
        );

        /*
         * As colunas Editar e Apagar serão configuradas
         * na próxima etapa.
         */
    }

    private void configurarChoiceBoxes() {
        cbMotorista.setConverter(
                new StringConverter<Motorista>() {

                    @Override
                    public String toString(Motorista motorista) {
                        if (motorista == null) {
                            return "";
                        }

                        return String.format(
                                "%s - CNH %s",
                                motorista.getNome(),
                                motorista.getCnh()
                        );
                    }

                    @Override
                    public Motorista fromString(String texto) {
                        return null;
                    }
                });

        cbVeiculo.setConverter(
                new StringConverter<Veiculo>() {

                    @Override
                    public String toString(Veiculo veiculo) {
                        if (veiculo == null) {
                            return "";
                        }

                        return String.format(
                                "%s - %s",
                                veiculo.getModelo(),
                                veiculo.getPlaca()
                        );
                    }

                    @Override
                    public Veiculo fromString(String texto) {
                        return null;
                    }
                });
    }

    private void carregarOpcoes() {
        cbMotorista.setItems(
                FXCollections.observableArrayList(
                        servico.listarMotoristas()
                )
        );

        cbVeiculo.setItems(
                FXCollections.observableArrayList(
                        servico.listarVeiculos()
                )
        );
    }

    public void carregarListaAssociacoes() {
        tbAssociacoes.setItems(
                FXCollections.observableArrayList(
                        servico.listarAssociacoes()
                )
        );
    }

    private void limparSelecoes() {
        cbMotorista
                .getSelectionModel()
                .clearSelection();

        cbVeiculo
                .getSelectionModel()
                .clearSelection();

        tbAssociacoes
                .getSelectionModel()
                .clearSelection();

        cbMotorista.requestFocus();
    }
}