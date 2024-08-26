package edu.ifsp.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ifsp.modelo.Cliente;

public class ClienteDAO {
	
	public Cliente save(Cliente cliente) throws PersistenceException {
		if (cliente.getUsuario() == null) {
			throw new IllegalStateException("cliente.usuario == null");
		}
		
		try (Connection conn = DatabaseConnector.getConnection()) {
		
			try {
				/* Iniciando transação */
				conn.setAutoCommit(false);
				
				/* Inserindo registro na tabela `usuario` */
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO usuario (username, password, salt) values (?, ?, ?);", 
						Statement.RETURN_GENERATED_KEYS);
				PasswordEncoder passwordEncoder = new PasswordEncoder();
				String salt = passwordEncoder.generateSalt();
				String encoded = passwordEncoder.encode(cliente.getUsuario().getPassword(), salt);
				ps.setString(1, cliente.getUsuario().getUsername());
				ps.setString(2, encoded);
				ps.setString(3, salt);
				
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (!rs.next()) {
					throw new PersistenceException("Missing key for 'usuario': " + cliente.getUsuario().getUsername()); 
				}
				cliente.getUsuario().setId(rs.getInt(1));
				
				
				/* Inserindo registro na tabela `cliente`, com relação 1:1 para `usuario` */
				ps = conn.prepareStatement(
						"INSERT INTO cliente (usuario, nome, email, logradouro, bairro, cidade, estado, cep) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, cliente.getUsuario().getId());
				ps.setString(2, cliente.getNome());
				ps.setString(3, cliente.getEmail());
				ps.setString(4, cliente.getLogradouro());
				ps.setString(5, cliente.getBairro());
				ps.setString(6, cliente.getCidade());
				ps.setString(7, cliente.getEstado());
				ps.setString(8, cliente.getCep());
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if (!rs.next()) {
					throw new PersistenceException("Missing key for 'cliente': " + cliente.getNome()); 
				}
				cliente.setId(rs.getInt(1));
				
				/* Finalizando transação */
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return cliente;
	}
}
