package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bean.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import sorveteria.Main;

public class FXMLVBoxMainController implements Initializable {

    @FXML
    private MenuItem menuItemCadastrosUsuarios;

    @FXML
    private MenuItem menuItemCadastrosVendedores;

    @FXML
    private MenuItem menuItemCadastrosProdutos;

    @FXML
    private MenuItem menuItemCadastrosEstoque;

    @FXML
    private MenuItem menuItemCadastrosFornecedor;

    @FXML
    private MenuItem menuItemProcessosVendas;

    @FXML
    private MenuItem menuItemAnalisesVendasMensais;

    @FXML
    private MenuItem menuItemRelatoriosProdutosEmEstoque;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label labelUsuarioAcesso;
    @FXML
    private Label labelUsuarioNome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Main.addOnChangeScreenListeners(new Main.OnChangeScreen() {
            // gera uma classe interna anônima
            @Override
            public void onScreenChanged(String newScreen, Usuario usuario) {
                System.out.println("Tela " + newScreen + "Usuario: " + usuario.getUser());
				// Bloqueando as telas de acordo com vendedor ou admin

                if (usuario.getNivel() == 0) {
                    menuItemCadastrosUsuarios.setVisible(false);
                    menuItemCadastrosVendedores.setVisible(false);
                    labelUsuarioNome.setText(usuario.getUser());
                    labelUsuarioAcesso.setText("Vendedor");
                } else {
                    labelUsuarioNome.setText(usuario.getUser());
                    labelUsuarioAcesso.setText("Admin");
                }

            }
        });

    }

    // actions para abrir as telas atraves dos meus do meu mainBox
    @FXML
    public void handleMenuItemCadastrosVendedores() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastrosVendedores.fxml"));
        anchorPane.getChildren().setAll(a);

    }

    @FXML
    public void handleMenuCadastrosFornecedores() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastrosFornecedores.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemProcessosVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneProcessosVendas.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemAnalisesVendasPorMes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneGraficosVendasPorMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemRelatoriosEstoque() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneRelatoriosEstoque.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuCadastrosProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastrosProdutos.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuCadastrosUsuarios() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastrosUsuarios.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
