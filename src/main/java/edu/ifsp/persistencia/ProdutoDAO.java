package edu.ifsp.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.modelo.Produto;


public class ProdutoDAO {
	
	public List<Produto> findAll() throws PersistenceException {
		List<Produto> produtos = new ArrayList<>();
		
		try (Connection conn = DatabaseConnector.getConnection()) {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT id, nome, tipoMidia, preco, categoria, plataforma FROM produto;");
			
			
		    while (rs.next()) {
				Produto p = mapRow(rs);
		    	produtos.add(p);
		    }
			

		} catch (SQLException e) {
			throw new PersistenceException(e);			
		}
		return produtos;
	}


	public Produto save(Produto p) throws PersistenceException {
		
		if (p.isNew()) {
			insert(p);
		} else {
			update(p);
		}
		
		return p;
	}
	
	private void insert(Produto p) throws PersistenceException {
		try (Connection conn = DatabaseConnector.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO produto (nome, tipoMidia, preco, categoria, plataforma) VALUES (?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS
				);
			ps.setString(1, p.getNome());
			ps.setString(2, p.getTipoMidia());
			ps.setDouble(3, p.getPreco());
			ps.setString(4, p.getCategoria());
			ps.setString(5, p.getPlataforma());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (!rs.next()) {
				throw new IllegalStateException("Expected key missing.");
			}
			
			int id = rs.getInt(1);
			p.setId(id);			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	private void update(Produto p) throws PersistenceException {
		try (Connection conn = DatabaseConnector.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement("UPDATE produto SET nome = ?, tipoMidia = ?, preco = ?, categoria = ?, plataforma = ? WHERE id = ?;");
			ps.setString(1, p.getNome());
			ps.setString(2, p.getTipoMidia());
			ps.setDouble(3, p.getPreco());
			ps.setString(4, p.getCategoria());
			ps.setString(5, p.getPlataforma());
			ps.setInt(6, p.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}		
	}
	
	public void delete(Produto p) throws PersistenceException {
		try (Connection conn = DatabaseConnector.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement("DELETE FROM produto WHERE id = ?;");
			ps.setInt(1, p.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	public Produto findById(int id) throws PersistenceException {
		Produto p = null;
		
		try (Connection conn = DatabaseConnector.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement("SELECT id, nome, tipoMidia, preco, categoria, plataforma FROM produto WHERE id = ?;");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			
			if (rs.next()) {
				p = mapRow(rs);
			}
			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		
		return p;
	}


	private Produto mapRow(ResultSet rs) throws SQLException {
		Produto p = new Produto();
		p.setId(rs.getInt("id"));
		p.setNome(rs.getString("nome"));
		p.setTipoMidia(rs.getString("tipoMidia"));
		p.setPreco(rs.getDouble("preco"));
		p.setCategoria(rs.getString("categoria"));
		p.setPlataforma(rs.getString("plataforma"));
		return p;
	}
}