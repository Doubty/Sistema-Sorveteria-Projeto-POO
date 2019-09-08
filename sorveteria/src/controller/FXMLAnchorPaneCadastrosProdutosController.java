
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import bean.Produto;
import dao.DaoProduto;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Database;

public class FXMLAnchorPaneCadastrosProdutosController implements Initializable {

	@FXML
	private TableView<Produto> tableViewProdutos;
	@FXML
	private TableColumn<Produto, String> tableColumnProduto;
	@FXML
	private TableColumn<Produto, String> tableColumnPreco;
	@FXML
	private TableColumn<Produto, String> tableColumnDescricao;
	@FXML
	private TableColumn<Produto, LocalDate> tableColumnValidade;
	@FXML
	private TableColumn<Produto, Integer> tableColumnQuantidade;

	@FXML
	private Button buttonInserir;
	@FXML
	private Button buttonAlterar;
	@FXML
	private Button buttonRemover;

	@FXML
	private Label labelIdProduto;
	@FXML
	private Label labelNome;
	@FXML
	private Label labelPreco;
	@FXML
	private Label labelDescricao;
	@FXML
	private Label labelValidade;
	@FXML
	private Label labelQuantidade;
	@FXML
	private Label labelCategoria;
	@FXML
	private Label labelFornecedor;
	
	@FXML
	private TextField textFieldConsultaProduto;

	private List<Produto> listProdutos; // Retorno da lista do banco de dados armazena no list
	private ObservableList<Produto> observableListProdutos; // usado para setar os dados na tableView

	// Atributos para realizar a conexão com o banco de dados, de acordo com um
	// padrão de projeto.
	private final Database database = DatabaseFactory.getDatabase("postgresql");
	private final Connection connection = database.conectar();
	private final DaoProduto daoProduto = new DaoProduto();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		daoProduto.setConnection(connection);
		carregarTableViewProdutos();

		tableViewProdutos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewProdutos(newValue));
	}

	public void carregarTableViewProdutos() {

		tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		// retornando uma lista de fornecedores
		listProdutos = daoProduto.listar();
		// convertendo minha lista de fornecedores em um observablelist de fornecedores
		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		// passando o observablelist de fornecedores para a tableViewFornecedores para
		// exibir
		tableViewProdutos.setItems(observableListProdutos);

	}
	
	public void carregarTableViewProdutos(ObservableList<Produto> observableProdutos) {

		tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));
		tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		tableViewProdutos.setItems(observableProdutos);

	}
	

	public void consultaProdutos(KeyEvent event) {
		
		if (!textFieldConsultaProduto.getText().isEmpty()) {
			Iterator<Produto> inter = observableListProdutos.iterator(); //search with date the databases.
			List<Produto> colection = new ArrayList<Produto>();
			
			while(inter.hasNext()) {
				Produto produto = (Produto) inter.next();
				if((produto.getNome()).contains(textFieldConsultaProduto.getText())){
					colection.add(produto);
				}
			}
			carregarTableViewProdutos(FXCollections.observableArrayList(colection));
		} else {
			carregarTableViewProdutos(observableListProdutos);
		}
	}

	public void selecionarItemTableViewProdutos(Produto produto) {

		// Teste caso a linha tenha um retorno de Fornecedor vazio
		if (produto != null) {
			labelIdProduto.setText(String.valueOf(produto.getId_produto()));
			labelNome.setText(produto.getNome());
			labelPreco.setText(String.valueOf(produto.getPreco()));
			labelDescricao.setText(produto.getDescricao());
			labelValidade.setText(String.valueOf(produto.getValidade()));
			labelQuantidade.setText(String.valueOf(produto.getQuantidade()));
			labelCategoria.setText(String.valueOf(produto.getCategoria()));
			labelFornecedor.setText(String.valueOf(produto.getFornecedor()));
		} else {

			labelIdProduto.setText("");
			labelNome.setText("");
			labelPreco.setText("");
			labelDescricao.setText("");
			labelValidade.setText("");
			labelQuantidade.setText("");
			labelCategoria.setText("");
			labelFornecedor.setText("");
		}

	}
	
	 public void handleButtonInserir() throws IOException {
	    	
	       Produto produto = new Produto();
	        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosProdutosInserir(produto);
	        if (buttonConfirmarClicked) {
	            daoProduto.inserir(produto);
	            carregarTableViewProdutos();
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Produtos");
	            alert.setHeaderText("Inserção de produto:");
	            alert.setContentText("Produto inserido com sucesso!");
	            alert.show();
	        }
	        
	    }
	 
	 public void handleButtonInserirCategoria() throws IOException {
	    	
	       Produto produto = new Produto();
	        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosProdutosInserir(produto);
	        if (buttonConfirmarClicked) {
	            daoProduto.inserir(produto);
	            carregarTableViewProdutos();
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Categoria");
	            alert.setHeaderText("Cadastro de nova categoria:");
	            alert.setContentText("Categoria cadastrada com sucesso!");
	            alert.show();
	        }
	        
	    }

	    @FXML
	    public void handleButtonAlterar() throws IOException {
	    	Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();// Pegando o produto Selecionado
	        // teste caso nenhuma linha seja selecionada e o botão alterar é clicado
	        if (produto != null) {
	            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosProdutosInserir(produto);
	            if (buttonConfirmarClicked) {
	               daoProduto.alterar(produto);
	                carregarTableViewProdutos();
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Produtos");
	                alert.setHeaderText("Alteração de produto:");
	                alert.setContentText("Produto alterado com sucesso!");
	                alert.show();
	            }
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, é necessário escolher um produto na Tabela!");
	            alert.show();
	        }
	    }

	    @FXML
	    public void handleButtonRemover() throws IOException {
	        Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
	        // Teste caso nenhuma linha seja selecionada e o botão remover é clicado
	        if (produto != null) {
	            daoProduto.remover(produto);
	            carregarTableViewProdutos();
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Produtos");
	            alert.setHeaderText("Remoção de produto:");
	            alert.setContentText("Produto removido com sucesso!");
	            alert.show();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, é necessário escolher um produto na Tabela!");
	            alert.show();
	        }
	    }
	    
	    public boolean showFXMLAnchorPaneCadastrosProdutosInserir(Produto produto) throws IOException {
	    	
	  	  FXMLLoader loader = new FXMLLoader();
          loader.setLocation(FXMLAnchorPaneCadastrosProdutosInserirController.class.getResource("../view/FXMLAnchorPaneCadastrosProdutosInserir.fxml"));
          AnchorPane page = (AnchorPane) loader.load();

    
          Stage dialogStage = new Stage();
          dialogStage.setTitle("Cadastro de Produtos");
         
          
          Scene scene = new Scene(page);
          dialogStage.setScene(scene);

          // Setando o produto no Controller.
          // Pegando o controle da minha tela de inserção de dados
          
          FXMLAnchorPaneCadastrosProdutosInserirController controller = loader.getController();
          
          controller.setDialogStage(dialogStage);
          controller.setProduto(produto);

          // Mostra a tela e fica em estado de esperando os dados
          dialogStage.showAndWait();

          return controller.isButtonConfirmarClicked();
	    }
}
