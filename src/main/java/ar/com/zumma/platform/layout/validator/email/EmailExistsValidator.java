package ar.com.zumma.platform.layout.validator.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.zumma.platform.services.user.UserService;

public class EmailExistsValidator implements ConstraintValidator<EmailExists, String> {

	@Autowired
	UserService service;
	
	@Override
	public void initialize(EmailExists arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
	    if (value == null || value.isEmpty())
	        return true;
	    
	    try {
			if(service.getUserByEmail(value) != null)
				return false;
		} catch (UsernameNotFoundException e) {
			return true;
		}
	    
	    return true;
	}

}
