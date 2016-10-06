package ar.com.zumma.platform.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint( columnNames = "email")})
@NamedQueries({
  @NamedQuery(name = User.FIND_BY_EMAIL, query = "select u from User u where u.email = :email"),
  @NamedQuery(name = User.FIND_BY_FILTER, query = "select u from User u where u.email like :filter or role like :filter")
})
public class User extends BaseEntity {

	private static final long serialVersionUID = 7701592929924007476L;
	public static final String FIND_BY_EMAIL = "User.findByEmail";
	public static final String FIND_BY_FILTER = "User.findByFilter";
	
	public static final int STATUS_AVAILABLE = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private long id;

	@NotNull
	private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

	@NotNull
	private int status;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
    
    @Override
    public String toString(){
        return this.email;
    }
	
	public User() {
	}

	public User(long id) {
		this.id = id;
	}

	public User(String email, String password, int status, Role role) {
		super();
		this.email = email;
		this.passwordHash = password;
		this.status = status;
		this.roles.add(role);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String password) {
		this.passwordHash = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

}
