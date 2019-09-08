package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.Categoria;
import bean.Fornecedor;
import bean.ViewRelatorio;

public class DaoViewRelatorio {
	
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public List<ViewRelatorio> listar() {
		String sql = "select * from relatorio_estoque";
		List<ViewRelatorio> retorno = new ArrayList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
               // alterei para obeter o nome do fornecedor e categoria
				ViewRelatorio viewRelatorio = new ViewRelatorio();
				
				Categoria categoria = new Categoria();
				Fornecedor fornecedor = new Fornecedor();

				viewRelatorio.setId_produto(resultado.getInt(1));
				viewRelatorio.setNome(resultado.getString(2));
				viewRelatorio.setPreco(resultado.getFloat(3));
				viewRelatorio.setValidade(resultado.getDate(4).toLocalDate());
				viewRelatorio.setQuantidade(resultado.getInt(5));
				
				categoria.setNome(resultado.getString(6)); // adicionado para pegar o nome da categoria
				
				fornecedor.setNome(resultado.getString(7)); // adicionado para pegar o nome do fornecedor
				
				
				viewRelatorio.setCategoria(categoria);
				viewRelatorio.setFornecedor(fornecedor);
				retorno.add(viewRelatorio);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DaoViewRelatorio.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

}
