package ar.com.zumma.platform.services.user;

import java.util.Collection;
import java.util.Optional;

import ar.com.zumma.platform.domain.User;
import ar.com.zumma.platform.layout.form.UserForm;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserForm form);
    
    User update(UserForm form);

    boolean delete(Long id);
}
