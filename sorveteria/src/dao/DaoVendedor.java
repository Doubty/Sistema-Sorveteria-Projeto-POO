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
import bean.Vendedor;

public class DaoVendedor {

	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean inserir(Vendedor vendedor) {
		String sql = "insert into vendedor (carga_horaria, data_inicio, turno, cpf, salario, data_nasc, endereco, telefone, email, nome, sobrenome, id_usuario) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, vendedor.getCarga_horaria());
			stmt.setDate(2, Date.valueOf(vendedor.getData_inicio()));
			stmt.setString(3, vendedor.getTurno());
			stmt.setString(4, vendedor.getCpf());
			stmt.setFloat(5, vendedor.getSalario());
			stmt.setDate(6, Date.valueOf(vendedor.getData_nasc()));
			stmt.setString(7, vendedor.getEndereco());
			stmt.setString(8, vendedor.getTelefone());
			stmt.setString(9, vendedor.getEmail());
			stmt.setString(10, vendedor.getNome());
			stmt.setString(11, vendedor.getSobrenome());
			stmt.setInt(12, vendedor.getId_usuario());

			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DaoVendedor.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean alterar(Vendedor vendedor) {
		String sql = "UPDATE Vendedor SET carga_horaria=?, data_inicio=?, turno=?, cpf=?, salario=?, data_nasc=?, endereco=?, telefone=?, email=?, nome=?, sobrenome=?, id_usuario=? WHERE id_vendedor=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, vendedor.getCarga_horaria());
			stmt.setDate(2, Date.valueOf(vendedor.getData_inicio()));
			stmt.setString(3, vendedor.getTurno());
			stmt.setString(4, vendedor.getCpf());
			stmt.setFloat(5, vendedor.getSalario());
			stmt.setDate(6, Date.valueOf(vendedor.getData_nasc()));
			stmt.setString(7, vendedor.getEndereco());
			stmt.setString(8, vendedor.getTelefone());
			stmt.setString(9, vendedor.getEmail());
			stmt.setString(10, vendedor.getNome());
			stmt.setString(11, vendedor.getSobrenome());
			stmt.setInt(12, vendedor.getId_usuario());
			stmt.setInt(13, vendedor.getId_vendedor());

			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DaoVendedor.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean remover(Vendedor vendedor) {
		String sql = "DELETE FROM vendedor WHERE id_vendedor=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, vendedor.getId_vendedor());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DaoVendedor.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	// Método para listar os vendedores, retornando uma lista de vendedores
	public List<Vendedor> listar() {
		String sql = "select * from vendedor";
		List<Vendedor> retorno = new ArrayList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				Vendedor vendedor = new Vendedor();
				vendedor.setId_vendedor(resultado.getInt("id_vendedor"));
				vendedor.setNome(resultado.getString("nome"));
				vendedor.setSobrenome(resultado.getString("sobrenome"));
				vendedor.setEmail(resultado.getString("email"));
				vendedor.setTelefone(resultado.getString("telefone"));
				vendedor.setEndereco(resultado.getString("Endereco"));
				vendedor.setCpf(resultado.getString("cpf"));
				vendedor.setId_usuario(resultado.getInt("id_usuario"));
				vendedor.setSalario(resultado.getFloat("salario"));
				vendedor.setTurno(resultado.getString("turno"));
				vendedor.setData_inicio(resultado.getDate("data_inicio").toLocalDate());
				vendedor.setData_nasc(resultado.getDate("data_nasc").toLocalDate());
				vendedor.setCarga_horaria(resultado.getInt("carga_horaria"));
				retorno.add(vendedor);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DaoVendedor.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public Vendedor buscar(Vendedor vendedor) {
		String sql = "SELECT * FROM vendedor WHERE id_vendedor=?";
		Vendedor retorno = new Vendedor();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, vendedor.getId_vendedor());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {

				vendedor.setNome(resultado.getString("nome"));
				vendedor.setSobrenome(resultado.getString("sobrenome"));
				vendedor.setEmail(resultado.getString("email"));
				vendedor.setTelefone(resultado.getString("telefone"));
				vendedor.setEndereco(resultado.getString("Endereco"));
				vendedor.setCpf(resultado.getString("cpf"));
				vendedor.setId_usuario(resultado.getInt("id_usuario"));
				vendedor.setSalario(resultado.getFloat("salario"));
				vendedor.setTurno(resultado.getString("turno"));
				vendedor.setData_inicio(resultado.getDate("data_inicio").toLocalDate());
				vendedor.setData_nasc(resultado.getDate("data_nasc").toLocalDate());
				vendedor.setCarga_horaria(resultado.getInt("carga_horaria"));

				retorno = vendedor;
			}
		} catch (SQLException ex) {
			Logger.getLogger(DaoVendedor.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

}
