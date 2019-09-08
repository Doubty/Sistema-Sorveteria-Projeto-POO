package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bean.Usuario;
import javafx.scene.control.Alert;

public class DaoUsuario {

	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Usuario checkLogin(String usuario, String senha) throws SQLException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Usuario usuario1 = new Usuario();
		// boolean check = false;

		try {
			stmt = connection.prepareStatement("SELECT * FROM usuario WHERE nome_login = ? and senha_login = ?");

			stmt.setString(1, usuario);
			stmt.setString(2, senha);

			rs = stmt.executeQuery();

			if (rs.next()) {

				usuario1.setUser(rs.getString("nome_login"));
				usuario1.setSenha(rs.getString("senha_login"));
				usuario1.setNivel(rs.getInt("nivel"));

				//check = true;

			}
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Erro ao cadastrar Usuario " + e);
			alert.show();

		} finally {

			// connection.close();

		}
		return usuario1;
	}

	public List<Usuario> listar() {
		String sql = "select * from usuario";
		List<Usuario> retorno = new ArrayList<>();
		try {

			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {

				Usuario usuario = new Usuario();

				usuario.setId(resultado.getInt("id_usuario"));
				usuario.setUser(resultado.getString("nome_login"));
				usuario.setSenha(resultado.getString("senha_login"));
				usuario.setNivel(resultado.getInt("nivel"));

				retorno.add(usuario);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public boolean inserir(Usuario usuario) {
		String sql = "insert into usuario (nome_login, senha_login, nivel) values (?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, usuario.getUser());
			stmt.setString(2, usuario.getSenha());
			stmt.setInt(3, usuario.getNivel());

			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean alterar(Usuario usuario) {
		String sql = "UPDATE usuario SET nome_login=?, senha_login=?, nivel=? WHERE id_usuario=?";
		try {

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, usuario.getUser());
			stmt.setString(2, usuario.getSenha());
			stmt.setInt(3, usuario.getNivel());
			stmt.setInt(4, usuario.getId());

			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean remover(Usuario usuario) {
		String sql = "DELETE FROM usuario WHERE id_usuario=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, usuario.getId());

			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

}