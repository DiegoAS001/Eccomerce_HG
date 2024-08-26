package edu.ifsp.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrinho extends Entity {
	private List<Produto> itens = new ArrayList<>();
	private Usuario usuario;
	
	public List<Produto> getItens() {
		return Collections.unmodifiableList(itens);
	}
	
	public void adicionar(Produto produto) {
		itens.add(produto);
	}
	
	public double getTotal() {
		double total = 0;
		for (Produto p : itens) {
			total = total + p.getPreco();
		}
		return total;
	}
	
	public int getCount() {
		return itens.size();
	}
	
	public void remover(int pos) {
		itens.remove(pos);
	}
	
	public boolean isVazio() {
		return itens.isEmpty();
	}
	
	public void esvaziar() {
		itens = new ArrayList<>();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
