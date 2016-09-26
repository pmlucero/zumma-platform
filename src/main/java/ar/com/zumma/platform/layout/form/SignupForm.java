package ar.com.zumma.platform.layout.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ar.com.zumma.platform.domain.Role;
import ar.com.zumma.platform.domain.User;
import ar.com.zumma.platform.layout.validator.email.EmailExists;

public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{general.validation.notBlank}";
	private static final String EMAIL_MESSAGE = "{general.validation.email}";
	private static final String LENGTH_MESSAGE = "{general.validation.length}";
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	@EmailExists
	private String username;
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Length(message = SignupForm.LENGTH_MESSAGE, min = 8)
	private String password;

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
	
	public User createAccount() {
		return new User(getUsername(), getPassword(), User.STATUS_AVAILABLE, Role.USER);
	}
}
