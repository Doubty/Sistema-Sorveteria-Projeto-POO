package sorveteria;

import java.util.ArrayList;

import bean.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Stage stage;
	
	private static Scene mainLogin;
	private static Scene painelScene;
	

	public void start(Stage palco) throws Exception {
		
		stage = palco;
		
		palco.setTitle("Sorveteria OBA OBA");
		
		Parent login = FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneLogin.fxml"));
		mainLogin = new Scene(login);
		
		Parent painel = FXMLLoader.load(getClass().getResource("../view/FXMLVboxMain.fxml"));
		painelScene = new Scene(painel);
		
	
		
		palco.setScene(mainLogin);
		palco.show();
		
	}
	
	public static void mudarTela(String src, Usuario usuario){
		switch(src){
		case "login": 
		stage.setScene(mainLogin);
		notifyAllListeners("Login", usuario);
		
		break;
		case "painel":
			stage.setScene(painelScene);
			notifyAllListeners("Painel", usuario);
			break;
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/*Criação do padrão de projeto observer para a navegação de telas passando o usuário entre painelPrincipal e login*/
	private static ArrayList<OnChangeScreen> listeners = new ArrayList<>();
	
	public static interface OnChangeScreen{
		void onScreenChanged(String newScreen, Usuario usuario);
	}
	
	public static void addOnChangeScreenListeners(OnChangeScreen newListeners){
		listeners.add(newListeners);
	}
	
	private static void notifyAllListeners(String newScreen, Usuario usuario){
		for(OnChangeScreen l : listeners){
			l.onScreenChanged(newScreen, usuario);
		}
	}
	
	// Fim da implementação do observer
}