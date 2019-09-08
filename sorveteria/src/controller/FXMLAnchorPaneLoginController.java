package controller;

import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import bean.Usuario;
import dao.DaoUsuario;
import dao.DatabaseFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Database;
import sorveteria.Main;


public class FXMLAnchorPaneLoginController implements Initializable {

	@FXML
	private TextField usuario;
	@FXML
	private PasswordField senha;

	// Atributos para manipulação de banco de dados

	private final Database database = DatabaseFactory.getDatabase("postgresql");
	private final Connection connection = database.conectar();
	private final DaoUsuario daoUsuario = new DaoUsuario();
	Usuario user = new Usuario(); // Usado para guardar o usuário logado

	@FXML
	public void entrar(ActionEvent event) { // ajeitar depois

		daoUsuario.setConnection(connection);

		try {
			
			user = daoUsuario.checkLogin(usuario.getText(), senha.getText());
           
			if (user.getSenha() != null && user.getUser() != null) {
             
                
				Main.mudarTela("painel", user);
              
	
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Erro. Usuário ou senha incorretos!");
				alert.show();
			}

		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("ERRO HEADEXP: " + e);
			alert.show();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("ERRO SQLEXP: " + e);
			alert.show();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	
}
