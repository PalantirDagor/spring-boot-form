package com.bolsadeideas.springboot.form.app.services;

import java.util.List;

import com.bolsadeideas.springboot.form.app.models.domain.Rol;

public interface IRolService {

	public List<Rol> listar();
	
	public Rol obtenerPorId(Integer id);
}
