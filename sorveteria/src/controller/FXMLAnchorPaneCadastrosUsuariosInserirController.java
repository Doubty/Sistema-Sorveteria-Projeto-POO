package controller;

import java.net.URL;
import java.util.ResourceBundle;
import bean.Usuario;
import excecao.ModelValidar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ModelValidarUsuario;

public class FXMLAnchorPaneCadastrosUsuariosInserirController implements Initializable {

    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldSenha;
    @FXML
    private TextField textFieldNivel;

    @FXML
    private Button buttonConfimar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage; // Atributo para abrir e fechar a tela cadastro usuario
    private boolean buttonConfirmarClicked = false; // Atributo para verificar se o botão foi clicado
    private Usuario usuario; // Classe usada como modelo para inserir os dados

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        // setar os textfields para o valor já começar inicializado
        this.usuario = usuario;
        this.textFieldNome.setText(usuario.getUser());
        this.textFieldSenha.setText(usuario.getSenha());
        this.textFieldNivel.setText(usuario.getNivel() + "");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handlButtonConfirmar() throws ModelValidar {
        // Teste usando o método que valida os campos do formulário de cadastro usuário

        try {
            
            usuario.setUser(textFieldNome.getText());
            usuario.setSenha(textFieldSenha.getText());
            usuario.setNivel(Integer.parseInt(textFieldNivel.getText()));

            ModelValidarUsuario.validarEntradaDeDados(usuario);

            buttonConfirmarClicked = true;
            dialogStage.close();
        } catch (ModelValidar e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Usuario:");
            alert.setHeaderText("Inserção de usuário:");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void handlButtonCancelar() {
        dialogStage.close();
    }

}
