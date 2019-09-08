
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import bean.Fornecedor;
import dao.DaoFornecedor;
import dao.DatabaseFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Database;

public class FXMLAnchorPaneCadastrosFornecedoresController implements Initializable {

	@FXML
	private TableView<Fornecedor> tableViewFornecedores;
	@FXML
	private TableColumn<Fornecedor, String> tableViewColumnFornecedoresNome;
	@FXML
	private TableColumn<Fornecedor, String> tableViewColumnFornecedoresCnpj;
	@FXML
	private TableColumn<Fornecedor, String> tableViewColumnFornecedoresEndereco;
	@FXML
	private TableColumn<Fornecedor, String> tableViewColumnFornecedoresTelefone;

	@FXML
	private Button buttonInserir;
	@FXML
	private Button buttonAlterar;
	@FXML
	private Button buttonRemover;

	@FXML
	private Label labelFornecedorId_fornecedor;
	@FXML
	private Label labelFornecedorCnpj;
	@FXML
	private Label labelFornecedorNome;
	@FXML
	private Label labelFornecedorEndereco;
	@FXML
	private Label labelFornecedorTelefone;

	@FXML
	private RadioButton radioNome;
	@FXML
	private RadioButton radioCnpj;
	@FXML
	private ToggleGroup radioConsulta;
	@FXML
	private TextField textFieldConsulta;

	private List<Fornecedor> listFornecedores; // Retorno da lista do banco de dados armazena no list
	private ObservableList<Fornecedor> observableListFornecedores; // usado para setar os dados na tableView

	// Atributos para realizar a conexão com o banco de dados, de acordo com um
	// padrão de projeto.
	private final Database database = DatabaseFactory.getDatabase("postgresql");
	private final Connection connection = database.conectar();
	private final DaoFornecedor daoFornecedor = new DaoFornecedor();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		daoFornecedor.setConnection(connection); // Setando a conexão que foi instanciada
		carregarTableViewFornecedores();
		// listen para verificar as alterações da lista, usando um padrão de projeto

		tableViewFornecedores.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewFornecedores(newValue));

	}

	public void selecionarItemTableViewFornecedores(Fornecedor fornecedor) {

		// Teste caso a linha tenha um retorno de Fornecedor vazio
		if (fornecedor != null) {
			labelFornecedorId_fornecedor.setText(String.valueOf(fornecedor.getId_fornecedor()));
			labelFornecedorCnpj.setText(fornecedor.getCnpj());
			labelFornecedorNome.setText(fornecedor.getNome());
			labelFornecedorEndereco.setText(fornecedor.getTelefone());
			labelFornecedorTelefone.setText(fornecedor.getTelefone());

		} else {

			labelFornecedorId_fornecedor.setText("");
			labelFornecedorCnpj.setText("");
			labelFornecedorNome.setText("");
			labelFornecedorEndereco.setText("");
			labelFornecedorTelefone.setText("");
		}
	}

	// Método para carregar meus fornecedores na tabela de fornecedores
	public void carregarTableViewFornecedores() {

		tableViewColumnFornecedoresNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableViewColumnFornecedoresCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		tableViewColumnFornecedoresEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableViewColumnFornecedoresTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

		// retornando uma lista de fornecedores
		listFornecedores = daoFornecedor.listar();
		// convertendo minha lista de fornecedores em um observablelist de fornecedores
		observableListFornecedores = FXCollections.observableArrayList(listFornecedores);
		// passando o observablelist de fornecedores para a tableViewFornecedores para
		// exibir
		tableViewFornecedores.setItems(observableListFornecedores);
	}

	public void handleButtonInserir() throws IOException {

		Fornecedor fornecedor = new Fornecedor();
		boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosFornecedoresInserir(fornecedor);
		if (buttonConfirmarClicked) {
			daoFornecedor.inserir(fornecedor);
			carregarTableViewFornecedores();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Fornecedor");
			alert.setHeaderText("Inserção de fornecedor:");
			alert.setContentText("Fornecedor inserido com sucesso!");
			alert.show();
		}

	}

	@FXML
	public void handleButtonAlterar() throws IOException {
		Fornecedor fornecedor = tableViewFornecedores.getSelectionModel().getSelectedItem();// Pegando o fornecedor
																							// Selecionado
		// teste caso nenhuma linha seja selecionada e o botão alterar é clicado
		if (fornecedor != null) {
			boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosFornecedoresInserir(fornecedor);
			if (buttonConfirmarClicked) {
				daoFornecedor.alterar(fornecedor);
				carregarTableViewFornecedores();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Fornecedor");
				alert.setHeaderText("Alteração de fornecedor:");
				alert.setContentText("Fornecedor alterado com sucesso!");
				alert.show();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, é necessário escolher um fornecedor na Tabela!");
			alert.show();
		}
	}

	@FXML
	public void handleButtonRemover() throws IOException {
		Fornecedor fornecedor = tableViewFornecedores.getSelectionModel().getSelectedItem();
		// Teste caso nenhuma linha seja selecionada e o botão remover é clicado
		if (fornecedor != null) {
			daoFornecedor.remover(fornecedor);
			carregarTableViewFornecedores();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Fornecedor");
			alert.setHeaderText("Remoção de fornecedor:");
			alert.setContentText("Fornecedor removido com sucesso!");
			alert.show();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, é necessário escolher um fornecedor na Tabela!");
			alert.show();
		}
	}

	public boolean showFXMLAnchorPaneCadastrosFornecedoresInserir(Fornecedor fornecedor) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(FXMLAnchorPaneCadastrosFornecedoresInserir.class
				.getResource("../view/FXMLAnchorPaneCadastrosFornecedoresInserir.fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		Stage dialogStage = new Stage();
		dialogStage.setTitle("Cadastro de Fornecedores");

		Scene scene = new Scene(page);
		dialogStage.setScene(scene);

		// Setando o fornecedor no Controller.
		// Pegando o controle da minha tela de inserção de dados
		FXMLAnchorPaneCadastrosFornecedoresInserir controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setFornecedor(fornecedor);

		// Mostra a tela e fica em estado de esperando os dados
		dialogStage.showAndWait();

		return controller.isButtonConfirmarClicked();

	}

	public void carregarTableViewFornecedores(ObservableList<Fornecedor> observableListFornecedores1) {

		tableViewColumnFornecedoresNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableViewColumnFornecedoresCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		tableViewColumnFornecedoresEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableViewColumnFornecedoresTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

		// passando o observablelist de fornecedores para a tableViewFornecedores para
		// exibir
		tableViewFornecedores.setItems(observableListFornecedores1);
	}

	public void consultaFornecedor(KeyEvent event) {
		if (!textFieldConsulta.getText().isEmpty()) {
			Iterator<Fornecedor> inter = observableListFornecedores.iterator(); // search with date the databases.
			ArrayList<Fornecedor> colection = new ArrayList<Fornecedor>();
			RadioButton selected = (RadioButton) radioConsulta.getSelectedToggle();

			if (selected.getText().equals("Nome")) {
				while (inter.hasNext()) {
					Fornecedor f = (Fornecedor) inter.next();
					if (f.getNome().contains(textFieldConsulta.getText())) {
						colection.add(f);
					}
				}
			} else {
				while (inter.hasNext()) {
					Fornecedor f = (Fornecedor) inter.next();
					if (f.getCnpj().contains(textFieldConsulta.getText())) {
						colection.add(f);
					}
				}
			}
			carregarTableViewFornecedores(FXCollections.observableArrayList(colection));
		} else {
			carregarTableViewFornecedores(observableListFornecedores);
		}
	}

}
