package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.Fornecedor;


public class DaoFornecedor {

	 private Connection connection;

	    public Connection getConnection() {
	        return connection;
	    }

	    public void setConnection(Connection connection) {
	        this.connection = connection;
	    }
	    
	    
	    public Fornecedor buscar(Fornecedor fornecedor) {
	        String sql = "SELECT * FROM fornecedor WHERE id_fornecedor=?";
	        Fornecedor retorno = new Fornecedor();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, fornecedor.getId_fornecedor());
	            ResultSet resultado = stmt.executeQuery();
	            if (resultado.next()) {
	            	
	                fornecedor.setCnpj(resultado.getString("cnpj"));
	               fornecedor.setNome(resultado.getString("nome"));
	               fornecedor.setEndereco(resultado.getString("endereco"));
	               fornecedor.setTelefone(resultado.getString("telefone"));
	                
	                retorno = fornecedor;
	            }
	            
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoFornecedor.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }
	    
		// Método para listar os fornecedores, retornando uma lista de fornecedores
	    
		public List<Fornecedor> listar() {
			String sql = "select * from fornecedor";
			List<Fornecedor> retorno = new ArrayList<>();
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				ResultSet resultado = stmt.executeQuery();
				while (resultado.next()) {
					
					Fornecedor fornecedor = new Fornecedor();
					fornecedor.setId_fornecedor(resultado.getInt("id_fornecedor"));
					fornecedor.setCnpj(resultado.getString("cnpj"));
					fornecedor.setNome(resultado.getString("nome"));
					fornecedor.setEndereco(resultado.getString("endereco"));
					fornecedor.setTelefone(resultado.getString("telefone"));
			
					retorno.add(fornecedor);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DaoFornecedor.class.getName()).log(Level.SEVERE, null, ex);
			}
			return retorno;
		}
		
		public boolean inserir(Fornecedor fornecedor) {
			String sql = "insert into fornecedor (cnpj, nome, endereco, telefone) values (?,?,?,?)";
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);

				stmt.setString(1, fornecedor.getCnpj());
				stmt.setString(2, fornecedor.getNome());
				stmt.setString(3, fornecedor.getEndereco());
				stmt.setString(4, fornecedor.getTelefone());

				stmt.execute();
				return true;
			} catch (SQLException ex) {
				Logger.getLogger(DaoFornecedor.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		}
		
		public boolean alterar(Fornecedor fornecedor) {
			String sql = "UPDATE fornecedor SET cnpj=?, nome=?, endereco=?, telefone=? WHERE id_fornecedor=?";
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				
				stmt.setString(1, fornecedor.getCnpj());
				stmt.setString(2, fornecedor.getNome());
				stmt.setString(3, fornecedor.getEndereco());
				stmt.setString(4, fornecedor.getTelefone());
				stmt.setInt(5, fornecedor.getId_fornecedor());
				stmt.execute();
				return true;
			} catch (SQLException ex) {
				Logger.getLogger(DaoFornecedor.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		}
		
		public boolean remover(Fornecedor fornecedor) {
			String sql = "DELETE FROM fornecedor WHERE id_fornecedor=?";
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setInt(1, fornecedor.getId_fornecedor());
				stmt.execute();
				return true;
			} catch (SQLException ex) {
				Logger.getLogger(DaoFornecedor.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		}
	
}
