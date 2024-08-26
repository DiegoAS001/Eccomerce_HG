package edu.ifsp.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ifsp.modelo.Carrinho;
import edu.ifsp.modelo.Produto;
import edu.ifsp.modelo.Usuario;

public class CarrinhoDAO {
	public Carrinho save(Carrinho carrinho) throws PersistenceException {
		if (carrinho == null || carrinho.getUsuario() == null) {			
			throw new IllegalArgumentException(carrinho == null ? "carrinho == null" : "carrinho.usuario == null");
		}
		
		try (Connection conn = DatabaseConnector.getConnection()) {
			
			try {
				/* Iniciando transação */
				conn.setAutoCommit(false);
				
				PreparedStatement ps = conn.prepareStatement("DELETE FROM carrinho_item WHERE usuario = ?;");
				ps.setInt(1, carrinho.getUsuario().getId());
				ps.executeUpdate();
				
				ps = conn.prepareStatement("insert into carrinho_item (usuario, produto) values (?, ?);");
				for (Produto p : carrinho.getItens()) {
					ps.setInt(1, carrinho.getUsuario().getId());
					ps.setInt(2, p.getId());
					ps.executeUpdate();
				}
				
				/* Finalizando transação */
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

		return carrinho;
	}
	
	public Carrinho retrieveFor(Usuario usuario) throws PersistenceException {
		if (usuario == null) {
			throw new IllegalArgumentException();
		}
		
		Carrinho carrinho = new Carrinho();
		carrinho.setUsuario(usuario);
		
		try (Connection conn = DatabaseConnector.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT produto FROM carrinho_item WHERE usuario = ?;");
			ps.setInt(1, usuario.getId());
			ResultSet rs = ps.executeQuery();
			/*
			 * FIXME ProdutoDAO.findById() não deve abrir e fechar uma nova conexão a cada chamada. 
			 * Deve compartilhar a mesma conexão que foi aberta neste método.
			 */
			ProdutoDAO dao = new ProdutoDAO();
			while (rs.next()) {
				int produtoId = rs.getInt("produto");
				Produto p = dao.findById(produtoId);
				carrinho.adicionar(p);				
			}			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return carrinho;
	}
}
