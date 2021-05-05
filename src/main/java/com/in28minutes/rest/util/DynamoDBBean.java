package com.in28minutes.rest.util;

public class DynamoDBBean {
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DynamoDBBean(String nombre) {
		super();
		this.nombre = nombre;
	}
	
}
