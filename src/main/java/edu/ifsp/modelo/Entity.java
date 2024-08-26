package edu.ifsp.modelo;

public abstract class Entity {
	private int id = -1;

	public boolean isNew() {
		return id == -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
