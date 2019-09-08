
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.Fornecedor;
import bean.Item_venda;
import bean.Produto;
import bean.Venda;
import dao.DaoItem_venda;
import dao.DaoProduto;
import dao.DaoVenda;
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

public class FXMLAnchorPaneProcessosVendasController implements Initializable {

	@FXML
	private TableView<Venda> tableViewVendas;
	@FXML
	private TableColumn<Venda, Integer> tableColumnVendasCodigo;
	@FXML
	private TableColumn<Venda, LocalDate> tableColumnVendasData;
	@FXML
	private TableColumn<Venda, Venda> tableColumnVendasVendedor;
	@FXML
	private TableColumn<Venda, Float> tableColumnVendasTotal;

	@FXML
	private Button buttonInserir;
	@FXML
	private Button buttonAlterar;
	@FXML
	private Button buttonRemover;

	@FXML
	private Label labelVendasCodigo;
	@FXML
	private Label labelVendasData;
	@FXML
	private Label labelVendasTotal;
	@FXML
	private Label labelVendasVendedorNome;
	@FXML
	private Label labelVendasVendedorSobrenome;
	@FXML
	private Label labelVendasVendedorCpf;
	@FXML
	private Label labelVendasVendedorTelefone;

	@FXML
	private RadioButton radioVendedor;
	@FXML
	private RadioButton radioData;
	@FXML
	private ToggleGroup toggleVendas;
	@FXML
	private TextField textFieldConsultaVendas;

	// Atributos para fazer a listagem das minhas vendas
	private List<Venda> listVendas;
	private ObservableList<Venda> observableListVendas;

	// Atributos para a manipulação do banco de dados
	private final Database database = DatabaseFactory.getDatabase("postgresql");
	private final Connection connection = database.conectar();
	private final DaoVenda vendaDAO = new DaoVenda();
	private final DaoItem_venda itemDeVendaDAO = new DaoItem_venda();
	private final DaoProduto produtoDAO = new DaoProduto();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vendaDAO.setConnection(connection); // Colocando na mesma conexão, senão dar erro
		carregarTableViewVendas();

		// Listen usado para exibir qualquer alteração na tabela de vendas
		tableViewVendas.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewVendas(newValue));
	}

	public void selecionarItemTableViewVendas(Venda venda) {
		if (venda != null) {
			labelVendasCodigo.setText(String.valueOf(venda.getId_venda()));
			labelVendasData.setText(String.valueOf(venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
			labelVendasTotal.setText(String.format("%.2f", venda.getTotal_venda()));
			labelVendasVendedorNome.setText(venda.getVendedor().getNome());
			labelVendasVendedorSobrenome.setText(venda.getVendedor().getSobrenome());
			labelVendasVendedorCpf.setText(venda.getVendedor().getCpf());
			labelVendasVendedorTelefone.setText(venda.getVendedor().getTelefone());
		} else {
			labelVendasCodigo.setText("");
			labelVendasData.setText("");
			labelVendasCodigo.setText("");
			labelVendasVendedorNome.setText("");
			labelVendasVendedorNome.setText("");
			labelVendasVendedorNome.setText("");
			labelVendasVendedorNome.setText("");
		}
	}

	public void carregarTableViewVendas() {
		// Apenas sentado de acordo com as colunas quais campos do banco de dados vai
		// aparecer
		tableColumnVendasCodigo.setCellValueFactory(new PropertyValueFactory<>("id_venda"));
		tableColumnVendasData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnVendasVendedor.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
		tableColumnVendasTotal.setCellValueFactory(new PropertyValueFactory<>("total_venda"));

		listVendas = vendaDAO.listar();

		observableListVendas = FXCollections.observableArrayList(listVendas);
		tableViewVendas.setItems(observableListVendas);
	}

	@FXML
	public void handleButtonInserir() throws IOException {

		Venda venda = new Venda();

		List<Item_venda> listItensDeVenda = new ArrayList<>();
		venda.setItensDeVenda(listItensDeVenda);
		boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosVendasDialog(venda);
		
		if (buttonConfirmarClicked) {
			try {
				
				connection.setAutoCommit(false);
				vendaDAO.setConnection(connection);
				vendaDAO.inserir(venda);
				itemDeVendaDAO.setConnection(connection);
				produtoDAO.setConnection(connection);
				
				// Para venda será necessário armazenar um item de venda da tabela
				
				for (Item_venda listItemDeVenda : venda.getItensDeVenda()) {
					Produto produto = listItemDeVenda.getProduto();
					listItemDeVenda.setVenda(vendaDAO.buscarUltimaVenda());
					itemDeVendaDAO.inserir(listItemDeVenda);
					produto.setQuantidade(produto.getQuantidade() - listItemDeVenda.getQuantidade());
					produtoDAO.alterar(produto);
				}
				connection.commit();
				carregarTableViewVendas();
			    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Vendas");
	            alert.setHeaderText("Inserção de venda:");
	            alert.setContentText("Venda inserida com sucesso!");
	            alert.show();
			} catch (SQLException ex) {
				try {
					connection.rollback();
				} catch (SQLException ex1) {
					Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null,
							ex1);
				}
				Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	@FXML
	public void handleButtonRemover() throws IOException, SQLException {
		Venda venda = tableViewVendas.getSelectionModel().getSelectedItem();
		if (venda != null) {

			connection.setAutoCommit(false);
			vendaDAO.setConnection(connection);
			itemDeVendaDAO.setConnection(connection);
			produtoDAO.setConnection(connection);

			for (Item_venda listItemDeVenda : venda.getItensDeVenda()) {
				Produto produto = listItemDeVenda.getProduto();
				produto.setQuantidade(produto.getQuantidade() + listItemDeVenda.getQuantidade());
				produtoDAO.alterar(produto);

				// Removendo primeiros os itens da lista
				itemDeVendaDAO.remover(listItemDeVenda);
			}
			// depois é apagada a venda
			vendaDAO.remover(venda);
			connection.commit();
			carregarTableViewVendas();
			  Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Vendas");
	            alert.setHeaderText("Remoção de venda:");
	            alert.setContentText("Venda removida com sucesso!");
	            alert.show();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha uma venda na Tabela!");
			alert.show();
		}
	}

	public boolean showFXMLAnchorPaneProcessosVendasDialog(Venda venda) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(FXMLAnchorPaneProcessosVendasInserirController.class
				.getResource("../view/FXMLAnchorPaneProcessosVendasInserir.fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		// Criando um estage de dialogo (Stage Dialog)
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Registro de Vendas");
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);

		// Setando a Venda no Controller.
		FXMLAnchorPaneProcessosVendasInserirController controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setVenda(venda);

		// Mostra o Dialog e espera até que o usuário feche a janela
		dialogStage.showAndWait();

		return controller.isButtonConfirmarClicked();

	}

	public void carregarTableViewVendas(ObservableList<Venda> observableListVendas1) {

		tableColumnVendasCodigo.setCellValueFactory(new PropertyValueFactory<>("id_venda"));
		tableColumnVendasData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnVendasVendedor.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
		tableColumnVendasTotal.setCellValueFactory(new PropertyValueFactory<>("total_venda"));

		// passando o observablelist de fornecedores para a tableViewFornecedores para
		// exibir
		tableViewVendas.setItems(observableListVendas1);
	}

	public void consultaVendas(KeyEvent event) {
		if (!textFieldConsultaVendas.getText().isEmpty()) {
			Iterator<Venda> inter = observableListVendas.iterator(); 
			ArrayList<Venda> colection = new ArrayList<Venda>();
			RadioButton selected = (RadioButton) toggleVendas.getSelectedToggle();

			if (selected.getText().equals("Vendedor")) {
				while (inter.hasNext()) {
					Venda f = (Venda) inter.next();
					if (String.valueOf(f.getVendedor()).contains(textFieldConsultaVendas.getText())) {
						colection.add(f);
					}
				}
			} else {
				while (inter.hasNext()) {
					Venda f = (Venda) inter.next();
					if (String.valueOf(f.getData()).contains(textFieldConsultaVendas.getText())) {
						colection.add(f);
					}
				}
			}
			carregarTableViewVendas(FXCollections.observableArrayList(colection));
		} else {
			carregarTableViewVendas(observableListVendas);
		}
	}

}