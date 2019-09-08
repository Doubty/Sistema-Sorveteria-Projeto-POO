package controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import bean.Categoria;
import dao.DaoCategoria;
import dao.DatabaseFactory;
import excecao.ModelValidar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Database;
import model.ModelValidarCategoria;

public class FXMLAnchorPaneCategoriaInserirController implements Initializable {

    @FXML
    private TextField textFieldNome;
    @FXML
    private Button buttonConfimar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Categoria categoria;

    private List<Categoria> listCategorias; // Retorno da lista do banco de dados armazena no list
    private ObservableList<Categoria> observableListCategorias; // usado para setar os dados na tableView

    // Atributos para realizar a conexão com o banco de dados, de acordo com um
    // padrão de projeto.
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final DaoCategoria daoCategoria = new DaoCategoria();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void handlButtonConfirmar() {

        try {

            categoria.setNome(textFieldNome.getText());
            ModelValidarCategoria.validarEntradaDeDados(categoria);
            // Usuário clicou no botão
            buttonConfirmarClicked = true;
            dialogStage.close();

        } catch (ModelValidar e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Categoria");
            alert.setHeaderText("Cadastro de categoria:");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    public void handlButtonCancelar() {
        dialogStage.close();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
