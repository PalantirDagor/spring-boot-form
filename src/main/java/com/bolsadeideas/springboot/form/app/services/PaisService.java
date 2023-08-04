package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

@Service
public class PaisService implements IPaisService {

	private List<Pais> lista;
	
	public PaisService() {

		this.lista = Arrays.asList(
				new Pais(1, "AR", "Argentina"),
				new Pais(2, "CL", "Chile"),
				new Pais(3, "CO", "Colombia"),
				new Pais(4, "ES", "España"),
				new Pais(5, "PE", "Peru"),
				new Pais(6, "VE", "Venezuela")
			);
	}
	
	@Override
	public List<Pais> listar() {
		
		return lista;
	}

	@Override
	public Pais obtenerPorId(Integer id) {

		Pais resultado = null;
		
		for(Pais pais: this.lista) {
			if(id.equals(pais.getId())) {
				
				resultado = pais;
				
				break;
			}
		}
		
		return resultado;
	}
}
