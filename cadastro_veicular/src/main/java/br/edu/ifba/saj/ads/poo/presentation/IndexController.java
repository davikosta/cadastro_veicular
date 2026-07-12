package br.edu.ifba.saj.ads.poo.presentation;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class IndexController {

    @FXML
    private BorderPane panePrincipal;

    @FXML
    private void abrirDashboard() {
        panePrincipal.setCenter(new Label("Dashboard"));
    }

    @FXML
    private void abrirCadastroMotorista() {
        try {
            Parent telaMotorista = FXMLLoader.load(
                    getClass().getResource("Motorista.fxml")
            );

            panePrincipal.setCenter(telaMotorista);
        } catch (IOException erro) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Erro");
            alerta.setHeaderText("Não foi possível abrir a tela de motoristas.");
            alerta.setContentText(erro.getMessage());

            alerta.showAndWait();
        }
    }

    @FXML
    private void abrirCadastroVeiculo() {
        try {
            Parent telaVeiculo = FXMLLoader.load(
                    getClass().getResource("Veiculo.fxml")
            );

            panePrincipal.setCenter(telaVeiculo);
        } catch (IOException erro) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Erro");
            alerta.setHeaderText(
                    "Não foi possível abrir a tela de veículos."
            );
            alerta.setContentText(erro.getMessage());

            alerta.showAndWait();
        }
    }

    @FXML
    private void abrirAssociacoes() {
        try {
            Parent telaAssociacao = FXMLLoader.load(
                    getClass().getResource("Associacao.fxml")
            );

            panePrincipal.setCenter(telaAssociacao);
        } catch (IOException erro) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Erro");
            alerta.setHeaderText(
                    "Não foi possível abrir a tela de associações."
            );
            alerta.setContentText(erro.getMessage());

            alerta.showAndWait();
        }
    }
}