package edu.ifsp.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ifsp.modelo.Usuario;

public class UsuarioDAO {
	
	/**
	 * Recupera um objeto <code>Usuario</code>.
	 * 
	 * Retorna <code>null</code> quando <code>username</code> ou <code>password</code> forem inválidos.
	 */
	public Usuario check(String username, String password) throws PersistenceException {
		Usuario usuario = null;

		try (Connection conn = DatabaseConnector.getConnection()) {	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT id, username, password, salt FROM usuario WHERE username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			/* Se o usuário for encontrado */
			if (rs.next()) {				
				String salt = rs.getString("salt");
				String storedHash = rs.getString("password");
				
				PasswordEncoder encoder = new PasswordEncoder();
				String hash = encoder.encode(password, salt);
				
				/* A senha é válida? */
				if (storedHash.equals(hash)) {
					usuario = new Usuario();
					usuario.setId(rs.getInt("id"));
					usuario.setUsername(rs.getString("username"));
				}
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		
		return usuario;
	}

	
}
