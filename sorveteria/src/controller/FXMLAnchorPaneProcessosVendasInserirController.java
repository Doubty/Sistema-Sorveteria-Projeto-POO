package controller;



import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import bean.Item_venda;
import bean.Produto;
import bean.Venda;
import bean.Vendedor;
import dao.DaoProduto;
import dao.DaoVendedor;
import dao.DatabaseFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Database;

public class FXMLAnchorPaneProcessosVendasInserirController implements Initializable {
    
	@FXML
	private ComboBox<Vendedor> comboBoxVendaVendedores;
	@FXML
	private DatePicker datePickerVendaData;
	@FXML
	private CheckBox checkBoxVendaPagamento;
	@FXML
	private ComboBox<Produto> comboBoxVendaProduto;

	@FXML
	private TableView<Item_venda> tableViewItemVenda;
	@FXML
	private TableColumn<Item_venda, Produto> tableColumnItemVendaProduto;
	@FXML
	private TableColumn<Item_venda, Integer> tableColumnItemVendaQuantidade;
	@FXML
	private TableColumn<Item_venda, Float> tableColumnItemVendaValorUnitario;
	@FXML
	private TableColumn<Item_venda, String> tableColumnItemVendaFornecedor;
	@FXML
	private TableColumn<Item_venda, String> tableColumnItemVendaCategoria;
	@FXML
	private TableColumn<Item_venda, Float> tableColumnItemVendaTotal;

	@FXML
	private TextField textFieldVendaValor;
	@FXML
	private TextField textFieldVendaItemVendaQuantidade;
	@FXML
	private Button buttonConfirmar;
	@FXML
	private Button buttonCancelar;
	@FXML
	private Button buttonAdicionar;


	private List<Vendedor> listVendedores;
	private List<Produto> listProdutos;
	private ObservableList<Vendedor> observableListVendedores;
	private ObservableList<Produto> observableListProdutos;
	
	private ObservableList<Item_venda> observableListItensDeVenda;

	// Atributos para manipulação do banco de dados
	private final Database database = DatabaseFactory.getDatabase("postgresql");
	private final Connection connection = database.conectar();
	private final DaoVendedor daoVendedor = new DaoVendedor();
	private final DaoProduto daoProduto = new DaoProduto();

	private Stage dialogStage;
	private boolean buttonConfirmarClicked = false;
	private Venda venda;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		daoVendedor.setConnection(connection);
		daoProduto.setConnection(connection);

		carregarComboBoxVendedores();
		carregarComboBoxProdutos();

		tableColumnItemVendaProduto.setCellValueFactory(new PropertyValueFactory<>("Produto"));
		tableColumnItemVendaQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
		tableColumnItemVendaValorUnitario.setCellValueFactory(new PropertyValueFactory<>("preco"));
		tableColumnItemVendaFornecedor.setCellValueFactory(new PropertyValueFactory<>(""));
		tableColumnItemVendaCategoria.setCellValueFactory(new PropertyValueFactory<>(""));
		tableColumnItemVendaTotal.setCellValueFactory(new PropertyValueFactory<>("total_itens"));

	}

	public void carregarComboBoxVendedores() {

		listVendedores = daoVendedor.listar();

		observableListVendedores = FXCollections.observableArrayList(listVendedores);
		comboBoxVendaVendedores.setItems(observableListVendedores);

		// Não será possível alterar uma venda, assim, não será implementado a seleção
		// de vendedores
	}

	public void carregarComboBoxProdutos() {

		listProdutos = daoProduto.listar();

		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		comboBoxVendaProduto.setItems(observableListProdutos);

		// Não será possível alterar uma venda, assim, não será implementado a seleção
		// de produtos (caso seja uma alteração de venda)
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

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	  @FXML
	    public void handleButtonAdicionar() {
	        Produto produto;
	        
	       Item_venda itemDeVenda = new Item_venda();
          // teste para a seleção do produto e guarda o produto pra realizar a venda
	        if (comboBoxVendaProduto.getSelectionModel().getSelectedItem() != null) {
	            produto = (Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem();

	            if (produto.getQuantidade() >= Integer.parseInt(textFieldVendaItemVendaQuantidade.getText())) {
	                itemDeVenda.setProduto((Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem());
	                itemDeVenda.setQuantidade(Integer.parseInt(textFieldVendaItemVendaQuantidade.getText()));
	                itemDeVenda.setTotal_itens(itemDeVenda.getProduto().getPreco() * itemDeVenda.getQuantidade());
	               
                     
	                venda.getItensDeVenda().add(itemDeVenda); // Setando no arraylist de itens de venda
	                venda.setTotal_venda(venda.getTotal_venda() + itemDeVenda.getTotal_itens()); // Valor da venda + itens de venda

	                observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
	                tableViewItemVenda.setItems(observableListItensDeVenda);

	                textFieldVendaValor.setText(String.format("%.2f", venda.getTotal_venda()));
	            } else {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setHeaderText("Erro ao escolher um produto!");
	                alert.setContentText("O produto estar em falta no estoque!");
	                alert.show();
	            }
	        }
	    }
	  
	    @FXML
	    public void handleButtonConfirmar() {
                
	        if (validarEntradaDeDados()) {
	            venda.setVendedor((Vendedor) comboBoxVendaVendedores.getSelectionModel().getSelectedItem());
	            venda.setPagamento(checkBoxVendaPagamento.isSelected());
	            venda.setData(datePickerVendaData.getValue());
	            buttonConfirmarClicked = true;
	            dialogStage.close();
                    
                    
	        }
	    }

	    @FXML
	    public void handleButtonCancelar() {
	        getDialogStage().close();
	    }
	    
	    private boolean validarEntradaDeDados() {
	        String errorMessage = "";

	        if (comboBoxVendaVendedores.getSelectionModel().getSelectedItem() == null) {
	            errorMessage += "Vendedor Inválido!\n";
	        }
	        if (datePickerVendaData.getValue() == null) {
	            errorMessage += "Data Inválida!\n";
	        }
	        if (observableListItensDeVenda == null) {
	            errorMessage += "Itens de Venda Inválidos!\n";
	        }

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Mostrando a mensagem de erro
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Erro no cadastro!");
	            alert.setHeaderText("Campos inválidos! por favor, é necessários corrigi-los");
	            alert.setContentText(errorMessage);
	            alert.show();
	            return false;
	        }
	    }
	  

}
