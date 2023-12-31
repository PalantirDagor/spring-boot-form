package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Rol;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.IPaisService;
import com.bolsadeideas.springboot.form.app.services.IRolService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class FormController {

	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private IPaisService paisService;
	
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@Autowired
	private IRolService rolService;
	
	@Autowired
	private RolesEditor rolEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		binder.addValidators(validador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true));
		
		binder.registerCustomEditor(String.class, "name", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "lastName", new NombreMayusculaEditor());
		
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);		
		binder.registerCustomEditor(Rol.class, "roles", rolEditor);
		
	}
	
	@ModelAttribute("sexo")
	public List<String> sexo(){
		
		return Arrays.asList("Hombre", "Mujer");
	}
	
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises(){
		
		return paisService.listar();
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
	
	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString(){
		
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		
		return roles;
	}
	
	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap(){
		
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		
		return roles;
	}
	
	@ModelAttribute("listaRoles")
	public List<Rol> listaRoles(){
		
		return this.rolService.listar();
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		
		Usuario usuario = new Usuario();
		usuario.setIdentification("12.457.588-D");
		usuario.setName("Jhon");
		usuario.setLastName("Doe");
		usuario.setHabilitarUsuario(true);
		usuario.setValorSecreto("Algún valor secreto ***");
		usuario.setPais(new Pais(3, "CO", "Colombia"));
		usuario.setRoles(Arrays.asList(new Rol(2, "Usuario", "ROLE_USER")));
		
		model.addAttribute("titulo", "Formulario Usuarios");
		model.addAttribute("usuario", usuario);
		
		return "form";
	}
	
	@PostMapping("/form")
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model) {
		
		// validador.validate(usuario, result); desacoplar el validador
		
		
		if(result.hasErrors()) {
			
			model.addAttribute("titulo", "Resultado del Formulario");
//			Map<String, String> errores = new HashMap<>();
//			result.getFieldErrors().forEach(err -> {
//				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
//			});
//			
//			model.addAttribute("error", errores);
			return "form";
		}
		
		return "redirect:/ver";
	}
	
	@GetMapping("/ver")
	public String ver(@SessionAttribute(name = "usuario", required = false) Usuario usuario, Model model, SessionStatus status) {
		
		if(usuario == null) {
			
			return "redirect:/form";
		}
		model.addAttribute("titulo", "Resultado del Formulario");
		
		status.setComplete();
		return "resultado";
	}
	
}
