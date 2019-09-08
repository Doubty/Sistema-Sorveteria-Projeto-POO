package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.Item_venda;
import bean.Produto;
import bean.Venda;


public class DaoItem_venda {
	
	  private Connection connection;

	    public Connection getConnection() {
	        return connection;
	    }

	    public void setConnection(Connection connection) {
	        this.connection = connection;
	    }
	    
	    
	    public List<Item_venda> listarPorVenda(Venda venda) {
	        String sql = "SELECT * FROM item_venda WHERE id_item_venda=?";
	        List<Item_venda> retorno = new ArrayList<>();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, venda.getId_venda());
	            ResultSet resultado = stmt.executeQuery();
	            while (resultado.next()) {
	            	
	                Item_venda itemDeVenda = new Item_venda();
	                Produto produto = new Produto();
	                Venda v = new Venda();
	                
	                itemDeVenda.setId_item_venda(resultado.getInt("id_item_venda"));
	                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
	                itemDeVenda.setTotal_itens(resultado.getFloat("total_itens"));
	                
	                produto.setId_produto(resultado.getInt("id_produto"));
	                v.setId_venda(resultado.getInt("id_venda"));
	                
	                //Obtendo os dados completos do Produto associado ao Item de Venda
	                
	                DaoProduto produtoDAO = new DaoProduto();
	                produtoDAO.setConnection(connection);
	                produto = produtoDAO.buscar(produto);
	                
	                itemDeVenda.setProduto(produto);
	                itemDeVenda.setVenda(v);
	                
	                retorno.add(itemDeVenda);
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoItem_venda.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }
	    
	    public boolean inserir(Item_venda itemDeVenda) {
	        String sql = "INSERT INTO item_venda (quantidade, total_itens, id_produto, id_venda) VALUES(?,?,?,?)";
	        try {
	        	
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, itemDeVenda.getQuantidade());
	            stmt.setDouble(2, itemDeVenda.getTotal_itens());
	            stmt.setInt(3, itemDeVenda.getProduto().getId_produto());
	            stmt.setInt(4, itemDeVenda.getVenda().getId_venda());
	            
	            stmt.execute();
	            return true;
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoItem_venda.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
	    }
	    
	    public boolean remover(Item_venda itemDeVenda) {
	        String sql = "select * from item_venda where id_item_venda=?";
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            
	            stmt.setInt(1, itemDeVenda.getId_item_venda());
	            
	            stmt.execute();
	            return true;
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoItem_venda.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
	    }

}
