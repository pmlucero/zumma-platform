package ar.com.zumma.platform.services.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.com.zumma.platform.domain.User;
import ar.com.zumma.platform.layout.form.UserForm;
import ar.com.zumma.platform.repositories.search.SearchDTO;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Page<User> getAllUsers(Pageable pageable);

    User create(UserForm form);
    
    User update(UserForm form);

    boolean delete(Long id);
    
    Page<User> getFilteredUsers(SearchDTO searchCriteria);
    
    Page<User> getUsersPage(Pageable pageable);
}
