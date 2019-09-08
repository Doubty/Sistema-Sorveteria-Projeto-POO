
package controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import bean.Categoria;
import bean.Fornecedor;
import bean.ViewRelatorio;
import dao.DaoViewRelatorio;
import dao.DatabaseFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Database;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



public class FXMLAnchorPaneRelatoriosEstoqueController implements Initializable {

    
	 @FXML
	    private TableView<ViewRelatorio> tableViewProdutos;
	    @FXML
	    private TableColumn<ViewRelatorio, Integer> tableColumnProdutoCodigo;
	    @FXML
	    private TableColumn<ViewRelatorio, String> tableColumnProdutoProduto;
	    @FXML
	    private TableColumn<ViewRelatorio, Float> tableColumnProdutoPreco;
	    @FXML
	    private TableColumn<ViewRelatorio, LocalDate> tableColumnProdutoValidade;
	    @FXML
	    private TableColumn<ViewRelatorio, Integer> tableColumnProdutoQuantidade;
	    @FXML
	    private TableColumn<ViewRelatorio, Categoria> tableColumnProdutoCategoria;
	    @FXML
	    private TableColumn<ViewRelatorio, Fornecedor> tableColumnProdutoFornecedor;
	    
	    
	    @FXML
	    private Button buttonImprimir;
	    
	    private List<ViewRelatorio> listProdutos;
	    private ObservableList<ViewRelatorio> observableListProdutos;

	    //Atributos para manipula��o de banco de dados
	    private final Database database = DatabaseFactory.getDatabase("postgresql");
	    private final Connection connection = database.conectar();
	    private final DaoViewRelatorio produtoDAO = new DaoViewRelatorio();
	
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO.setConnection(connection);
        carregarTableViewProdutos();
    }
    
    public void carregarTableViewProdutos() {
    	
    	tableColumnProdutoCodigo.setCellValueFactory(new PropertyValueFactory<>("id_produto"));
    	tableColumnProdutoProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnProdutoPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tableColumnProdutoValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));
        tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnProdutoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tableColumnProdutoFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        
        listProdutos = produtoDAO.listar();

        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        tableViewProdutos.setItems(observableListProdutos);
    }
    
    // Implementacao do botao para gerar o relatorio do produtos em estoque
 
    public void handleImprimir() throws JRException{
       

        URL url = getClass().getResource("../relatorios/SorveteriaRelatorioEstoque.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
        //Passando nada por paramentro, apenas a consulta inteira da tableview relatorio_estoque
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: para caso n�o exista nenhum filtro
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: para n�o deixar fechar o programa principal e n�o parar de rodar
        jasperViewer.setVisible(true);
    }
 
    
}