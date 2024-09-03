package edu.ifsp.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ifsp.modelo.Cartao;

public class CartaoDAO {
	
	
	public Cartao save(Cartao cartao) throws PersistenceException {
		if (cartao.getNome() == null) {
			throw new IllegalStateException("cartao.nome == null");
		}
		
		try (Connection conn = DatabaseConnector.getConnection()) {
		
			try {
				/* Iniciando transação */
				conn.setAutoCommit(false);
				
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO cartao (nome, numero, cvv, data_vencimento) values (?, ?, ?, ?);", 
						Statement.RETURN_GENERATED_KEYS);
				
				ps.setString(1, cartao.getNome());
				ps.setString(2, cartao.getNumero());
				ps.setInt(3, cartao.getCvv());
				ps.setString(4, cartao.getData_vencimento());
				
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				
				/* Finalizando transação */
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return cartao;
	}
}
