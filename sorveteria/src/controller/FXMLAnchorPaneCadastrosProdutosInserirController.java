package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import bean.Categoria;
import bean.Fornecedor;
import bean.Produto;
import dao.DaoCategoria;
import dao.DaoFornecedor;
import dao.DaoProduto;
import dao.DatabaseFactory;
import excecao.ModelValidar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Database;
import model.ModelValidarProduto;

public class FXMLAnchorPaneCadastrosProdutosInserirController implements Initializable {

    @FXML
    private TextField textFieldProdutoNome;
    @FXML
    private TextField textFieldProdutoPreco;
    @FXML
    private TextField textFieldProdutoDescricao;
    @FXML
    private TextField textFieldProdutoQuantidade;

    @FXML
    private DatePicker datePickerProdutoValidade;

    @FXML
    private ComboBox<Categoria> comboBoxProdutoCategoria;
    @FXML
    private ComboBox<Fornecedor> comboBoxProdutoFornecedor;

    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonNovaCategoria;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Produto produto;
    private Categoria categoria;

    private List<Categoria> listCategorias;
    private List<Fornecedor> listFornecedores;
    private ObservableList<Categoria> observableListCategorias;
    private ObservableList<Fornecedor> observableListFornecedores;

    private List<Produto> listProdutos; // Retorno da lista do banco de dados armazena no list
    private ObservableList<Produto> observableListProdutos; // usado para setar os dados na tableView

    // Atributos para realizar a conexão com o banco de dados, de acordo com um
    // padrão de projeto.
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final DaoProduto daoProduto = new DaoProduto();
    private final DaoCategoria daoCategoria = new DaoCategoria();
    private final DaoFornecedor daoFornecedor = new DaoFornecedor();

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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {

        this.produto = produto;
        // Setando os campos para parecer inicializando com os valores
        this.textFieldProdutoNome.setText(produto.getNome());
        this.textFieldProdutoPreco.setText("" + produto.getPreco());
        this.textFieldProdutoDescricao.setText(produto.getDescricao());
        this.textFieldProdutoQuantidade.setText("" + produto.getQuantidade());
        this.datePickerProdutoValidade.setValue(produto.getValidade());
        this.comboBoxProdutoCategoria.setValue(produto.getCategoria());
        this.comboBoxProdutoFornecedor.setValue(produto.getFornecedor());

    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void handleButtonInserirCategoria() throws IOException {

        Categoria categoria = new Categoria();

        boolean buttonConfirmarClicked = showFXMLAnchorPaneInserirCategorias(categoria);
        if (buttonConfirmarClicked) {
            daoCategoria.inserir(categoria);
            carregarComboBoxCategorias();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Categorias");
            alert.setHeaderText("Cadastro de nova categoria:");
            alert.setContentText("Categoria cadastrada com sucesso!");
            alert.show();
        }

    }

    public boolean showFXMLAnchorPaneInserirCategorias(Categoria categoria) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCategoriaInserirController.class.getResource("../view/FXMLAnchorPaneCategoriaInserir.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categorias");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando a categoria no Controller.
        // Pegando o controle da minha tela de cadastro de dados
        FXMLAnchorPaneCategoriaInserirController controller = loader.getController();

        controller.setDialogStage(dialogStage);
        controller.setCategoria(categoria);

        // Mostra a tela e fica em estado de esperando os dados
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

    @FXML
    public void handlButtonConfirmar() {
        // Teste usando o método que valida os campos do formulário de cadastro fornecedor

        try {

            produto.setNome(textFieldProdutoNome.getText());
            produto.setPreco(Float.parseFloat(textFieldProdutoPreco.getText()));
            produto.setDescricao(textFieldProdutoDescricao.getText());
            produto.setValidade(datePickerProdutoValidade.getValue());
            produto.setQuantidade(Integer.parseInt(textFieldProdutoQuantidade.getText()));
            produto.setCategoria(comboBoxProdutoCategoria.getSelectionModel().getSelectedItem());
            produto.setFornecedor(comboBoxProdutoFornecedor.getSelectionModel().getSelectedItem());

            ModelValidarProduto.validarEntradaDeDados(produto);

            buttonConfirmarClicked = true;
            dialogStage.close();

        } catch (ModelValidar e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Produto");
            alert.setHeaderText("Inserção de produto:");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    public void handlButtonCancelar() {
        dialogStage.close();
    }

    public void carregarComboBoxFornecedores() {

        listFornecedores = daoFornecedor.listar();
        observableListFornecedores = FXCollections.observableArrayList(listFornecedores);
        comboBoxProdutoFornecedor.setItems(observableListFornecedores);

    }

    public void carregarComboBoxCategorias() {

        listCategorias = daoCategoria.listar();

        observableListCategorias = FXCollections.observableArrayList(listCategorias);
        comboBoxProdutoCategoria.setItems(observableListCategorias);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        daoFornecedor.setConnection(connection);
        daoCategoria.setConnection(connection);

        carregarComboBoxFornecedores();
        carregarComboBoxCategorias();
    }

}
