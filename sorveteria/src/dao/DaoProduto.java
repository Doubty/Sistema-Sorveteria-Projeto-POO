package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.Categoria;
import bean.Fornecedor;
import bean.Produto;
import bean.Venda;


public class DaoProduto {

	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, descricao, validade, id_categoria, id_fornecedor, quantidade) VALUES(?,?,?,?,?,?,?)";
        try {
        	
            PreparedStatement stmt = connection.prepareStatement(sql); 
            
            stmt.setString(1, produto.getNome());
            stmt.setFloat(2, produto.getPreco());
            stmt.setString(3, produto.getDescricao());
            stmt.setDate(4, Date.valueOf(produto.getValidade()));
            stmt.setInt(5, produto.getCategoria().getId_categoria());
            stmt.setInt(6, produto.getFornecedor().getId_fornecedor());
            stmt.setInt(7, produto.getQuantidade());
            
            stmt.execute();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoProduto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
	 public boolean alterar(Produto produto) {
	        String sql = "UPDATE produto SET nome=?, preco=?, descricao=?, validade=?, id_categoria=?, id_fornecedor=?, quantidade=? WHERE id_produto=?";
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            
	            stmt.setString(1, produto.getNome());
	            stmt.setFloat(2, produto.getPreco());
	            stmt.setString(3, produto.getDescricao());
	            stmt.setDate(4, Date.valueOf(produto.getValidade()));
	            stmt.setInt(5, produto.getCategoria().getId_categoria());
	            stmt.setInt(6, produto.getFornecedor().getId_fornecedor());
	            stmt.setInt(7, produto.getQuantidade());
	            stmt.setInt(8, produto.getId_produto());
	            
	            stmt.execute();
	            
	            return true;
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoProduto.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
	    }
	 
	  public boolean remover(Produto produto) {
	        String sql = "DELETE FROM produto WHERE id_produto=?";
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, produto.getId_produto());
	            stmt.execute();
	            return true;
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoProduto.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
	    }
	  

	public Produto buscar(Produto produto) {
		String sql = "SELECT * FROM produto WHERE id_produto=?";
		Produto retorno = new Produto();
		Categoria categoria = new Categoria();
		Fornecedor fornecedor = new Fornecedor();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, produto.getId_produto());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {

				retorno.setId_produto(resultado.getInt("id_produto"));
				retorno.setNome(resultado.getString("nome"));
				retorno.setPreco(resultado.getFloat("preco"));
				retorno.setDescricao(resultado.getString("descricao"));
				retorno.setValidade(resultado.getDate("validade").toLocalDate());
				retorno.setQuantidade(resultado.getInt("quantidade"));

				categoria.setId_categoria(resultado.getInt("id_categoria"));
				fornecedor.setId_fornecedor(resultado.getInt("id_fornecedor"));
				retorno.setFornecedor(fornecedor);
				retorno.setCategoria(categoria);

			}
		} catch (SQLException ex) {
			Logger.getLogger(DaoProduto.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public List<Produto> listar() {
		String sql = "select p.id_produto, p.nome, p.preco, p.descricao, p.validade, p.quantidade, p.id_categoria, c.nome, p.id_fornecedor, f.nome from produto as p inner join fornecedor as f using (id_fornecedor) inner join categoria as c using (id_categoria)";
		List<Produto> retorno = new ArrayList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
               // alterei para ober o nome do fornecedor e categoria
				Produto produto = new Produto();
				Categoria categoria = new Categoria();
				Fornecedor fornecedor = new Fornecedor();

				produto.setId_produto(resultado.getInt(1));
				produto.setNome(resultado.getString(2));
				produto.setPreco(resultado.getFloat(3));
				produto.setDescricao(resultado.getString(4));
				produto.setValidade(resultado.getDate(5).toLocalDate());
				produto.setQuantidade(resultado.getInt(6));

				categoria.setId_categoria(resultado.getInt(7));
				categoria.setNome(resultado.getString(8)); // adicionado para pegar o nome da categoria
				
				fornecedor.setId_fornecedor(resultado.getInt(9));
				fornecedor.setNome(resultado.getString(10)); // adicionado para pegar o nome do fornecedor
				// Obtendo os dados completos da Categoria associada ao Produto
				
				DaoCategoria categoriaDAO = new DaoCategoria();
				categoriaDAO.setConnection(connection);
				categoria = categoriaDAO.buscar(categoria);

				// Obtendo os dados completos do fornecedor associado ao Produto
				DaoFornecedor fornecedorDAO = new DaoFornecedor();
				fornecedorDAO.setConnection(connection);
				fornecedor = fornecedorDAO.buscar(fornecedor);

				produto.setCategoria(categoria);
				produto.setFornecedor(fornecedor);
				retorno.add(produto);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DaoProduto.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

}
