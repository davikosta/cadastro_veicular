package br.edu.ifba.saj.ads.poo.presentation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import javafx.scene.control.ButtonType;

import br.edu.ifba.saj.ads.poo.business.ServicoTransporte;
import br.edu.ifba.saj.ads.poo.model.CategoriaCnh;
import br.edu.ifba.saj.ads.poo.model.Motorista;
import java.util.List;
import br.edu.ifba.saj.ads.poo.model.Veiculo;
import javafx.beans.property.SimpleStringProperty;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

public class MotoristaController {

    private final ServicoTransporte servico =
            ServicoTransporte.getInstancia();

    private Motorista motoristaEmEdicao;


    @FXML
    private TextField txNome;

    @FXML
    private TextField txCnh;

    @FXML
    private ChoiceBox<CategoriaCnh> cbCategoriaCnh;

    @FXML
    private DatePicker dpValidadeCnh;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TableView<Motorista> tbMotoristas;

    @FXML
    private TableColumn<Motorista, String> clmNome;

    @FXML
    private TableColumn<Motorista, String> clmCnh;

    @FXML
    private TableColumn<Motorista, CategoriaCnh> clmCategoria;

    @FXML
    private TableColumn<Motorista, LocalDate> clmValidadeCnh;

    @FXML
    private TableColumn<Motorista, String> clmCarroAssociado;

    @FXML
    private TableColumn<Motorista, Void> clmEditar;

    @FXML
    private TableColumn<Motorista, Void> clmApagar;

    @FXML
    private void initialize() {
        configurarColunas();
        configurarColunasAcoes();
        carregarCategoriasCnh();
        configurarCampoCnh();
        carregarListaMotoristas();

        Platform.runLater(() -> txNome.requestFocus());
    }

    private void configurarColunas() {
        clmNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        clmCnh.setCellValueFactory(
                new PropertyValueFactory<>("cnh")
        );

        clmCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoriaCnh")
        );

        clmValidadeCnh.setCellValueFactory(
                new PropertyValueFactory<>("validadeCnh")
        );

        DateTimeFormatter formatadorData =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        clmValidadeCnh.setCellFactory(coluna -> new TableCell<>() {

            @Override
            protected void updateItem(
                    LocalDate validade,
                    boolean vazio
            ) {
                super.updateItem(validade, vazio);

                if (vazio || validade == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(formatadorData.format(validade));
                }
            }
        });

        clmCarroAssociado.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        montarTextoVeiculosAssociados(
                                dados.getValue()
                        )
                )
        );
    }

    private void carregarCategoriasCnh() {
        cbCategoriaCnh.setItems(
                FXCollections.observableArrayList(
                        CategoriaCnh.values()
                )
        );
    }

    private void configurarCampoCnh() {
        UnaryOperator<TextFormatter.Change> filtroCnh =
                alteracao -> {

                    String novoTexto =
                            alteracao.getControlNewText();

                    if (novoTexto.matches("\\d{0,11}")) {
                        return alteracao;
                    }

                    return null;
                };

        txCnh.setTextFormatter(
                new TextFormatter<>(filtroCnh)
        );
    }

    public void carregarListaMotoristas() {
        tbMotoristas.setItems(
                FXCollections.observableArrayList(
                        servico.listarMotoristas()
                )
        );
    }

    @FXML
    private void cadastrarMotorista(ActionEvent evento) {
        try {
            boolean editando = motoristaEmEdicao != null;

            Motorista motorista;

            if (editando) {
                motorista = servico.atualizarMotorista(
                        motoristaEmEdicao,
                        txNome.getText(),
                        txCnh.getText(),
                        cbCategoriaCnh.getValue(),
                        dpValidadeCnh.getValue()
                );
            } else {
                motorista = servico.cadastrarMotorista(
                        txNome.getText(),
                        txCnh.getText(),
                        cbCategoriaCnh.getValue(),
                        dpValidadeCnh.getValue()
                );
            }

            String mensagem;

            if (editando) {
                mensagem = String.format(
                        "Motorista %s atualizado com sucesso.",
                        motorista.getNome()
                );
            } else {
                mensagem = String.format(
                        "Motorista %s cadastrado com sucesso.",
                        motorista.getNome()
                );
            }

            carregarListaMotoristas();
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

    private void limparFormulario() {
        txNome.clear();
        txCnh.clear();

        cbCategoriaCnh
                .getSelectionModel()
                .clearSelection();

        dpValidadeCnh.setValue(null);

        motoristaEmEdicao = null;
        btnCadastrar.setText("Cadastrar motorista");

        tbMotoristas
                .getSelectionModel()
                .clearSelection();

        txNome.requestFocus();
    }

    private void configurarColunasAcoes() {
        clmEditar.setCellFactory(coluna -> new TableCell<>() {

            private final Button botaoEditar =
                    new Button("Editar");

            {
                botaoEditar.setOnAction(evento -> {
                    Motorista motorista = getTableView()
                            .getItems()
                            .get(getIndex());

                    prepararEdicao(motorista);
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
                    Motorista motorista = getTableView()
                            .getItems()
                            .get(getIndex());

                    confirmarExclusao(motorista);
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

    private void prepararEdicao(Motorista motorista) {
        motoristaEmEdicao = motorista;

        txNome.setText(motorista.getNome());
        txCnh.setText(motorista.getCnh());

        cbCategoriaCnh.setValue(
                motorista.getCategoriaCnh()
        );

        dpValidadeCnh.setValue(
                motorista.getValidadeCnh()
        );

        btnCadastrar.setText("Atualizar motorista");

        txNome.requestFocus();
    }

    private void confirmarExclusao(Motorista motorista) {
        Alert confirmacao = new Alert(
                AlertType.CONFIRMATION
        );

        confirmacao.setTitle("Confirmar exclusão");
        confirmacao.setHeaderText("Excluir motorista");
        confirmacao.setContentText(
                String.format(
                        "Deseja excluir o motorista %s?",
                        motorista.getNome()
                )
        );

        ButtonType resposta = confirmacao
                .showAndWait()
                .orElse(ButtonType.CANCEL);

        if (resposta != ButtonType.OK) {
            return;
        }

        try {
            servico.excluirMotorista(motorista);

            if (motorista == motoristaEmEdicao) {
                limparFormulario();
            }

            carregarListaMotoristas();

            new Alert(
                    AlertType.INFORMATION,
                    "Motorista excluído com sucesso."
            ).showAndWait();

        } catch (IllegalArgumentException erro) {
            new Alert(
                    AlertType.ERROR,
                    erro.getMessage()
            ).showAndWait();
        }
    }

    private String montarTextoVeiculosAssociados(
            Motorista motorista
    ) {
        List<Veiculo> veiculos =
                servico.listarVeiculosAssociados(
                        motorista
                );

        if (veiculos.isEmpty()) {
            return "Sem associação";
        }

        StringBuilder texto = new StringBuilder();

        for (int indice = 0;
             indice < veiculos.size();
             indice++) {

            Veiculo veiculo = veiculos.get(indice);

            if (indice > 0) {
                texto.append(", ");
            }

            texto.append(veiculo.getModelo());
            texto.append(" (");
            texto.append(veiculo.getPlaca());
            texto.append(")");
        }

        return texto.toString();
    }
}