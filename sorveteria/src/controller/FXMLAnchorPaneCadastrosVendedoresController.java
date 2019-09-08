package controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import bean.Fornecedor;
import bean.Vendedor;
import dao.DaoVendedor;
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




public class FXMLAnchorPaneCadastrosVendedoresController implements Initializable {

	    @FXML
	    private TableView<Vendedor> tableViewVendedores;
	    @FXML
	    private TableColumn<Vendedor, String> tableColumnVendedorNome;
	    @FXML
	    private TableColumn<Vendedor, String> tableColumnVendedorSobrenome;
	    @FXML
	    private TableColumn<Vendedor, String> tableColumnVendedorTurno;
	    @FXML
	    private TableColumn<Vendedor, Integer> tableColumnVendedorCargaHoraria;
	    @FXML
	    private TableColumn<Vendedor, String> tableColumnVendedorCPF;
	    @FXML
	    private TableColumn<Vendedor, Float> tableColumnVendedorSalario;
	    
	    @FXML
	    private Button buttonInserir;
	    @FXML
	    private Button buttonAlterar;
	    @FXML
	    private Button buttonRemover;
	    
	    
	    @FXML
	    private Label labelId_vendedor;   
	    @FXML
	    private Label labelData_inicio; 
	    @FXML
	    private Label labelData_nasc; 
	    @FXML
	    private Label labelEndereco; 
	    @FXML
	    private Label labelTelefone; 
	    @FXML
	    private Label labelEmail; 
	    @FXML
	    private Label labelId_usuario; 
	    
	    @FXML
	    private RadioButton radioNome;
	    @FXML
	    private RadioButton radioSobrenome;
	    @FXML
	    private RadioButton radioTurno;
	    @FXML
	    private RadioButton radioCPF;
	    @FXML
	    private ToggleGroup toggleVendedor;
	    @FXML 
	    private TextField textFieldConsultaVendedor;
	    
	    
	    
	    private List<Vendedor> listVendedores; // Retorno da lista do banco de dados armazena no list
	    private ObservableList<Vendedor> observableListVendedores; // usado para setar os dados na tableView
	    
	    // Atributos para realizar a conexão com o banco de dados, de acordo com um padrão de projeto.
	    private final Database database = DatabaseFactory.getDatabase("postgresql");
	    private final Connection connection = database.conectar();
	    private final DaoVendedor daoVendedor = new DaoVendedor();
	    
	    
	    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	// sentando a conexão criada como atributo
       daoVendedor.setConnection(connection);
       // Método para carregar a minha tableview de vendedores
    	carregarTableViewVendedores();
    	
    	  
        tableViewVendedores.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVendedores(newValue));
    	
    }
    
    public void carregarTableViewVendedores() {
    	
    	tableColumnVendedorNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	tableColumnVendedorSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
    	tableColumnVendedorTurno.setCellValueFactory(new PropertyValueFactory<>("Turno"));
    	tableColumnVendedorCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("carga_horaria"));
    	tableColumnVendedorCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	tableColumnVendedorSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
    	
      
         // pegando a lista de vendedores e setando na lista instanciada
        listVendedores = daoVendedor.listar();
       // convertendo a lista de vendedores em um observablelist de vendedores
        observableListVendedores = FXCollections.observableArrayList(listVendedores);
        // Passando o observablelist de fornecedores para a minha tableViewFornecedores para aparecer na tela
        tableViewVendedores.setItems(observableListVendedores);
    	
    }
    
    // Método usado para selecionar os itens da minha tableView, que no caso, são as linhas e setar os campos
    public void selecionarItemTableViewVendedores(Vendedor vendedor) {
    
    	// Teste caso a linha tenha um retorno de vendedor vazio
    	  if (vendedor != null) {
              labelId_vendedor.setText(String.valueOf(vendedor.getId_vendedor()));
              labelData_inicio.setText(vendedor.getData_inicio()+"");
              labelData_nasc.setText(vendedor.getData_nasc()+"");
              labelEndereco.setText(vendedor.getEndereco());
              labelTelefone.setText(vendedor.getTelefone());
              labelEmail.setText(vendedor.getEmail());
              labelId_usuario.setText(String.valueOf(vendedor.getId_usuario()));
             
          } else {
          
        	    labelId_vendedor.setText("");
                labelData_inicio.setText("");
                labelData_nasc.setText("");
                labelEndereco.setText("");
                labelTelefone.setText("");
                labelEmail.setText("");
                labelId_usuario.setText("");
          }
    }
    
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Vendedor vendedor = new Vendedor();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosVendedoresInserir(vendedor);
        if (buttonConfirmarClicked) {
            daoVendedor.inserir(vendedor);
            carregarTableViewVendedores();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vendedor");
            alert.setHeaderText("Inserção de Vendedor:");
            alert.setContentText("Vendedor inserido com sucesso!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Vendedor vendedor = tableViewVendedores.getSelectionModel().getSelectedItem();// Pegando o Vendedor Selecionado
        // teste caso nenhuma linha seja selecionada e o botão alterar é clicado
        if (vendedor != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosVendedoresInserir(vendedor);
            if (buttonConfirmarClicked) {
               daoVendedor.alterar(vendedor);
                carregarTableViewVendedores();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vendedor");
                alert.setHeaderText("Alteração de Vendedor:");
                alert.setContentText("Vendedor alterado com sucesso!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, é necessário escolher um vendedor na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Vendedor vendedor = tableViewVendedores.getSelectionModel().getSelectedItem();
        // Teste caso nenhuma linha seja selecionada e o botão remover é clicado
        if (vendedor != null) {
            daoVendedor.remover(vendedor);
            carregarTableViewVendedores();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vendedor");
            alert.setHeaderText("Remoção de Vendedor:");
            alert.setContentText("Vendedor removido com sucesso!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, é necessário escolher um vendedor na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosVendedoresInserir(Vendedor vendedor) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosVendedoresInserirController.class.getResource("../view/FXMLAnchorPaneCadastrosVendedoresInserir.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

  
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Vendedores");
       
        
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o vendedor no Controller.
        FXMLAnchorPaneCadastrosVendedoresInserirController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVendedor(vendedor);

        // Mostra a tela e fica em estado esperando os dados
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
    
public void carregarTableViewVendedores(ObservableList<Vendedor> observableListVendedores1){
    	
	tableColumnVendedorNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	tableColumnVendedorSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
	tableColumnVendedorTurno.setCellValueFactory(new PropertyValueFactory<>("Turno"));
	tableColumnVendedorCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("carga_horaria"));
	tableColumnVendedorCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	tableColumnVendedorSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
    
        
        // passando o observablelist de vendedores para a tableViewVendedores para exibir
        tableViewVendedores.setItems(observableListVendedores1);
    }

public void consultaVendedor(KeyEvent event) {
		if (!textFieldConsultaVendedor.getText().isEmpty()) {
			Iterator<Vendedor> inter = observableListVendedores.iterator(); //search with date the databases.
			ArrayList<Vendedor> colection = new ArrayList<Vendedor>();
			RadioButton selected = (RadioButton) toggleVendedor.getSelectedToggle();
			
			if (selected.getText().equals("Nome")) {
				while(inter.hasNext()) {
					Vendedor f = (Vendedor) inter.next();
					if(f.getNome().contains(textFieldConsultaVendedor.getText())){
						colection.add(f);
					}
				}
			} 
			
			if (selected.getText().equals("Sobrenome")) {
				while(inter.hasNext()) {
					Vendedor f = (Vendedor) inter.next();
					if(f.getSobrenome().contains(textFieldConsultaVendedor.getText())){
						colection.add(f);
					}
				}
			} 
			
			if (selected.getText().equals("Turno")) {
				while(inter.hasNext()) {
					Vendedor f = (Vendedor) inter.next();
					if(f.getTurno().contains(textFieldConsultaVendedor.getText())){
						colection.add(f);
					}
				}
			} 
			
			if (selected.getText().equals("CPF")) {
				while(inter.hasNext()) {
					Vendedor f = (Vendedor) inter.next();
					if(f.getCpf().contains(textFieldConsultaVendedor.getText())){
						colection.add(f);
					}
				}
			} 
			
			carregarTableViewVendedores(FXCollections.observableArrayList(colection));
		} else {
			carregarTableViewVendedores(observableListVendedores);
		}
	}

    
}