package ar.com.zumma.platform.domain;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;


public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 8759682917009905692L;

	private User user;

    public CurrentUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPasswordHash(), authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Collection<Role> getRoles() {
        return user.getRoles();
    }

    @Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
}
