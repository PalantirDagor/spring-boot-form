package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Date;

//import org.springframework.format.annotation.DateTimeFormat;

import com.bolsadeideas.springboot.form.app.validation.IdentificadorRegex;
import com.bolsadeideas.springboot.form.app.validation.Requerido;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
//import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Usuario {

	//@NotEmpty
	//@Pattern(regexp = "[1-9]{2}[.][0-9]{3}[.][\\d]{3}[-][A-Z]{1}")
	@IdentificadorRegex
	private String identification;

	//@NotEmpty(message = "El nombre no puede estar vacio")
	private String name;
	
	// @NotEmpty
	@Requerido
	private String lastName;
	
	@NotBlank
	@Size(min = 3, max = 8)
	private String username;
	
	@NotEmpty
	private String password;
	
	// @NotEmpty
	@Requerido
	@Email
	private String email;
	
	@NotNull
	@Min(5)
	@Max(5000)
	private Integer cuenta;
	
	@Past
	//@Future
	@NotNull
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
