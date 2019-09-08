package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.Item_venda;
import bean.Venda;
import bean.Vendedor;


public class DaoVenda {

	   private Connection connection;

	    public Connection getConnection() {
	        return connection;
	    }

	    public void setConnection(Connection connection) {
	        this.connection = connection;
	    }
	
	    
	    public List<Venda> listar() {
	        String sql = "select * from venda";
	        List<Venda> retorno = new ArrayList<>();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            ResultSet resultado = stmt.executeQuery();
	            while (resultado.next()) {
	                Venda venda = new Venda();
	                Vendedor vendedor = new Vendedor();
	                List<Item_venda> item_venda = new ArrayList<Item_venda>();
                    
	                venda.setId_venda(resultado.getInt("id_venda"));
	                venda.setData(resultado.getDate("data").toLocalDate());
	                venda.setTotal_venda(resultado.getFloat("total_venda"));
	                venda.setPagamento(resultado.getBoolean("pagamento"));
	               vendedor.setId_vendedor(resultado.getInt("id_vendedor"));

	                //Obtendo os dados completos do vendedor associado a venda
	                DaoVendedor daoVendedor = new DaoVendedor();
	                daoVendedor.setConnection(connection);
	                vendedor = daoVendedor.buscar(vendedor);
                       
	                //Obtendo os dados completos dos Itens de Venda associados a venda
	                DaoItem_venda itemDeVendaDAO = new DaoItem_venda();
	                itemDeVendaDAO.setConnection(connection);
	                item_venda = itemDeVendaDAO.listarPorVenda(venda);

	                venda.setVendedor(vendedor);
	                venda.setItensDeVenda(item_venda);
	                retorno.add(venda);
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoVenda.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }
	    
	    public boolean inserir(Venda venda) {
	        String sql = "INSERT INTO venda (data, total_venda, pagamento, id_vendedor) VALUES(?,?,?,?)";
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql); 
	            stmt.setDate(1, Date.valueOf(venda.getData()));
	            stmt.setDouble(2, venda.getTotal_venda());
	            stmt.setBoolean(3, venda.isPagamento());
	            stmt.setInt(4, venda.getVendedor().getId_vendedor());
	            stmt.execute();
	            return true;
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoVenda.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
	    }
	    
	    public boolean remover(Venda venda) {
	        String sql = "DELETE FROM venda WHERE id_venda=?";
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, venda.getId_venda());
	            stmt.execute();
	            return true;
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoVenda.class.getName()).log(Level.SEVERE, null, ex);
	            return false;
	        }
	    }
	    
	    public Venda buscar(Venda venda) {
	        String sql = "SELECT * FROM venda WHERE id_venda=?";
	        Venda retorno = new Venda();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setInt(1, venda.getId_venda());
	            ResultSet resultado = stmt.executeQuery();
	            if (resultado.next()) {
	                Vendedor vendedor = new Vendedor();
	                venda.setId_venda(resultado.getInt("id_venda"));
	                venda.setData(resultado.getDate("data").toLocalDate());
	                venda.setTotal_venda(resultado.getFloat("total_venda"));
	                venda.setPagamento(resultado.getBoolean("pagamento"));
	                vendedor.setId_vendedor(resultado.getInt("id_vendedor"));
	                venda.setVendedor(vendedor);
	                retorno = venda;
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoVenda.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }
	    
	    public Venda buscarUltimaVenda() {
	        String sql = "SELECT max(id_venda) FROM venda";
	        Venda retorno = new Venda();
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            ResultSet resultado = stmt.executeQuery();

	            if (resultado.next()) {
	                retorno.setId_venda(resultado.getInt("max"));
	                return retorno;
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoVenda.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }
	    
	    public Map<Integer, ArrayList> listarQuantidadeVendasPorMes() {
	        String sql = "select count(id_venda), extract(year from data) as ano, extract(month from data) as mes from venda group by ano, mes order by ano, mes";
	        Map<Integer, ArrayList> retorno = new HashMap();
	        
	        try {
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            ResultSet resultado = stmt.executeQuery();

	            while (resultado.next()) {
	                ArrayList linha = new ArrayList();
	                if (!retorno.containsKey(resultado.getInt("ano")))
	                {
	                    linha.add(resultado.getInt("mes"));
	                    linha.add(resultado.getInt("count"));
	                    retorno.put(resultado.getInt("ano"), linha);
	                }else{
	                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
	                    linhaNova.add(resultado.getInt("mes"));
	                    linhaNova.add(resultado.getInt("count"));
	                }
	            }
	            return retorno;
	        } catch (SQLException ex) {
	            Logger.getLogger(DaoVenda.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return retorno;
	    }
	
}
