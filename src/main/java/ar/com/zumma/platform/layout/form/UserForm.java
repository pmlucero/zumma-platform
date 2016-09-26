package ar.com.zumma.platform.layout.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ar.com.zumma.platform.domain.Role;
import ar.com.zumma.platform.domain.User;

public class UserForm {

	private Long id = null;
	
	@NotEmpty
	private String email = "";

	@NotEmpty
	private String password = "";

	@NotEmpty
	private String passwordRepeated = "";

	@NotNull
	private Role role = Role.USER;

	public UserForm() {}
	
	public UserForm(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.role = user.getRole();
		this.password = user.getPasswordHash();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserCreateForm{" + "email='" + email.replaceFirst("@.+", "@***") + '\'' + ", password=***" + '\''
				+ ", passwordRepeated=***" + '\'' + ", role=" + role + '}';
	}

}
