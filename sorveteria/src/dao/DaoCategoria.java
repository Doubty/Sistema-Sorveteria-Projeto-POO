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


public class DaoCategoria {

	 private Connection connection;

	    public Connection getConnection() {
	        return connection;
	    }

	    public void setConnection(Connection connection) {
	        this.connection = connection;
	    }
	    
	    public Categoria buscar(Categoria categoria) {
	        String sql = "SELECT * FROM categoria WHERE id_categoria=?";
	        Categoria retorno = new Categoria();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, categoria.getId_categoria());
	            ResultSet resultado = stmt.executeQuery();
	            if (resultado.next()) {
	            	
	                categoria.setNome(resultado.getString("nome"));
	                retorno = categoria;
	            }
	            
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoCategoria.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }
	    
	    public List<Categoria> listar() {
			String sql = "select * from categoria";
			List<Categoria> retorno = new ArrayList<>();
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				ResultSet resultado = stmt.executeQuery();
				while (resultado.next()) {
					
					Categoria categoria = new Categoria();
					categoria.setId_categoria(resultado.getInt("id_categoria"));
					categoria.setNome(resultado.getString("nome"));
				
			
					retorno.add(categoria);
				}
			} catch (SQLException ex) {
				Logger.getLogger(DaoCategoria.class.getName()).log(Level.SEVERE, null, ex);
			}
			return retorno;
		}
	    
	    public boolean inserir(Categoria categoria) {
			String sql = "insert into categoria (nome) values (?)";
			try {
				
				PreparedStatement stmt = connection.prepareStatement(sql);
	
				stmt.setString(1, categoria.getNome());
			

				stmt.execute();
				return true;
			} catch (SQLException ex) {
				Logger.getLogger(DaoCategoria.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		}
	
}
