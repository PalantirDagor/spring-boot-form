package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class FormController {

	@Autowired
	private UsuarioValidador validador;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		binder.addValidators(validador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true));
		
		binder.registerCustomEditor(String.class, "name", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "lastName", new NombreMayusculaEditor());
	}
	
	@ModelAttribute("paises")
	public List<String> paises(){
		
		return Arrays.asList("Argentina", "Chile", "Colombia", "España", "Peru", "Venezuela");
	}
	
	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap(){
		
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("AR", "Argentina");
		paises.put("CL", "Chile");
		paises.put("CO", "Colombia");
		paises.put("ES", "España");
		paises.put("PE", "Peru");
		paises.put("VE", "Venezuela");
		
		return paises;
	}
	
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises(){
		
		return Arrays.asList(
					new Pais(1, "AR", "Argentina"),
					new Pais(2, "CL", "Chile"),
					new Pais(3, "CO", "Colombia"),
					new Pais(4, "ES", "España"),
					new Pais(5, "PE", "Peru"),
					new Pais(6, "VE", "Venezuela")
				);
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		
		Usuario usuario = new Usuario();
		usuario.setIdentification("12.457.588-D");
		usuario.setName("Jhon");
		usuario.setLastName("Doe");
		
		model.addAttribute("titulo", "Formulario Usuarios");
		model.addAttribute("usuario", usuario);
		
		return "form";
	}
	
	@PostMapping("/form")
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {
		
		// validador.validate(usuario, result); desacoplar el validador
		model.addAttribute("titulo", "Resultado del Formulario");
		
		if(result.hasErrors()) {
			
//			Map<String, String> errores = new HashMap<>();
//			result.getFieldErrors().forEach(err -> {
//				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
//			});
//			
//			model.addAttribute("error", errores);
			return "form";
		}
		
		model.addAttribute("usuario", usuario);
		
		return "resultado";
	}
}
