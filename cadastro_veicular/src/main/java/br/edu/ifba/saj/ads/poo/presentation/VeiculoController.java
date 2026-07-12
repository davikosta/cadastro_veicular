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
import br.edu.ifba.saj.ads.poo.business.ServicoTransporte;
import br.edu.ifba.saj.ads.poo.model.CategoriaVeiculo;
import br.edu.ifba.saj.ads.poo.model.Veiculo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;


public class VeiculoController {

    private final ServicoTransporte servico =
            ServicoTransporte.getInstancia();

    private Veiculo veiculoEmEdicao;

    @FXML
    private TextField txModelo;

    @FXML
    private TextField txPlaca;

    @FXML
    private ChoiceBox<CategoriaVeiculo> cbCategoriaVeiculo;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Veiculo> tbVeiculos;

    @FXML
    private TableColumn<Veiculo, String> clmModelo;

    @FXML
    private TableColumn<Veiculo, String> clmPlaca;

    @FXML
    private TableColumn<Veiculo, CategoriaVeiculo> clmCategoria;

    @FXML
    private TableColumn<Veiculo, String> clmMotoristaAssociado;

    @FXML
    private TableColumn<Veiculo, Void> clmEditar;

    @FXML
    private TableColumn<Veiculo, Void> clmApagar;

    @FXML
    private void initialize() {
        configurarColunas();
        configurarColunasAcoes();
        carregarCategorias();
        configurarCampoPlaca();
        carregarListaVeiculos();

        Platform.runLater(() -> txModelo.requestFocus());
    }

    private void carregarCategorias() {
        cbCategoriaVeiculo.setItems(
                FXCollections.observableArrayList(
                        CategoriaVeiculo.values()
                )
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
    private void cadastrarVeiculo(ActionEvent evento) {
        try {
            boolean editando = veiculoEmEdicao != null;

            Veiculo veiculo;

            if (editando) {
                veiculo = servico.atualizarVeiculo(
                        veiculoEmEdicao,
                        txModelo.getText(),
                        txPlaca.getText(),
                        cbCategoriaVeiculo.getValue()
                );
            } else {
                veiculo = servico.cadastrarVeiculo(
                        txModelo.getText(),
                        txPlaca.getText(),
                        cbCategoriaVeiculo.getValue()
                );
            }

            String mensagem;

            if (editando) {
                mensagem = String.format(
                        "Veículo %s atualizado com sucesso.",
                        veiculo.getModelo()
                );
            } else {
                mensagem = String.format(
                        "Veículo %s cadastrado com sucesso.",
                        veiculo.getModelo()
                );
            }

            carregarListaVeiculos();
            limparFormulario();

            new Alert(
                    AlertType.INFORMATION,
                    mensagem
            ).showAndWait();

        } catch (IllegalArgumentException erro) {
            new Alert(
                    AlertType.ERROR,
                    erro.getMessage()
            ).showAndWait();
        }
    }

    private void configurarColunas() {
        clmModelo.setCellValueFactory(
                new PropertyValueFactory<>("modelo")
        );

        clmPlaca.setCellValueFactory(
                new PropertyValueFactory<>("placa")
        );

        clmCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoria")
        );

        /*
         * A coluna de motorista associado será configurada
         * quando as associações estiverem conectadas.
         */
    }

    public void carregarListaVeiculos() {
        tbVeiculos.setItems(
                FXCollections.observableArrayList(
                        servico.listarVeiculos()
                )
        );
    }

    private void limparFormulario() {
        txModelo.clear();
        txPlaca.clear();

        cbCategoriaVeiculo
                .getSelectionModel()
                .clearSelection();

        veiculoEmEdicao = null;
        btnCadastrar.setText("Cadastrar veículo");

        tbVeiculos
                .getSelectionModel()
                .clearSelection();

        txModelo.requestFocus();
    }

    private void configurarColunasAcoes() {
        clmEditar.setCellFactory(coluna -> new TableCell<>() {

            private final Button botaoEditar =
                    new Button("Editar");

            {
                botaoEditar.setOnAction(evento -> {
                    Veiculo veiculo = getTableRow().getItem();

                    if (veiculo != null) {
                        prepararEdicao(veiculo);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);

                if (vazio) {
                    setGraphic(null);
                } else {
                    setGraphic(botaoEditar);
                }
            }
        });

        clmApagar.setCellFactory(coluna -> new TableCell<>() {

            private final Button botaoApagar =
                    new Button("Apagar");

            {
                botaoApagar.setOnAction(evento -> {
                    Veiculo veiculo = getTableRow().getItem();

                    if (veiculo != null) {
                        confirmarExclusao(veiculo);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);

                if (vazio) {
                    setGraphic(null);
                } else {
                    setGraphic(botaoApagar);
                }
            }
        });
    }

    private void prepararEdicao(Veiculo veiculo) {
        veiculoEmEdicao = veiculo;

        txModelo.setText(veiculo.getModelo());
        txPlaca.setText(veiculo.getPlaca());
        cbCategoriaVeiculo.setValue(
                veiculo.getCategoria()
        );

        btnCadastrar.setText("Atualizar veículo");

        txModelo.requestFocus();
    }

    private void confirmarExclusao(Veiculo veiculo) {
        Alert confirmacao = new Alert(
                AlertType.CONFIRMATION
        );

        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText("Excluir veículo");
        confirmacao.setContentText(
                String.format(
                        "Deseja excluir o veículo %s, placa %s?",
                        veiculo.getModelo(),
                        veiculo.getPlaca()
                )
        );

        ButtonType resposta = confirmacao
                .showAndWait()
                .orElse(ButtonType.CANCEL);

        if (resposta != ButtonType.OK) {
            return;
        }

        try {
            servico.excluirVeiculo(veiculo);

            if (veiculo == veiculoEmEdicao) {
                limparFormulario();
            }

            carregarListaVeiculos();

            new Alert(
                    AlertType.INFORMATION,
                    "Veículo excluído com sucesso."
            ).showAndWait();

        } catch (IllegalArgumentException erro) {
            new Alert(
                    AlertType.ERROR,
                    erro.getMessage()
            ).showAndWait();
        }
    }
}