package controller;

import java.net.URL;
import java.util.ResourceBundle;
import bean.Fornecedor;
import excecao.ModelValidar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ModelValidarFornecedor;

public class FXMLAnchorPaneCadastrosFornecedoresInserir implements Initializable {

    @FXML
    private Label labelFornecedorNome;
    @FXML
    private Label labelFornecedorCnpj;
    @FXML
    private Label labelFornecedorEndereco;
    @FXML
    private Label labelFornecedorTelefone;

    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCnpj;
    @FXML
    private TextField textFieldEndereco;
    @FXML
    private TextField textFieldTelefone;

    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage; // Atributo para abrir e fechar a tela cadastro fornecedor
    private boolean buttonConfirmarClicked = false; // Atributo para verificar se o botão foi clicado
    private Fornecedor fornecedor; // Classe usada como modelo para inserir os dados

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        // Setar nos campos para começar aparecendo os valores do fornecedor
        this.textFieldNome.setText(fornecedor.getNome());
        this.textFieldCnpj.setText(fornecedor.getCnpj());
        this.textFieldEndereco.setText(fornecedor.getEndereco());
        this.textFieldTelefone.setText(fornecedor.getTelefone());

    }

    @FXML
    public void handlButtonConfirmar() {

        try {

            fornecedor.setNome(textFieldNome.getText());
            fornecedor.setCnpj(textFieldCnpj.getText());
            fornecedor.setEndereco(textFieldEndereco.getText());
            fornecedor.setTelefone(textFieldTelefone.getText());

            ModelValidarFornecedor.validarEntradaDeDados(fornecedor);

            buttonConfirmarClicked = true;
            dialogStage.close();

        } catch (ModelValidar e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fornecedor");
            alert.setHeaderText("Inserção de fornecedor:");
            alert.setContentText(e.getMessage());
            alert.show();

        }

    }

    public void handlButtonCancelar() {
        dialogStage.close();
    }

   

}
