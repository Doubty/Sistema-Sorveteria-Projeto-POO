
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import bean.Fornecedor;
import bean.Usuario;
import dao.DaoUsuario;
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

public class FXMLAnchorPaneCadastrosUsuariosController implements Initializable {

	@FXML
	private TableView<Usuario> tableViewUsuarios;
	@FXML
	private TableColumn<Usuario, Integer> itemTableCodigo;
	@FXML
	private TableColumn<Usuario, String> itemTableLogin;
	@FXML
	private TableColumn<Usuario, String> itemTableSenha;
	@FXML
	private TableColumn<Usuario, String> itemTableNivel;

	@FXML
	private Button buttonInserir;
	@FXML
	private Button buttonAlterar;
	@FXML
	private Button buttonRemover;

	@FXML
	private Label labelCodigo;
	@FXML
	private Label labelLogin;
	@FXML
	private Label labelSenha;
	
    @FXML
    private RadioButton radioUsuarioLogin;
    @FXML
    private RadioButton radioNivel;
    @FXML
    private ToggleGroup radioConsulta;
	@FXML
	private TextField textFieldConsultaUsuario;

	private List<Usuario> listUsuarios; // Retorno da lista do banco de dados armazena no list
	private ObservableList<Usuario> observableListUsuarios; // usado para setar os dados na tableView

	// Atributos para realizar a conexão com o banco de dados, de acordo com um
	// padrão de projeto.
	private final Database database = DatabaseFactory.getDatabase("postgresql");
	private final Connection connection = database.conectar();
	private final DaoUsuario daoUsuario = new DaoUsuario();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		daoUsuario.setConnection(connection); 
		carregarTableViewUsuarios();
		
	    tableViewUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewUsuarios(newValue));
	}
	
	   public void selecionarItemTableViewUsuarios(Usuario usuario) {
	        
	    	// Teste caso a linha tenha um retorno de usuario vazio
	    	  if (usuario != null) {
	    		  
	    		  labelCodigo.setText(String.valueOf(usuario.getId()));
	    		  labelLogin.setText(usuario.getUser());
	    		  labelSenha.setText(usuario.getSenha());
	             
	          } else {
	          
	        	  labelCodigo.setText("");
	    		  labelLogin.setText("");
	    		  labelSenha.setText("");
	          }
	    }

	public void carregarTableViewUsuarios() {

		
		itemTableCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		itemTableLogin.setCellValueFactory(new PropertyValueFactory<>("user"));
		itemTableSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
		itemTableNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

		// retornando uma lista de usuarios
		listUsuarios = daoUsuario.listar();
		// convertendo minha lista de usuarios em um observablelist de usuarios
		observableListUsuarios = FXCollections.observableArrayList(listUsuarios);
		// passando o observablelist de usuarios para a tableViewUsuarios para exibir
		tableViewUsuarios.setItems(observableListUsuarios);
		
	}
	
	public void carregarTableViewUsuarios(ObservableList<Usuario> observableListUsuarios1) {

		itemTableCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		itemTableLogin.setCellValueFactory(new PropertyValueFactory<>("user"));
		itemTableSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
		itemTableNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));


		// passando o observablelist de usuarios para a tableViewUsuarios para exibir
		tableViewUsuarios.setItems(observableListUsuarios1);
	}
	
	public void consultaUsuario(KeyEvent event) {
		
		if (!textFieldConsultaUsuario.getText().isEmpty()) {
			Iterator<Usuario> inter = observableListUsuarios.iterator(); 
			ArrayList<Usuario> colection = new ArrayList<Usuario>();
			RadioButton selected = (RadioButton) radioConsulta.getSelectedToggle();
			
			if (selected.getText().equals("Usuario")) {
				while(inter.hasNext()) {
					Usuario f = (Usuario) inter.next();
					if(f.getUser().contains(textFieldConsultaUsuario.getText())){
						colection.add(f);
					}
				}
			} else {
				while(inter.hasNext()) {
					Usuario f = (Usuario) inter.next();
					if( (f.getNivel()+"").contains(textFieldConsultaUsuario.getText()) ){
						colection.add(f);
					}
				}
			}
			carregarTableViewUsuarios(FXCollections.observableArrayList(colection));
		} else {
			carregarTableViewUsuarios(observableListUsuarios);
		}
	}
	
	
	  public void handleButtonInserir() throws IOException {
	    	
	       Usuario usuario = new Usuario();
	        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUsuariosInserir(usuario);
	        if (buttonConfirmarClicked) {
	            daoUsuario.inserir(usuario);
	            carregarTableViewUsuarios();
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Usuario");
	            alert.setHeaderText("Inserção de usuario:");
	            alert.setContentText("Usuario inserido com sucesso!");
	            alert.show();
	        }
	        
	    }

	    @FXML
	    public void handleButtonAlterar() throws IOException {
	    	Usuario usuario = tableViewUsuarios.getSelectionModel().getSelectedItem();// Pegando o usuario Selecionado
	        // teste caso nenhuma linha seja selecionada e o botão alterar é clicado
	        if (usuario != null) {
	            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUsuariosInserir(usuario);
	            if (buttonConfirmarClicked) {
	               daoUsuario.alterar(usuario);
	                carregarTableViewUsuarios();
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Usuário");
	                alert.setHeaderText("Alteração de usuário:");
	                alert.setContentText("Usuário alterado com sucesso!");
	                alert.show();
	            }
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, é necessário escolher um usuário na Tabela!");
	            alert.show();
	        }
	    }

	    @FXML
	    public void handleButtonRemover() throws IOException {
	    	Usuario usuario = tableViewUsuarios.getSelectionModel().getSelectedItem();
	        // Teste caso nenhuma linha seja selecionada e o botão remover é clicado
	        if (usuario != null) {
	            daoUsuario.remover(usuario);
	            carregarTableViewUsuarios();
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Usuário");
	            alert.setHeaderText("Remoção de usuário:");
	            alert.setContentText("Usuário removido com sucesso!");
	            alert.show();
	        } else {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setContentText("Por favor, é necessário escolher um usuário na Tabela!");
	            alert.show();
	        }
	    }
	    
	    public boolean showFXMLAnchorPaneCadastrosUsuariosInserir(Usuario usuario) throws IOException {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(FXMLAnchorPaneCadastrosUsuariosInserirController.class.getResource("../view/FXMLAnchorPaneCadastrosUsuariosInserir.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	  
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Cadastro de usuarios");
	       
	        
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Setando o usuario no Controller.
	        // Pegando o controle da minha tela de inserção de dados
	        
	        FXMLAnchorPaneCadastrosUsuariosInserirController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setUsuario(usuario);

	        // Mostra a tela e fica em estado de esperando os dados
	        dialogStage.showAndWait();

	        return controller.isButtonConfirmarClicked();

	    }
	
	
	

}
