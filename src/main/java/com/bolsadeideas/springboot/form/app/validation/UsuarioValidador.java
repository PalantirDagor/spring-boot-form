package com.bolsadeideas.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

@Component
public class UsuarioValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Usuario usuario = (Usuario)target;
		
		ValidationUtils.rejectIfEmpty(errors, "name", "NotEmpty.usuario.name");
		
		if(!usuario.getIdentification().matches("[1-9]{2}[.][0-9]{3}[.][\\d]{3}[-][A-Z]{1}")) {
			
			errors.rejectValue("identification", "Pattern.usuario.identification");
		}
	}

}
